package net.itshamza.expansion.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs {
    public static final CreativeModeTab LIVELY_TAB = new CreativeModeTab("lively_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.SKUBBLE_SPAWN_EGG.get());
        }
    };
}
