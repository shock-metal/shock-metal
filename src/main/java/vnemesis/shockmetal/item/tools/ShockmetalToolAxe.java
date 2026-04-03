package vnemesis.shockmetal.item.tools;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;

public class ShockmetalToolAxe extends AxeItem
{
    public ShockmetalToolAxe() {
        super(
            ShockmetalItemTier.SHOCKMETAL,
            new Item.Properties().fireResistant()
                .attributes(AxeItem.createAttributes(ShockmetalItemTier.SHOCKMETAL, 5f, -3.0f))
        );
    }
}

