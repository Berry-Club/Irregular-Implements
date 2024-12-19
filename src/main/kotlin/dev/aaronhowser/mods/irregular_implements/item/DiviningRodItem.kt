package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.ChatFormatting
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import kotlin.jvm.optionals.getOrNull

class DiviningRodItem : Item(
    Properties()
        .stacksTo(1)
) {

    companion object {
        fun getRodForBlockTag(blockTag: TagKey<Block>): ItemStack {
            val stack = ModItems.DIVINING_ROD.toStack()

            stack.set(ModDataComponents.BLOCK_TAG, blockTag)

            return stack
        }

        fun getAllOreRods(): List<ItemStack> {
            return getAllOreTags().map { getRodForBlockTag(it) }
        }

        fun getAllOreTags(): Set<TagKey<Block>> {
            val oresTagKey = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores"))
            val ores = BuiltInRegistries.BLOCK.getTag(oresTagKey).getOrNull() ?: return emptySet()

            val oreTags = mutableSetOf(oresTagKey)

            for (ore in ores) {
                for (tag in ore.tags()) {
                    if (tag.location.namespace == "c" && tag.location.path.startsWith("ores/")) {
                        oreTags.add(tag)
                    }
                }
            }

            return oreTags
        }

        fun getNameForBlockTag(blockTag: TagKey<Block>): Component {
            val firstBlock = getBlockForTag(blockTag)

            if (firstBlock == Blocks.AIR) return Component
                .literal(blockTag.location.toString())
                .withStyle(ChatFormatting.RED)
                .withStyle(ChatFormatting.STRIKETHROUGH)

            return firstBlock.name
        }

        private val defaultBlockForTag: HashMap<TagKey<Block>, Block> = hashMapOf()

        fun getBlockForTag(blockTag: TagKey<Block>): Block {
            return defaultBlockForTag.computeIfAbsent(blockTag) {
                BuiltInRegistries.BLOCK
                    .getTag(blockTag)
                    .getOrNull()
                    ?.firstOrNull()
                    ?.value()
                    ?: Blocks.AIR
            }
        }

        private val tagColors: HashMap<TagKey<Block>, Int> = hashMapOf()

        fun getColorForBlockTag(blockTag: TagKey<Block>): Int {
            return tagColors.computeIfAbsent(blockTag) {
                getBlockForTag(blockTag).defaultMapColor().col
            }
        }
    }

    override fun appendHoverText(
        stack: ItemStack,
        context: TooltipContext,
        tooltipComponents: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        val blockTag = stack.get(ModDataComponents.BLOCK_TAG) ?: return

        tooltipComponents.add(getNameForBlockTag(blockTag))
    }

}