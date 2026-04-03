package vnemesis.shockmetal.sounds;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import vnemesis.shockmetal.reference.ModIdReference;

public class ShockMetalSoundsRegistry {

    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, ModIdReference.SHOCKMETAL_MOD_ID);

    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }

    public static final DeferredHolder<SoundEvent, SoundEvent> SHIELD_BREAK =
            registerSoundEvent("shield_break");

    public static final DeferredHolder<SoundEvent, SoundEvent> SHIELD_COOLDOWN_TONE =
            registerSoundEvent("shield_cooldown_tone");

    public static final DeferredHolder<SoundEvent, SoundEvent> SHIELD_RECHARGE =
            registerSoundEvent("shield_recharge");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(
                ResourceLocation.fromNamespaceAndPath(ModIdReference.SHOCKMETAL_MOD_ID, name)));
    }
}

