package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.RainShieldChunks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class BlockStateBaseMixin {

    @Inject(
            method = "emissiveRendering",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void emissiveRendering(BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (level instanceof RainShieldChunks rainShieldChunks) {
            var chunkPosLong = ChunkPos.asLong(pos);

            if (rainShieldChunks.irregular_implements$chunkPosHasRainShields(chunkPosLong)) {
                System.out.println("Emissive");
                cir.setReturnValue(true);
            }
        }
    }

}
