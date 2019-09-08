package mods.endermagnet.network;

import mods.endermagnet.EnderMagnet;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {

	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(EnderMagnet.MODID, "main"),
		    () -> PROTOCOL_VERSION,
		    PROTOCOL_VERSION::equals,
		    PROTOCOL_VERSION::equals
		);

	public static void register() {
		int index = 0;
		INSTANCE.registerMessage(index++, PacketToggleMagnet.class, PacketToggleMagnet::writePacket, PacketToggleMagnet::readPacket, PacketToggleMagnet.Handler::handlePacket);
		INSTANCE.registerMessage(index++, PacketPlaySound.class, PacketPlaySound::writePacket, PacketPlaySound::readPacket, PacketPlaySound.Handler::handlePacket);
	}

	/** @see net.minecraftforge.fml.network.PacketDistributor */
	public static void sendToServer(Object msg) {
		INSTANCE.sendToServer(msg);
	}

	public static void sendToClient(Object msg, ServerPlayerEntity player) {
		INSTANCE.sendTo(msg, player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
	}
	
	public static void sendToAllClients(Object msg) {
		INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
	}

}
