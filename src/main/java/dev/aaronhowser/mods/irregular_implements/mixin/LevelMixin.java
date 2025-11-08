package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.*;
import dev.aaronhowser.mods.irregular_implements.block.block_entity.RainShieldBlockEntity;
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneInterfaceBlockEntity;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class LevelMixin implements RainShieldCarrier, PeaceCandleCarrier, RedstoneInterfaceCarrier, SlimeCubeCarrier, EnderAnchorCarrier {

	@Unique
	LongOpenHashSet irregular_implements$peaceCandleChunks = new LongOpenHashSet();

	@Unique
	LongOpenHashSet irregular_implements$rainShieldChunks = new LongOpenHashSet();

	@Unique
	LongOpenHashSet irregularImplements$slimeCubeBlockPositions = new LongOpenHashSet();

	@Unique
	LongOpenHashSet irregular_implements$enderAnchorBlockPositions = new LongOpenHashSet();

	@Inject(
			method = "tickBlockEntities",
			at = @At("HEAD")
	)
	private void irregular_implements$tickBlockEntities(CallbackInfo ci) {

		irregular_implements$getRainShieldChunks().clear();
		irregular_implements$getPeaceCandleChunks().clear();
		irregular_implements$getSlimeCubeBlockPositions().clear();
		irregular_implements$getEnderAnchorBlockPositions().clear();

		// Doing it here because it's the only way to guarantee that it runs before the set is added to, rather than before the set is checked.
		// I was doing it on LevelTickEvent before, but neither Pre not Post worked. The order that it was going was:
		// 1. LevelTickEvent.Pre
		// 2. The set is checked
		// 3. The set is added to
		// 4. LevelTickEvent.Post
		// So no matter if the event is checked on Pre or Post, the set will always be empty when checked.
		// Doing it this way adds a single tick delay, but honestly that's fine.
	}

	@Unique
	@Override
	public LongOpenHashSet irregular_implements$getRainShieldChunks() {
		return this.irregular_implements$rainShieldChunks;
	}

	@Unique
	@Override
	public LongOpenHashSet irregular_implements$getPeaceCandleChunks() {
		return this.irregular_implements$peaceCandleChunks;
	}

	@Override
	public LongOpenHashSet irregular_implements$getSlimeCubeBlockPositions() {
		return this.irregularImplements$slimeCubeBlockPositions;
	}

	@Unique
	public LongOpenHashSet irregular_implements$getEnderAnchorBlockPositions() {
		return this.irregular_implements$enderAnchorBlockPositions;
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

	@Override
	public int irregular_implements$getLinkedInterfacePower(BlockPos blockPos, @Nullable Direction direction) {
		return RedstoneInterfaceBlockEntity
				.getLinkedPower(
						(Level) (Object) this,
						direction == null
								? blockPos
								: blockPos.relative(direction.getOpposite())
				);
	}
}
