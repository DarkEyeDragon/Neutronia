package net.hdt.neutronia.util.handlers;

import net.hdt.neutronia.entity.*;
import net.hdt.neutronia.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EntityEventHandler {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof EntityVillager) {
            World world = event.getEntity().world;
            BlockPos pos = event.getEntity().getPosition();
            if (event.getEntity().getEntityWorld().getBiome(new BlockPos(pos)) == Biomes.DESERT || event.getEntity().getEntityWorld().getBiome(new BlockPos(pos)) == Biomes.BEACH || event.getEntity().getEntityWorld().getBiome(new BlockPos(pos)) == Biomes.MUTATED_DESERT || event.getEntity().getEntityWorld().getBiome(new BlockPos(pos)) == Biomes.DESERT_HILLS   && !world.isRemote) {
                EntityMummyVillager mummyVillager = new EntityMummyVillager(world);
                if (mummyVillager.isAIDisabled())
                    mummyVillager.setNoAI(false);
                mummyVillager.setPositionAndRotation(pos.getX() + 1D, pos.getY() + 0.5D, pos.getZ() + 1D, mummyVillager.rotationYaw, mummyVillager.cameraPitch);
                mummyVillager.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(mummyVillager)), null);
                world.spawnEntity(mummyVillager);
            }
        }
        if (event.getEntity() instanceof EntityZombie) {
            World world = event.getEntity().world;
            BlockPos pos = event.getEntity().getPosition();
            if (event.getEntity().getEntityWorld().getBiome(new BlockPos(pos)) == Biomes.DESERT || event.getEntity().getEntityWorld().getBiome(new BlockPos(pos)) == Biomes.BEACH || event.getEntity().getEntityWorld().getBiome(new BlockPos(pos)) == Biomes.MUTATED_DESERT || event.getEntity().getEntityWorld().getBiome(new BlockPos(pos)) == Biomes.DESERT_HILLS && !world.isRemote) {
                EntityMummy mummy = new EntityMummy(world);
                if (mummy.isAIDisabled())
                    mummy.setNoAI(false);
                mummy.setPositionAndRotation(pos.getX() + 1D, pos.getY() + 0.5D, pos.getZ() + 1D, mummy.rotationYaw, mummy.cameraPitch);
                mummy.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(mummy)), null);
                world.spawnEntity(mummy);
            }
        }
        if (event.getEntity() instanceof EntityScubaVillager) {
            World world = event.getEntity().world;
            BlockPos pos = event.getEntity().getPosition();
            if (event.getEntity().getEntityWorld().getBiome(new BlockPos(pos)) == Biomes.OCEAN || event.getEntity().getEntityWorld().getBiome(new BlockPos(pos)) == Biomes.DEEP_OCEAN || event.getEntity().getEntityWorld().getBiome(new BlockPos(pos)) == Biomes.FROZEN_OCEAN || event.getEntity().getEntityWorld().getBiome(new BlockPos(pos)) == Biomes.RIVER || event.getEntity().getEntityWorld().getBiome(new BlockPos(pos)) == Biomes.FROZEN_RIVER && !world.isRemote) {
                EntityDrownedScubaVillager drowned = new EntityDrownedScubaVillager(world);
                if (drowned.isAIDisabled())
                    drowned.setNoAI(false);
                drowned.setPositionAndRotation(pos.getX() + 1D, pos.getY() + 0.5D, pos.getZ() + 1D, drowned.rotationYaw, drowned.cameraPitch);
                drowned.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(drowned)), null);
                world.spawnEntity(drowned);
            }
        }
        if (event.getEntity() instanceof EntityZombie) {
            World world = event.getEntity().world;
            BlockPos pos = event.getEntity().getPosition();
            if (event.getEntity().getEntityWorld().getBiome(new BlockPos(pos)) == Biomes.OCEAN || event.getEntity().getEntityWorld().getBiome(new BlockPos(pos)) == Biomes.DEEP_OCEAN || event.getEntity().getEntityWorld().getBiome(new BlockPos(pos)) == Biomes.FROZEN_OCEAN || event.getEntity().getEntityWorld().getBiome(new BlockPos(pos)) == Biomes.RIVER || event.getEntity().getEntityWorld().getBiome(new BlockPos(pos)) == Biomes.FROZEN_RIVER && !world.isRemote) {
                EntityDrowned drowned = new EntityDrowned(world);
                if (drowned.isAIDisabled())
                    drowned.setNoAI(false);
                drowned.setPositionAndRotation(pos.getX() + 1D, pos.getY() + 0.5D, pos.getZ() + 1D, drowned.rotationYaw, drowned.cameraPitch);
                drowned.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(drowned)), null);
                world.spawnEntity(drowned);
            }
        }
    }

    @SubscribeEvent
    public void onHurt(LivingHurtEvent event) {
        Entity e = event.getEntity();
        if (e instanceof EntitySquid && !e.world.isRemote) {
            List<EntityPlayer> players = e.world.getEntitiesWithinAABB(EntityPlayer.class, e.getEntityBoundingBox().grow(4, 4, 4));
            for (EntityPlayer player : players)
                player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 80, 0));
            WorldServer ws = (WorldServer) e.world;
            ws.spawnParticle(EnumParticleTypes.SMOKE_LARGE, e.posX + e.width / 2, e.posY + e.height / 2, e.posZ + e.width / 2, 100, 0, 0, 0, 0.02);
        }
    }

}
