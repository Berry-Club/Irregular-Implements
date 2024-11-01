package dev.aaronhowser.mods.irregular_implements.mixin;

import dev.aaronhowser.mods.irregular_implements.RainShieldChunks;
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
public abstract class LevelMixin implements RainShieldChunks {

    @Inject(
            method = "isRainingAt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;getBiome(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/Holder;"
            ),
            cancellable = true
    )
    private void isRainingAt(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (RainShieldBlockEntity.Companion.chunkIsProtectedFromRain((Level) (Object) this, pos)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(
            method = "tickBlockEntities",
            at = @At("HEAD")
    )
    private void tickBlockEntities(CallbackInfo ci) {
        irregular_implements$clearRainShieldChunks();

        // Doing it here because it's the only way to guarantee that it runs before the set is added to, rather than before the set is checked.
        // I was doing it on LevelTickEvent before, but neither Pre not Post worked. The order that it was going was:
        // 1. LevelTickEvent.Pre
        // 2. The set is checked
        // 3. The set is added to
        // 4. LevelTickEvent.Post
        // So no matter if the event is checked on Pre or Post, the set will always be empty when checked.
        // Doing it this way adds a single tick delay, but honestly that's fine.
    }


    // The Long is the chunk pos converted to long `ChunkPos.toLong()`
    @Unique
    LongOpenHashSet irregular_implements$rainShieldChunks = new LongOpenHashSet();

    @Unique
    @Override
    public boolean irregular_implements$addChunkPos(long pos) {
        return this.irregular_implements$rainShieldChunks.add(pos);
    }

    @Unique
    @Override
    public boolean irregular_implements$removeChunkPos(long pos) {
        return this.irregular_implements$rainShieldChunks.remove(pos);
    }

    @Unique
    @Override
    public boolean irregular_implements$chunkPosHasRainShields(long pos) {
        return this.irregular_implements$rainShieldChunks.contains(pos);
    }

    @Unique
    @Override
    public void irregular_implements$clearRainShieldChunks() {
        this.irregular_implements$rainShieldChunks.clear();
    }

    @Unique
    @Override
    public LongOpenHashSet irregular_implements$getRainShieldChunks() {
        return this.irregular_implements$rainShieldChunks;
    }

}
