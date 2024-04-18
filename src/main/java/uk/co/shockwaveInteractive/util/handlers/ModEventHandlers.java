package uk.co.shockwaveinteractive.util.handlers;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import uk.co.shockwaveinteractive.util.events.DamageEventHandler;

public class ModEventHandlers {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new DamageEventHandler());
    }
}

