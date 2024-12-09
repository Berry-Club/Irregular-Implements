package dev.aaronhowser.mods.irregular_implements;

import dev.aaronhowser.mods.irregular_implements.registry.ModEffects;
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil;
import net.minecraft.client.Options;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.client.player.LocalPlayer;

public interface KeyboardInputFunctions {

    //FIXME: Not working
    default void checkInvert(Options options) {
        LocalPlayer player = ClientUtil.getLocalPlayer();
        if (player == null) return;

        KeyboardInput input = (KeyboardInput) this;

        if (player.hasEffect(ModEffects.getCOLLAPSE_IMBUE())) {
            input.up = options.keyDown.isDown();
            input.down = options.keyUp.isDown();
            input.left = options.keyRight.isDown();
            input.right = options.keyLeft.isDown();
        }
    }

}
