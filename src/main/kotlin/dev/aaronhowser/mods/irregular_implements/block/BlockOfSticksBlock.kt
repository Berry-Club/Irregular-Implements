package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.aaron.isTrue
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.GameRules
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.material.PushReaction

class BlockOfSticksBlock(
	val returning: Boolean
) : Block(
	Properties.of()
		.mapColor(MapColor.SAND)
		.sound(SoundType.SCAFFOLDING)
		.strength(0.3f)
		.pushReaction(PushReaction.DESTROY)
		.isRedstoneConductor(Blocks::never)
		.isValidSpawn(Blocks::never)
		.isSuffocating(Blocks::never)
		.isViewBlocking(Blocks::never)
		.noOcclusion()
) {

	init {
		registerDefaultState(
			defaultBlockState()
				.setValue(SHOULD_DROP, true)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(SHOULD_DROP)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
		return defaultBlockState().setValue(
			SHOULD_DROP,
			context.player?.hasInfiniteMaterials().isTrue.not()  // Only drop if placed by a player with infinite materials
		)
	}

	override fun onPlace(
		pState: BlockState,
		pLevel: Level,
		pPos: BlockPos,
		pOldState: BlockState,
		pMovedByPiston: Boolean
	) {
		if (!pLevel.isClientSide) pLevel.scheduleTick(pPos, this, 20 * 7)
		super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston)
	}

	override fun tick(
		pState: BlockState,
		level: ServerLevel,
		pos: BlockPos,
		random: RandomSource
	) {
		val shouldDrop = pState.getValue(SHOULD_DROP)

		if (!this.returning) {
			level.destroyBlock(pos, shouldDrop)
			return super.tick(pState, level, pos, random)
		}

		if (shouldDrop) {
			if (!level.isClientSide
				&& level.gameRules.getBoolean(GameRules.RULE_DOBLOCKDROPS)
				&& !level.restoringBlockSnapshots
			) {
				val nearestPlayer = level.getNearestPlayer(
					pos.x.toDouble(),
					pos.y.toDouble(),
					pos.z.toDouble(),
					100.0,
					false   // "Should exclude creative players" == false
				)

				val dropPos = nearestPlayer?.position() ?: pos.center

				val drops = getDrops(pState, level, pos, null)
				for (drop in drops) {
					val itemEntity = ItemEntity(level, dropPos.x, dropPos.y, dropPos.z, drop)
					itemEntity.setNoPickUpDelay()
					level.addFreshEntity(itemEntity)

					if (nearestPlayer != null) {
						itemEntity.playerTouch(nearestPlayer)
					}
				}
			}
		}

		level.destroyBlock(pos, false)
		super.tick(pState, level, pos, random)
	}

	companion object {
		val SHOULD_DROP: BooleanProperty = BooleanProperty.create("should_drop")
	}

}