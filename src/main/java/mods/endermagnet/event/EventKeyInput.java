package mods.endermagnet.event;

import mods.endermagnet.network.PacketHandler;
import mods.endermagnet.network.PacketToggleMagnet;
import mods.endermagnet.proxy.ClientProxy;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventKeyInput {
	
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		if (ClientProxy.toggleMagnet.isPressed()) {
			PacketHandler.sendToServer(new PacketToggleMagnet());
		}
	}
	
}
