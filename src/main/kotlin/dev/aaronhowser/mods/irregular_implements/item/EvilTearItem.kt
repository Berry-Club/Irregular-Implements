package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.entity.ArtificialEndPortalEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level

class EvilTearItem : Item(Properties()) {

	companion object {

		private fun tryPlacePortal(level: Level, clickedPos: BlockPos): Boolean {
			val centerPos = clickedPos.below(3)

			val isValidLocation = ArtificialEndPortalEntity.isValidPosition(level, centerPos, checkForOtherPortals = true)
			if (!isValidLocation) return false

			val artificialEndPortalEntity = ArtificialEndPortalEntity(level, centerPos)
			level.addFreshEntity(artificialEndPortalEntity)

			return true
		}
	}

	override fun useOn(context: UseOnContext): InteractionResult {
		val clickedPos = context.clickedPos
		val level = context.level

		if (level.isClientSide) return InteractionResult.PASS

		if (!tryPlacePortal(level, clickedPos)) return InteractionResult.FAIL

		val usedStack = context.itemInHand
		usedStack.consume(1, context.player)

		return InteractionResult.SUCCESS
	}

}