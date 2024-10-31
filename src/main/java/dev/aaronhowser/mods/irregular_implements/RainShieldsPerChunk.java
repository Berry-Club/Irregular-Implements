package dev.aaronhowser.mods.irregular_implements;

public interface RainShieldsPerChunk {

    default void irregular_implements$setRainShieldCount(int count) {
        throw new IllegalStateException();
    }

    default int irregular_implements$getRainShieldCount() {
        throw new IllegalStateException();
    }

}
