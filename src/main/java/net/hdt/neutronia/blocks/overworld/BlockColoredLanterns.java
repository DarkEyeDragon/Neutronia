package net.hdt.neutronia.blocks.overworld;

import net.hdt.neutronia.Main;
import net.hdt.neutronia.blocks.base.BlockColoredAlt;
import net.hdt.neutronia.colored_lighting.ColoredLights;
import net.hdt.neutronia.colored_lighting.IColoredLightSource;
import net.hdt.neutronia.init.NBlocks;
import net.hdt.neutronia.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockColoredLanterns extends BlockColoredAlt implements IColoredLightSource {

    public static EnumDyeColor color = null;
    private final boolean isOn;

    public BlockColoredLanterns(EnumDyeColor color, boolean isOn) {
        super(Reference.MOD_ID, isOn ? "lit_lantern" : "lantern", color);
        BlockColoredLanterns.color = color;
        this.isOn = isOn;
        setCreativeTab(!isOn ? Main.OVERWORLD_EXPANSION_TAB : null);
        this.setLightLevel(isOn ? 1.0F: 0.0F);
        this.setTickRandomly(isOn);
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.updateState(worldIn, pos, state);
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        this.updateState(worldIn, pos, state);
    }

    public void updateState(World worldIn, BlockPos pos, IBlockState state) {
        if(!worldIn.isRemote) {
            if(this.isOn && !worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, NBlocks.coloredLanterns[color.getMetadata()].getDefaultState(), 2);
            } else if(!this.isOn && worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, NBlocks.coloredLitLanterns[color.getMetadata()].getDefaultState(), 2);
            }
        }
    }

    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!worldIn.isRemote)
        {
            if (this.isOn && !worldIn.isBlockPowered(pos))
            {
                worldIn.scheduleUpdate(pos, this, 4);
            }
            else if (!this.isOn && worldIn.isBlockPowered(pos))
            {
                worldIn.setBlockState(pos, NBlocks.coloredLitLanterns[color.getMetadata()].getDefaultState(), 2);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if(isOn) {
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = (double) pos.getY() + 0.7D;
            double d2 = (double) pos.getZ() + 0.5D;

            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(NBlocks.coloredLanterns[color.getMetadata()]);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(NBlocks.coloredLanterns[color.getMetadata()]);
    }

    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
        return new ItemStack(NBlocks.coloredLanterns[color.getMetadata()]);
    }

    @Override
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
        if(isOn) {
            ColoredLights.addLightSource(world, pos, state);
            return super.getLightOpacity(state, world, pos);
        } else
            return super.getLightOpacity(state, world, pos);
    }

    @Override
    public float[] getColoredLight(IBlockAccess world, BlockPos pos) {
        if(isOn) {
            return VANILLA_SPECTRUM_COLORS[color.getMetadata()];
        } else
            return null;
    }

}
