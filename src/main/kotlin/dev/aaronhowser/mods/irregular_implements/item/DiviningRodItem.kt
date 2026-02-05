package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
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
import kotlin.jvm.optionals.getOrNull

class DiviningRodItem(properties: Properties) : Item(properties) {

	override fun appendHoverText(
		stack: ItemStack,
		context: TooltipContext,
		tooltipComponents: MutableList<Component>,
		tooltipFlag: TooltipFlag
	) {
		val blockTag = stack.get(ModDataComponents.DIVINE_BLOCKS) ?: return

		val component = if (blockTag == Tags.Blocks.ORES) {
			ModTooltipLang.ALL_ORES.toGrayComponent()
		} else {
			getNameForBlockTag(blockTag)
		}

		tooltipComponents.add(component)

		ModTooltipLang.addSodiumTooltip(tooltipComponents)
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)

		fun getRodForItemTag(itemTag: TagKey<Item>): ItemStack {
			val blockTag = TagKey.create(Registries.BLOCK, itemTag.location)
			return getRodForBlockTag(blockTag)
		}

		fun getRodForBlockTag(blockTag: TagKey<Block>): ItemStack {
			val stack = ModItems.DIVINING_ROD.toStack()

			stack.set(ModDataComponents.DIVINE_BLOCKS, blockTag)

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

			return if (firstBlock == Blocks.AIR) {
				return Component
					.literal(blockTag.location.toString())
					.withStyle(ChatFormatting.RED)
					.withStyle(ChatFormatting.STRIKETHROUGH)
			} else {
				firstBlock.name.withStyle(ChatFormatting.GRAY)
			}
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

		private fun oreTag(name: String): TagKey<Block> =
			TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores/$name"))

		/**
		 * Can be added to using KubeJS.
		 *
		 * Here's an example:
		 * ```js
		 * const $DiviningRodItem = Java.loadClass('dev.aaronhowser.mods.irregular_implements.item.DiviningRodItem')
		 * const $ResourceLocation = Java.loadClass('net.minecraft.resources.ResourceLocation')
		 *
		 * $DiviningRodItem.COLORS_PER_TAG.put(
		 * 		$ResourceLocation.fromNamespaceAndPath('c', 'ores/mythril'),
		 * 		0xFF00FF
		 * )
		 * ```
		 */
		@JvmField
		val COLORS_PER_TAG: MutableMap<TagKey<Block>, Int> =
			mutableMapOf(
				Tags.Blocks.ORES_COAL to 0x141414,
				Tags.Blocks.ORES_IRON to 0xD3B09F,
				Tags.Blocks.ORES_GOLD to 0xF6E950,
				Tags.Blocks.ORES_LAPIS to 0x053096,
				Tags.Blocks.ORES_REDSTONE to 0xD30101,
				Tags.Blocks.ORES_EMERALD to 0x00DC00,
				Tags.Blocks.ORES_DIAMOND to 0x57DDE5,
				Tags.Blocks.ORES_COPPER to 0xC6522B,
				Tags.Blocks.ORES_QUARTZ to 0xDBCEBA,
				Tags.Blocks.ORES_NETHERITE_SCRAP to 0xB76659,
				oreTag("tin") to 0xAF9557,
				oreTag("osmium") to 0x416FAF,
				oreTag("uranium") to 0x5ED323,
				oreTag("fluorite") to 0xC462C1,
				oreTag("lead") to 0x5786CC
			)

		fun getOverlayColor(blockState: BlockState): Int {
			for ((tag, color) in COLORS_PER_TAG) {
				if (blockState.`is`(tag)) {
					return (50 shl 24) or color
				}
			}

			return (50 shl 24) or 0xFFFFFF
		}

		fun getItemColor(itemStack: ItemStack, tintIndex: Int): Int {
			if (tintIndex != 1) return 0xFFFFFFFF.toInt()

			val blockTag = itemStack.get(ModDataComponents.DIVINE_BLOCKS)

			if (blockTag != null) {
				val rgb = COLORS_PER_TAG[blockTag] ?: 0xFFFFFF
				return (0xFF shl 24) or rgb
			}

			return 0xFFFFFFFF.toInt()
		}
	}

}