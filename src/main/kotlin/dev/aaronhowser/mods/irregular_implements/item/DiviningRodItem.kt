package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.block.Block
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
            val firstBlock = BuiltInRegistries.BLOCK
                .getTag(blockTag)
                .getOrNull()
                ?.firstOrNull()
                ?.value()
                ?: return Component.literal(blockTag.location.toString())

            return firstBlock.name
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