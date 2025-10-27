package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.RainShieldCarrier;
import dev.aaronhowser.mods.irregular_implements.block.block_entity.RainShieldBlockEntity;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class LevelRainMixin implements RainShieldCarrier {

	@Unique
	LongOpenHashSet irregular_implements$rainShieldChunks = new LongOpenHashSet();

	@Unique
	@Override
	public LongOpenHashSet irregular_implements$getRainShieldChunks() {
		return this.irregular_implements$rainShieldChunks;
	}

	@Inject(
			method = "tickBlockEntities",
			at = @At("HEAD")
	)
	private void irregular_implements$tickBlockEntities(CallbackInfo ci) {
		irregular_implements$getRainShieldChunks().clear();

		// Doing it here because it's the only way to guarantee that it runs before the set is added to, rather than before the set is checked.
		// I was doing it on LevelTickEvent before, but neither Pre not Post worked. The order that it was going was:
		// 1. LevelTickEvent.Pre
		// 2. The set is checked
		// 3. The set is added to
		// 4. LevelTickEvent.Post
		// So no matter if the event is checked on Pre or Post, the set will always be empty when checked.
		// Doing it this way adds a single tick delay, but honestly that's fine.
	}


	@Inject(
			method = "isRainingAt",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/Level;getBiome(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/Holder;"
			),
			cancellable = true
	)
	private void irregular_implements$rainShieldStopsRain(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (RainShieldBlockEntity.chunkIsProtectedFromRain((Level) (Object) this, pos)) {
			cir.setReturnValue(false);
		}
	}

}
