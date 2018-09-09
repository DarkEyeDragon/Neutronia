package net.hdt.neutronia.groups.decoration.features;

import net.hdt.neutronia.base.groups.Component;
import net.hdt.neutronia.groups.decoration.blocks.BlockGlazedTerracottaStripes;
import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class GlazedTerracottaStripes extends Component {

    private Block glazedTerracottaStriped;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        for(EnumDyeColor color : EnumDyeColor.values()) {
            glazedTerracottaStriped = new BlockGlazedTerracottaStripes(color);
        }
    }

    @Override
    public boolean requiresMinecraftRestartToEnable() {
        return true;
    }

}