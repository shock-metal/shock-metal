package vnemesis.shockmetal.item.armour;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ArmourBase extends ArmorItem
{
    public ArmourBase(ArmorItem.Type type) {
        super(
            ShockMetalArmorMaterials.SHOCKMETAL,
            type,
            new ArmorItem.Properties().fireResistant()
        );
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (!world.isClientSide() && entity instanceof Player player) {
            boolean fullSet =
                player.getInventory().armor.get(3).getItem() instanceof ArmourBase  // helmet
                && player.getInventory().armor.get(2).getItem() instanceof ArmourBase  // chestplate
                && player.getInventory().armor.get(1).getItem() instanceof ArmourBase  // leggings
                && player.getInventory().armor.get(0).getItem() instanceof ArmourBase; // boots

            // Apply fire resistance when wearing the full set — triggered by chestplate slot tick
            if (fullSet && this.getType() == ArmorItem.Type.CHESTPLATE) {
                var effect = player.getEffect(MobEffects.FIRE_RESISTANCE);
                if (effect == null || effect.getDuration() < 40) {
                    player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 1, true, false));
                }
            }
        }
    }
}


