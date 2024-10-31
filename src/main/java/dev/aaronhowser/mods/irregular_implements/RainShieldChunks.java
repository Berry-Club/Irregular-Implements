package dev.aaronhowser.mods.irregular_implements;

public interface RainShieldChunks {

    default boolean irregular_implements$addChunkPos(long chunkPosAsLong) {
        throw new IllegalStateException();
    }

    default boolean irregular_implements$removeChunkPos(long chunkPosAsLong) {
        throw new IllegalStateException();
    }

    default boolean irregular_implements$chunkPosHasRainShields(long chunkPosAsLong) {
        throw new IllegalStateException();
    }

}
