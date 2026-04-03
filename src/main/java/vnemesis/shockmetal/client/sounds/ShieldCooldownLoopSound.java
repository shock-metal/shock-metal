package vnemesis.shockmetal.client.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import vnemesis.shockmetal.sounds.ShockMetalSoundsRegistry;
import vnemesis.shockmetal.util.InventoryUtilities;

@OnlyIn(Dist.CLIENT)
public class ShieldCooldownLoopSound extends AbstractTickableSoundInstance {

    private final Player player;

    public ShieldCooldownLoopSound(Player player, float volume, RandomSource source) {
        super(ShockMetalSoundsRegistry.SHIELD_COOLDOWN_TONE.get(), SoundSource.PLAYERS, source);
        this.player = player;
        this.looping = true;
        this.delay = 0;
        this.volume = volume;
        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public void tick() {
        ItemStack activeShield = InventoryUtilities.getActiveShieldInInventory(player);
        boolean hasCooldownShield = !activeShield.isEmpty() && player.getCooldowns().isOnCooldown(activeShield.getItem());

        if (activeShield.isEmpty() || !hasCooldownShield) {
            this.stop();
        } else {
            this.x = this.player.getX();
            this.y = this.player.getY();
            this.z = this.player.getZ();
        }
    }
}

