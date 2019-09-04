package mods.endermagnet.proxy;

import org.lwjgl.glfw.GLFW;

import mods.endermagnet.EnderMagnet;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy implements IProxy {
//	public static final KeyBinding toggleMagnet = new KeyBinding(EnderMagnet.MODID + ".key.toggleMagnet", GLFW.GLFW_KEY_UNKNOWN, "key.categories." + EnderMagnet.MODID);
	public static final KeyBinding toggleMagnet = new KeyBinding("key." + EnderMagnet.MODID + ".toggleMagnet", GLFW.GLFW_KEY_UNKNOWN, "key.categories." + EnderMagnet.MODID);

	static {
		ClientRegistry.registerKeyBinding(toggleMagnet);
	}
}
