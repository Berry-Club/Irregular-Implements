package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.server.commands.FillBiomeCommand
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext

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

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)
	}

}