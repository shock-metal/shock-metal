package vnemesis.shockmetal.item.tools;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;

public class ShockmetalToolPickaxe extends PickaxeItem
{
    public ShockmetalToolPickaxe() {
        super(
            ShockmetalItemTier.SHOCKMETAL,
            new Item.Properties().fireResistant()
                .attributes(PickaxeItem.createAttributes(ShockmetalItemTier.SHOCKMETAL, 1, -2.8f))
        );
    }
}

