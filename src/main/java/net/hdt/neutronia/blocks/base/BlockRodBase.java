package net.hdt.neutronia.blocks.base;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRodBase extends BlockDirectional {

    protected static final AxisAlignedBB BB_AXIS_Y = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
    protected static final AxisAlignedBB BB_AXIS_Z = new AxisAlignedBB(0.375D, 0.375D, 0.0D, 0.625D, 0.625D, 1.0D);
    protected static final AxisAlignedBB BB_AXIS_X = new AxisAlignedBB(0.0D, 0.375D, 0.375D, 1.0D, 0.625D, 0.625D);

    private Material mapColor = Material.SAND;

    public BlockRodBase(String name, CreativeTabs creativeTabs, boolean emitsLight) {
        super(Material.CIRCUITS, name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
        this.setCreativeTab(creativeTabs);
        if(emitsLight) {
            this.setHardness(0.3F);
            this.setLightLevel(1.0F);
        }
    }

    @SuppressWarnings("unused")
    public BlockRodBase(Material color, String name, CreativeTabs creativeTabs, boolean emitsLight) {
        super(Material.CIRCUITS, name);
        mapColor = color;
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
        this.setCreativeTab(creativeTabs);
        if(emitsLight) {
            this.setHardness(0.3F);
            this.setLightLevel(1.0F);
        }
    }

    @Override
    public Material getMaterial(IBlockState state) {
        return this.mapColor;
    }

    @Override
    public IBlockState withRotation(IBlockState blockState, Rotation rotation) {
        return blockState.withProperty(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState blockState, Mirror mirror) {
        return blockState.withProperty(FACING, mirror.mirror(blockState.getValue(FACING)));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos) {
        switch (blockState.getValue(FACING).getAxis()) {
            case X:
            default:
                return BB_AXIS_X;
            case Z:
                return BB_AXIS_Z;
            case Y:
                return BB_AXIS_Y;
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return true;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState iblockstate = world.getBlockState(pos.offset(facing.getOpposite()));

        if (iblockstate.getBlock() == this) {
            EnumFacing enumfacing = iblockstate.getValue(FACING);

            if (enumfacing == facing) {
                return this.getDefaultState().withProperty(FACING, facing.getOpposite());
            }
        }

        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public void onBlockAdded(World world, BlockPos blockPos, IBlockState state) {
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = this.getDefaultState();
        state = state.withProperty(FACING, EnumFacing.getFront(meta));
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public EnumPushReaction getMobilityFlag(IBlockState blockState) {
        return EnumPushReaction.NORMAL;
    }
}