//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.hdt.neutronia.blocks.base;

import net.hdt.huskylib2.interf.IBlockColorProvider;
import net.hdt.huskylib2.interf.IModBlock;
import net.hdt.huskylib2.item.ItemModBlock;
import net.hdt.huskylib2.recipe.RecipeHandler;
import net.hdt.huskylib2.util.ProxyRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.text.WordUtils;

import javax.annotation.Nullable;
import java.util.List;

public abstract class BlockModColoredStairs extends BlockStairs implements IModBlock, IBlockColorProvider {

    public final EnumDyeColor color;
    private final String[] variants;
    private String bareName;

    public BlockModColoredStairs(String name, IBlockState state, EnumDyeColor color) {
        super(state);
        this.variants = new String[]{name};
        this.bareName = name;
        this.color = color;
        this.setTranslationKey(color.getName() + "_" + name);
        this.useNeighborBrightness = true;
    }

    public static void initStairs(Block base, int meta, BlockStairs block) {
        RecipeHandler.addOreDictRecipe(ProxyRegistry.newStack(block, 4), "B  ", "BB ", "BBB", 'B', ProxyRegistry.newStack(base, 1, meta));
    }

    public Block setTranslationKey(String name) {
        super.setTranslationKey(name);
        this.setRegistryName(this.getPrefix(), name);
        ProxyRegistry.register(this);
        ProxyRegistry.register(new ItemModBlock(this, new ResourceLocation(this.getPrefix(), name)));
        return this;
    }

    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        if(blockState.getMaterial() == Material.GLASS) {
            if (this.getDefaultState().withProperty(SHAPE, EnumShape.STRAIGHT) == blockState) {
                return this.originalShouldSideBeRendered(blockState, blockAccess, pos, side);
            } else if (side != EnumFacing.UP && side != EnumFacing.DOWN && !super.shouldSideBeRendered(blockState, blockAccess, pos, side)) {
                return false;
            }
        }

        return this.originalShouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @SideOnly(Side.CLIENT)
    private boolean originalShouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        AxisAlignedBB axisalignedbb = blockState.getBoundingBox(blockAccess, pos);

        switch (side) {
            case DOWN:

                if (axisalignedbb.minY > 0.0D) {
                    return true;
                }

                break;
            case UP:

                if (axisalignedbb.maxY < 1.0D) {
                    return true;
                }

                break;
            case NORTH:

                if (axisalignedbb.minZ > 0.0D) {
                    return true;
                }

                break;
            case SOUTH:

                if (axisalignedbb.maxZ < 1.0D) {
                    return true;
                }

                break;
            case WEST:

                if (axisalignedbb.minX > 0.0D) {
                    return true;
                }

                break;
            case EAST:

                if (axisalignedbb.maxX < 1.0D) {
                    return true;
                }
        }

        IBlockState sideBlockState = blockAccess.getBlockState(pos.offset(side));

        Material material = sideBlockState.getMaterial();

        // Glass and other transparent materials force this side to be transparent.
        if (!material.isOpaque() && material != Material.AIR) {
            return false;
        }

        return !sideBlockState.doesSideBlockRendering(blockAccess, pos.offset(side), side.getOpposite());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return true;
    }

    public String getBareName() {
        return this.bareName;
    }

    public String[] getVariants() {
        return this.variants;
    }

    @SideOnly(Side.CLIENT)
    public ItemMeshDefinition getCustomMeshDefinition() {
        return null;
    }

    public IProperty[] getIgnoredProperties() {
        return new IProperty[0];
    }

    public IProperty getVariantProp() {
        return null;
    }

    public Class getVariantEnum() {
        return null;
    }

    private static TextFormatting getFromColor(EnumDyeColor color) {
        switch (color) {
            case ORANGE:
                return TextFormatting.GOLD;
            case MAGENTA:
                return TextFormatting.LIGHT_PURPLE;
            case LIGHT_BLUE:
                return TextFormatting.BLUE;
            case YELLOW:
                return TextFormatting.YELLOW;
            case LIME:
                return TextFormatting.GREEN;
            case PINK:
                return TextFormatting.LIGHT_PURPLE;
            case GRAY:
                return TextFormatting.DARK_GRAY;
            case SILVER:
                return TextFormatting.GRAY;
            case CYAN:
                return TextFormatting.DARK_AQUA;
            case PURPLE:
                return TextFormatting.DARK_PURPLE;
            case BLUE:
                return TextFormatting.DARK_BLUE;
            case BROWN:
                return TextFormatting.GOLD;
            case GREEN:
                return TextFormatting.DARK_GREEN;
            case RED:
                return TextFormatting.DARK_RED;
            case BLACK:
                return TextFormatting.BLACK;
            default:
                return TextFormatting.WHITE;
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        if (!GuiScreen.isShiftKeyDown()) {
            tooltip.add("Hold " + TextFormatting.BOLD + getFromColor(color) + "SHIFT " + TextFormatting.GRAY + "for more information");
        } else {
            String colorName = color.getName().replace("_", " ");
            colorName = WordUtils.capitalize(colorName);
            tooltip.add("Color: " + TextFormatting.BOLD.toString() + getFromColor(color).toString() + colorName);
        }
    }

    @Override
    public IBlockColor getBlockColor() {
        return (state, worldIn, pos, tintIndex) -> EnumDyeColor.values()[tintIndex].getColorValue();
    }

    @Override
    public IItemColor getItemColor() {
        return (stack, tintIndex) -> EnumDyeColor.values()[tintIndex].getColorValue();
    }

}
