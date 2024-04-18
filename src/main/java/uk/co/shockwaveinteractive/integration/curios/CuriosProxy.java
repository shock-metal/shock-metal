package uk.co.shockwaveinteractive.integration.curios;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.items.IItemHandlerModifiable;
import uk.co.shockwaveinteractive.ShockMetalMain;

public class CuriosProxy {

    private static CuriosProxy instance;

    public static void register() {

        if (ShockMetalMain.curiosLoaded) {
            instance = new CuriosIntegration();
            FMLJavaModLoadingContext.get().getModEventBus().addListener(CuriosIntegration::sendImc);
        } else {
            instance = new CuriosProxy();
        }
    }

    public static LazyOptional<IItemHandlerModifiable> getAllWorn(LivingEntity living) {

        return instance.getAllWornItems(living);
    }

    protected CuriosProxy() {

    }

    public LazyOptional<IItemHandlerModifiable> getAllWornItems(LivingEntity living) {

        return LazyOptional.empty();
    }

}
