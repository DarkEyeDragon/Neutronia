package net.hdt.neutronia.world.gen.feature;

import com.google.gson.JsonObject;
import net.hdt.neutronia.util.BlockUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Random;

public class EnhancedGeneratorOre extends EnhancedGenerator {
    public static final EnhancedGeneratorOre INSTANCE = new EnhancedGeneratorOre(0, 0.0F, 0, 0, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 0);
    private final IBlockState blockToSpawn;
    private final IBlockState blockToReplace;
    private final int size;

    private EnhancedGeneratorOre(int generationAttempts, float generationProbability, int minHeight, int maxHeight, IBlockState blockToSpawnIn, IBlockState blockToReplaceIn, int sizeIn) {
        super(generationAttempts, generationProbability, minHeight, maxHeight);

        blockToSpawn = blockToSpawnIn;
        blockToReplace = blockToReplaceIn;
        size = sizeIn;
    }

    @Override
    public EnhancedGeneratorOre deserializeConfig(JsonObject config) {
        int generationAttempts = JsonUtils.getInt(config, "generationAttempts", 10);
        float generationProbability = JsonUtils.getFloat(config, "generationProbability", 1.0F);
        int minHeight = JsonUtils.getInt(config, "minHeight", 32);
        int maxHeight = JsonUtils.getInt(config, "maxHeight", 128);

        IBlockState blockToSpawn = null;
        IBlockState blockToReplace = null;

        JsonObject blockToSpawnJson = JsonUtils.getJsonObject(config, "blockToSpawn", new JsonObject());
        JsonObject blockToReplaceJson = JsonUtils.getJsonObject(config, "blockToReplace", new JsonObject());

        if (blockToSpawnJson.entrySet().size() > 0) {
            ResourceLocation block = new ResourceLocation(JsonUtils.getString(blockToSpawnJson, "block"));

            if (ForgeRegistries.BLOCKS.containsKey(block)) {
                blockToSpawn = ForgeRegistries.BLOCKS.getValue(block).getDefaultState();
            }
        }
        if (blockToReplaceJson.entrySet().size() > 0) {
            ResourceLocation block = new ResourceLocation(JsonUtils.getString(blockToReplaceJson, "block"));

            if (ForgeRegistries.BLOCKS.containsKey(block)) {
                blockToReplace = ForgeRegistries.BLOCKS.getValue(block).getDefaultState();
            }
        }

        JsonObject blockToSpawnProperties = JsonUtils.getJsonObject(blockToSpawnJson, "properties", new JsonObject());
        JsonObject blockToReplaceProperties = JsonUtils.getJsonObject(blockToReplaceJson, "properties", new JsonObject());

        if (blockToSpawnProperties.entrySet().size() > 0) {
            blockToSpawn = BlockUtil.getBlockWithProperties(blockToSpawn, JsonUtils.getJsonObject(blockToSpawnJson, "properties"));
        }
        if (blockToReplaceProperties.entrySet().size() > 0) {
            blockToReplace = BlockUtil.getBlockWithProperties(blockToReplace, JsonUtils.getJsonObject(blockToReplaceJson, "properties"));
        }

        int size = JsonUtils.getInt(config, "size", 16);

        if (blockToSpawn != null && blockToReplace != null) {
            return new EnhancedGeneratorOre(generationAttempts, generationProbability, minHeight, maxHeight, blockToSpawn, blockToReplace, size);
        }

        return null;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        float f = rand.nextFloat() * (float) Math.PI;
        double d0 = (double) ((float) pos.getX() + MathHelper.sin(f) * (float) size / 8.0F);
        double d1 = (double) ((float) pos.getX() - MathHelper.sin(f) * (float) size / 8.0F);
        double d2 = (double) ((float) pos.getZ() + MathHelper.cos(f) * (float) size / 8.0F);
        double d3 = (double) ((float) pos.getZ() - MathHelper.cos(f) * (float) size / 8.0F);
        double d4 = (double) (pos.getY() + rand.nextInt(3) - 2);
        double d5 = (double) (pos.getY() + rand.nextInt(3) - 2);

        for (int i = 0; i < size; ++i) {
            float f1 = (float) i / (float) size;
            double d6 = d0 + (d1 - d0) * (double) f1;
            double d7 = d4 + (d5 - d4) * (double) f1;
            double d8 = d2 + (d3 - d2) * (double) f1;
            double d9 = rand.nextDouble() * (double) size / 16.0D;
            double d10 = (double) (MathHelper.sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0D;
            double d11 = (double) (MathHelper.sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0D;
            int j = MathHelper.floor(d6 - d10 / 2.0D);
            int k = MathHelper.floor(d7 - d11 / 2.0D);
            int l = MathHelper.floor(d8 - d10 / 2.0D);
            int i1 = MathHelper.floor(d6 + d10 / 2.0D);
            int j1 = MathHelper.floor(d7 + d11 / 2.0D);
            int k1 = MathHelper.floor(d8 + d10 / 2.0D);

            for (int l1 = j; l1 <= i1; ++l1) {
                double d12 = ((double) l1 + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D) {
                    for (int i2 = k; i2 <= j1; ++i2) {
                        double d13 = ((double) i2 + 0.5D - d7) / (d11 / 2.0D);

                        if (d12 * d12 + d13 * d13 < 1.0D) {
                            for (int j2 = l; j2 <= k1; ++j2) {
                                double d14 = ((double) j2 + 0.5D - d8) / (d10 / 2.0D);

                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {
                                    BlockPos newPos = new BlockPos(l1, i2, j2);
                                    IBlockState state = world.getBlockState(newPos);

                                    if (state.getBlock().isReplaceableOreGen(state, world, newPos, BlockMatcher.forBlock(blockToReplace.getBlock())) && state == blockToReplace) {
                                        world.setBlockState(newPos, blockToSpawn, 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}