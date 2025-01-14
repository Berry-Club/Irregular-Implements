package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.block_entity.*
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

    val BLOCK_BREAKER: DeferredHolder<BlockEntityType<*>, BlockEntityType<BlockBreakerBlockEntity>> =
        BLOCK_ENTITY_REGISTRY.register("block_breaker", Supplier {
            BlockEntityType.Builder.of(
                { pos, state -> BlockBreakerBlockEntity(pos, state) },
                ModBlocks.BLOCK_BREAKER.get()
            ).build(null)
        })

    val SPECTRE_LENS: DeferredHolder<BlockEntityType<*>, BlockEntityType<SpectreLensBlockEntity>> =
        BLOCK_ENTITY_REGISTRY.register("spectre_lens", Supplier {
            BlockEntityType.Builder.of(
                { pos, state -> SpectreLensBlockEntity(pos, state) },
                ModBlocks.SPECTRE_LENS.get()
            ).build(null)
        })

    val SPECTRE_ENERGY_INJECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<SpectreEnergyInjectorBlockEntity>> =
        BLOCK_ENTITY_REGISTRY.register("spectre_energy_injector", Supplier {
            BlockEntityType.Builder.of(
                { pos, state -> SpectreEnergyInjectorBlockEntity(pos, state) },
                ModBlocks.SPECTRE_ENERGY_INJECTOR.get()
            ).build(null)
        })

    val SPECTRE_COIL: DeferredHolder<BlockEntityType<*>, BlockEntityType<SpectreCoilBlockEntity>> =
        BLOCK_ENTITY_REGISTRY.register("spectre_coil", Supplier {
            BlockEntityType.Builder.of(
                { pos, state -> SpectreCoilBlockEntity(pos, state) },
                ModBlocks.SPECTRE_COIL_BASIC.get(),
                ModBlocks.SPECTRE_COIL_REDSTONE.get(),
                ModBlocks.SPECTRE_COIL_ENDER.get(),
                ModBlocks.SPECTRE_COIL_NUMBER.get(),
                ModBlocks.SPECTRE_COIL_GENESIS.get()
            ).build(null)
        })

    val MOON_PHASE_DETECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<MoonPhaseDetectorBlockEntity>> =
        BLOCK_ENTITY_REGISTRY.register("moon_phase_detector", Supplier {
            BlockEntityType.Builder.of(
                { pos, state -> MoonPhaseDetectorBlockEntity(pos, state) },
                ModBlocks.MOON_PHASE_DETECTOR.get()
            ).build(null)
        })

    val CHAT_DETECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<ChatDetectorBlockEntity>> =
        BLOCK_ENTITY_REGISTRY.register("chat_detector", Supplier {
            BlockEntityType.Builder.of(
                { pos, state -> ChatDetectorBlockEntity(pos, state) },
                ModBlocks.CHAT_DETECTOR.get()
            ).build(null)
        })

    val GLOBAL_CHAT_DETECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<GlobalChatDetectorBlockEntity>> =
        BLOCK_ENTITY_REGISTRY.register("global_chat_detector", Supplier {
            BlockEntityType.Builder.of(
                { pos, state -> GlobalChatDetectorBlockEntity(pos, state) },
                ModBlocks.GLOBAL_CHAT_DETECTOR.get()
            ).build(null)
        })

    val ONLINE_DETECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<OnlineDetectorBlockEntity>> =
        BLOCK_ENTITY_REGISTRY.register("online_detector", Supplier {
            BlockEntityType.Builder.of(
                { pos, state -> OnlineDetectorBlockEntity(pos, state) },
                ModBlocks.ONLINE_DETECTOR.get()
            ).build(null)
        })

    val DIAPHANOUS_BLOCK: DeferredHolder<BlockEntityType<*>, BlockEntityType<DiaphanousBlockEntity>> =
        BLOCK_ENTITY_REGISTRY.register("diaphanous_block", Supplier {
            BlockEntityType.Builder.of(
                { pos, state -> DiaphanousBlockEntity(pos, state) },
                ModBlocks.DIAPHANOUS_BLOCK.get()
            ).build(null)
        })

    val IRON_DROPPER: DeferredHolder<BlockEntityType<*>, BlockEntityType<IronDropperBlockEntity>> =
        BLOCK_ENTITY_REGISTRY.register("iron_dropper", Supplier {
            BlockEntityType.Builder.of(
                { pos, state -> IronDropperBlockEntity(pos, state) },
                ModBlocks.IRON_DROPPER.get()
            ).build(null)
        })

    val CUSTOM_CRAFTING_TABLE: DeferredHolder<BlockEntityType<*>, BlockEntityType<CustomCraftingTableBlockEntity>> =
        BLOCK_ENTITY_REGISTRY.register("custom_crafting_table", Supplier {
            BlockEntityType.Builder.of(
                { pos, state -> CustomCraftingTableBlockEntity(pos, state) },
                ModBlocks.CUSTOM_CRAFTING_TABLE.get()
            ).build(null)
        })

}