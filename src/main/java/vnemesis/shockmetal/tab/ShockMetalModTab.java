package vnemesis.shockmetal.tab;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import vnemesis.shockmetal.item.ShockMetalItemsRegistry;

public class ShockMetalModTab extends CreativeModeTab
{
    public ShockMetalModTab(Builder builder) {
        super(builder);
    }

    @Override
    public @NotNull ItemStack getIconItem() {
        return new ItemStack(ShockMetalItemsRegistry.SHOCKMETAL_INGOT.get());
    }
}
