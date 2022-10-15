package net.itshamza.expansion.block;

import net.itshamza.expansion.ExpansionMod;
import net.itshamza.expansion.item.ModCreativeModeTabs;
import net.itshamza.expansion.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static net.minecraft.world.level.material.Material.STONE;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ExpansionMod.MOD_ID);

    public static final RegistryObject<Block> infested_dripstone = registerBlock("infested_dripstone", () -> new Block(BlockBehaviour.Properties.of(STONE)), ModCreativeModeTabs.LIVELY_TAB);
    public static final RegistryObject<Block> infested_amethyst = registerBlock("infested_amethyst", () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST)), ModCreativeModeTabs.LIVELY_TAB);

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem (String name, RegistryObject<T> block, CreativeModeTab tab){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
