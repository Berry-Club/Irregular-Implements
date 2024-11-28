package dev.aaronhowser.mods.irregular_implements.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider;
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModFluidTagsProvider;
import dev.aaronhowser.mods.irregular_implements.registries.ModItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    @Inject(
            method = "shouldDiscardFriction",
            at = @At("HEAD"),
            cancellable = true
    )
    private void irregular_implements$shouldDiscardFriction(CallbackInfoReturnable<Boolean> cir) {
        if (getDeltaMovement().lengthSqr() > 1f) return;

        if (this.getItemBySlot(EquipmentSlot.FEET)
                .is(ModItems.getSUPER_LUBRICANT_BOOTS())
        ) {
            cir.setReturnValue(true);
            return;
        }

        if (this.level()
                .getBlockState(this.getBlockPosBelowThatAffectsMyMovement())
                .is(ModBlockTagsProvider.getSUPER_LUBRICATED())
        ) {
            cir.setReturnValue(true);
        }
    }


    @ModifyVariable(
            method = "tickEffects",
            at = @At(
                    value = "STORE",
                    target = "Lnet/minecraft/network/syncher/SynchedEntityData;get(Lnet/minecraft/network/syncher/EntityDataAccessor;)Ljava/lang/Object;",
                    ordinal = 0
            )
    )
    private List<ParticleOptions> irregular_implements$hideMobEffectParticles(List<ParticleOptions> original) {
        if (this.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.INSTANCE.getMAGIC_HOOD())) {
            return List.of();
        }
        return original;
    }

    @ModifyReturnValue(
            method = "canStandOnFluid",
            at = @At("RETURN")
    )
    private boolean irregular_implements$canStandOnFluid(boolean original, FluidState fluidState) {
        if (original) return true;

        var wornBoots = this.getItemBySlot(EquipmentSlot.FEET);

        if (fluidState.is(ModFluidTagsProvider.getALLOWS_WATER_WALKING())) {
            return wornBoots.is(ModItems.INSTANCE.getWATER_WALKING_BOOTS());
        } else if (fluidState.is(ModFluidTagsProvider.getALLOWS_LAVA_WALKING())) {
            return wornBoots.is(ModItems.INSTANCE.getLAVA_WADERS());
        }

        return false;
    }

}