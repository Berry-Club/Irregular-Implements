package dev.aaronhowser.mods.irregular_implements

import dev.aaronhowser.mods.irregular_implements.config.CommonConfig
import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.registry.ModRegistries
import net.neoforged.api.distmarker.Dist
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import net.neoforged.neoforge.client.gui.ConfigurationScreen
import net.neoforged.neoforge.client.gui.IConfigScreenFactory
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.neoforge.forge.runWhenOn

@Mod(IrregularImplements.ID)
class IrregularImplements(
    modContainer: ModContainer
) {
    companion object {
        const val ID = "irregular_implements"
        val LOGGER: Logger = LogManager.getLogger(ID)
    }

    init {
        ModRegistries.register(MOD_BUS)

        runWhenOn(Dist.CLIENT) {
            val screenFactory = IConfigScreenFactory { container, screen -> ConfigurationScreen(container, screen) }
            modContainer.registerExtensionPoint(IConfigScreenFactory::class.java, screenFactory)
        }

        modContainer.registerConfig(ModConfig.Type.COMMON, CommonConfig.CONFIG_SPEC)
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.CONFIG_SPEC)
    }

}