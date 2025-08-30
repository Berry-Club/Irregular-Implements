package dev.aaronhowser.mods.irregular_implements;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ThisWillDoNothing {

	public static Integer fill(
			ServerLevel level,
			BlockPos from,
			BlockPos to,
			Holder<Biome> biome,
			Predicate<Holder<Biome>> filter
	) {
		BlockPos blockpos = quantize(from);
		BlockPos blockpos1 = quantize(to);
		BoundingBox boundingbox = BoundingBox.fromCorners(blockpos, blockpos1);

		List<ChunkAccess> list = new ArrayList<>();

		for (int k = SectionPos.blockToSectionCoord(boundingbox.minZ()); k <= SectionPos.blockToSectionCoord(boundingbox.maxZ()); k++) {
			for (int l = SectionPos.blockToSectionCoord(boundingbox.minX()); l <= SectionPos.blockToSectionCoord(boundingbox.maxX()); l++) {
				ChunkAccess chunkaccess = level.getChunk(l, k, ChunkStatus.FULL, false);
				if (chunkaccess == null) {
					return -1;
				}

				list.add(chunkaccess);
			}
		}

		MutableInt mutableint = new MutableInt(0);

		for (ChunkAccess chunkaccess1 : list) {
			chunkaccess1.fillBiomesFromNoise(
					makeResolver(mutableint, chunkaccess1, boundingbox, biome, filter), level.getChunkSource().randomState().sampler()
			);
			chunkaccess1.setUnsaved(true);
		}

		level.getChunkSource().chunkMap.resendBiomesForChunks(list);

		return mutableint.getValue();
	}

	private static int quantize(int value) {
		return QuartPos.toBlock(QuartPos.fromBlock(value));
	}

	private static BlockPos quantize(BlockPos pos) {
		return new BlockPos(quantize(pos.getX()), quantize(pos.getY()), quantize(pos.getZ()));
	}

	private static BiomeResolver makeResolver(
			MutableInt biomeEntries, ChunkAccess chunk, BoundingBox targetRegion, Holder<Biome> replacementBiome, Predicate<Holder<Biome>> filter
	) {
		return (p_262550_, p_262551_, p_262552_, p_262553_) -> {
			int i = QuartPos.toBlock(p_262550_);
			int j = QuartPos.toBlock(p_262551_);
			int k = QuartPos.toBlock(p_262552_);
			Holder<Biome> holder = chunk.getNoiseBiome(p_262550_, p_262551_, p_262552_);
			if (targetRegion.isInside(i, j, k) && filter.test(holder)) {
				biomeEntries.increment();
				return replacementBiome;
			} else {
				return holder;
			}
		};
	}

}
