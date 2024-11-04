package dev.aaronhowser.mods.irregular_implements.registries

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.effect.ImbueEffect
import net.minecraft.core.registries.Registries
import net.minecraft.world.effect.MobEffect
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModEffects {

    val EFFECT_REGISTRY: DeferredRegister<MobEffect> =
        DeferredRegister.create(Registries.MOB_EFFECT, IrregularImplements.ID)

    val FIRE_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =
        EFFECT_REGISTRY.register("fire_imbue", Supplier { ImbueEffect() })
    val POISON_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =
        EFFECT_REGISTRY.register("poison_imbue", Supplier { ImbueEffect() })
    val EXPERIENCE_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =
        EFFECT_REGISTRY.register("experience_imbue", Supplier { ImbueEffect() })
    val WITHER_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =
        EFFECT_REGISTRY.register("wither_imbue", Supplier { ImbueEffect() })
    val COLLAPSE_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =
        EFFECT_REGISTRY.register("collapse_imbue", Supplier { ImbueEffect() })

}