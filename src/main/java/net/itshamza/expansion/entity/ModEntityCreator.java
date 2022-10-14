package net.itshamza.expansion.entity;

import net.itshamza.expansion.ExpansionMod;
import net.itshamza.expansion.entity.client.SkubbleRenderer;
import net.itshamza.expansion.entity.custom.SkubbleEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = ExpansionMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityCreator {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, ExpansionMod.MOD_ID);

    // REGESTRIES

    public static final RegistryObject<EntityType<SkubbleEntity>> SKUBBLE = ENTITY_TYPES.register("skubble", () -> EntityType.Builder.of(SkubbleEntity::new, MobCategory.MONSTER).sized(0.6f, 0.2f).build(new ResourceLocation(ExpansionMod.MOD_ID, "skubble").toString()));

    // ATTRIBUTES

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntityCreator.SKUBBLE.get(), SkubbleEntity.setAttributes());
    }

    // RENDERERS

    @SubscribeEvent
    public static void registerEntityRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityCreator.SKUBBLE.get(), SkubbleRenderer::new);
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
