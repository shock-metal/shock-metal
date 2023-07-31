package uk.co.shockwaveinteractive.objects.armour;


import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import uk.co.shockwaveinteractive.init.Items;

public class ArmourBase extends ArmorItem
{
	public ArmourBase(ArmorMaterial materialIn, EquipmentSlot slot, Properties builderIn) {
		super(materialIn, slot, builderIn);
	}

	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		if (player.getInventory().armor.get(3).getItem() == Items.SHOCKMETAL_HELMET.get()
				&& player.getInventory().armor.get(2).getItem() == Items.SHOCKMETAL_CHESTPLATE.get()
				&& player.getInventory().armor.get(1).getItem() == Items.SHOCKMETAL_LEGGINGS.get()
				&& player.getInventory().armor.get(0).getItem() == Items.SHOCKMETAL_BOOTS.get()) {
			try {
				if(stack.getItem() == Items.SHOCKMETAL_CHESTPLATE.get())
				{
					if((!player.getActiveEffects().contains(MobEffects.FIRE_RESISTANCE) || player.getEffect(MobEffects.FIRE_RESISTANCE).getDuration() < 40)) {
						player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 1, true, false));
					}
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isFireResistant() {
		return true;
	}
}
