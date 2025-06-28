package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.sounds.SoundEvent
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModSounds {

	val SOUND_EVENT_REGISTRY: DeferredRegister<SoundEvent> =
		DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, IrregularImplements.ID)

	val FART: DeferredHolder<SoundEvent, SoundEvent> =
		SOUND_EVENT_REGISTRY.register("fart", Supplier {
			SoundEvent.createVariableRangeEvent(OtherUtil.modResource("fart"))
		})

	val EXTRACTINATOR: DeferredHolder<SoundEvent, SoundEvent> =
		SOUND_EVENT_REGISTRY.register("extractinator", Supplier {
			SoundEvent.createVariableRangeEvent(OtherUtil.modResource("extractinator"))
		})

	val NOTIFICATION: DeferredHolder<SoundEvent, SoundEvent> =
		SOUND_EVENT_REGISTRY.register("notification", Supplier {
			SoundEvent.createVariableRangeEvent(OtherUtil.modResource("notification"))
		})

	val REVIVE: DeferredHolder<SoundEvent, SoundEvent> =
		SOUND_EVENT_REGISTRY.register("revive", Supplier {
			SoundEvent.createVariableRangeEvent(OtherUtil.modResource("revive"))
		})

	val TELEPORT: DeferredHolder<SoundEvent, SoundEvent> =
		SOUND_EVENT_REGISTRY.register("teleport", Supplier {
			SoundEvent.createVariableRangeEvent(OtherUtil.modResource("teleport"))
		})

	val WHITE_STONE_ACTIVATE: DeferredHolder<SoundEvent, SoundEvent> =
		SOUND_EVENT_REGISTRY.register("white_stone_activate", Supplier {
			SoundEvent.createVariableRangeEvent(OtherUtil.modResource("white_stone_activate"))
		})

}