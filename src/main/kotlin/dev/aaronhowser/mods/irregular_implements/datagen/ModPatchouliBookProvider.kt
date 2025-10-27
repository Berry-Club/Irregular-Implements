package dev.aaronhowser.mods.irregular_implements.datagen

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModCreativeModeTabs
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBook
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookCategory
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookElement
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookEntry
import dev.aaronhowser.mods.patchoulidatagen.multiblock.PatchouliMultiblock
import dev.aaronhowser.mods.patchoulidatagen.page.AbstractPage
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.MultiblockPage
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.SpotlightPage
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.TextPage
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider.Companion.TextColor
import net.minecraft.core.Direction
import net.minecraft.data.DataGenerator
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EndRodBlock
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredItem
import java.util.function.Consumer

class ModPatchouliBookProvider(
	generator: DataGenerator,
	bookName: String
) : PatchouliBookProvider(generator, bookName, IrregularImplements.MOD_ID) {

	override fun buildPages(consumer: Consumer<PatchouliBookElement>) {
		val book = PatchouliBook.builder()
			.setBookText(
				bookModId = IrregularImplements.MOD_ID,
				name = "Irregular Implements Instructional Index",
				landingText = "Welcome to the Irregular Implements!"
			)
			.creativeTab(ModCreativeModeTabs.MOD_TAB.key!!.location().toString())
			.hideProgress()
			.save(consumer)

		items(consumer, book)
		blocks(consumer, book)
	}

	private fun items(consumer: Consumer<PatchouliBookElement>, book: PatchouliBook) {
		val category = PatchouliBookCategory.builder()
			.book(book)
			.setDisplay(
				name = "Items",
				description = "All of the mods' items!",
				icon = ModItems.SPECTRE_KEY
			)
			.save(consumer, "items")

		fun add(
			item: DeferredItem<*>,
			name: String,
			vararg pages: AbstractPage
		): PatchouliBookEntry {
			val builder = PatchouliBookEntry.builder()
				.category(category)
				.display(
					entryName = name,
					icon = item
				)

			for (page in pages) {
				builder.addPage(page)
			}

			return builder.save(consumer, item.key!!.location().path)
		}

		add(
			ModItems.STABLE_ENDER_PEARL,
			"Stable Ender Pearl",
			TextPage.basicTextPage(
				"Stable Ender Pearl",
				doubleSpacedLines(
					"Use to bind yourself to the ${major("Stable Ender Pearl")}.",
					"After seven seconds of existing as an item entity, the Pearl will ${minor("teleport the bound player to its location")}."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.STABLE_ENDER_PEARL,
				doubleSpacedLines(
					"You don't have to drop it yourself! It'll still teleport you if it's dropped in any other way.",
					"It has to be loaded though, and you must be in the same dimension!"
				)
			)
		)

		add(
			ModItems.EVIL_TEAR,
			"Evil Tear",
			SpotlightPage.linkedPage(
				ModItems.EVIL_TEAR,
				"Evil Tear",
				doubleSpacedLines(
					"The ${major("Evil Tear")} can be used to make an ${minor("Artificial End Portal")}.",
					"Make the structure shown on the opposite page and then ${minor("use the Evil Tear on the End Rod")} to activate it.",
					"A portal will grow below it, and it will function exactly like a normal End Portal."
				)
			),
			MultiblockPage.builder()
				.name("Artificial End Portal")
				.multiblock(
					"Artificial End Portal",
					PatchouliMultiblock.builder()
						.setSymmetrical()
						.pattern(
							arrayOf(
								"     ",
								"     ",
								"  E  ",
								"     ",
								"     ",
							),
							arrayOf(
								"     ",
								"     ",
								"  R  ",
								"     ",
								"     ",
							),
							arrayOf(
								"     ",
								"     ",
								"     ",
								"     ",
								"     ",
							),
							arrayOf(
								"     ",
								"     ",
								"     ",
								"     ",
								"     ",
							),
							arrayOf(
								"BBBBB",
								"B   B",
								"B 0 B",
								"B   B",
								"BBBBB"
							),
							arrayOf(
								"     ",
								" EEE ",
								" EEE ",
								" EEE ",
								"     ",
							)
						)
						.map('B', Tags.Blocks.OBSIDIANS)
						.map('E', Tags.Blocks.END_STONES)
						.map('R', Blocks.END_ROD, EndRodBlock.FACING, Direction.DOWN)
						.build()
				)
				.build()
		)

		add(
			ModItems.PORTKEY,
			"Portkey",
			TextPage.basicTextPage(
				"Portkey",
				doubleSpacedLines(
					"${major("Portkeys")} will instantly teleport the player to a set location when they're picked up off the ground.",
					"To set the location, ${minor("click the Portkey on the ground")}, which will make it start glowing. Throw it on the ground and it will activate after several seconds, which will make it stop glowing.",
					"The only limit is that it cannot cross dimensions. The Portkey is also ${bad("consumed upon use")}."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.PORTKEY,
				"Additionally, you can ${minor("craft the Portkey with any other item")} to disguise it. The dropped item will look like the disguise rather than the Portkey itself."
			)
		)

		add(
			ModItems.BOTTLE_OF_AIR,
			"Bottle of Air",
			SpotlightPage.linkedPage(
				ModItems.BOTTLE_OF_AIR,
				"Bottle of Air",
				doubleSpacedLines(
					"The ${major("Bottle of Air")} can be \"drunk\" to refill your air supply while underwater.",
					"It can be found in ${minor("Ocean Monuments")}."
				)
			)
		)

		add(
			ModItems.GOLDEN_EGG,
			"Golden Egg",
			SpotlightPage.linkedPage(
				ModItems.GOLDEN_EGG,
				"Golden Egg",
				doubleSpacedLines(
					"The ${major("Golden Egg")} spawns a Chicken that ${minor("lays Golden Ingots")} instead of Eggs.",
					StringBuilder()
						.append("They can be found in ")
						.append(internalLink("blocks/bean_pod", "Bean Pods"))
						.append(", which can be found at the top of planted ")
						.append(internalLink("items/magic_bean", "Magic Beans"))
						.toString()
				)
			)
		)

		add(
			ModItems.EMERALD_COMPASS,
			"Emerald Compass",
			TextPage.basicTextPage(
				"Emerald Compass",
				doubleSpacedLines(
					"The ${major("Emerald Compass")} can be linked to a player, and  ${minor("aims towards them")}.",
					"Of course, it only works if the player is logged in, and is in the same dimension as you."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.EMERALD_COMPASS,
				doubleSpacedLines(
					"You can link the Compass to a player in two ways.",
					"First is to simply right-click them while holding the Compass.",
					"The other is to craft the Compass with a set ${internalLink("items/player_filter", "Player Filter")}."
				)
			)
		)

		add(
			ModItems.GOLDEN_COMPASS,
			"Golden Compass",
			TextPage.basicTextPage(
				"Golden Compass",
				doubleSpacedLines(
					"The ${major("Golden Compass")} can be linked to a specific location, and ${minor("aims towards it")}.",
					"Of course, it only works if you're in the same dimension as the location.",
					"Is this functionally identical to simply using a Lodestone? Maybe!"
				)
			),
			SpotlightPage.linkedPage(
				ModItems.GOLDEN_COMPASS,
				doubleSpacedLines(
					"To link the Compass to a location, craft it together with a set ${internalLink("items/location_filter", "Location Filter")}.",
					"There are some other ways as well, such as using it on a ${internalLink("blocks/biome_radar", "Biome Radar")} to link it to the located biome."
				)
			)
		)

		add(
			ModItems.BLAZE_AND_STEEL,
			"Blaze and Steel",
			SpotlightPage.linkedPage(
				ModItems.BLAZE_AND_STEEL,
				"Blaze and Steel",
				doubleSpacedLines(
					"The ${major("Blaze and Steel")} functions similarly to Flint and Steel, but the fire it lights is much more aggressive."
				)
			)
		)

		add(
			ModItems.ESCAPE_ROPE,
			"Escape Rope",
			TextPage.basicTextPage(
				"Escape Rope",
				doubleSpacedLines(
					"The ${major("Escape Rope")} can be used to quickly get out of caves.",
					"Hold right-click while in a cave, and it will quickly search for ${minor("the nearest location that can see the sky")}, and then teleport you there.",
					"If it can't find a valid location, it will be dropped at your feet."
				)
			)
		)

	}

	private fun blocks(consumer: Consumer<PatchouliBookElement>, book: PatchouliBook) {
		val category = PatchouliBookCategory.builder()
			.book(book)
			.setDisplay(
				name = "Blocks",
				description = "All of the mods' blocks!",
				icon = ModBlocks.PLAYER_INTERFACE
			)
			.save(consumer, "blocks")

		fun add(
			block: DeferredBlock<*>,
			name: String,
			vararg pages: AbstractPage
		): PatchouliBookEntry {
			val builder = PatchouliBookEntry.builder()
				.category(category)
				.display(
					entryName = name,
					icon = block
				)

			for (page in pages) {
				builder.addPage(page)
			}

			return builder.save(consumer, block.key!!.location().path)
		}

		add(
			ModBlocks.SPECTRE_LOG,
			"Spectre Log",
			TextPage.basicTextPage(
				"Spectre Log",
				"A log block infused with otherworldly energy."
			)
		)
	}

	private fun major(text: String): String {
		return colored(TextColor.LIGHT_PURPLE, text)
	}

	private fun minor(text: String): String {
		return colored(TextColor.DARK_AQUA, text)
	}

	private fun good(text: String): String {
		return colored(TextColor.GREEN, text)
	}

	private fun bad(text: String): String {
		return colored(TextColor.RED, text)
	}

}