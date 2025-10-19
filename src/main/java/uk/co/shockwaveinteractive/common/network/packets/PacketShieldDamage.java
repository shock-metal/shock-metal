package uk.co.shockwaveinteractive.common.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import uk.co.shockwaveinteractive.objects.items.energytools.ItemShieldModule;

import java.util.UUID;
import java.util.function.Supplier;

public class PacketShieldDamage {

    private ItemStack stack;
    private int amount;
    private UUID playerId;

    public PacketShieldDamage(ItemStack stack, int amount, UUID playerId) {
        this.stack = stack;
        this.amount = amount;
        this.playerId = playerId;
    }

    public static void encode(PacketShieldDamage packet, FriendlyByteBuf buffer) {
        buffer.writeItem(packet.stack);
        buffer.writeInt(packet.amount);
        buffer.writeUUID(packet.playerId);
    }

    public static PacketShieldDamage decode(FriendlyByteBuf buffer) {
        ItemStack stack = buffer.readItem();
        int amount = buffer.readInt();
        UUID playerId = buffer.readUUID();
        return new PacketShieldDamage(stack, amount, playerId);
    }

    public static class Handler {
        public static void handle(PacketShieldDamage packet, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                ServerPlayer player = context.getSender().getServer().getPlayerList().getPlayer(packet.playerId);
                if (player != null && packet.stack.getItem() instanceof ItemShieldModule shieldModule) {
                    System.out.println("Packet Received!");
                    System.out.println(player.getScoreboardName());
                    shieldModule.doShieldDamageUpdate(packet.stack, packet.amount, player);
                }
            });
            context.setPacketHandled(true);
        }
    }
}
