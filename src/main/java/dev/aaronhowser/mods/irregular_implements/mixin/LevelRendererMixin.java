package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.RainShieldChunks;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

    @Shadow
    @Nullable
    private ClientLevel level;

    @Redirect(
            method = {"renderSnowAndRain", "tickRain"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitationAt(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/biome/Biome$Precipitation;"
            )
    )
    private Biome.Precipitation irregular_implements$getPrecipitationAt(Biome biome, BlockPos pos) {
        if (level instanceof RainShieldChunks l) {
            var chunkPos = ChunkPos.asLong(pos.getX() >> 4, pos.getZ() >> 4);
            if (l.irregular_implements$chunkHasRainShield(chunkPos)) {
                return Biome.Precipitation.NONE;
            }
        }

        return biome.getPrecipitationAt(pos);
    }

}