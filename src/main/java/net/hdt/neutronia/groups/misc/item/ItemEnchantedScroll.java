/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Quark Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Quark
 *
 * Quark is Open Source and distributed under the
 * CC-BY-NC-SA 3.0 License: https://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB
 *
 * File Created @ [01/06/2016, 19:43:18 (GMT)]
 */
package net.hdt.neutronia.groups.misc.item;

import net.hdt.huskylib2.item.ItemMod;
import net.hdt.neutronia.base.items.INeutroniaItem;
import net.hdt.neutronia.groups.misc.feature.EnchantedScrolls;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemEnchantedScroll extends ItemMod implements INeutroniaItem {

	public ItemEnchantedScroll() {
		super("enchanted_scroll");
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.MISC);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		if(isInCreativeTab(tab))
			for(Enchantment e : EnchantedScrolls.validEnchants) {
				ItemStack stack = new ItemStack(this);
				stack.addEnchantment(e, e.getMaxLevel());
				subItems.add(stack);
			}
	}

}
