package dev.aaronhowser.mods.irregular_implements.config

import net.neoforged.neoforge.common.ModConfigSpec
import org.apache.commons.lang3.tuple.Pair

class ServerConfig(
	private val builder: ModConfigSpec.Builder
) {

	lateinit var blockDestabilizerLimit: ModConfigSpec.IntValue

	lateinit var biomePainterRadius: ModConfigSpec.IntValue
	lateinit var biomePainterViewHorizontalRadius: ModConfigSpec.IntValue
	lateinit var biomePainterViewVerticalRadius: ModConfigSpec.IntValue

	lateinit var flooNameMaxDistance: ModConfigSpec.IntValue
	lateinit var blockMoverTryVaporizeFluid: ModConfigSpec.BooleanValue
	lateinit var portableEnderBridgeRange: ModConfigSpec.IntValue
	lateinit var summoningPendulumCapacity: ModConfigSpec.IntValue
	lateinit var blockReplacerUniqueBlocks: ModConfigSpec.IntValue
	lateinit var diviningRodCheckRadius: ModConfigSpec.IntValue
	lateinit var rainShieldChunkRadius: ModConfigSpec.IntValue
	lateinit var slimeCubeChunkRadius: ModConfigSpec.IntValue
	lateinit var peaceCandleChunkRadius: ModConfigSpec.IntValue

	lateinit var natureCoreReplaceSandRadius: ModConfigSpec.IntValue
	lateinit var natureCorePlantSaplingRadius: ModConfigSpec.IntValue
	lateinit var natureCoreBoneMealCropRadius: ModConfigSpec.IntValue
	lateinit var natureCoreSpawnAnimalRadius: ModConfigSpec.DoubleValue

	lateinit var spectreImbueChance: ModConfigSpec.DoubleValue

	lateinit var spectreBufferCapacity: ModConfigSpec.IntValue
	lateinit var spectreBasicRate: ModConfigSpec.IntValue
	lateinit var spectreRedstoneRate: ModConfigSpec.IntValue
	lateinit var spectreEnderRate: ModConfigSpec.IntValue
	lateinit var spectreNumberRate: ModConfigSpec.IntValue
	lateinit var spectreGenesisRate: ModConfigSpec.IntValue

	lateinit var spectreChargerBasic: ModConfigSpec.IntValue
	lateinit var specterChargerRedstone: ModConfigSpec.IntValue
	lateinit var spectreChargerEnder: ModConfigSpec.IntValue
	lateinit var spectreChargerGenesis: ModConfigSpec.IntValue

	lateinit var escapeRopeMaxBlocks: ModConfigSpec.IntValue
	lateinit var escapeRopeBlocksPerTick: ModConfigSpec.IntValue

	lateinit var blockTeleporterCrossDimension: ModConfigSpec.BooleanValue

	lateinit var spiritMaxAge: ModConfigSpec.IntValue
	lateinit var spiritBaseSpawnChance: ModConfigSpec.DoubleValue
	lateinit var spiritSpawnChanceDragonKilledBonus: ModConfigSpec.DoubleValue
	lateinit var spiritSpawnFullMoonBonus: ModConfigSpec.DoubleValue

	lateinit var pitcherPlantTickFillAmount: ModConfigSpec.IntValue
	lateinit var pitcherPlantBoneMealFillAmount: ModConfigSpec.IntValue
	lateinit var pitcherPlantUseFillAmount: ModConfigSpec.IntValue
	lateinit var pitcherPlantPipeDrainRate: ModConfigSpec.IntValue

	lateinit var biomeRadarSearchRadius: ModConfigSpec.IntValue
	lateinit var biomeRadarHorizontalStep: ModConfigSpec.IntValue
	lateinit var biomeRadarVerticalStep: ModConfigSpec.IntValue

	lateinit var triggerGlassRange: ModConfigSpec.IntValue
	lateinit var triggerGlassDuration: ModConfigSpec.IntValue

	init {
		basicServerConfigs()
		biomePainter()
		spectreConfigs()
		biomeRadar()
		triggerGlass()

		builder.build()
	}

	private fun triggerGlass() {
		builder.push(TRIGGER_GLASS_CATEGORY)

		triggerGlassRange = builder
			.comment("How far should the Trigger Glass effect propagate? (in blocks)")
			.defineInRange("triggerGlassRange", 20, 1, Int.MAX_VALUE)

		triggerGlassDuration = builder
			.comment("How long should the Trigger Glass remain non-solid? (in ticks)")
			.defineInRange("triggerGlassDuration", 20 * 3, 1, Int.MAX_VALUE)

		builder.pop()
	}

	private fun basicServerConfigs() {
		blockDestabilizerLimit = builder
			.comment("How many blocks should the Block Destabilizer be able to drop?")
			.defineInRange("blockDestabilizerLimit", 50, 1, Int.MAX_VALUE)

		flooNameMaxDistance = builder
			.comment("What is the maximum Levenshtein distance allowed when searching for a Floo Fireplace by name?")
			.defineInRange("flooNameMaxDistance", 2, 0, Int.MAX_VALUE)

		blockMoverTryVaporizeFluid = builder
			.comment("Should the Block Mover try to vaporize fluids (un-water-logging a slab when moved to the Nether, etc), or should it just refuse to move the block?")
			.define("blockMoverTryVaporizeFluid", true)

		portableEnderBridgeRange = builder
			.comment("How far should the Portable Ender Bridge be able to look for an Ender Anchor?")
			.defineInRange("portableEnderBridgeRange", 300, 1, Int.MAX_VALUE)

		summoningPendulumCapacity = builder
			.comment("How many entities should the Summoning Pendulum be able to store?")
			.defineInRange("summoningPendulumCapacity", 5, 1, Int.MAX_VALUE)

		blockReplacerUniqueBlocks = builder
			.comment("How many unique blocks should the Block Replacer be able to store?")
			.defineInRange("blockReplacerUniqueBlocks", 9, 1, Int.MAX_VALUE)

		diviningRodCheckRadius = builder
			.comment("The radius around the player to check for blocks with the Divining Rod")
			.defineInRange("diviningRodCheckRadius", 20, 1, 100)

		rainShieldChunkRadius = builder
			.comment("What chunk radius should the Rain Shield have? (0 means only the chunk the Rain Shield is in)")
			.defineInRange("rainShieldChunkRadius", 5, 0, Int.MAX_VALUE)

		slimeCubeChunkRadius = builder
			.comment("What chunk radius should the Slime Cube have? (0 means only the chunk the Slime Cube is in)")
			.defineInRange("slimeCubeChunkRadius", 0, 0, Int.MAX_VALUE)

		peaceCandleChunkRadius = builder
			.comment("What chunk radius should the Peace Candle have? (0 means only the chunk the Peace Candle is in)")
			.defineInRange("peaceCandleChunkRadius", 1, 0, Int.MAX_VALUE)

		natureCoreReplaceSandRadius = builder
			.comment("What radius should the Nature Core replace blocks in?")
			.defineInRange("natureCoreReplaceRadius", 10, 1, Int.MAX_VALUE)

		natureCorePlantSaplingRadius = builder
			.comment("What is the maximum radius that the Nature Core can plant saplings in?")
			.defineInRange("natureCorePlantSaplingRadius", 16, 1, Int.MAX_VALUE)

		natureCoreBoneMealCropRadius = builder
			.comment("What radius should the Nature Core apply bone meal to crops in?")
			.defineInRange("natureCoreBoneMealCropRadius", 25, 1, Int.MAX_VALUE)

		natureCoreSpawnAnimalRadius = builder
			.comment("What radius should the Nature Core spawn animals in?")
			.defineInRange("natureCoreSpawnAnimalRadius", 8.0, 1.0, 128.0)

		escapeRopeMaxBlocks = builder
			.comment("How many blocks should the Escape Rope check before giving up?\n\n0 means no limit")
			.defineInRange("escapeRopeMaxBlocks", 0, 0, Int.MAX_VALUE)

		escapeRopeBlocksPerTick = builder
			.comment("How many blocks should the Escape Rope check each tick?")
			.defineInRange("escapeRopeBlocksPerTick", 250, 1, Int.MAX_VALUE)

		blockTeleporterCrossDimension = builder
			.comment("Should the Block Teleporter be able to teleport blocks across dimensions?")
			.define("blockTeleporterCrossDimension", false)

		pitcherPlantTickFillAmount = builder
			.comment("How much fluid should the Pitcher Plant fill adjacent tanks on a random tick?")
			.defineInRange("pitcherPlantTickFillAmount", 1000 * 100, 0, Int.MAX_VALUE)

		pitcherPlantBoneMealFillAmount = builder
			.comment("How much fluid should the Pitcher Plant fill adjacent tanks when bone mealed?")
			.defineInRange("pitcherPlantBoneMealFillAmount", 1000 * 100, 0, Int.MAX_VALUE)

		pitcherPlantUseFillAmount = builder
			.comment("How much fluid should the Pitcher Plant fill a container clicked onto it?")
			.defineInRange("pitcherPlantUseFillAmount", 1000 * 100, 1, Int.MAX_VALUE)

		pitcherPlantPipeDrainRate = builder
			.comment("How much fluid should the Pitcher Plant allow to be drained by pipes per tick?")
			.defineInRange("pitcherPlantPipeDrainRate", 1000 * 100, 1, Int.MAX_VALUE)
	}

	private fun spectreConfigs() {
		builder.push(SPECTRE_CATEGORY)

		spectreBufferCapacity = builder
			.comment("What is the maximum energy that a Spectre Energy network can store?")
			.defineInRange("capacity", 1_000_000, 1, Int.MAX_VALUE)

		spectreBasicRate = builder
			.comment("How much energy should a Basic Spectre Coil transfer per tick?")
			.defineInRange("basicRate", 1024, 1, Int.MAX_VALUE)

		spectreRedstoneRate = builder
			.comment("How much energy should a Redstone Spectre Coil transfer per tick?")
			.defineInRange("redstoneRate", 4096, 1, Int.MAX_VALUE)

		spectreEnderRate = builder
			.comment("How much energy should an Ender Spectre Coil transfer per tick?")
			.defineInRange("enderRate", 20480, 1, Int.MAX_VALUE)

		spectreNumberRate = builder
			.comment("How much energy should a Number Spectre Coil generate per tick?")
			.defineInRange("numberRate", 128, 1, Int.MAX_VALUE)

		spectreGenesisRate = builder
			.comment("How much energy should a Genesis Spectre Coil generate per tick?")
			.defineInRange("genesisRate", 10_000_000, 1, Int.MAX_VALUE)

		spectreChargerBasic = builder
			.comment("How fast should the Basic Spectre Charger charge items?")
			.defineInRange("chargerBasic", 1024, 1, Int.MAX_VALUE)

		specterChargerRedstone = builder
			.comment("How fast should the Redstone Spectre Charger charge items?")
			.defineInRange("chargerRedstone", 4096, 1, Int.MAX_VALUE)

		spectreChargerEnder = builder
			.comment("How fast should the Ender Spectre Charger charge items?")
			.defineInRange("chargerEnder", 20480, 1, Int.MAX_VALUE)

		spectreChargerGenesis = builder
			.comment("How fast should the Genesis Spectre Charger charge items?")
			.defineInRange("chargerGenesis", Int.MAX_VALUE, 1, Int.MAX_VALUE)

		spectreImbueChance = builder
			.comment("What is the chance that the Spectre Imbue will cancel incoming damage?")
			.defineInRange("imbueProcChance", 0.1, 0.0, 1.0)

		spiritMaxAge = builder
			.comment("How long should a Spirit last before it despawns? (in ticks)")
			.defineInRange("spiritMaxAge", 20 * 20, 1, Int.MAX_VALUE)

		spiritBaseSpawnChance = builder
			.comment("What is the base chance of a Spirit spawning when an entity dies?")
			.defineInRange("spiritBaseSpawnChance", 0.01, 0.0, 1.0)

		spiritSpawnChanceDragonKilledBonus = builder
			.comment("How much is the Spirit spawn chance increased if the Ender Dragon has been killed?")
			.defineInRange("spiritSpawnDragonKilledBonus", 0.07, 0.0, 1.0)

		spiritSpawnFullMoonBonus = builder
			.comment("How much is the Spirit spawn chance increased if it's a full moon?")
			.defineInRange("spiritSpawnFullMoonBonus", 0.02, 0.0, 1.0)

		builder.pop()
	}

	private fun biomePainter() {
		builder.push(BIOME_PAINTER_CATEGORY)

		biomePainterRadius = builder
			.comment("The radius that the Biome Painter will affect. 0 is just the targeted block, 1 is a 3x3x3 area, etc.")
			.defineInRange("biomePainterRadius", 2, 0, 100)

		biomePainterViewHorizontalRadius = builder
			.comment("The horizontal that you can see and paint biomes from.")
			.defineInRange("biomePainterViewHorizontalRadius", 10, 1, 100)

		biomePainterViewVerticalRadius = builder
			.comment("The vertical that you can see and paint biomes from.")
			.defineInRange("biomePainterViewVerticalRadius", 5, 1, 100)

		builder.pop()
	}

	private fun biomeRadar() {
		builder.push(BIOME_RADAR_CATEGORY)

		biomeRadarSearchRadius = builder
			.comment("The radius that the Biome Radar will search for the selected biome.")
			.defineInRange("biomeRadarSearchRadius", 6400, 1, 10000)

		biomeRadarHorizontalStep = builder
			.comment("The horizontal step size that the Biome Radar will use when searching.")
			.defineInRange("biomeRadarHorizontalStep", 32, 1, 100)

		biomeRadarVerticalStep = builder
			.comment("The vertical step size that the Biome Radar will use when searching.")
			.defineInRange("biomeRadarVerticalStep", 64, 1, 100)

		builder.pop()
	}

	companion object {
		private val configPair: Pair<ServerConfig, ModConfigSpec> = ModConfigSpec.Builder().configure(::ServerConfig)

		val CONFIG: ServerConfig = configPair.left
		val CONFIG_SPEC: ModConfigSpec = configPair.right

		const val SPECTRE_CATEGORY = "spectre"
		const val BIOME_PAINTER_CATEGORY = "biome_painter"
		const val BIOME_RADAR_CATEGORY = "biome_radar"
		const val TRIGGER_GLASS_CATEGORY = "trigger_glass"
	}

}