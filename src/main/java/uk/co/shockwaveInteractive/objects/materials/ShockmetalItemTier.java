package uk.co.shockwaveinteractive.objects.materials;


import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import uk.co.shockwaveinteractive.init.Items;

import java.util.function.Supplier;

public enum ShockmetalItemTier implements Tier {

    // String name, int harvestLevel, int maxUses, float efficiency, float damage, int enchantability
//    (float) ShockMetalConfiguration.materialDamage
    SHOCKMETAL(3, 2500, 8.0f, 5.0f, 16, () -> { return Ingredient.of(Items.SHOCKMETAL_INGOT.get()); });

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> shockmetal_repair_material;

    ShockmetalItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> shockmetal_repair_material)
    {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.shockmetal_repair_material = shockmetal_repair_material;
    }


    @Override
    public int getUses() {
        return this.maxUses;
    }

    @Override
    public float getSpeed() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    @Override
    public int getLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.shockmetal_repair_material.get();
    }
}
