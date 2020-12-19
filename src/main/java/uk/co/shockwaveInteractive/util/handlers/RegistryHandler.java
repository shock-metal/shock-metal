package uk.co.shockwaveinteractive.util.handlers;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import uk.co.shockwaveinteractive.init.BlockInit;
import uk.co.shockwaveinteractive.init.ItemInit;

public class RegistryHandler
{
	public static void init() {
		ItemInit.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		BlockInit.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
