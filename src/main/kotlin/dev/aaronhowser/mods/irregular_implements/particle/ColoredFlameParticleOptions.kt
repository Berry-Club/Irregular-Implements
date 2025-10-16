package dev.aaronhowser.mods.irregular_implements.particle

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.aaronhowser.mods.irregular_implements.registry.ModParticleTypes
import io.netty.buffer.ByteBuf
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.util.ExtraCodecs
import org.joml.Vector3f

class ColoredFlameParticleOptions(
	val color: Vector3f
) : ParticleOptions {

	override fun getType(): ParticleType<*> {
		return ModParticleTypes.COLORED_FLAME.get()
	}

	companion object {
		val CODEC: MapCodec<ColoredFlameParticleOptions> =
			RecordCodecBuilder.mapCodec { instance ->
				instance.group(
					ExtraCodecs.VECTOR3F
						.fieldOf("color")
						.forGetter(ColoredFlameParticleOptions::color)
				).apply(instance, ::ColoredFlameParticleOptions)
			}

		val STREAM_CODEC: StreamCodec<ByteBuf, ColoredFlameParticleOptions> =
			StreamCodec.composite(
				ByteBufCodecs.VECTOR3F, ColoredFlameParticleOptions::color,
				::ColoredFlameParticleOptions
			)
	}

}