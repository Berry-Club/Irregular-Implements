package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.entity.PortkeyItemEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.GlobalPos
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level

class PortkeyItem(properties: Properties) : Item(properties) {

	override fun useOn(context: UseOnContext): InteractionResult {
		val level = context.level
		val clickedPos = context.clickedPos
		val clickedFace = context.clickedFace

		val posToTeleportTo = if (level.getBlockState(clickedPos).getCollisionShape(level, clickedPos).isEmpty) {
			clickedPos
		} else {
			clickedPos.relative(clickedFace)
		}

		val usedStack = context.itemInHand

		usedStack.set(
			ModDataComponents.GLOBAL_POS,
			GlobalPos(level.dimension(), posToTeleportTo)
		)

		return InteractionResult.SUCCESS
	}


	override fun isFoil(stack: ItemStack): Boolean {
		return stack.has(ModDataComponents.GLOBAL_POS) || super.isFoil(stack)
	}

	override fun hasCustomEntity(stack: ItemStack): Boolean = stack.has(ModDataComponents.GLOBAL_POS)
	override fun createEntity(level: Level, original: Entity, stack: ItemStack): Entity {
		return if (original is ItemEntity) PortkeyItemEntity(original) else original
	}

	override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {

		val target = stack.get(ModDataComponents.GLOBAL_POS)
		if (target != null) {
			tooltipComponents.add(
				ModTooltipLang.PORTKEY_DESTINATION
					.toGrayComponent(
						OtherUtil.getDimensionComponent(target.dimension),
						target.pos.x,
						target.pos.y,
						target.pos.z
					)
			)
		}

		val disguise = stack.get(ModDataComponents.PORTKEY_DISGUISE)
		if (disguise != null) {
			tooltipComponents.add(
				ModTooltipLang.PORTKEY_DISGUISE.toGrayComponent(disguise.stack.displayName)
			)
		}

		FlooPouchItem.fuckJkr(tooltipComponents)
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)
	}

}