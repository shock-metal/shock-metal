package vnemesis.shockmetal.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import vnemesis.shockmetal.util.Helpers;

import java.util.List;

public abstract class ItemEnergyBase extends Item
{
    public ItemEnergyBase() {
        super(new Properties()
                .stacksTo(1)
        );
    }

    public ItemEnergyBase(Properties props) {
        super(props);
    }

    public abstract int getEnergyMax();

    public abstract int getEnergyCost();

    public void setEnergy(ItemStack stack, int amount) {
        var energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);

        if(energy == null)
            return;

        int energyStored = energy.getEnergyStored();
        if(amount > energyStored)
        {
            energy.receiveEnergy(Math.abs(amount - energyStored), false);
        } else {
            energy.extractEnergy(Math.abs(energyStored - amount), false);
        }
    }

    public static int getEnergyStored(ItemStack stack) {
        var energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);

        if(energy == null)
            return 0;

        return energy.getEnergyStored();
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return getEnergyMax();
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        var energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energy == null) return super.isBarVisible(stack);
        return energy.getEnergyStored() < energy.getMaxEnergyStored();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        IEnergyStorage cap = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (cap == null) return super.getBarWidth(stack);
        if (cap.getMaxEnergyStored() == 0) return 0;
        return Math.min(13 * cap.getEnergyStored() / cap.getMaxEnergyStored(), 13);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        IEnergyStorage cap = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (cap == null) return super.getBarColor(stack);
        float ratio = cap.getMaxEnergyStored() == 0 ? 0f
                : (float) cap.getEnergyStored() / cap.getMaxEnergyStored();
        return Mth.hsvToRgb(Math.max(0.0F, ratio) / 3.0F, 1.0F, 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        Minecraft mc = Minecraft.getInstance();

        if (context.level() == null || mc.player == null) {
            return;
        }

        boolean sneakPressed = Screen.hasShiftDown();

        if (!sneakPressed) {
            tooltipComponents.add(Component.translatable("shockmetal.tooltip.shift-info").withStyle(ChatFormatting.GRAY));
        }

        IEnergyStorage energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energy != null) {
            MutableComponent energyText = !sneakPressed
                    ? Component.translatable("shockmetal.tooltip.energy",
                            Helpers.condenseValue(energy.getEnergyStored()),
                            Helpers.condenseValue(energy.getMaxEnergyStored()))
                    : Component.translatable("shockmetal.tooltip.energy",
                            String.format("%,d", energy.getEnergyStored()),
                            String.format("%,d", energy.getMaxEnergyStored()));
            tooltipComponents.add(energyText.withStyle(ChatFormatting.GREEN));
        }
    }
}
