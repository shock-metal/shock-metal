package uk.co.shockwaveinteractive;

import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;
import uk.co.shockwaveinteractive.config.MainConfig;
import uk.co.shockwaveinteractive.integration.IntegrationHandler;
import uk.co.shockwaveinteractive.tabs.ShockMetalTab;
import uk.co.shockwaveinteractive.util.reference.MainReference;
import uk.co.shockwaveinteractive.util.handlers.RegistryHandler;
import uk.co.shockwaveinteractive.util.renderers.SpriteRendererShock;
import uk.co.shockwaveinteractive.util.renderers.VacuumMinecartRenderer;
import uk.co.shockwaveinteractive.world.gen.WorldGenCustomOres;

import java.util.Random;

import static uk.co.shockwaveinteractive.init.Entities.SHOCK_GRENADE_ENTITY;
import static uk.co.shockwaveinteractive.init.Entities.VACUUM_MINECART_ENTITY;

@Mod(MainReference.MODID)
public class ShockMetalMain
{
	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();
	public static final ItemGroup SHOCKMETALTAB = new ShockMetalTab("shockmetaltab");
	public static FMLCommonSetupEvent preIntEvent;
	public static Random rnd;

	public ShockMetalMain() {
		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		// Register the enqueueIMC method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		// Register the processIMC method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		// Register the doClientStuff method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

		MainConfig.register();

		RegistryHandler.init();

		rnd = new Random();

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event)
	{
		preIntEvent = event;
		WorldGenCustomOres.initOres();
		IntegrationHandler.checkInstalled();
		IntegrationHandler.runPreInit();
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		registerEntityRenderingHandlers();
		IntegrationHandler.runInit();
	}

	private void enqueueIMC(final InterModEnqueueEvent event)
	{
		// some example code to dispatch IMC to another mod
//		InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
	}

	private void processIMC(final InterModProcessEvent event)
	{
		// some example code to receive and process InterModComms from other mods
//		LOGGER.info("Got IMC {}", event.getIMCStream().
//				map(m->m.getMessageSupplier().get()).
//				collect(Collectors.toList()));
	}
	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {
	}

	// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
	// Event bus for receiving Registry Events)
	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
		@SubscribeEvent
		public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
		}
	}

	private void registerEntityRenderingHandlers() {
		RenderingRegistry.registerEntityRenderingHandler(SHOCK_GRENADE_ENTITY.get(), SpriteRendererShock::new);
		RenderingRegistry.registerEntityRenderingHandler(VACUUM_MINECART_ENTITY.get(), VacuumMinecartRenderer::new);
	}
}
