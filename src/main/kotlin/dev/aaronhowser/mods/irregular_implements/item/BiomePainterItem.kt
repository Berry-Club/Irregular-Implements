package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.client.render.CubeIndicatorRenderer
import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isClientSide
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.Holder
import net.minecraft.server.commands.FillBiomeCommand
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.biome.Biome

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

	override fun inventoryTick(stack: ItemStack, level: Level, entity: Entity, slotId: Int, isSelected: Boolean) {
		if (isSelected && level.isClientSide) {
			renderCubes(entity)
		}
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)

		private fun renderCubes(player: Entity) {
			if (player !is Player || !player.isClientSide) return

			val paintingBiome = BiomeCapsuleItem.getFirstNonEmptyCapsule(player.inventory)
				?.get(ModDataComponents.BIOME_POINTS)
				?.biome
				?: return

			val goodColor = 0x6622AA00
			val badColor = 0x66AA2200

			val (goodPositions, badPositions) = getPositions(player, paintingBiome)

			// Duration is 2 because CubeIndicatorRenderer.afterClientTick is called AFTER this runs,
			// so the Indicators added here would lose 1 tick immediately

			for (pos in goodPositions) {
				CubeIndicatorRenderer.addIndicator(pos, 2, goodColor, size = 0.05f)
			}

			for (pos in badPositions) {
				CubeIndicatorRenderer.addIndicator(pos, 2, badColor, size = 0.35f)
			}
		}

		private fun getPositions(player: Player, biome: Holder<Biome>): Pair<Set<BlockPos>, Set<BlockPos>> {
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

			return Pair(goodPositions, badPositions)
		}
	}

}