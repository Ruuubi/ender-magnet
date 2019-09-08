package mods.endermagnet.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
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
				PlayerEntity player = Minecraft.getInstance().player;
				if (player != null) player.world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.75F, 1.0F);
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
