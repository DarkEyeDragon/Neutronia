package net.hdt.neutronia.entity.render;

import net.hdt.neutronia.entity.EntityMushroomShroomGlutton;
import net.hdt.neutronia.entity.render.model.ModelMushroomShroomGlutton;
import net.hdt.neutronia.util.Reference;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderMushroomShroomGlutton extends RenderLiving<EntityMushroomShroomGlutton> {

    public static final ResourceLocation SCORP_TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/entity/mushroom_shroom_glutton.png");

    public RenderMushroomShroomGlutton(RenderManager manager) {
        super(manager, new ModelMushroomShroomGlutton(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMushroomShroomGlutton entity) {
        return SCORP_TEXTURE;
    }
}
