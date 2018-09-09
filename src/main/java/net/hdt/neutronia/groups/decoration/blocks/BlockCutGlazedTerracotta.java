package net.hdt.neutronia.groups.decoration.blocks;

import net.hdt.neutronia.base.blocks.BlockNeutroniaPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;

public class BlockCutGlazedTerracotta extends BlockNeutroniaPillar {

    public BlockCutGlazedTerracotta(EnumDyeColor color) {
        super(Material.CLAY, String.format("%s_cut_glazed_terracotta", color.getName()));
        setHardness(1.4F);
        setSoundType(SoundType.STONE);
        setCreativeTab(CreativeTabs.DECORATIONS);
    }

}
