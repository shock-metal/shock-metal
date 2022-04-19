package uk.co.shockwaveinteractive.world;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.co.shockwaveinteractive.util.reference.MainReference;
import uk.co.shockwaveinteractive.world.gen.ModOreGeneration;

@Mod.EventBusSubscriber(modid = MainReference.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModWorldEvents {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void BiomeLoadingEvent(final BiomeLoadingEvent event)
    {
        ModOreGeneration.genOres(event);
    }
}
