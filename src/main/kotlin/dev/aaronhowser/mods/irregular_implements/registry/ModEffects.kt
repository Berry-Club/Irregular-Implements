package dev.aaronhowser.mods.irregular_implements.registry

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
        EFFECT_REGISTRY.register("imbue_fire", Supplier { ImbueEffect() })
    val POISON_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =
        EFFECT_REGISTRY.register("imbue_poison", Supplier { ImbueEffect() })
    val EXPERIENCE_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =
        EFFECT_REGISTRY.register("imbue_experience", Supplier { ImbueEffect() })
    val WITHER_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =
        EFFECT_REGISTRY.register("imbue_wither", Supplier { ImbueEffect() })
    val COLLAPSE_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =
        EFFECT_REGISTRY.register("imbue_collapse", Supplier { ImbueEffect() })  //TODO: Inverts wasd

}