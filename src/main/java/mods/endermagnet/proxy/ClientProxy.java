package mods.endermagnet.proxy;

import org.lwjgl.glfw.GLFW;

import mods.endermagnet.EnderMagnet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy implements IProxy {
	
	public static final KeyBinding toggleMagnet = new KeyBinding("key." + EnderMagnet.MODID + ".toggleMagnet", GLFW.GLFW_KEY_UNKNOWN, "key.categories." + EnderMagnet.MODID);

	static {
		ClientRegistry.registerKeyBinding(toggleMagnet);
	}

	@Override
	public void playSound() {
		PlayerEntity player = Minecraft.getInstance().player;
		if (player != null) player.world.playSound(player, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.75F, 1.0F);
	}
	
}
