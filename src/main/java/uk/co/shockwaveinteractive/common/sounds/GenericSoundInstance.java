package uk.co.shockwaveinteractive.common.sounds;

import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;

public class GenericSoundInstance extends AbstractSoundInstance {

    public GenericSoundInstance(Player player, RandomSource source, SoundEvent event) {
        super(event, SoundSource.PLAYERS, source);
        this.looping = false;
        this.delay = 0;
        this.x = (float) player.getX();
        this.y = (float) player.getY();
        this.z = (float) player.getZ();
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }
}
