package net.hdt.neutronia.groups.dimensions.features;

import net.hdt.neutronia.base.groups.Component;
import net.hdt.neutronia.groups.dimensions.world.biomes.moon.BiomeMoonMain;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.hdt.neutronia.base.util.Reference.MOD_ID;

public class MoonBiomes extends Component {

    public static final Biome MOON_MAIN = new BiomeMoonMain();

    @SubscribeEvent
    public void registerBiome(RegistryEvent.Register<Biome> event) {
        event.getRegistry().register(MOON_MAIN.setRegistryName(MOD_ID, "moon"));
    }

    @Override
    public boolean hasSubscriptions() {
        return true;
    }

    @Override
    public boolean requiresMinecraftRestartToEnable() {
        return true;
    }

}
