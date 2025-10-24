package dev.aaronhowser.mods.irregular_implements

import dev.aaronhowser.mods.irregular_implements.config.ClientConfig
import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.registry.ModRegistries
import net.minecraftforge.fml.ModContainer
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(IrregularImplements.ID)
class IrregularImplements(
	modContainer: ModContainer
) {

	companion object {
		const val ID = "irregular_implements"

		@JvmField
		val LOGGER: Logger = LogManager.getLogger(ID)
	}

	init {
		ModRegistries.register(MOD_BUS)

		//TODO: Configs
//		runWhenOn(Dist.CLIENT) {
//			val screenFactory = IConfigScreenFactory { container, screen -> ConfigurationScreen(container, screen) }
//			modContainer.registerExtensionPoint(IConfigScreenFactory::class.java, screenFactory)
//		}

		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.CONFIG_SPEC)
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.CONFIG_SPEC)
	}

}