package dev.aaronhowser.mods.irregular_implements.registries

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.entity.TimeAcceleratorEntity
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModEntityTypes {

    val ENTITY_TYPE_REGISTRY: DeferredRegister<EntityType<*>> =
        DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, IrregularImplements.ID)

    val TIME_ACCELERATOR: DeferredHolder<EntityType<*>, EntityType<TimeAcceleratorEntity>> =
        ENTITY_TYPE_REGISTRY.register("time_accelerator", Supplier {
            EntityType.Builder.of(
                ::TimeAcceleratorEntity,
                MobCategory.MISC
            )
                .sized(1f, 1f)
                .build("time_accelerator")
        })

}