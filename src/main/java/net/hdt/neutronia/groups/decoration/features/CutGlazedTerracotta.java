package net.hdt.neutronia.groups.decoration.features;

import net.hdt.neutronia.base.groups.Component;
import net.hdt.neutronia.groups.decoration.blocks.BlockCutGlazedTerracotta;
import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CutGlazedTerracotta extends Component {

    private Block cutGlazedTerracotta;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        for(EnumDyeColor color : EnumDyeColor.values()) {
            cutGlazedTerracotta = new BlockCutGlazedTerracotta(color);
        }
    }

    @Override
    public boolean requiresMinecraftRestartToEnable() {
        return true;
    }

}