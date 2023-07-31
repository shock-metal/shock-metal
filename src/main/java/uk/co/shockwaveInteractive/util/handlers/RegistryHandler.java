package uk.co.shockwaveinteractive.util.handlers;


import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static uk.co.shockwaveinteractive.init.Blocks.REGISTRY_BLOCKS;
import static uk.co.shockwaveinteractive.init.Enchantments.REGISTRY_ENCHANTMENTS;
import static uk.co.shockwaveinteractive.init.Entities.REGISTRY_ENTITIES;
import static uk.co.shockwaveinteractive.init.Items.REGISTRY_ITEMS;
import static uk.co.shockwaveinteractive.world.biomemods.ModBiomeModifiers.BIOME_MODIFIERS;
import static uk.co.shockwaveinteractive.world.feature.ModPlacedFeatures.PLACED_FEATURE;

public class RegistryHandler
{
	public static void init() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		REGISTRY_ITEMS.register(bus);
		REGISTRY_BLOCKS.register(bus);
		REGISTRY_ENTITIES.register(bus);
		REGISTRY_ENCHANTMENTS.register(bus);
		BIOME_MODIFIERS.register(bus);
		PLACED_FEATURE.register(bus);
	}
}
