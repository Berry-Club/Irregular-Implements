package dev.aaronhowser.mods.irregular_implements.registries

import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister

object ModRegistries {

    private val registries: List<DeferredRegister<out Any>> = listOf(
        ModItems.ITEM_REGISTRY,
        ModBlocks.BLOCK_REGISTRY,
        ModCreativeModeTabs.TABS_REGISTRY,
        ModDataComponents.DATA_COMPONENT_REGISTRY
    )

    fun register(modBus: IEventBus) {
        registries.forEach { it.register(modBus) }
    }
}