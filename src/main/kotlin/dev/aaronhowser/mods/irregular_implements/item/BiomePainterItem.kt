package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.chunk.ChunkAccess

class BiomePainterItem : Item(
    Properties()
        .stacksTo(1)
) {

    override fun useOn(context: UseOnContext): InteractionResult {
        val player = context.player ?: return InteractionResult.FAIL

        val level = context.level
        val clickedPos = context.clickedPos

        val clickedBiome = level.getBiome(clickedPos)

        val firstNonEmptyCapsule = player.inventory.items.find {
            val component = it.get(ModDataComponents.BIOME_POINTS) ?: return@find false

            return@find component.biome != clickedBiome && component.points > 0
        } ?: return InteractionResult.FAIL

        val chunkAccessList: MutableList<ChunkAccess> = mutableListOf()

        for (dX in -1..1) {
            for (dY in -1..1) {
                for (dZ in -1..1) {
                    val pos = clickedPos.offset(dX, dY, dZ)
                    if (!level.isLoaded(pos)) continue

                    val chunkAccess = level.getChunkAt(pos)
                    chunkAccessList.add(chunkAccess)
                }
            }
        }

        for (chunkAccess in chunkAccessList) {
            chunkAccess.fillBiomesFromNoise()
        }

    }
}