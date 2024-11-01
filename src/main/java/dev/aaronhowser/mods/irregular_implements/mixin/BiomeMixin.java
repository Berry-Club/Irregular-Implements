package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.block.block_entity.RainShieldBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public abstract class BiomeMixin {

    @Inject(
            method = "shouldSnow",
            at = @At(
                    value = "RETURN",
                    ordinal = 1
            ),
            cancellable = true
    )
    private void shouldSnow(LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (RainShieldBlockEntity.Companion.chunkHasActiveRainShield(level, pos)) {
            cir.setReturnValue(false);
        }
    }

}
