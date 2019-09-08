package mods.endermagnet.network;

import java.util.function.Supplier;

import mods.endermagnet.item.ItemEnderMagnet;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketToggleMagnet {

	public PacketToggleMagnet() {}

	public static void writePacket(PacketToggleMagnet packet, PacketBuffer buf) {}

	public static PacketToggleMagnet readPacket(PacketBuffer buf) {
		return new PacketToggleMagnet();
	}

	public static class Handler {
		public static void handlePacket(PacketToggleMagnet packet, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				ServerPlayerEntity player = ctx.get().getSender();
				if (player != null) {
					for (int slot=0; slot<player.inventory.getSizeInventory(); slot++) {
						ItemStack stack = player.inventory.getStackInSlot(slot);
						if (!stack.isEmpty() && stack.getItem() instanceof ItemEnderMagnet) {
							ItemEnderMagnet.toggle(player, stack);
							PacketHandler.sendToClient(new PacketPlaySound(), player);
							break;
						}
					}
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
