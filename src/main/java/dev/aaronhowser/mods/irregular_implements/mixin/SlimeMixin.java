package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.block.block_entity.SlimeCubeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Slime.class)
public abstract class SlimeMixin {

	@Inject(
			method = "checkSlimeSpawnRules",
			at = @At("HEAD"),
			cancellable = true
	)
	private static void irregular$checkSlimeSpawnRules$inject(
			EntityType<Slime> slime,
			LevelAccessor level,
			MobSpawnType spawnType,
			BlockPos pos,
			RandomSource random,
			CallbackInfoReturnable<Boolean> cir
	) {
		Optional<Boolean> slimeCubeResult = SlimeCubeBlockEntity.slimeCubeResult(level, pos);
		slimeCubeResult.ifPresent(cir::setReturnValue);
	}

}
