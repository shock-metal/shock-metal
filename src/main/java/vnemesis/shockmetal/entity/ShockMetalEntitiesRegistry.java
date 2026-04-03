package vnemesis.shockmetal.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import vnemesis.shockmetal.entity.projectile.ShockGrenadeEntity;
import vnemesis.shockmetal.entity.transport.VacuumMinecartEntity;
import vnemesis.shockmetal.reference.EntityIdReference;
import vnemesis.shockmetal.reference.ModIdReference;

public class ShockMetalEntitiesRegistry
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
        DeferredRegister.create(net.minecraft.core.registries.Registries.ENTITY_TYPE,
            ModIdReference.SHOCKMETAL_MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<ShockGrenadeEntity>> SHOCK_GRENADE_ENTITY =
        ENTITY_TYPES.register(EntityIdReference.ID_SHOCK_GRENADE_ENTITY,
            () -> EntityType.Builder.<ShockGrenadeEntity>of(ShockGrenadeEntity::new, MobCategory.MISC)
                .sized(0.25f, 0.25f)
                .build(EntityIdReference.ID_SHOCK_GRENADE_ENTITY));

    public static final DeferredHolder<EntityType<?>, EntityType<VacuumMinecartEntity>> VACUUM_MINECART_ENTITY =
        ENTITY_TYPES.register(EntityIdReference.ID_VACUUM_MINECART_ENTITY,
            () -> EntityType.Builder.<VacuumMinecartEntity>of(VacuumMinecartEntity::new, MobCategory.MISC)
                .sized(0.98f, 0.7f)
                .build(EntityIdReference.ID_VACUUM_MINECART_ENTITY));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}

