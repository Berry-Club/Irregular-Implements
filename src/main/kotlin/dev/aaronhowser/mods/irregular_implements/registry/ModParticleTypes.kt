package dev.aaronhowser.mods.irregular_implements.registry

import com.mojang.serialization.MapCodec
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.particle.ColoredFlameParticleOptions
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.core.registries.Registries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegistryObject
import net.neoforged.neoforge.registries.DeferredHolder
import java.util.function.Supplier

object ModParticleTypes {

	val PARTICLE_TYPE_REGISTRY: DeferredRegister<ParticleType<*>> =
		DeferredRegister.create(Registries.PARTICLE_TYPE, IrregularImplements.ID)

	val FLOO_FLAME: RegistryObject<SimpleParticleType> =
		simple("floo_flame")

	val COLORED_FLAME: RegistryObject<out ParticleType<ColoredFlameParticleOptions>> =
		register(
			"colored_flame",
			false,
			{ ColoredFlameParticleOptions.CODEC },
			{ ColoredFlameParticleOptions.STREAM_CODEC }
		)

	fun simple(name: String): RegistryObject<SimpleParticleType> {
		return PARTICLE_TYPE_REGISTRY.register(name, Supplier { SimpleParticleType(true) })
	}

	fun <T : ParticleOptions> register(
		name: String,
		overridesLimiter: Boolean,
		codecGetter: (ParticleType<T>) -> MapCodec<T>,
		streamCodecGetter: (ParticleType<T>) -> StreamCodec<in RegistryFriendlyByteBuf, T>
	): DeferredHolder<ParticleType<*>, out ParticleType<T>> {
		return PARTICLE_TYPE_REGISTRY.register(name, Supplier {
			object : ParticleType<T>(overridesLimiter) {
				override fun codec(): MapCodec<T> = codecGetter(this)
				override fun streamCodec(): StreamCodec<in RegistryFriendlyByteBuf, T> = streamCodecGetter(this)
			}
		})
	}

}