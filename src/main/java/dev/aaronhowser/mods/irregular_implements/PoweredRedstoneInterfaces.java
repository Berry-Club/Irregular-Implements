package dev.aaronhowser.mods.irregular_implements;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import javax.annotation.Nullable;

public interface PoweredRedstoneInterfaces {

    default void irregular_implements$addWeakPower(
            BlockPos blockPos,
            @Nullable Direction direction,
            int power
    ) {
        throw new IllegalStateException();
    }

    default void irregular_implements$removeWeakPower(
            BlockPos blockPos,
            @Nullable Direction direction
    ) {
        throw new IllegalStateException();
    }

    default int irregular_implements$getWeakPower(
            BlockPos blockPos,
            @Nullable Direction direction
    ) {
        throw new IllegalStateException();
    }



    default void irregular_implements$addStrongPower(
            BlockPos blockPos,
            @Nullable Direction direction,
            int power
    ) {
        throw new IllegalStateException();
    }

    default void irregular_implements$removeStrongPower(
            BlockPos blockPos,
            @Nullable Direction direction
    ) {
        throw new IllegalStateException();
    }

    default int irregular_implements$getStrongPower(
            BlockPos blockPos,
            @Nullable Direction direction
    ) {
        throw new IllegalStateException();
    }

}
