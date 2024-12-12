package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneToolLinkable
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.item.component.LocationItemComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.ChatFormatting
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RedStoneWireBlock

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
            val locationComponent = LocationItemComponent(level, clickedPos)
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

    override fun inventoryTick(stack: ItemStack, level: Level, entity: Entity, slotId: Int, isSelected: Boolean) {
        if (!isSelected) return
        if (entity !is Player) return

        val clipResult = OtherUtil.getPovResult(level, entity, entity.blockInteractionRange())

        val pos = clipResult.blockPos
        val state = level.getBlockState(pos)

        if (state.`is`(Blocks.REDSTONE_WIRE)) {
            val signalStrength = state.getValue(RedStoneWireBlock.POWER)

            entity.displayClientMessage(
                signalStrength.toString().toComponent().withStyle(ChatFormatting.RED),
                true
            )
        }
    }

}