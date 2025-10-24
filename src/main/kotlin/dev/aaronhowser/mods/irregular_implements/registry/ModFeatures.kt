package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.world.feature.NatureCoreFeature
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegistryObject

object ModFeatures {

	val FEATURE_REGISTRY: DeferredRegister<Feature<*>> =
		DeferredRegister.create(Registries.FEATURE, IrregularImplements.ID)

	val NATURE_CORE: RegistryObject<NatureCoreFeature> =
		FEATURE_REGISTRY.register("nature_core", ::NatureCoreFeature)

}