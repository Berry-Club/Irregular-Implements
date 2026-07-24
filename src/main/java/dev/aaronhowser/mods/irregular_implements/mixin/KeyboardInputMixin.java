package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.aaron.client.AaronClientUtil;
import dev.aaronhowser.mods.irregular_implements.config.ClientConfig;
import dev.aaronhowser.mods.irregular_implements.registry.ModMobEffects;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
abstract public class KeyboardInputMixin {

	@Inject(
			method = "tick",
			at = @At("RETURN")
	)
	private void irregular_implements$invertWhenCollapse(boolean isSneaking, float sneakingSpeedMultiplier, CallbackInfo ci) {
		if (!ClientConfig.CONFIG.collapseInvertsControls.get()) return;

		Player player = AaronClientUtil.getLocalPlayer();
		if (player == null) return;

		KeyboardInput input = (KeyboardInput) (Object) this;

		if (player.hasEffect(ModMobEffects.COLLAPSE)) {
			input.leftImpulse = -input.leftImpulse;
			input.forwardImpulse = -input.forwardImpulse;
		}
	}
}