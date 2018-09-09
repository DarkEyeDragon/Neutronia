package net.hdt.neutronia.groups.decoration.blocks;

import net.hdt.neutronia.base.blocks.BlockNeutroniaPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;

public class BlockGlazedTerracottaPillars extends BlockNeutroniaPillar {

    public BlockGlazedTerracottaPillars(EnumDyeColor color) {
        super(Material.CLAY, String.format("%s_glazed_terracotta_pillar", color.getName()));
        setHardness(1.4F);
        setSoundType(SoundType.STONE);
        setCreativeTab(CreativeTabs.DECORATIONS);
    }

}