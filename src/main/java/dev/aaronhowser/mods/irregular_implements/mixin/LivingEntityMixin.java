package dev.aaronhowser.mods.irregular_implements.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.aaronhowser.mods.irregular_implements.LivingEntityFunctions;
import dev.aaronhowser.mods.irregular_implements.block.BeanStalk;
import dev.aaronhowser.mods.irregular_implements.registry.ModItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityFunctions {

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
        if (checkShouldDiscardFriction()) cir.setReturnValue(true);
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
        return (this.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.INSTANCE.getMAGIC_HOOD()))
                ? List.of()
                : original;
    }

    @WrapOperation(
            method = "handleOnClimbable",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Math;max(DD)D",
                    ordinal = 0
            )
    )
    private double irregular_implements$climbingBeanStalk(double a, double b, Operation<Double> original) {
        double originalValue = original.call(a, b);
        return originalValue * BeanStalk.climbingFactor((LivingEntity) (Object) this);
    }

}