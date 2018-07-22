package net.hdt.neutronia.modules.decoration.features;

import net.hdt.huskylib2.blocks.BlockMod;
import net.hdt.neutronia.base.recipe.RecipeHandler;
import net.hdt.huskylib2.utils.ProxyRegistry;
import net.hdt.neutronia.base.module.Feature;
import net.hdt.neutronia.base.recipe.BlacklistOreIngredient;
import net.hdt.neutronia.modules.decoration.blocks.BlockCustomBookshelf;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class VariedBookshelves extends Feature {

	public static BlockMod custom_bookshelf;

	boolean renameVanillaBookshelves;
	
	@Override
	public void setupConfig() {
		renameVanillaBookshelves = loadPropBool("Rename vanilla bookshelves to Oak Bookshelf", "", true);
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		if(renameVanillaBookshelves)
			Blocks.BOOKSHELF.setTranslationKey("oak_bookshelf");
		
		custom_bookshelf = new BlockCustomBookshelf();

		List<ResourceLocation> recipeList = new ArrayList<>(CraftingManager.REGISTRY.getKeys());
		for(ResourceLocation res : recipeList) {
			IRecipe recipe = CraftingManager.REGISTRY.getObject(res);
			ItemStack out = recipe.getRecipeOutput();
			if(recipe instanceof ShapedRecipes && !out.isEmpty() && (out.getItem() == Item.getItemFromBlock(Blocks.BOOKSHELF))) {
				ShapedRecipes shaped = (ShapedRecipes) recipe;
				NonNullList<Ingredient> ingredients = shaped.recipeItems;
				for(int i = 0; i < ingredients.size(); i++) {
					Ingredient ingr = ingredients.get(i);
					if(ingr.apply(ProxyRegistry.newStack(Blocks.PLANKS)))
						ingredients.set(i, Ingredient.fromStacks(ProxyRegistry.newStack(Blocks.PLANKS, 1, 0)));
				}
			}
		}
		
		for(int i = 0; i < 5; i++)
			RecipeHandler.addOreDictRecipe(ProxyRegistry.newStack(custom_bookshelf, 1, i),
					"WWW", "BBB", "WWW",
					'W', ProxyRegistry.newStack(Blocks.PLANKS, 1, i + 1),
					'B', ProxyRegistry.newStack(Items.BOOK));
		
		Ingredient wood = new BlacklistOreIngredient("plankWood", (stack) -> stack.getItem() == Item.getItemFromBlock(Blocks.PLANKS));
		RecipeHandler.addOreDictRecipe(ProxyRegistry.newStack(Blocks.BOOKSHELF),
				"WWW", "BBB", "WWW",
				'W', wood,
				'B', ProxyRegistry.newStack(Items.BOOK));
	}

	@Override
	public void init(FMLInitializationEvent event) {
		OreDictionary.registerOre("bookshelf", Blocks.BOOKSHELF);
		OreDictionary.registerOre("bookshelf", ProxyRegistry.newStack(custom_bookshelf, 1, OreDictionary.WILDCARD_VALUE));
		
		OreDictionary.registerOre("bookshelfOak", Blocks.BOOKSHELF);
		OreDictionary.registerOre("bookshelfSpruce", ProxyRegistry.newStack(custom_bookshelf, 1, 0));
		OreDictionary.registerOre("bookshelfBirch", ProxyRegistry.newStack(custom_bookshelf, 1, 1));
		OreDictionary.registerOre("bookshelfJungle", ProxyRegistry.newStack(custom_bookshelf, 1, 2));
		OreDictionary.registerOre("bookshelfAcacia", ProxyRegistry.newStack(custom_bookshelf, 1, 3));
		OreDictionary.registerOre("bookshelfDarkOak", ProxyRegistry.newStack(custom_bookshelf, 1, 4));
	}
	
	@Override
	public boolean requiresMinecraftRestartToEnable() {
		return true;
	}
	
}