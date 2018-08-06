package net.hdt.neutronia.blocks;

import net.hdt.huskylib2.block.BlockMod;
import net.hdt.neutronia.base.blocks.INeutroniaBlock;
import net.hdt.neutronia.init.NBlocks;
import net.hdt.neutronia.init.NCreativeTabs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPurpurChiseled extends BlockMod implements INeutroniaBlock {

    private boolean filled;

    public BlockPurpurChiseled(String name, boolean filled) {
        super(name, Material.ROCK);
        this.filled = filled;
        setCreativeTab(filled ? null : NCreativeTabs.END_EXPANSION_TAB);
        setHardness(1.5F);
        setResistance(10.0F);
        setSoundType(SoundType.STONE);
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = playerIn.getHeldItem(hand);

        if (itemstack.isEmpty()) {
            return false;
        } else {
            Item item = itemstack.getItem();

            if (item == Items.ENDER_PEARL) {
                if (!filled) {
                    worldIn.setBlockState(pos, NBlocks.chiseledPurpurFilled.getDefaultState(), 2);
                    itemstack.shrink(1);
                    return true;
                }
                return false;
            } else {
                if (filled) {
                    worldIn.setBlockState(pos, NBlocks.chiseledPurpur.getDefaultState(), 2);
                    playerIn.inventory.addItemStackToInventory(new ItemStack(Items.ENDER_PEARL));
                    return true;
                }
                return false;
            }
        }
    }
}
