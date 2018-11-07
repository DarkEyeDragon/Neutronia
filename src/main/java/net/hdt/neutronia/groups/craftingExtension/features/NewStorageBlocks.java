package net.hdt.neutronia.groups.craftingExtension.features;

import net.hdt.neutronia.base.blocks.BlockNeutroniaBase;
import net.hdt.neutronia.base.groups.Component;
import net.hdt.neutronia.groups.craftingExtension.blocks.BlockBarrel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class NewStorageBlocks extends Component {

    public static Block[] barrels = new Block[6], lecterns = new Block[6];

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        for(BlockPlanks.EnumType type : BlockPlanks.EnumType.values()) {
            barrels[type.getMetadata()] = new BlockBarrel(type);
            lecterns[type.getMetadata()] = new BlockNeutroniaBase(Material.WOOD, String.format("%s_lectern", type.getName()));
        }
    }

}