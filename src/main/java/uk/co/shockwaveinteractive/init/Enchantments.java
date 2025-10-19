package uk.co.shockwaveinteractive.init;


import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import uk.co.shockwaveinteractive.objects.enchantment.AtomRipperEnchantment;
import uk.co.shockwaveinteractive.objects.enchantment.ShockMetalAspectEnchantment;
import uk.co.shockwaveinteractive.util.reference.MainReference;

import static uk.co.shockwaveinteractive.util.reference.IDReference.ID_ATOM_RIPPER_ENCHANT;
import static uk.co.shockwaveinteractive.util.reference.IDReference.ID_SHOCK_METAL_ASPECT_ENCHANT;

public class Enchantments {

    public static final DeferredRegister<Enchantment> REGISTRY_ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MainReference.MODID);

    public static final RegistryObject<Enchantment> ATOM_RIPPER = REGISTRY_ENCHANTMENTS.register(ID_ATOM_RIPPER_ENCHANT, AtomRipperEnchantment::new);
    public static final RegistryObject<Enchantment> SHOCKMETAL_ASPECT = REGISTRY_ENCHANTMENTS.register(ID_SHOCK_METAL_ASPECT_ENCHANT, ShockMetalAspectEnchantment::new);
}
