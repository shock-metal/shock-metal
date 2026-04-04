package vnemesis.shockmetal.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import vnemesis.shockmetal.item.shield.ItemShieldModule;

public class DamageEventHandler {

    @SubscribeEvent
    public void onEntityHurt(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();

        if (event.getNewDamage() <= 0 || !entity.isAlive()) {
            return;
        }

        if (entity instanceof ServerPlayer player) {
            float ratioAbsorbed = ItemShieldModule.getDamageAbsorbed(
                    player, event.getSource(), event.getNewDamage());

            if (ratioAbsorbed > 0) {
                float damageRemaining = event.getNewDamage() * Math.max(0f, 1f - ratioAbsorbed);
                event.setNewDamage(damageRemaining);
            }
        }
    }
}


