package net.hdt.neutronia.groups.world.features;

import net.hdt.neutronia.base.groups.Component;
import net.hdt.neutronia.base.groups.GroupLoader;
import net.hdt.neutronia.base.handler.server.DimensionConfig;
import net.hdt.neutronia.groups.world.blocks.BlockStalagmite;
import net.hdt.neutronia.groups.world.world.StalagmiteGenerator;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Stalagmite extends Component {

    public static Block stone_stalagmite, granite_stalagmite, diorite_stalagmite,
            andesite_stalagmite, basalt_stalagmite, marble_stalagmite, limestone_stalagmite,
            netherrack_stalagmite;

    public static int tries, clusterCount, netherTries, netherClusterCount;
    public static DimensionConfig dimensionConfig;

    @Override
    public void setupConfig() {
        tries = loadPropInt("Cluster Attempts Per Chunk", "", 60);
        clusterCount = loadPropInt("Stalagmite Per Cluster", "", 12);
        netherTries = loadPropInt("Cluster Attempts Per Chunk (Nether)", "", 4);
        netherClusterCount = loadPropInt("Stalagmite Per Cluster (Nether)", "", 12);

        dimensionConfig = new DimensionConfig(configCategory, "0,-1");
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        stone_stalagmite = new BlockStalagmite("stone");
        granite_stalagmite = new BlockStalagmite("granite");
        diorite_stalagmite = new BlockStalagmite("diorite");
        andesite_stalagmite = new BlockStalagmite("andesite");
        netherrack_stalagmite = new BlockStalagmite("netherrack").setNetherrack();

        if (GroupLoader.isFeatureEnabled(Basalt.class))
            basalt_stalagmite = new BlockStalagmite("basalt");

        if (GroupLoader.isFeatureEnabled(RevampStoneGen.class)) {
            if (RevampStoneGen.enableMarble)
                marble_stalagmite = new BlockStalagmite("marble");

            if (RevampStoneGen.enableLimestone)
                limestone_stalagmite = new BlockStalagmite("limestone");
        }

        GameRegistry.registerWorldGenerator(new StalagmiteGenerator(), 1000);
    }

}
