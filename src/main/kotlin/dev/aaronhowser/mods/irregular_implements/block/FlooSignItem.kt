package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext

class FlooSignItem(properties: Properties) : Item(properties) {

	override fun useOn(context: UseOnContext): InteractionResult {
		val level = context.level
		if (level.isClientSide) return InteractionResult.PASS

		val clickedPos = context.clickedPos
		val clickedState = level.getBlockState(clickedPos)

		if (!clickedState.`is`(ModBlockTagsProvider.CONVERTS_TO_FLOO_BRICK)) return InteractionResult.PASS

		return super.useOn(context)
	}

}