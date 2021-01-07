package uk.co.shockwaveinteractive.util.handlers;


import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static uk.co.shockwaveinteractive.init.Blocks.REGISTRY_BLOCKS;
import static uk.co.shockwaveinteractive.init.Entities.REGISTRY_ENTITIES;
import static uk.co.shockwaveinteractive.init.Items.REGISTRY_ITEMS;

public class RegistryHandler
{
	public static void init() {
		REGISTRY_ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		REGISTRY_BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		REGISTRY_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
