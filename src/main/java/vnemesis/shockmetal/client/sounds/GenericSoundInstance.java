package vnemesis.shockmetal.client.sounds;

import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GenericSoundInstance extends AbstractSoundInstance {

    public GenericSoundInstance(Player player, RandomSource source, SoundEvent event) {
        super(event, SoundSource.PLAYERS, source);
        this.looping = false;
        this.delay = 0;
        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }
}

