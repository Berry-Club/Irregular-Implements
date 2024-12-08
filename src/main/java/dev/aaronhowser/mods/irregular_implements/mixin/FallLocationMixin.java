package dev.aaronhowser.mods.irregular_implements.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.aaronhowser.mods.irregular_implements.item.ModArmorItems;
import net.minecraft.world.damagesource.FallLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FallLocation.class)
public class FallLocationMixin {

    @ModifyReturnValue(
            method = "getCurrentFallLocation",
            at = @At("RETURN")
    )
    private static FallLocation irregular_implements$fallOnWaterMessage(FallLocation original, LivingEntity entity) {
        if (original != null) return original;

        return ModArmorItems.fluidWalkingFallLocation(entity);
    }

}
