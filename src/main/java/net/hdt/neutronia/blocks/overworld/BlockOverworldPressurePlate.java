package net.hdt.neutronia.blocks.overworld;

import net.hdt.neutronia.blocks.base.BlockBasePressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class BlockOverworldPressurePlate extends BlockBasePressurePlate {

    public static final PropertyBool POWERED = PropertyBool.create("powered");
    private final BlockOverworldPressurePlate.Sensitivity sensitivity;

    public BlockOverworldPressurePlate(Material materialIn, String name, BlockOverworldPressurePlate.Sensitivity sensitivityIn) {
        super(materialIn, name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(POWERED, Boolean.FALSE));
        this.sensitivity = sensitivityIn;
    }

    protected int getRedstoneStrength(IBlockState state) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    protected IBlockState setRedstoneStrength(IBlockState state, int strength) {
        return state.withProperty(POWERED, strength > 0);
    }

    protected void playClickOnSound(World worldIn, BlockPos color) {
        if (this.material == Material.WOOD) {
            worldIn.playSound(null, color, SoundEvents.BLOCK_WOOD_PRESSPLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.8F);
        } else {
            worldIn.playSound(null, color, SoundEvents.BLOCK_STONE_PRESSPLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
        }
    }

    protected void playClickOffSound(World worldIn, BlockPos pos) {
        if (this.material == Material.WOOD) {
            worldIn.playSound(null, pos, SoundEvents.BLOCK_WOOD_PRESSPLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.7F);
        } else {
            worldIn.playSound(null, pos, SoundEvents.BLOCK_STONE_PRESSPLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.5F);
        }
    }

    protected int computeRedstoneStrength(World worldIn, BlockPos pos) {
        AxisAlignedBB axisalignedbb = PRESSURE_AABB.offset(pos);
        List<? extends Entity> list;

        switch (this.sensitivity) {
            case EVERYTHING:
                list = worldIn.getEntitiesWithinAABBExcludingEntity(null, axisalignedbb);
                break;
            case MOBS:
                list = worldIn.<Entity>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
                break;
            default:
                return 0;
        }

        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (!entity.doesEntityNotTriggerPressurePlate()) {
                    return 15;
                }
            }
        }

        return 0;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(POWERED, meta == 1);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state) {
        return state.getValue(POWERED) ? 1 : 0;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, POWERED);
    }

    public enum Sensitivity {
        EVERYTHING,
        MOBS
    }

}