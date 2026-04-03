package vnemesis.shockmetal.item.tools;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import vnemesis.shockmetal.item.ShockMetalItemsRegistry;

public enum ShockmetalItemTier implements Tier
{
    SHOCKMETAL(
        BlockTags.NEEDS_DIAMOND_TOOL,
        2500,
        8.0f,
        5.0f,
        16
    );

    private final TagKey<Block> incorrectBlocksForDrops;
    private final int uses;
    private final float speed;
    private final float attackDamageBonus;
    private final int enchantmentValue;

    ShockmetalItemTier(TagKey<Block> incorrectBlocksForDrops, int uses, float speed,
                       float attackDamageBonus, int enchantmentValue)
    {
        this.incorrectBlocksForDrops = incorrectBlocksForDrops;
        this.uses = uses;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.enchantmentValue = enchantmentValue;
    }

    @Override
    public int getUses() { return uses; }

    @Override
    public float getSpeed() { return speed; }

    @Override
    public float getAttackDamageBonus() { return attackDamageBonus; }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() { return incorrectBlocksForDrops; }

    @Override
    public int getEnchantmentValue() { return enchantmentValue; }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(ShockMetalItemsRegistry.SHOCKMETAL_INGOT.get());
    }
}

