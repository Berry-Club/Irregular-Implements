package dev.aaronhowser.mods.irregular_implements.config

import net.minecraft.util.Mth
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
        lateinit var TIAB_MAX_STACKS: ModConfigSpec.IntValue

        val TIAB_TICKS_PER: Int
            get() = Mth.ceil(TIAB_SECONDS_PER.get() * 20)
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
            .comment("For how many seconds should the block stay accelerated?")
            .defineInRange("tiab_seconds_per", 30.0, 0.0, Double.MAX_VALUE)

        TIAB_MAX_STACKS = builder
            .comment("How many times can the Time in a Bottle stack on the same block? Each stack doubles the acceleration with the formula 2^n")
            .defineInRange("tiab_max_stacks", 8, 1, Int.MAX_VALUE)
    }

}