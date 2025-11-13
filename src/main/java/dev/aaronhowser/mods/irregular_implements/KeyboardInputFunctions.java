package dev.aaronhowser.mods.irregular_implements;

import dev.aaronhowser.mods.irregular_implements.registry.ModEffects;
import dev.aaronhowser.mods.aaron.ClientUtil;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.world.entity.player.Player;

public interface KeyboardInputFunctions {

	default void checkInvert() {
		Player player = ClientUtil.getLocalPlayer();
		if (player == null) return;

		KeyboardInput input = (KeyboardInput) this;

		if (player.hasEffect(ModEffects.COLLAPSE)) {
			input.leftImpulse = -input.leftImpulse;
			input.forwardImpulse = -input.forwardImpulse;
		}
	}

}
