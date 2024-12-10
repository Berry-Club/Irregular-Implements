package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.item.component.BiomePointsDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.color.item.ItemColor
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag

class BiomeCapsuleItem : Item(
    Properties()
        .stacksTo(1)
) {

    companion object {

        val itemColorFunction = ItemColor { stack: ItemStack, tintIndex: Int ->
            stack.get(ModDataComponents.BIOME_POINTS)
                ?.biome
                ?.value()
                ?.foliageColor
                ?: return@ItemColor 0xFFFFFFFF.toInt()
        }

    }

    override fun onEntityItemUpdate(stack: ItemStack, entity: ItemEntity): Boolean {
        val biome = entity.level().getBiome(entity.blockPosition())
        val component = stack.get(ModDataComponents.BIOME_POINTS) ?: BiomePointsDataComponent(biome, 0)

        val newComponent = component.withMorePoints(1)
        stack.set(ModDataComponents.BIOME_POINTS, newComponent)

        return super.onEntityItemUpdate(stack, entity)
    }

    override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
        val biomePointsComponent = stack.get(ModDataComponents.BIOME_POINTS) ?: return

        val biomeComponent = OtherUtil.getBiomeComponent(biomePointsComponent.biome)
        val biomePoints = biomePointsComponent.points

        tooltipComponents.add(biomeComponent)
        tooltipComponents.add(biomePoints.toString().toComponent())

    }

}