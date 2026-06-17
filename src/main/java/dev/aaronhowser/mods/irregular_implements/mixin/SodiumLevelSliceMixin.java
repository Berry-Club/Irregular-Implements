package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.handler.SpectreIlluminationHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LightLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "net.caffeinemc.mods.sodium.client.world.LevelSlice", remap = false)
public class SodiumLevelSliceMixin {

	@Inject(
			method = "getBrightness",
			at = @At("HEAD"),
			cancellable = true,
			require = 0,
			remap = false
	)
	private void irregular_implements$getBrightness(
			LightLayer type,
			BlockPos pos,
			CallbackInfoReturnable<Integer> cir
	) {
		if (SpectreIlluminationHandler.isChunkIlluminated((BlockAndTintGetter) this, pos)) {
			cir.setReturnValue(15);
		}
	}

	@Inject(
			method = "getRawBrightness",
			at = @At("HEAD"),
			cancellable = true,
			require = 0,
			remap = false
	)
	private void irregular_implements$getRawBrightness(
			BlockPos pos,
			int ambientDarkness,
			CallbackInfoReturnable<Integer> cir
	) {
		if (SpectreIlluminationHandler.isChunkIlluminated((BlockAndTintGetter) this, pos)) {
			cir.setReturnValue(15);
		}
	}

}
