package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.item.component.LocationItemComponent
import dev.aaronhowser.mods.irregular_implements.registries.ModDataComponents
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext

class RedstoneToolItem : Item(
    Properties()
        .stacksTo(1)
) {

    override fun useOn(context: UseOnContext): InteractionResult {
        val player = context.player ?: return InteractionResult.PASS
        val level = context.level as? ServerLevel ?: return InteractionResult.PASS

        val clickedPos = context.clickedPos
        val clickedState = level.getBlockState(clickedPos)

        val usedStack = context.itemInHand

        if (clickedState.`is`(ModBlockTagsProvider.REDSTONE_TOOL_ACCESSIBLE)) {
            val locationComponent = LocationItemComponent(level, clickedPos)
            usedStack.set(ModDataComponents.LOCATION, locationComponent)

            val blockName = clickedState.block.name

            player.displayClientMessage(
                Component.literal("Location set to the ").append(blockName).append(Component.literal(" at $clickedPos")),
                true
            )

            return InteractionResult.SUCCESS
        }


        return InteractionResult.SUCCESS
    }

}