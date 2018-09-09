package net.hdt.neutronia.groups.world.blocks;

import net.hdt.huskylib2.block.BlockMetaVariants;
import net.hdt.neutronia.base.blocks.INeutroniaBlock;
import net.hdt.neutronia.init.NCreativeTabs;
import net.minecraft.block.material.Material;

public class BlockNaturalAquamarine extends BlockMetaVariants implements INeutroniaBlock {

    public BlockNaturalAquamarine() {
        super("natural_aquamarine", Material.ROCK, Variants.class);
        setCreativeTab(NCreativeTabs.OCEAN_EXPANSION_TAB);
    }

    public enum Variants implements EnumBase {
        NATURAL_CRACKED_AQUAMARINE_BRICKS,
        NATURAL_MOSSY_AQUAMARINE_BRICKS,
        NATURAL_CRACKED_CHISELED_AQUAMARINE,
        NATURAL_MOSSY_CHISELED_AQUAMARINE,
        RAW_NATURAL_AQUAMARINE,
        NATURAL_CRACKED_SMALL_AQUAMARINE_BRICKS,
        NATURAL_MOSSY_SMALL_AQUAMARINE_BRICKS,
        NATURAL_CRACKED_SMOOTH_AQUAMARINE,
        NATURAL_MOSSY_SMOOTH_AQUAMARINE
    }

}
