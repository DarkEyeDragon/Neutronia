package net.hdt.neutronia.world.dimensions.new_nether;

import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

@SuppressWarnings("ConstantConditions")
public class TeleporterNetherEx extends Teleporter
{
    public TeleporterNetherEx(WorldServer world)
    {
        super(world);
    }

    @Override
    public void placeInPortal(Entity entity, float rotationYaw)
    {
        if(world.provider.getDimensionType().getId() != DimensionType.THE_END.getId())
        {
            if(!placeInExistingPortal(entity, rotationYaw))
            {
                makePortal(entity);
                placeInExistingPortal(entity, rotationYaw);
            }
        }
        else
        {
            int entityPosX = MathHelper.floor(entity.posX);
            int entityPosY = MathHelper.floor(entity.posY) - 1;
            int entityPosZ = MathHelper.floor(entity.posZ);

            for(int z = -2; z <= 2; ++z)
            {
                for(int x = -2; x <= 2; ++x)
                {
                    for(int y = -1; y < 3; ++y)
                    {
                        int posX = entityPosX + x;
                        int posY = entityPosY + y;
                        int posZ = entityPosZ - z;
                        boolean beneathWorld = y < 0;
                        world.setBlockState(new BlockPos(posX, posY, posZ), beneathWorld ? Blocks.OBSIDIAN.getDefaultState() : Blocks.AIR.getDefaultState());
                    }
                }
            }

            entity.setLocationAndAngles((double) entityPosX, (double) entityPosY, (double) entityPosZ, entity.rotationYaw, 0.0F);
            entity.motionX = 0.0D;
            entity.motionY = 0.0D;
            entity.motionZ = 0.0D;
        }
    }

