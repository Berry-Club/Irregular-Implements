package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.aaron.registry.AaronMobEffectsRegistry
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.effect.CollapseEffect
import dev.aaronhowser.mods.irregular_implements.effect.ImbueEffect
import net.minecraft.core.registries.Registries
import net.minecraft.world.effect.MobEffect
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.awt.Color

object ModMobEffects : AaronMobEffectsRegistry() {

	val EFFECT_REGISTRY: DeferredRegister<MobEffect> =
		DeferredRegister.create(Registries.MOB_EFFECT, IrregularImplements.MOD_ID)

	override fun getMobEffectRegistry(): DeferredRegister<MobEffect> = EFFECT_REGISTRY

	val FIRE_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =
		register("imbue_fire") { ImbueEffect(Color.ORANGE.rgb) }
	val POISON_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =
		register("imbue_poison") { ImbueEffect(Color.GREEN.darker().rgb) }
	val EXPERIENCE_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =
		register("imbue_experience") { ImbueEffect(Color.YELLOW.rgb) }
	val WITHER_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =
		register("imbue_wither") { ImbueEffect(Color.BLACK.brighter().rgb) }
	val SPECTRE_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =
		register("imbue_spectre") { ImbueEffect(0xBFEFFF) }
	val COLLAPSE_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =
		register("imbue_collapse") { ImbueEffect(Color.PINK.rgb) }

	@JvmField
	val COLLAPSE: DeferredHolder<MobEffect, CollapseEffect> =
		register("collapse") { CollapseEffect() }

}