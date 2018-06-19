package net.hdt.neutronia.dying_system_rewamp;

import net.hdt.huskylib2.items.ItemMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemDye extends ItemMod {

    public ItemDye(EnumDyeColor color) {
        super(String.format("%s_dye", color.getName()), "rds");
        this.setCreativeTab(RevampedColoringSystemMod.ITEMS);
    }

    public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target) {
        if (worldIn instanceof net.minecraft.world.WorldServer)
            return applyBonemeal(stack, worldIn, target, net.minecraftforge.common.util.FakePlayerFactory.getMinecraft((net.minecraft.world.WorldServer) worldIn), null);
        return false;
    }

    public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target, EntityPlayer player, @Nullable EnumHand hand) {
        IBlockState iblockstate = worldIn.getBlockState(target);

        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, worldIn, target, iblockstate, stack, hand);
        if (hook != 0) return hook > 0;

        if (iblockstate.getBlock() instanceof IGrowable) {
            IGrowable igrowable = (IGrowable) iblockstate.getBlock();

            if (igrowable.canGrow(worldIn, target, iblockstate, worldIn.isRemote)) {
                if (!worldIn.isRemote) {
                    if (igrowable.canUseBonemeal(worldIn, worldIn.rand, target, iblockstate)) {
                        igrowable.grow(worldIn, worldIn.rand, target, iblockstate);
                    }

                    stack.shrink(1);
                }

                return true;
            }
        }

        return false;
    }

    @SideOnly(Side.CLIENT)
    public static void spawnBonemealParticles(World worldIn, BlockPos pos, int amount) {
        if (amount == 0) {
            amount = 15;
        }

        IBlockState iblockstate = worldIn.getBlockState(pos);

        if (iblockstate.getMaterial() != Material.AIR) {
            for (int i = 0; i < amount; ++i) {
                double d0 = itemRand.nextGaussian() * 0.02D;
                double d1 = itemRand.nextGaussian() * 0.02D;
                double d2 = itemRand.nextGaussian() * 0.02D;
                worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (double) ((float) pos.getX() + itemRand.nextFloat()), (double) pos.getY() + (double) itemRand.nextFloat() * iblockstate.getBoundingBox(worldIn, pos).maxY, (double) ((float) pos.getZ() + itemRand.nextFloat()), d0, d1, d2);
            }
        } else {
            for (int i1 = 0; i1 < amount; ++i1) {
                double d0 = itemRand.nextGaussian() * 0.02D;
                double d1 = itemRand.nextGaussian() * 0.02D;
                double d2 = itemRand.nextGaussian() * 0.02D;
                worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (double) ((float) pos.getX() + itemRand.nextFloat()), (double) pos.getY() + (double) itemRand.nextFloat() * 1.0f, (double) ((float) pos.getZ() + itemRand.nextFloat()), d0, d1, d2, new int[0]);
            }
        }
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
            return EnumActionResult.FAIL;
        } else {
            EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(itemstack.getMetadata());

            if (enumdyecolor == EnumDyeColor.WHITE) {
                if (applyBonemeal(itemstack, worldIn, pos, player, hand)) {
                    if (!worldIn.isRemote) worldIn.playEvent(2005, pos, 0);
                    return EnumActionResult.SUCCESS;
                }
            } else if (enumdyecolor == EnumDyeColor.BROWN) {
                IBlockState iblockstate = worldIn.getBlockState(pos);
                Block block = iblockstate.getBlock();
                if (block == Blocks.LOG && iblockstate.getValue(BlockOldLog.VARIANT) == BlockPlanks.EnumType.JUNGLE) {
                    if (facing == EnumFacing.DOWN || facing == EnumFacing.UP) return EnumActionResult.FAIL;
                    pos = pos.offset(facing);
                    if (worldIn.isAirBlock(pos)) {
                        IBlockState iblockstate1 = Blocks.COCOA.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, 0, player, hand);
                        worldIn.setBlockState(pos, iblockstate1, 10);
                        if (!player.capabilities.isCreativeMode) itemstack.shrink(1);
                        return EnumActionResult.SUCCESS;
                    }
                }
                return EnumActionResult.FAIL;
            }

            for(EnumDyeColor color : EnumDyeColor.values()) {
                IBlockState iblockstate = worldIn.getBlockState(pos);
                Block block = iblockstate.getBlock();
                if(block == RevampedColoringSystemMod.testWool[0]) {
                    block = new BlockWool(color);
                    RevampedColoringSystemMod.testWool[0] = block;
                }
            }

            return EnumActionResult.PASS;
        }
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if (target instanceof EntitySheep) {
            EntitySheep entitysheep = (EntitySheep) target;
            EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());

            if (!entitysheep.getSheared() && entitysheep.getFleeceColor() != enumdyecolor) {
                entitysheep.setFleeceColor(enumdyecolor);
                stack.shrink(1);
            }

            return true;
        } else {
            return false;
        }
    }

}