package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.serialization.Codec
import dev.aaronhowser.mods.irregular_implements.util.SpecificEntity
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.entity.Entity

data class SpecificEntityDataComponent(
	val specificEntity: SpecificEntity
) {

	constructor(entity: Entity) : this(SpecificEntity(entity))

	val uuid = specificEntity.uuid
	val name = specificEntity.name

	companion object {

		val CODEC: Codec<SpecificEntityDataComponent> =
			SpecificEntity.CODEC.xmap(::SpecificEntityDataComponent, SpecificEntityDataComponent::specificEntity)

		val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, SpecificEntityDataComponent> =
			SpecificEntity.STREAM_CODEC.map(
				::SpecificEntityDataComponent,
				SpecificEntityDataComponent::specificEntity
			)
	}

}