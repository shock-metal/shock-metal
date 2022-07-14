package uk.co.shockwaveinteractive.objects.armour;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import uk.co.shockwaveinteractive.config.MainConfig;
import uk.co.shockwaveinteractive.init.Items;

import javax.annotation.Nullable;
import java.util.List;

public class ShockMetalArmour extends ArmourBase {

    private int powerLevel = 0;
    private int maxPowerLevel = 5;

    public ShockMetalArmour(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
        super(materialIn, slot, builderIn);
    }

    public static boolean isWearingFullShockMentalSuit(PlayerEntity player)
    {
        return player.inventory.armorInventory.get(3).getItem() == Items.SHOCKMETAL_HELMET.get()
                && player.inventory.armorInventory.get(2).getItem() == Items.SHOCKMETAL_CHESTPLATE.get()
                && player.inventory.armorInventory.get(1).getItem() == Items.SHOCKMETAL_LEGGINGS.get()
                && player.inventory.armorInventory.get(0).getItem() == Items.SHOCKMETAL_BOOTS.get();
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public void increasePowerLevelByOne() {
        if(powerLevel < maxPowerLevel)
        {
            powerLevel += 1;
        }
    }

    public void resetPowerLevel()
    {
        powerLevel = 0;
    }

    public boolean canUseArmourBurst()
    {
        return powerLevel >= 3;
    }

    public int getMultiplier() {
        if(powerLevel == 5) {
            return 2;
        } else {
            return 1;
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        try {
            if(stack.getItem() == Items.SHOCKMETAL_CHESTPLATE.get())
            {
                tooltip.add(new StringTextComponent(String.format("Charge: %s/%s (%sx)", powerLevel, maxPowerLevel, getMultiplier())).mergeStyle(TextFormatting.DARK_PURPLE));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return stack.isEnchanted() || powerLevel >= 3;
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (isWearingFullShockMentalSuit(player)) {
            try {
                if(stack.getItem() == Items.SHOCKMETAL_CHESTPLATE.get())
                {
                    if((player.getActivePotionEffect(Effects.FIRE_RESISTANCE) == null || player.getActivePotionEffect(Effects.FIRE_RESISTANCE).getDuration() < 40)) {
                        player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 100, 0, true, false));
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isImmuneToFire() {
        return true;
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }
}
