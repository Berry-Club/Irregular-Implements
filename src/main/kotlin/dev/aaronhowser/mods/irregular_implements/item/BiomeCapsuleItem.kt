package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.item.component.BiomePointsDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag

class BiomeCapsuleItem(properties: Properties) : Item(properties) {

	override fun onEntityItemUpdate(stack: ItemStack, entity: ItemEntity): Boolean {
		val onBlockPos = entity.blockPosBelowThatAffectsMyMovement

		if (entity.level().getBlockState(onBlockPos).isAir) return super.onEntityItemUpdate(stack, entity)

		val biome = entity.level().getBiome(onBlockPos)
		val component = stack.get(ModDataComponents.BIOME_POINTS) ?: BiomePointsDataComponent(biome, 0)

		if (component.biome != biome) return super.onEntityItemUpdate(stack, entity)

		//TODO: Max amount?
		stack.set(
			ModDataComponents.BIOME_POINTS,
			component.withMorePoints(1)
		)

		return super.onEntityItemUpdate(stack, entity)
	}

	//TODO: Improve this
	override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
		val biomePointsComponent = stack.get(ModDataComponents.BIOME_POINTS) ?: return

		val biomeComponent = OtherUtil.getBiomeComponent(biomePointsComponent.biome)
		val biomePoints = biomePointsComponent.points

		tooltipComponents.add(biomeComponent)
		tooltipComponents.add(biomePoints.toString().toComponent())
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)

		fun getItemColor(stack: ItemStack, tintIndex: Int): Int {
			val foliageColor = stack.get(ModDataComponents.BIOME)?.value()?.foliageColor ?: 0xFFFFFFFF.toInt()

			return (foliageColor or 0xFF000000.toInt())
		}
	}

}