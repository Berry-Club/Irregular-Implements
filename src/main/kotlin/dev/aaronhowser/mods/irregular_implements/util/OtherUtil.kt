package dev.aaronhowser.mods.irregular_implements.util

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.entity.IndicatorDisplayEntity
import io.netty.buffer.ByteBuf
import net.minecraft.ChatFormatting
import net.minecraft.client.resources.language.I18n
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.codec.StreamCodec
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.alchemy.PotionContents
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.Vec3
import java.util.*

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

    fun getPovResult(level: Level, entity: LivingEntity, range: Number): BlockHitResult {
        return level.clip(
            ClipContext(
                entity.eyePosition,
                entity.eyePosition.add(entity.lookAngle.scale(range.toDouble())),
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                entity
            )
        )
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
        duration: Int = 5
    ): IndicatorDisplayEntity? {
        if (level.isClientSide) return null

        val indicatorDisplay = IndicatorDisplayEntity(
            level,
            Blocks.GLASS.defaultBlockState(),
            color,
            duration
        )

        indicatorDisplay.setPos(pos.x + 0.25, pos.y + 0.25, pos.z + 0.25)
        level.addFreshEntity(indicatorDisplay)

        return indicatorDisplay
    }

    fun getBiomeComponent(biomeHolder: Holder<Biome>): Component {
        val biomeKey = biomeHolder.key!!

        val probableTranslationKey = "biome.${biomeKey.location().namespace}.${biomeKey.location().path}"
        val hasTranslation = I18n.exists(probableTranslationKey)

        return if (hasTranslation) {
            Component.translatable(probableTranslationKey)
        } else {
            Component.literal(biomeKey.location().toString())
        }.withStyle(ChatFormatting.GRAY)
    }

    fun getDimensionComponent(dimensionResourceKey: ResourceKey<Level>): Component {
        val location = dimensionResourceKey.location()

        val probableTranslationKey = "dimension.${location.namespace}.${location.path}"
        val hasTranslation = I18n.exists(probableTranslationKey)

        return if (hasTranslation) {
            Component.translatable(probableTranslationKey)
        } else {
            Component.literal(location.toString())
        }.withStyle(ChatFormatting.GRAY)
    }

    fun flattenStacks(input: List<ItemStack>): List<ItemStack> {
        val output = mutableListOf<ItemStack>()

        for (stack in input.filter { !it.isEmpty }.map { it.copy() }) {
            val matchingStack = output.firstOrNull { ItemStack.isSameItemSameComponents(it, stack) }

            if (matchingStack != null) {
                val amountToAdd = minOf(stack.count, matchingStack.maxStackSize - matchingStack.count)

                if (amountToAdd > 0) {
                    matchingStack.grow(amountToAdd)
                    stack.shrink(amountToAdd)
                }
            }

            if (!stack.isEmpty) {
                output.add(stack)
            }
        }

        return output
    }

    fun giveOrDropStack(itemStack: ItemStack, player: Player): Boolean {
        return player.inventory.add(itemStack)
                || dropStackAt(itemStack, player, true)
    }

    fun dropStackAt(itemStack: ItemStack, entity: Entity, instantPickup: Boolean = false): Boolean {
        return dropStackAt(itemStack, entity.level(), entity.position(), instantPickup)
    }

    fun dropStackAt(itemStack: ItemStack, level: Level, pos: Vec3, instantPickup: Boolean = false): Boolean {
        val itemEntity = ItemEntity(level, pos.x, pos.y, pos.z, itemStack)
        if (instantPickup) itemEntity.setNoPickUpDelay()
        return level.addFreshEntity(itemEntity)
    }

    fun CompoundTag.getUuidOrNull(key: String): UUID? {
        return if (this.hasUUID(key)) this.getUUID(key) else null
    }

}