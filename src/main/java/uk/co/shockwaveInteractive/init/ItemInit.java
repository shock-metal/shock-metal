package uk.co.shockwaveinteractive.init;


import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.objects.blocks.item.BlockItemBase;
import uk.co.shockwaveinteractive.objects.items.ItemBase;
import uk.co.shockwaveinteractive.objects.materials.ShockmetalArmorMaterial;
import uk.co.shockwaveinteractive.objects.tools.*;
import uk.co.shockwaveinteractive.util.Reference;

//import uk.co.shockwaveinteractive.objects.items.ItemShockMetalBook;


/*
 * Initialises Items
 * 
 * @author HarmanU / vNemesis_HD
 */
public class ItemInit
{

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MODID);

//	public static final ArmorMaterial ARMOUR_SHOCKMETAL = EnumHelper.addArmorMaterial("armour_shockmetal", Reference.MODID + ":shockmetal", 45, new int[]{5, 10, 16, 6}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.5f);
	
	//---------------------------- Items ----------------------------
	public static final RegistryObject<Item> SHOCKMETAL_INGOT = ITEMS.register("shockmetal_ingot", () -> new ItemBase(true));
	public static final RegistryObject<Item> SHOCKMETAL_DUST = ITEMS.register("shockmetal_dust", () -> new ItemBase(true));
	//	public static final RegistryObject<Item> BOOK_SHOCKMETALMANUAL = ITEMS.register("book_shockmetalmanual", ItemShockMetalBook::new);
	public static final RegistryObject<Item> FRAME_DIAMOND = ITEMS.register("frame_diamond", () -> new ItemBase(true));

	//---------------------------- Block Items ----------------------------
	public static final RegistryObject<Item> SHOCKMETAL_BLOCK_ITEM = ITEMS.register("shockmetal_block", () -> new BlockItemBase(BlockInit.SHOCKMETAL_BLOCK.get()));
	public static final RegistryObject<Item> SHOCKMETAL_NETHER_ORE_BLOCK_ITEM = ITEMS.register("shockmetal_nether_ore_block", () -> new BlockItemBase(BlockInit.SHOCKMETAL_NETHER_ORE_BLOCK.get()));
	
	//---------------------------- Tools ----------------------------
	public static final RegistryObject<SwordItem> SHOCKMETAL_SWORD = ITEMS.register("shockmetal_sword", ShockmetalToolSword::new);
	public static final RegistryObject<PickaxeItem> SHOCKMETAL_PICKAXE = ITEMS.register("shockmetal_pickaxe", ShockmetalToolPickaxe::new);
	public static final RegistryObject<AxeItem> SHOCKMETAL_AXE = ITEMS.register("shockmetal_axe", ShockmetalToolAxe::new);
	public static final RegistryObject<ShovelItem> SHOCKMETAL_SHOVEL = ITEMS.register("shockmetal_shovel", ShockmetalToolShovel::new);
	public static final RegistryObject<HoeItem> SHOCKMETAL_HOE = ITEMS.register("shockmetal_hoe", ShockmetalToolHoe::new);
	
	//---------------------------- Armour ----------------------------
	public static final RegistryObject<ArmorItem> SHOCKMETAL_HELMET = ITEMS.register("shockmetal_helmet", () -> new ArmorItem(ShockmetalArmorMaterial.SHOCKMETAL, EquipmentSlotType.HEAD, new Item.Properties().group(ShockMetalMain.SHOCKMETALTAB)));
	public static final RegistryObject<ArmorItem> SHOCKMETAL_CHESTPLATE = ITEMS.register("shockmetal_chestplate", () -> new ArmorItem(ShockmetalArmorMaterial.SHOCKMETAL, EquipmentSlotType.CHEST, new Item.Properties().group(ShockMetalMain.SHOCKMETALTAB)));
	public static final RegistryObject<ArmorItem> SHOCKMETAL_LEGGINGS = ITEMS.register("shockmetal_leggings", () -> new ArmorItem(ShockmetalArmorMaterial.SHOCKMETAL, EquipmentSlotType.LEGS, new Item.Properties().group(ShockMetalMain.SHOCKMETALTAB)));
	public static final RegistryObject<ArmorItem> SHOCKMETAL_BOOTS = ITEMS.register("shockmetal_boots", () -> new ArmorItem(ShockmetalArmorMaterial.SHOCKMETAL, EquipmentSlotType.FEET, new Item.Properties().group(ShockMetalMain.SHOCKMETALTAB)));
}
