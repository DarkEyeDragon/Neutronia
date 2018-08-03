package net.hdt.neutronia.groups.building.features;

import net.hdt.neutronia.base.blocks.BlockNeutroniaWall;
import net.hdt.neutronia.base.groups.Feature;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class VanillaWalls extends Feature {

	boolean stone, granite, diorite, andesite, sandstone, chiseledSandstone, smoothSandstone, redSandstone, chiseledRedSandstone, smoothRedSandstone, stoneBricks, bricks, quartz, prismarine, prismarineBricks, darkPrismarine, purpurBlock, endBricks;

	@Override
	public void setupConfig() {
		stone = loadPropBool("Stone", "", true);
		granite = loadPropBool("Granite", "", true);
		diorite = loadPropBool("Diorite", "", true);
		andesite = loadPropBool("Andesite", "", true);
		sandstone = loadPropBool("Sandstone", "", true);
		redSandstone = loadPropBool("Red Sandstone", "", true);
		stoneBricks = loadPropBool("Stone Bricks", "", true);
		bricks = loadPropBool("Bricks", "", true);
		quartz = loadPropBool("Quartz", "", true);
		prismarine = loadPropBool("Prismarine", "", true);
		prismarineBricks = loadPropBool("Prismarine Bricks", "", true);
		darkPrismarine = loadPropBool("Dark Prismarine", "", true);
		purpurBlock = loadPropBool("Purpur", "", true);
		endBricks = loadPropBool("End Bricks", "", true);
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		add("stone", Blocks.STONE, 0, stone);
		add("stone_granite", Blocks.STONE, 1, granite);
		add("stone_diorite", Blocks.STONE, 3, diorite);
		add("stone_andesite", Blocks.STONE, 5, andesite);
		add("sandstone", Blocks.SANDSTONE, 0, sandstone);
        add("chiseled_sandstone", Blocks.SANDSTONE, 1, chiseledSandstone);
        add("smooth_sandstone", Blocks.SANDSTONE, 2, smoothSandstone);
		add("red_sandstone", Blocks.RED_SANDSTONE, 0, redSandstone);
		add("chiseled_red_sandstone", Blocks.RED_SANDSTONE, 1, chiseledRedSandstone);
		add("smooth_red_sandstone", Blocks.RED_SANDSTONE, 2, smoothRedSandstone);
		add("stonebrick", Blocks.STONEBRICK, 0, stoneBricks);
		add("brick", Blocks.BRICK_BLOCK, 0, bricks);
		add("quartz", Blocks.QUARTZ_BLOCK, 0, quartz);
		add("prismarine_rough", Blocks.PRISMARINE, 0, prismarine);
		add("prismarine_bricks", Blocks.PRISMARINE, 1, prismarineBricks);
		add("dark_prismarine", Blocks.PRISMARINE, 2, darkPrismarine);
		add("purpur_block", Blocks.PURPUR_BLOCK, 0, purpurBlock);
		add("end_bricks", Blocks.END_BRICKS, 0, endBricks);
	}

	public static void add(String name, Block block, int meta, boolean doit) {
		add(name, block, meta, doit, BlockNeutroniaWall::new);
	}

	public static void add(String name, Block block, int meta, boolean doit, WallSupplier supplier) {
		if(!doit)
			return;

		IBlockState state = block.getStateFromMeta(meta);
		String wallName = name + "_wall";
		BlockNeutroniaWall.initWall(block, meta, supplier.supply(wallName, state));
	}
	
	@Override
	public boolean requiresMinecraftRestartToEnable() {
		return true;
	}

	public interface WallSupplier {
		BlockNeutroniaWall supply(String wallName, IBlockState state);
	}
}
