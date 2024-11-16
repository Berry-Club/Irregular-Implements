package dev.aaronhowser.mods.irregular_implements;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import javax.annotation.Nullable;

public interface PoweredRedstoneInterfaces {

    default int irregular_implements$getStrongPower(
            BlockPos blockPos,
            @Nullable Direction direction
    ) {
        throw new IllegalStateException();
    }

}
