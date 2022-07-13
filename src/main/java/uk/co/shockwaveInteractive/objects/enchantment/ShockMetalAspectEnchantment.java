package uk.co.shockwaveinteractive.objects.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import uk.co.shockwaveinteractive.ShockMetalMain;

/*
* Pretty much only used for tetra to apply shockmetal effects to tools using metal. if they want thr effect all the tome they can apply the atom ripper enchant
* */
public class ShockMetalAspectEnchantment extends Enchantment {

    public ShockMetalAspectEnchantment() {
        super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 25;
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
        return false;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return false;
    }

    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level) {
        if(((LivingEntity)target).getCreatureAttribute() == CreatureAttribute.UNDEAD && ShockMetalMain.rnd.nextInt(100) < 19)
        {
            target.setFire(5);
            user.addPotionEffect(new EffectInstance(Effects.REGENERATION, 100));
        }
    }
}
