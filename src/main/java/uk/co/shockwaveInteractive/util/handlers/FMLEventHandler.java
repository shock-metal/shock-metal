package uk.co.shockwaveInteractive.util.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import uk.co.shockwaveInteractive.init.ItemInit;
import uk.co.shockwaveInteractive.util.Reference;

public class FMLEventHandler {
	
	private static final String NBT_KEY = "shockm.firstjoin";
	
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {

        NBTTagCompound data = event.player.getEntityData();
        NBTTagCompound persistent;
        if (!data.hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
            data.setTag(EntityPlayer.PERSISTED_NBT_TAG, (persistent = new NBTTagCompound()));
        } else {
            persistent = data.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        }

        if (!persistent.hasKey(NBT_KEY) || Reference.DEBUG) {
            persistent.setBoolean(NBT_KEY, true);
            event.player.inventory.addItemStackToInventory(new ItemStack(ItemInit.INGOT_SHOCKMETAL));
        }
    }

}
