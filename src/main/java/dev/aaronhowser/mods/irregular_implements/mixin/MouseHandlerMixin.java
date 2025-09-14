package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.config.ClientConfig;
import dev.aaronhowser.mods.irregular_implements.registry.ModEffects;
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
abstract public class MouseHandlerMixin {

	@Shadow
	private double accumulatedDX;

	@Shadow
	private double accumulatedDY;

	@Inject(
			method = "turnPlayer",
			at = @At("HEAD")
	)
	private void tryInvert(double movementTime, CallbackInfo ci) {
		if (!ClientConfig.CONFIG.collapseInvertsMouse.get()) return;

		var player = ClientUtil.getLocalPlayer();
		if (player == null || !player.hasEffect(ModEffects.COLLAPSE)) return;

		accumulatedDX = -accumulatedDX;
		accumulatedDY = -accumulatedDY;
	}

}
