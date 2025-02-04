package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.entity.ArtificialEndPortalEntity
import dev.aaronhowser.mods.irregular_implements.entity.IndicatorDisplayEntity
import dev.aaronhowser.mods.irregular_implements.entity.SpectreIlluminatorEntity
import dev.aaronhowser.mods.irregular_implements.entity.ThrownGoldenEggEntity
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModEntityTypes {

    val ENTITY_TYPE_REGISTRY: DeferredRegister<EntityType<*>> =
        DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, IrregularImplements.ID)

    val INDICATOR_DISPLAY: DeferredHolder<EntityType<*>, EntityType<IndicatorDisplayEntity>> =
        ENTITY_TYPE_REGISTRY.register("indicator_display", Supplier {
            EntityType.Builder.of(
                ::IndicatorDisplayEntity,
                MobCategory.MISC
            )
                .sized(1f, 1f)
                .build("indicator_display")
        })

    val SPECTRE_ILLUMINATOR: DeferredHolder<EntityType<*>, EntityType<SpectreIlluminatorEntity>> =
        ENTITY_TYPE_REGISTRY.register("spectre_illuminator", Supplier {
            EntityType.Builder.of(
                ::SpectreIlluminatorEntity,
                MobCategory.MISC
            )
                .sized(1f, 1f)
                .clientTrackingRange(256)
                .build("spectre_illuminator")
        })

    val ARTIFICIAL_END_PORTAL: DeferredHolder<EntityType<*>, EntityType<ArtificialEndPortalEntity>> =
        ENTITY_TYPE_REGISTRY.register("artificial_end_portal", Supplier {
            EntityType.Builder.of(
                ::ArtificialEndPortalEntity,
                MobCategory.MISC
            )
                .sized(3f, 0.25f)
                .build("artificial_end_portal")
        })

    val GOLDEN_EGG: DeferredHolder<EntityType<*>, EntityType<ThrownGoldenEggEntity>> =
        ENTITY_TYPE_REGISTRY.register("thrown_golden_egg", Supplier {
            EntityType.Builder.of(
                ::ThrownGoldenEggEntity,
                MobCategory.MISC
            )
                .sized(0.25f, 0.25f)
                .clientTrackingRange(4)
                .updateInterval(10)
                .build("thrown_golden_egg")
        })

}