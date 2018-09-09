package net.hdt.neutronia.groups.decoration.blocks;

import net.hdt.huskylib2.block.BlockMetaVariants;
import net.hdt.neutronia.base.blocks.INeutroniaBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockCenteredGlazedTerracotta extends BlockMetaVariants implements INeutroniaBlock {

    public BlockCenteredGlazedTerracotta() {
        super("centered_glazed_terracotta", Material.CLAY, Variants.class);
        setHardness(1.4F);
        setSoundType(SoundType.STONE);
        setCreativeTab(CreativeTabs.DECORATIONS);
    }

    public enum Variants implements EnumBase {
        WHITE_CENTERED_GLAZED_TERRACOTTA,
        ORANGE_CENTERED_GLAZED_TERRACOTTA,
        MAGENTA_CENTERED_GLAZED_TERRACOTTA,
        LIGHT_BLUE_CENTERED_GLAZED_TERRACOTTA,
        YELLOW_CENTERED_GLAZED_TERRACOTTA,
        LIME_CENTERED_GLAZED_TERRACOTTA,
        PINK_CENTERED_GLAZED_TERRACOTTA,
        GRAY_CENTERED_GLAZED_TERRACOTTA,
        SILVER_CENTERED_GLAZED_TERRACOTTA,
        CYAN_CENTERED_GLAZED_TERRACOTTA,
        PURPLE_CENTERED_GLAZED_TERRACOTTA,
        BLUE_CENTERED_GLAZED_TERRACOTTA,
        BROWN_CENTERED_GLAZED_TERRACOTTA,
        GREEN_CENTERED_GLAZED_TERRACOTTA,
        RED_CENTERED_GLAZED_TERRACOTTA,
        BLACK_CENTERED_GLAZED_TERRACOTTA
    }

}
