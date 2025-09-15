package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.GlobalPos
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level

class LocationFilterItem(properties: Properties) : Item(properties) {

	override fun useOn(context: UseOnContext): InteractionResult {
		val usedStack = context.itemInHand
		usedStack.set(
			ModDataComponents.GLOBAL_POS,
			GlobalPos(context.level.dimension(), context.clickedPos)
		)

		return InteractionResult.SUCCESS
	}

	override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
		val usedStack = player.getItemInHand(usedHand)

		if (level.isClientSide || !player.isSecondaryUseActive) return InteractionResultHolder.pass(usedStack)

		usedStack.remove(ModDataComponents.GLOBAL_POS)

		return InteractionResultHolder.success(usedStack)
	}

	override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
		val location = stack.get(ModDataComponents.GLOBAL_POS)

		if (location != null) {
			val x = location.pos.x
			val y = location.pos.y
			val z = location.pos.z

			val dimensionComponent = OtherUtil.getDimensionComponent(location.dimension)

			val component = ModTooltipLang.LOCATION_COMPONENT
				.toGrayComponent(dimensionComponent, x, y, z)

			tooltipComponents.add(component)
		}
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)
	}

}