package mods.endermagnet.network;

import java.util.function.Supplier;

import mods.endermagnet.EnderMagnet;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketPlaySound {

	public PacketPlaySound() {}

	public static void writePacket(PacketPlaySound packet, PacketBuffer buf) {}

	public static PacketPlaySound readPacket(PacketBuffer buf) {
		return new PacketPlaySound();
	}

	public static class Handler {
		public static void handlePacket(PacketPlaySound packet, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				EnderMagnet.PROXY.playSound();
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
