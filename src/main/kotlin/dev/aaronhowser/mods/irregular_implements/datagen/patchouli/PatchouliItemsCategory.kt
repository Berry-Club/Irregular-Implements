package dev.aaronhowser.mods.irregular_implements.datagen.patchouli

import dev.aaronhowser.mods.irregular_implements.item.DiviningRodItem
import dev.aaronhowser.mods.irregular_implements.item.WeatherEggItem
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
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider.Companion.RESET
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider.Companion.TextColor
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider.Companion.UNDERLINE
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider.Companion.colored
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider.Companion.doubleSpacedLines
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider.Companion.internalLink
import net.minecraft.core.Direction
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EndRodBlock
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.registries.DeferredItem
import java.util.function.Consumer

object PatchouliItemsCategory {

	fun addItems(consumer: Consumer<PatchouliBookElement>, book: PatchouliBook) {
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
			ModPatchouliBookProvider.spotlight(
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
			ModPatchouliBookProvider.spotlight(
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
			ModPatchouliBookProvider.stacksSpotlight(
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
			ModPatchouliBookProvider.stacksSpotlight(
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
			ModPatchouliBookProvider.spotlight(
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
			ModPatchouliBookProvider.spotlight(
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
			ModPatchouliBookProvider.stacksSpotlight(
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
	}

	private fun major(text: String) = ModPatchouliBookProvider.major(text)
	private fun minor(text: String) = ModPatchouliBookProvider.minor(text)
	private fun good(text: String) = ModPatchouliBookProvider.good(text)
	private fun bad(text: String) = ModPatchouliBookProvider.bad(text)
	
}