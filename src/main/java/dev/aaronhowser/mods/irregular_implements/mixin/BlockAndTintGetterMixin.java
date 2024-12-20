package dev.aaronhowser.mods.irregular_implements.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockAndTintGetter.class)
public interface BlockAndTintGetterMixin {

    @Inject(
            method = "getBrightness",
            at = @At("HEAD"),
            cancellable = true
    )
    default void irregular_implements$getBrightness(LightLayer lightType, BlockPos blockPos, CallbackInfoReturnable<Integer> cir) {
        if (this instanceof ServerLevelAccessor) {
            cir.setReturnValue(0);
        }
    }

    @Inject(
            method = "getRawBrightness",
            at = @At("HEAD"),
            cancellable = true
    )
    default void irregular_implements$getRawBrightness(BlockPos blockPos, int amount, CallbackInfoReturnable<Integer> cir) {
        if (this instanceof ServerLevelAccessor) {
            cir.setReturnValue(0);
        }
    }

}
