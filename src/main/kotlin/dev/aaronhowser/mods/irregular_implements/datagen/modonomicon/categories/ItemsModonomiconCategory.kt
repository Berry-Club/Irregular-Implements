package dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.categories

import dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.ModonomiconBookText.bookText
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel
import dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.ModonomiconBookText.internalLink
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.patchoulidatagen.modonomicon.book_element.ModonomiconBook
import dev.aaronhowser.mods.patchoulidatagen.modonomicon.book_element.ModonomiconBookCategory
import dev.aaronhowser.mods.patchoulidatagen.modonomicon.book_element.ModonomiconBookEntry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation

object ItemsModonomiconCategory {

	lateinit var bookCategory: ModonomiconBookCategory
	lateinit var lavaWaders: ModonomiconBookEntry
	lateinit var magicHood: ModonomiconBookEntry
	lateinit var obsidianWaterWalkingBoots: ModonomiconBookEntry
	lateinit var spectreHelmet: ModonomiconBookEntry
	lateinit var waterWalkingBoots: ModonomiconBookEntry
	lateinit var bean: ModonomiconBookEntry
	lateinit var biomePainter: ModonomiconBookEntry
	lateinit var blazeAndSteel: ModonomiconBookEntry
	lateinit var blockMover: ModonomiconBookEntry
	lateinit var blockReplacer: ModonomiconBookEntry
	lateinit var bottleOfAir: ModonomiconBookEntry
	lateinit var chunkAnalyzer: ModonomiconBookEntry
	lateinit var diviningRod: ModonomiconBookEntry
	lateinit var dropFilter: ModonomiconBookEntry
	lateinit var ectoplasm: ModonomiconBookEntry
	lateinit var emeraldCompass: ModonomiconBookEntry
	lateinit var enderBucket: ModonomiconBookEntry
	lateinit var enderLetter: ModonomiconBookEntry
	lateinit var escapeRope: ModonomiconBookEntry
	lateinit var evilTear: ModonomiconBookEntry
	lateinit var entityFilter: ModonomiconBookEntry
	lateinit var itemFilter: ModonomiconBookEntry
	lateinit var locationFilter: ModonomiconBookEntry
	lateinit var playerFilter: ModonomiconBookEntry
	lateinit var flooPouch: ModonomiconBookEntry
	lateinit var flooPowder: ModonomiconBookEntry
	lateinit var flooSign: ModonomiconBookEntry
	lateinit var flooToken: ModonomiconBookEntry
	lateinit var goldenCompass: ModonomiconBookEntry
	lateinit var goldenEgg: ModonomiconBookEntry
	lateinit var grassSeeds: ModonomiconBookEntry
	lateinit var imbueFire: ModonomiconBookEntry
	lateinit var lavaCharm: ModonomiconBookEntry
	lateinit var lotusBlossom: ModonomiconBookEntry
	lateinit var luminousPowder: ModonomiconBookEntry
	lateinit var magicBean: ModonomiconBookEntry
	lateinit var obsidianSkull: ModonomiconBookEntry
	lateinit var portableEnderBridge: ModonomiconBookEntry
	lateinit var portkey: ModonomiconBookEntry
	lateinit var redstoneActivator: ModonomiconBookEntry
	lateinit var redstoneRemote: ModonomiconBookEntry
	lateinit var redstoneTool: ModonomiconBookEntry
	lateinit var spectreAnchor: ModonomiconBookEntry
	lateinit var spectreCharger: ModonomiconBookEntry
	lateinit var spectreIlluminator: ModonomiconBookEntry
	lateinit var spectreKey: ModonomiconBookEntry
	lateinit var spectreSword: ModonomiconBookEntry
	lateinit var stableEnderPearl: ModonomiconBookEntry
	lateinit var summoningPendulum: ModonomiconBookEntry
	lateinit var superLubricantTincture: ModonomiconBookEntry
	lateinit var voidStone: ModonomiconBookEntry
	lateinit var weatherEgg: ModonomiconBookEntry
	lateinit var whiteStone: ModonomiconBookEntry

	fun generate(book: ModonomiconBook) {
		bookCategory = book.category(
			saveName = "items",
			name = "Items",
			description = "All of the mod's items",
			icon = ModItems.SPECTRE_KEY.get()
		) {
			sortNumber = 2
		}

		addEntries()
	}

