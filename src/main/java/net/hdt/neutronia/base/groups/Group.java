package net.hdt.neutronia.base.groups;

import net.hdt.neutronia.base.lib.LibMisc;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Group implements Comparable<Group> {

    public final Map<String, Component> components = new HashMap<>();
    private final List<Component> enabledComponents = new ArrayList<>();
    public String name;
    public boolean enabled;
    public Property prop;
    private ItemStack iconStack;

    public Group() {
        name = getClass().getSimpleName().replaceAll("Neutronia", "").toLowerCase();
        GroupLoader.registerGroup(this);
    }

    public Group(Builder builder) {
        name = builder.name;
        for (Component component : builder.components) {
            registerComponent(component);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private void addComponents() {
        // NO-OP
    }

    private void registerComponent(Component component) {
        registerComponent(component, convertName(component.getClass().getSimpleName()));
    }

    public void registerComponent(Component component, boolean enabledByDefault) {
        registerComponent(component, convertName(component.getClass().getSimpleName()), enabledByDefault);
    }

    private String convertName(String origName) {
        String withSpaces = origName.replaceAll("(?<=.)([A-Z])", " $1").toLowerCase();
        return Character.toUpperCase(withSpaces.charAt(0)) + withSpaces.substring(1);
    }

    private void registerComponent(Component component, String name) {
        registerComponent(component, name, true);
    }

    private void registerComponent(Component component, String name, boolean enabledByDefault) {
        Class<? extends Component> clazz = component.getClass();
        if (GroupLoader.componentInstances.containsKey(clazz))
            throw new IllegalArgumentException("Component " + clazz + " is already registered!");

        GroupLoader.componentInstances.put(clazz, component);
        GroupLoader.componentClassNames.put(clazz.getSimpleName(), component);
        components.put(name, component);

        component.enabledByDefault = enabledByDefault;
        component.prevEnabled = false;

        component.group = this;
        component.configName = name;
        component.configCategory = this.name + "." + name;
    }

    public void setupConfig() {
        if (components.isEmpty())
            addComponents();

        forEachComponent(component -> {
            ConfigHelper.needsRestart = component.requiresMinecraftRestartToEnable();
            component.enabled = loadPropBool(component.configName, component.getFeatureDescription(), component.enabledByDefault) && enabled;
            component.prop = ConfigHelper.lastProp;

            component.setupConstantConfig();

            if (!component.forceLoad && GlobalConfig.enableAntiOverlap) {
                String[] incompatibilities = component.getIncompatibleMods();
                if (incompatibilities != null) {
                    List<String> failiures = new ArrayList<>();

                    for (String s : incompatibilities)
                        if (Loader.isModLoaded(s)) {
                            component.enabled = false;
                            failiures.add(s);
                        }

                    if (!failiures.isEmpty())
                        LibMisc.LOGGER.info("'" + component.configName + "' is forcefully disabled as it's incompatible with the following loaded mods: " + failiures);
                }
            }

            if (!component.loadtimeDone) {
                component.enabledAtLoadtime = component.enabled;
                component.loadtimeDone = true;
            }

            if (component.enabled && !enabledComponents.contains(component))
                enabledComponents.add(component);
            else if (!component.enabled)
                enabledComponents.remove(component);

            component.setupConfig();

            if (!component.enabled && component.prevEnabled) {
                if (component.hasSubscriptions())
                    MinecraftForge.EVENT_BUS.unregister(component);
                if (component.hasTerrainSubscriptions())
                    MinecraftForge.TERRAIN_GEN_BUS.unregister(component);
                if (component.hasOreGenSubscriptions())
                    MinecraftForge.ORE_GEN_BUS.unregister(component);
                component.onDisabled();
            } else if (component.enabled && (component.enabledAtLoadtime || !component.requiresMinecraftRestartToEnable()) && !component.prevEnabled) {
                if (component.hasSubscriptions())
                    MinecraftForge.EVENT_BUS.register(component);
                if (component.hasTerrainSubscriptions())
                    MinecraftForge.TERRAIN_GEN_BUS.register(component);
                if (component.hasOreGenSubscriptions())
                    MinecraftForge.ORE_GEN_BUS.register(component);
                component.onEnabled();
            }

            component.prevEnabled = component.enabled;
        });
    }

    public void preInit(FMLPreInitializationEvent event) {
        forEachEnabled(component -> component.preInit(event));
    }

    void postPreInit(FMLPreInitializationEvent event) {
        forEachEnabled(component -> component.postPreInit(event));
    }

    public void init(FMLInitializationEvent event) {
        forEachEnabled(component -> component.init(event));
    }

    public void postInit(FMLPostInitializationEvent event) {
        forEachEnabled(component -> component.postInit(event));
    }

    void finalInit(FMLPostInitializationEvent event) {
        forEachEnabled(component -> component.finalInit(event));
    }

    @SideOnly(Side.CLIENT)
    void preInitClient(FMLPreInitializationEvent event) {
        forEachEnabled(component -> component.preInitClient(event));
    }

    @SideOnly(Side.CLIENT)
    void initClient(FMLInitializationEvent event) {
        forEachEnabled(component -> component.initClient(event));
    }

    @SideOnly(Side.CLIENT)
    void postInitClient(FMLPostInitializationEvent event) {
        forEachEnabled(component -> component.postInitClient(event));
    }

    void serverStarting(FMLServerStartingEvent event) {
        forEachEnabled(component -> component.serverStarting(event));
    }

    boolean canBeDisabled() {
        return true;
    }

    boolean isEnabledByDefault() {
        return true;
    }

    String getModuleDescription() {
        return "";
    }

    public ItemStack getIconStack() {
        if(iconStack != null) {
            return iconStack;
        } else {
            return new ItemStack(Blocks.BARRIER);
        }
    }

    public void setIconStack(ItemStack stack) {
        this.iconStack = stack;
    }

    public void forEachComponent(Consumer<Component> consumer) {
        components.values().forEach(consumer);
    }

    private void forEachEnabled(Consumer<Component> consumer) {
        enabledComponents.forEach(consumer);
    }

    public final int loadPropInt(String propName, String desc, int default_) {
        return ConfigHelper.loadPropInt(propName, name, desc, default_);
    }

    public final double loadPropDouble(String propName, String desc, double default_) {
        return ConfigHelper.loadPropDouble(propName, name, desc, default_);
    }

    private boolean loadPropBool(String propName, String desc, boolean default_) {
        return ConfigHelper.loadPropBool(propName, name, desc, default_);
    }

    public final String loadPropString(String propName, String desc, String default_) {
        return ConfigHelper.loadPropString(propName, name, desc, default_);
    }

    @Override
    public int compareTo(Group o) {
        return name.compareTo(o.name);
    }

    public static class Builder {

        private String name, desc;
        private ItemStack icon;
        private Component component;
        private Group group;
        private boolean enabled;
        private List<Component> components = new ArrayList<>();

        public Builder withName(String name) {
            if (!name.isEmpty()) {
                this.name = name;
            } else {
                this.name = "Missing Group Name";
            }
            return this;
        }

        public Builder withDesc(String desc) {
            if (!desc.isEmpty()) {
                this.desc = desc;
            } else {
                this.desc = "This is an example description";
            }
            return this;
        }

        public Builder withComponent(Component component) {
            this.component = component;
            components.add(component);
            return this;
        }

        public Builder withIcon(ItemStack icon) {
            if (!icon.isEmpty()) {
                this.icon = icon;
            } else {
                this.icon = group.getIconStack();
            }
            return this;
        }

        public Builder isEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Group register() {
            group = new Group(this);
            group.enabled = this.enabled;
            group.setIconStack(icon);
            GroupLoader.registerGroup(group);
            return group;
        }

    }

}
