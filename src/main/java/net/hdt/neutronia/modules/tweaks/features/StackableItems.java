/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Quark Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Quark
 *
 * Quark is Open Source and distributed under the
 * CC-BY-NC-SA 3.0 License: https://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB
 *
 * File Created @ [18/03/2016, 23:01:43 (GMT)]
 */
package net.hdt.neutronia.modules.tweaks.features;

import com.google.common.collect.ImmutableSet;
import net.hdt.neutronia.base.module.Feature;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class StackableItems extends Feature {

	int potions, minecarts, writableBooks, records;

	@Override
	public void setupConfig() {
		potions = loadPropInt("Potions", "", 8);
		minecarts = loadPropInt("Minecarts", "", 16);
		writableBooks = loadPropInt("Writable Books", "", 16);
		records = loadPropInt("Records", "", 8);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		ImmutableSet.<Item>of(Items.POTIONITEM, Items.SPLASH_POTION, Items.LINGERING_POTION)
		.forEach(item -> item.setMaxStackSize(potions));

		ImmutableSet.<Item>of(Items.MINECART, Items.CHEST_MINECART, Items.COMMAND_BLOCK_MINECART, Items.FURNACE_MINECART, Items.HOPPER_MINECART, Items.TNT_MINECART)
		.forEach(item -> item.setMaxStackSize(minecarts));

		ImmutableSet.of(Items.RECORD_11, Items.RECORD_13, Items.RECORD_BLOCKS, Items.RECORD_CAT, Items.RECORD_CHIRP, Items.RECORD_FAR, Items.RECORD_MALL, Items.RECORD_MELLOHI,
				Items.RECORD_STAL, Items.RECORD_STRAD, Items.RECORD_WAIT, Items.RECORD_WARD)
				.forEach(item -> item.setMaxStackSize(records));

		ImmutableSet.of(Items.WRITABLE_BOOK)
				.forEach(item -> item.setMaxStackSize(writableBooks));
	}
	
	@Override
	public boolean requiresMinecraftRestartToEnable() {
		return true;
	}

}
