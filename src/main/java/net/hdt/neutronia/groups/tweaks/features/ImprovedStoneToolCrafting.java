package net.hdt.neutronia.groups.tweaks.features;

import net.hdt.huskylib2.recipe.RecipeHandler;
import net.hdt.huskylib2.util.ProxyRegistry;
import net.hdt.neutronia.base.groups.Feature;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

public class ImprovedStoneToolCrafting extends Feature {

	String mat = "materialStoneTool";
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		
		OreDictionary.registerOre(mat, ProxyRegistry.newStack(Items.FLINT));
		OreDictionary.registerOre(mat, ProxyRegistry.newStack(Blocks.STONE));
		OreDictionary.registerOre(mat, ProxyRegistry.newStack(Blocks.STONE, 1, 1));
		OreDictionary.registerOre(mat, ProxyRegistry.newStack(Blocks.STONE, 1, 3));
		OreDictionary.registerOre(mat, ProxyRegistry.newStack(Blocks.STONE, 1, 5));
		OreDictionary.registerOre(mat, ProxyRegistry.newStack(Blocks.COBBLESTONE));
		
		String[][] patterns = new String[][] {{"XXX", " # ", " # "}, {"X", "#", "#"}, {"XX", "X#", " #"}, {"XX", " #", " #"}, {"X", "X", "#"}};
		Item[] items = new Item[] { Items.STONE_PICKAXE, Items.STONE_SHOVEL, Items.STONE_AXE, Items.STONE_HOE, Items.STONE_SWORD };

		for(int i = 0; i < patterns.length; i++)
			RecipeHandler.addOreDictRecipe(ProxyRegistry.newStack(items[i]),
					patterns[i][0], patterns[i][1], patterns[i][2],
					'X', mat,
					'#', ProxyRegistry.newStack(Items.STICK));
	}
	
	@Override
	public void init(FMLInitializationEvent event) {

	}

	@Override
	public boolean requiresMinecraftRestartToEnable() {
		return true;
	}
	
	@Override
	public String getFeatureIngameConfigName() {
		return "Better Stone Tool Crafting";
	}
	
}