    @Override
    public boolean placeInExistingPortal(Entity entity, float rotationYaw)
    {
        double portalDistance = -1.0D;
        int entityPosX = MathHelper.floor(entity.posX);
        int entityPosZ = MathHelper.floor(entity.posZ);
        boolean newPortal = true;
        BlockPos portalPos = BlockPos.ORIGIN;
        long portalChunkPos = ChunkPos.asLong(entityPosX, entityPosZ);

        if(destinationCoordinateCache.containsKey(portalChunkPos))
        {
            Teleporter.PortalPosition portalPosition = destinationCoordinateCache.get(portalChunkPos);
            portalDistance = 0.0D;
            portalPos = portalPosition;
            portalPosition.lastUpdateTime = world.getTotalWorldTime();
            newPortal = false;
        }
        else
        {
            BlockPos entityPos = new BlockPos(entity);

            for(int x = -128; x <= 128; ++x)
            {
                BlockPos newPos;

                for(int z = -128; z <= 128; ++z)
                {
                    for(BlockPos checkPos = entityPos.add(x, world.getActualHeight() - 1 - entityPos.getY(), z); checkPos.getY() >= 0; checkPos = newPos)
                    {
                        newPos = checkPos.down();

                        if(world.getBlockState(checkPos).getBlock() == Blocks.PORTAL)
                        {
                            for(newPos = checkPos.down(); world.getBlockState(newPos).getBlock() == Blocks.PORTAL; newPos = newPos.down())
                            {
                                checkPos = newPos;
                            }

                            double distanceSqBetweenPosAndEntity = checkPos.distanceSq(entityPos);

                            if(portalDistance < 0.0D || distanceSqBetweenPosAndEntity < portalDistance)
                            {
                                portalDistance = distanceSqBetweenPosAndEntity;
                                portalPos = checkPos;
                            }
                        }
                    }
                }
            }
        }

        if(portalDistance >= 0.0D)
        {
            if(newPortal)
            {
                destinationCoordinateCache.put(portalChunkPos, new Teleporter.PortalPosition(portalPos, world.getTotalWorldTime()));
            }

            double adjustedPosX = (double) portalPos.getX() + 0.5D;
            double adjustedPosZ = (double) portalPos.getZ() + 0.5D;
            BlockPattern.PatternHelper patternHelper = Blocks.PORTAL.createPatternHelper(world, portalPos);
            boolean facingNegative = patternHelper.getForwards().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE;
            double offset = patternHelper.getForwards().getAxis() == EnumFacing.Axis.X ? (double) patternHelper.getFrontTopLeft().getZ() : (double) patternHelper.getFrontTopLeft().getX();
            double adjustedPosY = (double) (patternHelper.getFrontTopLeft().getY() + 1) - entity.getLastPortalVec().y * (double) patternHelper.getHeight();

            if(facingNegative)
            {
                ++offset;
            }

            if(patternHelper.getForwards().getAxis() == EnumFacing.Axis.X)
            {
                adjustedPosZ = offset + (1.0D - entity.getLastPortalVec().x) * (double) patternHelper.getWidth() * (double) patternHelper.getForwards().rotateY().getAxisDirection().getOffset();
            }
            else
            {
                adjustedPosX = offset + (1.0D - entity.getLastPortalVec().x) * (double) patternHelper.getWidth() * (double) patternHelper.getForwards().rotateY().getAxisDirection().getOffset();
            }

            float f = 0.0F;
            float f1 = 0.0F;
            float f2 = 0.0F;
            float f3 = 0.0F;

            if(patternHelper.getForwards().getOpposite() == entity.getTeleportDirection())
            {
                f = 1.0F;
                f1 = 1.0F;
            }
            else if(patternHelper.getForwards().getOpposite() == entity.getTeleportDirection().getOpposite())
            {
                f = -1.0F;
                f1 = -1.0F;
            }
            else if(patternHelper.getForwards().getOpposite() == entity.getTeleportDirection().rotateY())
            {
                f2 = 1.0F;
                f3 = -1.0F;
            }
            else
            {
                f2 = -1.0F;
                f3 = 1.0F;
            }

            double motionX = entity.motionX;
            double motionZ = entity.motionZ;
            entity.motionX = motionX * (double) f + motionZ * (double) f3;
            entity.motionZ = motionX * (double) f2 + motionZ * (double) f1;
            entity.rotationYaw = rotationYaw - (float) (entity.getTeleportDirection().getOpposite().getHorizontalIndex() * 90) + (float) (patternHelper.getForwards().getHorizontalIndex() * 90);

            if(entity instanceof EntityPlayerMP)
            {
                ((EntityPlayerMP) entity).connection.setPlayerLocation(adjustedPosX, adjustedPosY, adjustedPosZ, entity.rotationYaw, entity.rotationPitch);
            }
            else
            {
                entity.setLocationAndAngles(adjustedPosX, adjustedPosY, adjustedPosZ, entity.rotationYaw, entity.rotationPitch);
            }

            return true;

        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean makePortal(Entity entity)
    {
        double portalDistance = -1.0D;
        int entityPosX = MathHelper.floor(entity.posX);
        int entityPosY = MathHelper.floor(entity.posY);
        int entityPosZ = MathHelper.floor(entity.posZ);
        int i1 = entityPosX;
        int j1 = entityPosY;
        int k1 = entityPosZ;
        int l1 = 0;
        int i2 = random.nextInt(4);
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for(int x = entityPosX - 16; x <= entityPosX + 16; ++x)
        {
            double adjustedPosX = (double) x + 0.5D - entity.posX;

            for(int z = entityPosZ - 16; z <= entityPosZ + 16; ++z)
            {
                double adjustedPosZ = (double) z + 0.5D - entity.posZ;
                label293:

                for(int y = world.getActualHeight() - 1; y >= 0; --y)
                {
                    if(world.isAirBlock(mutableBlockPos.setPos(x, y, z)))
                    {
                        while(y > 0 && world.isAirBlock(mutableBlockPos.setPos(x, y - 1, z)))
                        {
                            --y;
                        }

                        for(int k3 = i2; k3 < i2 + 4; ++k3)
                        {
                            int l3 = k3 % 2;
                            int i4 = 1 - l3;

                            if(k3 % 4 >= 2)
                            {
                                l3 = -l3;
                                i4 = -i4;
                            }

                            for(int offsetZ = 0; offsetZ < 3; ++offsetZ)
                            {
                                for(int offsetX = 0; offsetX < 4; ++offsetX)
                                {
                                    for(int offsetY = -1; offsetY < 4; ++offsetY)
                                    {
                                        int posX = x + (offsetX - 1) * l3 + offsetZ * i4;
                                        int posY = y + offsetY;
                                        int posZ = z + (offsetX - 1) * i4 - offsetZ * l3;
                                        mutableBlockPos.setPos(posX, posY, posZ);

                                        if(offsetY < 0 && !world.getBlockState(mutableBlockPos).getMaterial().isSolid() || offsetY >= 0 && !world.isAirBlock(mutableBlockPos))
                                        {
                                            continue label293;
                                        }
                                    }
                                }
                            }

                            double adjustedPosY = (double) y + 0.5D - entity.posY;
                            double adjustedPos = adjustedPosX * adjustedPosX + adjustedPosY * adjustedPosY + adjustedPosZ * adjustedPosZ;

                            if(portalDistance < 0.0D || adjustedPos < portalDistance)
                            {
                                portalDistance = adjustedPos;
                                i1 = x;
                                j1 = y;
                                k1 = z;
                                l1 = k3 % 4;
                            }
                        }
                    }
                }
            }
        }

        if(portalDistance < 0.0D)
        {
            for(int x = entityPosX - 16; x <= entityPosX + 16; ++x)
            {
                double adjustedPosX = (double) x + 0.5D - entity.posX;

                for(int z = entityPosZ - 16; z <= entityPosZ + 16; ++z)
                {
                    double adjustedPosZ = (double) z + 0.5D - entity.posZ;
                    label231:

                    for(int y = world.getActualHeight() - 1; y >= 0; --y)
                    {
                        if(world.isAirBlock(mutableBlockPos.setPos(x, y, z)))
                        {
                            while(y > 0 && world.isAirBlock(mutableBlockPos.setPos(x, y - 1, z)))
                            {
                                --y;
                            }

                            for(int offsetZ = i2; offsetZ < i2 + 2; ++offsetZ)
                            {
                                int j8 = offsetZ % 2;
                                int j9 = 1 - j8;

                                for(int offsetX = 0; offsetX < 4; ++offsetX)
                                {
                                    for(int offsetY = -1; offsetY < 4; ++offsetY)
                                    {
                                        int posX = x + (offsetX - 1) * j8;
                                        int posY = y + offsetY;
                                        int posZ = z + (offsetX - 1) * j9;
                                        mutableBlockPos.setPos(posX, posY, posZ);

                                        if(offsetY < 0 && !world.getBlockState(mutableBlockPos).getMaterial().isSolid() || offsetY >= 0 && !world.isAirBlock(mutableBlockPos))
                                        {
                                            continue label231;
                                        }
                                    }
                                }

                                double adjustedPosY = (double) y + 0.5D - entity.posY;
                                double adjustedPos = adjustedPosX * adjustedPosX + adjustedPosY * adjustedPosY + adjustedPosZ * adjustedPosZ;

                                if(portalDistance < 0.0D || adjustedPos < portalDistance)
                                {
                                    portalDistance = adjustedPos;
                                    i1 = x;
                                    j1 = y;
                                    k1 = z;
                                    l1 = offsetZ % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        int i6 = i1;
        int k2 = j1;
        int k6 = k1;
        int l6 = l1 % 2;
        int i3 = 1 - l6;

        if(l1 % 4 >= 2)
        {
            l6 = -l6;
            i3 = -i3;
        }

        if(portalDistance < 0.0D)
        {
            j1 = MathHelper.clamp(j1, 70, world.getActualHeight() - 10);
            k2 = j1;

            for(int z = -1; z <= 1; ++z)
            {
                for(int x = 1; x < 3; ++x)
                {
                    for(int y = -1; y < 3; ++y)
                    {
                        int posX = i6 + (x - 1) * l6 + z * i3;
                        int posY = k2 + y;
                        int posZ = k6 + (x - 1) * i3 - z * l6;
                        boolean beneathWorld = y < 0;
                        world.setBlockState(new BlockPos(posX, posY, posZ), beneathWorld ? Blocks.OBSIDIAN.getDefaultState() : Blocks.AIR.getDefaultState());
                    }
                }
            }
        }

        IBlockState iblockstate = Blocks.PORTAL.getDefaultState().withProperty(BlockPortal.AXIS, l6 == 0 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);

        for(int i8 = 0; i8 < 4; ++i8)
        {
            for(int l8 = 0; l8 < 4; ++l8)
            {
                for(int l9 = -1; l9 < 4; ++l9)
                {
                    int l10 = i6 + (l8 - 1) * l6;
                    int l11 = k2 + l9;
                    int k12 = k6 + (l8 - 1) * i3;
                    boolean beneathWorld = l8 == 0 || l8 == 3 || l9 == -1 || l9 == 3;
                    world.setBlockState(new BlockPos(l10, l11, k12), beneathWorld ? Blocks.OBSIDIAN.getDefaultState() : iblockstate, 2);
                }
            }

            for(int zx = 0; zx < 4; ++zx)
            {
                for(int y = -1; y < 4; ++y)
                {
                    int posX = i6 + (zx - 1) * l6;
                    int posY = k2 + y;
                    int posZ = k6 + (zx - 1) * i3;
                    BlockPos blockpos = new BlockPos(posX, posY, posZ);
                    world.notifyNeighborsOfStateChange(blockpos, world.getBlockState(blockpos).getBlock(), false);
                }
            }
        }

        return true;
    }

    @Override
    public void removeStalePortalLocations(long worldTime)
    {
        if(worldTime % 100L == 0L)
        {
            destinationCoordinateCache.values().removeIf(portalPosition -> portalPosition == null || portalPosition.lastUpdateTime < worldTime - 300L);
        }
    }

    public static TeleporterNetherEx getTeleporterForWorld(MinecraftServer minecraftServer, int dimension)
    {
        WorldServer worldServer = minecraftServer.getWorld(dimension);

        for(Teleporter teleporter : worldServer.customTeleporters)
        {
            if(teleporter instanceof TeleporterNetherEx)
            {
                return (TeleporterNetherEx) teleporter;
            }
        }

        TeleporterNetherEx teleporterNetherEx = new TeleporterNetherEx(worldServer);
        worldServer.customTeleporters.add(teleporterNetherEx);
        return teleporterNetherEx;
    }
}