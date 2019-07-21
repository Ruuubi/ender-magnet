package mods.endermagnet.item;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import mods.endermagnet.EnderMagnet;
import mods.endermagnet.tile.TileEntityEnderTorch;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemEnderTorch extends WallOrFloorItem {

	public ItemEnderTorch(String regname) {
		super(EnderMagnet.block_ender_torch_floor, EnderMagnet.block_ender_torch_wall, new Item.Properties().group(EnderMagnet.TAB));
		this.setRegistryName(regname);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World player, List<ITextComponent> tooltip, ITooltipFlag advanced) {
		List<String> text = Arrays.asList(
				TextFormatting.GRAY.toString() + "Disables nearby Ender Magnets.",
				TextFormatting.DARK_GREEN.toString() + "Range: " + TextFormatting.GRAY.toString() + TileEntityEnderTorch.RANGE);
		for (String entry : text) tooltip.add(new StringTextComponent(entry));
	}

}
