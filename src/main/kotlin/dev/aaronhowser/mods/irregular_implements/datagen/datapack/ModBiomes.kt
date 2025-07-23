package dev.aaronhowser.mods.irregular_implements.datagen.datapack

import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.HolderGetter
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.BiomeGenerationSettings
import net.minecraft.world.level.biome.BiomeSpecialEffects
import net.minecraft.world.level.biome.MobSpawnSettings
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import java.awt.Color

object ModBiomes {

	val SPECTRAL_BIOME_RL: ResourceLocation = OtherUtil.modResource("spectral")
	val SPECTRAL_BIOME_RK: ResourceKey<Biome> = ResourceKey.create(Registries.BIOME, SPECTRAL_BIOME_RL)

	fun bootstrap(context: BootstrapContext<Biome>) {
		val placedFeature = context.lookup(Registries.PLACED_FEATURE)
		val configuredCarver = context.lookup(Registries.CONFIGURED_CARVER)

		context.register(SPECTRAL_BIOME_RK, spectral(placedFeature, configuredCarver))
	}

	private fun spectral(placedFeature: HolderGetter<PlacedFeature>, configuredCarver: HolderGetter<ConfiguredWorldCarver<*>>): Biome {
		val generationSettings = BiomeGenerationSettings.Builder(placedFeature, configuredCarver)
			.build()

		val specialEffects = BiomeSpecialEffects.Builder()
			.waterColor(Color.CYAN.rgb)
			.fogColor(0xC0D8FF)
			.waterColor(0x3F76E4)
			.waterFogColor(0x050533)
			.skyColor(0x000000)
			.build()

		val spawnSettings = MobSpawnSettings.Builder()
			.creatureGenerationProbability(0f)
			.build()

		return Biome.BiomeBuilder()
			.hasPrecipitation(false)
			.downfall(0f)
			.specialEffects(specialEffects)
			.generationSettings(generationSettings)
			.mobSpawnSettings(spawnSettings)
			.temperature(0.2f)
			.build()
	}

}