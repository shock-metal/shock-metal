package uk.co.shockwaveinteractive.common.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import uk.co.shockwaveinteractive.objects.items.energytools.ItemShieldModule;

import java.util.function.Supplier;

public class PacketShieldDamage {

    private ItemStack stack;
    private int amount;

    public PacketShieldDamage(ItemStack stack, int amount) {
        this.stack = stack;
        this.amount = amount;
    }

    public static void encode(PacketShieldDamage packet, FriendlyByteBuf buffer) {
        buffer.writeItem(packet.stack);
        buffer.writeInt(packet.amount);
    }

    public static PacketShieldDamage decode(FriendlyByteBuf buffer) {
        ItemStack stack = buffer.readItem();
        int amount = buffer.readInt();
        return new PacketShieldDamage(stack, amount);
    }

    public static class Handler {
        public static void handle(PacketShieldDamage packet, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                System.out.println(packet.stack.getItem() instanceof ItemShieldModule shieldModule);
                if (packet.stack.getItem() instanceof ItemShieldModule shieldModule) {
                    shieldModule.hurtActiveShield(packet.stack, packet.amount, context.getSender());
                }
            });
            context.setPacketHandled(true);
        }
    }
}
