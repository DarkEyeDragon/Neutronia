package net.hdt.neutronia.properties;

import net.minecraft.util.IStringSerializable;

public enum EnumSoulStoneTypes implements IStringSerializable {

    HAUNTED_SANDSTONE(0, "soulstone_normal"),
    CHISELED_HAUNTED_SANDSTONE(1, "chiseled_soulstone"),
    SMOOTH_HAUNTED_SANDSTONE(2, "smooth_soulstone"),
    HAUNTED_SANDSTONE_BRICKS(3, "soulstone_bricks");

    private static final EnumSoulStoneTypes[] META_LOOKUP = new EnumSoulStoneTypes[values().length];

    static {
        for (EnumSoulStoneTypes blockstone$enumtype : values()) {
            META_LOOKUP[blockstone$enumtype.getMetadata()] = blockstone$enumtype;
        }
    }

    private final int meta;
    private final String name;

    EnumSoulStoneTypes(int p_i46384_3_, String p_i46384_5_) {
        this.meta = p_i46384_3_;
        this.name = p_i46384_5_;
    }

    public static EnumSoulStoneTypes byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) {
            meta = 0;
        }

        return META_LOOKUP[meta];
    }

    public int getMetadata() {
        return this.meta;
    }

    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

}