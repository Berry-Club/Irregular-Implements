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

        lateinit var TIAB_COST: ModConfigSpec.IntValue
        lateinit var TIAB_SECONDS_PER: ModConfigSpec.DoubleValue
    }

    init {
        tiab()

        builder.build()
    }

    private fun tiab() {
        TIAB_COST = builder
            .comment("How many stored ticks does it cost to accelerate a block?")
            .defineInRange("tiab_tick_cost", 30 * 20 / 2, 0, Int.MAX_VALUE)

        TIAB_SECONDS_PER = builder
            .comment("How many seconds")
            .defineInRange("tiab_seconds_per", 30.0, 0.0, Double.MAX_VALUE)
    }

}