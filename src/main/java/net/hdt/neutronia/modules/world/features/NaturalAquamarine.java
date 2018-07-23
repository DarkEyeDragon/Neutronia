package net.hdt.neutronia.modules.world.features;

import net.hdt.neutronia.base.module.Feature;
import net.hdt.neutronia.blocks.overworld.BlockOverworldBase;
import net.hdt.neutronia.properties.EnumNaturalAquamarineVariants;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static net.hdt.neutronia.init.NCreativeTabs.OCEAN_EXPANSION_TAB;

public class NaturalAquamarine extends Feature {

    private static final Block[] naturalAquamarine = new Block[13];

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        for (EnumNaturalAquamarineVariants naturalAquamarineVariants : EnumNaturalAquamarineVariants.values()) {
            naturalAquamarine[naturalAquamarineVariants.getID()] = new BlockOverworldBase(Material.ROCK, naturalAquamarineVariants.getName(), false).setCreativeTab(OCEAN_EXPANSION_TAB);
        }
    }

}