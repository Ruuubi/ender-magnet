package mods.endermagnet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mods.endermagnet.block.BlockEnderTorch;
import mods.endermagnet.item.ItemEnderMagnet;
import mods.endermagnet.item.ItemEnderTorch;
import mods.endermagnet.tile.TileEntityEnderTorch;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod("endermagnet")
public class EnderMagnet {
	public static final String MODID = "endermagnet";
	public static final String MODNAME = "Ender Magnet";
	public static final String MODNAME_NOSPACE = "EnderMagnet";
	public static final String VERSION = "1.14.3-1.0.0";
	public static final Logger LOGGER = LogManager.getLogger();

	public static ItemEnderMagnet icon;
	public static BlockEnderTorch.Floor block_ender_torch_floor;
	public static BlockEnderTorch.Wall block_ender_torch_wall;
	public static TileEntityType<TileEntityEnderTorch> tile_ender_torch;

	public static final ItemGroup TAB = new ItemGroup(MODID + "." + "main") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(icon);
		}
	};

	public EnderMagnet() {
		MinecraftForge.EVENT_BUS.register(new ItemEnderMagnet.TossEvent());
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class Registration {
		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			IForgeRegistry<Block> registry = event.getRegistry();
			registry.register(block_ender_torch_floor = new BlockEnderTorch.Floor());
			registry.register(block_ender_torch_wall = new BlockEnderTorch.Wall());
		}

		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			IForgeRegistry<Item> registry = event.getRegistry();
			registry.register(new ItemEnderMagnet("ender_magnet_tier1", 4, 0.3f));
			registry.register(new ItemEnderMagnet("ender_magnet_tier2", 8, 0.4f));
			registry.register(icon = new ItemEnderMagnet("ender_magnet_tier3", 12, 0.5f));
			registry.register(new ItemEnderTorch("ender_torch"));
		}

		@SuppressWarnings("unchecked")
		@SubscribeEvent
		public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event) {
			event.getRegistry().register(tile_ender_torch = (TileEntityType<TileEntityEnderTorch>) TileEntityType.Builder.create(TileEntityEnderTorch::new, block_ender_torch_floor, block_ender_torch_wall).build(null).setRegistryName("tile_ender_torch"));
		}
	}
}