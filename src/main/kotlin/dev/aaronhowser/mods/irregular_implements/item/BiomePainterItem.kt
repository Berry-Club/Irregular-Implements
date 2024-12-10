package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.server.commands.FillBiomeCommand
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext

class BiomePainterItem : Item(
    Properties()
        .stacksTo(1)
) {

    override fun useOn(context: UseOnContext): InteractionResult {
        val player = context.player ?: return InteractionResult.FAIL

        val level = context.level as? ServerLevel ?: return InteractionResult.PASS
        val clickedPos = context.clickedPos

        val clickedBiome = level.getBiome(clickedPos)

        val firstNonEmptyCapsule = player.inventory.items.find {
            val component = it.get(ModDataComponents.BIOME_POINTS) ?: return@find false

            return@find component.biome != clickedBiome && component.points > 0
        } ?: return InteractionResult.FAIL

        val component = firstNonEmptyCapsule.get(ModDataComponents.BIOME_POINTS)!!
        val biomeToPlace = component.biome
        val points = component.points

        val horizontalRadius = ServerConfig.BIOME_PAINTER_HORIZONTAL_RADIUS.get()
        val blocksBelow = ServerConfig.BIOME_PAINTER_BLOCKS_BELOW.get()
        val blocksAbove = ServerConfig.BIOME_PAINTER_BLOCKS_ABOVE.get()

        var pointsLeft = points

        val result = FillBiomeCommand.fill(
            level,
            clickedPos.offset(-horizontalRadius, -blocksBelow, -horizontalRadius),
            clickedPos.offset(horizontalRadius, blocksAbove, horizontalRadius),
            biomeToPlace,
            { _ -> pointsLeft-- > 0 },
            { _ -> }
        )

        val amountChanged = result.left().orElse(0)
        if (amountChanged == 0) return InteractionResult.FAIL

        if (pointsLeft > 0) {
            firstNonEmptyCapsule.set(
                ModDataComponents.BIOME_POINTS,
                component.copy(points = pointsLeft)
            )
        } else {
            firstNonEmptyCapsule.remove(ModDataComponents.BIOME_POINTS)
        }

        return InteractionResult.SUCCESS
    }
}