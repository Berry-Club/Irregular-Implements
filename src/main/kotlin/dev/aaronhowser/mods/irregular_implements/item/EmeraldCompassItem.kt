package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.aaronhowser.mods.irregular_implements.util.SpecificEntity
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag

class EmeraldCompassItem(properties: Properties) : Item(properties) {

	override fun interactLivingEntity(
		stack: ItemStack,
		player: Player,
		interactionTarget: LivingEntity,
		usedHand: InteractionHand
	): InteractionResult {
		if (interactionTarget !is Player) return InteractionResult.PASS

		val usedStack = player.getItemInHand(usedHand)
		usedStack.set(ModDataComponents.PLAYER, SpecificEntity(interactionTarget))

		return InteractionResult.SUCCESS
	}

	override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
		val dataComponent = stack.get(ModDataComponents.PLAYER) ?: return

		val name = dataComponent.name
		val uuid = dataComponent.uuid

		val component = ModTooltipLang.PLAYER_FILTER_PLAYER
			.toGrayComponent(name)

		tooltipComponents.add(component)

		if (tooltipFlag.hasShiftDown()) {
			val uuidComponent = ModTooltipLang.PLAYER_FILTER_UUID
				.toGrayComponent(uuid.toString())

			tooltipComponents.add(uuidComponent)
		}
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)

		val ANGLE: ResourceLocation = OtherUtil.modResource("emerald_compass_angle")
	}

}