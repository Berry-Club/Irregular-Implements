package dev.aaronhowser.mods.irregular_implements.util

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentSerialization
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.entity.Entity
import java.util.*

data class SpecificEntity(
	val uuid: UUID,
	val name: Component
) {

	constructor(entity: Entity) : this(entity.uuid, entity.name)

	companion object {

		val CODEC: Codec<SpecificEntity> =
			RecordCodecBuilder.create { instance ->
				instance.group(
					OtherUtil.UUID_CODEC
						.fieldOf("uuid")
						.forGetter(SpecificEntity::uuid),
					ComponentSerialization.CODEC
						.fieldOf("name")
						.forGetter(SpecificEntity::name)
				).apply(instance, ::SpecificEntity)
			}

		val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, SpecificEntity> =
			StreamCodec.composite(
				OtherUtil.UUID_STREAM_CODEC, SpecificEntity::uuid,
				ComponentSerialization.STREAM_CODEC, SpecificEntity::name,
				::SpecificEntity
			)
	}

}