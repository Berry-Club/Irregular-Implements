package dev.aaronhowser.mods.irregular_implements.config

import net.neoforged.neoforge.common.ModConfigSpec
import org.apache.commons.lang3.tuple.Pair

class ServerConfig(
    private val builder: ModConfigSpec.Builder
) {

    companion object {
        private val configPair: Pair<ServerConfig, ModConfigSpec> = ModConfigSpec.Builder().configure(::ServerConfig)

        val CONFIG: ServerConfig = configPair.left
        val CONFIG_SPEC: ModConfigSpec = configPair.right

        lateinit var BLOCK_DESTABILIZER_LIMIT: ModConfigSpec.IntValue

        lateinit var BIOME_PAINTER_HORIZONTAL_RADIUS: ModConfigSpec.IntValue
        lateinit var BIOME_PAINTER_BLOCKS_BELOW: ModConfigSpec.IntValue
        lateinit var BIOME_PAINTER_BLOCKS_ABOVE: ModConfigSpec.IntValue

        lateinit var BLOCK_MOVER_TRY_VAPORIZE_FLUID: ModConfigSpec.BooleanValue

        lateinit var SPECTRE_IMBUE_CHANCE: ModConfigSpec.DoubleValue

        lateinit var PORTABLE_ENDER_BRIDGE_RANGE: ModConfigSpec.IntValue

        lateinit var SUMMONING_PENDULUM_CAPACITY: ModConfigSpec.IntValue
        lateinit var BLOCK_REPLACER_UNIQUE_BLOCKS: ModConfigSpec.IntValue

        lateinit var DIVINING_ROD_CHECK_RADIUS: ModConfigSpec.IntValue

        lateinit var SPECTRE_MAX_ENERGY: ModConfigSpec.IntValue
        lateinit var SPECTRE_BASIC_RATE: ModConfigSpec.IntValue
        lateinit var SPECTRE_REDSTONE_RATE: ModConfigSpec.IntValue
        lateinit var SPECTRE_ENDER_RATE: ModConfigSpec.IntValue
        lateinit var SPECTRE_NUMBER_RATE: ModConfigSpec.IntValue
        lateinit var SPECTRE_GENESIS_RATE: ModConfigSpec.IntValue

    }

    init {
        basicServerConfigs()
        spectreConfigs()

        builder.build()
    }

    private fun spectreConfigs() {
        builder.push("spectre")

        SPECTRE_MAX_ENERGY = builder
            .comment("What is the maximum energy that a Spectre Energy network can store?")
            .defineInRange("maxEnergy", 1_000_000, 1, Int.MAX_VALUE)

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

        SPECTRE_IMBUE_CHANCE = builder
            .comment("What is the chance that the Spectre Imbue will cancel incoming damage?")
            .defineInRange("imbueProcChance", 0.1, 0.0, 1.0)

        builder.pop()
    }

    private fun basicServerConfigs() {
        BLOCK_DESTABILIZER_LIMIT = builder
            .comment("How many blocks should the Block Destabilizer be able to drop?")
            .defineInRange("blockDestabilizerLimit", 50, 1, Int.MAX_VALUE)

        BIOME_PAINTER_HORIZONTAL_RADIUS = builder
            .comment("What should be the horizontal radius of the Biome Painter?")
            .defineInRange("biomePainterHorizontalRadius", 0, 0, Int.MAX_VALUE)

        BIOME_PAINTER_BLOCKS_BELOW = builder
            .comment("How many blocks below the clicked block should the Biome Painter change?")
            .defineInRange("biomePainterBlocksBelow", 0, 0, Int.MAX_VALUE)

        BIOME_PAINTER_BLOCKS_ABOVE = builder
            .comment("How many blocks above the clicked block should the Biome Painter change?")
            .defineInRange("biomePainterBlocksAbove", 0, 0, Int.MAX_VALUE)

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
    }

}