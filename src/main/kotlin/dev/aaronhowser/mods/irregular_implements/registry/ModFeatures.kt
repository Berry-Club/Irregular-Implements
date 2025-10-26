package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.world.feature.NatureCoreFeature
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.levelgen.feature.Feature
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

object ModFeatures {

	val FEATURE_REGISTRY: DeferredRegister<Feature<*>> =
		DeferredRegister.create(BuiltInRegistries.FEATURE, IrregularImplements.MOD_ID)

	val NATURE_CORE: DeferredHolder<Feature<*>, NatureCoreFeature> =
		FEATURE_REGISTRY.register("nature_core", ::NatureCoreFeature)

}