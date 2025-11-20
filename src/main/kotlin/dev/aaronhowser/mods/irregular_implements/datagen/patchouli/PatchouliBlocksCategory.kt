package dev.aaronhowser.mods.irregular_implements.datagen.patchouli

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModItemTagsProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBook
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookCategory
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookElement
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookEntry
import dev.aaronhowser.mods.patchoulidatagen.page.AbstractPage
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.SpotlightPage
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.TextPage
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider.Companion.BR
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider.Companion.ITALIC
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider.Companion.RESET
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider.Companion.doubleSpacedLines
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider.Companion.internalLink
import net.minecraft.world.level.block.Blocks
import net.neoforged.neoforge.registries.DeferredBlock
import java.util.function.Consumer

object PatchouliBlocksCategory {

	fun blocks(consumer: Consumer<PatchouliBookElement>, book: PatchouliBook) {
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
			ModPatchouliBookProvider.spotlight(
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
				ModPatchouliBookProvider.dottedLines(
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
			ModPatchouliBookProvider.spotlight(
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
			ModPatchouliBookProvider.spotlight(
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
			ModPatchouliBookProvider.spotlight(
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
			ModPatchouliBookProvider.spotlight(
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
						ModPatchouliBookProvider.dottedLines(
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
	}

	private fun major(text: String) = ModPatchouliBookProvider.major(text)
	private fun minor(text: String) = ModPatchouliBookProvider.minor(text)
	private fun good(text: String) = ModPatchouliBookProvider.good(text)
	private fun bad(text: String) = ModPatchouliBookProvider.bad(text)
	
}