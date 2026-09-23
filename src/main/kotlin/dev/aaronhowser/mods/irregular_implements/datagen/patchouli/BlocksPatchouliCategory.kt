package dev.aaronhowser.mods.irregular_implements.datagen.patchouli

import dev.aaronhowser.mods.irregular_implements.datagen.patchouli.PatchouliBookText.bookText
import dev.aaronhowser.mods.irregular_implements.datagen.patchouli.PatchouliBookText.internalLink
import dev.aaronhowser.mods.irregular_implements.datagen.patchouli.PatchouliBookText.major
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.patchoulidatagen.patchouli.book_element.PatchouliBook
import dev.aaronhowser.mods.patchoulidatagen.patchouli.book_element.PatchouliBookCategory
import dev.aaronhowser.mods.patchoulidatagen.patchouli.book_element.PatchouliBookEntry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation

object BlocksPatchouliCategory {

	lateinit var bookCategory: PatchouliBookCategory
	lateinit var platesCategory: PatchouliBookCategory
	lateinit var redstoneCategory: PatchouliBookCategory
	lateinit var advancedItemCollector: PatchouliBookEntry
	lateinit var autoPlacer: PatchouliBookEntry
	lateinit var biomeBlocks: PatchouliBookEntry
	lateinit var biomeRadar: PatchouliBookEntry
	lateinit var blockBreaker: PatchouliBookEntry
	lateinit var blockOfSticks: PatchouliBookEntry
	lateinit var blockTeleporter: PatchouliBookEntry
	lateinit var compressedSlimeBlock: PatchouliBookEntry
	lateinit var customCraftingTable: PatchouliBookEntry
	lateinit var diaphanousBlock: PatchouliBookEntry
	lateinit var enderBridge: PatchouliBookEntry
	lateinit var enderMailbox: PatchouliBookEntry
	lateinit var energyDistributor: PatchouliBookEntry
	lateinit var fertilizedDirt: PatchouliBookEntry
	lateinit var glowingMushroom: PatchouliBookEntry
	lateinit var igniter: PatchouliBookEntry
	lateinit var imbuingStation: PatchouliBookEntry
	lateinit var inventoryRerouter: PatchouliBookEntry
	lateinit var itemCollector: PatchouliBookEntry
	lateinit var lapisGlass: PatchouliBookEntry
	lateinit var luminousBlockWhite: PatchouliBookEntry
	lateinit var natureCore: PatchouliBookEntry
	lateinit var notificationInterface: PatchouliBookEntry
	lateinit var peaceCandle: PatchouliBookEntry
	lateinit var pitcherPlant: PatchouliBookEntry
	lateinit var acceleratorPlate: PatchouliBookEntry
	lateinit var bouncyPlate: PatchouliBookEntry
	lateinit var collectionPlate: PatchouliBookEntry
	lateinit var correctorPlate: PatchouliBookEntry
	lateinit var directionalAcceleratorPlate: PatchouliBookEntry
	lateinit var extractionPlate: PatchouliBookEntry
	lateinit var filteredRedirectorPlate: PatchouliBookEntry
	lateinit var itemRejuvenatorPlate: PatchouliBookEntry
	lateinit var itemSealerPlate: PatchouliBookEntry
	lateinit var processingPlate: PatchouliBookEntry
	lateinit var redirectorPlate: PatchouliBookEntry
	lateinit var redstonePlate: PatchouliBookEntry
	lateinit var platforms: PatchouliBookEntry
	lateinit var playerInterface: PatchouliBookEntry
	lateinit var quartzGlass: PatchouliBookEntry
	lateinit var rainShield: PatchouliBookEntry
	lateinit var rainbowLamp: PatchouliBookEntry
	lateinit var advancedRedstoneInterface: PatchouliBookEntry
	lateinit var advancedRedstoneTorch: PatchouliBookEntry
	lateinit var analogEmitter: PatchouliBookEntry
	lateinit var basicRedstoneInterface: PatchouliBookEntry
	lateinit var blockDestabilizer: PatchouliBookEntry
	lateinit var blockDetector: PatchouliBookEntry
	lateinit var chatDetector: PatchouliBookEntry
	lateinit var contactButton: PatchouliBookEntry
	lateinit var contactLever: PatchouliBookEntry
	lateinit var entityDetector: PatchouliBookEntry
	lateinit var inventoryTester: PatchouliBookEntry
	lateinit var ironDropper: PatchouliBookEntry
	lateinit var lapisLamp: PatchouliBookEntry
	lateinit var moonPhaseDetector: PatchouliBookEntry
	lateinit var onlineDetector: PatchouliBookEntry
	lateinit var quartzLamp: PatchouliBookEntry
	lateinit var redstoneObserver: PatchouliBookEntry
	lateinit var sidedRedstone: PatchouliBookEntry
	lateinit var triggerGlass: PatchouliBookEntry
	lateinit var sakanadeSpores: PatchouliBookEntry
	lateinit var shockAbsorber: PatchouliBookEntry
	lateinit var slimeCube: PatchouliBookEntry
	lateinit var spectreCoils: PatchouliBookEntry
	lateinit var spectreEnergyInjector: PatchouliBookEntry
	lateinit var spectreLens: PatchouliBookEntry
	lateinit var spectreSapling: PatchouliBookEntry
	lateinit var superLubricatedBlocks: PatchouliBookEntry

	fun generate(book: PatchouliBook) {
		bookCategory = book.category(
			saveName = "blocks",
			name = "Blocks",
			description = "All of the mod's other blocks",
			icon = ModBlocks.ENDER_ANCHOR.get()
		) {
			sortNumber = 1
		}

		platesCategory = book.category(
			saveName = "plates",
			name = "Plates",
			description = "Plates that interact with entities and inventories",
			icon = ModBlocks.ACCELERATOR_PLATE.get()
		) {
			parent = bookCategory
			sortNumber = 1
		}

		redstoneCategory = book.category(
			saveName = "redstone",
			name = "Redstone",
			description = "Blocks that interact with redstone signals",
			icon = ModBlocks.BASIC_REDSTONE_INTERFACE.get()
		) {
			parent = bookCategory
			sortNumber = 2
		}

		addEntries(book)
	}

