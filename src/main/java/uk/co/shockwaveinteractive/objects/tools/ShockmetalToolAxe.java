package uk.co.shockwaveinteractive.objects.tools;


import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
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
                        .tab(ShockMetalMain.SHOCKMETALTAB)
                        .fireResistant()
        );


    }
}
