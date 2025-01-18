package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneToolLinkable
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.item.component.LocationDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
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
        val clickedBlockName = clickedState.block.name
        val clickedBlockEntity = level.getBlockEntity(clickedPos)

        val usedStack = context.itemInHand

        if (clickedBlockEntity is RedstoneToolLinkable) {
            val locationComponent = LocationDataComponent(level, clickedPos)
            usedStack.set(ModDataComponents.LOCATION, locationComponent)

            player.displayClientMessage(
                ModLanguageProvider.Messages.REDSTONE_TOOL_BASE_SET
                    .toComponent(clickedBlockName, clickedPos.x, clickedPos.y, clickedPos.z),
                true
            )

            return InteractionResult.SUCCESS
        }

        val locationComponent = usedStack.get(ModDataComponents.LOCATION)
        if (locationComponent == null) {
            player.displayClientMessage(
                ModLanguageProvider.Messages.REDSTONE_TOOL_INVALID_BASE_BLOCK.toComponent(),
                true
            )

            return InteractionResult.FAIL
        }

        if (level.dimension() != locationComponent.dimension) {
            player.displayClientMessage(
                ModLanguageProvider.Messages.REDSTONE_TOOL_WRONG_DIMENSION.toComponent(),
                true
            )

            return InteractionResult.FAIL
        }

        val baseBlockPos = locationComponent.blockPos
        val baseBlockName = locationComponent.blockName

        if (!level.isLoaded(baseBlockPos)) {
            player.displayClientMessage(
                ModLanguageProvider.Messages.REDSTONE_TOOL_UNLOADED
                    .toComponent(baseBlockName),
                true
            )

            return InteractionResult.FAIL
        }

        val baseBlockEntity = level.getBlockEntity(baseBlockPos)
        if (baseBlockEntity !is RedstoneToolLinkable) {
            player.displayClientMessage(
                ModLanguageProvider.Messages.REDSTONE_TOOL_BASE_NOT_LINKABLE
                    .toComponent(baseBlockName, level.getBlockState(baseBlockPos).block.name),
                true
            )

            return InteractionResult.FAIL
        }

        //FIXME: Not updating on client?
        baseBlockEntity.linkedPos = clickedPos

        player.displayClientMessage(
            ModLanguageProvider.Messages.REDSTONE_TOOL_LINKED
                .toComponent(
                    clickedBlockName, clickedPos.x, clickedPos.y, clickedPos.z,
                    baseBlockName, baseBlockPos.x, baseBlockPos.y, baseBlockPos.z
                ),
            true
        )

        return InteractionResult.SUCCESS
    }

}