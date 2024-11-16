package dev.aaronhowser.mods.irregular_implements.util

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import net.minecraft.ChatFormatting
import net.minecraft.core.Holder
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.*
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.alchemy.PotionContents
import net.neoforged.neoforge.common.Tags

object OtherUtil {

    fun modResource(path: String): ResourceLocation =
        ResourceLocation.fromNamespaceAndPath(IrregularImplements.ID, path)

    val Boolean?.isTrue: Boolean
        inline get() = this == true

    val Entity.isClientSide: Boolean
        get() = this.level().isClientSide

    fun getPotionStack(potion: Holder<Potion>): ItemStack {
        return PotionContents.createItemStack(Items.POTION, potion)
    }

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

    fun getDyeTag(dyeColor: DyeColor): TagKey<Item> {
        return when (dyeColor) {
            DyeColor.WHITE -> Tags.Items.DYES_WHITE
            DyeColor.ORANGE -> Tags.Items.DYES_ORANGE
            DyeColor.MAGENTA -> Tags.Items.DYES_MAGENTA
            DyeColor.LIGHT_BLUE -> Tags.Items.DYES_LIGHT_BLUE
            DyeColor.YELLOW -> Tags.Items.DYES_YELLOW
            DyeColor.LIME -> Tags.Items.DYES_LIME
            DyeColor.PINK -> Tags.Items.DYES_PINK
            DyeColor.GRAY -> Tags.Items.DYES_GRAY
            DyeColor.LIGHT_GRAY -> Tags.Items.DYES_LIGHT_GRAY
            DyeColor.CYAN -> Tags.Items.DYES_CYAN
            DyeColor.PURPLE -> Tags.Items.DYES_PURPLE
            DyeColor.BLUE -> Tags.Items.DYES_BLUE
            DyeColor.BROWN -> Tags.Items.DYES_BROWN
            DyeColor.GREEN -> Tags.Items.DYES_GREEN
            DyeColor.RED -> Tags.Items.DYES_RED
            DyeColor.BLACK -> Tags.Items.DYES_BLACK
        }
    }

}