package vnemesis.shockmetal.item.armour;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import vnemesis.shockmetal.item.ShockMetalItemsRegistry;
import vnemesis.shockmetal.reference.ModIdReference;

import java.util.List;
import java.util.Map;

public class ShockMetalArmorMaterials
{
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
        DeferredRegister.create(Registries.ARMOR_MATERIAL, ModIdReference.SHOCKMETAL_MOD_ID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SHOCKMETAL = ARMOR_MATERIALS.register(
        "shockmetal",
        () -> new ArmorMaterial(
            Map.of(
                ArmorItem.Type.BOOTS,      6,
                ArmorItem.Type.LEGGINGS,  16,
                ArmorItem.Type.CHESTPLATE,10,
                ArmorItem.Type.HELMET,     5,
                ArmorItem.Type.BODY,      10
            ),
            16,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            () -> Ingredient.of(ShockMetalItemsRegistry.SHOCKMETAL_INGOT.get()),
            List.of(new ArmorMaterial.Layer(
                ResourceLocation.fromNamespaceAndPath(ModIdReference.SHOCKMETAL_MOD_ID, "shockmetal")
            )),
            3.0f,
            1.0f
        )
    );

    public static void register(IEventBus eventBus) {
        ARMOR_MATERIALS.register(eventBus);
    }
}


