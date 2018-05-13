/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Quark Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Quark
 * <p>
 * Quark is Open Source and distributed under the
 * CC-BY-NC-SA 3.0 License: https://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB
 * <p>
 * File Created @ [19/04/2016, 16:59:04 (GMT)]
 */
package net.hdt.neutronia.modules.building.blocks.wall;

import net.hdt.neutronia.blocks.base.BlockModWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.Random;

public class BlockSnowBricksWall extends BlockModWall {

    public BlockSnowBricksWall(String name, IBlockState state) {
        super(name, state);
    }

    @Override
    public boolean isToolEffective(String type, IBlockState state) {
        return type.equals("shovel");
    }

    @Override
    public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return true;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

}
