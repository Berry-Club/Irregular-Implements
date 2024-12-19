package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.attachment.DeathKeptItems
import net.neoforged.neoforge.attachment.AttachmentType
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries
import java.util.function.Supplier

object ModAttachmentTypes {

    val ATTACHMENT_TYPES_REGISTRY: DeferredRegister<AttachmentType<*>> =
        DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, IrregularImplements.ID)

    val DEATH_KEPT_ITEMS: DeferredHolder<AttachmentType<*>, AttachmentType<DeathKeptItems>> =
        ATTACHMENT_TYPES_REGISTRY.register("kept_inventory", Supplier {
            AttachmentType
                .builder(::DeathKeptItems)
                .serialize(DeathKeptItems.CODEC)
                .copyOnDeath()
                .build()
        })

}