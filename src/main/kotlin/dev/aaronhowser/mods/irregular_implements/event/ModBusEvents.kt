package dev.aaronhowser.mods.irregular_implements.event

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.block_entity.PlayerInterfaceBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.SpectreCoilBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.SpectreEnergyInjectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.entity.GoldenChickenEntity
import dev.aaronhowser.mods.irregular_implements.item.component.RedstoneRemoteDataComponent
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent

@EventBusSubscriber(
	modid = IrregularImplements.ID,
	bus = EventBusSubscriber.Bus.MOD
)
object ModBusEvents {

	@SubscribeEvent
	fun registerPayloads(event: RegisterPayloadHandlersEvent) {
		ModPacketHandler.registerPayloads(event)
	}

	@SubscribeEvent
	fun onRegisterCapabilities(event: RegisterCapabilitiesEvent) {
		event.registerBlockEntity(
			Capabilities.EnergyStorage.BLOCK,
			ModBlockEntities.SPECTRE_ENERGY_INJECTOR.get(),
			SpectreEnergyInjectorBlockEntity::getCapability
		)

		event.registerBlockEntity(
			Capabilities.EnergyStorage.BLOCK,
			ModBlockEntities.SPECTRE_COIL.get(),
			SpectreCoilBlockEntity::getCapability
		)

		event.registerBlockEntity(
			Capabilities.ItemHandler.BLOCK,
			ModBlockEntities.PLAYER_INTERFACE.get(),
			PlayerInterfaceBlockEntity::getCapability
		)

		event.registerItem(
			Capabilities.ItemHandler.ITEM,
			RedstoneRemoteDataComponent::getCapability,
			ModItems.REDSTONE_REMOTE.get()
		)
	}

	@SubscribeEvent
	fun entityAttributeEvent(event: EntityAttributeCreationEvent) {
		event.put(ModEntityTypes.GOLDEN_CHICKEN.get(), GoldenChickenEntity.createAttributes())
	}

}