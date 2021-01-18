package uk.co.shockwaveinteractive.init;


import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.entity.projectile.AbstractGrenadeEntity;
import uk.co.shockwaveinteractive.entity.projectile.ShockGrenadeEntity;
import uk.co.shockwaveinteractive.objects.blocks.item.BlockItemBase;
import uk.co.shockwaveinteractive.objects.items.ItemBase;
import uk.co.shockwaveinteractive.objects.items.ItemRecipeTool;
import uk.co.shockwaveinteractive.objects.items.resources.ItemIngotShockmetal;
import uk.co.shockwaveinteractive.objects.items.throwables.ItemGrenadeBase;
import uk.co.shockwaveinteractive.objects.materials.ShockmetalArmorMaterial;
import uk.co.shockwaveinteractive.objects.tools.*;
import uk.co.shockwaveinteractive.util.reference.MainReference;

import static uk.co.shockwaveinteractive.util.reference.IDReference.*;
import static uk.co.shockwaveinteractive.util.reference.MainReference.TRANSLATION_INFO_PREFIX;

/*
 * Initialises Items
 * 
 * @author HarmanU / vNemesis_HD
 */
public class Items
{
	public static final DeferredRegister<Item> REGISTRY_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MainReference.MODID);

	//---------------------------- Items ----------------------------
	public static final RegistryObject<Item> SHOCKMETAL_INGOT = REGISTRY_ITEMS.register(ID_SHOCKMETAL_INGOT, ItemIngotShockmetal::new);
	public static final RegistryObject<Item> SHOCKMETAL_DUST = REGISTRY_ITEMS.register(ID_SHOCKMETAL_DUST, ItemBase::new);
	public static final RegistryObject<Item> NETHERITE_DUST = REGISTRY_ITEMS.register(ID_NETHERITE_DUST, ItemBase::new);
	public static final RegistryObject<Item> SHOCKRITE_DUST = REGISTRY_ITEMS.register(ID_SHOCKRITE_DUST, ItemBase::new);
	public static final RegistryObject<Item> ORE_GRINDER = REGISTRY_ITEMS.register(ID_ORE_GRINDER, ItemRecipeTool::new);
	public static final RegistryObject<Item> FRAME_DIAMOND = REGISTRY_ITEMS.register(ID_FRAME_DIAMOND, ItemBase::new);

//	Projectiles
	public static final RegistryObject<Item> SHOCK_GRENADE_ITEM = REGISTRY_ITEMS.register(ID_SHOCK_GRENADE,
		() -> new ItemGrenadeBase(new ItemGrenadeBase.IGrenadeFactory<AbstractGrenadeEntity>() {

			@Override
			public AbstractGrenadeEntity createGrenade(World world, LivingEntity living) {

				return new ShockGrenadeEntity(world, living);
			}

			@Override
			public AbstractGrenadeEntity createGrenade(World world, double posX, double posY, double posZ) {

				return new ShockGrenadeEntity(world, posX, posY, posZ);
			}


		}, new Item.Properties().group(ShockMetalMain.SHOCKMETALTAB).maxStackSize(16), TRANSLATION_INFO_PREFIX +"shock_grenade"));

	//---------------------------- Block Items ----------------------------
	public static final RegistryObject<Item> SHOCKMETAL_BLOCK_ITEM = REGISTRY_ITEMS.register(ID_SHOCKMETAL_BLOCK_ITEM, () -> new BlockItemBase(Blocks.SHOCKMETAL_BLOCK.get()));
	public static final RegistryObject<Item> SHOCKMETAL_NETHER_ORE_BLOCK_ITEM = REGISTRY_ITEMS.register(ID_SHOCKMETAL_NETHER_ORE_BLOCK_ITEM, () -> new BlockItemBase(Blocks.SHOCKMETAL_NETHER_ORE_BLOCK.get()));
	
	//---------------------------- Tools ----------------------------
	public static final RegistryObject<SwordItem> SHOCKMETAL_SWORD = REGISTRY_ITEMS.register(ID_SHOCKMETAL_SWORD, ShockmetalToolSword::new);
	public static final RegistryObject<PickaxeItem> SHOCKMETAL_PICKAXE = REGISTRY_ITEMS.register(ID_SHOCKMETAL_PICKAXE, ShockmetalToolPickaxe::new);
	public static final RegistryObject<AxeItem> SHOCKMETAL_AXE = REGISTRY_ITEMS.register(ID_SHOCKMETAL_AXE, ShockmetalToolAxe::new);
	public static final RegistryObject<ShovelItem> SHOCKMETAL_SHOVEL = REGISTRY_ITEMS.register(ID_SHOCKMETAL_SHOVEL, ShockmetalToolShovel::new);
	public static final RegistryObject<HoeItem> SHOCKMETAL_HOE = REGISTRY_ITEMS.register(ID_SHOCKMETAL_HOE, ShockmetalToolHoe::new);
	
	//---------------------------- Armour ----------------------------
	public static final RegistryObject<ArmorItem> SHOCKMETAL_HELMET = REGISTRY_ITEMS.register(ID_SHOCKMETAL_HELMET, () -> new ArmorItem(ShockmetalArmorMaterial.SHOCKMETAL, EquipmentSlotType.HEAD, new Item.Properties().group(ShockMetalMain.SHOCKMETALTAB)));
	public static final RegistryObject<ArmorItem> SHOCKMETAL_CHESTPLATE = REGISTRY_ITEMS.register(ID_SHOCKMETAL_CHESTPLATE, () -> new ArmorItem(ShockmetalArmorMaterial.SHOCKMETAL, EquipmentSlotType.CHEST, new Item.Properties().group(ShockMetalMain.SHOCKMETALTAB)));
	public static final RegistryObject<ArmorItem> SHOCKMETAL_LEGGINGS = REGISTRY_ITEMS.register(ID_SHOCKMETAL_LEGGINGS, () -> new ArmorItem(ShockmetalArmorMaterial.SHOCKMETAL, EquipmentSlotType.LEGS, new Item.Properties().group(ShockMetalMain.SHOCKMETALTAB)));
	public static final RegistryObject<ArmorItem> SHOCKMETAL_BOOTS = REGISTRY_ITEMS.register(ID_SHOCKMETAL_BOOTS, () -> new ArmorItem(ShockmetalArmorMaterial.SHOCKMETAL, EquipmentSlotType.FEET, new Item.Properties().group(ShockMetalMain.SHOCKMETALTAB)));
}
