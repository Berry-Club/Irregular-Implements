package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.client.render.BiomePainterRenderer
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.PaintBiomePacket
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.status
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.server.commands.FillBiomeCommand
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.phys.AABB
import net.neoforged.neoforge.network.handling.IPayloadContext
import kotlin.jvm.optionals.getOrNull
import kotlin.math.abs

class BiomePainterItem(properties: Properties) : Item(properties) {

	override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
		val stack = player.getItemInHand(usedHand)

		if (level.isClientSide) {
			val biomeToPaint = BiomeCapsuleItem.getBiomeToPaint(player) ?: return InteractionResultHolder.pass(stack)
			val biomeRk = biomeToPaint.key ?: return InteractionResultHolder.pass(stack)

			val positions = Positions.getPositions(player, biomeToPaint)
			val targetPos = positions.targetedUnmatchingPosition ?: return InteractionResultHolder.pass(stack)

			val packet = PaintBiomePacket(targetPos, biomeRk)
			packet.messageServer()
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

		fun handlePacket(packet: PaintBiomePacket, context: IPayloadContext) {
			val player = context.player() as? ServerPlayer ?: return
			val level = player.serverLevel()

			val blockPos = packet.blockPos
			val biomeThere = level.getBiome(blockPos)
			player.displayClientMessage(Component.literal("Biome there: ${biomeThere.key?.location()}"), false)

			val biomeRk = packet.biomeRk

			val biomeToPlace = level.registryAccess()
				.registryOrThrow(Registries.BIOME)
				.getHolder(biomeRk)
				.getOrNull()
				?: return

			val firstCapsule = BiomeCapsuleItem.getFirstCapsuleWithBiome(player.inventory, biomeToPlace)
			if (firstCapsule == null) {
				player.status(Component.literal("No biome capsules with that biome found!"))
				return
			}

			val component = firstCapsule.get(ModDataComponents.BIOME_POINTS) ?: return

			var points = if (player.hasInfiniteMaterials()) 99999 else component.points
			var amountChanged = 0

			val iterator = BlockPos.betweenClosed(
				blockPos.offset(0, 0, 0),
				blockPos.offset(0, 0, 0)
			)

			for (mutable in iterator) {
				val pos = mutable.immutable()

				val biomeThere = level.getBiome(pos)
				if (biomeThere.`is`(biomeToPlace) || points-- < 0) continue

				val result = FillBiomeCommand.fill(
					level,
					pos,
					pos,
					biomeToPlace,
					{ _ -> true },
					{ _ -> }
				)

				result.ifLeft {
					amountChanged++
				}

				val newBiomeThere = level.getBiome(pos)
				player.sendSystemMessage(
					Component.literal("It is now ${newBiomeThere.key?.location()}.")
				)
			}

			if (amountChanged == 0) {
				player.status(Component.literal("No biomes changed!"))
				return
			}

			if (!player.hasInfiniteMaterials()) {
				if (amountChanged < points) {
					firstCapsule.set(ModDataComponents.BIOME_POINTS, component.withLessPoints(amountChanged))
				} else {
					firstCapsule.remove(ModDataComponents.BIOME_POINTS)
				}
			}

			player.status(Component.literal("Changed $amountChanged blocks to ${biomeRk.location()}."))
		}
	}

	data class Positions(
		val matchingPositions: Set<BlockPos>,
		val unmatchingPositions: Set<BlockPos>,
		val targetedUnmatchingPosition: BlockPos?,
	) {
		val untargetedUnmatchingPositions: Set<BlockPos> =
			if (targetedUnmatchingPosition != null) {
				unmatchingPositions - targetedUnmatchingPosition
			} else {
				unmatchingPositions
			}

		companion object {
			fun getPositions(player: Player, matchingBiome: Holder<Biome>): Positions {
				val level = player.level()
				val playerPos = player.blockPosition()

				val horizontalRadius = 3
				val verticalRadius = 3

				val matchingPositions = mutableSetOf<BlockPos>()
				val unmatchingPositions = mutableSetOf<BlockPos>()

				val toCheck = arrayListOf(playerPos)

				while (toCheck.isNotEmpty()) {
					val pos = toCheck.removeLast()
					if (!level.isLoaded(pos)) continue

					val biomeAtPos = level.getBiome(pos)

					if (biomeAtPos != matchingBiome) {
						unmatchingPositions.add(pos)
						continue
					}

					matchingPositions.add(pos)

					for (direction in Direction.entries) {
						val offset = pos.relative(direction)

						if (offset in matchingPositions || offset in unmatchingPositions) continue

						val dy = offset.y - playerPos.y
						if (dy < -verticalRadius || dy > verticalRadius) continue

						val dx = offset.x - playerPos.x
						val dz = offset.z - playerPos.z

						val hDistSqr = dx * dx + dz * dz
						if (abs(hDistSqr) > horizontalRadius * horizontalRadius) continue

						toCheck.add(offset)
					}
				}

				val targetedIncorrectBiomePosition = getTargetedPos(player, unmatchingPositions)

				if (targetedIncorrectBiomePosition != null) {
					val biomeThere = level.getBiome(targetedIncorrectBiomePosition)
//					println(biomeThere.key?.location().toString())
				}

				return Positions(
					matchingPositions = matchingPositions,
					unmatchingPositions = unmatchingPositions,
					targetedUnmatchingPosition = targetedIncorrectBiomePosition,
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