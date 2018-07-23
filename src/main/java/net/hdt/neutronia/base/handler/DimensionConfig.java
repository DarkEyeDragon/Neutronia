package net.hdt.neutronia.base.handler;

import net.hdt.neutronia.base.module.ModuleLoader;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class DimensionConfig {

	private final boolean blacklist;
	private final List<Integer> dims;
	
	public DimensionConfig(String parent) {
		this(parent, false, "0");
	}
	
	public DimensionConfig(String parent, String dimStr) {
		this(parent, false, dimStr.split(","));
	}
	
	public DimensionConfig(String parent, boolean blacklist, String... defaultStrs) {
		String category = parent + ".dimensions";
		this.blacklist = ModuleLoader.config.getBoolean("Is Blacklist", category, blacklist, "");
		
		String[] dimStrs = ModuleLoader.config.getStringList("Dimensions", category, defaultStrs, "");
		dims = new ArrayList();
		for(String s : dimStrs)
			try {
				dims.add(Integer.parseInt(s));
			} catch(NumberFormatException e) {}
	}
	
	public boolean canSpawnHere(World world) {
		return dims.contains(world.provider.getDimension()) != blacklist;
	}
	
}
