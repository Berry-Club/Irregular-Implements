package dev.aaronhowser.mods.irregular_implements.mixin;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Inject(
            method = "renderSnowAndRain",
            at = @At("HEAD")
    )
    private void renderSnowAndRain(LightTexture lightTexture, float partialTick, double camX, double camY, double camZ, CallbackInfo ci) {

    }

}
