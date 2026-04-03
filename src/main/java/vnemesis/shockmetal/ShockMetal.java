package vnemesis.shockmetal;

import net.minecraft.world.item.CreativeModeTabs;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import vnemesis.shockmetal.blocks.ShockMetalBlocksRegistry;
import vnemesis.shockmetal.entity.ShockMetalEntitiesRegistry;
import vnemesis.shockmetal.item.ShockMetalItemsRegistry;
import vnemesis.shockmetal.item.armour.ShockMetalArmorMaterials;
import vnemesis.shockmetal.tab.ShockMetalCreativeModeTabs;

import static vnemesis.shockmetal.reference.ModIdReference.SHOCKMETAL_MOD_ID;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(SHOCKMETAL_MOD_ID)
public class ShockMetal
{
    // Define mod id in a common place for everything to reference
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
//    public static final CreativeModeTab SHOCKMETALTAB = new ShockMetalModTab(
//            CreativeModeTab.builder().title(new ).icon(() -> new net.minecraft.world.item.ItemStack(ShockMetalItems.SHOCKMETAL_INGOT.get())).build());


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public ShockMetal(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        InitRegistries(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ShockMetal) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event)
    {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS)
        {
            event.accept(ShockMetalItemsRegistry.SHOCKMETAL_INGOT);
            event.accept(ShockMetalItemsRegistry.SHOCKMETAL_DUST);
            event.accept(ShockMetalItemsRegistry.NETHERITE_DUST);
            event.accept(ShockMetalItemsRegistry.SHOCKRITE_DUST);
            event.accept(ShockMetalItemsRegistry.ORE_GRINDER);
        }

        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
        {
            event.accept(ShockMetalBlocksRegistry.SHOCKMETAL_BLOCK);
            event.accept(ShockMetalBlocksRegistry.SHOCKMETAL_NETHER_ORE_BLOCK);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    private void InitRegistries(IEventBus modEventBus)
    {
        ShockMetalCreativeModeTabs.register(modEventBus);
        ShockMetalArmorMaterials.register(modEventBus);
        ShockMetalItemsRegistry.register(modEventBus);
        ShockMetalBlocksRegistry.register(modEventBus);
        ShockMetalEntitiesRegistry.register(modEventBus);
    }
}
