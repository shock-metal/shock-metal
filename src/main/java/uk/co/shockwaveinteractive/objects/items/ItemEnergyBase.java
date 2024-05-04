package uk.co.shockwaveinteractive.objects.items;

import com.ibm.icu.impl.Pair;
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
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.objects.capabilities.EnergyCapabilityProvider;
import uk.co.shockwaveinteractive.objects.capabilities.ItemEnergy;
import uk.co.shockwaveinteractive.util.Helpers;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ItemEnergyBase extends Item {
    public ItemEnergyBase() {
        super(new Properties()
                .stacksTo(1)
                .tab(ShockMetalMain.SHOCKMETALTAB)
        );
    }

    public ItemEnergyBase(Properties props) {
        super(props);
    }

    public abstract int getEnergyMax();

    public abstract int getEnergyCost();

    public void setEnergy(ItemStack stack, int amount) {
        var energy = stack.getCapability(ForgeCapabilities.ENERGY);

        if(!energy.isPresent())
            return;

        int energyStored = energy.map(IEnergyStorage::getEnergyStored).get();
        if(amount > energyStored)
        {
            energy.map(e -> e.receiveEnergy(Math.abs(amount - energyStored), false));
        } else {
            energy.map(e -> e.extractEnergy(Math.abs(energyStored - amount), false));
        }
    }

    public int getEnergyStored(ItemStack stack) {
        var energy = stack.getCapability(ForgeCapabilities.ENERGY);

        if(!energy.isPresent())
            return 0;

        return energy.map(IEnergyStorage::getEnergyStored).get();
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return getEnergyMax();
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        var energy = stack.getCapability(ForgeCapabilities.ENERGY);
        return energy.map(e -> e.getEnergyStored() < e.getMaxEnergyStored()).orElse(super.isBarVisible(stack));
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        LazyOptional<IEnergyStorage> cap = stack.getCapability(ForgeCapabilities.ENERGY);
        if (!cap.isPresent())
            return super.getBarWidth(stack);

        return cap.map(e -> Math.min(13 * e.getEnergyStored() / e.getMaxEnergyStored(), 13))
                .orElse(super.getBarWidth(stack));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        LazyOptional<IEnergyStorage> cap = stack.getCapability(ForgeCapabilities.ENERGY);
        if (!cap.isPresent())
            return super.getBarColor(stack);

        Pair<Integer, Integer> energyStorage = cap.map(e -> Pair.of(e.getEnergyStored(), e.getMaxEnergyStored())).orElse(Pair.of(0, 0));
        return Mth.hsvToRgb(Math.max(0.0F, energyStorage.first / (float) energyStorage.second) / 3.0F, 1.0F, 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        Minecraft mc = Minecraft.getInstance();

        if (level == null || mc.player == null) {
            return;
        }

        boolean sneakPressed = Screen.hasShiftDown();

        if (!sneakPressed) {
            tooltip.add(Component.translatable("shockmetal.tooltip.shift-info").withStyle(ChatFormatting.GRAY));
        }

        LazyOptional<IEnergyStorage> energy = stack.getCapability(ForgeCapabilities.ENERGY);
        if (energy.isPresent()) {
            Pair<Integer, Integer> energyStorage = energy.map(e -> Pair.of(e.getEnergyStored(), e.getMaxEnergyStored())).orElse(Pair.of(0, 0));
            MutableComponent energyText = !sneakPressed
                    ? Component.translatable("shockmetal.tooltip.energy", Helpers.condenseValue(energyStorage.first), Helpers.condenseValue(energyStorage.second))
                    : Component.translatable("shockmetal.tooltip.energy", String.format("%,d", energyStorage.first), String.format("%,d", energyStorage.second));

            tooltip.add(energyText.withStyle(ChatFormatting.GREEN));
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        ItemEnergyBase itemEnergyBase = (ItemEnergyBase) stack.getItem();
        return new EnergyCapabilityProvider(new ItemEnergy(stack, itemEnergyBase.getEnergyMax()));
    }
}
