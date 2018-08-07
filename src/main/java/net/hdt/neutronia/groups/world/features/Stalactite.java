package net.hdt.neutronia.groups.world.features;

import net.hdt.neutronia.base.groups.Component;
import net.hdt.neutronia.base.groups.GroupLoader;
import net.hdt.neutronia.base.handler.DimensionConfig;
import net.hdt.neutronia.groups.world.blocks.BlockStalactite;
import net.hdt.neutronia.groups.world.world.StalactiteGenerator;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Stalactite extends Component {

    public static Block stone_stalactite, granite_stalactite, diorite_stalactite,
            andesite_stalactite, basalt_stalactite, marble_stalactite, limestone_stalactite,
            netherrack_stalactite;

    public static int tries, clusterCount, netherTries, netherClusterCount;
    public static DimensionConfig dimensionConfig;

    @Override
    public void setupConfig() {
        tries = loadPropInt("Cluster Attempts Per Chunk", "", 60);
        clusterCount = loadPropInt("Stalactite Per Cluster", "", 12);
        netherTries = loadPropInt("Cluster Attempts Per Chunk (Nether)", "", 4);
        netherClusterCount = loadPropInt("Stalactite Per Cluster (Nether)", "", 12);

        dimensionConfig = new DimensionConfig(configCategory, "0,-1");
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        stone_stalactite = new BlockStalactite("stone");
        granite_stalactite = new BlockStalactite("granite");
        diorite_stalactite = new BlockStalactite("diorite");
        andesite_stalactite = new BlockStalactite("andesite");
        netherrack_stalactite = new BlockStalactite("netherrack").setNetherrack();

        if (GroupLoader.isFeatureEnabled(Basalt.class))
            basalt_stalactite = new BlockStalactite("basalt");

        if (GroupLoader.isFeatureEnabled(RevampStoneGen.class)) {
            if (RevampStoneGen.enableMarble)
                marble_stalactite = new BlockStalactite("marble");

            if (RevampStoneGen.enableLimestone)
                limestone_stalactite = new BlockStalactite("limestone");
        }

        GameRegistry.registerWorldGenerator(new StalactiteGenerator(), 1000);
    }

}
