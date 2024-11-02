package dev.aaronhowser.mods.irregular_implements;

public interface BetterFire {

    default float irregular_implements$getTickDelayFactor() {
        throw new IllegalStateException();
    }

}
