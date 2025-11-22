package dev.aaronhowser.mods.irregular_implements.datagen

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModItemTagsProvider
import dev.aaronhowser.mods.irregular_implements.item.DiviningRodItem
import dev.aaronhowser.mods.irregular_implements.item.WeatherEggItem
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModCreativeModeTabs
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
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
import net.minecraft.core.HolderLookup
import net.minecraft.data.DataGenerator
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EndRodBlock
import net.minecraft.world.level.block.IronBarsBlock
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredItem
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

class ModPatchouliBookProvider(
	generator: DataGenerator,
	bookName: String,
	val lookupProvider: CompletableFuture<HolderLookup.Provider>
) : PatchouliBookProvider(generator, lookupProvider.get(), bookName, IrregularImplements.MOD_ID) {

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
					"To link the Compass to a location, craft it together with a set ${internalLink("items/location_filter", "Location Filter")}."
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
				)
			),
			SpotlightPage.linkedPage(
				ModItems.ESCAPE_ROPE,
				"If it can't find a valid location, it will be dropped at your feet."
			)
		)

		add(
			ModItems.CHUNK_ANALYZER,
			"Chunk Analyzer",
			SpotlightPage.linkedPage(
				ModItems.CHUNK_ANALYZER,
				"Chunk Analyzer",
				doubleSpacedLines(
					"The ${major("Chunk Analyzer")} can be used to analyze the current chunk you're in.",
					"USe it to see a list of ${minor("every block and their counts")} in the chunk."
				)
			)
		)

		add(
			ModItems.LAVA_CHARM,
			"Lava Charm",
			TextPage.basicTextPage(
				"Lava Charm",
				doubleSpacedLines(
					"The ${major("Lava Charm")} adds a temporary lava shield, visible above your armor bar.",
					"While the shield is full, you will be ${good("completely immune to lava damage")}. However, it does ${bad("not protect you from fire")}!",
					"Lava will still light you on fire, and you will still take damage from that!. Maybe invest in an ${internalLink("items/obsidian_skull", "Obsidian Skull")} as well?"
				)
			),
			SpotlightPage.linkedPage(
				ModItems.LAVA_CHARM,
				"Lava Charms can be found rarely in ${minor("dungeon chests")} and more commonly in ${minor("Nether Fortress chests")}."
			)
		)

		add(
			ModItems.OBSIDIAN_SKULL,
			"Obsidian Skull",
			SpotlightPage.linkedPage(
				ModItems.OBSIDIAN_SKULL,
				"Obsidian Skull",
				doubleSpacedLines(
					"The ${major("Obsidian Skull")} has a chance of ${minor("negating fire damage")} when held in the player's inventory.",
					"The more damage that would have been taken, the higher the chance of negation. The exact formula is ${colored(TextColor.DARK_RED, "(amount^3)/100")}.",
				)
			),
			SpotlightPage.linkedPage(
				ModItems.OBSIDIAN_SKULL_RING,
				"Obsidian Skull Ring",
				doubleSpacedLines(
					"You can also craft the ${major("Obsidian Skull Ring")}, which can be worn in the Curio's ring slot!"
				)
			)
		)

		add(
			ModItems.SUPER_LUBRICANT_TINCTURE,
			"Super Lubricant Tincture",
			TextPage.basicTextPage(
				"Super Lubricant Tincture",
				doubleSpacedLines(
					"The ${major("Super Lubricant Tincture")} can be crafted with any boot item to make them ${minor("completely negate friction")} when worn.",
					"That means if you start moving, you won't stop!"
				)
			),
			SpotlightPage.linkedPage(
				ModItems.SUPER_LUBRICANT_TINCTURE,
				"You can craft Lubricated boots with a Water Bottle to wash the lubricant off."
			)
		)

		add(
			ModItems.ENDER_BUCKET,
			"Ender Bucket",
			SpotlightPage.linkedPage(
				ModItems.ENDER_BUCKET,
				"Ender Bucket",
				doubleSpacedLines(
					"The ${major("Ender Bucket")} can be used to pick up fluids from a distance.",
					"If you use an empty bucket on a non-source fluid block, it will search for the nearest source block and pick that up instead."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.REINFORCED_ENDER_BUCKET,
				"Reinforced Ender Bucket",
				doubleSpacedLines(
					"The ${major("Reinforced Ender Bucket")} works the same way, but it can ${minor("hold 10 buckets worth of fluid")} at a time."
				)
			)
		)

		add(
			ModItems.LOTUS_BLOSSOM,
			"Lotus Blossom",
			SpotlightPage.linkedPage(
				ModItems.LOTUS_BLOSSOM,
				"Lotus Blossom",
				doubleSpacedLines(
					"${major("Lotus Blossoms")} can be eaten to give the player a handful of experience.",
					"If you eat them while sneaking, you'll consume the entire stack at once."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.LOTUS_SEEDS,
				"Lotus Seeds",
				doubleSpacedLines(
					"Lotus Blossoms can be farmed with ${major("Lotus Seeds")}, which are planted on any dirt/grass blocks.",
					"You can find naturally occurring Lotus plants in ${minor("cold biomes")}."
				)
			)
		)

		add(
			ModItems.MAGIC_HOOD,
			"Magic Hood",
			SpotlightPage.linkedPage(
				ModItems.MAGIC_HOOD,
				"Magic Hood",
				doubleSpacedLines(
					"The ${major("Magic Hood")}, when worn, will disable your nameplate and hide your potion particles.",
					"It can be found in dungeons and blacksmith chests."
				)
			)
		)

		add(
			ModItems.WATER_WALKING_BOOTS,
			"Water Walking Boots",
			TextPage.basicTextPage(
				"Water Walking Boots",
				doubleSpacedLines(
					"The ${major("Water Walking Boots")} allow you to ${minor("walk on water")} when worn.",
					"They do not work while sneaking, or when you're already under water.",
					"Do note that being able to $(o)stand$() on water means you're also able to ${bad("land")} on it as well!"
				)
			),
			SpotlightPage.linkedPage(
				ModItems.WATER_WALKING_BOOTS,
				"Water Walking Boots can be found in ${minor("Water Chests")}, which spawn in Ocean Monuments."
			),
			SpotlightPage.linkedPage(
				ModItems.OBSIDIAN_WATER_WALKING_BOOTS,
				"Obsidian Water Walking Boots",
				doubleSpacedLines(
					"The ${major("Obsidian Water Walking Boots")} combines the effects of the Water Walking Boots and the ${internalLink("items/obsidian_skull", "Obsidian Skull")}.",
					"Get it by combining the two in an Anvil.",
					"Remember that the Obsidian Skull does ${bad("not let you walk on lava")}! For that, you'll need the ${internalLink("items/lava_waders", "Lava Waders")}.",
				)
			)
		)

		add(
			ModItems.LAVA_WADERS,
			"Lava Waders",
			SpotlightPage.linkedPage(
				ModItems.LAVA_WADERS,
				"Lava Waders",
				doubleSpacedLines(
					"The ${major("Lava Waders")} combines the effects of the ${internalLink("items/water_walking_boots", "Water Walking Boots")} and the ${internalLink("items/lava_charm", "Lava Charm")}, allowing you to ${minor("walk on both lava and water")}!",
				)
			)
		)

		add(
			ModItems.LOCATION_FILTER,
			"Location Filter",
			TextPage.basicTextPage(
				"Location Filter",
				doubleSpacedLines(
					"The ${major("Location Filter")} allows you to ${minor("set a location")}, which is used by various items and blocks.",
					"Set the location by using it on the block you want to set it to."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.LOCATION_FILTER,
				"There are some other ways as well, such as using it on a ${internalLink("blocks/biome_radar", "Biome Radar")} to link it to the located biome."
			)
		)

		add(
			ModItems.ITEM_FILTER,
			"Item Filter",
			TextPage.basicTextPage(
				"Item Filter",
				doubleSpacedLines(
					"The ${major("Item Filter")} has a list of up to 9 items, which can act as either a blacklist or a whitelist for certain things.",
					"Use the Filter to open it, and place items in the slots to add them to the list.",
					"The button on the far right of the gui lets you ${minor("toggle between blacklist and whitelist mode")} for the entire Filter."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.ITEM_FILTER,
				doubleSpacedLines(
					"Above each filled slot is 2 buttons. The left button allows you to ${minor("toggle if the slot is an Item Filter or a Tag Filter")}.",
					"The right button changes depending on the above. If the slot is an Item Filter, it allows you to ${minor("toggle if it requires it to match the data components")}.",
					"If it's a Tag Filter, it allows you to ${minor("cycle the tag")}. For example, putting in an Iron Ingot and setting it to be a Tag Filter, you can cycle between it being \"Beacon Payments\", \"Ingots\", \"Trim Materials\", etc.",
				)
			)
		)

		add(
			ModItems.ENTITY_FILTER,
			"Entity Filter",
			TextPage.basicTextPage(
				"Entity Filter",
				doubleSpacedLines(
					"The ${major("Entity Filter")} allows you to ${minor("filter based on entity type")} for various items and blocks.",
					"To set it, simply use the Filter on the entity you want to filter."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.ENTITY_FILTER,
				"Using it while sneaking will set it to the entity type Player."
			)
		)

		add(
			ModItems.PLAYER_FILTER,
			"Player Filter",
			TextPage.basicTextPage(
				"The ${major("Player Filter")} allows you to ${minor("filter based on a specific player")}.",
				"To set it, use the Filter on the player you want to filter."
			),
			SpotlightPage.linkedPage(
				ModItems.PLAYER_FILTER,
				"Using it while sneaking will set it to yourself."
			)
		)

		add(
			ModItems.FIRE_IMBUE,
			"Imbues",
			TextPage.basicTextPage(
				"Imbues",
				doubleSpacedLines(
					"${major("Imbues")} can be crafted in the ${internalLink("blocks/imbuing_station", "Imbuing Station")} and act similarly to potions.",
					"When you drink an Imbue, you will be given a unique buff for ${minor("20 minutes")}. However, you can ${bad("only have one Imbue effect at a time")}."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.FIRE_IMBUE,
				"The ${major("Fire Imbue")} will light your opponent on fire when you strike them."
			),
			SpotlightPage.linkedPage(
				ModItems.POISON_IMBUE,
				"The ${major("Poison Imbue")} will inflict Poison II on your opponent when you strike them."
			),
			SpotlightPage.linkedPage(
				ModItems.WITHER_IMBUE,
				"The ${major("Wither Imbue")} will inflict Wither II on your opponent when you strike them."
			),
			SpotlightPage.linkedPage(
				ModItems.EXPERIENCE_IMBUE,
				"The ${major("Experience Imbue")} will increase the experience dropped by slain mobs by 50%."
			),
			SpotlightPage.linkedPage(
				ModItems.SPECTRE_IMBUE,
				doubleSpacedLines(
					"The ${major("Spectre Imbue")} will give you a ${minor("chance to completely negate incoming damage")}.",
					"The default chance is 10%, but this can be configured."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.COLLAPSE_IMBUE,
				doubleSpacedLines(
					"The ${major("Collapse Imbue")} will inflict the ${internalLink("blocks/sakanade_spores", "Collapse effect")} on your opponent when you strike them.",
				)
			)
		)

		add(
			ModItems.SPECTRE_ILLUMINATOR,
			"Spectre Illuminator",
			SpotlightPage.linkedPage(
				ModItems.SPECTRE_ILLUMINATOR,
				doubleSpacedLines(
					"The ${major("Spectre Illuminator")} can be used to ${minor("light up an entire chunk")}.",
					"It will place an entity down that slowly floats upwards, until it's a certain distance above the highest block in the chunk."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.BLACKOUT_POWDER,
				"This can be undone either by right-clicking the entity, or using a ${major("Blackout Powder")} anywhere in the chunk."
			)
		)

		add(
			ModItems.SPECTRE_KEY,
			"Spectre Key",
			TextPage.basicTextPage(
				"Spectre Key",
				doubleSpacedLines(
					"The ${major("Spectre Key")} can be used to ${minor("teleport to a private room in another dimension")}, unique to each player.",
					"Once there, you can build and store items safely, and they'll remain there any time you return. Leave by either using the Key again, or clicking one of the Spectre Core blocks on the room's floor."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.SPECTRE_KEY,
				doubleSpacedLines(
					"The room has a floor of 16x16 blocks, and starts out only 2 blocks tall.",
					"You can expand it vertically by using ${internalLink("items/ectoplasm", "Ectoplasm")} on the Spectre Core blocks.",
					"Each Ectoplasm increases the room's height by 1 block, and it will use the entire stack when clicked."
				)
			)
		)

		add(
			ModItems.SPECTRE_ANCHOR,
			"Spectre Anchor",
			SpotlightPage.linkedPage(
				ModItems.SPECTRE_ANCHOR,
				doubleSpacedLines(
					"The ${major("Spectre Anchor")} can be crafted with an item to ${minor("allow you to retain it after death")}.",
					"After this, the anchor is removed from the item."
				)
			)
		)

		add(
			ModItems.SPECTRE_SWORD,
			"Spectre Tools",
			SpotlightPage.linkedPage(
				ModItems.SPECTRE_SWORD,
				doubleSpacedLines(
					"The ${major("Spectre Sword")} is comparable to a Diamond Sword, with higher durability and enchantability.",
					"It increases your entity interaction range by 3 blocks, and can be used to kill Spirits."
				)
			),
			spotlight(
				listOf(
					ModItems.SPECTRE_PICKAXE,
					ModItems.SPECTRE_AXE,
					ModItems.SPECTRE_SHOVEL
				),
				" ",
				doubleSpacedLines(
					"The ${major("Spectre Pickaxe, Axe, and Shovel")} are each comparable to their Diamond counterpart, with higher durability and enchantability.",
					"Each of them increases your block interaction range by 3 blocks."
				),
				true
			)
		)

		add(
			ModItems.REDSTONE_ACTIVATOR,
			"Redstone Activator",
			TextPage.basicTextPage(
				"Redstone Activator",
				"Using the ${major("Redstone Activator")} will power it for a short duration."
			),
			SpotlightPage.linkedPage(
				ModItems.REDSTONE_ACTIVATOR,
				doubleSpacedLines(
					"Sneak right-click it to change the duration between 2, 20, and 100 ticks."
				)
			)
		)

		add(
			ModItems.REDSTONE_REMOTE,
			"Redstone Remote",
			TextPage.basicTextPage(
				"Redstone Remote",
				doubleSpacedLines(
					"The ${major("Redstone Remote")} acts like the ${internalLink("items/redstone_activator", "Redstone Activator")}, but instead powers up to 9 selectable locations.",
					"Sneak right-click to open its GUI. Place a filled ${internalLink("items/location_filter", "Location Filter")} in one of the top slots to add it. You can optionally place any item in the slot below it to add an icon to the location button."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.REDSTONE_REMOTE,
				doubleSpacedLines(
					"Use while not sneaking to access the location buttons. Clicking one will power that location for a short duration."
				)
			)
		)

		add(
			ModItems.BIOME_PAINTER,
			"Biome Painter",
			SpotlightPage.linkedPage(
				ModItems.BIOME_CAPSULE,
				doubleSpacedLines(
					"The ${major("Biome Capsule")} allows you to capture a biome from the world.",
					"Throw it on the ground, and if the Capsule has no biome it will be set to the biome at that location.",
					"As it sits in that biome, it will gain points. Hover over it in the inventory to see it current amount."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.BIOME_PAINTER,
				doubleSpacedLines(
					"The ${major("Biome Painter")} allows you to change the biome in a radius around the selected block.",
					"While held, it will show you the nearby blocks that $(o)aren't$() the same as the first filled Biome Capsule in your inventory. Using the Painter on those blocks will change the biome there."
				)
			)
		)

		add(
			ModItems.DROP_FILTER,
			"Drop Filter",
			SpotlightPage.linkedPage(
				ModItems.DROP_FILTER,
				doubleSpacedLines(
					"The ${major("Drop Filter")} can hold an ${internalLink("items/item_filter", "Item Filter")}, and prevents any items matching the Filter from entering your inventory."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.VOIDING_DROP_FILTER,
				doubleSpacedLines(
					"The ${major("Voiding Drop Filter")} works the same way, but instead ${bad("deletes any matching items")}."
				)
			)
		)

		add(
			ModItems.VOID_STONE,
			"Void Stone",
			SpotlightPage.linkedPage(
				ModItems.VOID_STONE,
				"The ${major("Void Stone")} will ${minor("delete any item")} insert into its menu.",
			)
		)

		add(
			ModItems.WHITE_STONE,
			"White Stone",
			TextPage.basicTextPage(
				"White Stone",
				doubleSpacedLines(
					"The ${major("White Stone")} has the power to ${minor("prevent your death once")} while charged. Doing so fully discharges the item.",
					"To recharge it, ${minor("expose it to the full moon")}. It will gain charge while it's under the night sky while the full moon is at its peak, while either dropped on the ground or in your inventory."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.WHITE_STONE,
				"The White Stone can be rarely found in dungeon chests."
			)
		)

		add(
			ModItems.PORTABLE_ENDER_BRIDGE,
			"Portable Ender Bridge",
			SpotlightPage.linkedPage(
				ModItems.PORTABLE_ENDER_BRIDGE,
				"Portable Ender Bridge",
				"The ${major("Portable Ender Bridge")} allows you to teleport to any nearby ${internalLink("blocks/ender_bridge", "anchor", "Ender Anchor")}, including through blocks!"
			)
		)

		add(
			ModItems.BLOCK_MOVER,
			"Block Mover",
			TextPage.basicTextPage(
				"Block Mover",
				doubleSpacedLines(
					"The ${major("Block Mover")} allows you to pick up and move blocks around easily. This ${minor("includes blocks with block entities")}, like Chests!",
					"Right-click a block to pick it up, then right-click again to place it down."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.BLOCK_MOVER,
				doubleSpacedLines(
					"Blocks with the block tag ${bad("#irregular_implements:block_mover_blacklist")} cannot be moved with the Block Mover.",
					"By default, this includes blocks like Bedrock."
				)
			)
		)

		add(
			ModItems.BLOCK_REPLACER,
			"Block Replacer",
			TextPage.basicTextPage(
				"Block Replacer",
				doubleSpacedLines(
					"The ${major("Block Replacer")} allows you to quickly replace blocks in the world with blocks stored in the Block Replacer.",
					"To store a block in the Block Replacer, ${minor("right-click the block stack in your inventory onto the Block Replacer")}, like a Bundle.",
					"You can remove the stored block by right-clicking the Block Replacer itemstack onto any empty inventory slot."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.BLOCK_REPLACER,
				doubleSpacedLines(
					"Using the item on a block in the world will break that block and place a random stored block in its place.",
					"Blocks with the block tag ${bad("#irregular_implements:block_replacer_blacklist")} cannot be replaced with the Block Replacer."
				)
			)
		)

		add(
			ModItems.GRASS_SEEDS,
			"Grass Seeds",
			SpotlightPage.linkedPage(
				ModItems.GRASS_SEEDS,
				"Grass Seeds",
				doubleSpacedLines(
					"${major("Grass Seeds")} can be used on blocks like Dirt to turn them into Grass.",
					"Specifically, it works on any block with the tag ${minor("irregular_implements:grass_seeds_compatible")}."
				)
			),
			spotlight(
				listOf(
					ModItems.GRASS_SEEDS_RED,
					ModItems.GRASS_SEEDS_WHITE,
					ModItems.GRASS_SEEDS_ORANGE,
					ModItems.GRASS_SEEDS_MAGENTA,
					ModItems.GRASS_SEEDS_LIGHT_BLUE,
					ModItems.GRASS_SEEDS_YELLOW,
					ModItems.GRASS_SEEDS_LIME,
					ModItems.GRASS_SEEDS_PINK,
					ModItems.GRASS_SEEDS_GRAY,
					ModItems.GRASS_SEEDS_LIGHT_GRAY,
					ModItems.GRASS_SEEDS_CYAN,
					ModItems.GRASS_SEEDS_PURPLE,
					ModItems.GRASS_SEEDS_BLUE,
					ModItems.GRASS_SEEDS_BROWN,
					ModItems.GRASS_SEEDS_GREEN,
					ModItems.GRASS_SEEDS_RED,
					ModItems.GRASS_SEEDS_BLACK
				),
				"Colored Grass Seeds",
				doubleSpacedLines(
					"There are also ${major("Colored Grass Seeds")}, which plant Colored Grass of their respective color.",
					"They act exactly the same as regular Grass Blocks, but are colored."
				),
				true
			)
		)

		add(
			ModItems.DIVINING_ROD,
			"Divining Rods",
			stacksSpotlight(
				listOf(
					DiviningRodItem.getRodForBlockTag(Tags.Blocks.ORES_COAL),
					DiviningRodItem.getRodForBlockTag(Tags.Blocks.ORES_COPPER),
					DiviningRodItem.getRodForBlockTag(Tags.Blocks.ORES_IRON),
					DiviningRodItem.getRodForBlockTag(Tags.Blocks.ORES_GOLD),
					DiviningRodItem.getRodForBlockTag(Tags.Blocks.ORES_REDSTONE),
					DiviningRodItem.getRodForBlockTag(Tags.Blocks.ORES_EMERALD),
					DiviningRodItem.getRodForBlockTag(Tags.Blocks.ORES_LAPIS),
					DiviningRodItem.getRodForBlockTag(Tags.Blocks.ORES_DIAMOND),
					DiviningRodItem.getRodForBlockTag(Tags.Blocks.ORES_NETHERITE_SCRAP),
					DiviningRodItem.getRodForBlockTag(Tags.Blocks.ORES_QUARTZ),
				),
				"Divining Rods",
				doubleSpacedLines(
					"When holding a ${major("Divining Rod")} in your hand, you'll be able to ${minor("see the corresponding ore block through walls")} at a configurable distance.",
					"You can make a Divining Rod out of any ore item that has an item tag starting with \"${UNDERLINE}c:ores/ ${RESET}\", and it will show that ore type."
				),
				true
			),
			stacksSpotlight(
				listOf(
					DiviningRodItem.getRodForBlockTag(Tags.Blocks.ORES),
				),
				"Universal Divining Rod",
				"You can craft them all together into one that will ${minor("show all ore types")}!",
				true
			)
		)

		add(
			ModItems.SPECTRE_CHARGER_BASIC,
			"Spectre Chargers",
			TextPage.basicTextPage(
				"Spectre Chargers",
				doubleSpacedLines(
					"${major("Spectre Chargers")} are used to charge items in your inventory using FE from your ${internalLink("blocks/spectre_energy_injector", "Spectre Energy buffer")}.",
					"It will charge every item in your inventory that can be charged, including your armor and any equipped Curios."
				)
			),
			spotlight(
				listOf(
					ModItems.SPECTRE_CHARGER_BASIC,
					ModItems.SPECTRE_CHARGER_REDSTONE,
					ModItems.SPECTRE_CHARGER_ENDER,
					ModItems.SPECTRE_CHARGER_GENESIS
				),
				" ",
				doubleSpacedLines(
					"Each tier charges faster than the last.",
					"The Genesis Spectre Charger is creative-only, and does not use energy from your buffer."
				),
				true
			)
		)

		add(
			ModItems.SUMMONING_PENDULUM,
			"Summoning Pendulum",
			TextPage.basicTextPage(
				"Summoning Pendulum",
				doubleSpacedLines(
					"The ${major("Summoning Pendulum")} can be used to ${minor("store mobs to place them later")}.",
					"Simply right-click a mob with the Pendulum to store it inside. Then, right-click anywhere to summon it back out."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.SUMMONING_PENDULUM,
				doubleSpacedLines(
					"The Summoning Pendulum can store up to 5 mobs at a time.",
					"Any mobs with the entity type tag ${bad("#irregular_implements:summoning_pendulum_blacklist")} cannot be stored in the Pendulum."
				)
			)
		)

		add(
			ModItems.SPECTRE_CHESTPLATE,
			"Spectre Armor",
			TextPage.basicTextPage(
				"Spectre Armor",
				doubleSpacedLines(
					"${major("Spectre armor")} is comparable to Diamond armor, with higher durability and enchantability.",
				)
			),
			spotlight(
				listOf(
					ModItems.SPECTRE_HELMET,
					ModItems.SPECTRE_CHESTPLATE,
					ModItems.SPECTRE_LEGGINGS,
					ModItems.SPECTRE_BOOTS
				),
				" ",
				"Wearing a full set also makes you slightly transparent!",
				true
			)
		)

		add(
			ModItems.WEATHER_EGG,
			"Weather Eggs",
			TextPage.basicTextPage(
				"Weather Eggs",
				doubleSpacedLines(
					"${major("Weather Eggs")} allow you to ${minor("change the weather")} when thrown.",
					"There are 3 types: Sunny, Rainy, and Stormy.",
					"Naturally, throwing a Sunny Egg will clear the weather, a Rainy Egg will cause rain, and a Stormy Egg will cause a thunderstorm."
				)
			),
			stacksSpotlight(
				listOf(
					WeatherEggItem.fromWeather(WeatherEggItem.Weather.SUNNY),
					WeatherEggItem.fromWeather(WeatherEggItem.Weather.RAINY),
					WeatherEggItem.fromWeather(WeatherEggItem.Weather.STORMY)
				),
				" ",
				"Throwing an Egg that matches the current weather will do nothing.",
				true
			)
		)

		add(
			ModItems.LUMINOUS_POWDER,
			"Luminous Powder",
			TextPage.basicTextPage(
				"Luminous Powder",
				doubleSpacedLines(
					"The ${major("Luminous Powder")} can be applied to any item to make it glow in the dark!",
					"Simply craft the Powder with any item to apply it."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.LUMINOUS_POWDER,
				doubleSpacedLines(
					"You can ${minor("craft an illuminated item with a Water Bucket")} to clean it, removing the illumination.",
				)
			)
		)

		add(
			ModItems.ENDER_LETTER,
			"Ender Letter",
			TextPage.basicTextPage(
				"Ender Letter",
				doubleSpacedLines(
					"${major("Ender Letters")} allow you to ${minor("send items to other players")} easily.",
					"Use the Letter to open it. You can insert ${minor("up to 9 items")} to send, and type the recipient's username in the text box."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.ENDER_LETTER,
				doubleSpacedLines(
					"To send the letter, simply use it on an ${internalLink("blocks/ender_mailbox", "Ender Mailbox")}. The Letter will be inserted into the recipient's mailbox, if it has room."
				)
			)
		)

		add(
			ModItems.FLOO_SIGN,
			"Floo Network",
			SpotlightPage.linkedPage(
				ModItems.FLOO_SIGN,
				"Floo Network",
				doubleSpacedLines(
					"The ${major("Floo network")} allows you to teleport between different ${minor("Floo Fireplaces")} within the same dimension.",
					"To make a Fireplace, use a ${major("Floo Sign")} on Bricks in the world. It will turn into ${minor("Floo Bricks")}, sharing the Sign's custom anvil name."
				)
			),
			SpotlightPage.linkedPage(
				ModBlocks.FLOO_BRICK,
				doubleSpacedLines(
					"If the Sign was not named, the Fireplace will also not be named. Nameless Fireplaces ${bad("cannot be teleported to")}. but can be teleported from.",
					"To teleport to a Fireplace, stand on any other Fireplace and then ${minor("say its name in chat")}.",
					"Teleporting requires Floo Powder; see the next page for details."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.FLOO_POWDER,
				"To teleport, you need to be holding ${major("Floo Powder")} in your hand. It will consume a single item per teleport."
			),
			SpotlightPage.linkedPage(
				ModItems.FLOO_POUCH,
				doubleSpacedLines(
					"Alternatively, you can hold a ${major("Floo Pouch")}, which can hold up to 128 Floo Powder and works when held anywhere in your inventory.",
					"Sneak right-click the Floo Pouch to fill it up with Floo Powder from your inventory."
				),
			),
		)

		add(
			ModItems.FLOO_TOKEN,
			"Floo Token",
			SpotlightPage.linkedPage(
				ModItems.FLOO_TOKEN,
				"Floo Token",
				doubleSpacedLines(
					"The ${major("Floo Token")} allows you to ${minor("teleport to any")} ${internalLink("items/floo_sign", "Floo Fireplace")} ${minor("from anywhere in the world.")}.",
					"Throw it on the ground and wait for the fire to burn in a square. Stand in the square and then speak the name of the Fireplace you wish to teleport to."
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
			ModBlocks.FERTILIZED_DIRT,
			"Fertilized Dirt",
			SpotlightPage.linkedPage(
				ModBlocks.FERTILIZED_DIRT,
				"Fertilized Dirt",
				doubleSpacedLines(
					"${major("Fertilized Dirt")} does not need to be hydrated, cannot be trampled, and ${minor("grows crops 3 times faster")}.",
					"You still have to till it with a Hoe to plant crops on it."
				)
			)
		)

		add(
			ModBlocks.IMBUING_STATION,
			"Imbuing Station",
			TextPage.basicTextPage(
				"Imbuing Station",
				doubleSpacedLines(
					"The ${major("Imbuing Station")} is used to craft ${internalLink("items/imbue_fire", "Imbues")}.",
					"Ingredients that go in the outer slots can go in any outer slot, but the center ingredient must go in the center slot."
				)
			),
			SpotlightPage.linkedPage(
				ModBlocks.IMBUING_STATION,
				doubleSpacedLines(
					"For automation, the center slot can be accessed from the top face, the other input slots can be accessed from the sides, and the output slot can be accessed from the bottom.",
				)
			)
		)

		add(
			ModBlocks.RAIN_SHIELD,
			"Rain Shield",
			SpotlightPage.linkedPage(
				ModBlocks.RAIN_SHIELD,
				"Rain Shield",
				doubleSpacedLines(
					"The ${major("Rain Shield")} prevents rain from falling in a radius around it. By default, this radius is 5 chunks.",
					"It can be disabled with a Redstone signal."
				)
			)
		)

		add(
			ModBlocks.PEACE_CANDLE,
			"Peace Candle",
			TextPage.basicTextPage(
				"Peace Candle",
				doubleSpacedLines(
					"The ${major("Peace Candle")} prevents hostile mobs from spawning in a radius around it. By default, this radius a single chunk (so a 3x3 chunk area).",
					"It can be disabled with a Redstone signal."
				)
			),
			SpotlightPage.linkedPage(
				ModBlocks.PEACE_CANDLE,
				doubleSpacedLines(
					"Peace Candles can be found in certain Village temples. Where there would be a Brewing Stand, instead you may sometimes find a Peace Candle."
				)
			)
		)

		add(
			ModBlocks.SLIME_CUBE,
			"Slime Cube",
			SpotlightPage.linkedPage(
				ModBlocks.SLIME_CUBE,
				"Slime Cube",
				doubleSpacedLines(
					"The ${major("Slime Cube")}, when unpowered, causes Slimes to spawn in a radius around it. By default, this radius is 1 chunk (so a 3x3 chunk area).",
					"When powered, however, it prevents Slimes from spawning in the same area."
				)
			)
		)

		add(
			ModBlocks.OAK_PLATFORM,
			"Platforms",
			spotlight(
				listOf(
					ModBlocks.OAK_PLATFORM,
					ModBlocks.OAK_PLATFORM,
					ModBlocks.SPRUCE_PLATFORM,
					ModBlocks.BIRCH_PLATFORM,
					ModBlocks.JUNGLE_PLATFORM,
					ModBlocks.ACACIA_PLATFORM,
					ModBlocks.DARK_OAK_PLATFORM,
					ModBlocks.CRIMSON_PLATFORM,
					ModBlocks.WARPED_PLATFORM,
					ModBlocks.MANGROVE_PLATFORM,
					ModBlocks.BAMBOO_PLATFORM,
					ModBlocks.CHERRY_PLATFORM
				),
				"Platforms",
				doubleSpacedLines(
					"${major("Platforms")} are ${minor("solid on top but not from the bottom or sides")}.",
					"Additionally, sneaking will allow you to fall through them."
				),
				true
			),
			SpotlightPage.linkedPage(
				ModBlocks.SUPER_LUBRICANT_PLATFORM,
				"Super Lubricant Platform",
				doubleSpacedLines(
					"The ${major("Super Lubricant Platform")} acts the same way but fully negates friction, just like the other ${internalLink("blocks/super_lubricant_stone", "Super Lubricated blocks")}.",
					"This makes it very useful for transporting items, especially when used with ${internalLink("blocks/plates", "Plates")}."
				)
			),
			SpotlightPage.linkedPage(
				ModBlocks.FILTERED_SUPER_LUBRICANT_PLATFORM,
				"Filtered Super Lubricant Platform",
				doubleSpacedLines(
					"The ${major("Filtered Super Lubricant Platform")} works the same way, but can accept an ${internalLink("items/item_filter", "Item Filter")}.",
					"Any items matching the Filter will fall through the Platform."
				)
			)
		)

		add(
			ModBlocks.SUPER_LUBRICANT_STONE,
			"Super Lubricated Blocks",
			SpotlightPage.linkedPage(
				ModItemTagsProvider.SUPER_LUBRICATED_BLOCKS,
				"Super Lubricated Blocks",
				doubleSpacedLines(
					"${major("Super Lubricated Blocks")} fully negate friction, allowing entities to move across them without slowing down.",
					"This is very useful for transporting items, especially when used with ${internalLink("blocks/plates", "Plates")}."
				)
			)
		)

		add(
			ModBlocks.LAPIS_GLASS,
			"Permeable Glass Blocks",
			SpotlightPage.linkedPage(
				ModBlocks.LAPIS_GLASS,
				"Lapis Glass",
				doubleSpacedLines(
					"${major("Lapis Glass")} is solid for players, but allows all other entities to pass through it.",
				)
			),
			SpotlightPage.linkedPage(
				ModBlocks.QUARTZ_GLASS,
				"Quartz Glass",
				doubleSpacedLines(
					"${major("Quartz Glass")} allows players to pass through it, but is solid for all other entities.",
				)
			)
		)

		add(
			ModBlocks.TRIGGER_GLASS,
			"Trigger Glass",
			SpotlightPage.linkedPage(
				ModBlocks.TRIGGER_GLASS,
				"Trigger Glass",
				doubleSpacedLines(
					"${major("Trigger Glass")} is usually solid, but if it gets a Redstone pulse, it becomes non-solid for a short duration.",
					"This effect propagates to all connected Trigger Glass blocks, within a distance."
				)
			)
		)

		add(
			ModBlocks.RAINBOW_LAMP,
			"Rainbow Lamp",
			SpotlightPage.linkedPage(
				ModBlocks.RAINBOW_LAMP,
				"Rainbow Lamp",
				doubleSpacedLines(
					"The ${major("Rainbow Lamp")} has a different color depending on the strength of the Redstone signal powering it."
				)
			)
		)

		add(
			ModBlocks.SHOCK_ABSORBER,
			"Shock Absorber",
			SpotlightPage.linkedPage(
				ModBlocks.SHOCK_ABSORBER,
				"Shock Absorber",
				doubleSpacedLines(
					"The ${major("Shock Absorber")} fully negates fall damage when landed on.",
					"Additionally, it will emit a Redstone signal proportional to the fall distance when landed on."
				)
			)
		)

		add(
			ModBlocks.BLOCK_TELEPORTER,
			"Block Teleporter",
			TextPage.basicTextPage(
				"Block Teleporter",
				doubleSpacedLines(
					"The ${major("Block Teleporter")} allows you to ${minor("teleport the block in front of itself to another Block Teleporter")} when powered by Redstone.",
					"Use a ${internalLink("items/location_filter", "Location Filter")} on a Block Teleporter to save its location, and then insert that Filter into a second Block Teleporter.",
				)
			),
			SpotlightPage.linkedPage(
				ModBlocks.BLOCK_TELEPORTER,
				doubleSpacedLines(
					"When that second Block Teleporter is powered, it will try to swap the block in front of the first Block Teleporter with the block in front of itself.",
					"Whether or not the Block Teleporter works across dimensions can be configured, but it defaults to true."
				)
			)
		)

		add(
			ModBlocks.MOON_PHASE_DETECTOR,
			"Moon Phase Detector",
			SpotlightPage.linkedPage(
				ModBlocks.MOON_PHASE_DETECTOR,
				"Moon Phase Detector",
				doubleSpacedLines(
					"The ${major("Moon Phase Detector")} emits a Redstone signal strength based on the current moon phase.",
					"It emits a full signal (15) during a full moon, and no signal (0) during a new moon.",
					"You can invert this behavior by right-clicking it."
				)
			)
		)

		add(
			ModBlocks.SIDED_BLOCK_OF_REDSTONE,
			"Sided Block of Redstone",
			SpotlightPage.linkedPage(
				ModBlocks.SIDED_BLOCK_OF_REDSTONE,
				"Sided Block of Redstone",
				doubleSpacedLines(
					"The ${major("Sided Block of Redstone")} emits a Redstone signal only from its front face.",
				)
			)
		)

		add(
			ModBlocks.COMPRESSED_SLIME_BLOCK,
			"Compressed Slime Block",
			SpotlightPage.linkedPage(
				ModBlocks.COMPRESSED_SLIME_BLOCK,
				"Compressed Slime Block",
				doubleSpacedLines(
					"A ${major("Compressed Slime Block")} will bounce entities that touch it up into the air.",
					"Get it by using a Shovel on a Slime Block. You can compress it multiple times for a stronger bounce effect."
				)
			)
		)

		add(
			ModBlocks.ANALOG_EMITTER,
			"Analog Emitter",
			SpotlightPage.linkedPage(
				ModBlocks.ANALOG_EMITTER,
				"Analog Emitter",
				doubleSpacedLines(
					"The ${major("Analog Emitter")}, when powered on its front face, emits a redstone signal with a configurable strength.",
					"Right-click it to cycle the output strength."
				)
			)
		)

		add(
			ModBlocks.CONTACT_LEVER,
			"Contact Lever",
			SpotlightPage.linkedPage(
				ModBlocks.CONTACT_LEVER,
				"Contact Lever",
				doubleSpacedLines(
					"When the block in front of the ${major("Contact Lever")} is clicked, the Contact Lever will toggle between on and off.",
					"While on, it emits a Redstone signal from its other faces."
				)
			)
		)

		add(
			ModBlocks.CONTACT_BUTTON,
			"Contact Button",
			SpotlightPage.linkedPage(
				ModBlocks.CONTACT_BUTTON,
				"Contact Button",
				doubleSpacedLines(
					"When the block in front of the ${major("Contact Button")} is clicked, the Contact Button will emit a short Redstone pulse from its other faces.",
				)
			)
		)

		add(
			ModBlocks.IGNITER,
			"Igniter",
			TextPage.basicTextPage(
				"Igniter",
				doubleSpacedLines(
					"The ${major("Igniter")} can be used to light a fire when given a Redstone signal.",
					"It has 3 modes, which you can cycle in its GUI.",
				)
			),
			SpotlightPage.linkedPage(
				ModBlocks.IGNITER,
				dottedLines(
					"Toggle - Lights a fire when powered, and extinguishes it when unpowered.",
					"Keep Ignited - Keeps the fire lit while powered, and does nothing when unpowered.",
					"Ignite - Lights a fire when powered, and does nothing when unpowered."
				)
			)
		)

		add(
			ModBlocks.BLOCK_DETECTOR,
			"Block Detector",
			SpotlightPage.linkedPage(
				ModBlocks.BLOCK_DETECTOR,
				"Block Detector",
				doubleSpacedLines(
					"The ${major("Block Detector")} emits a Redstone signal when the block in front of it matches the block stored in its inventory."
				)
			)
		)

		add(
			ModBlocks.INVENTORY_TESTER,
			"Inventory Tester",
			TextPage.basicTextPage(
				"Inventory Tester",
				doubleSpacedLines(
					"The ${major("Inventory Tester")} is placed on the side of an inventory and holds an item.",
					"It emits a Redstone signal when that inventory is capable of accepting that item.",
					"It also checks the ${ITALIC}side${RESET} of the inventory it's attached to. If placed on the top of a Furnace, it will only check if the Furnace can accept the item from the top slot."
				)
			),
			SpotlightPage.linkedPage(
				ModBlocks.INVENTORY_TESTER,
				doubleSpacedLines(
					"Because of that, you may also want to use the ${internalLink("blocks/inventory_rerouter", "Inventory Rerouter")} to access the inventory's face from another side.",
					"You can invert it in its GUI, so it emits a signal when the inventory cannot accept the item.",
				)
			)
		)

		add(
			ModBlocks.BLOCK_OF_STICKS,
			"Block of Sticks",
			SpotlightPage.linkedPage(
				ModBlocks.BLOCK_OF_STICKS,
				"Block of Sticks",
				doubleSpacedLines(
					"The ${major("Block of Sticks")} breaks itself shortly after being placed.",
					"This makes it an effective scaffolding block."
				)
			),
			SpotlightPage.linkedPage(
				ModBlocks.RETURNING_BLOCK_OF_STICKS,
				"Returning Block of Sticks",
				doubleSpacedLines(
					"When the ${major("Returning Block of Sticks")} breaks itself, it will teleport its drops to the nearest player."
				)
			)
		)

		add(
			ModBlocks.SPECTRE_LENS,
			"Spectre Lens",
			SpotlightPage.linkedPage(
				ModBlocks.SPECTRE_LENS,
				"Spectre Lens",
				doubleSpacedLines(
					"The ${major("Spectre Lens")}, when placed on top of a Beacon, allows it to effect you from any distance, as long as you're in the same dimension.",
					"It only extends this effect to the player that placed the Lens."
				)
			)
		)

		add(
			ModBlocks.ONLINE_DETECTOR,
			"Online Detector",
			SpotlightPage.linkedPage(
				ModBlocks.ONLINE_DETECTOR,
				"Online Detector",
				doubleSpacedLines(
					"The ${major("Online Detector")} emits a Redstone signal when the chosen player is logged in to the server.",
					"Type the players' exact username into its GUI to set it."
				)
			)
		)

		add(
			ModBlocks.CHAT_DETECTOR,
			"Chat Detector",
			SpotlightPage.linkedPage(
				ModBlocks.CHAT_DETECTOR,
				"Chat Detector",
				doubleSpacedLines(
					"The ${major("Chat Detector")} emits a Redstone signal when the player that placed it says a specific phrase in chat.",
					"The text box in its GUI is actually a ${ITALIC}regex${RESET} field, so it can be pretty fancy.",
					"You can also toggle if the message gets canceled or not."
				)
			),
			SpotlightPage.linkedPage(
				ModBlocks.GLOBAL_CHAT_DETECTOR,
				"Global Chat Detector",
				doubleSpacedLines(
					"The ${major("Global Chat Detector")} works the same way, but listens to chat messages from all players instead of only whoever placed it.",
					"It can only cancel messages sent by its owner, though."
				)
			)
		)

		add(
			ModBlocks.CUSTOM_CRAFTING_TABLE,
			"Custom Crafting Table",
			SpotlightPage.linkedPage(
				ModBlocks.CUSTOM_CRAFTING_TABLE,
				"Custom Crafting Table",
				doubleSpacedLines(
					"The ${major("Custom Crafting Table")} is functionally identical to a normal Crafting Table, but looks like the block it was crafted with.",
					"A ring of Oak Logs, for example, around a Crafting Table in the recipe will make it look like an Oak Log block."
				)
			)
		)

		add(
			ModBlocks.DIAPHANOUS_BLOCK,
			"Diaphanous Block",
			TextPage.basicTextPage(
				"Diaphanous Block",
				doubleSpacedLines(
					"The ${major("Diaphanous Block")} looks like another block from a distance, but vanishes as you approach.",
					"To set the appearance, craft it with the desired block."
				)
			),
			SpotlightPage.builder()
				.text("Crafting it with itself will invert it, so it's visible from up close but vanishes at a distance.")
				.linkRecipe(true)
				.addItemLike(ModBlocks.DIAPHANOUS_BLOCK)
				.addItemStack(
					ModBlocks.DIAPHANOUS_BLOCK.asItem()
						.defaultInstance
						.apply {
							set(ModDataComponents.BLOCK, Blocks.OAK_LOG)
						}
				)
				.build()
		)

		add(
			ModBlocks.ADVANCED_REDSTONE_TORCH,
			"Advanced Redstone Torch",
			SpotlightPage.linkedPage(
				ModBlocks.ADVANCED_REDSTONE_TORCH,
				"Advanced Redstone Torch",
				doubleSpacedLines(
					"The ${major("Advanced Redstone Torch")} has a configurable redstone output when powered and when unpowered.",
					"USe its GUI strength to set these two values."
				)
			)
		)

		add(
			ModBlocks.BIOME_STONE_BRICKS,
			"Biome Blocks",
			spotlight(
				listOf(
					ModBlocks.BIOME_STONE,
					ModBlocks.BIOME_COBBLESTONE,
					ModBlocks.BIOME_STONE_BRICKS,
					ModBlocks.BIOME_STONE_BRICKS_CRACKED,
					ModBlocks.BIOME_STONE_BRICKS_CHISELED,
					ModBlocks.BIOME_GLASS
				),
				" ",
				doubleSpacedLines(
					"${major("Biome blocks")} change their color to match the biome they're placed in.",
					"They'll be green in lush biomes, brown in dry biomes, etc."
				),
				true
			)
		)

		add(
			ModBlocks.SPECTRE_ENERGY_INJECTOR,
			"Spectre Energy Injector",
			TextPage.basicTextPage(
				"Spectre Energy Injector",
				doubleSpacedLines(
					"Every player has a ${minor("Spectre Energy buffer")} which acts sort of ${minor("like an Ender Chest, but for FE")} instead of items.",
					"By default, this pool can store up to 1,000,000 FE. This amount can be changed in the server config."
				),
			),
			SpotlightPage.linkedPage(
				ModBlocks.SPECTRE_ENERGY_INJECTOR,
				doubleSpacedLines(
					"The ${major("Spectre Energy Injector")} allows you to ${minor("insert FE into the pool")}. It's owned by whoever placed it.",
					"You ${bad("cannot extract from the Injector")}. You'll have to use a ${internalLink("blocks/spectre_coil_basic", "Spectre Coil")} or a ${internalLink("items/spectre_charger_basic", "Spectre Charger")} to do that."
				)
			)
		)

		add(
			ModBlocks.SPECTRE_COIL_BASIC,
			"Spectre Coils",
			spotlight(
				listOf(
					ModBlocks.SPECTRE_COIL_BASIC,
					ModBlocks.SPECTRE_COIL_REDSTONE,
					ModBlocks.SPECTRE_COIL_ENDER,
				),
				"Spectre Coils",
				doubleSpacedLines(
					"${major("Spectre Coils")} allow you to wirelessly extract energy from your ${internalLink("blocks/spectre_energy_injector", "Spectre Energy buffer")}.",
					"Each version can pull a different amount of FE/t from the pool. Place the Coil directly on the machine you want to power."
				),
				true
			),
			spotlight(
				listOf(
					ModBlocks.SPECTRE_COIL_NUMBER,
					ModBlocks.SPECTRE_COIL_GENESIS
				),
				" ",
				doubleSpacedLines(
					"There are two special Coils that ${minor("generate FE")} from nothing, instead of pulling from the Spectre Energy buffer.",
					"The first can be found in dungeon chests, while the second is only obtainable via commands or creative mode.}"
				),
				true
			)
		)

		add(
			ModBlocks.NOTIFICATION_INTERFACE,
			"Notification Interface",
			TextPage.basicTextPage(
				"Notification Interface",
				doubleSpacedLines(
					"The ${major("Notification Interface")}, when powered, will send a configurable notification to the player that placed it.",
					"You can set a title, body, and icon in its GUI."
				)
			),
			SpotlightPage.linkedPage(
				ModBlocks.NOTIFICATION_INTERFACE,
				doubleSpacedLines(
					"Server admins can also use the command \"/ii notify <player> <title> <body> <itemstack>\" to send notifications to players."
				)
			)
		)

		add(
			ModBlocks.ENDER_BRIDGE,
			"Ender Bridges",
			spotlight(
				listOf(
					ModBlocks.ENDER_BRIDGE,
					ModBlocks.PRISMARINE_ENDER_BRIDGE
				),
				"Ender Bridges",
				doubleSpacedLines(
					"${major("Ender Bridges")}, when powered, will teleport the entities standing on top of them to the ${major("Ender Anchor")} it's aimed at.",
					"It works through blocks over any distance, as long as it's ${minor("looking DIRECTLY at the Ender Anchor")}."
				),
				true
			),
			SpotlightPage.linkedPage(
				ModBlocks.ENDER_ANCHOR,
				doubleSpacedLines(
					"When powered, it searches in a straight line for an Ender Anchor. If there is one, it charges based on the distance and then activates. If there's not, it will audibly fail.",
					"The basic Bridge takes 1 tick per block traveled, while the Prismarine Bridge takes 0.5 ticks per block traveled.",
				)
			)
		)

		add(
			ModBlocks.PITCHER_PLANT,
			"Pitcher Plant",
			TextPage.basicTextPage(
				"Pitcher Plant",
				doubleSpacedLines(
					"${major("Pitcher Plants")} generate water.",
					"You can harvest this water by using a Bucket or any other fluid-storage item on it.",
					"It will also periodically fill adjacent fluid tanks, and can be extracted from via fluid pipes."
				)
			),
			SpotlightPage.linkedPage(
				ModBlocks.PITCHER_PLANT,
				doubleSpacedLines(
					"Pitcher Plants can be found in biomes with the biome tag ${minor("c:is_wet/overworld")}."
				)
			)
		)

		add(
			ModBlocks.SAKANADE_SPORES,
			"Sakanade Spores",
			TextPage.basicTextPage(
				"Sakanade Spores",
				doubleSpacedLines(
					"${major("Sakanade Spores")} when walked through, will apply the ${minor("Collapse")} potion effect to entities.",
					"For players, the Collapse effect inverts their movement and mouse controls.",
					"For mobs, it confuses their pathfinding, causing them to move erratically."
				)
			),
			SpotlightPage.linkedPage(
				ModBlocks.SAKANADE_SPORES,
				doubleSpacedLines(
					"Sakanade Spores can be found on the bottom of Giant Brown Mushrooms."
				)
			)
		)

		add(
			ModBlocks.BLOCK_BREAKER,
			"Block Breaker",
			SpotlightPage.linkedPage(
				ModBlocks.BLOCK_BREAKER,
				"Block Breaker",
				doubleSpacedLines(
					"The ${major("Block Breaker")} will break the block in front of it. It has the equivalent of an Iron Pickaxe.",
					"Blocks broken will be placed into an inventory behind it, or dropped on the ground.",
					"It can be disabled with a Redstone signal."
				)
			),
			SpotlightPage.linkedPage(
				ModItems.DIAMOND_BREAKER,
				"Diamond Breaker",
				doubleSpacedLines(
					"The ${major("Diamond Breaker")} can be used on the Block Breaker to upgrade it, making it equivalent to a Diamond Pickaxe.",
					"It can also be enchanted, and the enchantments will be applied when breaking blocks!"
				)
			)
		)

		add(
			ModBlocks.ENERGY_DISTRIBUTOR,
			"Energy Distributors",
			SpotlightPage.linkedPage(
				ModBlocks.ENERGY_DISTRIBUTOR,
				"Energy Distributor",
				doubleSpacedLines(
					"The ${major("Energy Distributor")} allows you to evenly distribute FE among all adjacent energy storage blocks in a line.",
					"Starting from the block in front of it, it will check each block in that direction for energy storage, and add them to its cache.",
				)
			),
			SpotlightPage.linkedPage(
				ModBlocks.ENDER_ENERGY_DISTRIBUTOR,
				"Ender Energy Distributor",
				doubleSpacedLines(
					"The Energy Distributor's energy storage is actually all of those blocks combined. Inserting into the Distributor will insert into the blocks, and the same for extracting.",
					"The ${major("Ender Energy Distributor")} works similarly, but uses 8 ${internalLink("items/location_filter", "Location Filters")} to specify the machines in the cache."
				)
			)
		)

		add(
			ModBlocks.NATURE_CORE,
			"Nature Core",
			TextPage.basicTextPage(
				"Nature Core",
				StringBuilder()
					.append(
						doubleSpacedLines(
							"The ${major("Nature Core")} structure can be found randomly around the world, with a ${minor("Nature Chest")} nearby full of goodies.",
							"The Nature Core itself also does a handful of things every so often."
						)
					)
					.toString()
			),
			SpotlightPage.linkedPage(
				ModBlocks.NATURE_CORE,
				StringBuilder()
					.append("It can:${BR}")
					.append(
						dottedLines(
							"Convert nearby Sand into Dirt or Grass",
							"Spawn a nearby animal",
							"Bone Meal nearby crops",
							"Plant saplings nearby",
							"Repair the structure around itself"
						)
					)
					.toString()
			)
		)

		add(
			ModBlocks.ENDER_MAILBOX,
			"Ender Mailbox",
			TextPage.basicTextPage(
				"Ender Mailbox",
				doubleSpacedLines(
					"The ${major("Ender Mailbox")} allows you to send and receive ${internalLink("items/ender_letter", "Ender Letters")} to and from other players.",
					"To send a Letter, simply use it on the Mailbox and it will be sent to the recipient."
				)
			),
			SpotlightPage.linkedPage(
				ModBlocks.ENDER_MAILBOX,
				doubleSpacedLines(
					"Using any Mailbox will show your incoming Letters, in the same way that Ender Chests show your items."
				)
			)
		)

		add(
			ModBlocks.BIOME_RADAR,
			"Biome Radar",
			SpotlightPage.linkedPage(
				ModBlocks.BIOME_RADAR,
				"Biome Radar",
				doubleSpacedLines(
					"The ${major("Biome Radar")} is a multiblock structure that ${minor("helps you find biomes")} in the world.",
					"First, you have to actually build the structure."
				)
			),
			MultiblockPage.builder()
				.name("Biome Radar Antenna")
				.multiblock(
					"Biome Radar Antenna",
					PatchouliMultiblock.builder()
						.setSymmetrical()
						.pattern(
							arrayOf(
								" I ",
								"I I",
								" I "
							),
							arrayOf(
								" E ",
								"SAN",
								" W "
							),
							arrayOf(
								"   ",
								" I ",
								"   "
							),
							arrayOf(
								"   ",
								" 0 ",
								"   "
							),
						)
						.map('I', Blocks.IRON_BARS)
						.map('N', Blocks.IRON_BARS, IronBarsBlock.NORTH, true)
						.map('S', Blocks.IRON_BARS, IronBarsBlock.SOUTH, true)
						.map('E', Blocks.IRON_BARS, IronBarsBlock.EAST, true)
						.map('W', Blocks.IRON_BARS, IronBarsBlock.WEST, true)
						.map(
							'A', Blocks.IRON_BARS, mapOf(
								IronBarsBlock.NORTH to true,
								IronBarsBlock.SOUTH to true,
								IronBarsBlock.EAST to true,
								IronBarsBlock.WEST to true
							)
						)
						.map('0', ModBlocks.BIOME_RADAR.get())
						.build()
				)
				.build(),
			SpotlightPage.builder()
				.setItemsString("irregular_implements:biome_crystal[irregular_implements:biome=\"minecraft:plains\"]")
				.text(
					doubleSpacedLines(
						"${major("Biome Crystals")} can be found in dungeon chests, and there's ${minor("one for every biome")}.",
						"Insert a Biome Crystal into the Biome Radar, and the flames on the radar will start to blow in the direction of the biome, if it's found."
					)
				)
				.build(),
			TextPage.basicTextPage(
				doubleSpacedLines(
					"You can also use a ${internalLink("items/location_filter", "Location Filter")} on the Biome Radar to save the biome's location to the Filter.",
					"With that, you can craft it with a ${internalLink("items/golden_compass", "Golden Compass")} to make your way to the biome!"
				)
			)
		)

		add(
			ModBlocks.GLOWING_MUSHROOM,
			"Glowing Mushroom",
			SpotlightPage.linkedPage(
				ModBlocks.GLOWING_MUSHROOM,
				"Glowing Mushroom",
				doubleSpacedLines(
					"${major("Glowing Mushrooms")} can rarely found in caves.",
					"They can be used in any brewing recipe that would accept Glowstone Dust."
				)
			)
		)

		add(
			ModBlocks.IRON_DROPPER,
			"Iron Dropper",
			TextPage.basicTextPage(
				"Iron Dropper",
				doubleSpacedLines(
					"The ${major("Iron Dropper")} is an upgrade to the regular Dropper, with a handful of extra features.",
					"Inside its GUI are 4 buttons to change some settings.",
					"Its redstone settings are:$BR" + dottedLines(
						"Eject when pulsed",
						"Eject while powered",
						"Eject always"
					)
				)
			),
			SpotlightPage.linkedPage(
				ModBlocks.IRON_DROPPER,
				doubleSpacedLines(
					"You can cycle the items' pickup delay through 20, 5, and 0 ticks.",
					"You can toggle if the items shoot straight forward or if they have slightly randomized velocity.",
					"Finally, you can toggle whether it should make particles and sounds when ejecting items or not."
				)
			)
		)

		add(
			ModBlocks.BLOCK_DESTABILIZER,
			"Block Destabilizer",
			TextPage.basicTextPage(
				"Block Destabilizer",
				doubleSpacedLines(
					"The ${major("Block Destabilizer")} will cause the block in front of it, and all connected blocks of the same type, to ${minor("fall like Sand")} when it receives a Redstone pulse.",
					"In its GUI, you can toggle various settings related to Laziness.",
				)
			),
			SpotlightPage.linkedPage(
				ModBlocks.BLOCK_DESTABILIZER,
				doubleSpacedLines(
					"When in Lazy mode, it will ${minor("remember what block positions to destabilize")}, even if they're different blocks.",
					"If the Lazy Shape is not set, it will be set the first time it activates in Lazy mode.",
				)
			),
			TextPage.basicTextPage(
				doubleSpacedLines(
					"Clicking the ${minor("Show Lazy Shape")} button will show cubes where all positions in the Lazy Shape are located.",
					"Note that this part ${bad("does not work with Sodium")} very well; you're supposed to see those positions through blocks but that doesn't work.",
					"Finally, the ${minor("Forget Lazy Shape")} button will clear the saved shape."
				)
			)
		)

		add(
			ModBlocks.ITEM_COLLECTOR,
			"Item Collectors",
			SpotlightPage.linkedPage(
				ModBlocks.ITEM_COLLECTOR,
				"Item Collector",
				doubleSpacedLines(
					"The ${major("Item Collector")} can be placed on an inventory, and will collect item entities in a 3 block radius around itself.",
					"That's a 7x7x7 cube centered on the Collector."
				)
			),
			SpotlightPage.linkedPage(
				ModBlocks.ADVANCED_ITEM_COLLECTOR,
				"Advanced Item Collector",
				doubleSpacedLines(
					"The ${major("Advanced Item Collector")} works the same, but has a configurable radius from 0 to 10 blocks.",
					"Each dimension (X/Y/Z) has its own radius, as well.",
					"You can optionally put an ${internalLink("items/item_filter", "Item Filter")} in it as well, and it will only collect items that match it."
				)
			)
		)

		add(
			ModBlocks.REDSTONE_OBSERVER,
			"Redstone Observer",
			SpotlightPage.linkedPage(
				ModBlocks.REDSTONE_OBSERVER,
				"Redstone Observer",
				doubleSpacedLines(
					"The ${major("Redstone Observer")} gives off the same Redstone signal strength as the block it's linked to.",
					"Use a ${internalLink("items/redstone_tool", "Redstone Tool")} to link it to a block."
				)
			)
		)

		add(
			ModBlocks.AUTO_PLACER,
			"Auto Placer",
			SpotlightPage.linkedPage(
				ModBlocks.AUTO_PLACER,
				"Auto Placer",
				doubleSpacedLines(
					"The ${major("Auto Placer")} will try to place the block in its inventory in front of itself.",
					"It tries to do this every tick that it does not have a Redstone signal."
				)
			)
		)

		add(
			ModBlocks.PLAYER_INTERFACE,
			"Player Interface",
			TextPage.basicTextPage(
				"Player Interface",
				doubleSpacedLines(
					"The ${major("Player Interface")} allows you to ${minor("interact with the owner's inventory")} with pipes etc.",
					"It is owned by whoever placed it.",
					"Each face of the block aims to a different part of the player's inventory."
				)
			),
			SpotlightPage.linkedPage(
				ModBlocks.PLAYER_INTERFACE,
				dottedLines(
					"Top - Armor slots",
					"Bottom - Hotbar slots",
					"North - Offhand slots",
					"Others - Main inventory slots"
				)
			)
		)

		add(
			ModBlocks.ENTITY_DETECTOR,
			"Entity Detector",
			TextPage.basicTextPage(
				"Entity Detector",
				doubleSpacedLines(
					"The ${major("Entity Detector")} emits a Redstone signal when an entity matching its filter is within its detection area.",
					"In its GUI you can set the radius in each dimension, if the Detector is inverted or not, and specify what type of entity to detect."
				)
			),
			SpotlightPage.linkedPage(
				ModBlocks.ENTITY_DETECTOR,
				doubleSpacedLines(
					"You can choose to detect" + dottedLines("All Entities", "Living Entities", "Animals", "Monsters", "Players", "Items", "Custom"),
					"When in Custom mode, you can put an ${internalLink("items/entity_filter", "Entity Filter")} in the Detector to specify exactly what to detect."
				)
			)
		)

		plates(consumer, book)
	}

	private fun plates(consumer: Consumer<PatchouliBookElement>, book: PatchouliBook) {
		val category = PatchouliBookCategory.builder()
			.book(book)
			.setDisplay(
				name = "Plates",
				description = "Plates generally serve some function in moving entities, especially item entities.",
				icon = ModBlocks.DIRECTIONAL_ACCELERATOR_PLATE
			)
			.parent("irregular_implements:blocks")
			.save(consumer, "blocks/plates")

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
			ModBlocks.BOUNCY_PLATE,
			"Bouncy Plate",
			SpotlightPage.linkedPage(
				ModBlocks.BOUNCY_PLATE,
				"Bouncy Plate",
				doubleSpacedLines(
					"The ${major("Bouncy Plate")} will ${minor("make entities that walk over it bounce up into the air.")}",
				)
			)
		)

		add(
			ModBlocks.ACCELERATOR_PLATE,
			"Accelerator Plate",
			SpotlightPage.linkedPage(
				ModBlocks.ACCELERATOR_PLATE,
				"Accelerator Plate",
				doubleSpacedLines(
					"The ${major("Accelerator Plate")} will ${minor("speed up anything that's moving on it")}, up to a limit.",
					"Whatever direction it's already moving, it'll move faster in that direction.",
				)
			)
		)

		add(
			ModBlocks.DIRECTIONAL_ACCELERATOR_PLATE,
			"Directional Accelerator Plate",
			SpotlightPage.linkedPage(
				ModBlocks.DIRECTIONAL_ACCELERATOR_PLATE,
				"Directional Accelerator Plate",
				doubleSpacedLines(
					"The ${major("Directional Accelerator Plate")} will ${minor("push entities standing on it in the direction the Plate is facing")}.",
					"Very useful for item and mob transportation!"
				)
			)
		)

		add(
			ModBlocks.REDIRECTOR_PLATE,
			"Redirector Plate",
			SpotlightPage.linkedPage(
				ModBlocks.REDIRECTOR_PLATE,
				"Redirector Plate",
				doubleSpacedLines(
					"${major("Redirector Plates")} have two active sides. Any entity that walks onto one active side will be teleported to the other active side.",
					"Right click to set the first active side, and sneak right-click to set the second active side."
				)
			)
		)

		add(
			ModBlocks.CORRECTOR_PLATE,
			"Corrector Plate",
			SpotlightPage.linkedPage(
				ModBlocks.CORRECTOR_PLATE,
				"Corrector Plate",
				doubleSpacedLines(
					"Entities moving on a ${major("Corrector Plate")} will snap to the center of the block.",
					"That is, they can only move along the path going straight between the middle of the X and Z axes of the block."
				)
			)
		)

		add(
			ModBlocks.ITEM_SEALER_PLATE,
			"Item Sealer Plate",
			SpotlightPage.linkedPage(
				ModBlocks.ITEM_SEALER_PLATE,
				"Item Sealer Plate",
				doubleSpacedLines(
					"Any item entity that touches the ${major("Item Sealer Plate")} will be prevented from being picked up for 30 seconds."
				)
			)
		)

		add(
			ModBlocks.ITEM_REJUVENATOR_PLATE,
			"Item Rejuvenator Plate",
			SpotlightPage.linkedPage(
				ModBlocks.ITEM_REJUVENATOR_PLATE,
				"Item Rejuvenator Plate",
				doubleSpacedLines(
					"Any item entity that touches the ${major("Item Rejuvenator Plate")} will have its despawn timer reset to 4 minutes."
				)
			)
		)

		add(
			ModBlocks.COLLECTION_PLATE,
			"Collection Plate",
			SpotlightPage.linkedPage(
				ModBlocks.COLLECTION_PLATE,
				"Collection Plate",
				doubleSpacedLines(
					"Any item entity that touches the ${major("Collection Plate")} will be inserted into an adjacent inventory, if possible.",
					"The order it tries is: Down, North, South, East, East, Up."
				)
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

	private fun stacksSpotlight(
		list: List<ItemStack>,
		title: String,
		text: String,
		linkRecipe: Boolean
	): SpotlightPage {
		val builder = SpotlightPage.builder()
			.text(text)
			.linkRecipe(linkRecipe)

		if (title.isNotEmpty()) {
			builder.title(title)
		}

		for (item in list) {
			builder.addItemStack(item)
		}

		return builder.build()
	}

	private fun spotlight(
		list: List<ItemLike>,
		title: String,
		text: String,
		linkRecipe: Boolean
	): SpotlightPage {
		val builder = SpotlightPage.builder()
			.text(text)
			.linkRecipe(linkRecipe)

		if (title.isNotEmpty()) {
			builder.title(title)
		}

		for (item in list) {
			builder.addItemLike(item)
		}

		return builder.build()
	}

	private fun dottedLines(vararg lines: String): String {
		val sb = StringBuilder()

		for (line in lines) {
			sb.append(LI).append(line)
		}

		return sb.toString()
	}

}