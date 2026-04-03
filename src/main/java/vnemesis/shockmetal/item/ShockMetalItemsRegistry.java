package vnemesis.shockmetal.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import vnemesis.shockmetal.entity.projectile.ShockGrenadeEntity;
import vnemesis.shockmetal.item.armour.ArmourBase;
import vnemesis.shockmetal.item.throwables.ItemGrenadeBase;
import vnemesis.shockmetal.item.tools.*;
import vnemesis.shockmetal.reference.ModIdReference;

import static vnemesis.shockmetal.reference.ItemIdReference.*;

public class ShockMetalItemsRegistry
{
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ModIdReference.SHOCKMETAL_MOD_ID);

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }

    //---------------------------- Resources ----------------------------
    public static final DeferredItem<Item> SHOCKMETAL_INGOT = ITEMS.register(ID_SHOCKMETAL_INGOT, () -> new ItemBase(new Item.Properties().fireResistant()));
    public static final DeferredItem<Item> SHOCKMETAL_DUST  = ITEMS.register(ID_SHOCKMETAL_DUST,  () -> new ItemBase(new Item.Properties().fireResistant()));
    public static final DeferredItem<Item> NETHERITE_DUST   = ITEMS.register(ID_NETHERITE_DUST,   () -> new ItemBase(new Item.Properties().fireResistant()));
    public static final DeferredItem<Item> SHOCKRITE_DUST   = ITEMS.register(ID_SHOCKRITE_DUST,   () -> new ItemBase(new Item.Properties().fireResistant()));
    public static final DeferredItem<Item> ORE_GRINDER      = ITEMS.register(ID_ORE_GRINDER,      () -> new ItemRecipeTool(50));

    //---------------------------- Projectiles ----------------------------
    public static final DeferredItem<Item> SHOCK_GRENADE_ITEM = ITEMS.register(ID_SHOCK_GRENADE,
        () -> new ItemGrenadeBase(
            new ItemGrenadeBase.IGrenadeFactory<ShockGrenadeEntity>() {
                @Override
                public ShockGrenadeEntity createGrenade(net.minecraft.world.level.Level level,
                                                        net.minecraft.world.entity.LivingEntity thrower) {
                    return new ShockGrenadeEntity(level, thrower);
                }
                @Override
                public ShockGrenadeEntity createGrenade(net.minecraft.world.level.Level level,
                                                        double x, double y, double z) {
                    return new ShockGrenadeEntity(level, x, y, z);
                }
            },
            new Item.Properties().stacksTo(16),
            "info.shockmetal.gui.shock_grenade"
        )
    );

    public static final DeferredItem<Item> VACUUM_MINECART_ITEM = ITEMS.register(ID_VACUUM_MINECART, ItemVacuumMinecart::new);

    //---------------------------- Tools ----------------------------
    public static final DeferredItem<Item> SHOCKMETAL_SWORD   = ITEMS.register(ID_SHOCKMETAL_SWORD,   ShockmetalToolSword::new);
    public static final DeferredItem<Item> SHOCKMETAL_PICKAXE = ITEMS.register(ID_SHOCKMETAL_PICKAXE, ShockmetalToolPickaxe::new);
    public static final DeferredItem<Item> SHOCKMETAL_AXE     = ITEMS.register(ID_SHOCKMETAL_AXE,     ShockmetalToolAxe::new);
    public static final DeferredItem<Item> SHOCKMETAL_SHOVEL  = ITEMS.register(ID_SHOCKMETAL_SHOVEL,  ShockmetalToolShovel::new);
    public static final DeferredItem<Item> SHOCKMETAL_HOE     = ITEMS.register(ID_SHOCKMETAL_HOE,     ShockmetalToolHoe::new);

    //---------------------------- Armour ----------------------------
    public static final DeferredItem<Item> SHOCKMETAL_HELMET     = ITEMS.register(ID_SHOCKMETAL_HELMET,     () -> new ArmourBase(ArmorItem.Type.HELMET));
    public static final DeferredItem<Item> SHOCKMETAL_CHESTPLATE = ITEMS.register(ID_SHOCKMETAL_CHESTPLATE, () -> new ArmourBase(ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<Item> SHOCKMETAL_LEGGINGS   = ITEMS.register(ID_SHOCKMETAL_LEGGINGS,   () -> new ArmourBase(ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<Item> SHOCKMETAL_BOOTS      = ITEMS.register(ID_SHOCKMETAL_BOOTS,      () -> new ArmourBase(ArmorItem.Type.BOOTS));
}
