package uk.co.shockwaveInteractive.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import uk.co.shockwaveInteractive.objects.armour.ArmourBase;
import uk.co.shockwaveInteractive.objects.items.ItemBase;
import uk.co.shockwaveInteractive.objects.items.ItemShockMetalBook;
import uk.co.shockwaveInteractive.objects.tools.ToolAxe;
import uk.co.shockwaveInteractive.objects.tools.ToolHoe;
import uk.co.shockwaveInteractive.objects.tools.ToolPickaxe;
import uk.co.shockwaveInteractive.objects.tools.ToolShovel;
import uk.co.shockwaveInteractive.objects.tools.ToolSword;
import uk.co.shockwaveInteractive.util.Reference;
import uk.co.shockwaveInteractive.util.config.ShockMetalConfiguration;

/*
 * Initialises Items
 * 
 * @author HarmanU / vNemesis_HD
 */
public class ItemInit
{

	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	
	//---------------------------- Materials ----------------------------
																				// String name, int harvestLevel, int maxUses, float efficiency, float damage, int enchantability
	public static final ToolMaterial TOOL_SHOCKMETAL = EnumHelper.addToolMaterial("tool_shockmetal", 5, 1600, 10.0f, (float) ShockMetalConfiguration.materialDamage, 10);
	public static final ArmorMaterial ARMOUR_SHOCKMETAL = EnumHelper.addArmorMaterial("armour_shockmetal", Reference.MODID + ":shockmetal", 45, new int[]{5, 10, 16, 6}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.5f);
	
	//---------------------------- Items ----------------------------
	public static final Item INGOT_SHOCKMETAL = new ItemBase("ingot_shockmetal", true);
	public static final Item DUST_SHOCKMETAL = new ItemBase("dust_shockmetal", true);
	public static final Item BOOK_SHOCKMETALMANUAL = new ItemShockMetalBook("book_shockmetalmanual");
	public static final Item FRAME_DIAMOND = new ItemBase("frame_diamond", false);
	
	//---------------------------- Tools ----------------------------
	public static final Item AXE_SHOCKMETAL = new ToolAxe("axe_shockmetal", TOOL_SHOCKMETAL);
	public static final Item HOE_SHOCKMETAL = new ToolHoe("hoe_shockmetal", TOOL_SHOCKMETAL);
	public static final Item PICKAXE_SHOCKMETAL = new ToolPickaxe("pickaxe_shockmetal", TOOL_SHOCKMETAL);
	public static final Item SHOVEL_SHOCKMETAL = new ToolShovel("shovel_shockmetal", TOOL_SHOCKMETAL);
	public static final Item SWORD_SHOCKMETAL = new ToolSword("sword_shockmetal", TOOL_SHOCKMETAL);
	
	//---------------------------- Armour ----------------------------
	public static final Item HELMET_SHOCKMETAL = new ArmourBase("helmet_shockmetal", ARMOUR_SHOCKMETAL, 1, EntityEquipmentSlot.HEAD);
	public static final Item CHESTPLATE_SHOCKMETAL = new ArmourBase("chestplate_shockmetal", ARMOUR_SHOCKMETAL, 1, EntityEquipmentSlot.CHEST);
	public static final Item LEGGINGS_SHOCKMETAL = new ArmourBase("leggings_shockmetal", ARMOUR_SHOCKMETAL, 2, EntityEquipmentSlot.LEGS);
	public static final Item BOOTS_SHOCKMETAL = new ArmourBase("boots_shockmetal", ARMOUR_SHOCKMETAL, 1, EntityEquipmentSlot.FEET);
}
