package uk.co.shockwaveinteractive.objects.armour;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.init.ItemInit;
import uk.co.shockwaveinteractive.util.interfaces.IHasModel;

public class ArmourBase extends ArmorItem
{

	public ArmourBase(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
		super(materialIn, slot, builderIn);
	}
}

//	public ArmourBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn)
//	{
//		super(materialIn, renderIndexIn, equipmentSlotIn);
//		setTranslationKey(name);
//		setRegistryName(name);
//		setCreativeTab(ShockMetalMain.SHOCKMETALTAB);
//
//		ItemInit.ITEMS.add(this);
//	}
//}
