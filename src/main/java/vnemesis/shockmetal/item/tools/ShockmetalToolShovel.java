package vnemesis.shockmetal.item.tools;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;

public class ShockmetalToolShovel extends ShovelItem
{
    public ShockmetalToolShovel() {
        super(
            ShockmetalItemTier.SHOCKMETAL,
            new Item.Properties().fireResistant()
                .attributes(ShovelItem.createAttributes(ShockmetalItemTier.SHOCKMETAL, 1.5f, -3.0f))
        );
    }
}

