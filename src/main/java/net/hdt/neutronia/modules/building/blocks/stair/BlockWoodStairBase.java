package net.hdt.neutronia.modules.building.blocks.stair;

import net.hdt.huskylib2.block.BlockModStairs;
import net.hdt.neutronia.base.blocks.INeutroniaBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockWoodStairBase extends BlockModStairs implements INeutroniaBlock {

    public BlockWoodStairBase(String name, IBlockState state, CreativeTabs tab) {
        super(name + "_stair", state);
        setCreativeTab(tab);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    /**
     * Check if the face of a block should block rendering.
     * <p>
     * Faces which are fully opaque should return true, faces with transparency
     * or faces which do not span the full size of the block should return false.
     *
     * @param state The current block state
     * @param world The current world
     * @param pos   Block position in world
     * @param face  The side to check
     * @return True if the block is opaque on the specified side.
     */
    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        BlockPos offSetPos = pos.offset(face);
        IBlockState offSetState = world.getBlockState(offSetPos);
        Material offSetMaterial = offSetState.getMaterial();
        return !offSetMaterial.isOpaque();
    }

}