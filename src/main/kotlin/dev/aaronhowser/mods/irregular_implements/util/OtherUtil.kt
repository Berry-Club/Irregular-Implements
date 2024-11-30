package dev.aaronhowser.mods.irregular_implements.util

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import io.netty.buffer.ByteBuf
import net.minecraft.ChatFormatting
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.network.chat.Component
import net.minecraft.network.codec.StreamCodec
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.alchemy.PotionContents

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

    fun <T> tagKeyStreamCodec(registry: ResourceKey<out Registry<T>>): StreamCodec<ByteBuf, TagKey<T>> {
        return ResourceLocation.STREAM_CODEC.map(
            { TagKey.create(registry, it) },
            { it.location() }
        )
    }

}