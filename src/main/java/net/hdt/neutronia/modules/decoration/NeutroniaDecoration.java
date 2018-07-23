package net.hdt.neutronia.modules.decoration;

import net.hdt.neutronia.base.module.Module;
import net.hdt.neutronia.modules.decoration.features.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class NeutroniaDecoration extends Module {

    @Override
    public void addFeatures() {
        registerFeature(new DecorativeCorals());
        registerFeature(new VariedTrapdoors());
        registerFeature(new MoreBanners());
        registerFeature(new NetherBrickFenceGate());
        registerFeature(new ColoredItemFrames());
        registerFeature(new CharcoalBlock());
        registerFeature(new VariedChests());
        registerFeature(new VariedBookshelves());
        registerFeature(new FlatItemFrames());
        registerFeature(new GlassItemFrame());
        registerFeature(new ColoredFlowerPots());
    }

    @Override
    public ItemStack getIconStack() {
        return new ItemStack(Blocks.RED_FLOWER);
    }

}