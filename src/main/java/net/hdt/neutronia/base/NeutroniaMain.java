package net.hdt.neutronia.base;

import net.hdt.neutronia.base.lib.LibMisc;
import net.hdt.neutronia.base.groups.GroupLoader;
import net.hdt.neutronia.base.proxy.CommonProxy;
import net.hdt.neutronia.base.util.Reference;
import net.hdt.neutronia.events.ILifeCycleHandler;
import net.hdt.neutronia.events.handlers.EventHandlers;
import net.hdt.neutronia.events.handlers.RecipeHandlers;
import net.hdt.neutronia.init.NEnchantments;
import net.hdt.neutronia.groups.NGroups;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static net.hdt.neutronia.base.util.Reference.*;

@Mod(modid = MOD_ID, name = NAME, version = VERSION, dependencies = DEPENDENCIES, guiFactory = LibMisc.GUI_FACTORY, updateJSON = UPDATE_JSON)
public class NeutroniaMain {

    public static Logger LOGGER;

    @Mod.Instance
    public static NeutroniaMain instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    public static CommonProxy proxy;

    private List<ILifeCycleHandler> handlers = new ArrayList<ILifeCycleHandler>(){{
        add(new EventHandlers());
        add(new RecipeHandlers());
    }};

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
        handlers.forEach(handler -> handler.preInit(event));
        proxy.preInit(event);
        if(GroupLoader.groupInstances.containsKey(NGroups.building.getClass())) {
            NeutroniaMain.LOGGER.info("Group Test Building is loaded!");
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        handlers.forEach(handler -> handler.init(event));
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        handlers.forEach(handler -> handler.postInit(event));
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event){
        handlers.forEach(handler -> handler.loadComplete(event));
    }

    @Mod.EventHandler
    public void serverInit(FMLServerStartingEvent event) {
        handlers.forEach(handler -> handler.serverInit(event));
    }

    public static DamageSource func_203096_a(Entity p_203096_0_, @Nullable Entity p_203096_1_)
    {
        return (new EntityDamageSourceIndirect("trident", p_203096_0_, p_203096_1_)).setProjectile();
    }

    public static int func_203191_f(ItemStack p_203191_0_)
    {
        return EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(NEnchantments.field_203193_C), p_203191_0_);
    }

    public static int func_203190_g(ItemStack p_203190_0_)
    {
        return EnchantmentHelper.getEnchantmentLevel(NEnchantments.field_203195_E, p_203190_0_);
    }

    public static boolean func_203192_h(ItemStack p_203192_0_)
    {
        return EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(NEnchantments.field_203196_F), p_203192_0_) > 0;
    }

}