package mods.endermagnet.block;

import java.util.Random;

import mods.endermagnet.tile.TileEntityEnderTorch;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockEnderTorch {

	public static class Floor extends TorchBlock {

		public Floor() {
			super(Block.Properties.from(Blocks.TORCH).lightValue(0));
			this.setRegistryName("ender_torch");
		}

		@Override
		public boolean hasTileEntity(BlockState state) {
			return true;
		}

		@Override
		public TileEntity createTileEntity(BlockState state, IBlockReader world) {
			return new TileEntityEnderTorch();
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
			double d0 = (double) pos.getX() + 0.5D;
			double d1 = (double) pos.getY() + 0.6D;
			double d2 = (double) pos.getZ() + 0.5D;
			worldIn.addParticle(ParticleTypes.DRAGON_BREATH, d0, d1, d2, 0.0D, 0.005D, 0.0D);
		}
	}

	public static class Wall extends WallTorchBlock {

		public Wall() {
			super(Block.Properties.from(Blocks.WALL_TORCH).lightValue(0));
			this.setRegistryName("ender_torch_wall");
		}

		@Override
		public boolean hasTileEntity(BlockState state) {
			return true;
		}

		@Override
		public TileEntity createTileEntity(BlockState state, IBlockReader world) {
			return new TileEntityEnderTorch();
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
			Direction direction = stateIn.get(HORIZONTAL_FACING);
			double d0 = (double) pos.getX() + 0.5D;
			double d1 = (double) pos.getY() + 0.6D;
			double d2 = (double) pos.getZ() + 0.5D;
			Direction direction1 = direction.getOpposite();
			worldIn.addParticle(ParticleTypes.DRAGON_BREATH, d0 + 0.27D * (double) direction1.getXOffset(), d1 + 0.22D, d2 + 0.27D * (double) direction1.getZOffset(), 0.0D, 0.005D, 0.0D);
		}
	}

}
