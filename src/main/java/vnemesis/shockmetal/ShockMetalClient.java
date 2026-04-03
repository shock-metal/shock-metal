package vnemesis.shockmetal;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import vnemesis.shockmetal.client.renderer.SpriteRendererShock;
import vnemesis.shockmetal.client.renderer.VacuumMinecartRenderer;
import vnemesis.shockmetal.entity.ShockMetalEntitiesRegistry;

import static vnemesis.shockmetal.reference.ModIdReference.SHOCKMETAL_MOD_ID;

@Mod(value = SHOCKMETAL_MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = SHOCKMETAL_MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ShockMetalClient {
    public ShockMetalClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        ShockMetal.LOGGER.info("HELLO FROM CLIENT SETUP");
        ShockMetal.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ShockMetalEntitiesRegistry.SHOCK_GRENADE_ENTITY.get(),
            SpriteRendererShock::new);
        event.registerEntityRenderer(ShockMetalEntitiesRegistry.VACUUM_MINECART_ENTITY.get(),
            ctx -> new VacuumMinecartRenderer<>(ctx, ModelLayers.CHEST_MINECART));
    }
}
