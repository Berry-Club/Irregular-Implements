package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.aaron.AaronExtensions.status
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneToolLinkable
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModMessageLang
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.core.GlobalPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext

class RedstoneToolItem(properties: Properties) : Item(properties) {

	override fun useOn(context: UseOnContext): InteractionResult {
		val player = context.player ?: return InteractionResult.PASS
		val level = context.level as? ServerLevel ?: return InteractionResult.PASS

		val clickedPos = context.clickedPos
		val clickedState = level.getBlockState(clickedPos)
		val clickedBlockName = clickedState.block.name
		val clickedBlockEntity = level.getBlockEntity(clickedPos)

		val usedStack = context.itemInHand

		if (clickedBlockEntity is RedstoneToolLinkable) {
			val locationComponent = GlobalPos(level.dimension(), clickedPos)
			usedStack.set(ModDataComponents.GLOBAL_POS, locationComponent)

			player.status(
				ModMessageLang.REDSTONE_TOOL_BASE_SET
					.toComponent(clickedBlockName, clickedPos.x, clickedPos.y, clickedPos.z)
			)

			return InteractionResult.SUCCESS
		}

		val locationComponent = usedStack.get(ModDataComponents.GLOBAL_POS)
		if (locationComponent == null) {
			player.status(
				ModMessageLang.REDSTONE_TOOL_INVALID_BASE_BLOCK.toComponent()
			)

			return InteractionResult.FAIL
		}

		if (level.dimension() != locationComponent.dimension) {
			player.status(
				ModMessageLang.REDSTONE_TOOL_WRONG_DIMENSION.toComponent()
			)

			return InteractionResult.FAIL
		}

		val baseBlockPos = locationComponent.pos
		val baseBlockName = level.getBlockState(baseBlockPos).block.name
		val baseBlockEntity = level.getBlockEntity(baseBlockPos)

		if (baseBlockEntity !is RedstoneToolLinkable) {

			player.status(
				ModMessageLang.REDSTONE_TOOL_BASE_NOT_LINKABLE
					.toComponent(baseBlockName, level.getBlockState(baseBlockPos).block.name)
			)

			return InteractionResult.FAIL
		}

		baseBlockEntity.setLinkedPos(clickedPos)

		player.status(
			ModMessageLang.REDSTONE_TOOL_LINKED
				.toComponent(
					clickedBlockName, clickedPos.x, clickedPos.y, clickedPos.z,
					baseBlockName, baseBlockPos.x, baseBlockPos.y, baseBlockPos.z
				)
		)

		return InteractionResult.SUCCESS
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)

	}

}