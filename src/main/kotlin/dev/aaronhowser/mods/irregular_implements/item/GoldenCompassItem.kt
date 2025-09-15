package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item

class GoldenCompassItem(properties: Properties) : Item(properties) {

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)
		val ANGLE: ResourceLocation = OtherUtil.modResource("angle")
	}

}