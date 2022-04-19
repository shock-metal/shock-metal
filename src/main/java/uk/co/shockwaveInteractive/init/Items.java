package uk.co.shockwaveinteractive.init;


import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.config.MainConfig;
import uk.co.shockwaveinteractive.entity.projectile.AbstractGrenadeEntity;
import uk.co.shockwaveinteractive.entity.projectile.ShockGrenadeEntity;
import uk.co.shockwaveinteractive.objects.armour.ArmourBase;
import uk.co.shockwaveinteractive.objects.blocks.item.BlockItemBase;
import uk.co.shockwaveinteractive.objects.items.ItemBase;
import uk.co.shockwaveinteractive.objects.items.ItemRecipeTool;
import uk.co.shockwaveinteractive.objects.items.ItemVacuumMinecart;
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
	public static final RegistryObject<Item> SHOCKMETAL_DUST = REGISTRY_ITEMS.register(ID_SHOCKMETAL_DUST, () -> new ItemBase(new Item.Properties().tab(ShockMetalMain.SHOCKMETALTAB).fireResistant()));
	public static final RegistryObject<Item> NETHERITE_DUST = REGISTRY_ITEMS.register(ID_NETHERITE_DUST, () -> new ItemBase(new Item.Properties().tab(ShockMetalMain.SHOCKMETALTAB).fireResistant()));
	public static final RegistryObject<Item> SHOCKRITE_DUST = REGISTRY_ITEMS.register(ID_SHOCKRITE_DUST, () -> new ItemBase(new Item.Properties().tab(ShockMetalMain.SHOCKMETALTAB).fireResistant()));
	public static final RegistryObject<Item> ORE_GRINDER = REGISTRY_ITEMS.register(ID_ORE_GRINDER, () -> new ItemRecipeTool(MainConfig.oreGrinderUses));
	public static final RegistryObject<Item> FRAME_DIAMOND = REGISTRY_ITEMS.register(ID_FRAME_DIAMOND, ItemBase::new);

//	Projectiles
	public static final RegistryObject<Item> SHOCK_GRENADE_ITEM = REGISTRY_ITEMS.register(ID_SHOCK_GRENADE,
		() -> new ItemGrenadeBase(new ItemGrenadeBase.IGrenadeFactory<AbstractGrenadeEntity>() {

			@Override
			public AbstractGrenadeEntity createGrenade(net.minecraft.world.level.Level level, LivingEntity living) {
				return new ShockGrenadeEntity(level, living);
			}

			@Override
			public AbstractGrenadeEntity createGrenade(net.minecraft.world.level.Level level, double posX, double posY, double posZ) {
				return new ShockGrenadeEntity(level, posX, posY, posZ);
			}


		}, new Item.Properties().tab(ShockMetalMain.SHOCKMETALTAB).stacksTo(16), TRANSLATION_INFO_PREFIX +"shock_grenade"));

	//	Projectiles
	public static final RegistryObject<Item> VACUUM_MINECART_ITEM = REGISTRY_ITEMS.register(ID_VACUUM_MINECART, ItemVacuumMinecart::new);

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
	public static final RegistryObject<ArmorItem> SHOCKMETAL_HELMET = REGISTRY_ITEMS.register(
			ID_SHOCKMETAL_HELMET,
			() -> new ArmourBase(ShockmetalArmorMaterial.SHOCKMETAL,
			EquipmentSlot.HEAD,
			new Item.Properties().tab(ShockMetalMain.SHOCKMETALTAB)));
	public static final RegistryObject<ArmorItem> SHOCKMETAL_CHESTPLATE = REGISTRY_ITEMS.register(
			ID_SHOCKMETAL_CHESTPLATE,
			() -> new ArmourBase(ShockmetalArmorMaterial.SHOCKMETAL,
			EquipmentSlot.CHEST,
			new Item.Properties().tab(ShockMetalMain.SHOCKMETALTAB)));
	public static final RegistryObject<ArmorItem> SHOCKMETAL_LEGGINGS = REGISTRY_ITEMS.register(
			ID_SHOCKMETAL_LEGGINGS,
			() -> new ArmourBase(ShockmetalArmorMaterial.SHOCKMETAL,
			EquipmentSlot.LEGS,
			new Item.Properties().tab(ShockMetalMain.SHOCKMETALTAB)));
	public static final RegistryObject<ArmorItem> SHOCKMETAL_BOOTS = REGISTRY_ITEMS.register(
			ID_SHOCKMETAL_BOOTS,
			() -> new ArmourBase(ShockmetalArmorMaterial.SHOCKMETAL,
			EquipmentSlot.FEET,
			new Item.Properties().tab(ShockMetalMain.SHOCKMETALTAB)));
}
