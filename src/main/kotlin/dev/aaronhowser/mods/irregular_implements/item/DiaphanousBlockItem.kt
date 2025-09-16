package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.block.block_entity.DiaphanousBlockEntity
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.ItemNameBlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.BlockPlaceContext

class DiaphanousBlockItem(properties: Properties) : ItemNameBlockItem(
	ModBlocks.DIAPHANOUS_BLOCK.get(), properties
) {

	override fun appendHoverText(
		stack: ItemStack,
		context: TooltipContext,
		tooltipComponents: MutableList<Component>,
		tooltipFlag: TooltipFlag
	) {
		val block = stack.get(ModDataComponents.BLOCK)
		if (block != null) {
			val component = ModTooltipLang.BLOCK
				.toGrayComponent(block.name)

			tooltipComponents.add(component)
		}

		val isInverted = stack.has(ModDataComponents.IS_INVERTED)
		if (isInverted) {
			val component = ModTooltipLang.DIAPHANOUS_INVERTED.toGrayComponent()
			tooltipComponents.add(component)
		}
	}

	override fun place(context: BlockPlaceContext): InteractionResult {
		val result = super.place(context)

		if (!result.consumesAction()) {
			return result
		}

		val blockEntity = context.level.getBlockEntity(context.clickedPos)

		if (blockEntity is DiaphanousBlockEntity) {
			val isInverted = context.itemInHand.has(ModDataComponents.IS_INVERTED)
			blockEntity.isInverted = isInverted

			val stateToRender = context.itemInHand.get(ModDataComponents.BLOCK)?.getStateForPlacement(context)
			if (stateToRender != null) {
				blockEntity.renderedBlockState = stateToRender
			}
		}

		return result
	}

}