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
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import vnemesis.shockmetal.blocks.ShockMetalBlocksRegistry;
import vnemesis.shockmetal.capabilities.ItemEnergy;
import vnemesis.shockmetal.entity.ShockMetalEntitiesRegistry;
import vnemesis.shockmetal.event.DamageEventHandler;
import vnemesis.shockmetal.item.ItemEnergyBase;
import vnemesis.shockmetal.item.ShockMetalItemsRegistry;
import vnemesis.shockmetal.item.armour.ShockMetalArmorMaterials;
import vnemesis.shockmetal.item.shield.ItemShieldModule;
import vnemesis.shockmetal.sounds.ShockMetalSoundsRegistry;
import vnemesis.shockmetal.tab.ShockMetalCreativeModeTabs;

import static vnemesis.shockmetal.reference.ModIdReference.SHOCKMETAL_MOD_ID;

@Mod(SHOCKMETAL_MOD_ID)
public class ShockMetal
{
    public static final Logger LOGGER = LogUtils.getLogger();

    public ShockMetal(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerCapabilities);

        InitRegistries(modEventBus);

        // Register game-event listeners (damage handler etc.)
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new DamageEventHandler());

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event)
    {
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event)
    {
        // Register FE energy capability for all ItemEnergyBase items
        event.registerItem(
                Capabilities.EnergyStorage.ITEM,
                (stack, ctx) -> {
                    if (stack.getItem() instanceof ItemEnergyBase energyItem) {
                        return new ItemEnergy(stack, energyItem.getEnergyMax());
                    }
                    return null;
                },
                ShockMetalItemsRegistry.SHIELD_MODULE.get()
        );
    }

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

        if (event.getTabKey() == CreativeModeTabs.COMBAT)
        {
            event.accept(ShockMetalItemsRegistry.SHIELD_MODULE);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("HELLO from server starting");
    }

    private void InitRegistries(IEventBus modEventBus)
    {
        ShockMetalCreativeModeTabs.register(modEventBus);
        ShockMetalArmorMaterials.register(modEventBus);
        ShockMetalItemsRegistry.register(modEventBus);
        ShockMetalBlocksRegistry.register(modEventBus);
        ShockMetalEntitiesRegistry.register(modEventBus);
        ShockMetalSoundsRegistry.register(modEventBus);
    }
}
