package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.FlooBrickBlockEntity
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.handler.floo.FlooNetworkSavedData
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.component.DataComponents
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import java.util.*

class FlooSignItem(properties: Properties) : Item(properties) {

	override fun useOn(context: UseOnContext): InteractionResult {
		val level = context.level
		if (level.isClientSide) return InteractionResult.PASS

		val player = context.player ?: return InteractionResult.PASS

		val clickedPos = context.clickedPos
		val clickedState = level.getBlockState(clickedPos)

		if (!clickedState.`is`(ModBlockTagsProvider.CONVERTS_TO_FLOO_BRICK)) return InteractionResult.PASS

		if (createFlooBricks(context.itemInHand, player, clickedPos)) return InteractionResult.SUCCESS

		return InteractionResult.PASS
	}

	companion object {
		private fun createFlooBricks(stack: ItemStack, player: Player, clickedPos: BlockPos): Boolean {
			val level = player.level() as? ServerLevel ?: return false

			val name = stack.get(DataComponents.CUSTOM_NAME)?.string
			val facing = player.direction

			val blocksToConvert = findConvertibleBlocks(level, clickedPos).toList()
			if (blocksToConvert.isEmpty()) return false

			val masterUuid = UUID.randomUUID()

			val canCreate = FlooNetworkSavedData.get(level).createFireplace(
				masterUuid = masterUuid,
				name = name,
				blockPos = clickedPos,
				connectedBricks = blocksToConvert
			)

			if (!canCreate) return false

			for (pos in blocksToConvert) {
				level.setBlockAndUpdate(pos, ModBlocks.FLOO_BRICK.get().defaultBlockState())
				val blockEntity = level.getBlockEntity(pos) as? FlooBrickBlockEntity ?: continue

				if (pos == clickedPos) {
					blockEntity.setupMaster(masterUuid, facing.opposite, blocksToConvert)
				} else {
					blockEntity.setupChild(masterUuid)
				}
			}

			return true
		}

		private fun findConvertibleBlocks(level: Level, startPos: BlockPos): Set<BlockPos> {
			val toCheck = mutableListOf(startPos)
			val checked = mutableSetOf<BlockPos>()
			val found = mutableSetOf<BlockPos>()

			while (toCheck.isNotEmpty() && found.size < 256) {
				val checkedPos = toCheck.removeFirst()
				checked.add(checkedPos)

				val state = level.getBlockState(checkedPos)
				if (state.`is`(ModBlockTagsProvider.CONVERTS_TO_FLOO_BRICK)) {
					found.add(checkedPos)

					for (dir in Direction.entries) {
						if (dir.axis.isVertical) continue
						val offsetPos = checkedPos.relative(dir)
						if (offsetPos in checked || offsetPos in toCheck) continue
						toCheck.add(offsetPos)
					}
				}
			}

			return found
		}
	}

}