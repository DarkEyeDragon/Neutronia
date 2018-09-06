package net.hdt.neutronia.base.block;

import net.hdt.huskylib2.interf.IModBlock;
import net.hdt.huskylib2.util.ProxyRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockModDoor extends BlockDoor implements IModBlock {

    private final String[] variants;
    private final String bareName;

    public BlockModDoor(Material materialIn, String name, String... variants) {
        super(materialIn);

        if (variants.length == 0) {
            variants = new String[]{name};
        }

        this.bareName = name;
        this.variants = variants;
        if (this.registerInConstruction()) {
            this.setTranslationKey(name);
        }
    }

    public Block setTranslationKey(String name) {
        super.setTranslationKey(name);
        this.setRegistryName(this.getPrefix() + name);
        ProxyRegistry.register(this);
        ProxyRegistry.register(new ItemDoor(this).setRegistryName(new ResourceLocation(this.getPrefix() + name)).setTranslationKey(name));
        return this;
    }

    public boolean registerInConstruction() {
        return true;
    }

    public String getBareName() {
        return this.bareName;
    }

    public String[] getVariants() {
        return this.variants;
    }

    @SideOnly(Side.CLIENT)
    public ItemMeshDefinition getCustomMeshDefinition() {
        return null;
    }

    public EnumRarity getBlockRarity(ItemStack stack) {
        return EnumRarity.COMMON;
    }

    public IProperty[] getIgnoredProperties() {
        return new IProperty[0];
    }

    public IProperty getVariantProp() {
        return null;
    }

    public Class getVariantEnum() {
        return null;
    }

}