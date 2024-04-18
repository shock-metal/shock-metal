package uk.co.shockwaveinteractive.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import uk.co.shockwaveinteractive.util.reference.MainReference;

public class Sounds {
    public static final DeferredRegister<SoundEvent> REGISTRY_SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MainReference.MODID);
    public static final ResourceLocation location = new ResourceLocation(MainReference.MODID);

    public static final RegistryObject<SoundEvent> SHIELD_COOLDOWN_APPLIED = registerSoundEvent("shield_cooldown_applied");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return REGISTRY_SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(MainReference.MODID, name)));
    }
}
