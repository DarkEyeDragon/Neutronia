package net.hdt.neutronia.groups.world.blocks.corals;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.hdt.neutronia.base.states.BlockStateProperties;
import net.hdt.neutronia.groups.world.blocks.BlockWaterPlantBase;
import net.hdt.neutronia.groups.world.features.Corals;
import net.hdt.neutronia.properties.EnumCoralColor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static net.minecraft.block.BlockLiquid.LEVEL;

/**
 * Created on 7/5/18 by alexiy.
 * This coral fan turns dead if no water blocks are adjacent to it
 */
public class BlockCoralFan extends BlockWaterPlantBase {

    private static final Map<EnumFacing, AxisAlignedBB> field_211885_c = Maps.newEnumMap(ImmutableMap.of(EnumFacing.NORTH, new AxisAlignedBB(0.0D, 4.0D, 5.0D, 16.0D, 12.0D, 16.0D), EnumFacing.SOUTH, new AxisAlignedBB(0.0D, 4.0D, 0.0D, 16.0D, 12.0D, 11.0D), EnumFacing.WEST, new AxisAlignedBB(5.0D, 4.0D, 0.0D, 16.0D, 12.0D, 16.0D), EnumFacing.EAST, new AxisAlignedBB(0.0D, 4.0D, 0.0D, 11.0D, 12.0D, 16.0D)));
    private static final PropertyEnum<EnumFacing> FACING = BlockHorizontal.FACING;
    private boolean dead;
    private ArrayList<Block> livingVersion, deadVersion;
    private EnumCoralColor color;
    public static final PropertyBool field_211882_a = BlockStateProperties.field_208198_y;

    public BlockCoralFan(EnumCoralColor colorIn, boolean isDead, ArrayList<Block> livingVersion, ArrayList<Block> deadVersion) {
        super(isDead ? "dead_" + colorIn.getNewName() + "_coral_fan" : colorIn.getNewName() + "_coral_fan");
        this.dead = isDead;
        this.color = colorIn;
        this.livingVersion = livingVersion;
        this.deadVersion = deadVersion;
        if (isDead) {
            deadVersion.add(this);
        } else {
            livingVersion.add(this);
        }
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(LEVEL, 15));
    }

    public BlockCoralFan(EnumCoralColor colorIn, String name, boolean isDead, ArrayList<Block> livingVersion, ArrayList<Block> deadVersion) {
        super(isDead ? "dead_" + colorIn.getNewName() + name : colorIn.getNewName() + name);
        this.dead = isDead;
        this.color = colorIn;
        this.livingVersion = livingVersion;
        this.deadVersion = deadVersion;
        if (isDead) {
            deadVersion.add(this);
        } else {
            livingVersion.add(this);
        }
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(LEVEL, 15));
    }

    @Override
    public IProperty[] getIgnoredProperties() {
        return new IProperty[]{LEVEL};
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (!dead && !canLive(worldIn, pos))
            worldIn.scheduleUpdate(pos, this, 100);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        worldIn.scheduleUpdate(pos, this, 100);
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == Blocks.WATER || state.getMaterial() == Material.ICE;
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        if (pos.getY() >= 0 && pos.getY() < 256) {
            IBlockState iblockstate = worldIn.getBlockState(pos.down());
            Material material = iblockstate.getMaterial();
            return material == Material.WATER && iblockstate.getValue(BlockLiquid.LEVEL) == 0 || material == Material.ICE;
        } else
            return false;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos.down());
        return super.canPlaceBlockAt(worldIn, pos) && state.getBlock() == Blocks.WATER;
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return field_211885_c.get(state.getValue(FACING));
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return field_211885_c.get(state.getValue(FACING));
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     * @deprecated call via {@link IBlockState#withRotation(Rotation)} whenever possible. Implementing/overriding is
     * fine.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     * @deprecated call via {@link IBlockState#withMirror(Mirror)} whenever possible. Implementing/overriding is fine.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @SideOnly(Side.CLIENT)
    public boolean hasCustomBreakingProgress(IBlockState state) {
        return true;
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
        if (this == Corals.coralFan[color.getMetadata()] || this == Corals.deadCoralFan[color.getMetadata()]) {
            return side != EnumFacing.DOWN && side != EnumFacing.UP;
        } else {
            return true;
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        return state.withProperty(FACING, placer.getHorizontalFacing());
    }

    /*public IBlockState func_196271_a(IBlockState p_196271_1_, EnumFacing p_196271_2_, IBlockState p_196271_3_, World p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_)
    {
        if (p_196271_1_.getValue(field_211882_a))
        {
            p_196271_4_.func_205219_F_().func_205360_a(p_196271_5_, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(p_196271_4_));
        }

        return p_196271_2_.getOpposite() == p_196271_1_.getValue(FACING) && !p_196271_1_.withProperty(p_196271_4_, p_196271_5_) ? Blocks.AIR.getDefaultState() : p_196271_1_;
    }*/

    @Override
    public boolean canBlockStay(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
        EnumFacing enumfacing = state.getValue(FACING);
        BlockPos blockpos = pos.offset(enumfacing.getOpposite());
        IBlockState iblockstate = worldIn.getBlockState(blockpos);
        return iblockstate.getBlockFaceShape(worldIn, blockpos, enumfacing) == BlockFaceShape.SOLID && !isExceptBlockForAttachWithPiston(iblockstate.getBlock());
    }

    public int getMetaFromState(IBlockState state) {
        return (state.getValue(FACING)).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, BlockLiquid.LEVEL);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!this.dead && !canLive(worldIn, pos))
            worldIn.setBlockState(pos, deadVersion.get(livingVersion.indexOf(this)).getDefaultState());
        if (this.dead && canLive(worldIn, pos))
            worldIn.setBlockState(pos, livingVersion.get(deadVersion.indexOf(this)).getDefaultState());
    }

    private boolean canLive(World world, BlockPos itsPosition) {
        for (EnumFacing facing : EnumFacing.values()) {
            IBlockState sidestate = world.getBlockState(itsPosition.offset(facing));
            if (sidestate.getBlock() == Blocks.WATER || sidestate.getBlock() == Blocks.FLOWING_WATER) {
                return true;
            }
        }
        return false;
    }

}