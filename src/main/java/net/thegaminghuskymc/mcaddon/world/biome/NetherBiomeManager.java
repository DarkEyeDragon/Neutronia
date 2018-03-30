package net.thegaminghuskymc.mcaddon.world.biome;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.thegaminghuskymc.mcaddon.Main;
import net.thegaminghuskymc.mcaddon.util.BlockUtil;
import net.thegaminghuskymc.mcaddon.util.FileUtil;
import net.thegaminghuskymc.mcaddon.world.gen.feature.*;
import net.thegaminghuskymc.mcaddon.world.gen.layer.GenLayerNether;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class NetherBiomeManager {
    private static final Logger LOGGER = LogManager.getLogger("Husky's Minecraft Additions | NetherBiomeManager");

    public static void postInit(File directory) {
        try {
            if (!directory.exists()) {
                directory.mkdirs();
            }

            LOGGER.info("Copying the Biome List Directory to the config folder.");

            if (Main.isInDevEnv) {
                FileUtils.copyDirectory(new File(Main.class.getResource("/assets/hmca/biome_lists").getFile()), directory);
            } else {
                FileUtil.extractFromJar("/assets/hmca/biome_lists", directory.getPath());
            }
        } catch (IOException e) {
            LOGGER.fatal("The attempt to copy the Biome List Directory to the config folder was unsuccessful.");
            LOGGER.fatal(e);
        }

        Gson gson = new Gson();
        List<File> netherBiomeFiles = Lists.newArrayList(directory.listFiles());

        try {
            for (File netherBiomeFile : netherBiomeFiles) {
                String jsonText = Files.toString(netherBiomeFile, Charsets.UTF_8);
                NetherBiome.BiomeList biomeList = gson.fromJson(jsonText, NetherBiome.BiomeList.class);

                LOGGER.info("Adding biomes from the " + biomeList.getName() + ".");

                for (NetherBiome.Mod biomeMod : biomeList.getMods()) {
                    for (NetherBiome netherBiome : biomeMod.getBiomes()) {
                        ResourceLocation biomeRegistryName = new ResourceLocation(biomeMod.getId(), netherBiome.getId());
                        Biome biome = ForgeRegistries.BIOMES.getValue(biomeRegistryName);

                        if (biome == null) {
                            continue;
                        }

                        NetherBiome.BiomeBlock biomeTopBlock = netherBiome.getTopBlock();
                        NetherBiome.BiomeBlock biomeFillerBlock = netherBiome.getFillerBlock();
                        NetherBiome.BiomeBlock biomeOceanBlock = netherBiome.getOceanBlock();
                        IBlockState topBlock = biome.topBlock;
                        IBlockState fillerBlock = biome.fillerBlock;
                        IBlockState oceanBlock = Blocks.LAVA.getDefaultState();

                        if (biomeTopBlock != null) {
                            IBlockState state = BlockUtil.getBlock(biomeTopBlock, "minecraft:air");
                            topBlock = state.getBlock() == Blocks.AIR ? topBlock : state;

                            LOGGER.info("Set the " + biome.getRegistryName().toString() + " biome's top Block to " + topBlock.getBlock().getRegistryName().toString() + " with a meta of " + topBlock.getBlock().getMetaFromState(topBlock) + ".");
                        }
                        if (biomeFillerBlock != null) {
                            IBlockState state = BlockUtil.getBlock(biomeFillerBlock, "minecraft:air");
                            fillerBlock = state.getBlock() == Blocks.AIR ? fillerBlock : state;

                            LOGGER.info("Set the " + biome.getRegistryName().toString() + " biome's filler Block to " + fillerBlock.getBlock().getRegistryName().toString() + " with a meta of " + fillerBlock.getBlock().getMetaFromState(fillerBlock) + ".");
                        }
                        if (biomeOceanBlock != null) {
                            IBlockState state = BlockUtil.getBlock(biomeOceanBlock, "minecraft:air");
                            oceanBlock = state.getBlock() == Blocks.AIR ? oceanBlock : state;

                            LOGGER.info("Set the " + biome.getRegistryName().toString() + " biome's ocean Block to " + oceanBlock.getBlock().getRegistryName().toString() + " with a meta of " + oceanBlock.getBlock().getMetaFromState(oceanBlock) + ".");
                        }

                        Map<EnumCreatureType, List<Biome.SpawnListEntry>> entitySpawnList = Maps.newHashMap();

                        for (EnumCreatureType creatureType : EnumCreatureType.values()) {
                            entitySpawnList.put(creatureType, Lists.newArrayList());
                        }

                        if (netherBiome.getEntitySpawnList() != null) {
                            for (NetherBiome.BiomeEntity entity : netherBiome.getEntitySpawnList()) {
                                for (EnumCreatureType creatureType : EnumCreatureType.values()) {
                                    if (creatureType.toString().equalsIgnoreCase(entity.getCreatureType())) {
                                        Class<? extends Entity> cls = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(entity.getId())).getEntityClass();

                                        if (cls != null && EntityLiving.class.isAssignableFrom(cls)) {
                                            entitySpawnList.get(creatureType).add(new Biome.SpawnListEntry((Class<? extends EntityLiving>) cls, entity.getWeight(), entity.getMinGroupCount(), entity.getMaxGroupCount()));
                                            LOGGER.info("Added the " + entity.getId() + " Entity to the " + biome.getRegistryName().toString() + " biome.");
                                        }
                                    }
                                }
                            }
                        }

                        List<Feature> features = Lists.newArrayList();

                        if (netherBiome.getFeatureList() != null) {
                            for (NetherBiome.BiomeFeature biomeFeature : netherBiome.getFeatureList()) {
                                Feature.FeatureType featureType = Feature.FeatureType.getFromString(biomeFeature.getFeatureType());
                                Feature feature;

                                if (featureType == Feature.FeatureType.SCATTERED) {
                                    feature = new FeatureScattered(biome, biomeFeature);
                                } else if (featureType == Feature.FeatureType.CLUMPED) {
                                    feature = new FeatureClumped(biome, biomeFeature);
                                } else if (featureType == Feature.FeatureType.ORE) {
                                    feature = new FeatureOre(biome, biomeFeature);
                                } else if (featureType == Feature.FeatureType.FLUID) {
                                    feature = new FeatureFluid(biome, biomeFeature);
                                } else if (featureType == Feature.FeatureType.POOL) {
                                    feature = new FeaturePool(biome, biomeFeature);
                                } else if (featureType == Feature.FeatureType.STRUCTURE) {
                                    feature = new FeatureStructure(biome, biomeFeature);
                                } else {
                                    LOGGER.info("A biome feature with the type of " + biomeFeature.getFeatureType() + " is unknown.");
                                    continue;
                                }

                                features.add(feature);
                            }
                        }

                        NetherBiomeManager.NetherBiomeType.getFromString(netherBiome.getClimateType()).addBiome(biome, netherBiome.getWeight(), topBlock, fillerBlock, oceanBlock, entitySpawnList, features);
                        LOGGER.info("Added the " + biome.getRegistryName().toString() + " biome from the " + biomeList.getName() + " to the Nether.");
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.fatal("NetherEx was unable to read the Biome lists.");
            LOGGER.fatal(e);
        }
    }

    public static List<NetherBiomeEntry> getAllBiomeEntries() {
        List<NetherBiomeEntry> biomes = Lists.newArrayList();

        for (NetherBiomeType biomeType : NetherBiomeType.values()) {
            biomes.addAll(biomeType.getBiomeEntries());
        }

        return biomes;
    }

    public static Biome getRandomBiome(List<NetherBiomeEntry> biomeEntryList, Random rand) {
        return WeightedRandom.getRandomItem(biomeEntryList, rand.nextInt(WeightedRandom.getTotalWeight(biomeEntryList))).getBiome();
    }

    public static Biome getRandomBiome(List<NetherBiomeEntry> biomeEntryList, GenLayerNether layer) {
        return WeightedRandom.getRandomItem(biomeEntryList, layer.nextInt(WeightedRandom.getTotalWeight(biomeEntryList))).getBiome();
    }

    public static IBlockState getBiomeTopBlock(Biome biome) {
        Map<Biome, NetherBiomeEntry> biomeEntryMap = NetherBiomeType.getFromBiome(biome).getBiomeEntryMap();

        if (biomeEntryMap.containsKey(biome)) {
            return biomeEntryMap.get(biome).getTopBlock();
        }

        return biome.topBlock;
    }

    public static IBlockState getBiomeFillerBlock(Biome biome) {
        Map<Biome, NetherBiomeEntry> biomeEntryMap = NetherBiomeType.getFromBiome(biome).getBiomeEntryMap();

        if (biomeEntryMap.containsKey(biome)) {
            return biomeEntryMap.get(biome).getFillerBlock();
        }

        return biome.fillerBlock;
    }

    public static IBlockState getBiomeOceanBlock(Biome biome) {
        Map<Biome, NetherBiomeEntry> biomeEntryMap = NetherBiomeType.getFromBiome(biome).getBiomeEntryMap();

        if (biomeEntryMap.containsKey(biome)) {
            return biomeEntryMap.get(biome).getOceanBlock();
        }

        return Blocks.LAVA.getDefaultState();
    }

    public static Map<EnumCreatureType, List<Biome.SpawnListEntry>> getBiomeEntitySpawnList(Biome biome) {
        Map<Biome, NetherBiomeEntry> biomeEntryMap = NetherBiomeType.getFromBiome(biome).getBiomeEntryMap();

        if (biomeEntryMap.containsKey(biome)) {
            return biomeEntryMap.get(biome).getEntitySpawnList();
        }

        Map<EnumCreatureType, List<Biome.SpawnListEntry>> entitySpawnList = Maps.newHashMap();

        for (EnumCreatureType creatureType : EnumCreatureType.values()) {
            entitySpawnList.put(creatureType, Lists.newArrayList());
        }

        return entitySpawnList;
    }

    public static List<Feature> getBiomeFeatures(Biome biome) {
        Map<Biome, NetherBiomeEntry> biomeEntryMap = NetherBiomeType.getFromBiome(biome).getBiomeEntryMap();

        if (biomeEntryMap.containsKey(biome)) {
            return biomeEntryMap.get(biome).getFeatures();
        }

        return Lists.newArrayList();
    }

    public enum NetherBiomeType {
        HOT,
        WARM,
        TEMPERATE,
        COOL,
        COLD;

        private static final Map<Biome, NetherBiomeEntry> biomeEntryMap = Maps.newHashMap();

        public static NetherBiomeType getFromBiome(Biome biome) {
            for (NetherBiomeType biomeType : values()) {
                for (NetherBiomeEntry biomeEntry : biomeType.getBiomeEntries()) {
                    if (biomeEntry.biome == biome) {
                        return biomeType;
                    }
                }
            }

            return TEMPERATE;
        }

        public static NetherBiomeType getFromString(String string) {
            if (!Strings.isNullOrEmpty(string)) {
                for (NetherBiomeType biomeType : values()) {
                    if (biomeType.name().equalsIgnoreCase(string)) {
                        return biomeType;
                    }
                }
            }

            return TEMPERATE;
        }

        public void addBiome(Biome biome, int weight, IBlockState topBlock, IBlockState fillerBlock, IBlockState oceanBlock, Map<EnumCreatureType, List<Biome.SpawnListEntry>> entitySpawnList, List<Feature> features) {
            biomeEntryMap.put(biome, new NetherBiomeEntry(biome, weight, topBlock, fillerBlock, oceanBlock, entitySpawnList, features));
        }

        public Map<Biome, NetherBiomeEntry> getBiomeEntryMap() {
            return ImmutableMap.copyOf(biomeEntryMap);
        }

        public List<NetherBiomeEntry> getBiomeEntries() {
            return ImmutableList.copyOf(biomeEntryMap.values());
        }
    }

    public static class NetherBiomeEntry extends BiomeManager.BiomeEntry {
        private final IBlockState topBlock;
        private final IBlockState fillerBlock;
        private final IBlockState oceanBlock;
        private final Map<EnumCreatureType, List<Biome.SpawnListEntry>> entitySpawnList;
        private final List<Feature> features;

        public NetherBiomeEntry(Biome biome, int weight, IBlockState topBlockIn, IBlockState fillerBlockIn, IBlockState oceanBlockIn, Map<EnumCreatureType, List<Biome.SpawnListEntry>> entitySpawnListIn, List<Feature> featuresIn) {
            super(biome, weight <= 0 ? 10 : weight);

            topBlock = topBlockIn;
            fillerBlock = fillerBlockIn;
            oceanBlock = oceanBlockIn;
            entitySpawnList = entitySpawnListIn;
            features = featuresIn;
        }

        public Biome getBiome() {
            return biome;
        }

        public int getWeight() {
            return itemWeight;
        }

        public IBlockState getTopBlock() {
            return topBlock;
        }

        public IBlockState getFillerBlock() {
            return fillerBlock;
        }

        public IBlockState getOceanBlock() {
            return oceanBlock;
        }

        public Map<EnumCreatureType, List<Biome.SpawnListEntry>> getEntitySpawnList() {
            return entitySpawnList;
        }

        public List<Feature> getFeatures() {
            return features;
        }
    }
}