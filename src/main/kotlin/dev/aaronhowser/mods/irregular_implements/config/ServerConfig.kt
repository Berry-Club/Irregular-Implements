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
    }

    init {
        serverConfigs()

        builder.build()
    }

    private fun serverConfigs() {
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

        SPECTRE_IMBUE_CHANCE = builder
            .comment("What is the chance that the Spectre Imbue will cancel incoming damage?")
            .defineInRange("spectreImbueChance", 0.1, 0.0, 1.0)

        PORTABLE_ENDER_BRIDGE_RANGE = builder
            .comment("How far should the Portable Ender Bridge be able to look for an Ender Anchor?")
            .defineInRange("portableEnderBridgeRange", 300, 1, Int.MAX_VALUE)

        SUMMONING_PENDULUM_CAPACITY = builder
            .comment("How many entities should the Summoning Pendulum be able to store?")
            .defineInRange("summoningPendulumCapacity", 5, 1, Int.MAX_VALUE)
    }

}