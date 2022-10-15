package net.itshamza.expansion.entity.client;

import net.itshamza.expansion.ExpansionMod;
import net.itshamza.expansion.entity.custom.SkubbleEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SkubbleModel extends AnimatedGeoModel<SkubbleEntity> {
    @Override
    public ResourceLocation getModelLocation(SkubbleEntity object) {
        return new ResourceLocation(ExpansionMod.MOD_ID, "geo/skubble.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SkubbleEntity object) {
        return new ResourceLocation(ExpansionMod.MOD_ID, "textures/entity/skubble/skubble.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SkubbleEntity animatable) {
        return new ResourceLocation(ExpansionMod.MOD_ID, "animations/skubble.animation.json");
    }
}
