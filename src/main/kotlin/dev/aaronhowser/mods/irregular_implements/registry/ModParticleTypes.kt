package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.core.registries.Registries
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModParticleTypes {

	val PARTICLE_TYPE_REGISTRY: DeferredRegister<ParticleType<*>> =
		DeferredRegister.create(Registries.PARTICLE_TYPE, IrregularImplements.ID)

	val FLOO_FLAME: DeferredHolder<ParticleType<*>, SimpleParticleType> =
		PARTICLE_TYPE_REGISTRY.register("floo_flame", Supplier { SimpleParticleType(true) })

}