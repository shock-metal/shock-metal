package uk.co.shockwaveInteractive.objects.items;

import java.util.List;

import javax.annotation.Nonnull;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import uk.co.shockwaveInteractive.ShockMetalMain;
import uk.co.shockwaveInteractive.gui.GuiShockmetalManual;
import uk.co.shockwaveInteractive.init.ItemInit;
import uk.co.shockwaveInteractive.util.Reference;
import uk.co.shockwaveInteractive.util.interfaces.IHasModel;

public class ItemShockMetalBook extends Item implements IHasModel
{
	
	 public ItemShockMetalBook(String name) 
	 {
		 setTranslationKey(name);
			setRegistryName(name);
		    this.setCreativeTab(ShockMetalMain.shockmetaltab);
		    this.setMaxStackSize(1);
		    
		    ItemInit.ITEMS.add(this);
	}
	 
	 @Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		 
		 if(GuiScreen.isShiftKeyDown())
			{
			 	tooltip.add(ChatFormatting.WHITE + "Learn all there is to learn about shock metal!");
			}
			else tooltip.add(ChatFormatting.DARK_PURPLE + "Press <<SHIFT>> for more Info");
		 
		 
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	 
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) 
	 {
		 ItemStack itemStack = playerIn.getHeldItem(handIn);
		    if(worldIn.isRemote) {
		    	Minecraft.getMinecraft().displayGuiScreen(new GuiShockmetalManual());
		    }
		    
		    return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
	}
	 
		@Override
		public void registerModels() {
			
			ShockMetalMain.proxy.registerItemRenderer(this, 0, "inventory");
			
		}
	 
}
