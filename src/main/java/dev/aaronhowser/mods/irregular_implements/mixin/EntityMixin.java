package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.registry.ModEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    public abstract void setDeltaMovement(Vec3 deltaMovement);

    @Inject(
            method = "setDeltaMovement(DDD)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onSetDeltaMovement(double x, double y, double z, CallbackInfo ci) {
        if (((Entity) (Object) this) instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(ModEffects.COLLAPSE)) {
                this.setDeltaMovement(new Vec3(-x, y, -z));
                ci.cancel();
            }
        }
    }

}
