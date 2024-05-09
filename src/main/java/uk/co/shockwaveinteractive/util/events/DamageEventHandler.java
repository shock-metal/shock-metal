package uk.co.shockwaveinteractive.util.events;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import uk.co.shockwaveinteractive.objects.items.energytools.ItemShieldModule;

public class DamageEventHandler {

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (event.getAmount() <= 0 || !entity.isAlive()) {
            return;
        }

        if (event.getEntity() instanceof ServerPlayer player) {
            float ratioAbsorbed = ItemShieldModule.getDamageAbsorbed(player, event.getSource(), event.getAmount());

            if (ratioAbsorbed > 0) {
                float damageRemaining = event.getAmount() * Math.max(0, 1 - ratioAbsorbed);
                if (damageRemaining <= 0) {
                    event.setCanceled(true);
                } else {
                    event.setAmount(damageRemaining);
                }
            }
        }
    }
}

