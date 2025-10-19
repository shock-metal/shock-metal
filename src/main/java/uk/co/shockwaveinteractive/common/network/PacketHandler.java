package uk.co.shockwaveinteractive.common.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import uk.co.shockwaveinteractive.common.network.packets.PacketShieldDamage;
import uk.co.shockwaveinteractive.util.reference.MainReference;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = Integer.toString(2);
    private static short index = 0;

    public static final SimpleChannel HANDLER  = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(MainReference.MODID, "main_network_channel"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static void register() {
        int id = 0;

        // Server side
        HANDLER.registerMessage(id++, PacketShieldDamage.class, PacketShieldDamage::encode, PacketShieldDamage::decode,PacketShieldDamage.Handler::handle);
    }
}
