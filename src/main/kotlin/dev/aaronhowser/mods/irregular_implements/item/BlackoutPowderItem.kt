package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.entity.IlluminatorEntity
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.phys.AABB

class BlackoutPowderItem : Item(Properties()) {

    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level

        val clickedPos = context.clickedPos

        val chunkPos = ChunkPos(clickedPos)
        val minX = chunkPos.minBlockX
        val minY = level.minBuildHeight
        val minZ = chunkPos.minBlockZ
        val maxX = chunkPos.maxBlockX
        val maxY = level.maxBuildHeight
        val maxZ = chunkPos.maxBlockZ

        val aabb = AABB(minX.toDouble(), minY.toDouble(), minZ.toDouble(), maxX.toDouble(), maxY.toDouble(), maxZ.toDouble())

        val illuminators = level.getEntitiesOfClass(IlluminatorEntity::class.java, aabb)

        if (illuminators.isEmpty()) return InteractionResult.FAIL

        for (illuminator in illuminators) {
            illuminator.discard()
        }

        context.itemInHand.consume(1, context.player)

        return InteractionResult.SUCCESS
    }

}