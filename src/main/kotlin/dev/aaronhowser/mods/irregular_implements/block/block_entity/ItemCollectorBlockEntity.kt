package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.ItemCollectorBlock
import dev.aaronhowser.mods.irregular_implements.item.component.ItemFilterDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.core.BlockPos
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

	constructor(pos: BlockPos, blockState: BlockState) : this(ModBlockEntities.ITEM_COLLECTOR.get(), pos, blockState)

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
			if (filter != null && !filter.test(stack).isTrue) continue

			val newStack = ItemHandlerHelper.insertItemStacked(itemHandler, stack, false)

			if (newStack.isEmpty) {
				itemEntity.discard()
				continue
			}

			itemEntity.item = newStack
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

}