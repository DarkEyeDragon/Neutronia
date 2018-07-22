package net.hdt.neutronia.base.tags;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;

public class BlockTags
{
    private static TagCollection<Block> field_199899_c = new TagCollection<>((p_203641_0_) -> false, (p_203640_0_) -> null, "", false, "");
    private static int field_199900_d;
    public static final Tag<Block> field_199897_a = func_199894_a("wool");
    public static final Tag<Block> field_199898_b = func_199894_a("planks");
    public static final Tag<Block> field_200026_c = func_199894_a("stone_bricks");
    public static final Tag<Block> field_200151_d = func_199894_a("wooden_buttons");
    public static final Tag<Block> field_200027_d = func_199894_a("buttons");
    public static final Tag<Block> field_200028_e = func_199894_a("carpets");
    public static final Tag<Block> field_200152_g = func_199894_a("wooden_doors");
    public static final Tag<Block> field_202894_h = func_199894_a("wooden_stairs");
    public static final Tag<Block> field_202895_i = func_199894_a("wooden_slabs");
    public static final Tag<Block> field_202896_j = func_199894_a("wooden_pressure_plates");
    public static final Tag<Block> field_212186_k = func_199894_a("wooden_trapdoors");
    public static final Tag<Block> field_200029_f = func_199894_a("doors");
    public static final Tag<Block> field_200030_g = func_199894_a("saplings");
    public static final Tag<Block> field_200031_h = func_199894_a("logs");
    public static final Tag<Block> field_203285_n = func_199894_a("dark_oak_logs");
    public static final Tag<Block> field_203286_o = func_199894_a("oak_logs");
    public static final Tag<Block> field_203287_p = func_199894_a("birch_logs");
    public static final Tag<Block> field_203288_q = func_199894_a("acacia_logs");
    public static final Tag<Block> field_203289_r = func_199894_a("jungle_logs");
    public static final Tag<Block> field_203290_s = func_199894_a("spruce_logs");
    public static final Tag<Block> field_202897_p = func_199894_a("banners");
    public static final Tag<Block> field_203436_u = func_199894_a("sand");
    public static final Tag<Block> field_203291_w = func_199894_a("stairs");
    public static final Tag<Block> field_203292_x = func_199894_a("slabs");
    public static final Tag<Block> field_200572_k = func_199894_a("anvil");
    public static final Tag<Block> field_203437_y = func_199894_a("rails");
    public static final Tag<Block> field_205598_B = func_199894_a("coral_blocks");
    public static final Tag<Block> field_204116_z = func_199894_a("corals");
    public static final Tag<Block> field_211922_B = func_199894_a("wall_corals");
    public static final Tag<Block> field_206952_E = func_199894_a("leaves");
    public static final Tag<Block> field_212185_E = func_199894_a("trapdoors");
    public static final Tag<Block> field_200032_i = func_199894_a("flower_pots");
    public static final Tag<Block> field_201151_l = func_199894_a("enderman_holdable");
    public static final Tag<Block> field_205213_E = func_199894_a("ice");
    public static final Tag<Block> field_205599_H = func_199894_a("valid_spawn");
    public static final Tag<Block> field_211923_H = func_199894_a("impermeable");

    public static void func_199895_a(TagCollection<Block> p_199895_0_)
    {
        field_199899_c = p_199895_0_;
        ++field_199900_d;
    }

    public static TagCollection<Block> func_199896_a()
    {
        return field_199899_c;
    }

    private static Tag<Block> func_199894_a(String p_199894_0_)
    {
        return new BlockTags.Wrapper(new ResourceLocation(p_199894_0_));
    }

    static class Wrapper extends Tag<Block>
    {
        private int field_199892_a = -1;
        private Tag<Block> field_199893_b;

        public Wrapper(ResourceLocation p_i48217_1_)
        {
            super(p_i48217_1_);
        }

        public boolean func_199685_a_(Block p_199685_1_)
        {
            if (this.field_199892_a != BlockTags.field_199900_d)
            {
                this.field_199893_b = BlockTags.field_199899_c.func_199915_b(this.func_199886_b());
                this.field_199892_a = BlockTags.field_199900_d;
            }

            return this.field_199893_b.func_199685_a_(p_199685_1_);
        }

        public Collection<Block> func_199885_a()
        {
            if (this.field_199892_a != BlockTags.field_199900_d)
            {
                this.field_199893_b = BlockTags.field_199899_c.func_199915_b(this.func_199886_b());
                this.field_199892_a = BlockTags.field_199900_d;
            }

            return this.field_199893_b.func_199885_a();
        }

        public Collection<ITagEntry<Block>> func_200570_b()
        {
            if (this.field_199892_a != BlockTags.field_199900_d)
            {
                this.field_199893_b = BlockTags.field_199899_c.func_199915_b(this.func_199886_b());
                this.field_199892_a = BlockTags.field_199900_d;
            }

            return this.field_199893_b.func_200570_b();
        }
    }
}
