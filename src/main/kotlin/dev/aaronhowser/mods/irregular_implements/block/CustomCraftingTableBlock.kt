package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isBlock
import dev.aaronhowser.mods.irregular_implements.block.block_entity.CustomCraftingTableBlockEntity
import dev.aaronhowser.mods.irregular_implements.item.CustomCraftingTableBlockItem
import net.minecraft.core.BlockPos
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionResult
import net.minecraft.world.MenuProvider
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.CraftingMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class CustomCraftingTableBlock : Block(Properties.ofFullCopy(Blocks.CRAFTING_TABLE)), EntityBlock {

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return CustomCraftingTableBlockEntity(pos, state)
	}

	override fun getOcclusionShape(state: BlockState, level: BlockGetter, pos: BlockPos): VoxelShape {
		return Shapes.empty()
	}

	override fun getRenderShape(state: BlockState): RenderShape {
		return RenderShape.ENTITYBLOCK_ANIMATED
	}

	override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {
		if (level.isClientSide) {
			return InteractionResult.SUCCESS
		} else {
			player.openMenu(state.getMenuProvider(level, pos))
			player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE)
			return InteractionResult.CONSUME
		}
	}

	override fun getMenuProvider(state: BlockState, level: Level, pos: BlockPos): MenuProvider {
		return SimpleMenuProvider(
			{ containerId, playerInventory, player ->
				CraftingMenu(containerId, playerInventory, ContainerLevelAccess.create(level, pos))
			},
			this.name
		)
	}

	// Stuff that uses the BE's rendered block state

	override fun getSoundType(state: BlockState, level: LevelReader, pos: BlockPos, entity: Entity?): SoundType {
		val blockEntity = level.getBlockEntity(pos) as? CustomCraftingTableBlockEntity
			?: return super.getSoundType(state, level, pos, entity)

		// Failsafe
		val renderedState = blockEntity.renderedBlockState
		if (renderedState.isBlock(this)) return SoundType.WOOD

		return renderedState.getSoundType(level, pos, entity)
	}

	override fun spawnDestroyParticles(level: Level, player: Player, pos: BlockPos, state: BlockState) {
		val blockEntity = level.getBlockEntity(pos) as? CustomCraftingTableBlockEntity
			?: return super.spawnDestroyParticles(level, player, pos, state)

		level.levelEvent(
			player,
			LevelEvent.PARTICLES_DESTROY_BLOCK,
			pos,
			getId(blockEntity.renderedBlockState)
		)
	}

	override fun getCloneItemStack(state: BlockState, target: HitResult, level: LevelReader, pos: BlockPos, player: Player): ItemStack {
		val blockEntity = level.getBlockEntity(pos) as? CustomCraftingTableBlockEntity ?: return ItemStack.EMPTY

		return CustomCraftingTableBlockItem.ofBlock(blockEntity.renderedBlockState.block)
	}

}