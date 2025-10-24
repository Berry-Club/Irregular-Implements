package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.world.placement_filters.WeightedBiomeRarityFilter
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.levelgen.placement.PlacementModifierType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

object ModPlacementModifierTypes {

	val PLACEMENT_MODIFIER_TYPE_REGISTRY: DeferredRegister<PlacementModifierType<*>> =
		DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, IrregularImplements.ID)

	val WEIGHTED_BIOME_RARITY: RegistryObject<PlacementModifierType<WeightedBiomeRarityFilter>> =
		PLACEMENT_MODIFIER_TYPE_REGISTRY.register("weighted_biome_rarity", Supplier {
			PlacementModifierType { WeightedBiomeRarityFilter.CODEC }
		})

}