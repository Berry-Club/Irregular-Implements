package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.entity.IlluminatorEntity;
import dev.aaronhowser.mods.irregular_implements.item.SpectreIlluminatorItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LightLayer;
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
        if (IlluminatorEntity.isChunkIlluminated(blockPos, (BlockAndTintGetter) this)) {
            cir.setReturnValue(15);
        }
    }

    @Inject(
            method = "getRawBrightness",
            at = @At("HEAD"),
            cancellable = true
    )
    default void irregular_implements$getRawBrightness(BlockPos blockPos, int amount, CallbackInfoReturnable<Integer> cir) {
        if (IlluminatorEntity.isChunkIlluminated(blockPos, (BlockAndTintGetter) this)) {
            cir.setReturnValue(15);
        }
    }

}
