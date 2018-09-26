package net.hdt.neutronia.world.gen;

import com.google.common.collect.Lists;
import net.hdt.neutronia.world.gen.generators.WorldGenStructure;
import net.hdt.neutronia.world.utils.WorldGenUtils;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * TODO: Add Mini Castle, More Village Stuff, Spider Nests, Endermite Nests, Guardian Ruins, Mesa Temple, Mesa Village, Desert Labyrinth, Actual Pyramids,
 **/
public class WorldGenCustomStructures implements IWorldGenerator {

    private static final WorldGenStructure CORALS = new WorldGenStructure(Lists.newArrayList("ocean_structures/coral_1", "ocean_structures/coral_2", "ocean_structures/coral_3", "ocean_structures/coral_4", "ocean_structures/coral_5", "ocean_structures/coral_blue", "ocean_structures/coral_pink", "ocean_structures/coral_purple", "ocean_structures/coral_yellow", "ocean_structures/coral_red"));
    private static final WorldGenStructure MISC_STRUCTURES = new WorldGenStructure(Lists.newArrayList("random_building"));

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()) {
            case 0:
//                generateCoral(world, random, chunkX, chunkZ);
//                generateStructure(MISC_STRUCTURES, world, random, chunkX, chunkZ, 40, Blocks.GRASS, Biomes.PLAINS);
                break;
        }
    }

    private void generateStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int spawnChance, Block spawnBlock, Biome... classes) {
        Set<Biome> biomeSet = new HashSet<>();
        Collections.addAll(biomeSet, classes);
        int x = (chunkX * 16) + random.nextInt(16);
        int z = (chunkZ * 16) + random.nextInt(16);
        int y = WorldGenUtils.getGroundFromAbove(world, x, z, spawnBlock) + 1;
        BlockPos pos = new BlockPos(x, y, z);
        Biome biome = world.getBiome(pos);
        if (world.getWorldInfo().isMapFeaturesEnabled() && biomeSet.contains(biome))
            if (random.nextInt(spawnChance) == 0) generator.generate(world, random, pos);
    }

    private void generateUndergroundStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, int structureHeight, Block topBlock, Biome... classes) {
        Set<Biome> biomeSet = new HashSet<>();
        Collections.addAll(biomeSet, classes);

        int x = (chunkX * 16) + random.nextInt(15);
        int z = (chunkZ * 16) + random.nextInt(15);
        int y = WorldGenUtils.calculateGenerationHeight(world, x, z, topBlock);
        BlockPos pos = new BlockPos(x, y, z);

        Biome biome = world.getBiome(pos);

        if (world.getWorldType() != WorldType.FLAT) {
            if (!biomeSet.contains(biome)) {
                return;
            }
            if (random.nextInt(chance) == 0) {
                if (y + structureHeight < world.getHeight()) {
                    generator.generate(world, random, pos);
                }
            }
        }
    }

    /*private void generateCoral(World world, Random random, int chunkX, int chunkZ) {
        int x = (chunkX * 16) + random.nextInt(16);
        int z = (chunkZ * 16) + random.nextInt(16);
        int y = WorldGenUtils.calculateGenerationHeight(world, x, z, Blocks.GRAVEL);
        BlockPos pos = new BlockPos(x, y, z);
        Biome biome = world.getBiome(pos);

        if (world.getWorldType() != WorldType.FLAT && biome == NBiomes.DEEP_WARM_OCEAN || biome == NBiomes.WARM_OCEAN) {
            if (random.nextInt(10) == 0) {
                if (y + 19 < world.getSeaLevel()) {
                    ((WorldGenerator) WorldGenCustomStructures.CORALS).generate(world, random, pos);
                }
            }
        }
    }*/

}