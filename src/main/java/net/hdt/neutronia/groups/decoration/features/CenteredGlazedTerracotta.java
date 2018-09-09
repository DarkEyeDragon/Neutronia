package net.hdt.neutronia.groups.decoration.features;

import net.hdt.neutronia.base.groups.Component;
import net.hdt.neutronia.groups.decoration.blocks.BlockCenteredGlazedTerracotta;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CenteredGlazedTerracotta extends Component {

    private Block centeredGlazedTerracotta;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        centeredGlazedTerracotta = new BlockCenteredGlazedTerracotta();
    }

    @Override
    public boolean requiresMinecraftRestartToEnable() {
        return true;
    }

}