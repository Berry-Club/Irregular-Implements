package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.datapack.placement_filters.WeightedBiomeRarityFilter
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.levelgen.placement.PlacementModifierType
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModPlacementModifierTypes {

    val PLACEMENT_MODIFIER_TYPE_REGISTRY: DeferredRegister<PlacementModifierType<*>> =
        DeferredRegister.create(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, IrregularImplements.ID)

    val WEIGHTED_BIOME_RARITY: DeferredHolder<PlacementModifierType<*>, PlacementModifierType<WeightedBiomeRarityFilter>> =
        PLACEMENT_MODIFIER_TYPE_REGISTRY.register("weighted_biome_rarity", Supplier {
            PlacementModifierType { WeightedBiomeRarityFilter.CODEC }
        })

}