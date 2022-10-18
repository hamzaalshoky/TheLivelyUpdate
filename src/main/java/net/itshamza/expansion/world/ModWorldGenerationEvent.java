package net.itshamza.expansion.world;

import net.itshamza.expansion.ExpansionMod;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ExpansionMod.MOD_ID)
public class ModWorldGenerationEvent {
    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        ModEntityGeneration.onEntitySpawn(event);
    }
}
