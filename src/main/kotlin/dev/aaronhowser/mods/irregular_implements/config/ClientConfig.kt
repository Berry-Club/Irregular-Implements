package dev.aaronhowser.mods.irregular_implements.config

import net.neoforged.neoforge.common.ModConfigSpec
import org.apache.commons.lang3.tuple.Pair

class ClientConfig(
    private val builder: ModConfigSpec.Builder
) {

    companion object {
        private val configPair: Pair<ClientConfig, ModConfigSpec> = ModConfigSpec.Builder().configure(::ClientConfig)

        val CONFIG: ClientConfig = configPair.left
        val CONFIG_SPEC: ModConfigSpec = configPair.right

        lateinit var COLLAPSE_INVERTS_MOUSE: ModConfigSpec.BooleanValue
        lateinit var DIVINING_ROD_CHECK_RADIUS: ModConfigSpec.IntValue  //TODO: Make serverside? For lag reasons
    }

    init {
        clientConfigs()
        builder.build()
    }

    private fun clientConfigs() {

        COLLAPSE_INVERTS_MOUSE = builder
            .comment("Should the Collapse Imbue invert the player's mouse sensitivity?")
            .define("collapseInvertsMouse", true)

        DIVINING_ROD_CHECK_RADIUS = builder
            .comment("The radius around the player to check for blocks with the Divining Rod")
            .defineInRange("diviningRodCheckRadius", 5, 1, 100)

    }

}