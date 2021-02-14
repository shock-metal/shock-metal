package uk.co.shockwaveinteractive.objects.armour;

import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import uk.co.shockwaveinteractive.init.Items;

public class ArmourBase extends ArmorItem
{
	public ArmourBase(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
		super(materialIn, slot, builderIn);
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (player.inventory.armorInventory.get(3).getItem() == Items.SHOCKMETAL_HELMET.get()
				&& player.inventory.armorInventory.get(2).getItem() == Items.SHOCKMETAL_CHESTPLATE.get()
				&& player.inventory.armorInventory.get(1).getItem() == Items.SHOCKMETAL_LEGGINGS.get()
				&& player.inventory.armorInventory.get(0).getItem() == Items.SHOCKMETAL_BOOTS.get()) {
			try {
				if(stack.getItem() == Items.SHOCKMETAL_CHESTPLATE.get())
				{
					if((player.getActivePotionEffect(Effects.FIRE_RESISTANCE) == null || player.getActivePotionEffect(Effects.FIRE_RESISTANCE).getDuration() < 40)) {
						player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 100, 0, true, false));
					}
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isImmuneToFire() {
		return true;
	}
}
