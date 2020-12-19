//package uk.co.shockwaveinteractive.objects.items;
//
//import net.minecraft.item.Item;
//import uk.co.shockwaveinteractive.ShockMetalMain;
//import uk.co.shockwaveinteractive.util.interfaces.IHasModel;
//
//public class ItemShockMetalBook extends Item implements IHasModel
//{
//
//	 public ItemShockMetalBook()
//	 {
//		 super(new Item.Properties().group(ShockMetalMain.SHOCKMETALTAB).maxStackSize(1));
//	}
//
////	 @Override
////	 @SideOnly(Side.CLIENT)
////	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
////
////		 if(GuiScreen.isShiftKeyDown())
////			{
////			 	tooltip.add(ChatFormatting.WHITE + "Learn all there is to learn about shock metal!");
////			}
////			else tooltip.add(ChatFormatting.DARK_PURPLE + "Press <<SHIFT>> for more Info");
////
////
////		super.addInformation(stack, worldIn, tooltip, flagIn);
////	}
//
//
////	@Override
////	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
////	 {
////		 ItemStack itemStack = playerIn.getHeldItem(handIn);
////		    if(worldIn.isRemote) {
////		    	Minecraft.getInstance().displayGuiScreen(new GuiShockmetalManual());
////		    }
////
////		    return new ActionResult<>(ActionResultType.SUCCESS, itemStack);
////	}
//
////	@Override
////	public void registerModels() {
////
////		ShockMetalMain.proxy.registerItemRenderer(this, 0, "inventory");
////
////	}
//
//}
