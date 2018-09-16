package net.hdt.neutronia.blocks.overworld;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.Random;

import static net.minecraft.block.BlockLiquid.LEVEL;

public class BlockKelp extends BlockOverworldBase {

    public static int GROWTH_CHANCE = 10;

    public BlockKelp() {
        super(Material.WATER, "kelp", false);
        this.setSoundType(SoundType.SAND);
        this.setHardness(0.5f);
        this.setHarvestLevel("shovel", 0);
        this.setTickRandomly(true);
        setDefaultState(getDefaultState().withProperty(BlockLiquid.LEVEL, 15));
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(LEVEL);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LEVEL);
    }

    @Override
    public IProperty[] getIgnoredProperties() {
        return new IProperty[]{BlockLiquid.LEVEL};
    }

    /**
     * Called randomly when setTickRandomly is set to true (used by e.g. crops to grow, etc.)
     */
    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
        tryGrow(world, pos);
    }

    //use for debugging
	/*@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.scheduleBlockUpdate(pos, this, 40, 1);
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		grow(worldIn, pos);
		worldIn.scheduleBlockUpdate(pos, this, 40, 1);
	}*/

    private void tryGrow(World world, BlockPos pos) {
        if (world.rand.nextInt(GROWTH_CHANCE) != 0) return;
        TileKelp tileEnt = (TileKelp) world.getTileEntity(pos);
        if (tileEnt.getHeight() < tileEnt.getMaxHeight() && tileEnt.canGrowTo(tileEnt.getHeight() + 1)) {
            tileEnt.setHeight(tileEnt.getHeight() + 1);
            tileEnt.syncTileEntity();
        }
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileKelp();
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.TRANSLUCENT;
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileKelp tileEnt = (TileKelp) world.getTileEntity(pos);
        int height = tileEnt.getHeight();

        for (int i = height; i > 0; i--) {
            ItemStack drop = new ItemStack(this);
            Block.spawnAsEntity(world, pos.up(i), drop);
        }

        super.breakBlock(world, pos, state);
    }

    /*@Override
    public IProperty[] getIgnoredProperties() {
        return new IProperty[] {
                BlockLiquid.LEVEL
        };
    }*/

    public static class TileKelp extends TileEntity {

        private static final Random rand = new Random(439875L);

        private static final NoiseGeneratorPerlin angleNoise = new NoiseGeneratorPerlin(rand, 1);
        private static final NoiseGeneratorPerlin delayNoise = new NoiseGeneratorPerlin(rand, 3);

        private int height = -1;
        private AxisAlignedBB cachedBB;
        // Optifine can somehow cause TESR to be called before getRenderBoundingBox,
        // so this needs to be nonnull otherwise there is a potential NPE
        private Vec3d offset = Vec3d.ZERO;
        private double swayAngle;
        private double swayDelay;
        private int maxHeight;

        public TileKelp() {
            initRandomHeights();
        }

        public void initRandomHeights() {
            this.setHeight(rand.nextInt(5) + 1);
            this.setMaxHeight(rand.nextInt(10) + 5);

			/*this.setHeight(2);
			this.setMaxHeight(15);*/
        }

        /**
         * Called when this is first added to the world (by {@link World#addTileEntity(TileEntity)}).
         * Override instead of adding {@code if (firstTick)} changers in update.
         */
        @Override
        public void onLoad() {
            revalidateHeight();
            recalculateClientBB();
        }

        public void syncTileEntity() {
            FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList()
                    .sendPacketToAllPlayersInDimension(getUpdatePacket(), world.provider.getDimension());
        }

        public void recalculateClientBB() {
            rand.setSeed(MathHelper.getCoordinateRandom(getPos().getX(), getPos().getY(), getPos().getZ()));

            cachedBB = new AxisAlignedBB(getPos()).grow(1.1, height / 2f, 1.1).offset(0, height / 2f, 0);

            offset = new Vec3d((rand.nextFloat() - 0.5f) * 0.25f, 0, (rand.nextFloat() - 0.5f) * 0.25f);

            Vec3d centerPos = new Vec3d(getPos()).add(0.5, 0.5, 0.5).add(offset);

            swayAngle = angleNoise.getValue(centerPos.x / 200, centerPos.z / 200);
            swayAngle += 1; // convert to 0..2
            swayAngle *= Math.PI; // convert to 0..2PI

            swayDelay = delayNoise.getValue(centerPos.x / 100, centerPos.z / 100);
            swayDelay *= 20;
        }

        /**
         * Adjust for dynamic changes like water removed, blocks placed, etc
         */
        public void revalidateHeight() {
            int testHeight = 0;
            while (testHeight < height && canGrowTo(testHeight + 1)) {
                testHeight++;
            }
            if (testHeight != getHeight()) {
                setHeight(testHeight);
                syncTileEntity();
            }
        }

        public boolean canGrowTo(int height) {
            return getWorld().getBlockState(getPos().up(height)).getMaterial().isLiquid();
        }

        @Override
        public AxisAlignedBB getRenderBoundingBox() {
            if (/*height < 0 || */cachedBB == null) {
                recalculateClientBB();
            }
            return cachedBB;
        }

        @Override
        public double getMaxRenderDistanceSquared() {
            // Show more kelp when the player is higher up
//			return MathHelper.clamp(((Minecraft.getMinecraft().getRenderViewEntity().posY - 40) / 12), 0.2, 4) * super.getMaxRenderDistanceSquared();
            return super.getMaxRenderDistanceSquared() * 2;
        }

        @Override
        public boolean hasFastRenderer() {
            return true;
        }

        public int getMaxHeight() {
            return this.maxHeight;
        }

        public void setMaxHeight(int newMax) {
            this.maxHeight = newMax;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public Vec3d getOffset() {
            return offset;
        }

        /**
         * The angle of sway, in radians, on 0..2PI
         */
        public double getSwayAngle() {
            return swayAngle;
        }

        /**
         * The amount of delay (offset from tick count) for the sway animation, in ticks
         */
        public double getSwayDelay() {
            return swayDelay;
        }

        @Override
        public void readFromNBT(NBTTagCompound compound) {
            super.readFromNBT(compound);

            setHeight(compound.getInteger("height"));
            setMaxHeight(compound.getInteger("maxHeight"));

            //prevent kelp breaking when updating old world
            if (this.getMaxHeight() == 0) {
                initRandomHeights();
            }

        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound compound) {

            compound.setInteger("height", getHeight());
            compound.setInteger("maxHeight", getMaxHeight());

            return super.writeToNBT(compound);
        }

        @Override
        public NBTTagCompound getUpdateTag() {
            return this.writeToNBT(new NBTTagCompound());
        }

        @Nullable
        @Override
        public SPacketUpdateTileEntity getUpdatePacket() {
            return new SPacketUpdateTileEntity(this.pos, 1, getUpdateTag());
        }

        @Override
        public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
            super.onDataPacket(net, pkt);
            this.readFromNBT(pkt.getNbtCompound());
            recalculateClientBB();
        }
    }
}