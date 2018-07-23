package net.hdt.neutronia.blocks;

import net.hdt.huskylib2.block.BlockMod;
import net.hdt.neutronia.base.blocks.INeutroniaBlock;
import net.hdt.neutronia.init.NBlocks;
import net.hdt.neutronia.init.NCreativeTabs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBrickChiseled extends BlockMod implements INeutroniaBlock {

    private boolean filled;

    public BlockBrickChiseled(String name, boolean filled) {
        super(name, Material.ROCK);
        this.filled = filled;
        setCreativeTab(filled ? null : NCreativeTabs.OVERWORLD_EXPANSION_TAB);
        setHardness(0.3F);
        setResistance(6.5F);
        setSoundType(SoundType.STONE);
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = playerIn.getHeldItem(hand);

        if (itemstack.isEmpty()) {
            return false;
        }
        else {
            Item item = itemstack.getItem();

            if (item == Item.getItemFromBlock(Blocks.STONE_SLAB)) {
                if (!filled) {
                    worldIn.setBlockState(pos, NBlocks.chiseledBricksFilled.getDefaultState(), 2);
                    itemstack.shrink(1);
                    return true;
                }
                return false;
            }
            else {
                if (filled) {
                    worldIn.setBlockState(pos, NBlocks.chiseledBricks.getDefaultState(), 2);
                    playerIn.inventory.addItemStackToInventory(new ItemStack(Blocks.STONE_SLAB));
                    return true;
                }
                return false;
            }
        }
    }
}
