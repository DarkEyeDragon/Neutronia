package net.hdt.neutronia.groups.decoration.features;

import net.hdt.neutronia.base.groups.Component;
import net.hdt.neutronia.groups.decoration.blocks.BlockGlazedTerracottaPillars;
import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class GlazedTerracottaPillar extends Component {

    private Block glazedTerracottaPillar;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        for(EnumDyeColor color : EnumDyeColor.values()) {
            glazedTerracottaPillar = new BlockGlazedTerracottaPillars(color);
        }
    }

    @Override
    public boolean requiresMinecraftRestartToEnable() {
        return true;
    }

}