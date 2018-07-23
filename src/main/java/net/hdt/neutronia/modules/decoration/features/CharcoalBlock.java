package net.hdt.neutronia.modules.decoration.features;

import net.hdt.huskylib2.recipe.RecipeHandler;
import net.hdt.huskylib2.util.ProxyRegistry;
import net.hdt.neutronia.base.module.Feature;
import net.hdt.neutronia.modules.decoration.blocks.BlockCharcoal;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class CharcoalBlock extends Feature {

	public static Block charcoal_block;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		charcoal_block = new BlockCharcoal();

		RecipeHandler.addOreDictRecipe(ProxyRegistry.newStack(charcoal_block),
				"CCC", "CCC", "CCC",
				'C', ProxyRegistry.newStack(Items.COAL, 1, 1));
		RecipeHandler.addShapelessOreDictRecipe(ProxyRegistry.newStack(Items.COAL, 9, 1), ProxyRegistry.newStack(charcoal_block));

		GameRegistry.registerFuelHandler((stack) -> !stack.isEmpty() && stack.getItem() == Item.getItemFromBlock(charcoal_block) ? 16000 : 0);
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		OreDictionary.registerOre("blockCharcoal", charcoal_block);
	}

	@Override
	public boolean requiresMinecraftRestartToEnable() {
		return true;
	}
	
	@Override
	public String[] getIncompatibleMods() {
		return new String[] { "actuallyadditions", "mekanism" };
	}
	
}