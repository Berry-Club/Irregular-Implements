package dev.aaronhowser.mods.irregular_implements.event

import dev.aaronhowser.mods.irregular_implements.entity.GoldenChickenEntity
import dev.aaronhowser.mods.irregular_implements.entity.SpiritEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import net.minecraftforge.event.entity.EntityAttributeCreationEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

object CommonModBusEvents {

	@SubscribeEvent
	fun entityAttributeEvent(event: EntityAttributeCreationEvent) {
		event.put(ModEntityTypes.GOLDEN_CHICKEN.get(), GoldenChickenEntity.createAttributes())
		event.put(ModEntityTypes.SPIRIT.get(), SpiritEntity.createAttributes())
	}

}