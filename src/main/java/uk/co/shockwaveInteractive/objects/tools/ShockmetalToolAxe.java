package uk.co.shockwaveinteractive.objects.tools;

import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.objects.materials.ShockmetalItemTier;

public class ShockmetalToolAxe extends AxeItem
{

    public ShockmetalToolAxe()
    {
        super(
                ShockmetalItemTier.SHOCKMETAL,
                2,
                -2.0f,
                new Item.Properties()
                        .group(ShockMetalMain.SHOCKMETALTAB)
        );
    }
}
