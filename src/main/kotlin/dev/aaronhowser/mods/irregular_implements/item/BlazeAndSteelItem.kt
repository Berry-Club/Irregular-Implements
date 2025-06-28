package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.block.BlazeFireBlock
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.FlintAndSteelItem
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.BaseFireBlock
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.gameevent.GameEvent
import net.neoforged.neoforge.common.ItemAbilities

class BlazeAndSteelItem(properties: Properties) : FlintAndSteelItem(properties) {

	override fun useOn(context: UseOnContext): InteractionResult {
		val originalState = context.level.getBlockState(context.clickedPos)
		val modifiedState = originalState.getToolModifiedState(context, ItemAbilities.FIRESTARTER_LIGHT, false)

		return if (modifiedState != null) tryModifyBlock(context) else tryPlaceFire(context)
	}

	private fun tryModifyBlock(context: UseOnContext): InteractionResult {
		val player = context.player
		val level = context.level
		val blockPos = context.clickedPos
		val newState = level
			.getBlockState(blockPos)
			.getToolModifiedState(context, ItemAbilities.FIRESTARTER_LIGHT, false)
			?: return InteractionResult.PASS

		level.playSound(
			player,
			blockPos,
			SoundEvents.FLINTANDSTEEL_USE,
			SoundSource.BLOCKS,
			1f,
			level.random.nextFloat() * 0.4f + 0.8f
		)

		level.setBlock(blockPos, newState, 11)
		level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos)
		if (player != null) {
			context.itemInHand.hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.hand))
		}

		return InteractionResult.sidedSuccess(level.isClientSide())
	}

	private fun tryPlaceFire(context: UseOnContext): InteractionResult {
		val player = context.player
		val level = context.level

		val firePos = context.clickedPos.relative(context.clickedFace)
		if (!BaseFireBlock.canBePlacedAt(level, firePos, context.horizontalDirection)) return InteractionResult.FAIL

		level.playSound(
			player,
			firePos,
			SoundEvents.FLINTANDSTEEL_USE,
			SoundSource.BLOCKS,
			1.0f,
			level.random.nextFloat() * 0.4f + 0.8f
		)

		val potentialFireState = BaseFireBlock.getState(level, firePos)
		val fireState = if (potentialFireState.block == Blocks.SOUL_FIRE) {
			potentialFireState
		} else {
			(ModBlocks.BLAZE_FIRE.get() as BlazeFireBlock).getStateForPlacement(level, firePos)
		}

		level.setBlock(firePos, fireState, 11)
		level.gameEvent(player, GameEvent.BLOCK_PLACE, firePos)
		val itemstack = context.itemInHand
		if (player is ServerPlayer) {
			CriteriaTriggers.PLACED_BLOCK.trigger(player, firePos, itemstack)
			itemstack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.hand))
		}

		return InteractionResult.sidedSuccess(level.isClientSide())
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().durability(64)
	}
}
