package vnemesis.shockmetal.tab;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import vnemesis.shockmetal.blocks.ShockMetalBlocksRegistry;
import vnemesis.shockmetal.item.ShockMetalItemsRegistry;
import vnemesis.shockmetal.reference.ModIdReference;

import java.util.function.Supplier;

public class ShockMetalCreativeModeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModIdReference.SHOCKMETAL_MOD_ID);

    public static final Supplier<CreativeModeTab> SHOCKMETAL_TAB =
            CREATIVE_MODE_TABS.register(
                    ModIdReference.SHOCKMETAL_MOD_ID,
                    () -> CreativeModeTab
                            .builder()
                            .icon(() -> new ItemStack(ShockMetalItemsRegistry.SHOCKMETAL_INGOT.get()))
                            .title(Component.translatable("creativetab.shockmetal.main"))
                            .displayItems((itemDisplayParameters, output) -> {
                                // Resources
                                output.accept(ShockMetalItemsRegistry.SHOCKMETAL_INGOT);
                                output.accept(ShockMetalItemsRegistry.SHOCKMETAL_DUST);
                                output.accept(ShockMetalItemsRegistry.NETHERITE_DUST);
                                output.accept(ShockMetalItemsRegistry.SHOCKRITE_DUST);
                                output.accept(ShockMetalItemsRegistry.ORE_GRINDER);
                                // Blocks
                                output.accept(ShockMetalBlocksRegistry.SHOCKMETAL_BLOCK);
                                output.accept(ShockMetalBlocksRegistry.SHOCKMETAL_NETHER_ORE_BLOCK);
                                // Tools
                                output.accept(ShockMetalItemsRegistry.SHOCKMETAL_SWORD);
                                output.accept(ShockMetalItemsRegistry.SHOCKMETAL_PICKAXE);
                                output.accept(ShockMetalItemsRegistry.SHOCKMETAL_AXE);
                                output.accept(ShockMetalItemsRegistry.SHOCKMETAL_SHOVEL);
                                output.accept(ShockMetalItemsRegistry.SHOCKMETAL_HOE);
                                // Armour
                                output.accept(ShockMetalItemsRegistry.SHOCKMETAL_HELMET);
                                output.accept(ShockMetalItemsRegistry.SHOCKMETAL_CHESTPLATE);
                                output.accept(ShockMetalItemsRegistry.SHOCKMETAL_LEGGINGS);
                                output.accept(ShockMetalItemsRegistry.SHOCKMETAL_BOOTS);
                                // Combat / transport
                                output.accept(ShockMetalItemsRegistry.SHOCK_GRENADE_ITEM);
                                output.accept(ShockMetalItemsRegistry.VACUUM_MINECART_ITEM);
                            })
                            .build()
            );

    public static void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
