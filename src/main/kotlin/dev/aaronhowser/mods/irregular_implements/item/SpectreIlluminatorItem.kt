package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModMessageLang
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.entity.SpectreIlluminatorEntity
import dev.aaronhowser.mods.irregular_implements.handler.SpectreIlluminationHandler
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext

class SpectreIlluminatorItem(properties: Properties) : Item(properties) {

	override fun useOn(context: UseOnContext): InteractionResult {
		val level = context.level
		if (level !is ServerLevel) {
			return InteractionResult.PASS
		}

		val clickedPos = context.clickedPos

		val player = context.player

		if (SpectreIlluminationHandler.isChunkIlluminated(level, clickedPos)) {
			player?.sendSystemMessage(
				ModMessageLang.ILLUMINATOR_ALREADY_PRESENT
					.toComponent()
					.withStyle(ChatFormatting.RED)
			)

			return InteractionResult.FAIL
		}

		val clickedFace = context.clickedFace

		val spawnPos = clickedPos.relative(clickedFace).center

		val entity = SpectreIlluminatorEntity(level)
		entity.setPos(spawnPos.x, spawnPos.y, spawnPos.z)

		level.addFreshEntity(entity)

		context.itemInHand.consume(1, player)

		return InteractionResult.SUCCESS
	}

	override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
		ModTooltipLang.addSodiumTooltip(tooltipComponents)
	}

}