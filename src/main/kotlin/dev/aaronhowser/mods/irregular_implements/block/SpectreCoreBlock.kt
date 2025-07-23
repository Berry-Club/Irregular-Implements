package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModDimensions
import dev.aaronhowser.mods.irregular_implements.handler.spectre_cube.SpectreCubeSavedData
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class SpectreCoreBlock : Block(Properties.ofFullCopy(Blocks.BEDROCK)) {

	override fun useItemOn(
		stack: ItemStack,
		state: BlockState,
		level: Level,
		pos: BlockPos,
		player: Player,
		hand: InteractionHand,
		hitResult: BlockHitResult
	): ItemInteractionResult {
		if (level !is ServerLevel || level.dimension() != ModDimensions.SPECTRE_LEVEL_KEY) return ItemInteractionResult.SUCCESS

		val handler = SpectreCubeSavedData.get(level)

		if (stack.`is`(ModItems.ECTOPLASM)) {
			val cube = handler.getSpectreCubeFromBlockPos(level, pos)
			val amount = cube?.increaseHeight(stack.count) ?: 0

			stack.consume(amount, player)
		} else if (stack.isEmpty && hand == InteractionHand.MAIN_HAND) {
			handler.teleportPlayerBack(player as ServerPlayer)
		}


		return ItemInteractionResult.SUCCESS
	}

}