package net.hdt.neutronia.world.gen.feature;

import com.google.gson.JsonObject;
import net.hdt.neutronia.util.BlockUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Random;

public class EnhancedGeneratorCluster extends EnhancedGenerator {
    public static final EnhancedGeneratorCluster INSTANCE = new EnhancedGeneratorCluster(0, 0.0F, 0, 0, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState());
    private IBlockState blockToSpawn;
    private IBlockState blockToHangFrom;

    private EnhancedGeneratorCluster(int generationAttempts, float generationProbability, int minHeight, int maxHeight, IBlockState blockToSpawnIn, IBlockState blockToHangFromIn) {
        super(generationAttempts, generationProbability, minHeight, maxHeight);

        blockToSpawn = blockToSpawnIn;
        blockToHangFrom = blockToHangFromIn;
    }

    @Override
    public EnhancedGeneratorCluster deserializeConfig(JsonObject config) {
        int generationAttempts = JsonUtils.getInt(config, "generationAttempts", 10);
        float generationProbability = JsonUtils.getFloat(config, "generationProbability", 1.0F);
        int minHeight = JsonUtils.getInt(config, "minHeight", 32);
        int maxHeight = JsonUtils.getInt(config, "maxHeight", 128);

        IBlockState blockToSpawn = null;
        IBlockState blockToHangFrom = null;

        JsonObject blockToSpawnJson = JsonUtils.getJsonObject(config, "blockToSpawn", new JsonObject());
        JsonObject blockToHangFromJson = JsonUtils.getJsonObject(config, "blockToHangFrom", new JsonObject());

        if (blockToSpawnJson.entrySet().size() > 0) {
            ResourceLocation block = new ResourceLocation(JsonUtils.getString(blockToSpawnJson, "block"));

            if (ForgeRegistries.BLOCKS.containsKey(block)) {
                blockToSpawn = ForgeRegistries.BLOCKS.getValue(block).getDefaultState();
            }
        }
        if (blockToHangFromJson.entrySet().size() > 0) {
            ResourceLocation block = new ResourceLocation(JsonUtils.getString(blockToHangFromJson, "block"));

            if (ForgeRegistries.BLOCKS.containsKey(block)) {
                blockToHangFrom = ForgeRegistries.BLOCKS.getValue(block).getDefaultState();
            }
        }

        JsonObject blockToSpawnProperties = JsonUtils.getJsonObject(blockToSpawnJson, "properties", new JsonObject());
        JsonObject blockToHangFromProperties = JsonUtils.getJsonObject(blockToHangFromJson, "properties", new JsonObject());

        if (blockToSpawnProperties.entrySet().size() > 0) {
            blockToSpawn = BlockUtil.getBlockWithProperties(blockToSpawn, JsonUtils.getJsonObject(blockToSpawnJson, "properties"));
        }
        if (blockToHangFromProperties.entrySet().size() > 0) {
            blockToHangFrom = BlockUtil.getBlockWithProperties(blockToHangFrom, JsonUtils.getJsonObject(blockToHangFromJson, "properties"));
        }

        if (blockToSpawn != null && blockToHangFrom != null) {
            return new EnhancedGeneratorCluster(generationAttempts, generationProbability, minHeight, maxHeight, blockToSpawn, blockToHangFrom);
        }

        return null;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        if (!world.isAirBlock(pos)) {
            return false;
        } else if (world.getBlockState(pos.up()) != blockToHangFrom) {
            return false;
        } else {
            world.setBlockState(pos, blockToSpawn, 3);

            for (int i = 0; i < 1500; ++i) {
                BlockPos newPos = pos.add(rand.nextInt(8) - rand.nextInt(8), -rand.nextInt(12), rand.nextInt(8) - rand.nextInt(8));

                if (world.isAirBlock(newPos)) {
                    int j = 0;

                    for (EnumFacing facing : EnumFacing.values()) {
                        if (world.getBlockState(newPos.offset(facing)).getBlock() == blockToSpawn.getBlock()) {
                            ++j;
                        }

                        if (j > 1) {
                            break;
                        }
                    }

                    if (j == 1) {
                        world.setBlockState(newPos, blockToSpawn, 3);
                    }
                }
            }

            return true;
        }
    }
}