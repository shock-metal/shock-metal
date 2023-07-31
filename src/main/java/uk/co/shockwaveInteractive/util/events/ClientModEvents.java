package uk.co.shockwaveinteractive.util.events;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.co.shockwaveinteractive.util.reference.MainReference;
import uk.co.shockwaveinteractive.util.renderers.SpriteRendererShock;
import uk.co.shockwaveinteractive.util.renderers.VacuumMinecartRenderer;

import static uk.co.shockwaveinteractive.init.Entities.SHOCK_GRENADE_ENTITY;
import static uk.co.shockwaveinteractive.init.Entities.VACUUM_MINECART_ENTITY;

@Mod.EventBusSubscriber(modid = MainReference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    private ClientModEvents() {}

    @SubscribeEvent
    public static void SetUpClient(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(SHOCK_GRENADE_ENTITY.get(), SpriteRendererShock::new);
        event.registerEntityRenderer(VACUUM_MINECART_ENTITY.get(), (context) -> { return new VacuumMinecartRenderer<>(context, ModelLayers.CHEST_MINECART); });
    }
}
