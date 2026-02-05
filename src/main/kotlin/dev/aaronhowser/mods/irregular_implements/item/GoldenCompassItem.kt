package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag

class GoldenCompassItem(properties: Properties) : Item(properties) {

	override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
		val location = stack.get(ModDataComponents.GLOBAL_POS) ?: return

		val x = location.pos.x
		val y = location.pos.y
		val z = location.pos.z

		val dimensionComponent = OtherUtil.getDimensionComponent(location.dimension)

		val component = ModTooltipLang.LOCATION_COMPONENT
			.toGrayComponent(dimensionComponent, x, y, z)

		tooltipComponents.add(component)
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)
		val ANGLE: ResourceLocation = OtherUtil.modResource("angle")
	}

}