	private fun addEntries(book: PatchouliBook) {
		advancedItemCollector = book.entry(
			category = bookCategory,
			saveName = "advanced_item_collector",
			name = "Advanced Item Collector",
			icon = ModBlocks.ADVANCED_ITEM_COLLECTOR.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Advanced Item Collector")} is an upgraded version of the ${internalLink(itemCollector, "Item Collector")}. Its collection volume is configurable, ranging from 0 to 10 blocks in each direction.",
					"Each axis (X/Y/Z) can be configured independently, as well."
				)
			)

			textPage(
				text = bookText(
					"Additionally, you can insert an ${internalLink(ItemsPatchouliCategory.itemFilter, "Item Filter")}, and it will only collect items that match the filter."
				)
			)
		}


		autoPlacer = book.entry(
			category = bookCategory,
			saveName = "auto_placer",
			name = "Auto Placer",
			icon = ModBlocks.AUTO_PLACER.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Auto Placer")} will try to place the block in its inventory in front of itself.",
					"It tries to do this every tick, unless it has a redstone signal."
				)
			)
		}


		biomeBlocks = book.entry(
			category = bookCategory,
			saveName = "biome_blocks",
			name = "Biome Blocks",
			icon = BuiltInRegistries.ITEM.get(ResourceLocation.parse("irregular_implements:biome_stone"))
		) {
			textPage(
				text = bookText(
					"${major("Biome blocks")} change color to match the biome they're in."
				)
			)

			craftingPage(ResourceLocation.parse("irregular_implements:biome_stone"))

			craftingPage(ResourceLocation.parse("irregular_implements:biome_cobblestone"))

			craftingPage(ResourceLocation.parse("irregular_implements:biome_bricks"))

			craftingPage(ResourceLocation.parse("irregular_implements:biome_bricks_cracked"))

			craftingPage(ResourceLocation.parse("irregular_implements:biome_bricks_chiseled"))

			craftingPage(ResourceLocation.parse("irregular_implements:biome_glass"))
		}


		biomeRadar = book.entry(
			category = bookCategory,
			saveName = "biome_radar",
			name = "Biome Radar",
			icon = ModBlocks.BIOME_RADAR.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Biome Radar")} is a multiblock structure that helps you find biomes in the world.",
					"First, you have to actually build the structure:"
				)
			)

			textPage(
				text = bookText(
					"Then, insert a ${major("Biome Crystal")}. There's one for each Biome in the game, and they can be found in basically any chest with loot.",
					"If the biome exists in the world, the fires on top of the Iron Bars will start blowing in its direction. If that's not enough, you can use a ${internalLink(ItemsPatchouliCategory.locationFilter, "Location Filter")} on it to set it to the biome's location."
				)
			)

			textPage(
				text = bookText(
					"With that, you can craft it with a ${internalLink(ItemsPatchouliCategory.goldenCompass, "Golden Compass")} to make your way there!"
				)
			)
		}


		blockBreaker = book.entry(
			category = bookCategory,
			saveName = "block_breaker",
			name = "Block Breaker",
			icon = ModBlocks.BLOCK_BREAKER.get()
		) {
			textPage(
				text = bookText(
					"${major("Block Breaker")}",
					"The ${major("Block Breaker")} will break the block in front of it. It has the equivalent of an unbreakable Iron Pickaxe.",
					"Blocks broken will be placed into an inventory behind it, if one exists. Otherwise, it will drop to the floor behind it."
				)
			)

			textPage(
				text = bookText(
					"It can be disabled with a redstone signal.",
					"${major("Diamond Breaker")}",
					"The ${major("Diamond Breaker")} can be applied to the Block Breaker to upgrade it, giving it the equivalent of a Diamond Pickaxe."
				)
			)

			textPage(
				text = bookText(
					"It can also be enchanted, and any enchantments will actually be applied when breaking blocks!"
				)
			)

			craftingPage(ResourceLocation.parse("irregular_implements:block_breaker"))

			craftingPage(ResourceLocation.parse("irregular_implements:diamond_breaker"))
		}


		blockOfSticks = book.entry(
			category = bookCategory,
			saveName = "block_of_sticks",
			name = "Block of Sticks",
			icon = ModBlocks.BLOCK_OF_STICKS.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Block of Sticks")} breaks itself shortly after being placed.",
					"The ${major("Returning Block of Sticks")} will teleport itself to the nearest player when broken."
				)
			)

			craftingPage(ResourceLocation.parse("irregular_implements:block_of_sticks"))

			craftingPage(ResourceLocation.parse("irregular_implements:returning_block_of_sticks"))
		}


		blockTeleporter = book.entry(
			category = bookCategory,
			saveName = "block_teleporter",
			name = "Block Teleporter",
			icon = ModBlocks.BLOCK_TELEPORTER.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Block Teleporter")} allows you to teleport the block in front of itself to another Block Teleporter when powered.",
					"Use a Location Filter to save the location of one Block Teleporter, and insert it into the inventory of a second Block Teleporter."
				)
			)

			textPage(
				text = bookText(
					"When that second Block Teleporter is powered, it will attempt to swap the block in front of itself with the block in front of the linked Block Teleporter.",
					"Whether or not it works between different dimensions can be configured, and defaults to true."
				)
			)
		}


		compressedSlimeBlock = book.entry(
			category = bookCategory,
			saveName = "compressed_slime_block",
			name = "Compressed Slime Block",
			icon = ModBlocks.COMPRESSED_SLIME_BLOCK.get()
		) {
			textPage(
				text = bookText(
					"Right-clicking a Slime Block with a Shovel will turn it into a ${major("Compressed Slime Block")}. Clicking more will compress it further, or reset it.",
					"Touching a Compressed Slime Block will launch you into the air. The more it's compressed, the higher you bounce."
				)
			)
		}


		customCraftingTable = book.entry(
			category = bookCategory,
			saveName = "custom_crafting_table",
			name = "Custom Crafting Table",
			icon = ModBlocks.CUSTOM_CRAFTING_TABLE.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Custom Crafting Table")} is functionally identical to a regular Crafting Table, but looks like the block it was crafted with.",
					"For example, 8 Oak Logs around a Crafting Table will make a Crafting Table that looks like an Oak Log."
				)
			)
		}


		diaphanousBlock = book.entry(
			category = bookCategory,
			saveName = "diaphanous_block",
			name = "Diaphanous Block",
			icon = ModBlocks.DIAPHANOUS_BLOCK.get()
		) {
			textPage(
				text = bookText(
					"${major("Diaphanous Blocks")} look like regular blocks at a distance, but vanishes as you approach, and can be walked through.",
					"Craft a Diaphanous Block with any other block to set what it looks like."
				)
			)

			textPage(
				text = bookText(
					"Crafting a Diaphanous Block by itself will invert it, making it invisible at a distance and solid up close."
				)
			)
		}


		enderBridge = book.entry(
			category = bookCategory,
			saveName = "ender_bridge",
			name = "Ender Bridge",
			icon = ModBlocks.ENDER_BRIDGE.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Ender Bridge")} and ${major("Prismarine Ender Bridge")}, when powered by redstone, will teleport the entities above it towards the ${major("Ender Anchor")} it's aiming at.",
					"It works through blocks, and across any distance (including unloaded chunks), as long as the Anchor is loaded. It has to be aiming DIRECTLY at the Anchor, as well."
				)
			)

			textPage(
				text = bookText(
					"When powered, it will look at every loaded Anchor and find the nearest one that it's aiming at. If there isn't one, it will audibly fail.",
					"If there *is* an Anchor. it will charge up before activating. Its charge time depends on the distance, as well the tier of Bridge."
				)
			)

			textPage(
				text = bookText(
					"The regular Bridge takes 1 tick per block to charge, and the Prismarine Ender Bridge is twice as fast."
				)
			)

			craftingPage(ResourceLocation.parse("irregular_implements:ender_bridge"))

			craftingPage(ResourceLocation.parse("irregular_implements:prismarine_ender_bridge"))

			craftingPage(ResourceLocation.parse("irregular_implements:ender_anchor"))
		}


		enderMailbox = book.entry(
			category = bookCategory,
			saveName = "ender_mailbox",
			name = "Ender Mailbox",
			icon = ModBlocks.ENDER_MAILBOX.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Ender Mailbox")} allows you to send and receive ${internalLink(ItemsPatchouliCategory.enderLetter, "Ender Letters")} to and from other players.",
					"To send a Letter, simply use it on any Mailbox, and it will be sent to the recipient if theirs is not full."
				)
			)

			textPage(
				text = bookText(
					"Using any Ender Mailbox will show your own incoming Letters, similar to an Ender Chest."
				)
			)
		}


		energyDistributor = book.entry(
			category = bookCategory,
			saveName = "energy_distributor",
			name = "Energy Distributor",
			icon = ModBlocks.ENERGY_DISTRIBUTOR.get()
		) {
			textPage(
				text = bookText(
					"${major("Energy Distributor")}",
					"The ${major("Energy Distributor")} allows you to evenly distribute FE along a line of adjacent machines it's aimed at.",
					"Starting from the block in front of it, it will step along a straight path until it reaches a block that cannot store FE. All blocks it finds will be added to its cache."
				)
			)

			textPage(
				text = bookText(
					"The Energy Distributor will simulate having an energy storage, which is actually made up of all machines in its cache. Any FE inserted into it is actually inserted into the cached blocks; the same for any FE removed.",
					"${major("Ender Energy Distributor")}",
					"The ${major("Ender Energy Distributor")} works similarly, but uses 8 ${internalLink(ItemsPatchouliCategory.locationFilter, "Location Filters")} to specify the machines in its cache."
				)
			)

			craftingPage(ResourceLocation.parse("irregular_implements:energy_distributor"))

			craftingPage(ResourceLocation.parse("irregular_implements:ender_energy_distributor"))
		}


		fertilizedDirt = book.entry(
			category = bookCategory,
			saveName = "fertilized_dirt",
			name = "Fertilized Dirt",
			icon = ModBlocks.FERTILIZED_DIRT.get()
		) {
			textPage(
				text = bookText(
					"${major("Fertilized Dirt")} does not need to be hydrated, cannot be trampled, and grows crops 3 times faster.",
					"You still have to till it with a Hoe before seeds can be planted, however."
				)
			)
		}


		glowingMushroom = book.entry(
			category = bookCategory,
			saveName = "glowing_mushroom",
			name = "Glowing Mushroom",
			icon = ModBlocks.GLOWING_MUSHROOM.get()
		) {
			textPage(
				text = bookText(
					"${major("Glowing Mushrooms")} can be found growing in caves.",
					"Any brewing recipe that would accept Glowstone Dust can also accept Glowing Mushrooms."
				)
			)
		}


		igniter = book.entry(
			category = bookCategory,
			saveName = "igniter",
			name = "Igniter",
			icon = ModBlocks.IGNITER.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Igniter")} can be used to light a fire in front of itself, when given a redstone signal.",
					"It has 3 modes, set in its GUI:\n- Toggle: Lights when powered, extinguishes when unpowered\n- Keep Ignited: Keeps the fire lit while powered, does nothing when unpowered\n- Ignite: Lights when powered, does nothing when unpowered"
				)
			)
		}


		imbuingStation = book.entry(
			category = bookCategory,
			saveName = "imbuing_station",
			name = "Imbuing Station",
			icon = ModBlocks.IMBUING_STATION.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Imbuing Station")} is used to create Imbues.",
					"Ingredients that go in the outer slots can go in any order, but the center ingredient must always go in the center slot."
				)
			)

			textPage(
				text = bookText(
					"The center slot can be accessed from the top face, the outer slots can be accessed from the sides, and the output slot can be accessed from the bottom."
				)
			)
		}


		inventoryRerouter = book.entry(
			category = bookCategory,
			saveName = "inventory_rerouter",
			name = "Inventory Rerouter",
			icon = ModBlocks.INVENTORY_REROUTER.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Inventory Rerouter")} exposes the sides of an adjacent inventory from a more convenient location.",
					"Place it so that its green face points toward the target inventory. Each of the other five faces can independently access any side of that inventory."
				)
			)

			textPage(
				text = bookText(
					"Right-click one of the exposed faces to cycle which side of the inventory it accesses. The colored overlay shows the selected side.",
					"This is useful when a machine needs to interact with a specific face of an inventory that would otherwise be inaccessible."
				)
			)

			textPage(
				text = bookText(
					"For example, place it so the block is facing the back of a Furnace. Cycle the side of the Rerouter until it displays a U, for \"Up\". Any items inserted into that face will actually be inserted into the top face of the Furnace."
				)
			)
		}


		itemCollector = book.entry(
			category = bookCategory,
			saveName = "item_collector",
			name = "Item Collector",
			icon = ModBlocks.ITEM_COLLECTOR.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Item Collector")} can be placed on any inventory, and it will collect item entities in a 3 block radius around itself.",
					"That's a 7x7x7 cube centered on the Collector."
				)
			)
		}


		lapisGlass = book.entry(
			category = bookCategory,
			saveName = "lapis_glass",
			name = "Lapis Glass",
			icon = ModBlocks.LAPIS_GLASS.get()
		) {
			textPage(
				text = bookText(
					"${major("Lapis Glass")} is solid for players, but allows everything else to pass through."
				)
			)
		}


		luminousBlockWhite = book.entry(
			category = bookCategory,
			saveName = "luminous_block_white",
			name = "Luminous Blocks",
			icon = BuiltInRegistries.ITEM.get(ResourceLocation.parse("irregular_implements:luminous_block_white"))
		) {
			textPage(
				text = bookText(
					"${major("Luminous Blocks")} are always lit."
				)
			)

			craftingPage(ResourceLocation.parse("irregular_implements:luminous_block_white"))

			craftingPage(ResourceLocation.parse("irregular_implements:translucent_luminous_block_white"))

			craftingPage(ResourceLocation.parse("irregular_implements:stained_bricks_white"))
		}


		natureCore = book.entry(
			category = bookCategory,
			saveName = "nature_core",
			name = "Nature Core",
			icon = ModBlocks.NATURE_CORE.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Nature Core")} structure can be found randomly around the overworld, with a Nature Chest nearby full of goodies.",
					"The Nature Core can also do these things randomly:\n- Convert nearby Sand into Dirt/Grass\n- Spawn an animal nearby\n- Bone Meal nearby crops\n- Plant Saplings nearby\n- Repair the structure around it"
				)
			)
		}


		notificationInterface = book.entry(
			category = bookCategory,
			saveName = "notification_interface",
			name = "Notification Interface",
			icon = ModBlocks.NOTIFICATION_INTERFACE.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Notification Interface")} will send its owner a customizable notification when it receives a redstone pulse.",
					"You can set the notifications title, body, and icon in its GUI."
				)
			)

			textPage(
				text = bookText(
					"Server admins can also use the command /ii notify     to send notifications to players."
				)
			)
		}


		peaceCandle = book.entry(
			category = bookCategory,
			saveName = "peace_candle",
			name = "Peace Candle",
			icon = ModBlocks.PEACE_CANDLE.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Peace Candle")} prevents hostile mobs from spawning within a radius around it. By default, this radius is a single chunk. That is, it effects a 3x3 chunk area centered on itself.",
					"It can be disabled with a redstone signal."
				)
			)

			textPage(
				text = bookText(
					"Peace Candles can be found in certain Village temples. Where there would be a Brewing Stand, you can sometimes instead find a Peace Candle."
				)
			)
		}


		pitcherPlant = book.entry(
			category = bookCategory,
			saveName = "pitcher_plant",
			name = "Pitcher Plant",
			icon = ModBlocks.PITCHER_PLANT.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Pitcher Plant")} generates an infinite amount of water. It can be found in wet biomes in the overworld.",
					"Clicking a bucket or other fluid receptacle will fill it with Water."
				)
			)

			textPage(
				text = bookText(
					"Placing it adjacent to a tank will fill the tank with water, at a configurable speed. You can also pipe water from it, also at a configurable speed."
				)
			)
		}


		acceleratorPlate = book.entry(
			category = platesCategory,
			saveName = "accelerator_plate",
			name = "Accelerator Plate",
			icon = ModBlocks.ACCELERATOR_PLATE.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Accelerator Plate")} will speed up any entity moving on it, up to a limit.",
					"Whatever direction it's already moving, it'll move that direction faster."
				)
			)
		}


		bouncyPlate = book.entry(
			category = platesCategory,
			saveName = "bouncy_plate",
			name = "Bouncy Plate",
			icon = ModBlocks.BOUNCY_PLATE.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Bouncy Plate")} will launch entities that touch it into the air."
				)
			)
		}


		collectionPlate = book.entry(
			category = platesCategory,
			saveName = "collection_plate",
			name = "Collection Plate",
			icon = ModBlocks.COLLECTION_PLATE.get()
		) {
			textPage(
				text = bookText(
					"Any item entity that touches an ${major("Collection Plate")} will be inserted into an adjacent inventory, if possible.",
					"The order it tries is: Down, North, South, East, East, Up."
				)
			)
		}


		correctorPlate = book.entry(
			category = platesCategory,
			saveName = "corrector_plate",
			name = "Corrector Plate",
			icon = ModBlocks.CORRECTOR_PLATE.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Corrector Plate")} will snap any entity moving on it to be moving along the center of the block.",
					"That is, they can only move along the plus-shaped path going straight through the middle of the block."
				)
			)
		}


		directionalAcceleratorPlate = book.entry(
			category = platesCategory,
			saveName = "directional_accelerator_plate",
			name = "Directional Accelerator Plate",
			icon = ModBlocks.DIRECTIONAL_ACCELERATOR_PLATE.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Directional Accelerator Plate")} will push entities touching it in the direction the Plate is aiming."
				)
			)
		}


		extractionPlate = book.entry(
			category = platesCategory,
			saveName = "extraction_plate",
			name = "Extraction Plate",
			icon = ModBlocks.EXTRACTION_PLATE.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Extraction Plate")} moves items from an inventory on its input side toward its output side.",
					"If an inventory is on the output side, the items are inserted into it. Otherwise, they are dropped into the world in that direction."
				)
			)

			textPage(
				text = bookText(
					"Right-click a side of the Plate to set the output direction. Sneak right-click anywhere on the Plate to cycle the input direction."
				)
			)
		}


		filteredRedirectorPlate = book.entry(
			category = platesCategory,
			saveName = "filtered_redirector_plate",
			name = "Filtered Redirector Plate",
			icon = ModBlocks.FILTERED_REDIRECTOR_PLATE.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Filtered Redirector Plate")} redirects entities based on their type.",
					"Entities entering from either end travel straight across by default. An entity matching the left filter is redirected to the left, while an entity matching the right filter is redirected to the right."
				)
			)

			textPage(
				text = bookText(
					"Right-click the Plate to open its filter screen, then place an ${internalLink(ItemsPatchouliCategory.entityFilter, "Entity Filter")} in either slot."
				)
			)
		}


		itemRejuvenatorPlate = book.entry(
			category = platesCategory,
			saveName = "item_rejuvenator_plate",
			name = "Item Rejuvenator Plate",
			icon = ModBlocks.ITEM_REJUVENATOR_PLATE.get()
		) {
			textPage(
				text = bookText(
					"Any item entity that touches an ${major("Item Rejuvenator Plate")} will have its despawn timer reset to 4 minutes and its age reset."
				)
			)
		}


		itemSealerPlate = book.entry(
			category = platesCategory,
			saveName = "item_sealer_plate",
			name = "Item Sealer Plate",
			icon = ModBlocks.ITEM_SEALER_PLATE.get()
		) {
			textPage(
				text = bookText(
					"Any item entity that touches an ${major("Item Sealer Plate")} will be prevented from being picked up for 30 seconds."
				)
			)
		}


		processingPlate = book.entry(
			category = platesCategory,
			saveName = "processing_plate",
			name = "Processing Plate",
			icon = ModBlocks.PROCESSING_PLATE.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Processing Plate")} combines the functions of the [Collection Plate](./collection_plate) and the [Extraction Plate](./extraction_plate). It can collect dropped items into the inventory below it while independently moving items from another adjacent inventory toward its configured output.",
					"Like the other Plates, it is placed on top of a supporting block and has no collision box. Entities can move through the thin Plate, and dropped items are processed when they touch it."
				)
			)

			textPage(
				text = bookText(
					"${major("Collecting dropped items")}",
					"When a dropped item entity touches the Processing Plate, the Plate attempts to insert the stack into the inventory directly below it.",
					"This collection direction is always ${major("Down")}. It is separate from the configurable extraction and output sides described below."
				)
			)

			textPage(
				text = bookText(
					"If the inventory can accept only part of the stack, the accepted items are inserted and the remainder stays in the world. If it cannot accept any of the stack, the item remains unchanged. If the entire stack is inserted, the item entity is removed.",
					"${major("Extracting items from an inventory")}",
					"The Processing Plate also performs an automatic inventory transfer twice per second. Each transfer attempt checks the inventory on the configured ${major("extraction side")}."
				)
			)

			textPage(
				text = bookText(
					"The extraction side may be Down, North, East, South, West, or Up. The Plate accesses the face of that inventory which points toward the Plate, so the sided insertion and extraction rules of machines and other inventories are respected.",
					"The Plate checks the inventory's slots in order. When it finds an item stack that can be extracted and sent to the output, it moves up to 64 items and finishes that transfer attempt. It therefore transfers from at most one slot per attempt."
				)
			)

			textPage(
				text = bookText(
					"${major("Sending extracted items to the output")}",
					"The ${major("output side")} may be North, East, South, or West.",
					"If an inventory is adjacent to the output side, the Plate inserts the extracted items into the face pointing toward the Plate. It extracts only as many items as the output can accept."
				)
			)

			textPage(
				text = bookText(
					"If there is no inventory on the output side, the extracted stack is dropped just beyond the edge of the Plate and pushed gently in the output direction. If an output inventory unexpectedly leaves a remainder during the transfer, that remainder is dropped and pushed in the same way.",
					"The marking on top of the Plate indicates its current output direction."
				)
			)

			textPage(
				text = bookText(
					"${major("Configuration")}",
					"The Processing Plate has no menu. Configure it with an empty hand:",
					"- ${major("Right-click")} to cycle the extraction side.\n- ${major("Sneak right-click")} to cycle the horizontal output side."
				)
			)

			textPage(
				text = bookText(
					"A status message displays the newly selected side after each interaction.",
					"When placed, the output points in the direction the player was facing. The extraction side points in the opposite direction."
				)
			)

			textPage(
				text = bookText(
					"${major("Example")}",
					"Place a Processing Plate on a chest, with a furnace to its north and another chest to its east. Set the extraction side to North and the output side to East.",
					"The Plate now performs two independent jobs:"
				)
			)

			textPage(
				text = bookText(
					"1. Dropped items moving over the Plate are inserted into the chest below. 2. Items are extracted from the furnace to the north and inserted into the chest to the east.",
					"Changing the extraction or output side does not change where dropped items are collected; dropped items always go into the inventory below."
				)
			)
		}


		redirectorPlate = book.entry(
			category = platesCategory,
			saveName = "redirector_plate",
			name = "Redirector Plate",
			icon = ModBlocks.REDIRECTOR_PLATE.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Redirector Plate")} has two active sides. Any entity that moves onto one active side will be teleported to the other.",
					"Right-click to set the first active side, and sneak right-click to set the other."
				)
			)
		}


		redstonePlate = book.entry(
			category = platesCategory,
			saveName = "redstone_plate",
			name = "Redstone Plate",
			icon = ModBlocks.REDSTONE_PLATE.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Redstone Plate")} redirects entities entering from its input side to one of two output sides.",
					"When the Plate receives a redstone signal, entities take the powered output. Otherwise, they take the unpowered output."
				)
			)

			textPage(
				text = bookText(
					"Right-click an unused side of the Plate to set the powered output. Sneak right-click to set the unpowered output."
				)
			)
		}


		platforms = book.entry(
			category = bookCategory,
			saveName = "platforms",
			name = "Platforms",
			icon = ModBlocks.OAK_PLATFORM.get()
		) {
			textPage(
				text = bookText(
					"${major("Platforms")}",
					"${major("Platforms")} are solid from the top, but not from the bottom or sides.",
					"Additionally, sneaking will allow you to fall through them."
				)
			)

			textPage(
				text = bookText(
					"${major("Super Lubricant Platform")}",
					"The ${major("Super Lubricant Platform")} works the same, but works like other ${internalLink(superLubricatedBlocks, "Super Lubricated blocks")} and negates all friction.",
					"This makes it very useful for transporting items."
				)
			)

			textPage(
				text = bookText(
					"${major("Filtered Super Lubricant Platform")}",
					"The ${major("Filtered Super Lubricant Platform")} works the same, but can hold an ${internalLink(ItemsPatchouliCategory.itemFilter, "Item Filter")}.",
					"Anything matching the filter will fall through the platform."
				)
			)

			craftingPage(ResourceLocation.parse("irregular_implements:oak_platform"))

			craftingPage(ResourceLocation.parse("irregular_implements:super_lubricant_platform"))

			craftingPage(ResourceLocation.parse("irregular_implements:filtered_super_lubricant_platform"))
		}


		playerInterface = book.entry(
			category = bookCategory,
			saveName = "player_interface",
			name = "Player Interface",
			icon = ModBlocks.PLAYER_INTERFACE.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Player Interface")} allows you to interact with the owner's inventory via pipes and the like.",
					"Each face aims at a different part of the inventory:\n- Top: armor\n- Bottom: hotbar\n- North: offhand\n- Others: main inventory"
				)
			)
		}


		quartzGlass = book.entry(
			category = bookCategory,
			saveName = "quartz_glass",
			name = "Quartz Glass",
			icon = ModBlocks.QUARTZ_GLASS.get()
		) {
			textPage(
				text = bookText(
					"${major("Quartz Glass")} allows players to pass through, but is solid for everything else."
				)
			)
		}


		rainShield = book.entry(
			category = bookCategory,
			saveName = "rain_shield",
			name = "Rain Shield",
			icon = ModBlocks.RAIN_SHIELD.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Rain Shield")} prevents rain from falling in a radius around it. By default, this radius is 5 chunks.",
					"It can be disabled with a redstone signal."
				)
			)
		}


		rainbowLamp = book.entry(
			category = bookCategory,
			saveName = "rainbow_lamp",
			name = "Rainbow Lamp",
			icon = ModBlocks.RAINBOW_LAMP.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Rainbow Lamp")} glows a different color depending on the redstone signal strength it's receiving."
				)
			)
		}


		advancedRedstoneInterface = book.entry(
			category = redstoneCategory,
			saveName = "advanced_redstone_interface",
			name = "Advanced Redstone Interface",
			icon = ModBlocks.ADVANCED_REDSTONE_INTERFACE.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Advanced Redstone Interface")} will transmit its own received redstone signal strength to up to 9 linked blocks.",
					"Insert ${internalLink(ItemsPatchouliCategory.locationFilter, "Location Filters")} to link it to those blocks."
				)
			)
		}


		advancedRedstoneTorch = book.entry(
			category = redstoneCategory,
			saveName = "advanced_redstone_torch",
			name = "Advanced Redstone Torch",
			icon = ModBlocks.ADVANCED_REDSTONE_TORCH.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Advanced Redstone Torch")} has a configurable redstone signal strength for when its powered and unpowered."
				)
			)
		}


		analogEmitter = book.entry(
			category = redstoneCategory,
			saveName = "analog_emitter",
			name = "Analog Emitter",
			icon = ModBlocks.ANALOG_EMITTER.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Analog Emitter")}, when powered from its front side, will emit a signal with a configurable strength.",
					"Right-click it to cycle the output strength."
				)
			)
		}


		basicRedstoneInterface = book.entry(
			category = redstoneCategory,
			saveName = "basic_redstone_interface",
			name = "Basic Redstone Interface",
			icon = ModBlocks.BASIC_REDSTONE_INTERFACE.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Basic Redstone Interface")} will transmit its own received redstone signal strength to the block it's linked to.",
					"Use a ${internalLink(ItemsPatchouliCategory.redstoneTool, "Redstone Tool")} to link it to a block."
				)
			)
		}


		blockDestabilizer = book.entry(
			category = redstoneCategory,
			saveName = "block_destabilizer",
			name = "Block Destabilizer",
			icon = ModBlocks.BLOCK_DESTABILIZER.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Block Destabilizer")} will cause the block in front of it, and all connected blocks of the same type, to fall like Sand when it receives a redstone pulse.",
					"In its GUI, you can toggle various settings."
				)
			)

			textPage(
				text = bookText(
					"When in Lazy Mode, it will remember what block positions to destabilize, even if they're different blocks.",
					"To set the Lazy Shape, set it to Lazy Mode and then run it once. It'll save the locations of the blocks it destabilizes, and will destabilize specifically those next time regardless of what block is there."
				)
			)

			textPage(
				text = bookText(
					"Clicking the Show Lazy Shape button will show cubes where all positions in the Lazy Shape are located.",
					"The custom render type that this uses is currently not compatible with Sodium! It won't crash, but the Lazy Shape indicators won't be visible through blocks."
				)
			)

			textPage(
				text = bookText(
					"Finally, the Forget Lazy Shape button will clear the saved positions."
				)
			)
		}


		blockDetector = book.entry(
			category = redstoneCategory,
			saveName = "block_detector",
			name = "Block Detector",
			icon = ModBlocks.BLOCK_DETECTOR.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Block Detector")} will emit a redstone signal when the block in front of it matches the block stored in its inventory."
				)
			)
		}


		chatDetector = book.entry(
			category = redstoneCategory,
			saveName = "chat_detector",
			name = "Chat Detector",
			icon = ModBlocks.CHAT_DETECTOR.get()
		) {
			textPage(
				text = bookText(
					"${major("Chat Detector")}",
					"The ${major("Chat Detector")} emits a redstone pulse when the player that places it says a specific phrase in chat.",
					"Set the phrase in its GUI. It's actually a RegEx field, so you can do more advanced filters. Just typing in the exact phrase is enough, however."
				)
			)

			textPage(
				text = bookText(
					"You can also toggle if the message gets canceled or not.",
					"${major("Global Chat Detector")}",
					"The ${major("Global Chat Detector")} works the same way, but listens to chat messages from every player instead of only its owner."
				)
			)

			textPage(
				text = bookText(
					"It can only cancel messages from its owner, however."
				)
			)

			craftingPage(ResourceLocation.parse("irregular_implements:chat_detector"))

			craftingPage(ResourceLocation.parse("irregular_implements:global_chat_detector"))
		}


		contactButton = book.entry(
			category = redstoneCategory,
			saveName = "contact_button",
			name = "Contact Button",
			icon = ModBlocks.CONTACT_BUTTON.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Contact Button")} works like a Button, but powers when the block it's aimed at is clicked, rather than when it itself is clicked."
				)
			)
		}


		contactLever = book.entry(
			category = redstoneCategory,
			saveName = "contact_lever",
			name = "Contact Lever",
			icon = ModBlocks.CONTACT_LEVER.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Contact Lever")} works like a Lever, but toggles when the block it's aimed at is clicked, rather than when it itself is clicked."
				)
			)
		}


		entityDetector = book.entry(
			category = redstoneCategory,
			saveName = "entity_detector",
			name = "Entity Detector",
			icon = ModBlocks.ENTITY_DETECTOR.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Entity Detector")} emits a redstone signal when an entity matching its filter is within its detection area.",
					"In its GUI you can set the radius in each dimension, if the signal is inverted or not, and specify what type of entity to detect."
				)
			)

			textPage(
				text = bookText(
					"You can choose to detect:\n- All Entities\n- Living Entities\n- Animals\n- Monsters\n- Players\n- Items\n- Custom",
					"When in Custom mode, it will use an inserted ${internalLink(ItemsPatchouliCategory.entityFilter, "Entity Filter")}."
				)
			)
		}


		inventoryTester = book.entry(
			category = redstoneCategory,
			saveName = "inventory_tester",
			name = "Inventory Tester",
			icon = ModBlocks.INVENTORY_TESTER.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Inventory Tester")} must be placed on an inventory, and stores an item in itself.",
					"It will emit a redstone signal if the inventory is capable of accepting that item."
				)
			)

			textPage(
				text = bookText(
					"It also checks the *side* of the inventory it's attached to. If placed on top of a Furnace, it will only work if the Furnace can accept the item into its top.",
					"Because of that, you may want to also use the ${internalLink(inventoryRerouter, "Inventory Rerouter")}."
				)
			)

			textPage(
				text = bookText(
					"You can invert it in its GUI, so it emits a signal when the inventory *can't* accept the item."
				)
			)
		}


		ironDropper = book.entry(
			category = redstoneCategory,
			saveName = "iron_dropper",
			name = "Iron Dropper",
			icon = ModBlocks.IRON_DROPPER.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Iron Dropper")} is an upgrade to the regular Dropper, with a handful of extra features.",
					"Inside it are 4 buttons to cycle its various settings."
				)
			)

			textPage(
				text = bookText(
					"Redstone:\n- Eject when pulsed\n- Eject while powered\n- Eject always",
					"Pickup delay:\n- 20 ticks\n- 5 ticks\n- 0 ticks"
				)
			)

			textPage(
				text = bookText(
					"Trajectory:\n- Random\n- Straight",
					"Effects:\n- Particles and sound\n- Sound only\n- Particles only\n- None"
				)
			)
		}


		lapisLamp = book.entry(
			category = redstoneCategory,
			saveName = "lapis_lamp",
			name = "Lapis Lamp",
			icon = ModBlocks.LAPIS_LAMP.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Lapis Lamp")}, when provided a redstone signal, will give off false light.",
					"That is, mob spawning and other game logic will treat the area is if it were not lit, but players will see light."
				)
			)
		}


		moonPhaseDetector = book.entry(
			category = redstoneCategory,
			saveName = "moon_phase_detector",
			name = "Moon Phase Detector",
			icon = ModBlocks.MOON_PHASE_DETECTOR.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Moon Phase Detector")} emits a redstone signal strength proportional to the phase of the moon.",
					"It emits a full signal during a full moon, and no signal during a new moon."
				)
			)

			textPage(
				text = bookText(
					"You can invert this by right-clicking it."
				)
			)
		}


		onlineDetector = book.entry(
			category = redstoneCategory,
			saveName = "online_detector",
			name = "Online Detector",
			icon = ModBlocks.ONLINE_DETECTOR.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Online Detector")} emits a redstone signal when the chosen player is logged onto the server. Set the player by typing their username into its GUI."
				)
			)
		}


		quartzLamp = book.entry(
			category = redstoneCategory,
			saveName = "quartz_lamp",
			name = "Quartz Lamp",
			icon = ModBlocks.QUARTZ_LAMP.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Quartz Lamp")}, when provided a redstone signal, will give off light that cannot be seen.",
					"That is, mob spawning and other game logic will treat the area is if it were lit, but players will not see any light."
				)
			)
		}


		redstoneObserver = book.entry(
			category = redstoneCategory,
			saveName = "redstone_observer",
			name = "Redstone Observer",
			icon = ModBlocks.REDSTONE_OBSERVER.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Redstone Observer")} gives off the same redstone signal output as the block it's linked to.",
					"Use a ${internalLink(ItemsPatchouliCategory.redstoneTool, "Redstone Tool")} to link it to a block."
				)
			)
		}


		sidedRedstone = book.entry(
			category = redstoneCategory,
			saveName = "sided_redstone",
			name = "Sided Block of Redstone",
			icon = ModBlocks.SIDED_BLOCK_OF_REDSTONE.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Sided Block of Redstone")} emits a full signal on one face, and nothing on the others."
				)
			)
		}


		triggerGlass = book.entry(
			category = redstoneCategory,
			saveName = "trigger_glass",
			name = "Trigger Glass",
			icon = ModBlocks.TRIGGER_GLASS.get()
		) {
			textPage(
				text = bookText(
					"${major("Trigger Glass")} is usually solid, but temporarily becomes non-solid when it receives a redstone pulse.",
					"This effect propagates to all adjacent Trigger Glass blocks, within a distance."
				)
			)
		}


		sakanadeSpores = book.entry(
			category = bookCategory,
			saveName = "sakanade_spores",
			name = "Sakanade Spores",
			icon = ModBlocks.SAKANADE_SPORES.get()
		) {
			textPage(
				text = bookText(
					"${major("Sakanade Spores")} can be found on the bottom of Large Brown Mushroom caps. When touched, Sakanade Spores will inflict the Collapse effect to entities.",
					"For players, the Collapse effect inverts their movement and mouse controls."
				)
			)

			textPage(
				text = bookText(
					"For mobs, it instead confuses their pathfinding, causing them to move erratically."
				)
			)
		}


		shockAbsorber = book.entry(
			category = bookCategory,
			saveName = "shock_absorber",
			name = "Shock Absorber",
			icon = ModBlocks.SHOCK_ABSORBER.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Shock Absorber")} negates all fall damage when landed on.",
					"Additionally, it will emit a redstone signal proportional the the distance fallen."
				)
			)
		}


		slimeCube = book.entry(
			category = bookCategory,
			saveName = "slime_cube",
			name = "Slime Cube",
			icon = ModBlocks.SLIME_CUBE.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Slime Cube")}, when unpowered, causes Slimes to spawn in a radius around it. By default, this radius is 1 chunk (so a 3x3 chunk area centered on itself).",
					"When powered, it instead prevents Slimes from spawning in that same area."
				)
			)
		}


		spectreCoils = book.entry(
			category = bookCategory,
			saveName = "spectre_coils",
			name = "Spectre Coils",
			icon = ModBlocks.SPECTRE_COIL_BASIC.get()
		) {
			textPage(
				text = bookText(
					"${major("Regular Coils")}",
					"${major("Spectre Coils")} allow you to extract energy from your ${internalLink(spectreEnergyInjector, "Spectre Energy Buffer")} wirelessly.",
					"Each tier pulls a higher RF/t. Place the Coil directly on the machine you want to charge."
				)
			)

			textPage(
				text = bookText(
					"${major("Generator Coils")}",
					"The ${major("Spectre Coil Nr. 245")} generates a small amount of RF/t for free, rather than using your Spectre Energy Buffer. It can be rarely found in dungeon chests.",
					"The ${major("Genesis Spectre Coil")} is creative-only, and generates an infinite amount of RF/t."
				)
			)

			craftingPage(ResourceLocation.parse("irregular_implements:spectre_coil_basic"))

			craftingPage(ResourceLocation.parse("irregular_implements:spectre_coil_redstone"))

			craftingPage(ResourceLocation.parse("irregular_implements:spectre_coil_ender"))
		}


		spectreEnergyInjector = book.entry(
			category = bookCategory,
			saveName = "spectre_energy_injector",
			name = "Spectre Energy Injector",
			icon = ModBlocks.SPECTRE_ENERGY_INJECTOR.get()
		) {
			textPage(
				text = bookText(
					"Every player has their own Spectre Energy Buffer, which acts sort of like an Ender Chest for FE. By default, it can store 1,000,000 FE.",
					"Energy can be inserted into a player's Buffer via the ${major("Spectre Energy Injector")}. Whoever placed the Injector is whose Buffer gets filled."
				)
			)

			textPage(
				text = bookText(
					"You cannot extract from the Injector. Instead, you'll have to use either a Spectre Coil or a ${internalLink(ItemsPatchouliCategory.spectreCharger, "Spectre Charger")}."
				)
			)
		}


		spectreLens = book.entry(
			category = bookCategory,
			saveName = "spectre_lens",
			name = "Spectre Lens",
			icon = ModBlocks.SPECTRE_LENS.get()
		) {
			textPage(
				text = bookText(
					"The ${major("Spectre Lens")} can be placed on a Beacon to allow it to effect you from any distance, as long as you're in the same dimension.",
					"It only effects the player who placed the Lens."
				)
			)

			textPage(
				text = bookText(
					"Naturally, it only works while the chunk it's in is loaded."
				)
			)
		}


		spectreSapling = book.entry(
			category = bookCategory,
			saveName = "spectre_sapling",
			name = "Spectre Sapling",
			icon = ModBlocks.SPECTRE_SAPLING.get()
		) {
			textPage(
				text = bookText(
					"${major("Spectre Saplings")} grow into a translucent Spectre trees.",
					"${major("Spectre Leaves")} have a chance of dropping ${internalLink(ItemsPatchouliCategory.ectoplasm, "Ectoplasm")}, allowing it to be farmed."
				)
			)

			textPage(
				text = bookText(
					"Create a Spectre Sapling by using Ectoplasm on any other Sapling."
				)
			)
		}


		superLubricatedBlocks = book.entry(
			category = bookCategory,
			saveName = "super_lubricated_blocks",
			name = "Super Lubricated Blocks",
			icon = BuiltInRegistries.ITEM.get(ResourceLocation.parse("irregular_implements:super_lubricant_stone"))
		) {
			textPage(
				text = bookText(
					"${major("Super Lubricant Stone")} and ${major("Super Lubricant Ice")} fully negate friction, allowing entities to slide over them without slowing down."
				)
			)

			craftingPage(ResourceLocation.parse("irregular_implements:super_lubricant_stone"))

			craftingPage(ResourceLocation.parse("irregular_implements:super_lubricant_ice"))
		}

	}
}