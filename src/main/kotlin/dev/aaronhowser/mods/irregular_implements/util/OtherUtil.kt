package dev.aaronhowser.mods.irregular_implements.util

import com.mojang.math.Transformation
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.entity.IndicatorDisplayEntity
import io.netty.buffer.ByteBuf
import net.minecraft.ChatFormatting
import net.minecraft.core.BlockPos
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
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import org.joml.Vector3f

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

    fun spawnIndicatorBlockDisplay(
        level: Level,
        pos: BlockPos,
        color: Int = 0xFFFFFF,
        duration: Int = 1
    ) {
        if (level.isClientSide) return

        val indicatorDisplay = IndicatorDisplayEntity(
            level,
            Blocks.GLASS.defaultBlockState(),
            color,
            5
        )

        indicatorDisplay.setPos(pos.x + 0.25, pos.y + 0.25, pos.z + 0.25)
        level.addFreshEntity(indicatorDisplay)

        ServerScheduler.scheduleTaskInTicks(duration) {
            indicatorDisplay.discard()
        }
    }

}