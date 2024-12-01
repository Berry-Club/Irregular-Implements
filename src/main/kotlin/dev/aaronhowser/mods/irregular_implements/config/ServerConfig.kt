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
    }

    init {
        serverConfigs()

        builder.build()
    }

    private fun serverConfigs() {
        BLOCK_DESTABILIZER_LIMIT = builder
            .comment("How many blocks should the Block Destabilizer be able to drop?")
            .defineInRange("blockDestabilizerLimit", 50, 1, Int.MAX_VALUE)
    }

}