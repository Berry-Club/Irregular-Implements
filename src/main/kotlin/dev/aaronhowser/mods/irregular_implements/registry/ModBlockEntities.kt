package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.block_entity.BlockDestabilizerBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.RainShieldBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.RedstoneInterfaceBasicBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.RedstoneObserverBlockEntity
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModBlockEntities {

    val BLOCK_ENTITY_REGISTRY: DeferredRegister<BlockEntityType<*>> =
        DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, IrregularImplements.ID)

    val RAIN_SHIELD: DeferredHolder<BlockEntityType<*>, BlockEntityType<RainShieldBlockEntity>> =
        BLOCK_ENTITY_REGISTRY.register("rain_shield", Supplier {
            BlockEntityType.Builder.of(
                { pos, state -> RainShieldBlockEntity(pos, state) },
                ModBlocks.RAIN_SHIELD.get()
            ).build(null)
        })

    val REDSTONE_INTERFACE: DeferredHolder<BlockEntityType<*>, BlockEntityType<RedstoneInterfaceBasicBlockEntity>> =
        BLOCK_ENTITY_REGISTRY.register("redstone_interface", Supplier {
            BlockEntityType.Builder.of(
                { pos, state -> RedstoneInterfaceBasicBlockEntity(pos, state) },
                ModBlocks.BASIC_REDSTONE_INTERFACE.get()
            ).build(null)
        })

    val REDSTONE_OBSERVER: DeferredHolder<BlockEntityType<*>, BlockEntityType<RedstoneObserverBlockEntity>> =
        BLOCK_ENTITY_REGISTRY.register("redstone_observer", Supplier {
            BlockEntityType.Builder.of(
                { pos, state -> RedstoneObserverBlockEntity(pos, state) },
                ModBlocks.REDSTONE_OBSERVER.get()
            ).build(null)
        })

    val BLOCK_DESTABILIZER: DeferredHolder<BlockEntityType<*>, BlockEntityType<BlockDestabilizerBlockEntity>> =
        BLOCK_ENTITY_REGISTRY.register("block_destabilizer", Supplier {
            BlockEntityType.Builder.of(
                { pos, state -> BlockDestabilizerBlockEntity(pos, state) },
                ModBlocks.BLOCK_DESTABILIZER.get()
            ).build(null)
        })

}