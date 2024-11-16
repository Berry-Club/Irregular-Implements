package dev.aaronhowser.mods.irregular_implements.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.aaronhowser.mods.irregular_implements.PoweredRedstoneInterfaces;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.SignalGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SignalGetter.class)
public interface SignalGetterMixin {

    //FIXME: This is checked for EVERY SURROUNDING BLOCK, not the block itself!
    @ModifyReturnValue(
            method = "getSignal",
            at = @At("RETURN")
    )
    default int irregular_implements$getSignal(int original, BlockPos pos, Direction direction) {
        return original >= 15 || !(this instanceof PoweredRedstoneInterfaces f)
                ? original
                : Math.max(original, f.irregular_implements$getStrongPower(pos, direction));
    }

    @ModifyReturnValue(
            method = "hasNeighborSignal",
            at = @At("RETURN")
    )
    default boolean irregular_implements$hasNeighborSignal(boolean original, BlockPos pos) {
        return original || !(this instanceof PoweredRedstoneInterfaces f)
                ? original
                : f.irregular_implements$getStrongPower(pos, null) > 0;
    }

    @ModifyReturnValue(
            method = "getBestNeighborSignal",
            at = @At("RETURN")
    )
    default int irregular_implements$getBestNeighborSignal(int original, BlockPos pos) {
        return original >= 15 || !(this instanceof PoweredRedstoneInterfaces f)
                ? original
                : Math.max(original, f.irregular_implements$getStrongPower(pos, null));
    }

}
