package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.entity.SpectreIlluminatorEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.toStack
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.phys.AABB

class BlackoutPowderItem(properties: Properties) : Item(properties) {

	override fun useOn(context: UseOnContext): InteractionResult {
		val level = context.level

		val clickedPos = context.clickedPos

		val chunkPos = ChunkPos(clickedPos)
		val minX = chunkPos.minBlockX
		val minY = level.minBuildHeight
		val minZ = chunkPos.minBlockZ
		val maxX = chunkPos.maxBlockX
		val maxY = level.maxBuildHeight + SpectreIlluminatorEntity.HEIGHT_ABOVE_MAX_BLOCK * 3
		val maxZ = chunkPos.maxBlockZ

		val aabb = AABB(minX.toDouble(), minY.toDouble(), minZ.toDouble(), maxX.toDouble(), maxY.toDouble(), maxZ.toDouble())

		val illuminators = level.getEntitiesOfClass(SpectreIlluminatorEntity::class.java, aabb)

		if (illuminators.isEmpty()) return InteractionResult.FAIL

		val player = context.player

		for (illuminator in illuminators) {

			illuminator.discard()

			if (!player?.isCreative.isTrue) {
				OtherUtil.dropStackAt(
					ModItems.SPECTRE_ILLUMINATOR.toStack(),
					illuminator.level(),
					clickedPos.relative(context.clickedFace).center
				)
			}

		}

		if (!player?.isCreative.isTrue) {
			context.itemInHand.shrink(1)
		}

		return InteractionResult.SUCCESS
	}

}