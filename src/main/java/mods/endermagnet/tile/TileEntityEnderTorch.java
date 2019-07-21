package mods.endermagnet.tile;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import mods.endermagnet.EnderMagnet;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityEnderTorch extends TileEntity implements ITickableTileEntity {

	public static final int RANGE = 5;
	private static final int RANGE_SQ = RANGE * RANGE;
	private static Set<TileEntityEnderTorch> torches = new HashSet<>();
	private static Set<TileEntityEnderTorch> invalidTorches = new HashSet<>();

	public TileEntityEnderTorch() {
		super(EnderMagnet.tile_ender_torch);
	}

	@Override
	public void tick() {
		if (!torches.contains(this)) torches.add(this);
	}

	
	@Override
	public void remove() {
		super.remove();
		invalidTorches.add(this);
	}

	public static boolean inRangeOfEntity(Entity entity) {
		if (entity != null) {
			if (invalidTorches.size() > 0) {
				for (TileEntityEnderTorch invalid : invalidTorches) torches.remove(invalid);
				invalidTorches.clear();
			}
			Iterator<TileEntityEnderTorch> itr = torches.iterator();
			while (itr.hasNext()) {
				TileEntityEnderTorch tile = itr.next();
				if (tile == null) continue;
				if (tile.world != entity.world) continue;
				if (!tile.world.isAreaLoaded(tile.pos, 1)) continue;
				if (tile.world.getTileEntity(tile.pos) != tile) continue;
				if (tile.getDistanceSq(entity.posX, entity.posY, entity.posZ) <= RANGE_SQ) return true;
			}
		}
		return false;
	}

}
