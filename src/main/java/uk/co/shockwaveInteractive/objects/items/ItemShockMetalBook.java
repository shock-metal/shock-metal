package uk.co.shockwaveInteractive.objects.items;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import uk.co.shockwaveInteractive.ShockMetalMain;
import uk.co.shockwaveInteractive.init.ItemInit;
import uk.co.shockwaveInteractive.util.interfaces.IHasModel;

public class ItemShockMetalBook extends Item implements IHasModel
{
	
	 public ItemShockMetalBook(String name) 
	 {
			setUnlocalizedName(name);
			setRegistryName(name);
		    this.setCreativeTab(ShockMetalMain.shockmetaltab);
		    this.setMaxStackSize(1);
		    
		    ItemInit.ITEMS.add(this);
	}
	 
	 @Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		 
		 tooltip.add("Learn all there is to learn about shock metal!");
		 
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	 
		@Override
		public void registerModels() {
			
			ShockMetalMain.proxy.registerItemRenderer(this, 0, "inventory");
			
		}
	 
}
