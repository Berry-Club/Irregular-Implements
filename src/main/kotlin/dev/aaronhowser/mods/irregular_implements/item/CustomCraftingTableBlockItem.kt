package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.block.block_entity.CustomCraftingTableBlockEntity
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block

class CustomCraftingTableBlockItem : BlockItem(
	ModBlocks.CUSTOM_CRAFTING_TABLE.get(), Properties()
) {

	override fun place(context: BlockPlaceContext): InteractionResult {
		val result = super.place(context)

		if (!result.consumesAction()) {
			return result
		}

		val stateToRender = context.itemInHand.get(ModDataComponents.BLOCK)?.getStateForPlacement(context)

		if (stateToRender != null) {
			val blockEntity = context.level.getBlockEntity(context.clickedPos)

			if (blockEntity is CustomCraftingTableBlockEntity) {
				blockEntity.renderedBlockState = stateToRender
			}
		}

		return result
	}

	override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
		val block = stack.get(ModDataComponents.BLOCK)

		if (block != null) {
			val component = ModLanguageProvider.Tooltips.BLOCK
				.toGrayComponent(block.name)

			tooltipComponents.add(component)
		}
	}

	companion object {
		fun ofBlock(block: Block): ItemStack {
			val stack = ModItems.CUSTOM_CRAFTING_TABLE.toStack()

			stack.set(ModDataComponents.BLOCK, block)

			return stack
		}
	}

}