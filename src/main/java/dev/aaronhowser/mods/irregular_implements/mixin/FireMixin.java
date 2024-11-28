package dev.aaronhowser.mods.irregular_implements.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.aaronhowser.mods.irregular_implements.BetterFire;
import net.minecraft.world.level.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FireBlock.class)
public abstract class FireMixin implements BetterFire {

    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/FireBlock;getFireTickDelay(Lnet/minecraft/util/RandomSource;)I"
            )
    )
    private int irregular_implements$replaceFireTickSpeed(int original) {
        return (int) (original * irregular_implements$getTickDelayFactor());
    }

}
