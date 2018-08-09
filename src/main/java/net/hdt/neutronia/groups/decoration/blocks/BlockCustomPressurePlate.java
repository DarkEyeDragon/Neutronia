package net.hdt.neutronia.groups.decoration.blocks;

import net.hdt.neutronia.base.blocks.BlockNeutroniaPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class BlockCustomPressurePlate extends BlockNeutroniaPressurePlate {

	public BlockCustomPressurePlate(String variant) {
		super(variant + "_pressure_plate", Material.WOOD, Sensitivity.EVERYTHING);
		setHardness(0.5F);
		setSoundType(SoundType.WOOD);
	}

	@Override
	protected List<Entity> getValidEntities(World world, AxisAlignedBB aabb) {
		return world.getEntitiesWithinAABBExcludingEntity(null, aabb);
	}

}
