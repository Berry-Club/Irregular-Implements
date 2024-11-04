package dev.aaronhowser.mods.irregular_implements.util

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.TooltipFlag

object OtherUtil {

    fun modResource(path: String): ResourceLocation =
        ResourceLocation.fromNamespaceAndPath(IrregularImplements.ID, path)

    val Boolean?.isTrue: Boolean
        get() = this == true

    val Entity.isClientSide: Boolean
        get() = this.level().isClientSide

    fun moreInfoTooltip(
        tooltipComponents: MutableList<Component>,
        tooltipFlag: TooltipFlag,
        vararg moreInfo: Component
    ) {
        if (!tooltipFlag.hasShiftDown()) {
            tooltipComponents.add(
                ModLanguageProvider.Tooltips.SHIFT_FOR_MORE
                    .toComponent()
                    .withStyle(ChatFormatting.GRAY)
            )
        } else {
            tooltipComponents.addAll(moreInfo)
        }
    }

}