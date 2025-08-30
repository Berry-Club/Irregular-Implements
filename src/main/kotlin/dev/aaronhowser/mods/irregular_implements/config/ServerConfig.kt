package dev.aaronhowser.mods.irregular_implements.config

import net.neoforged.neoforge.common.ModConfigSpec
import org.apache.commons.lang3.tuple.Pair

class ServerConfig(
	private val builder: ModConfigSpec.Builder
) {

	init {
		basicServerConfigs()
		biomePainter()
		spectreConfigs()

		builder.build()
	}

	private fun basicServerConfigs() {
		BLOCK_DESTABILIZER_LIMIT = builder
			.comment("How many blocks should the Block Destabilizer be able to drop?")
			.defineInRange("blockDestabilizerLimit", 50, 1, Int.MAX_VALUE)

		BLOCK_MOVER_TRY_VAPORIZE_FLUID = builder
			.comment("Should the Block Mover try to vaporize fluids (un-water-logging a slab when moved to the Nether, etc), or should it just refuse to move the block?")
			.define("blockMoverTryVaporizeFluid", true)

		PORTABLE_ENDER_BRIDGE_RANGE = builder
			.comment("How far should the Portable Ender Bridge be able to look for an Ender Anchor?")
			.defineInRange("portableEnderBridgeRange", 300, 1, Int.MAX_VALUE)

		SUMMONING_PENDULUM_CAPACITY = builder
			.comment("How many entities should the Summoning Pendulum be able to store?")
			.defineInRange("summoningPendulumCapacity", 5, 1, Int.MAX_VALUE)

		BLOCK_REPLACER_UNIQUE_BLOCKS = builder
			.comment("How many unique blocks should the Block Replacer be able to store?")
			.defineInRange("blockReplacerUniqueBlocks", 9, 1, Int.MAX_VALUE)

		DIVINING_ROD_CHECK_RADIUS = builder
			.comment("The radius around the player to check for blocks with the Divining Rod")
			.defineInRange("diviningRodCheckRadius", 20, 1, 100)

		RAIN_SHIELD_CHUNK_RADIUS = builder
			.comment("What chunk radius should the Rain Shield have? (0 means only the chunk the Rain Shield is in)")
			.defineInRange("rainShieldChunkRadius", 5, 0, Int.MAX_VALUE)

		PEACE_CANDLE_CHUNK_RADIUS = builder
			.comment("What chunk radius should the Peace Candle have? (0 means only the chunk the Peace Candle is in)")
			.defineInRange("peaceCandleChunkRadius", 1, 0, Int.MAX_VALUE)

		ESCAPE_ROPE_MAX_BLOCKS = builder
			.comment("How many blocks should the Escape Rope check before giving up?\n\n0 means no limit")
			.defineInRange("escapeRopeMaxBlocks", 0, 0, Int.MAX_VALUE)

		ESCAPE_ROPE_BLOCKS_PER_TICK = builder
			.comment("How many blocks should the Escape Rope check each tick?")
			.defineInRange("escapeRopeBlocksPerTick", 250, 1, Int.MAX_VALUE)

		BLOCK_TELEPORTER_CROSS_DIMENSION = builder
			.comment("Should the Block Teleporter be able to teleport blocks across dimensions?")
			.define("blockTeleporterCrossDimension", false)
	}

	private fun spectreConfigs() {
		builder.push(SPECTRE_CATEGORY)

		SPECTRE_BUFFER_CAPACITY = builder
			.comment("What is the maximum energy that a Spectre Energy network can store?")
			.defineInRange("capacity", 1_000_000, 1, Int.MAX_VALUE)

		SPECTRE_BASIC_RATE = builder
			.comment("How much energy should a Basic Spectre Coil transfer per tick?")
			.defineInRange("basicRate", 1024, 1, Int.MAX_VALUE)

		SPECTRE_REDSTONE_RATE = builder
			.comment("How much energy should a Redstone Spectre Coil transfer per tick?")
			.defineInRange("redstoneRate", 4096, 1, Int.MAX_VALUE)

		SPECTRE_ENDER_RATE = builder
			.comment("How much energy should an Ender Spectre Coil transfer per tick?")
			.defineInRange("enderRate", 20480, 1, Int.MAX_VALUE)

		SPECTRE_NUMBER_RATE = builder
			.comment("How much energy should a Number Spectre Coil generate per tick?")
			.defineInRange("numberRate", 128, 1, Int.MAX_VALUE)

		SPECTRE_GENESIS_RATE = builder
			.comment("How much energy should a Genesis Spectre Coil generate per tick?")
			.defineInRange("genesisRate", 10_000_000, 1, Int.MAX_VALUE)

		SPECTRE_CHARGER_BASIC = builder
			.comment("How fast should the Basic Spectre Charger charge items?")
			.defineInRange("chargerBasic", 1024, 1, Int.MAX_VALUE)

		SPECTRE_CHARGER_REDSTONE = builder
			.comment("How fast should the Redstone Spectre Charger charge items?")
			.defineInRange("chargerRedstone", 4096, 1, Int.MAX_VALUE)

		SPECTRE_CHARGER_ENDER = builder
			.comment("How fast should the Ender Spectre Charger charge items?")
			.defineInRange("chargerEnder", 20480, 1, Int.MAX_VALUE)

		SPECTRE_CHARGER_GENESIS = builder
			.comment("How fast should the Genesis Spectre Charger charge items?")
			.defineInRange("chargerGenesis", Int.MAX_VALUE, 1, Int.MAX_VALUE)

		SPECTRE_IMBUE_CHANCE = builder
			.comment("What is the chance that the Spectre Imbue will cancel incoming damage?")
			.defineInRange("imbueProcChance", 0.1, 0.0, 1.0)

		SPIRIT_MAX_AGE = builder
			.comment("How long should a Spirit last before it despawns? (in ticks)")
			.defineInRange("spiritMaxAge", 20 * 20, 1, Int.MAX_VALUE)

		SPIRIT_BASE_SPAWN_CHANCE = builder
			.comment("What is the base chance of a Spirit spawning when an entity dies?")
			.defineInRange("spiritBaseSpawnChance", 0.01, 0.0, 1.0)

		SPIRIT_SPAWN_DRAGON_KILLED_BONUS = builder
			.comment("How much is the Spirit spawn chance increased if the Ender Dragon has been killed?")
			.defineInRange("spiritSpawnDragonKilledBonus", 0.07, 0.0, 1.0)

		SPIRIT_SPAWN_FULL_MOON_BONUS = builder
			.comment("How much is the Spirit spawn chance increased if it's a full moon?")
			.defineInRange("spiritSpawnFullMoonBonus", 0.02, 0.0, 1.0)

		builder.pop()
	}

	private fun biomePainter() {
		builder.push(BIOME_PAINTER_CATEGORY)

		BIOME_PAINTER_RADIUS = builder
			.comment("The radius that the Biome Painter will affect. 0 is just the targeted block, 1 is a 3x3x3 area, etc.")
			.defineInRange("biomePainterRadius", 0, 0, 100)

		BIOME_PAINTER_VIEW_HORIZONTAL_RADIUS = builder
			.comment("The horizontal that you can see and paint biomes from.")
			.defineInRange("biomePainterViewHorizontalRadius", 10, 1, 100)

		BIOME_PAINTER_VIEW_VERTICAL_RADIUS = builder
			.comment("The vertical that you can see and paint biomes from.")
			.defineInRange("biomePainterViewVerticalRadius", 5, 1, 100)

		builder.pop()
	}

	companion object {
		private val configPair: Pair<ServerConfig, ModConfigSpec> = ModConfigSpec.Builder().configure(::ServerConfig)

		val CONFIG: ServerConfig = configPair.left
		val CONFIG_SPEC: ModConfigSpec = configPair.right

		lateinit var BLOCK_DESTABILIZER_LIMIT: ModConfigSpec.IntValue

		lateinit var BIOME_PAINTER_RADIUS: ModConfigSpec.IntValue
		lateinit var BIOME_PAINTER_VIEW_HORIZONTAL_RADIUS: ModConfigSpec.IntValue
		lateinit var BIOME_PAINTER_VIEW_VERTICAL_RADIUS: ModConfigSpec.IntValue

		lateinit var BLOCK_MOVER_TRY_VAPORIZE_FLUID: ModConfigSpec.BooleanValue
		lateinit var PORTABLE_ENDER_BRIDGE_RANGE: ModConfigSpec.IntValue
		lateinit var SUMMONING_PENDULUM_CAPACITY: ModConfigSpec.IntValue
		lateinit var BLOCK_REPLACER_UNIQUE_BLOCKS: ModConfigSpec.IntValue
		lateinit var DIVINING_ROD_CHECK_RADIUS: ModConfigSpec.IntValue
		lateinit var RAIN_SHIELD_CHUNK_RADIUS: ModConfigSpec.IntValue
		lateinit var PEACE_CANDLE_CHUNK_RADIUS: ModConfigSpec.IntValue

		lateinit var SPECTRE_IMBUE_CHANCE: ModConfigSpec.DoubleValue

		lateinit var SPECTRE_BUFFER_CAPACITY: ModConfigSpec.IntValue
		lateinit var SPECTRE_BASIC_RATE: ModConfigSpec.IntValue
		lateinit var SPECTRE_REDSTONE_RATE: ModConfigSpec.IntValue
		lateinit var SPECTRE_ENDER_RATE: ModConfigSpec.IntValue
		lateinit var SPECTRE_NUMBER_RATE: ModConfigSpec.IntValue
		lateinit var SPECTRE_GENESIS_RATE: ModConfigSpec.IntValue

		lateinit var SPECTRE_CHARGER_BASIC: ModConfigSpec.IntValue
		lateinit var SPECTRE_CHARGER_REDSTONE: ModConfigSpec.IntValue
		lateinit var SPECTRE_CHARGER_ENDER: ModConfigSpec.IntValue
		lateinit var SPECTRE_CHARGER_GENESIS: ModConfigSpec.IntValue

		const val SPECTRE_CATEGORY = "spectre"
		const val BIOME_PAINTER_CATEGORY = "biome_painter"

		lateinit var ESCAPE_ROPE_MAX_BLOCKS: ModConfigSpec.IntValue
		lateinit var ESCAPE_ROPE_BLOCKS_PER_TICK: ModConfigSpec.IntValue

		lateinit var BLOCK_TELEPORTER_CROSS_DIMENSION: ModConfigSpec.BooleanValue

		lateinit var SPIRIT_MAX_AGE: ModConfigSpec.IntValue
		lateinit var SPIRIT_BASE_SPAWN_CHANCE: ModConfigSpec.DoubleValue
		lateinit var SPIRIT_SPAWN_DRAGON_KILLED_BONUS: ModConfigSpec.DoubleValue
		lateinit var SPIRIT_SPAWN_FULL_MOON_BONUS: ModConfigSpec.DoubleValue
	}

}