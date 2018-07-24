package net.hdt.neutronia.properties;

import net.minecraft.block.material.MapColor;
import net.minecraft.util.IStringSerializable;

public enum EnumVanillaWoodTypes implements IStringSerializable {
    SPRUCE(0, "spruce", MapColor.OBSIDIAN),
    BIRCH(1, "birch", MapColor.SAND),
    JUNGLE(2, "jungle", MapColor.DIRT),
    ACACIA(3, "acacia", MapColor.ADOBE),
    DARK_OAK(4, "dark_oak", "big_oak", MapColor.BROWN);

    private static final EnumVanillaWoodTypes[] META_LOOKUP = new EnumVanillaWoodTypes[values().length];

    static {
        for (EnumVanillaWoodTypes blockplanks$enumtype : values()) {
            META_LOOKUP[blockplanks$enumtype.getMetadata()] = blockplanks$enumtype;
        }
    }

    private final int meta;
    private final String name;
    private final String unlocalizedName;
    /**
     * The color that represents this entry on a map.
     */
    private final MapColor mapColor;

    EnumVanillaWoodTypes(int metaIn, String nameIn, MapColor mapColorIn) {
        this(metaIn, nameIn, nameIn, mapColorIn);
    }

    EnumVanillaWoodTypes(int metaIn, String nameIn, String unlocalizedNameIn, MapColor mapColorIn) {
        this.meta = metaIn;
        this.name = nameIn;
        this.unlocalizedName = unlocalizedNameIn;
        this.mapColor = mapColorIn;
    }

    public static EnumVanillaWoodTypes byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) {
            meta = 0;
        }

        return META_LOOKUP[meta];
    }

    public int getMetadata() {
        return this.meta;
    }

    /**
     * The color which represents this entry on a map.
     */
    public MapColor getMapColor() {
        return this.mapColor;
    }

    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public String getTranslationKey() {
        return this.unlocalizedName;
    }
}