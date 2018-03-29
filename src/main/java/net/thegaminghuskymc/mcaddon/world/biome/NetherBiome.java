package net.thegaminghuskymc.mcaddon.world.biome;

import java.util.List;

public class NetherBiome {
    private String biomeId;
    private int weight;
    private String climateType;
    private BiomeBlock topBlock;
    private BiomeBlock fillerBlock;
    private BiomeBlock oceanBlock;
    private List<BiomeEntity> entitySpawnList;
    private List<BiomeFeature> featureList;

    public String getId() {
        return biomeId;
    }

    public int getWeight() {
        return weight;
    }

    public String getClimateType() {
        return climateType;
    }

    public BiomeBlock getTopBlock() {
        return topBlock;
    }

    public BiomeBlock getFillerBlock() {
        return fillerBlock;
    }

    public BiomeBlock getOceanBlock() {
        return oceanBlock;
    }

    public List<BiomeEntity> getEntitySpawnList() {
        return entitySpawnList;
    }

    public List<BiomeFeature> getFeatureList() {
        return featureList;
    }

    public static class BiomeList {
        private String name;
        private List<Mod> mods;

        public String getName() {
            return name;
        }

        public List<Mod> getMods() {
            return mods;
        }
    }

    public static class Mod {
        private String modId;
        private List<NetherBiome> biomes;

        public String getId() {
            return modId;
        }

        public List<NetherBiome> getBiomes() {
            return biomes;
        }
    }

    public static class BiomeBlock {
        private String blockId;
        private int meta;

        public BiomeBlock() {
            blockId = "";
            meta = 0;
        }

        public String getId() {
            return blockId;
        }

        public int getMeta() {
            return meta;
        }
    }

    public static class BiomeEntity {
        private String entityId;
        private String creatureType;
        private int weight;
        private int minGroupCount;
        private int maxGroupCount;

        public String getId() {
            return entityId;
        }

        public String getCreatureType() {
            return creatureType;
        }

        public int getWeight() {
            return weight;
        }

        public int getMinGroupCount() {
            return minGroupCount;
        }

        public int getMaxGroupCount() {
            return maxGroupCount;
        }
    }

    public static class BiomeFeature {
        private String featureType;
        private BiomeBlock blockToSpawn;
        private BiomeBlock targetBlock;
        private BiomeBlock surroundingBlock;
        private int minHeight;
        private int maxHeight;
        private int size;
        private int rarity;
        private boolean randomRarity;
        private boolean superRare;
        private List<BiomeStructure> structureList;
        private boolean hidden;

        public String getFeatureType() {
            return featureType;
        }

        public BiomeBlock getBlockToSpawn() {
            return blockToSpawn;
        }

        public BiomeBlock getTargetBlock() {
            return targetBlock;
        }

        public BiomeBlock getSurroundingBlock() {
            return surroundingBlock;
        }

        public int getMinHeight() {
            return minHeight;
        }

        public int getMaxHeight() {
            return maxHeight;
        }

        public int getSize() {
            return size;
        }

        public int getRarity() {
            return rarity;
        }

        public boolean useRandomRarity() {
            return randomRarity;
        }

        public boolean isSuperRare() {
            return superRare;
        }

        public List<BiomeStructure> getStructureList() {
            return structureList;
        }

        public boolean isHidden() {
            return hidden;
        }
    }

    public static class BiomeStructure {
        private String structureType;
        private String structureId;
        private String replacedBlock;
        private List<String> lootTables;
        private List<String> spawnerMobs;
        private boolean rotate;
        private boolean mirror;
        private int weight;

        public String getStructureType() {
            return structureType;
        }

        public String getStructureId() {
            return structureId;
        }

        public String getReplacedBlock() {
            return replacedBlock;
        }

        public List<String> getLootTables() {
            return lootTables;
        }

        public List<String> getSpawnerMobs() {
            return spawnerMobs;
        }

        public boolean rotate() {
            return rotate;
        }

        public boolean mirror() {
            return mirror;
        }

        public int getWeight() {
            return weight;
        }
    }
}