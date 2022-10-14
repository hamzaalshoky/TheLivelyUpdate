package net.itshamza.expansion.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.expansion.entity.custom.SkubbleEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SkubbleRenderer extends GeoEntityRenderer<SkubbleEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("expansion:textures/entity/skubble/skubble.png");
    
    public SkubbleRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SkubbleModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(SkubbleEntity instance) {
        return TEXTURE;
    }


    @Override
    public RenderType getRenderType(SkubbleEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(1F, 1F, 1F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
