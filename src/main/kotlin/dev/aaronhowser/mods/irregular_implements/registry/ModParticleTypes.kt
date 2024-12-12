package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.registries.BuiltInRegistries
import net.neoforged.neoforge.registries.DeferredRegister

object ModParticleTypes {

    val PARTICLE_TYPE_REGISTRY: DeferredRegister<ParticleType<*>> =
        DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, IrregularImplements.ID)


}