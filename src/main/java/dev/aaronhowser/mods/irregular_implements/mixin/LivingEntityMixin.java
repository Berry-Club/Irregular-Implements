package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.LivingEntityFunctions;
import dev.aaronhowser.mods.irregular_implements.block.BeanStalkBlock;
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModItemTagsProvider;
import dev.aaronhowser.mods.irregular_implements.registry.ModMobEffects;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
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
		return (getItemBySlot(EquipmentSlot.HEAD).is(ModItemTagsProvider.HIDE_POTION_HELMET))
				? List.of()
				: original;
	}

	@ModifyConstant(
			method = {"travel", "handleRelativeFrictionAndCalculateMovement"},
			constant = @Constant(doubleValue = 0.2)
	)
	private double irregular_implements$fasterOnStalk(double constant) {
		return constant * BeanStalkBlock.climbingFactor((LivingEntity) (Object) this);
	}

	@ModifyVariable(
			method = "travel",
			at = @At("HEAD"),
			argsOnly = true
	)
	private Vec3 irregular_implements$invertCollapsedMovement(Vec3 movement) {
		LivingEntity entity = (LivingEntity) (Object) this;
		if (entity instanceof Player || !entity.hasEffect(ModMobEffects.COLLAPSE)) return movement;

		return new Vec3(-movement.x, movement.y, -movement.z);
	}

	@ModifyConstant(
			method = "handleOnClimbable",
			constant = @Constant(doubleValue = -0.15000000596046448)
	)
	private double irregular_implements$climbFaster(double constant) {
		return constant * BeanStalkBlock.climbingFactor((LivingEntity) (Object) this);
	}

}