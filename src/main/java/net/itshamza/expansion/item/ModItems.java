package net.itshamza.expansion.item;

import net.itshamza.expansion.ExpansionMod;
import net.itshamza.expansion.entity.ModEntityCreator;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ExpansionMod.MOD_ID);

    public static final RegistryObject<Item> SKUBBLE_SPAWN_EGG = ITEMS.register("skubble_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.SKUBBLE,6577212, 7629892,
                    new Item.Properties().tab(ModCreativeModeTabs.LIVELY_TAB)));;

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
