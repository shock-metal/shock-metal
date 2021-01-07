package uk.co.shockwaveinteractive.init;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import uk.co.shockwaveinteractive.entity.projectile.ShockGrenadeEntity;
import uk.co.shockwaveinteractive.util.reference.MainReference;

import static uk.co.shockwaveinteractive.util.reference.IDReference.ID_SHOCK_GRENADE_ENTITY;

public class Entities {

    public static final DeferredRegister<EntityType<?>> REGISTRY_ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MainReference.MODID);

    public static final RegistryObject<EntityType<ShockGrenadeEntity>> SHOCK_GRENADE_ENTITY = REGISTRY_ENTITIES.register(ID_SHOCK_GRENADE_ENTITY,
            () -> EntityType.Builder.<ShockGrenadeEntity>create(ShockGrenadeEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).build(ID_SHOCK_GRENADE_ENTITY));
}
