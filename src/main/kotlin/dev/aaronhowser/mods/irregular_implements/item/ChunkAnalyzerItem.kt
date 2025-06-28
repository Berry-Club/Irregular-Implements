package dev.aaronhowser.mods.irregular_implements.item

import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import org.apache.commons.lang3.mutable.MutableInt
import java.util.*

//TODO: Either add entity support, or remove it from the info and book pages
class ChunkAnalyzerItem(properties: Properties) : Item(properties) {

	//TODO GUI

	override fun use(
		level: Level,
		player: Player,
		usedHand: InteractionHand
	): InteractionResultHolder<ItemStack> {
		val usedStack = player.getItemInHand(usedHand)
		if (level.isClientSide) return InteractionResultHolder.sidedSuccess(usedStack, true)

		val chunk = level.getChunkAt(player.blockPosition())

		val blockStateCounts: IdentityHashMap<Block, MutableInt> = IdentityHashMap()

		val minX = chunk.pos.minBlockX
		val maxX = chunk.pos.maxBlockX
		val minZ = chunk.pos.minBlockZ
		val maxZ = chunk.pos.maxBlockZ
		val minY = chunk.minBuildHeight
		val maxY = chunk.maxBuildHeight

		for (x in minX..maxX) {
			for (z in minZ..maxZ) {
				for (y in minY..maxY) {
					val blockState = chunk.getBlockState(BlockPos(x, y, z))
					if (blockState.isAir) continue

					blockStateCounts
						.computeIfAbsent(blockState.block) { MutableInt(0) }
						.increment()
				}
			}
		}

		for ((block, count) in blockStateCounts.entries.sortedByDescending { it.value }) {

			player.sendSystemMessage(
				Component.literal(
					"${block.name.string} x${count.value}"
				)
			)

		}

		return InteractionResultHolder.success(usedStack)
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)
	}

}