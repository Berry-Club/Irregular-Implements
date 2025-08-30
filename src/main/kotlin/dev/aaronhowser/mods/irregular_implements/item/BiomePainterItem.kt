package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.client.render.BiomePainterRenderer
import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.Holder
import net.minecraft.server.commands.FillBiomeCommand
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.phys.AABB
import kotlin.jvm.optionals.getOrNull

class BiomePainterItem(properties: Properties) : Item(properties) {

	//FIXME: Somehow making some blockpos immune to biome change??? maybe???
	override fun useOn(context: UseOnContext): InteractionResult {
		val player = context.player ?: return InteractionResult.FAIL

		val level = context.level as? ServerLevel ?: return InteractionResult.PASS
		val clickedPos = context.clickedPos

		val firstNonEmptyCapsule = BiomeCapsuleItem.getFirstNonEmptyCapsule(player.inventory)
		val component = firstNonEmptyCapsule?.get(ModDataComponents.BIOME_POINTS) ?: return InteractionResult.FAIL

		val biomeToPlace = component.biome
		val points = component.points

		//TODO: Make this configurable in-game with a GUI
		val horizontalRadius = ServerConfig.BIOME_PAINTER_HORIZONTAL_RADIUS.get()
		val blocksBelow = ServerConfig.BIOME_PAINTER_BLOCKS_BELOW.get()
		val blocksAbove = ServerConfig.BIOME_PAINTER_BLOCKS_ABOVE.get()

		var counter = 0

		val result = FillBiomeCommand.fill(
			level,
			clickedPos.offset(-horizontalRadius, -blocksBelow, -horizontalRadius),
			clickedPos.offset(horizontalRadius, blocksAbove, horizontalRadius),
			biomeToPlace,
			{ checkedBiome -> checkedBiome != biomeToPlace && counter++ <= points },
			{ _ -> }
		)

		val amountChanged = result.left().orElse(0)
		if (amountChanged == 0) return InteractionResult.FAIL

		if (amountChanged < points) {
			firstNonEmptyCapsule.set(ModDataComponents.BIOME_POINTS, component.withLessPoints(amountChanged))
		} else {
			firstNonEmptyCapsule.remove(ModDataComponents.BIOME_POINTS)
		}

		return InteractionResult.SUCCESS
	}

	override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
		val stack = player.getItemInHand(usedHand)

		if (level.isClientSide) {
			val biome = BiomeCapsuleItem.getBiomeToPaint(player.inventory)
		}

		return InteractionResultHolder.pass(stack)
	}

	override fun inventoryTick(stack: ItemStack, level: Level, entity: Entity, slotId: Int, isSelected: Boolean) {
		if (isSelected && level.isClientSide) {
			BiomePainterRenderer.indicatePositions(entity)
		}
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)
	}

	data class Positions(
		val correctBiomePositions: Set<BlockPos>,
		val incorrectBiomePositions: Set<BlockPos>,
		val targetedIncorrectBiomePosition: BlockPos?,
	) {
		val unselectedIncorrectBiomePositions: Set<BlockPos> =
			if (targetedIncorrectBiomePosition != null) {
				incorrectBiomePositions - targetedIncorrectBiomePosition
			} else {
				incorrectBiomePositions
			}

		companion object {
			fun getPositions(player: Player, biome: Holder<Biome>): Positions {
				val level = player.level()
				val playerPos = player.blockPosition()

				val horizontalRadius = 10
				val verticalRadius = 5

				val goodPositions = mutableSetOf<BlockPos>()
				val badPositions = mutableSetOf<BlockPos>()

				val toCheck = arrayListOf(playerPos)

				while (toCheck.isNotEmpty()) {
					val pos = toCheck.removeLast()
					if (!level.isLoaded(pos)) continue

					val biomeAtPos = level.getBiome(pos)

					if (biomeAtPos != biome) {
						badPositions.add(pos)
						continue
					}

					goodPositions.add(pos)

					for (direction in Direction.entries) {
						val offset = pos.relative(direction)

						if (offset in goodPositions || offset in badPositions) continue

						val dy = offset.y - playerPos.y
						if (dy < -verticalRadius || dy > verticalRadius) continue

						val dx = offset.x - playerPos.x
						val dz = offset.z - playerPos.z

						val hDistSqr = dx * dx + dz * dz
						if (hDistSqr > horizontalRadius * horizontalRadius) continue

						toCheck.add(offset)
					}
				}

				val targetedIncorrectBiomePosition = getTargetedPos(player, badPositions)

				return Positions(
					correctBiomePositions = goodPositions,
					incorrectBiomePositions = badPositions,
					targetedIncorrectBiomePosition = targetedIncorrectBiomePosition,
				)
			}

			fun getTargetedPos(player: Player, positions: Set<BlockPos>): BlockPos? {
				val eyePos = player.eyePosition
				val lookVec = player.lookAngle.normalize()
				val endPos = eyePos.add(lookVec.scale(20.0))

				var closestHit: Pair<Double, BlockPos>? = null

				for (pos in positions) {
					val aabb = AABB(pos)

					val hitPos = aabb.clip(eyePos, endPos).getOrNull() ?: continue
					val distSqr = eyePos.distanceToSqr(hitPos)

					if (closestHit == null || distSqr < closestHit.first) {
						closestHit = Pair(distSqr, pos)
					}
				}

				return closestHit?.second
			}
		}
	}

}