package mods.endermagnet.item;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import mods.endermagnet.Config;
import mods.endermagnet.EnderMagnet;
import mods.endermagnet.tile.TileEntityEnderTorch;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ItemEnderMagnet extends Item {

	private final int RANGE;
	private final float SPEED;
	private static final int DELAY_TOSS = 60;
	private static final int DELAY_ENDER_TORCH = 10;
	private static final int MAX_PULL_COUNT = 100;
	private static final String NBTTAG_TEMP = EnderMagnet.MODID + "TempDisabled"; // ItemToss or Ender Torch in range
	private static final String NBTTAG_PERM = EnderMagnet.MODID + "PermDisabled"; // Rightclicked

	public ItemEnderMagnet(String regname, int range, float speed) {
		super(new Item.Properties().group(EnderMagnet.TAB).maxStackSize(1));
		this.setRegistryName(regname);
		this.RANGE = range;
		this.SPEED = speed;
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return getPermDisabled(stack);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!world.isRemote) toggle(player, stack);
		EnderMagnet.PROXY.playSound();
		return new ActionResult<>(ActionResultType.SUCCESS, stack);
	}

	@Override
	// Magnet logic was taken from Botania (A mod by Vazkii)
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
		if (entity instanceof PlayerEntity == false) return;
		PlayerEntity player = (PlayerEntity) entity;
		if (player.isSpectator()) return;
		if (!TileEntityEnderTorch.inRangeOfEntity(player)) {
			int delay = getPickupDelay(stack);
			if (delay == 0) {
				if (!player.isSneaking() && !getPermDisabled(stack)) {
					double x = player.posX;
					double y = player.posY + 0.25;
					double z = player.posZ;
					Vec3d vecPlayer = new Vec3d(x, y, z);
					List<ItemEntity> items = player.world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(x - this.RANGE, y - this.RANGE, z - this.RANGE, x + this.RANGE, y + this.RANGE, z + this.RANGE));
					int itemcount = 0;
					for (ItemEntity entityItem : items) {
						// NBT Tag "PreventRemoteMovement" to support "Demagnetize" mod
						if (!TileEntityEnderTorch.inRangeOfEntity(entityItem) && !entityItem.getPersistentData().getBoolean("PreventRemoteMovement")) {
							itemcount += 1;
							Vec3d vecItem = new Vec3d(entityItem.posX, entityItem.posY + entityItem.getHeight() / 2, entityItem.posZ);
							Vec3d vecNewPos = vecPlayer.subtract(vecItem);
							if (Math.sqrt(vecNewPos.x * vecNewPos.x + vecNewPos.y * vecNewPos.y + vecNewPos.z * vecNewPos.z) > 1) vecNewPos = vecNewPos.normalize();
							if (player.world.isRemote && Config.CLIENT_ENABLE_PARTICLES) {
								player.world.addParticle(ParticleTypes.DRAGON_BREATH, entityItem.posX, entityItem.posY + entityItem.getHeight(), entityItem.posZ, vecNewPos.x * 0.05, vecNewPos.y * 0.01, vecNewPos.z * 0.05);
							}
							entityItem.setMotion(vecNewPos.scale(this.SPEED));
							if (itemcount >= MAX_PULL_COUNT) break;
						}
					}
				}
			} else {
				setPickupDelay(stack, delay - 1);
			}
		} else {
			setPickupDelay(stack, DELAY_ENDER_TORCH);
		}
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World player, List<ITextComponent> tooltip, ITooltipFlag advanced) {
		List<String> text = Arrays.asList(
				TextFormatting.GRAY.toString() + "Pulls nearby items.",
				TextFormatting.DARK_GREEN.toString() + "Range: " + TextFormatting.GRAY.toString() + this.RANGE);
		for (String entry : text) tooltip.add(new StringTextComponent(entry));
	}

	public static void toggle(PlayerEntity player, ItemStack stack) {
		boolean disabled = !getPermDisabled(stack);
		String text = (disabled) ? TextFormatting.RED.toString() + "Disabled" : TextFormatting.GREEN.toString() + "Enabled";
		setPermDisabled(stack, disabled);
		player.sendStatusMessage(new StringTextComponent(TextFormatting.GRAY.toString() + "Ender Magnet: " + text), true);
	}
	
	public static boolean getPermDisabled(ItemStack magnet) {
		if (magnet.getItem() instanceof ItemEnderMagnet) {
			CompoundNBT nbt = magnet.getOrCreateTag();
			return nbt.getBoolean(NBTTAG_PERM);
		}
		return false;
	}

	public static void setPermDisabled(ItemStack magnet, boolean disabled) {
		if (magnet.getItem() instanceof ItemEnderMagnet) {
			CompoundNBT nbt = magnet.getOrCreateTag();
			nbt.putBoolean(NBTTAG_PERM, disabled);
		}
	}

	public static int getPickupDelay(ItemStack magnet) {
		if (magnet.getItem() instanceof ItemEnderMagnet) {
			CompoundNBT nbt = magnet.getOrCreateTag();
			return nbt.getInt(NBTTAG_TEMP);
		}
		return 0;
	}

	public static void setPickupDelay(ItemStack magnet, int delay) {
		if (magnet.getItem() instanceof ItemEnderMagnet) {
			if (delay < 0) delay = 0;
			CompoundNBT nbt = magnet.getOrCreateTag();
			nbt.putInt(NBTTAG_TEMP, delay);
		}
	}

	public static class TossEvent {
		@SubscribeEvent
		public void onItemToss(ItemTossEvent event) {
			PlayerInventory inventory = event.getPlayer().inventory;
			for (int slot = 0; slot < inventory.getSizeInventory(); slot++) {
				ItemStack stack = inventory.getStackInSlot(slot);
				if (stack.getItem() instanceof ItemEnderMagnet) {
					setPickupDelay(stack, DELAY_TOSS);
				}
			}
		}
	}

}
