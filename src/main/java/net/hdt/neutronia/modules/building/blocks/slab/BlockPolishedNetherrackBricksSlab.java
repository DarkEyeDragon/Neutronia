package net.hdt.neutronia.modules.building.blocks.slab;

import net.hdt.neutronia.blocks.overworld.BlockOverworldSlabBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockPolishedNetherrackBricksSlab extends BlockOverworldSlabBase {

    public BlockPolishedNetherrackBricksSlab(boolean doubleSlab) {
        super("polished_netherrack_bricks_slab", Material.ROCK, doubleSlab);
        setHardness(1.5F);
        setResistance(10.0F);
        setSoundType(SoundType.STONE);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

}
