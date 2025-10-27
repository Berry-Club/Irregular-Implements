package dev.aaronhowser.mods.irregular_implements.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.aaronhowser.mods.irregular_implements.handler.FluidWalkingHandler;
import dev.aaronhowser.mods.irregular_implements.item.ModArmorItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.CombatEntry;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CombatTracker.class)
abstract public class CombatTrackerMixin {


	@Shadow
	@Final
	private LivingEntity mob;

	@ModifyReturnValue(
			method = "getFallMessage",
			at = @At(
					value = "RETURN",
					ordinal = 2
			)
	)
	private Component irregular_implements$addFallMessage(Component original, CombatEntry combatEntry) {
		return (combatEntry.fallLocation() == FluidWalkingHandler.FLUID_BOOT_FALL)
				? FluidWalkingHandler.fluidWalkingDeathMessage(this.mob)
				: original;
	}

}
