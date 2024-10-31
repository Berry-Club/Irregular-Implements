package dev.aaronhowser.mods.irregular_implements.mixin;

import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LevelChunk.class)
public class LevelChunkMixins implements RainShieldsPerChunk {

    /**
     * At the start of every tick, every chunk sets the count to 0.
     * <p>
     * Every time a RainShieldBlockEntity ticks, it increments the count for the chunk it is in.
     * <p>
     * This amount is checked by BiomeMixins and LevelMixins to see if it should rain in the chunk.
     */

    @Unique
    private int irregular_implements$rainShieldCount = 0;

    @Override
    public void irregular_implements$setRainShieldCount(int count) {
        this.irregular_implements$rainShieldCount = count;
    }

    @Override
    public int irregular_implements$getRainShieldCount() {
        return this.irregular_implements$rainShieldCount;
    }

    //TODO: At the start of each tick, every chunk sets the amount to 0

}
