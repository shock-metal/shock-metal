package uk.co.shockwaveinteractive.objects.enchantment;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import uk.co.shockwaveinteractive.config.MainConfig;

public class AtomRipperEnchantment extends Enchantment {

    public AtomRipperEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }

    @Override
    public boolean isDiscoverable() {
        return !MainConfig.disableAtomRipper;
    }

    @Override
    public void doPostHurt(LivingEntity user, Entity target, int level) {
        if(((LivingEntity)target).getMobType() == MobType.UNDEAD)
        {
            target.setSecondsOnFire(5);
            user.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1));
        }
    }
}
