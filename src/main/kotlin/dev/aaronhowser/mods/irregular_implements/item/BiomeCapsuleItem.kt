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

        fun getItemColor(stack: ItemStack, tintIndex: Int): Int {
            return stack.get(ModDataComponents.BIOME_POINTS)
                ?.biome
                ?.value()
                ?.foliageColor
                ?: 0xFFFFFFFF.toInt()
        }

    }

    override fun onEntityItemUpdate(stack: ItemStack, entity: ItemEntity): Boolean {
        val onBlockPos = entity.blockPosBelowThatAffectsMyMovement

        if (entity.level().getBlockState(onBlockPos).isAir) return super.onEntityItemUpdate(stack, entity)

        val biome = entity.level().getBiome(onBlockPos)
        val component = stack.get(ModDataComponents.BIOME_POINTS) ?: BiomePointsDataComponent(biome, 0)

        if (component.biome != biome) return super.onEntityItemUpdate(stack, entity)

        stack.set(
            ModDataComponents.BIOME_POINTS,
            component.withMorePoints(1)
        )

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