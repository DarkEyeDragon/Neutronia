package net.hdt.neutronia.groups.decoration.blocks;

import net.hdt.huskylib2.block.BlockMetaVariants;
import net.hdt.neutronia.base.blocks.INeutroniaBlock;
import net.hdt.neutronia.init.NCreativeTabs;
import net.minecraft.block.material.Material;

public class BlockDecorativeAquamarine extends BlockMetaVariants implements INeutroniaBlock {

    public BlockDecorativeAquamarine() {
        super("decorative_aquamarine", Material.ROCK, Variants.class);
        setCreativeTab(NCreativeTabs.OCEAN_EXPANSION_TAB);
    }

    public enum Variants implements EnumBase {
        DECORATIVE_AQUAMARINE_BRICKS,
        CHISELED_DECORATIVE_AQUAMARINE,
        RAW_DECORATIVE_AQUAMARINE,
        SMALL_DECORATIVE_AQUAMARINE_BRICKS,
        SMOOTH_DECORATIVE_AQUAMARINE
    }

}
