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
    }

}