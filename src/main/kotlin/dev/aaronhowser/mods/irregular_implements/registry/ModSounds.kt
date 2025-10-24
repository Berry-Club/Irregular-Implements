package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.registries.Registries
import net.minecraft.sounds.SoundEvent
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

object ModSounds {

	val SOUND_EVENT_REGISTRY: DeferredRegister<SoundEvent> =
		DeferredRegister.create(Registries.SOUND_EVENT, IrregularImplements.ID)

	val FART: RegistryObject<SoundEvent> =
		SOUND_EVENT_REGISTRY.register("fart", Supplier {
			SoundEvent.createVariableRangeEvent(OtherUtil.modResource("fart"))
		})

	val EXTRACTINATOR: RegistryObject<SoundEvent> =
		SOUND_EVENT_REGISTRY.register("extractinator", Supplier {
			SoundEvent.createVariableRangeEvent(OtherUtil.modResource("extractinator"))
		})

	val NOTIFICATION: RegistryObject<SoundEvent> =
		SOUND_EVENT_REGISTRY.register("notification", Supplier {
			SoundEvent.createVariableRangeEvent(OtherUtil.modResource("notification"))
		})

	val REVIVE: RegistryObject<SoundEvent> =
		SOUND_EVENT_REGISTRY.register("revive", Supplier {
			SoundEvent.createVariableRangeEvent(OtherUtil.modResource("revive"))
		})

	val TELEPORT: RegistryObject<SoundEvent> =
		SOUND_EVENT_REGISTRY.register("teleport", Supplier {
			SoundEvent.createVariableRangeEvent(OtherUtil.modResource("teleport"))
		})

	val WHITE_STONE_ACTIVATE: RegistryObject<SoundEvent> =
		SOUND_EVENT_REGISTRY.register("white_stone_activate", Supplier {
			SoundEvent.createVariableRangeEvent(OtherUtil.modResource("white_stone_activate"))
		})

}