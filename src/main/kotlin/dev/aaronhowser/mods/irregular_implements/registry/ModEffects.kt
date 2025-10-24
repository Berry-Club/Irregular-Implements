package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.effect.CollapseEffect
import dev.aaronhowser.mods.irregular_implements.effect.ImbueEffect
import net.minecraft.core.registries.Registries
import net.minecraft.world.effect.MobEffect
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegistryObject
import java.awt.Color
import java.util.function.Supplier

object ModEffects {

	val EFFECT_REGISTRY: DeferredRegister<MobEffect> =
		DeferredRegister.create(Registries.MOB_EFFECT, IrregularImplements.ID)

	val FIRE_IMBUE: RegistryObject<ImbueEffect> =
		imbue("fire", Color.ORANGE.rgb)
	val POISON_IMBUE: RegistryObject<ImbueEffect> =
		imbue("poison", Color.GREEN.darker().rgb)
	val EXPERIENCE_IMBUE: RegistryObject<ImbueEffect> =
		imbue("experience", Color.YELLOW.rgb)
	val WITHER_IMBUE: RegistryObject<ImbueEffect> =
		imbue("wither", Color.BLACK.brighter().rgb)
	val SPECTRE_IMBUE: RegistryObject<ImbueEffect> =    //FIXME: No texture
		imbue("spectre", 0xBFEFFF)
	val COLLAPSE_IMBUE: RegistryObject<ImbueEffect> =
		imbue("collapse", Color.PINK.rgb)

	@JvmField
	val COLLAPSE: RegistryObject<CollapseEffect> =
		EFFECT_REGISTRY.register("collapse", Supplier { CollapseEffect() })

	private fun <T : MobEffect> register(name: String, supplier: Supplier<T>): RegistryObject<T> {
		return EFFECT_REGISTRY.register(name, supplier)
	}

	private fun imbue(name: String, color: Int): RegistryObject<ImbueEffect> {
		return register("imbue_$name") { ImbueEffect(color) }
	}

}