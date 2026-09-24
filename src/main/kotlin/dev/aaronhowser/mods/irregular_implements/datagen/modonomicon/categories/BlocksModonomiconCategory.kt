package dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.categories

import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel
import dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.ModonomiconBookText.bookText
import dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.ModonomiconBookText.internalLink
import dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.ModonomiconBookText.list
import dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.ModonomiconMultiblockProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.aaronhowser.mods.patchoulidatagen.modonomicon.book_element.ModonomiconBook
import dev.aaronhowser.mods.patchoulidatagen.modonomicon.book_element.ModonomiconBookCategory
import dev.aaronhowser.mods.patchoulidatagen.modonomicon.book_element.ModonomiconBookEntry
import net.minecraft.core.registries.BuiltInRegistries

object BlocksModonomiconCategory {

	lateinit var bookCategory: ModonomiconBookCategory
	lateinit var platesCategory: ModonomiconBookCategory
	lateinit var redstoneCategory: ModonomiconBookCategory
	lateinit var advancedItemCollector: ModonomiconBookEntry
	lateinit var autoPlacer: ModonomiconBookEntry
	lateinit var biomeBlocks: ModonomiconBookEntry
	lateinit var biomeRadar: ModonomiconBookEntry
	lateinit var blockBreaker: ModonomiconBookEntry
	lateinit var blockOfSticks: ModonomiconBookEntry
	lateinit var blockTeleporter: ModonomiconBookEntry
	lateinit var compressedSlimeBlock: ModonomiconBookEntry
	lateinit var customCraftingTable: ModonomiconBookEntry
	lateinit var diaphanousBlock: ModonomiconBookEntry
	lateinit var enderBridge: ModonomiconBookEntry
	lateinit var enderMailbox: ModonomiconBookEntry
	lateinit var energyDistributor: ModonomiconBookEntry
	lateinit var fertilizedDirt: ModonomiconBookEntry
	lateinit var glowingMushroom: ModonomiconBookEntry
	lateinit var igniter: ModonomiconBookEntry
	lateinit var imbuingStation: ModonomiconBookEntry
	lateinit var inventoryRerouter: ModonomiconBookEntry
	lateinit var itemCollector: ModonomiconBookEntry
	lateinit var lapisGlass: ModonomiconBookEntry
	lateinit var luminousBlockWhite: ModonomiconBookEntry
	lateinit var natureCore: ModonomiconBookEntry
	lateinit var notificationInterface: ModonomiconBookEntry
	lateinit var peaceCandle: ModonomiconBookEntry
	lateinit var pitcherPlant: ModonomiconBookEntry
	lateinit var acceleratorPlate: ModonomiconBookEntry
	lateinit var bouncyPlate: ModonomiconBookEntry
	lateinit var collectionPlate: ModonomiconBookEntry
	lateinit var correctorPlate: ModonomiconBookEntry
	lateinit var directionalAcceleratorPlate: ModonomiconBookEntry
	lateinit var extractionPlate: ModonomiconBookEntry
	lateinit var filteredRedirectorPlate: ModonomiconBookEntry
	lateinit var itemRejuvenatorPlate: ModonomiconBookEntry
	lateinit var itemSealerPlate: ModonomiconBookEntry
	lateinit var processingPlate: ModonomiconBookEntry
	lateinit var redirectorPlate: ModonomiconBookEntry
	lateinit var redstonePlate: ModonomiconBookEntry
	lateinit var platforms: ModonomiconBookEntry
	lateinit var playerInterface: ModonomiconBookEntry
	lateinit var quartzGlass: ModonomiconBookEntry
	lateinit var rainShield: ModonomiconBookEntry
	lateinit var rainbowLamp: ModonomiconBookEntry
	lateinit var advancedRedstoneInterface: ModonomiconBookEntry
	lateinit var advancedRedstoneTorch: ModonomiconBookEntry
	lateinit var analogEmitter: ModonomiconBookEntry
	lateinit var basicRedstoneInterface: ModonomiconBookEntry
	lateinit var blockDestabilizer: ModonomiconBookEntry
	lateinit var blockDetector: ModonomiconBookEntry
	lateinit var chatDetector: ModonomiconBookEntry
	lateinit var contactButton: ModonomiconBookEntry
	lateinit var contactLever: ModonomiconBookEntry
	lateinit var entityDetector: ModonomiconBookEntry
	lateinit var inventoryTester: ModonomiconBookEntry
	lateinit var ironDropper: ModonomiconBookEntry
	lateinit var lapisLamp: ModonomiconBookEntry
	lateinit var moonPhaseDetector: ModonomiconBookEntry
	lateinit var onlineDetector: ModonomiconBookEntry
	lateinit var quartzLamp: ModonomiconBookEntry
	lateinit var redstoneObserver: ModonomiconBookEntry
	lateinit var sidedRedstone: ModonomiconBookEntry
	lateinit var triggerGlass: ModonomiconBookEntry
	lateinit var sakanadeSpores: ModonomiconBookEntry
	lateinit var shockAbsorber: ModonomiconBookEntry
	lateinit var slimeCube: ModonomiconBookEntry
	lateinit var spectreCoils: ModonomiconBookEntry
	lateinit var spectreEnergyInjector: ModonomiconBookEntry
	lateinit var spectreLens: ModonomiconBookEntry
	lateinit var spectreSapling: ModonomiconBookEntry
	lateinit var superLubricatedBlocks: ModonomiconBookEntry

	fun generate(book: ModonomiconBook) {
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
			sortNumber = 2
		}

