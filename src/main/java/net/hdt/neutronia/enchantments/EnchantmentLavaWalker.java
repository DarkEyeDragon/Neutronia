package net.hdt.neutronia.enchantments;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EnchantmentLavaWalker extends Enchantment
{
    public EnchantmentLavaWalker(Enchantment.Rarity rarityIn, EntityEquipmentSlot... slots)
    {
        super(rarityIn, EnumEnchantmentType.ARMOR_FEET, slots);
        this.setName("lavaWalker");
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int enchantmentLevel)
    {
        return enchantmentLevel * 10;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }

    public boolean isTreasureEnchantment()
    {
        return true;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 2;
    }

    public static void freezeNearby(EntityLivingBase living, World worldIn, BlockPos pos, int level)
    {
        if (living.onGround)
        {
            float f = (float)Math.min(16, 2 + level);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);

            for (BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.getAllInBoxMutable(pos.add((double)(-f), -1.0D, (double)(-f)), pos.add((double)f, -1.0D, (double)f)))
            {
                if (blockpos$mutableblockpos1.distanceSqToCenter(living.posX, living.posY, living.posZ) <= (double)(f * f))
                {
                    blockpos$mutableblockpos.setPos(blockpos$mutableblockpos1.getX(), blockpos$mutableblockpos1.getY() + 1, blockpos$mutableblockpos1.getZ());
                    IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos);

                    if (iblockstate.getMaterial() == Material.AIR)
                    {
                        IBlockState iblockstate1 = worldIn.getBlockState(blockpos$mutableblockpos1);

                        if (iblockstate1.getMaterial() == Material.LAVA && (iblockstate1.getBlock() == Blocks.LAVA || iblockstate1.getBlock() == Blocks.FLOWING_LAVA) && iblockstate1.getValue(BlockLiquid.LEVEL) == 0 && worldIn.mayPlace(Blocks.MAGMA, blockpos$mutableblockpos1, false, EnumFacing.DOWN, null))
                        {
                            worldIn.setBlockState(blockpos$mutableblockpos1, Blocks.MAGMA.getDefaultState());
                            worldIn.scheduleUpdate(blockpos$mutableblockpos1.toImmutable(), Blocks.MAGMA, MathHelper.getInt(living.getRNG(), 60, 120));
                        }
                    }
                }
            }
        }
    }

    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    public boolean canApplyTogether(Enchantment ench)
    {
        return super.canApplyTogether(ench) && ench != Enchantments.DEPTH_STRIDER && ench != Enchantments.FROST_WALKER;
    }
}