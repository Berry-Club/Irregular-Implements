package dev.aaronhowser.mods.irregular_implements;

import dev.aaronhowser.mods.irregular_implements.registry.ModEffects;
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.client.player.LocalPlayer;

public interface KeyboardInputFunctions {

    default void checkInvert() {
        LocalPlayer player = ClientUtil.getLocalPlayer();
        if (player == null) return;

        KeyboardInput input = (KeyboardInput) this;

        if (player.hasEffect(ModEffects.COLLAPSE)) {
            input.leftImpulse = -input.leftImpulse;
            input.forwardImpulse = -input.forwardImpulse;
        }
    }

}
