package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.nextRange
import dev.aaronhowser.mods.irregular_implements.block.ItemCollectorBlock
import dev.aaronhowser.mods.irregular_implements.item.component.ItemFilterDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.items.ItemHandlerHelper

open class ItemCollectorBlockEntity(
	blockEntity: BlockEntityType<out ItemCollectorBlockEntity>,
	pPos: BlockPos,
	pBlockState: BlockState
) : BlockEntity(blockEntity, pPos, pBlockState) {

	constructor(pos: BlockPos, blockState: BlockState) : this(ModBlockEntityTypes.ITEM_COLLECTOR.get(), pos, blockState)

	protected fun tick() {
		val level = this.level ?: return
		if (level.isClientSide || level.gameTime % 5 != 0L) return

		val facing = this.blockState.getValue(ItemCollectorBlock.FACING)
		val onPos = this.blockPos.relative(facing)

		val itemHandler = level.getCapability(Capabilities.ItemHandler.BLOCK, onPos, facing.opposite) ?: return

		val itemEntities = level.getEntitiesOfClass(
			ItemEntity::class.java,
			this.getCollectionArea(),
		)

		val filter = this.getFilter()

		for (itemEntity in itemEntities) {
			if (itemEntity.target != null || itemEntity.hasPickUpDelay()) continue

			val stack = itemEntity.item
			if (filter != null && !filter.test(stack)) continue

			val oldCount = stack.count
			val newStack = ItemHandlerHelper.insertItemStacked(itemHandler, stack, false)
			val newCount = newStack.count

			if (oldCount == newCount) continue

			level.playSound(
				null,
				this.blockPos,
				SoundEvents.ITEM_PICKUP,
				SoundSource.BLOCKS,
				0.2f,
				level.random.nextRange(0.6f, 3.4f)
			)

			if (newStack.isEmpty) {
				itemEntity.discard()
			} else {
				itemEntity.item = newStack
			}
		}
	}

	open fun getFilter(): ItemFilterDataComponent? {
		return null
	}

	open fun getCollectionArea(): AABB {
		val pos = this.blockPos
		val radius = 3.0
		return AABB.ofSize(
			pos.center,
			2 * radius,
			2 * radius,
			2 * radius
		)
	}


	companion object {
		fun tick(
			level: Level,
			blockPos: BlockPos,
			blockState: BlockState,
			blockEntity: ItemCollectorBlockEntity
		) {
			blockEntity.tick()
		}
	}

}