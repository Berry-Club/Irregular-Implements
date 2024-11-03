package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(
            method = "shouldDiscardFriction",
            at = @At("HEAD"),
            cancellable = true
    )
    private void shouldDiscardFriction(CallbackInfoReturnable<Boolean> cir) {
        var livingEntity = (LivingEntity) (Object) this;
        var standingOnLubricatedBlock = livingEntity
                .level()
                .getBlockState(
                        livingEntity.getBlockPosBelowThatAffectsMyMovement()
                ).is(ModBlockTagsProvider.Companion.getSUPER_LUBRICATED());

        if (standingOnLubricatedBlock) {
            cir.setReturnValue(true);
        }
    }

}