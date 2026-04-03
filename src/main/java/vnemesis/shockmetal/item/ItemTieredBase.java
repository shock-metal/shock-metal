package vnemesis.shockmetal.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;

public class ItemTieredBase extends TieredItem
{
    public ItemTieredBase(Tier tier, Properties props) {
        super(tier, props);
    }
    public ItemTieredBase(Tier tier) {
        super(tier, new Item.Properties());
    }
}
