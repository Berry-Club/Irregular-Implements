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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public class LevelMixin implements RainShieldChunks {

    @Inject(
            method = "isRainingAt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;getBiome(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/Holder;"
            ),
            cancellable = true
    )
    private void isRainingAt(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (RainShieldBlockEntity.Companion.chunkHasActiveRainShield((Level) (Object) this, pos)) {
            cir.setReturnValue(false);
        }
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
    public LongOpenHashSet irregular_implements$getRainShieldChunks() {
        return this.irregular_implements$rainShieldChunks;
    }

}
