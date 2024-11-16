package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.block.base.RedstoneToolLinkable
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
        val blockName = clickedState.block.name
        val clickedBlockEntity = level.getBlockEntity(clickedPos)

        val usedStack = context.itemInHand

        if (clickedBlockEntity is RedstoneToolLinkable) {
            val locationComponent = LocationItemComponent(level, clickedPos)
            usedStack.set(ModDataComponents.LOCATION, locationComponent)

            player.displayClientMessage(
                Component.literal("Location set to the ").append(blockName).append(Component.literal(" at $clickedPos")),
                true
            )

            return InteractionResult.SUCCESS
        }

        val locationComponent = usedStack.get(ModDataComponents.LOCATION)
        if (locationComponent == null) {
            player.displayClientMessage(
                Component.literal("No location set"),
                true
            )

            return InteractionResult.FAIL
        }

        if (level.dimension() != locationComponent.dimension) {
            player.displayClientMessage(
                Component.literal("Location is in a different dimension"),
                true
            )

            return InteractionResult.FAIL
        }

        val linkedBLockPos = locationComponent.blockPos
        if (!level.isLoaded(linkedBLockPos)) {
            player.displayClientMessage(
                Component.literal("Location is not loaded"),
                true
            )

            return InteractionResult.FAIL
        }

        val linkedBlockEntity = level.getBlockEntity(linkedBLockPos)
        if (linkedBlockEntity !is RedstoneToolLinkable) {
            player.displayClientMessage(
                Component.literal("Location is not a valid block"),
                true
            )

            return InteractionResult.FAIL
        }

        linkedBlockEntity.linkedPos = clickedPos

        player.displayClientMessage(
            Component.literal("Linked the ").append(blockName).append(Component.literal(" at $clickedPos")),
            true
        )

        return InteractionResult.SUCCESS
    }

}