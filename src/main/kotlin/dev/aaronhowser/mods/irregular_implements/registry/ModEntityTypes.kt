package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.entity.*
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.phys.Vec3
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

    val GOLDEN_CHICKEN: DeferredHolder<EntityType<*>, EntityType<GoldenChickenEntity>> =
        ENTITY_TYPE_REGISTRY.register("golden_chicken", Supplier {
            EntityType.Builder.of(
                ::GoldenChickenEntity,
                MobCategory.CREATURE
            )
                .sized(0.4F, 0.7F)
                .eyeHeight(0.644F)
                .passengerAttachments(Vec3(0.0, 0.7, -0.1))
                .clientTrackingRange(10)
                .build("golden_chicken")
        })

    val WEATHER_EGG: DeferredHolder<EntityType<*>, EntityType<ThrownWeatherEggEntity>> =
        ENTITY_TYPE_REGISTRY.register("weather_egg", Supplier {
            EntityType.Builder.of(
                ::ThrownWeatherEggEntity,
                MobCategory.MISC
            )
                .sized(0.25f, 0.25f)
                .clientTrackingRange(4)
                .updateInterval(10)
                .build("weather_egg")
        })

    val WEATHER_CLOUD: DeferredHolder<EntityType<*>, EntityType<WeatherCloudEntity>> =
        ENTITY_TYPE_REGISTRY.register("weather_cloud", Supplier {
            EntityType.Builder.of(
                ::WeatherCloudEntity,
                MobCategory.MISC
            )
                .sized(1f, 1f)
                .clientTrackingRange(256)
                .build("weather_cloud")
        })

}