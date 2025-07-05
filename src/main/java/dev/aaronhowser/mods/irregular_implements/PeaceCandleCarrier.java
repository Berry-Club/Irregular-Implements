package dev.aaronhowser.mods.irregular_implements;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;

public interface PeaceCandleCarrier {


	default boolean irregular_implements$addPeaceCandleChunk(long chunkPosAsLong) {
		throw new IllegalStateException();
	}

	default boolean irregular_implements$removePeaceCandleChunk(long chunkPosAsLong) {
		throw new IllegalStateException();
	}

	default boolean irregular_implements$chunkProtectedByPeaceCandle(long chunkPosAsLong) {
		throw new IllegalStateException();
	}

	default void irregular_implements$clearPeaceCandleChunks() {
		throw new IllegalStateException();
	}

	default LongOpenHashSet irregular_implements$getPeaceCandleChunks() {
		throw new IllegalStateException();
	}

}
