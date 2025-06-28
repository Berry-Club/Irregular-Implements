package dev.aaronhowser.mods.irregular_implements.event

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.entity.GoldenChickenEntity
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
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
			ModBlockEntities.SPECTRE_ENERGY_INJECTOR.get()
		) { spectreEnergyInjectorBE, direction ->
			spectreEnergyInjectorBE.getEnergyHandler(direction)
		}

		event.registerBlockEntity(
			Capabilities.EnergyStorage.BLOCK,
			ModBlockEntities.SPECTRE_COIL.get()
		) { spectreCoilBE, direction ->
			spectreCoilBE.getEnergyHandler(direction)
		}

		event.registerBlockEntity(
			Capabilities.ItemHandler.BLOCK,
			ModBlockEntities.PLAYER_INTERFACE.get()
		) { playerInterfaceBE, direction ->
			playerInterfaceBE.getItemHandler(direction)
		}
	}

	@SubscribeEvent
	fun entityAttributeEvent(event: EntityAttributeCreationEvent) {
		event.put(ModEntityTypes.GOLDEN_CHICKEN.get(), GoldenChickenEntity.createAttributes())
	}

}