package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.block.block_entity.SpectreLensBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(BeaconBlockEntity.class)
public class BeaconBlockEntityMixin {

    @Inject(
            method = "applyEffects",
            at = @At("HEAD")
    )
    private static void irregular_implements$spectreLens(
            Level level,
            BlockPos pos,
            int beaconLevel,
            @Nullable Holder<MobEffect> primaryEffect,
            @Nullable Holder<MobEffect> secondaryEffect,
            CallbackInfo ci
    ) {
        SpectreLensBlockEntity.applyEffects(level, pos, beaconLevel, primaryEffect, secondaryEffect);
    }

}
