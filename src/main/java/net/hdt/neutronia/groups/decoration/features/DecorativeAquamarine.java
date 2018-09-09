package net.hdt.neutronia.groups.decoration.features;

import net.hdt.neutronia.base.groups.Component;
import net.hdt.neutronia.groups.decoration.blocks.BlockDecorativeAquamarine;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class DecorativeAquamarine extends Component {

    private static Block decorativeAquamarine;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        decorativeAquamarine = new BlockDecorativeAquamarine();
    }

}
