package net.hdt.neutronia.groups.world.features.overworld;

import net.hdt.neutronia.base.groups.Component;
import net.hdt.neutronia.groups.world.blocks.BlockNaturalAquamarine;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class NaturalAquamarine extends Component {

    private static Block naturalAquamarine;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        naturalAquamarine = new BlockNaturalAquamarine();
    }

    @Override
    public boolean requiresMinecraftRestartToEnable() {
        return true;
    }

}
