/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Quark Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Quark
 * <p>
 * Quark is Open Source and distributed under the
 * CC-BY-NC-SA 3.0 License: https://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB
 * <p>
 * File Created @ [20/03/2016, 19:31:28 (GMT)]
 */
package net.hdt.neutronia.modules.building.blocks.slab;

import net.hdt.neutronia.blocks.overworld.BlockOverworldSlabBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.BlockPos;

public class BlockVanillaSlab extends BlockOverworldSlabBase {

    public BlockVanillaSlab(String name, IBlockState state, boolean doubleSlab) {
        super(name, state.getMaterial(), doubleSlab);

        setHardness(state.getBlockHardness(null, new BlockPos(0, 0, 0)));
        setResistance(state.getBlock().getExplosionResistance(null) * 5F / 3F);
        setSoundType(state.getBlock().getSoundType());
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

}
