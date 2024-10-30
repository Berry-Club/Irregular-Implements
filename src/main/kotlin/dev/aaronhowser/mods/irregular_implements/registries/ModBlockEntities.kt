package dev.aaronhowser.mods.irregular_implements.registries

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModBlockEntities {

    val BLOCK_ENTITY_REGISTRY: DeferredRegister<BlockEntityType<*>> =
        DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, IrregularImplements.ID)

    val LIGHT_REDIRECTOR =
        BLOCK_ENTITY_REGISTRY.register("light_redirector", Supplier {
            BlockEntityType.Builder.of(
                { pos, state -> LightRedirectorBlockEntity(pos, state) },
                ModBlocks.LIGHT_REDIRECTOR.get()
            ).build(null)
        })

}