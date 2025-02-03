package dev.aaronhowser.mods.irregular_implements;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;

public interface RainShieldChunks {

    default boolean irregular_implements$addRainShieldChunk(long chunkPosAsLong) {
        throw new IllegalStateException();
    }

    default boolean irregular_implements$removeRainShieldChunk(long chunkPosAsLong) {
        throw new IllegalStateException();
    }

    default boolean irregular_implements$chunkHasRainShield(long chunkPosAsLong) {
        throw new IllegalStateException();
    }

    default void irregular_implements$clearRainShieldChunks() {
        throw new IllegalStateException();
    }

    default LongOpenHashSet irregular_implements$getRainShieldChunks() {
        throw new IllegalStateException();
    }

}