		redstoneCategory = book.category(
			saveName = "redstone",
			name = "Redstone",
			description = "Blocks that interact with redstone signals",
			icon = ModBlocks.BASIC_REDSTONE_INTERFACE.get()
		) {
			sortNumber = 3
		}

		addEntries()
	}

	private fun addEntries() {
		advancedItemCollector = bookCategory.entry(
			saveName = "advanced_item_collector",
			name = "Advanced Item Collector",
			icon = ModBlocks.ADVANCED_ITEM_COLLECTOR.get()
		) {
			textPage(
				text = bookText(
					"The **Advanced Item Collector** is an upgraded version of the ${internalLink(itemCollector, "Item Collector")}. Its collection volume is configurable, ranging from 0 to 10 blocks in each direction.",
					"Each axis (X/Y/Z) can be configured independently, as well."
				)
			)

			textPage(
				text = bookText(
					"Additionally, you can insert an ${internalLink(ItemsModonomiconCategory.itemFilter, "Item Filter")}, and it will only collect items that match the filter."
				)
			)
		}


		autoPlacer = bookCategory.entry(
			saveName = "auto_placer",
			name = "Auto Placer",
			icon = ModBlocks.AUTO_PLACER.get()
		) {
			textPage(
				text = bookText(
					"The **Auto Placer** will try to place the block in its inventory in front of itself.",
					"It tries to do this every tick, unless it has a redstone signal."
				)
			)
		}


		biomeBlocks = bookCategory.entry(
			saveName = "biome_blocks",
			name = "Biome Blocks",
			icon = BuiltInRegistries.ITEM.get(OtherUtil.modResource("biome_stone"))
		) {
			textPage(
				text = bookText(
					"**Biome blocks** change color to match the biome they're in."
				)
			)

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(OtherUtil.modResource("biome_stone"))
					.withRecipeId2(OtherUtil.modResource("biome_cobblestone"))
			})

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(OtherUtil.modResource("biome_bricks"))
					.withRecipeId2(OtherUtil.modResource("biome_bricks_cracked"))
			})

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(OtherUtil.modResource("biome_bricks_chiseled"))
					.withRecipeId2(OtherUtil.modResource("biome_glass"))
			})
		}


		biomeRadar = bookCategory.entry(
			saveName = "biome_radar",
			name = "Biome Radar",
			icon = ModBlocks.BIOME_RADAR.get()
		) {
			textPage(
				text = bookText(
					"The **Biome Radar** is a multiblock structure that helps you find biomes in the world.",
					"Build the structure shown on the next page."
				)
			)

			customPage(factory = { _, _ ->
				BookMultiblockPageModel.create()
					.withMultiblockName("Biome Radar")
					.withMultiblockId(ModonomiconMultiblockProvider.BIOME_RADAR)
					.withText(
						bookText(
							"Required blocks:",
							list("1 Biome Radar", "10 Iron Bars")
						)
					)
			})

			textPage(
				text = bookText(
					"Insert a **Biome Crystal** into the radar. There is one for each biome in the game, and they can be found in almost any chest with loot.",
					"If the biome exists in the world, the flames above the Iron Bars will blow in its direction."
				)
			)

			textPage(
				text = bookText(
					"Use a ${internalLink(ItemsModonomiconCategory.locationFilter, "Location Filter")} on the radar to save the biome's location.",
					"Craft that filter with a ${internalLink(ItemsModonomiconCategory.goldenCompass, "Golden Compass")} to make your way there!"
				)
			)
		}


		blockBreaker = bookCategory.entry(
			saveName = "block_breaker",
			name = "Block Breaker",
			icon = ModBlocks.BLOCK_BREAKER.get()
		) {
			textPage(
				text = bookText(
					"**Block Breaker**",
					"The **Block Breaker** will break the block in front of it. It has the equivalent of an unbreakable Iron Pickaxe.",
					"Blocks broken will be placed into an inventory behind it, if one exists. Otherwise, it will drop to the floor behind it."
				)
			)

			textPage(
				text = bookText(
					"It can be disabled with a redstone signal.",
					"**Diamond Breaker**",
					"The **Diamond Breaker** can be applied to the Block Breaker to upgrade it, giving it the equivalent of a Diamond Pickaxe."
				)
			)

			textPage(
				text = bookText(
					"It can also be enchanted, and any enchantments will actually be applied when breaking blocks!"
				)
			)

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(OtherUtil.modResource("block_breaker"))
					.withRecipeId2(OtherUtil.modResource("diamond_breaker"))
			})
		}


		blockOfSticks = bookCategory.entry(
			saveName = "block_of_sticks",
			name = "Block of Sticks",
			icon = ModBlocks.BLOCK_OF_STICKS.get()
		) {
			textPage(
				text = bookText(
					"The **Block of Sticks** breaks itself shortly after being placed.",
					"The **Returning Block of Sticks** will teleport itself to the nearest player when broken."
				)
			)

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(OtherUtil.modResource("block_of_sticks"))
					.withRecipeId2(OtherUtil.modResource("returning_block_of_sticks"))
			})
		}


		blockTeleporter = bookCategory.entry(
			saveName = "block_teleporter",
			name = "Block Teleporter",
			icon = ModBlocks.BLOCK_TELEPORTER.get()
		) {
			textPage(
				text = bookText(
					"The **Block Teleporter** allows you to teleport the block in front of itself to another Block Teleporter when powered.",
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


		compressedSlimeBlock = bookCategory.entry(
			saveName = "compressed_slime_block",
			name = "Compressed Slime Block",
			icon = ModBlocks.COMPRESSED_SLIME_BLOCK.get()
		) {
			textPage(
				text = bookText(
					"Right-clicking a Slime Block with a Shovel will turn it into a **Compressed Slime Block**. Clicking more will compress it further, or reset it.",
					"Touching a Compressed Slime Block will launch you into the air. The more it's compressed, the higher you bounce."
				)
			)
		}


		customCraftingTable = bookCategory.entry(
			saveName = "custom_crafting_table",
			name = "Custom Crafting Table",
			icon = ModBlocks.CUSTOM_CRAFTING_TABLE.get()
		) {
			textPage(
				text = bookText(
					"The **Custom Crafting Table** is functionally identical to a regular Crafting Table, but looks like the block it was crafted with.",
					"For example, 8 Oak Logs around a Crafting Table will make a Crafting Table that looks like an Oak Log."
				)
			)
		}


		diaphanousBlock = bookCategory.entry(
			saveName = "diaphanous_block",
			name = "Diaphanous Block",
			icon = ModBlocks.DIAPHANOUS_BLOCK.get()
		) {
			textPage(
				text = bookText(
					"**Diaphanous Blocks** look like regular blocks at a distance, but vanishes as you approach, and can be walked through.",
					"Craft a Diaphanous Block with any other block to set what it looks like."
				)
			)

			textPage(
				text = bookText(
					"Crafting a Diaphanous Block by itself will invert it, making it invisible at a distance and solid up close."
				)
			)
		}


		enderBridge = bookCategory.entry(
			saveName = "ender_bridge",
			name = "Ender Bridge",
			icon = ModBlocks.ENDER_BRIDGE.get()
		) {
			textPage(
				text = bookText(
					"The **Ender Bridge** and **Prismarine Ender Bridge**, when powered by redstone, will teleport the entities above it towards the **Ender Anchor** it's aiming at.",
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

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(OtherUtil.modResource("ender_bridge"))
					.withRecipeId2(OtherUtil.modResource("prismarine_ender_bridge"))
			})

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(OtherUtil.modResource("ender_anchor"))
			})
		}


		enderMailbox = bookCategory.entry(
			saveName = "ender_mailbox",
			name = "Ender Mailbox",
			icon = ModBlocks.ENDER_MAILBOX.get()
		) {
			textPage(
				text = bookText(
					"The **Ender Mailbox** allows you to send and receive ${internalLink(ItemsModonomiconCategory.enderLetter, "Ender Letters")} to and from other players.",
					"To send a Letter, simply use it on any Mailbox, and it will be sent to the recipient if theirs is not full."
				)
			)

			textPage(
				text = bookText(
					"Using any Ender Mailbox will show your own incoming Letters, similar to an Ender Chest."
				)
			)
		}


		energyDistributor = bookCategory.entry(
			saveName = "energy_distributor",
			name = "Energy Distributor",
			icon = ModBlocks.ENERGY_DISTRIBUTOR.get()
		) {
			textPage(
				text = bookText(
					"**Energy Distributor**",
					"The **Energy Distributor** allows you to evenly distribute FE along a line of adjacent machines it's aimed at.",
					"Starting from the block in front of it, it will step along a straight path until it reaches a block that cannot store FE. All blocks it finds will be added to its cache."
				)
			)

			textPage(
				text = bookText(
					"The Energy Distributor will simulate having an energy storage, which is actually made up of all machines in its cache. Any FE inserted into it is actually inserted into the cached blocks; the same for any FE removed.",
					"**Ender Energy Distributor**",
					"The **Ender Energy Distributor** works similarly, but uses 8 ${internalLink(ItemsModonomiconCategory.locationFilter, "Location Filters")} to specify the machines in its cache."
				)
			)

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(OtherUtil.modResource("energy_distributor"))
					.withRecipeId2(OtherUtil.modResource("ender_energy_distributor"))
			})
		}


		fertilizedDirt = bookCategory.entry(
			saveName = "fertilized_dirt",
			name = "Fertilized Dirt",
			icon = ModBlocks.FERTILIZED_DIRT.get()
		) {
			textPage(
				text = bookText(
					"**Fertilized Dirt** does not need to be hydrated, cannot be trampled, and grows crops 3 times faster.",
					"You still have to till it with a Hoe before seeds can be planted, however."
				)
			)
		}


		glowingMushroom = bookCategory.entry(
			saveName = "glowing_mushroom",
			name = "Glowing Mushroom",
			icon = ModBlocks.GLOWING_MUSHROOM.get()
		) {
			textPage(
				text = bookText(
					"**Glowing Mushrooms** can be found growing in caves.",
					"Any brewing recipe that would accept Glowstone Dust can also accept Glowing Mushrooms."
				)
			)
		}


		igniter = bookCategory.entry(
			saveName = "igniter",
			name = "Igniter",
			icon = ModBlocks.IGNITER.get()
		) {
			textPage(
				text = bookText(
					"The **Igniter** can be used to light a fire in front of itself, when given a redstone signal.",
					"It has 3 modes, set in its GUI:\n- Toggle: Lights when powered, extinguishes when unpowered\n- Keep Ignited: Keeps the fire lit while powered, does nothing when unpowered\n- Ignite: Lights when powered, does nothing when unpowered"
				)
			)
		}


		imbuingStation = bookCategory.entry(
			saveName = "imbuing_station",
			name = "Imbuing Station",
			icon = ModBlocks.IMBUING_STATION.get()
		) {
			textPage(
				text = bookText(
					"The **Imbuing Station** is used to create Imbues.",
					"Ingredients that go in the outer slots can go in any order, but the center ingredient must always go in the center slot."
				)
			)

			textPage(
				text = bookText(
					"The center slot can be accessed from the top face, the outer slots can be accessed from the sides, and the output slot can be accessed from the bottom."
				)
			)
		}


		inventoryRerouter = bookCategory.entry(
			saveName = "inventory_rerouter",
			name = "Inventory Rerouter",
			icon = ModBlocks.INVENTORY_REROUTER.get()
		) {
			textPage(
				text = bookText(
					"The **Inventory Rerouter** exposes the sides of an adjacent inventory from a more convenient location.",
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


		itemCollector = bookCategory.entry(
			saveName = "item_collector",
			name = "Item Collector",
			icon = ModBlocks.ITEM_COLLECTOR.get()
		) {
			textPage(
				text = bookText(
					"The **Item Collector** can be placed on any inventory, and it will collect item entities in a 3 block radius around itself.",
					"That's a 7x7x7 cube centered on the Collector."
				)
			)
		}


		lapisGlass = bookCategory.entry(
			saveName = "lapis_glass",
			name = "Lapis Glass",
			icon = ModBlocks.LAPIS_GLASS.get()
		) {
			textPage(
				text = bookText(
					"**Lapis Glass** is solid for players, but allows everything else to pass through."
				)
			)
		}


		luminousBlockWhite = bookCategory.entry(
			saveName = "luminous_block_white",
			name = "Luminous Blocks",
			icon = BuiltInRegistries.ITEM.get(OtherUtil.modResource("luminous_block_white"))
		) {
			textPage(
				text = bookText(
					"**Luminous Blocks** are always lit."
				)
			)

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(OtherUtil.modResource("luminous_block_white"))
					.withRecipeId2(OtherUtil.modResource("translucent_luminous_block_white"))
			})

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(OtherUtil.modResource("stained_bricks_white"))
			})
		}


		natureCore = bookCategory.entry(
			saveName = "nature_core",
			name = "Nature Core",
			icon = ModBlocks.NATURE_CORE.get()
		) {
			textPage(
				text = bookText(
					"The **Nature Core** structure can be found randomly around the overworld, with a Nature Chest nearby full of goodies.",
					"The Nature Core can also do these things randomly:\n- Convert nearby Sand into Dirt/Grass\n- Spawn an animal nearby\n- Bone Meal nearby crops\n- Plant Saplings nearby\n- Repair the structure around it"
				)
			)
		}


		notificationInterface = bookCategory.entry(
			saveName = "notification_interface",
			name = "Notification Interface",
			icon = ModBlocks.NOTIFICATION_INTERFACE.get()
		) {
			textPage(
				text = bookText(
					"The **Notification Interface** will send its owner a customizable notification when it receives a redstone pulse.",
					"You can set the notifications title, body, and icon in its GUI."
				)
			)

			textPage(
				text = bookText(
					"Server admins can also use the command `/ii notify    ` to send notifications to players."
				)
			)
		}


		peaceCandle = bookCategory.entry(
			saveName = "peace_candle",
			name = "Peace Candle",
			icon = ModBlocks.PEACE_CANDLE.get()
		) {
			textPage(
				text = bookText(
					"The **Peace Candle** prevents hostile mobs from spawning within a radius around it. By default, this radius is a single chunk. That is, it effects a 3x3 chunk area centered on itself.",
					"It can be disabled with a redstone signal."
				)
			)

			textPage(
				text = bookText(
					"Peace Candles can be found in certain Village temples. Where there would be a Brewing Stand, you can sometimes instead find a Peace Candle."
				)
			)
		}


		pitcherPlant = bookCategory.entry(
			saveName = "pitcher_plant",
			name = "Pitcher Plant",
			icon = ModBlocks.PITCHER_PLANT.get()
		) {
			textPage(
				text = bookText(
					"The **Pitcher Plant** generates an infinite amount of water. It can be found in wet biomes in the overworld.",
					"Clicking a bucket or other fluid receptacle will fill it with Water."
				)
			)

			textPage(
				text = bookText(
					"Placing it adjacent to a tank will fill the tank with water, at a configurable speed. You can also pipe water from it, also at a configurable speed."
				)
			)
		}


		acceleratorPlate = platesCategory.entry(
			saveName = "accelerator_plate",
			name = "Accelerator Plate",
			icon = ModBlocks.ACCELERATOR_PLATE.get()
		) {
			textPage(
				text = bookText(
					"The **Accelerator Plate** will speed up any entity moving on it, up to a limit.",
					"Whatever direction it's already moving, it'll move that direction faster."
				)
			)
		}


		bouncyPlate = platesCategory.entry(
			saveName = "bouncy_plate",
			name = "Bouncy Plate",
			icon = ModBlocks.BOUNCY_PLATE.get()
		) {
			textPage(
				text = bookText(
					"The **Bouncy Plate** will launch entities that touch it into the air."
				)
			)
		}


		collectionPlate = platesCategory.entry(
			saveName = "collection_plate",
			name = "Collection Plate",
			icon = ModBlocks.COLLECTION_PLATE.get()
		) {
			textPage(
				text = bookText(
					"Any item entity that touches an **Collection Plate** will be inserted into an adjacent inventory, if possible.",
					"The order it tries is: Down, North, South, East, East, Up."
				)
			)
		}


		correctorPlate = platesCategory.entry(
			saveName = "corrector_plate",
			name = "Corrector Plate",
			icon = ModBlocks.CORRECTOR_PLATE.get()
		) {
			textPage(
				text = bookText(
					"The **Corrector Plate** will snap any entity moving on it to be moving along the center of the block.",
					"That is, they can only move along the plus-shaped path going straight through the middle of the block."
				)
			)
		}


		directionalAcceleratorPlate = platesCategory.entry(
			saveName = "directional_accelerator_plate",
			name = "Directional Accelerator Plate",
			icon = ModBlocks.DIRECTIONAL_ACCELERATOR_PLATE.get()
		) {
			textPage(
				text = bookText(
					"The **Directional Accelerator Plate** will push entities touching it in the direction the Plate is aiming."
				)
			)
		}


		extractionPlate = platesCategory.entry(
			saveName = "extraction_plate",
			name = "Extraction Plate",
			icon = ModBlocks.EXTRACTION_PLATE.get()
		) {
			textPage(
				text = bookText(
					"The **Extraction Plate** moves items from an inventory on its input side toward its output side.",
					"If an inventory is on the output side, the items are inserted into it. Otherwise, they are dropped into the world in that direction."
				)
			)

			textPage(
				text = bookText(
					"Right-click a side of the Plate to set the output direction. Sneak right-click anywhere on the Plate to cycle the input direction."
				)
			)
		}


		filteredRedirectorPlate = platesCategory.entry(
			saveName = "filtered_redirector_plate",
			name = "Filtered Redirector Plate",
			icon = ModBlocks.FILTERED_REDIRECTOR_PLATE.get()
		) {
			textPage(
				text = bookText(
					"The **Filtered Redirector Plate** redirects entities based on their type.",
					"Entities entering from either end travel straight across by default. An entity matching the left filter is redirected to the left, while an entity matching the right filter is redirected to the right."
				)
			)

			textPage(
				text = bookText(
					"Right-click the Plate to open its filter screen, then place an ${internalLink(ItemsModonomiconCategory.entityFilter, "Entity Filter")} in either slot."
				)
			)
		}


		itemRejuvenatorPlate = platesCategory.entry(
			saveName = "item_rejuvenator_plate",
			name = "Item Rejuvenator Plate",
			icon = ModBlocks.ITEM_REJUVENATOR_PLATE.get()
		) {
			textPage(
				text = bookText(
					"Any item entity that touches an **Item Rejuvenator Plate** will have its despawn timer reset to 4 minutes and its age reset."
				)
			)
		}


		itemSealerPlate = platesCategory.entry(
			saveName = "item_sealer_plate",
			name = "Item Sealer Plate",
			icon = ModBlocks.ITEM_SEALER_PLATE.get()
		) {
			textPage(
				text = bookText(
					"Any item entity that touches an **Item Sealer Plate** will be prevented from being picked up for 30 seconds."
				)
			)
		}


		processingPlate = platesCategory.entry(
			saveName = "processing_plate",
			name = "Processing Plate",
			icon = ModBlocks.PROCESSING_PLATE.get()
		) {
			textPage(
				text = bookText(
					"The **Processing Plate** combines the functions of the [Collection Plate](./collection_plate) and the [Extraction Plate](./extraction_plate). It can collect dropped items into the inventory below it while independently moving items from another adjacent inventory toward its configured output.",
					"Like the other Plates, it is placed on top of a supporting block and has no collision box. Entities can move through the thin Plate, and dropped items are processed when they touch it."
				)
			)

			textPage(
				text = bookText(
					"**Collecting dropped items**",
					"When a dropped item entity touches the Processing Plate, the Plate attempts to insert the stack into the inventory directly below it.",
					"This collection direction is always **Down**. It is separate from the configurable extraction and output sides described below."
				)
			)

			textPage(
				text = bookText(
					"If the inventory can accept only part of the stack, the accepted items are inserted and the remainder stays in the world. If it cannot accept any of the stack, the item remains unchanged. If the entire stack is inserted, the item entity is removed.",
					"**Extracting items from an inventory**",
					"The Processing Plate also performs an automatic inventory transfer twice per second. Each transfer attempt checks the inventory on the configured **extraction side**."
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
					"**Sending extracted items to the output**",
					"The **output side** may be North, East, South, or West.",
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
					"**Configuration**",
					"The Processing Plate has no menu. Configure it with an empty hand:",
					"- **Right-click** to cycle the extraction side.\n- **Sneak right-click** to cycle the horizontal output side."
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
					"**Example**",
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


		redirectorPlate = platesCategory.entry(
			saveName = "redirector_plate",
			name = "Redirector Plate",
			icon = ModBlocks.REDIRECTOR_PLATE.get()
		) {
			textPage(
				text = bookText(
					"The **Redirector Plate** has two active sides. Any entity that moves onto one active side will be teleported to the other.",
					"Right-click to set the first active side, and sneak right-click to set the other."
				)
			)
		}


		redstonePlate = platesCategory.entry(
			saveName = "redstone_plate",
			name = "Redstone Plate",
			icon = ModBlocks.REDSTONE_PLATE.get()
		) {
			textPage(
				text = bookText(
					"The **Redstone Plate** redirects entities entering from its input side to one of two output sides.",
					"When the Plate receives a redstone signal, entities take the powered output. Otherwise, they take the unpowered output."
				)
			)

			textPage(
				text = bookText(
					"Right-click an unused side of the Plate to set the powered output. Sneak right-click to set the unpowered output."
				)
			)
		}


		platforms = bookCategory.entry(
			saveName = "platforms",
			name = "Platforms",
			icon = ModBlocks.OAK_PLATFORM.get()
		) {
			textPage(
				text = bookText(
					"**Platforms**",
					"**Platforms** are solid from the top, but not from the bottom or sides.",
					"Additionally, sneaking will allow you to fall through them."
				)
			)

			textPage(
				text = bookText(
					"**Super Lubricant Platform**",
					"The **Super Lubricant Platform** works the same, but works like other ${internalLink(superLubricatedBlocks, "Super Lubricated blocks")} and negates all friction.",
					"This makes it very useful for transporting items."
				)
			)

			textPage(
				text = bookText(
					"**Filtered Super Lubricant Platform**",
					"The **Filtered Super Lubricant Platform** works the same, but can hold an ${internalLink(ItemsModonomiconCategory.itemFilter, "Item Filter")}.",
					"Anything matching the filter will fall through the platform."
				)
			)

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(OtherUtil.modResource("oak_platform"))
					.withRecipeId2(OtherUtil.modResource("super_lubricant_platform"))
			})

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(OtherUtil.modResource("filtered_super_lubricant_platform"))
			})
		}


		playerInterface = bookCategory.entry(
			saveName = "player_interface",
			name = "Player Interface",
			icon = ModBlocks.PLAYER_INTERFACE.get()
		) {
			textPage(
				text = bookText(
					"The **Player Interface** allows you to interact with the owner's inventory via pipes and the like.",
					"Each face aims at a different part of the inventory:\n- Top: armor\n- Bottom: hotbar\n- North: offhand\n- Others: main inventory"
				)
			)
		}


		quartzGlass = bookCategory.entry(
			saveName = "quartz_glass",
			name = "Quartz Glass",
			icon = ModBlocks.QUARTZ_GLASS.get()
		) {
			textPage(
				text = bookText(
					"**Quartz Glass** allows players to pass through, but is solid for everything else."
				)
			)
		}


		rainShield = bookCategory.entry(
			saveName = "rain_shield",
			name = "Rain Shield",
			icon = ModBlocks.RAIN_SHIELD.get()
		) {
			textPage(
				text = bookText(
					"The **Rain Shield** prevents rain from falling in a radius around it. By default, this radius is 5 chunks.",
					"It can be disabled with a redstone signal."
				)
			)
		}


		rainbowLamp = bookCategory.entry(
			saveName = "rainbow_lamp",
			name = "Rainbow Lamp",
			icon = ModBlocks.RAINBOW_LAMP.get()
		) {
			textPage(
				text = bookText(
					"The **Rainbow Lamp** glows a different color depending on the redstone signal strength it's receiving."
				)
			)
		}


		advancedRedstoneInterface = redstoneCategory.entry(
			saveName = "advanced_redstone_interface",
			name = "Advanced Redstone Interface",
			icon = ModBlocks.ADVANCED_REDSTONE_INTERFACE.get()
		) {
			textPage(
				text = bookText(
					"The **Advanced Redstone Interface** will transmit its own received redstone signal strength to up to 9 linked blocks.",
					"Insert ${internalLink(ItemsModonomiconCategory.locationFilter, "Location Filters")} to link it to those blocks."
				)
			)
		}


		advancedRedstoneTorch = redstoneCategory.entry(
			saveName = "advanced_redstone_torch",
			name = "Advanced Redstone Torch",
			icon = ModBlocks.ADVANCED_REDSTONE_TORCH.get()
		) {
			textPage(
				text = bookText(
					"The **Advanced Redstone Torch** has a configurable redstone signal strength for when its powered and unpowered."
				)
			)
		}


		analogEmitter = redstoneCategory.entry(
			saveName = "analog_emitter",
			name = "Analog Emitter",
			icon = ModBlocks.ANALOG_EMITTER.get()
		) {
			textPage(
				text = bookText(
					"The **Analog Emitter**, when powered from its front side, will emit a signal with a configurable strength.",
					"Right-click it to cycle the output strength."
				)
			)
		}


		basicRedstoneInterface = redstoneCategory.entry(
			saveName = "basic_redstone_interface",
			name = "Basic Redstone Interface",
			icon = ModBlocks.BASIC_REDSTONE_INTERFACE.get()
		) {
			textPage(
				text = bookText(
					"The **Basic Redstone Interface** will transmit its own received redstone signal strength to the block it's linked to.",
					"Use a ${internalLink(ItemsModonomiconCategory.redstoneTool, "Redstone Tool")} to link it to a block."
				)
			)
		}


		blockDestabilizer = redstoneCategory.entry(
			saveName = "block_destabilizer",
			name = "Block Destabilizer",
			icon = ModBlocks.BLOCK_DESTABILIZER.get()
		) {
			textPage(
				text = bookText(
					"The **Block Destabilizer** will cause the block in front of it, and all connected blocks of the same type, to fall like Sand when it receives a redstone pulse.",
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


		blockDetector = redstoneCategory.entry(
			saveName = "block_detector",
			name = "Block Detector",
			icon = ModBlocks.BLOCK_DETECTOR.get()
		) {
			textPage(
				text = bookText(
					"The **Block Detector** will emit a redstone signal when the block in front of it matches the block stored in its inventory."
				)
			)
		}


		chatDetector = redstoneCategory.entry(
			saveName = "chat_detector",
			name = "Chat Detector",
			icon = ModBlocks.CHAT_DETECTOR.get()
		) {
			textPage(
				text = bookText(
					"**Chat Detector**",
					"The **Chat Detector** emits a redstone pulse when the player that places it says a specific phrase in chat.",
					"Set the phrase in its GUI. It's actually a RegEx field, so you can do more advanced filters. Just typing in the exact phrase is enough, however."
				)
			)

			textPage(
				text = bookText(
					"You can also toggle if the message gets canceled or not.",
					"**Global Chat Detector**",
					"The **Global Chat Detector** works the same way, but listens to chat messages from every player instead of only its owner."
				)
			)

			textPage(
				text = bookText(
					"It can only cancel messages from its owner, however."
				)
			)

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(OtherUtil.modResource("chat_detector"))
					.withRecipeId2(OtherUtil.modResource("global_chat_detector"))
			})
		}


		contactButton = redstoneCategory.entry(
			saveName = "contact_button",
			name = "Contact Button",
			icon = ModBlocks.CONTACT_BUTTON.get()
		) {
			textPage(
				text = bookText(
					"The **Contact Button** works like a Button, but powers when the block it's aimed at is clicked, rather than when it itself is clicked."
				)
			)
		}


		contactLever = redstoneCategory.entry(
			saveName = "contact_lever",
			name = "Contact Lever",
			icon = ModBlocks.CONTACT_LEVER.get()
		) {
			textPage(
				text = bookText(
					"The **Contact Lever** works like a Lever, but toggles when the block it's aimed at is clicked, rather than when it itself is clicked."
				)
			)
		}


		entityDetector = redstoneCategory.entry(
			saveName = "entity_detector",
			name = "Entity Detector",
			icon = ModBlocks.ENTITY_DETECTOR.get()
		) {
			textPage(
				text = bookText(
					"The **Entity Detector** emits a redstone signal when an entity matching its filter is within its detection area.",
					"In its GUI you can set the radius in each dimension, if the signal is inverted or not, and specify what type of entity to detect."
				)
			)

			textPage(
				text = bookText(
					"You can choose to detect:\n- All Entities\n- Living Entities\n- Animals\n- Monsters\n- Players\n- Items\n- Custom",
					"When in Custom mode, it will use an inserted ${internalLink(ItemsModonomiconCategory.entityFilter, "Entity Filter")}."
				)
			)
		}


		inventoryTester = redstoneCategory.entry(
			saveName = "inventory_tester",
			name = "Inventory Tester",
			icon = ModBlocks.INVENTORY_TESTER.get()
		) {
			textPage(
				text = bookText(
					"The **Inventory Tester** must be placed on an inventory, and stores an item in itself.",
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


		ironDropper = redstoneCategory.entry(
			saveName = "iron_dropper",
			name = "Iron Dropper",
			icon = ModBlocks.IRON_DROPPER.get()
		) {
			textPage(
				text = bookText(
					"The **Iron Dropper** is an upgrade to the regular Dropper, with a handful of extra features.",
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


		lapisLamp = redstoneCategory.entry(
			saveName = "lapis_lamp",
			name = "Lapis Lamp",
			icon = ModBlocks.LAPIS_LAMP.get()
		) {
			textPage(
				text = bookText(
					"The **Lapis Lamp**, when provided a redstone signal, will give off false light.",
					"That is, mob spawning and other game logic will treat the area is if it were not lit, but players will see light."
				)
			)
		}


		moonPhaseDetector = redstoneCategory.entry(
			saveName = "moon_phase_detector",
			name = "Moon Phase Detector",
			icon = ModBlocks.MOON_PHASE_DETECTOR.get()
		) {
			textPage(
				text = bookText(
					"The **Moon Phase Detector** emits a redstone signal strength proportional to the phase of the moon.",
					"It emits a full signal during a full moon, and no signal during a new moon."
				)
			)

			textPage(
				text = bookText(
					"You can invert this by right-clicking it."
				)
			)
		}


		onlineDetector = redstoneCategory.entry(
			saveName = "online_detector",
			name = "Online Detector",
			icon = ModBlocks.ONLINE_DETECTOR.get()
		) {
			textPage(
				text = bookText(
					"The **Online Detector** emits a redstone signal when the chosen player is logged onto the server. Set the player by typing their username into its GUI."
				)
			)
		}


		quartzLamp = redstoneCategory.entry(
			saveName = "quartz_lamp",
			name = "Quartz Lamp",
			icon = ModBlocks.QUARTZ_LAMP.get()
		) {
			textPage(
				text = bookText(
					"The **Quartz Lamp**, when provided a redstone signal, will give off light that cannot be seen.",
					"That is, mob spawning and other game logic will treat the area is if it were lit, but players will not see any light."
				)
			)
		}


		redstoneObserver = redstoneCategory.entry(
			saveName = "redstone_observer",
			name = "Redstone Observer",
			icon = ModBlocks.REDSTONE_OBSERVER.get()
		) {
			textPage(
				text = bookText(
					"The **Redstone Observer** gives off the same redstone signal output as the block it's linked to.",
					"Use a ${internalLink(ItemsModonomiconCategory.redstoneTool, "Redstone Tool")} to link it to a block."
				)
			)
		}


		sidedRedstone = redstoneCategory.entry(
			saveName = "sided_redstone",
			name = "Sided Block of Redstone",
			icon = ModBlocks.SIDED_BLOCK_OF_REDSTONE.get()
		) {
			textPage(
				text = bookText(
					"The **Sided Block of Redstone** emits a full signal on one face, and nothing on the others."
				)
			)
		}


		triggerGlass = redstoneCategory.entry(
			saveName = "trigger_glass",
			name = "Trigger Glass",
			icon = ModBlocks.TRIGGER_GLASS.get()
		) {
			textPage(
				text = bookText(
					"**Trigger Glass** is usually solid, but temporarily becomes non-solid when it receives a redstone pulse.",
					"This effect propagates to all adjacent Trigger Glass blocks, within a distance."
				)
			)
		}


		sakanadeSpores = bookCategory.entry(
			saveName = "sakanade_spores",
			name = "Sakanade Spores",
			icon = ModBlocks.SAKANADE_SPORES.get()
		) {
			textPage(
				text = bookText(
					"**Sakanade Spores** can be found on the bottom of Large Brown Mushroom caps. When touched, Sakanade Spores will inflict the Collapse effect to entities.",
					"For players, the Collapse effect inverts their movement and mouse controls."
				)
			)

			textPage(
				text = bookText(
					"For mobs, it instead confuses their pathfinding, causing them to move erratically."
				)
			)
		}


		shockAbsorber = bookCategory.entry(
			saveName = "shock_absorber",
			name = "Shock Absorber",
			icon = ModBlocks.SHOCK_ABSORBER.get()
		) {
			textPage(
				text = bookText(
					"The **Shock Absorber** negates all fall damage when landed on.",
					"Additionally, it will emit a redstone signal proportional the the distance fallen."
				)
			)
		}


		slimeCube = bookCategory.entry(
			saveName = "slime_cube",
			name = "Slime Cube",
			icon = ModBlocks.SLIME_CUBE.get()
		) {
			textPage(
				text = bookText(
					"The **Slime Cube**, when unpowered, causes Slimes to spawn in a radius around it. By default, this radius is 1 chunk (so a 3x3 chunk area centered on itself).",
					"When powered, it instead prevents Slimes from spawning in that same area."
				)
			)
		}


		spectreCoils = bookCategory.entry(
			saveName = "spectre_coils",
			name = "Spectre Coils",
			icon = ModBlocks.SPECTRE_COIL_BASIC.get()
		) {
			textPage(
				text = bookText(
					"**Regular Coils**",
					"**Spectre Coils** allow you to extract energy from your ${internalLink(spectreEnergyInjector, "Spectre Energy Buffer")} wirelessly.",
					"Each tier pulls a higher RF/t. Place the Coil directly on the machine you want to charge."
				)
			)

			textPage(
				text = bookText(
					"**Generator Coils**",
					"The **Spectre Coil Nr. 245** generates a small amount of RF/t for free, rather than using your Spectre Energy Buffer. It can be rarely found in dungeon chests.",
					"The **Genesis Spectre Coil** is creative-only, and generates an infinite amount of RF/t."
				)
			)

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(OtherUtil.modResource("spectre_coil_basic"))
					.withRecipeId2(OtherUtil.modResource("spectre_coil_redstone"))
			})

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(OtherUtil.modResource("spectre_coil_ender"))
			})
		}


		spectreEnergyInjector = bookCategory.entry(
			saveName = "spectre_energy_injector",
			name = "Spectre Energy Injector",
			icon = ModBlocks.SPECTRE_ENERGY_INJECTOR.get()
		) {
			textPage(
				text = bookText(
					"Every player has their own Spectre Energy Buffer, which acts sort of like an Ender Chest for FE. By default, it can store 1,000,000 FE.",
					"Energy can be inserted into a player's Buffer via the **Spectre Energy Injector**. Whoever placed the Injector is whose Buffer gets filled."
				)
			)

			textPage(
				text = bookText(
					"You cannot extract from the Injector. Instead, you'll have to use either a Spectre Coil or a ${internalLink(ItemsModonomiconCategory.spectreCharger, "Spectre Charger")}."
				)
			)
		}


		spectreLens = bookCategory.entry(
			saveName = "spectre_lens",
			name = "Spectre Lens",
			icon = ModBlocks.SPECTRE_LENS.get()
		) {
			textPage(
				text = bookText(
					"The **Spectre Lens** can be placed on a Beacon to allow it to effect you from any distance, as long as you're in the same dimension.",
					"It only effects the player who placed the Lens."
				)
			)

			textPage(
				text = bookText(
					"Naturally, it only works while the chunk it's in is loaded."
				)
			)
		}


		spectreSapling = bookCategory.entry(
			saveName = "spectre_sapling",
			name = "Spectre Sapling",
			icon = ModBlocks.SPECTRE_SAPLING.get()
		) {
			textPage(
				text = bookText(
					"**Spectre Saplings** grow into a translucent Spectre trees.",
					"**Spectre Leaves** have a chance of dropping ${internalLink(ItemsModonomiconCategory.ectoplasm, "Ectoplasm")}, allowing it to be farmed."
				)
			)

			textPage(
				text = bookText(
					"Create a Spectre Sapling by using Ectoplasm on any other Sapling."
				)
			)
		}


		superLubricatedBlocks = bookCategory.entry(
			saveName = "super_lubricated_blocks",
			name = "Super Lubricated Blocks",
			icon = BuiltInRegistries.ITEM.get(OtherUtil.modResource("super_lubricant_stone"))
		) {
			textPage(
				text = bookText(
					"**Super Lubricant Stone** and **Super Lubricant Ice** fully negate friction, allowing entities to slide over them without slowing down."
				)
			)

			customPage(factory = { _, _ ->
				BookCraftingRecipePageModel.create()
					.withRecipeId1(OtherUtil.modResource("super_lubricant_stone"))
					.withRecipeId2(OtherUtil.modResource("super_lubricant_ice"))
			})
		}

	}
}