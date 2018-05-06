/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Quark Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Quark
 * <p>
 * Quark is Open Source and distributed under the
 * CC-BY-NC-SA 3.0 License: https://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB
 * <p>
 * File Created @ [06/06/2016, 23:08:40 (GMT)]
 */
package net.hdt.neutronia.modules.building.features;

import net.hdt.neutronia.modules.building.blocks.BlockNewSandstone;
import net.hdt.neutronia.modules.building.blocks.slab.BlockVanillaSlab;
import net.hdt.neutronia.modules.building.blocks.stairs.BlockVanillaStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.thegaminghuskymc.huskylib2.blocks.BlockMod;
import net.thegaminghuskymc.huskylib2.blocks.BlockModSlab;
import net.thegaminghuskymc.huskylib2.blocks.BlockModStairs;
import net.thegaminghuskymc.huskylib2.module.Feature;
import net.thegaminghuskymc.huskylib2.module.GlobalConfig;
import net.thegaminghuskymc.huskylib2.module.ModuleLoader;

public class MoreSandstone extends Feature {

    public static BlockMod sandstone_new;

    boolean enableStairsAndSlabs;

    @Override
    public void setupConfig() {
        enableStairsAndSlabs = loadPropBool("Enable stairs and slabs", "", true) && GlobalConfig.enableVariants;
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        sandstone_new = new BlockNewSandstone();
		
		/*RecipeHandler.addOreDictRecipe(ProxyRegistry.newStack(sandstone_new, 8, 0),
				"SSS", "S S", "SSS",
				'S', ProxyRegistry.newStack(Blocks.SANDSTONE));
		RecipeHandler.addOreDictRecipe(ProxyRegistry.newStack(sandstone_new, 4, 1),
				"SS", "SS",
				'S', ProxyRegistry.newStack(sandstone_new, 1, 0));
		RecipeHandler.addOreDictRecipe(ProxyRegistry.newStack(sandstone_new, 8, 2),
				"SSS", "S S", "SSS",
				'S', ProxyRegistry.newStack(Blocks.RED_SANDSTONE));
		RecipeHandler.addOreDictRecipe(ProxyRegistry.newStack(sandstone_new, 4, 3),
				"SS", "SS",
				'S', ProxyRegistry.newStack(sandstone_new, 1, 2));*/

        if (enableStairsAndSlabs) {
            boolean soulSandstone = ModuleLoader.isFeatureEnabled(SoulSandstone.class);

            for (BlockNewSandstone.Variants variant : BlockNewSandstone.Variants.class.getEnumConstants()) {
                if (!variant.stairs)
                    continue;
                if (variant.ordinal() > 3 && !soulSandstone)
                    break;

                IBlockState state = sandstone_new.getDefaultState().withProperty(sandstone_new.getVariantProp(), variant);
                String name = variant.getName() + "_stairs";
                BlockModStairs.initStairs(sandstone_new, variant.ordinal(), new BlockVanillaStairs(name, state));
            }

            for (BlockNewSandstone.Variants variant : BlockNewSandstone.Variants.class.getEnumConstants()) {
                if (!variant.slabs)
                    continue;
                if (variant.ordinal() > 3 && !soulSandstone)
                    break;

                IBlockState state = sandstone_new.getDefaultState().withProperty(sandstone_new.getVariantProp(), variant);
                String name = variant.getName() + "_slab";
                BlockModSlab.initSlab(sandstone_new, variant.ordinal(), new BlockVanillaSlab(name, state, false), new BlockVanillaSlab(name, state, true));
            }
        }
    }

    @Override
    public void postPreInit(FMLPreInitializationEvent event) {
		/*if(ModuleLoader.isFeatureEnabled(SoulSandstone.class)) {
			RecipeHandler.addOreDictRecipe(ProxyRegistry.newStack(sandstone_new, 8, 4),
					"SSS", "S S", "SSS",
					'S', ProxyRegistry.newStack(SoulSandstone.soul_sandstone));
			RecipeHandler.addOreDictRecipe(ProxyRegistry.newStack(sandstone_new, 4, 5),
					"SS", "SS",
					'S', ProxyRegistry.newStack(sandstone_new, 1, 4));
		}*/
    }

    @Override
    public boolean requiresMinecraftRestartToEnable() {
        return true;
    }

}