	private fun addEntries() {
		lavaWaders = bookCategory.entry(
			saveName = "lava_waders",
			name = "Lava Waders",
			icon = BuiltInRegistries.ITEM.get(ResourceLocation.parse("irregular_implements:lava_waders"))
		) {
			textPage(
				text = bookText(
					"**Lava Waders** combine the effects of the ${internalLink(obsidianWaterWalkingBoots, "Obsidian Water Walking Boots")} and the ${internalLink(lavaCharm, "Lava Charm")}, as well as allowing you to walk on Lava.",
					"Get them by combining the two in an Anvil."
				)
			)
		}


		magicHood = bookCategory.entry(
			saveName = "magic_hood",
			name = "Magic Hood",
			icon = BuiltInRegistries.ITEM.get(ResourceLocation.parse("irregular_implements:magic_hood"))
		) {
			textPage(
				text = bookText(
					"The **Magic Hood**, when worn, will hide your nameplate and potion particles.",
					"It can be found in dungeon chests."
				)
			)
		}


		obsidianWaterWalkingBoots = bookCategory.entry(
			saveName = "obsidian_water_walking_boots",
			name = "Obsidian Water Walking Boots",
			icon = BuiltInRegistries.ITEM.get(ResourceLocation.parse("irregular_implements:obsidian_water_walking_boots"))
		) {
			textPage(
				text = bookText(
					"**Obsidian Water Walking Boots** combine the effects of the ${internalLink(waterWalkingBoots, "Water Walking Boots")} and the ${internalLink(obsidianSkull, "Obsidian Skull")}.",
					"Get them by combining the two in an Anvil."
				)
			)

			textPage(
				text = bookText(
					"Note that this does not let you walk on Lava! You'll have to use the ${internalLink(lavaWaders, "Lava Waders")} for that."
				)
			)
		}


		spectreHelmet = bookCategory.entry(
			saveName = "spectre_helmet",
			name = "Spectre Armor",
			icon = BuiltInRegistries.ITEM.get(ResourceLocation.parse("irregular_implements:spectre_helmet"))
		) {
			textPage(
				text = bookText(
					"**Spectre Armor** is equivalent to Diamond, with higher durability and enchantability.",
					"Wearing a full set also makes you slightly transparent!"
				)
			)

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(ResourceLocation.parse("irregular_implements:spectre_helmet"))
					.withRecipeId2(ResourceLocation.parse("irregular_implements:spectre_chestplate"))
			})

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(ResourceLocation.parse("irregular_implements:spectre_leggings"))
					.withRecipeId2(ResourceLocation.parse("irregular_implements:spectre_boots"))
			})
		}


		waterWalkingBoots = bookCategory.entry(
			saveName = "water_walking_boots",
			name = "Water Walking Boots",
			icon = BuiltInRegistries.ITEM.get(ResourceLocation.parse("irregular_implements:water_walking_boots"))
		) {
			textPage(
				text = bookText(
					"**Water Walking Boots** allow you to walk on water. Wow!",
					"They do not work while already underwater, and are disabled while sneaking."
				)
			)

			textPage(
				text = bookText(
					"Note that walking on water means that you can also LAND on water, causing fall damage!",
					"They can only be found in chests in Ocean Monuments."
				)
			)
		}


		bean = bookCategory.entry(
			saveName = "bean",
			name = "Bean",
			icon = BuiltInRegistries.ITEM.get(ResourceLocation.parse("irregular_implements:bean"))
		) {
			textPage(
				text = bookText(
					"**Beans** can be found growing around the overworld in any biome that is neither hot, cold, dry, nor sparse.",
					"They can be planted on any dirt-like blocks."
				)
			)
		}


		biomePainter = bookCategory.entry(
			saveName = "biome_painter",
			name = "Biome Painter",
			icon = ModItems.BIOME_PAINTER.get()
		) {
			textPage(
				text = bookText(
					"The **Biome Capsule** and **Biome Painter** are used together to allow you to change the biome of a location.",
					"Throw the Biome Capsule on the ground, and it will absorb points of the biome it's in."
				)
			)

			textPage(
				text = bookText(
					"Hold the Biome Painter in-hand to see what blocks nearby are not the same biome as the first non-empty Capsule in your inventory. Right-click the Painter on those blocks to change it, spending the biome points."
				)
			)

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(ResourceLocation.parse("irregular_implements:biome_painter"))
					.withRecipeId2(ResourceLocation.parse("irregular_implements:biome_capsule"))
			})
		}


		blazeAndSteel = bookCategory.entry(
			saveName = "blaze_and_steel",
			name = "Blaze and Steel",
			icon = ModItems.BLAZE_AND_STEEL.get()
		) {
			textPage(
				text = bookText(
					"The **Blaze and Steel** lights a much more aggressive fire, which burns and spreads much faster."
				)
			)
		}


		blockMover = bookCategory.entry(
			saveName = "block_mover",
			name = "Block Mover",
			icon = ModItems.BLOCK_MOVER.get()
		) {
			textPage(
				text = bookText(
					"The **Block Mover** can pick up and place down a single block, including its block entity.",
					"It cannot move blocks that have the block tag `#irregular_implements:block_mover_blacklist`."
				)
			)
		}


		blockReplacer = bookCategory.entry(
			saveName = "block_replacer",
			name = "Block Replacer",
			icon = ModItems.BLOCK_REPLACER.get()
		) {
			textPage(
				text = bookText(
					"The **Block Replacer** allows you to quickly replace blocks in the world with blocks stored in the item.",
					"To store a block in the Replacer, right-click the block stack in your inventory onto the slot the Block Replacer is in, the same way you would use a Bundle."
				)
			)

			textPage(
				text = bookText(
					"You can empty the Replacer by right-clicking the Replacer stack onto an empty inventory slot.",
					"Using the item on a block in the world will break that block and place a random stored block in its place."
				)
			)

			textPage(
				text = bookText(
					"Blocks with the block tag `#irregular_implements:block_replacer_blacklist` cannot be replaced with the Block Replacer."
				)
			)
		}


		bottleOfAir = bookCategory.entry(
			saveName = "bottle_of_air",
			name = "Bottle of Air",
			icon = ModItems.BOTTLE_OF_AIR.get()
		) {
			textPage(
				text = bookText(
					"The **Bottle of Air** can be \"drunk\" to refill your air supply when underwater.",
					"It can only be found in chests in Ocean Monuments."
				)
			)
		}


		chunkAnalyzer = bookCategory.entry(
			saveName = "chunk_analyzer",
			name = "Chunk Analyzer",
			icon = ModItems.CHUNK_ANALYZER.get()
		) {
			textPage(
				text = bookText(
					"The **Chunk Analyzer** can be used to see the blocks that make up the chunk you're currently standing in.",
					"The list of all blocks in the chunk, as well as their counts, will be displayed in your chat."
				)
			)
		}


		diviningRod = bookCategory.entry(
			saveName = "divining_rod",
			name = "Divining Rod",
			icon = ModItems.DIVINING_ROD.get()
		) {
			textPage(
				text = bookText(
					"There is a **Divining Rod** for each ore (that is, any block/item that has a tag beginning with `#c:ores/`). When held in-hand, nearby ores of that type will be visible through walls.",
					"The custom render type that this uses is currently not compatible with Sodium! It won't crash, but the ores won't be highlighted."
				)
			)

			textPage(
				text = bookText(
					"The same recipe can be used for any ore type, just substituting the actual ore itself. It follows this pattern:",
					"``` OSO SES S S ``` where `O` is the ore block, `S` is a Stick, and `E` is a Spider Eye."
				)
			)

			textPage(
				text = bookText(
					"You can also craft a universal Divining Rod that works for all ores!"
				)
			)
		}


		dropFilter = bookCategory.entry(
			saveName = "drop_filter",
			name = "Drop Filter",
			icon = ModItems.DROP_FILTER.get()
		) {
			textPage(
				text = bookText(
					"The **Drop Filter** can hold an ${internalLink(itemFilter, "Item Filter")}, and will prevent items matching the filter from entering your inventory.",
					"The **Voiding Drop Filter** does the same, but instead deletes the items matching the filter entirely."
				)
			)

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(ResourceLocation.parse("irregular_implements:drop_filter"))
					.withRecipeId2(ResourceLocation.parse("irregular_implements:voiding_drop_filter"))
			})
		}


		ectoplasm = bookCategory.entry(
			saveName = "ectoplasm",
			name = "Ectoplasm",
			icon = BuiltInRegistries.ITEM.get(ResourceLocation.parse("irregular_implements:ectoplasm"))
		) {
			textPage(
				text = bookText(
					"**Ectoplasm** is a material that drops when Spirits are killed.",
					"Spirits have a chance of spawning any time a mob dies. The chance increases the closer it is to the full moon, and increases further if the Ender Dragon has ever been killed on the server."
				)
			)

			textPage(
				text = bookText(
					"Spirits can only be damaged via magic. This includes Splash Potions, enchanted items, and certain other items like the Spectre Sword."
				)
			)
		}


		emeraldCompass = bookCategory.entry(
			saveName = "emerald_compass",
			name = "Emerald Compass",
			icon = ModItems.EMERALD_COMPASS.get()
		) {
			textPage(
				text = bookText(
					"The **Emerald Compass** aims at a bound player, assuming they're online and in the same dimension as you. There are two ways to bind the Compass to a player:",
					"1. Simply right-click the Compass on them. 2. Craft the Compass together with a [Player Filter](irregular_implements:player_filter)."
				)
			)
		}


		enderBucket = bookCategory.entry(
			saveName = "ender_bucket",
			name = "Ender Bucket",
			icon = ModItems.ENDER_BUCKET.get()
		) {
			textPage(
				text = bookText(
					"The **Ender Bucket** can be used on a flowing fluid, and it will gather the nearest fluid source from a distance.",
					"The **Reinforced Ender Bucket** works the same, but can also store up to 10 fluid sources at once."
				)
			)

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(ResourceLocation.parse("irregular_implements:ender_bucket"))
					.withRecipeId2(ResourceLocation.parse("irregular_implements:reinforced_ender_bucket"))
			})
		}


		enderLetter = bookCategory.entry(
			saveName = "ender_letter",
			name = "Ender Letter",
			icon = ModItems.ENDER_LETTER.get()
		) {
			textPage(
				text = bookText(
					"The **Ender Letter** allows you to send items to other players more easily. It can store up to 9 stacks inside itself.",
					"In its GUI, type the recipient's username. Then, simply use it on any ${internalLink(BlocksModonomiconCategory.enderMailbox, "Ender Mailbox")}. If the recipient's Mailbox is not full, it will be placed inside it."
				)
			)
		}


		escapeRope = bookCategory.entry(
			saveName = "escape_rope",
			name = "Escape Rope",
			icon = ModItems.ESCAPE_ROPE.get()
		) {
			textPage(
				text = bookText(
					"The **Escape Rope** can be used to quickly get out of caves.",
					"Hold right-click, and it will try to find a path to the nearest block that can see the sky. If a path is found, you will be teleported there instantly."
				)
			)

			textPage(
				text = bookText(
					"If no path is found, the Rope will be dropped from your hands."
				)
			)
		}


		evilTear = bookCategory.entry(
			saveName = "evil_tear",
			name = "Evil Tear",
			icon = ModItems.EVIL_TEAR.get()
		) {
			textPage(
				text = bookText(
					"The **Evil Tear** can be used to make an Artificial End Portal.",
					"Make the following structure (the End Rod is 3 blocks above the Obsidian, 4 above the End Stone), and then use the Tear on the End Rod."
				)
			)

			textPage(
				text = bookText(
					"An End Portal will grow in the ring, and will function exactly as expected. Breaking any of these blocks will break the Portal."
				)
			)
		}


		entityFilter = bookCategory.entry(
			saveName = "entity_filter",
			name = "Entity Filter",
			icon = ModItems.ENTITY_FILTER.get()
		) {
			textPage(
				text = bookText(
					"The **Entity Filter** allows you to filter based on entity type.",
					"To set the entity type, simply use the Filter on the desired entity. Sneak right-click to set it to Player."
				)
			)
		}


		itemFilter = bookCategory.entry(
			saveName = "item_filter",
			name = "Item Filter",
			icon = ModItems.ITEM_FILTER.get()
		) {
			textPage(
				text = bookText(
					"The **Item Filter** has a list of up to 9 items, and can serve as either a Whitelist or a Blacklist. Toggle this using the button on the far right of the GUI.",
					"Above each filled slot is two buttons."
				)
			)

			textPage(
				text = bookText(
					"The left button allows you to toggle if the slot is an Item Filter or a Tag Filter. For example, putting a Stick in the slot will by default make it filter for the `minecraft:stick` item. Changing it to a Tag Filter will make it filter one of Stick's item tags.",
					"The right button's function changes depending on if the slot is an Item Filter or a Tag Filter."
				)
			)

			textPage(
				text = bookText(
					"For Item Filter slots, it toggles whether or not the item must match the data components (like enchantments, custom names, etc) of the item in the slot.",
					"For Tag Filter slots, it cycles which of the item's tags to filter for."
				)
			)
		}


		locationFilter = bookCategory.entry(
			saveName = "location_filter",
			name = "Location Filter",
			icon = ModItems.LOCATION_FILTER.get()
		) {
			textPage(
				text = bookText(
					"The **Location Filter** allows you to set a location, which can then be used by other items and blocks to reference that location.",
					"Set the location by using the Filter on the block."
				)
			)
		}


		playerFilter = bookCategory.entry(
			saveName = "player_filter",
			name = "Player Filter",
			icon = ModItems.PLAYER_FILTER.get()
		) {
			textPage(
				text = bookText(
					"The **Player Filter** allows you to filter based on a specific player.",
					"To set it, simply use the Filter on the desired player. Sneak right-click to set it to yourself."
				)
			)
		}


		flooPouch = bookCategory.entry(
			saveName = "floo_pouch",
			name = "Floo Pouch",
			icon = ModItems.FLOO_POUCH.get()
		) {
			textPage(
				text = bookText(
					"The **Floo Pouch** can store up to 128 ${internalLink(flooPowder, "Floo Powder")}.",
					"Unlike Floo Powder, it works from anywhere in your inventory rather than just in your hand."
				)
			)

			textPage(
				text = bookText(
					"Sneak right-click the Pouch to fill it with all the Powder in your inventory."
				)
			)
		}


		flooPowder = bookCategory.entry(
			saveName = "floo_powder",
			name = "Floo Powder",
			icon = BuiltInRegistries.ITEM.get(ResourceLocation.parse("irregular_implements:floo_powder"))
		) {
			textPage(
				text = bookText(
					"**Floo Powder** is the fuel that allows you to use Floo teleportation. It must be held in-hand when the destination is said in chat."
				)
			)
		}


		flooSign = bookCategory.entry(
			saveName = "floo_sign",
			name = "Floo Sign",
			icon = ModItems.FLOO_SIGN.get()
		) {
			textPage(
				text = bookText(
					"**Floo Signs** are used to create Floo Fireplaces. Using the Sign on Bricks placed in the world will convert them all to Floo Bricks.",
					"If the Floo Sign has been renamed in an Anvil, the Fireplace will be named the same. If not, the Fireplace will have no name and cannot be teleported from, not to."
				)
			)
		}


		flooToken = bookCategory.entry(
			saveName = "floo_token",
			name = "Floo Token",
			icon = ModItems.FLOO_TOKEN.get()
		) {
			textPage(
				text = bookText(
					"**Floo Tokens** allow you to use Floo teleportation from anywhere in the world.",
					"Throw it on the ground and wait several seconds. It will spawn a Temporary Floo Fireplace, which will allow you to teleport to any named Floo Fireplace."
				)
			)

			textPage(
				text = bookText(
					"While this will not take any ${internalLink(flooPowder, "Floo Powder")}, it *will* use up the Token."
				)
			)
		}


		goldenCompass = bookCategory.entry(
			saveName = "golden_compass",
			name = "Golden Compass",
			icon = ModItems.GOLDEN_COMPASS.get()
		) {
			textPage(
				text = bookText(
					"The **Golden Compass** aims at a bound location, assuming it's in the same dimension as the user.",
					"Bind the location by crafting the Compass together with a set ${internalLink(locationFilter, "Location Filter")}."
				)
			)
		}


		goldenEgg = bookCategory.entry(
			saveName = "golden_egg",
			name = "Golden Egg",
			icon = ModItems.GOLDEN_EGG.get()
		) {
			textPage(
				text = bookText(
					"**Golden Eggs** spawn Golden Chickens when thrown. Instead of laying Eggs, Golden Chickens lay Gold Ingots.",
					"Golden Eggs can be found in Bean Pods."
				)
			)
		}


		grassSeeds = bookCategory.entry(
			saveName = "grass_seeds",
			name = "Grass Seeds",
			icon = BuiltInRegistries.ITEM.get(ResourceLocation.parse("irregular_implements:grass_seeds"))
		) {
			textPage(
				text = bookText(
					"**Grass Seeds** can be used on dirt blocks to turn them into Grass.",
					"**Colored Grass Seeds** do the same, but make Colored Grass."
				)
			)

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(ResourceLocation.parse("irregular_implements:grass_seeds"))
					.withRecipeId2(ResourceLocation.parse("irregular_implements:grass_seeds_red"))
			})
		}


		imbueFire = bookCategory.entry(
			saveName = "imbue_fire",
			name = "Imbues",
			icon = BuiltInRegistries.ITEM.get(ResourceLocation.parse("irregular_implements:imbue_fire"))
		) {
			textPage(
				text = bookText(
					"**Imbues** are special potions that are created in the ${internalLink(BlocksModonomiconCategory.imbuingStation, "Imbuing Station")}. They provide powerful long-lasting effects when consumed.",
					"However, you can only have a single Imbue at a time. Drinking a new Imbue will replace your current one."
				)
			)

			textPage(
				text = bookText(
					"**Available Imbues**",
					"- **Fire Imbue**: Melee attacks set targets on fire.\n- **Poison Imbue**: Melee attacks inflict Poison II.\n- **Wither Imbue**: Melee attacks inflict Wither II.\n- **Experience Imbue**: Double experience from slain mobs.\n- **Spectre Imbue**: 10% chance to completely negate incoming damage.\n- **Collapse Imbue**: Melee attacks inflict ${internalLink(BlocksModonomiconCategory.sakanadeSpores, "Collapse")}."
				)
			)
		}


		lavaCharm = bookCategory.entry(
			saveName = "lava_charm",
			name = "Lava Charm",
			icon = ModItems.LAVA_CHARM.get()
		) {
			textPage(
				text = bookText(
					"The **Lava Charm** can be worn as a Curio, and adds a temporary lava shield. It is visible above your armor bar.",
					"While the shield is full, you will be completely immune to lava damage. Note that it does not protect you from fire damage, only lava damage."
				)
			)

			textPage(
				text = bookText(
					"It may be wise to also invest in an ${internalLink(obsidianSkull, "Obsidian Skull")} to protect against fire damage.",
					"Lava Charms can be found rarely in dungeon chests, and more commonly in Nether Fortress chests."
				)
			)
		}


		lotusBlossom = bookCategory.entry(
			saveName = "lotus_blossom",
			name = "Lotus Blossom",
			icon = ModItems.LOTUS_BLOSSOM.get()
		) {
			textPage(
				text = bookText(
					"**Lotus Blossoms** can be eaten to give the player a handful of experience. Sneaking will consume the entire stack.",
					"Lotus Blossoms can be found in cold biomes, and can be farmed using Lotus Seeds planted on any dirt block."
				)
			)
		}


		luminousPowder = bookCategory.entry(
			saveName = "luminous_powder",
			name = "Luminous Powder",
			icon = BuiltInRegistries.ITEM.get(ResourceLocation.parse("irregular_implements:luminous_powder"))
		) {
			textPage(
				text = bookText(
					"**Luminous Powder** can be crafted with any item to make it glow in the dark.",
					"You can remove it by crafting the item with a Water Bucket."
				)
			)
		}


		magicBean = bookCategory.entry(
			saveName = "magic_bean",
			name = "Magic Beans",
			icon = BuiltInRegistries.ITEM.get(ResourceLocation.parse("irregular_implements:magic_bean"))
		) {
			textPage(
				text = bookText(
					"**Lesser Magic Bean**",
					"**Lesser Magic Beans** can be planted to make a Bean Stalk that grows upwards until it's blocked by something. It can be climbed like a ladder!",
					"**Magic Bean**",
					"The non-lesser **Magic Bean** is a much more powerful version. They grow much, much faster, and will break (almost) any block in their way."
				)
			)

			textPage(
				text = bookText(
					"Once it reaches the top of the world (or if it wouldn't be able to grow further), it will place a **Bean Pod** at its top. Break it open to collect some rare loot!",
					"The Magic Bean cannot be crafted. Instead, it can be found in dungeon loot and ${internalLink(BlocksModonomiconCategory.natureCore, "Nature Core")} chests."
				)
			)

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(ResourceLocation.parse("irregular_implements:lesser_magic_bean"))
			})
		}


		obsidianSkull = bookCategory.entry(
			saveName = "obsidian_skull",
			name = "Obsidian Skull",
			icon = BuiltInRegistries.ITEM.get(ResourceLocation.parse("irregular_implements:obsidian_skull"))
		) {
			textPage(
				text = bookText(
					"The **Obsidian Skull** has a chance of negating fire damage while in your inventory.",
					"The more damage that would have been taken, the higher the chance of negation. The exact formula is:"
				)
			)

			textPage(
				text = bookText(
					"``` (damage ^ 3) / 100 ```",
					"The **Obsidian Skull Ring** does the exact same, but can be worn in a Curio slot."
				)
			)

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(ResourceLocation.parse("irregular_implements:obsidian_skull_ring"))
			})
		}


		portableEnderBridge = bookCategory.entry(
			saveName = "portable_ender_bridge",
			name = "Portable Ender Bridge",
			icon = ModItems.PORTABLE_ENDER_BRIDGE.get()
		) {
			textPage(
				text = bookText(
					"The **Portable Ender Bridge** can be used to teleport to ${internalLink(BlocksModonomiconCategory.enderBridge, "Ender Anchors")}.",
					"While held in the hand, you'll be able to see nearby Ender Bridges through blocks. Simply right-click while looking at an Ender Anchor to teleport to it."
				)
			)

			textPage(
				text = bookText(
					"The custom render type that this uses is currently not compatible with Sodium! It won't crash, but the Ender Anchors won't be visible through walls."
				)
			)
		}


		portkey = bookCategory.entry(
			saveName = "portkey",
			name = "Portkey",
			icon = ModItems.PORTKEY.get()
		) {
			textPage(
				text = bookText(
					"The **Portkey** is used to teleport whoever picks it up.",
					"Right-click the Portkey on a block to set its destination. Then, throw it on the ground. After a short delay, it will activate."
				)
			)

			textPage(
				text = bookText(
					"Anyone who touches the activated Portkey will be teleported instantly to the destination, assuming it's in the same dimension.",
					"You can craft the Portkey with any other item to disguise it, making it look like the item it was crafted with."
				)
			)
		}


		redstoneActivator = bookCategory.entry(
			saveName = "redstone_activator",
			name = "Redstone Activator",
			icon = ModItems.REDSTONE_ACTIVATOR.get()
		) {
			textPage(
				text = bookText(
					"The **Redstone Activator** will provide a short redstone pulse when used on a block.",
					"Sneak right-click to cycle the duration between 2, 20, and 100 ticks."
				)
			)
		}


		redstoneRemote = bookCategory.entry(
			saveName = "redstone_remote",
			name = "Redstone Remote",
			icon = ModItems.REDSTONE_REMOTE.get()
		) {
			textPage(
				text = bookText(
					"The **Redstone Remote** acts like the ${internalLink(redstoneActivator, "Redstone Activator")}, but can be used from a distance.",
					"It can have up to 9 stored locations. Sneak right-click it to open its GUI, and insert set ${internalLink(locationFilter, "Location Filters")}. You can place an item in the slot above to give its button an icon."
				)
			)

			textPage(
				text = bookText(
					"Right-click while not sneaking to access the location buttons. Click the button to pulse its location for a short duration."
				)
			)
		}


		redstoneTool = bookCategory.entry(
			saveName = "redstone_tool",
			name = "Redstone Tool",
			icon = ModItems.REDSTONE_TOOL.get()
		) {
			textPage(
				text = bookText(
					"The **Redstone Tool** is used to link certain wireless redstone blocks to other locations.",
					"First, use it on the linkable block, so the Tool knows what's being linked. Then, use it on the destination block you want it to be linked to."
				)
			)

			textPage(
				text = bookText(
					"While holding the Redstone Tool, you'll be able to see all links nearby through walls.",
					"The custom render type that this uses is currently not compatible with Sodium! It won't crash, but the line won't be visible  through blocks."
				)
			)

			textPage(
				text = bookText(
					"The Redstone Tool can be used on the following blocks:\n- ${internalLink(BlocksModonomiconCategory.basicRedstoneInterface, "Basic Redstone Interface")}\n- ${internalLink(BlocksModonomiconCategory.redstoneObserver, "Redstone Observer")}"
				)
			)
		}


		spectreAnchor = bookCategory.entry(
			saveName = "spectre_anchor",
			name = "Spectre Anchor",
			icon = ModItems.SPECTRE_ANCHOR.get()
		) {
			textPage(
				text = bookText(
					"The **Spectre Anchor** can be crafted together with any item to allow you to retain it after death.",
					"After this, the Anchor is removed from the item."
				)
			)
		}


		spectreCharger = bookCategory.entry(
			saveName = "spectre_charger",
			name = "Spectre Chargers",
			icon = BuiltInRegistries.ITEM.get(ResourceLocation.parse("irregular_implements:spectre_charger_basic"))
		) {
			textPage(
				text = bookText(
					"**Spectre Chargers** are used to charge items in your inventory with FE from your ${internalLink(BlocksModonomiconCategory.spectreEnergyInjector, "Spectre Energy Buffer")}.",
					"While active, it will charge every item in your inventory, including armor and Curio slots."
				)
			)

			textPage(
				text = bookText(
					"Each tier charges faster than the last. The **Genesis Spectre Charger** is creative only, and does not use your Spectre Energy Buffer's energy."
				)
			)

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(ResourceLocation.parse("irregular_implements:spectre_charger_basic"))
					.withRecipeId2(ResourceLocation.parse("irregular_implements:spectre_charger_redstone"))
			})

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(ResourceLocation.parse("irregular_implements:spectre_charger_ender"))
			})
		}


		spectreIlluminator = bookCategory.entry(
			saveName = "spectre_illuminator",
			name = "Spectre Illuminator",
			icon = ModItems.SPECTRE_ILLUMINATOR.get()
		) {
			textPage(
				text = bookText(
					"The **Spectre Illuminator** can be used on a block to fully light up the chunk it's in.",
					"It will place an entity that slowly floats upwards, aiming to be just above the highest block in the chunk."
				)
			)

			textPage(
				text = bookText(
					"This can be undone by either right-clicking the Illuminator entity, or by using **Blackout Powder** in the chunk."
				)
			)

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(ResourceLocation.parse("irregular_implements:spectre_illuminator"))
					.withRecipeId2(ResourceLocation.parse("irregular_implements:blackout_powder"))
			})
		}


		spectreKey = bookCategory.entry(
			saveName = "spectre_key",
			name = "Spectre Key",
			icon = ModItems.SPECTRE_KEY.get()
		) {
			textPage(
				text = bookText(
					"The **Spectre Key** can be used to teleport to a private room in another dimension, unique to each player.",
					"Once there, you can build and store items safely, and they'll remain there. Leave by either using the Spectre Key again or by clicking one of the Spectre Core blocks on the floor."
				)
			)

			textPage(
				text = bookText(
					"The room is 16 blocks wide and deep, and defaults to being 2 blocks tall internally. You can expand it vertically by using ${internalLink(ectoplasm, "Ectoplasm")} on the Spectre Core blocks.",
					"Each Ectoplasm increases the height by one. It will use the entire stack at once."
				)
			)
		}


		spectreSword = bookCategory.entry(
			saveName = "spectre_sword",
			name = "Spectre Tools",
			icon = BuiltInRegistries.ITEM.get(ResourceLocation.parse("irregular_implements:spectre_sword"))
		) {
			textPage(
				text = bookText(
					"The **Spectre Sword** is comparable to a Diamond Sword, with higher durability and enchantability.",
					"It increases your entity interaction range by 3 blocks, and can be used to kill ${internalLink(ectoplasm, "Spirits")}."
				)
			)

			textPage(
				text = bookText(
					"The **Spectre Pickaxe**, **Spectre Axe**, and **Spectre Shovel** are comparable to their Diamond counterparts, with higher durability and enchantability.",
					"They increase your block interaction range by 3 blocks."
				)
			)

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(ResourceLocation.parse("irregular_implements:spectre_sword"))
					.withRecipeId2(ResourceLocation.parse("irregular_implements:spectre_pickaxe"))
			})

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(ResourceLocation.parse("irregular_implements:spectre_axe"))
					.withRecipeId2(ResourceLocation.parse("irregular_implements:spectre_shovel"))
			})
		}


		stableEnderPearl = bookCategory.entry(
			saveName = "stable_ender_pearl",
			name = "Stable Ender Pearl",
			icon = ModItems.STABLE_ENDER_PEARL.get()
		) {
			textPage(
				text = bookText(
					"The **Stable Ender Pearl** is used to teleport a bound player in a more controlled way.",
					"Right-click the Pearl to bind it to you. After seven seconds of sitting on the ground as an item entity, it will teleport the bound player to its location, and break."
				)
			)

			textPage(
				text = bookText(
					"If there is no bound player, it will instead teleport a random nearby entity within 10 blocks."
				)
			)
		}


		summoningPendulum = bookCategory.entry(
			saveName = "summoning_pendulum",
			name = "Summoning Pendulum",
			icon = ModItems.SUMMONING_PENDULUM.get()
		) {
			textPage(
				text = bookText(
					"The **Summoning Pendulum** can be used to store up to 5 (by default) entities, and place them back down later.",
					"Right-click the Pendulum on an entity to pick it up, right-click on a block to place it down."
				)
			)
		}


		superLubricantTincture = bookCategory.entry(
			saveName = "super_lubricant_tincture",
			name = "Super Lubricant Tincture",
			icon = BuiltInRegistries.ITEM.get(ResourceLocation.parse("irregular_implements:super_lubricant_tincture"))
		) {
			textPage(
				text = bookText(
					"The **Super Lubricant Tincture** can be crafted with any boot item to make them completely negate friction when worn, while below a certain speed.",
					"You can craft lubricated boots with a Water Bottle to wash them of the lubricant."
				)
			)
		}


		voidStone = bookCategory.entry(
			saveName = "void_stone",
			name = "Void Stone",
			icon = ModItems.VOID_STONE.get()
		) {
			textPage(
				text = bookText(
					"The **Void Stone** will delete any items inserted into its GUI."
				)
			)
		}


		weatherEgg = bookCategory.entry(
			saveName = "weather_egg",
			name = "Weather Eggs",
			icon = ModItems.WEATHER_EGG.get()
		) {
			textPage(
				text = bookText(
					"**Weather Eggs** change the weather when thrown.",
					"There are 3 types: Sunny, Rainy, and Stormy."
				)
			)

			textPage(
				text = bookText(
					"Naturally, throwing a Sunny Egg will clear the weather, a Rainy Egg will cause rain, and a Stormy Egg will cause a thunderstorm.",
					"Throwing an Egg that matches the current weather will do nothing."
				)
			)
		}


		whiteStone = bookCategory.entry(
			saveName = "white_stone",
			name = "White Stone",
			icon = ModItems.WHITE_STONE.get()
		) {
			textPage(
				text = bookText(
					"The **White Stone** has the power to prevent your death once while charged. Doing so fully discharges the item.",
					"Charge the item by exposing it to the light of the full moon, either by dropping it on the ground or holding it while standing outside. The moon must also be at its peak."
				)
			)

			textPage(
				text = bookText(
					"It can be rarely found in dungeon chests."
				)
			)
		}

	}
}