package dev.aaronhowser.mods.irregular_implements.config

import net.neoforged.neoforge.common.ModConfigSpec
import org.apache.commons.lang3.tuple.Pair

class CommonConfig(
    private val builder: ModConfigSpec.Builder
) {

    companion object {
        private val configPair: Pair<CommonConfig, ModConfigSpec> = ModConfigSpec.Builder().configure(::CommonConfig)

        val CONFIG: CommonConfig = configPair.left
        val CONFIG_SPEC: ModConfigSpec = configPair.right

        lateinit var RAIN_SHIELD_CHUNK_RADIUS: ModConfigSpec.IntValue
    }

    init {
        commonConfigs()
        builder.build()
    }

    private fun commonConfigs() {

        RAIN_SHIELD_CHUNK_RADIUS = builder
            .comment("What chunk radius should the Rain Shield have?")
            .defineInRange("rainShieldChunkRadius", 5, 0, Int.MAX_VALUE)

    }

}