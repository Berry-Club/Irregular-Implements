package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.BiomeRadarBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.Containers
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import net.neoforged.neoforge.items.ItemHandlerHelper

class BiomeRadarBlock : Block(
	Properties
		.ofFullCopy(Blocks.IRON_BLOCK)
		.noOcclusion()
		.isViewBlocking(Blocks::never)
		.isSuffocating(Blocks::never)
), EntityBlock {

	override fun useItemOn(
		clickedStack: ItemStack,
		state: BlockState,
		level: Level,
		pos: BlockPos,
		player: Player,
		hand: InteractionHand,
		hitResult: BlockHitResult
	): ItemInteractionResult {
		if (level.isClientSide || hand == InteractionHand.OFF_HAND) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
		val blockEntity = level.getBlockEntity(pos) as? BiomeRadarBlockEntity ?: return ItemInteractionResult.FAIL

		val storedStack = blockEntity.getBiomeStack()

		if (!storedStack.isEmpty) {
			ItemHandlerHelper.giveItemToPlayer(player, storedStack.copy(), player.inventory.selected)
			blockEntity.setBiomeStack(ItemStack.EMPTY)
			level.playSound(
				null,
				pos,
				SoundEvents.ITEM_FRAME_REMOVE_ITEM,
				SoundSource.BLOCKS,
			)
			return ItemInteractionResult.SUCCESS
		}

		if (!clickedStack.has(ModDataComponents.BIOME)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION

		blockEntity.setBiomeStack(clickedStack.copyWithCount(1))
		clickedStack.consume(1, player)

		level.playSound(
			null,
			pos,
			SoundEvents.ITEM_FRAME_ADD_ITEM,
			SoundSource.BLOCKS,
		)

		return ItemInteractionResult.SUCCESS
	}

	override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, movedByPiston: Boolean) {
		val be = level.getBlockEntity(pos)
		if (be is BiomeRadarBlockEntity) {
			Containers.dropItemStack(level, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, be.getBiomeStack())
		}

		super.onRemove(state, level, pos, newState, movedByPiston)
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return BiomeRadarBlockEntity(pos, state)
	}

	override fun <T : BlockEntity?> getTicker(
		level: Level,
		state: BlockState,
		blockEntityType: BlockEntityType<T>
	): BlockEntityTicker<T>? {
		return BaseEntityBlock.createTickerHelper(
			blockEntityType,
			ModBlockEntities.BIOME_RADAR.get(),
			BiomeRadarBlockEntity::tick
		)
	}

	override fun getShadeBrightness(state: BlockState, level: BlockGetter, pos: BlockPos): Float = 1f
	override fun propagatesSkylightDown(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean = true
	override fun getVisualShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape = Shapes.empty()

}