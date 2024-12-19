package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.config.ClientConfig
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
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.common.Tags
import java.awt.Color
import kotlin.jvm.optionals.getOrNull

class DiviningRodItem : Item(
    Properties()
        .stacksTo(1)
) {

    companion object {

        fun getRodForItemTag(itemTag: TagKey<Item>): ItemStack {
            val blockTag = TagKey.create(Registries.BLOCK, itemTag.location)
            return getRodForBlockTag(blockTag)
        }

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

        fun getColor(blockState: BlockState): Color {
            for (blockTag in blockState.tags) {
                val tagString = blockTag.location.toString()
                val colorString = ClientConfig.DIVINING_ROD_COLORS.get()[tagString] ?: continue

                try {
                    return Color.decode(colorString)
                } catch (e: NumberFormatException) {
                    continue
                }

            }

            return Color.WHITE
        }

    }

    override fun appendHoverText(
        stack: ItemStack,
        context: TooltipContext,
        tooltipComponents: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        val blockTag = stack.get(ModDataComponents.BLOCK_TAG) ?: return

        val component = if (blockTag == Tags.Blocks.ORES) {
            Component
                .literal("All Ores")
                .withStyle(ChatFormatting.GRAY)
        } else {
            getNameForBlockTag(blockTag)
        }

        tooltipComponents.add(component)
    }

}