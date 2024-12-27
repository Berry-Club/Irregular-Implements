package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.entity.ArtificialEndPortalEntity
import dev.aaronhowser.mods.irregular_implements.entity.IlluminatorEntity
import dev.aaronhowser.mods.irregular_implements.entity.IndicatorDisplayEntity
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

    val ILLUMINATOR: DeferredHolder<EntityType<*>, EntityType<IlluminatorEntity>> =
        ENTITY_TYPE_REGISTRY.register("illuminator", Supplier {
            EntityType.Builder.of(
                ::IlluminatorEntity,
                MobCategory.MISC
            )
                .sized(1f, 1f)
                .build("illuminator")
        })

    val ARTIFICIAL_END_PORTAL: DeferredHolder<EntityType<*>, EntityType<ArtificialEndPortalEntity>> =
        ENTITY_TYPE_REGISTRY.register("artificial_end_portal", Supplier {
            EntityType.Builder.of(
                ::ArtificialEndPortalEntity,
                MobCategory.MISC
            )
                .sized(3f, 1f)
                .build("artificial_end_portal")
        })

}