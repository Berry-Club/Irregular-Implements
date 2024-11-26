package dev.aaronhowser.mods.irregular_implements.registries

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

}