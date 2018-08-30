package net.hdt.neutronia.entity.render;

import net.hdt.neutronia.entity.EntityAdventurerVillager;
import net.hdt.neutronia.entity.render.model.ModelAdventurerVillager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import static net.hdt.neutronia.base.util.Reference.MOD_ID;

public class RenderAdventurerVillager extends RenderLiving<EntityAdventurerVillager> {

    private static final ResourceLocation ADVENTURER_VILLAGER_TEXTURES = new ResourceLocation(MOD_ID, "textures/entity/villagers/adventurer/adventurer_villager.png");

    public RenderAdventurerVillager(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelAdventurerVillager(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAdventurerVillager entity) {
        return ADVENTURER_VILLAGER_TEXTURES;
    }

    @Override
    protected void applyRotations(EntityAdventurerVillager entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }
}
