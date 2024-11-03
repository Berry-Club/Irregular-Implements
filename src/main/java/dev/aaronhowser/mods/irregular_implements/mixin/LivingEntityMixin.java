package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider;
import dev.aaronhowser.mods.irregular_implements.registries.ModItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(
            method = "shouldDiscardFriction",
            at = @At("HEAD"),
            cancellable = true
    )
    private void shouldDiscardFriction(CallbackInfoReturnable<Boolean> cir) {
        var livingEntity = (LivingEntity) (Object) this;

        if (livingEntity.getDeltaMovement().lengthSqr() > 1f) {
            return;
        }

        if (livingEntity
                .getItemBySlot(EquipmentSlot.FEET)
                .is(ModItems.INSTANCE.getSUPER_LUBRICANT_BOOTS())
        ) {
            cir.setReturnValue(true);
            return;
        }

        if (livingEntity
                .level()
                .getBlockState(
                        livingEntity.getBlockPosBelowThatAffectsMyMovement()
                ).is(ModBlockTagsProvider.Companion.getSUPER_LUBRICATED())
        ) {
            cir.setReturnValue(true);
        }
    }

    @ModifyVariable(
            method = "updateSynchronizedMobEffectParticles",
            at = @At(
                    value = "STORE",
                    target = "Ljava/util/stream/Stream;toList()Ljava/util/List;"
            )
    )
    private List<ParticleOptions> hideMobEffectParticles(List<ParticleOptions> value) {
        if (
                ((LivingEntity) (Object) this)
                        .getItemBySlot(EquipmentSlot.HEAD)
                        .is(ModItems.INSTANCE.getMAGIC_HOOD())
        ) {
            return List.of();
        }
        return value;
    }

}