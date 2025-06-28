package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.effect.CollapseEffect
import dev.aaronhowser.mods.irregular_implements.effect.ImbueEffect
import net.minecraft.core.registries.Registries
import net.minecraft.world.effect.MobEffect
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.awt.Color
import java.util.function.Supplier

object ModEffects {

	val EFFECT_REGISTRY: DeferredRegister<MobEffect> =
		DeferredRegister.create(Registries.MOB_EFFECT, IrregularImplements.ID)

	val FIRE_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =
		EFFECT_REGISTRY.register("imbue_fire", Supplier { ImbueEffect(Color.ORANGE.rgb) })
	val POISON_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =
		EFFECT_REGISTRY.register("imbue_poison", Supplier { ImbueEffect(Color.GREEN.darker().rgb) })
	val EXPERIENCE_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =
		EFFECT_REGISTRY.register("imbue_experience", Supplier { ImbueEffect(Color.YELLOW.rgb) })
	val WITHER_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =
		EFFECT_REGISTRY.register("imbue_wither", Supplier { ImbueEffect(Color.BLACK.brighter().rgb) })
	val SPECTRE_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =    //FIXME: No texture
		EFFECT_REGISTRY.register("imbue_spectre", Supplier { ImbueEffect(0xBFEFFF) })
	val COLLAPSE_IMBUE: DeferredHolder<MobEffect, ImbueEffect> =
		EFFECT_REGISTRY.register("imbue_collapse", Supplier { ImbueEffect(Color.PINK.rgb) })

	//TODO:
	// Hitting someone while you have the Collapse Imbue gives them Collapse
	// Also you can get Collapse by touching Sakanade spores

	@JvmField
	val COLLAPSE: DeferredHolder<MobEffect, CollapseEffect> =
		EFFECT_REGISTRY.register("collapse", Supplier { CollapseEffect() })

}