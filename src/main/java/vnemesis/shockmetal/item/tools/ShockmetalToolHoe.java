package vnemesis.shockmetal.item.tools;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;

public class ShockmetalToolHoe extends HoeItem
{
    public ShockmetalToolHoe() {
        super(
            ShockmetalItemTier.SHOCKMETAL,
            new Item.Properties().fireResistant()
                .attributes(HoeItem.createAttributes(ShockmetalItemTier.SHOCKMETAL, -3f, 0f))
        );
    }
}

