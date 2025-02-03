package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.registry.ModEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MoveControl.class)
public class MoveControlMixin {

    @Shadow
    @Final
    protected Mob mob;

    @ModifyVariable(
            method = "tick",
            at = @At(
                    value = "STORE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getAttributeValue(Lnet/minecraft/core/Holder;)D"
            )
    )
    private float invertStrafeSpeed(float f) {
        return (this.mob.hasEffect(ModEffects.COLLAPSE)) ? -f : f;
    }


}
