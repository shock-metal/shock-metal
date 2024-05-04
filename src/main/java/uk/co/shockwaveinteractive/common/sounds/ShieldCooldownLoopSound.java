package uk.co.shockwaveinteractive.common.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import uk.co.shockwaveinteractive.init.ModSounds;
import uk.co.shockwaveinteractive.util.InventoryUtilities;

public class ShieldCooldownLoopSound extends AbstractTickableSoundInstance {
    private final Player player;

    public ShieldCooldownLoopSound(Player player, float volume, RandomSource source) {
        super(ModSounds.SHIELD_COOLDOWN_TONE.get(), SoundSource.PLAYERS, source);
        this.player = player;
        this.looping = true;
        this.delay = 0;
        this.volume = volume;
        this.x = (float) player.getX();
        this.y = (float) player.getY();
        this.z = (float) player.getZ();
    }

    public boolean canStartSilent() {
        return true;
    }

    public void tick() {
        ItemStack activeShield = InventoryUtilities.getActiveShieldInInventory(player);
        boolean hasCooldownShield = player.getCooldowns().isOnCooldown(activeShield.getItem());

        if(activeShield.isEmpty() || !hasCooldownShield) {
            this.stop();
        } else {
            this.x = (float) this.player.getX();
            this.y = (float) this.player.getY();
            this.z = (float) this.player.getZ();
        }
    }
}
