package net.hdt.neutronia.world.gen.feature;

import com.google.gson.JsonObject;
import net.hdt.neutronia.util.BlockUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Random;

public class EnhancedGeneratorFluid extends EnhancedGenerator {
    public static final EnhancedGeneratorFluid INSTANCE = new EnhancedGeneratorFluid(0, 0.0F, 0, 0, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
    private IBlockState blockToSpawn;
    private IBlockState blockToTarget;
    private boolean hidden;

    private EnhancedGeneratorFluid(int generationAttempts, float generationProbability, int minHeight, int maxHeight, IBlockState blockToSpawnIn, IBlockState blockToTargetIn, boolean hiddenIn) {
        super(generationAttempts, generationProbability, minHeight, maxHeight);

        blockToSpawn = blockToSpawnIn;
        blockToTarget = blockToTargetIn;
        hidden = hiddenIn;
    }

    @Override
    public EnhancedGeneratorFluid deserializeConfig(JsonObject config) {
        int generationAttempts = JsonUtils.getInt(config, "generationAttempts", 10);
        float generationProbability = JsonUtils.getFloat(config, "generationProbability", 1.0F);
        int minHeight = JsonUtils.getInt(config, "minHeight", 32);
        int maxHeight = JsonUtils.getInt(config, "maxHeight", 128);

        IBlockState blockToSpawn = null;
        IBlockState blockToTarget = null;

        JsonObject blockToSpawnJson = JsonUtils.getJsonObject(config, "blockToSpawn", new JsonObject());
        JsonObject blockToTargetJson = JsonUtils.getJsonObject(config, "blockToTarget", new JsonObject());

        if (blockToSpawnJson.entrySet().size() > 0) {
            ResourceLocation block = new ResourceLocation(JsonUtils.getString(blockToSpawnJson, "block"));

            if (ForgeRegistries.BLOCKS.containsKey(block)) {
                blockToSpawn = ForgeRegistries.BLOCKS.getValue(block).getDefaultState();
            }
        }
        if (blockToTargetJson.entrySet().size() > 0) {
            ResourceLocation block = new ResourceLocation(JsonUtils.getString(blockToTargetJson, "block"));

            if (ForgeRegistries.BLOCKS.containsKey(block)) {
                blockToTarget = ForgeRegistries.BLOCKS.getValue(block).getDefaultState();
            }
        }

        JsonObject blockToSpawnProperties = JsonUtils.getJsonObject(blockToSpawnJson, "properties", new JsonObject());
        JsonObject blockToTargetProperties = JsonUtils.getJsonObject(blockToTargetJson, "properties", new JsonObject());

        if (blockToSpawnProperties.entrySet().size() > 0) {
            blockToSpawn = BlockUtil.getBlockWithProperties(blockToSpawn, JsonUtils.getJsonObject(blockToSpawnJson, "properties"));
        }
        if (blockToTargetProperties.entrySet().size() > 0) {
            blockToTarget = BlockUtil.getBlockWithProperties(blockToTarget, JsonUtils.getJsonObject(blockToTargetJson, "properties"));
        }

        boolean hidden = JsonUtils.getBoolean(config, "hidden", false);

        if (blockToSpawn != null && blockToTarget != null) {
            return new EnhancedGeneratorFluid(generationAttempts, generationProbability, minHeight, maxHeight, blockToSpawn, blockToTarget, hidden);
        }

        return null;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        if (world.getBlockState(pos.up()) != blockToTarget) {
            return false;
        } else if (!world.isAirBlock(pos) && world.getBlockState(pos) != blockToTarget) {
            return false;
        } else {
            int i = 0;

            if (world.getBlockState(pos.west()) == blockToTarget) {
                ++i;
            }

            if (world.getBlockState(pos.east()) == blockToTarget) {
                ++i;
            }

            if (world.getBlockState(pos.north()) == blockToTarget) {
                ++i;
            }

            if (world.getBlockState(pos.south()) == blockToTarget) {
                ++i;
            }

            if (world.getBlockState(pos.down()) == blockToTarget) {
                ++i;
            }

            int j = 0;

            if (world.isAirBlock(pos.west())) {
                ++j;
            }

            if (world.isAirBlock(pos.east())) {
                ++j;
            }

            if (world.isAirBlock(pos.north())) {
                ++j;
            }

            if (world.isAirBlock(pos.south())) {
                ++j;
            }

            if (world.isAirBlock(pos.down())) {
                ++j;
            }

            if (!hidden && i == 4 && j == 1 || i == 5) {
                world.setBlockState(pos, blockToSpawn, 2);
                world.immediateBlockTick(pos, blockToSpawn, rand);
            }

            return true;
        }
    }
}