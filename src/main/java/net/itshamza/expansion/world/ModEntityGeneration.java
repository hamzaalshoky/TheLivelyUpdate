package net.itshamza.expansion.world;

import net.itshamza.expansion.entity.ModEntityCreator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class ModEntityGeneration {
    public static void onEntitySpawn(final BiomeLoadingEvent event) {

        if(doesBiomeMatch(event.getName(), Biomes.DRIPSTONE_CAVES)) {
            event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(ModEntityCreator.SKUBBLE.get(), 6, 3, 3));
        }
    }

    public static boolean doesBiomeMatch(ResourceLocation biomeNameIn, ResourceKey<Biome> biomeIn) {
        return biomeNameIn.getPath().matches(biomeIn.location().getPath());
    }
}
