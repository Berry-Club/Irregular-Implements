package dev.aaronhowser.mods.irregular_implements.config

import net.neoforged.neoforge.common.ModConfigSpec
import net.neoforged.neoforge.common.Tags
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
        lateinit var DIVINING_ROD_COLORS: ModConfigSpec.ConfigValue<Map<String, String>>

        private val defaultRodColors = mapOf(
            Tags.Blocks.ORES_COAL.location.toString() to "0x32141414",
            Tags.Blocks.ORES_IRON.location.toString() to "0x32D3B49F",
            Tags.Blocks.ORES_GOLD.location.toString() to "0x32F6E950",
            Tags.Blocks.ORES_LAPIS.location.toString() to "0x32052D96",
            Tags.Blocks.ORES_REDSTONE.location.toString() to "0x32D30101",
            Tags.Blocks.ORES_EMERALD.location.toString() to "0x3200DC00",
            Tags.Blocks.ORES_DIAMOND.location.toString() to "0x3255DDE5",
        )
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

        DIVINING_ROD_COLORS = builder
            .comment("What colors should each ore tag be when using the Divining Rod?")
            .define("diviningRodColors", defaultRodColors)

    }

}