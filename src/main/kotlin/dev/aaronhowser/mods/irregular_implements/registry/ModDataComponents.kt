package dev.aaronhowser.mods.irregular_implements.registry

import com.mojang.serialization.Codec
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.item.WeatherEggItem
import dev.aaronhowser.mods.irregular_implements.item.component.*
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.aaronhowser.mods.irregular_implements.util.SpecificEntity
import net.minecraft.core.Holder
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.tags.TagKey
import net.minecraft.util.Unit
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.component.CustomData
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Fluid
import net.neoforged.neoforge.fluids.SimpleFluidContent
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.*

object ModDataComponents {

	val DATA_COMPONENT_REGISTRY: DeferredRegister.DataComponents =
		DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, IrregularImplements.ID)

	val LOCATION: DeferredHolder<DataComponentType<*>, DataComponentType<LocationDataComponent>> =
		register("location", LocationDataComponent.CODEC, LocationDataComponent.STREAM_CODEC)

	val ENTITY_TYPE: DeferredHolder<DataComponentType<*>, DataComponentType<EntityType<*>>> =
		register(
			"entity_type",
			BuiltInRegistries.ENTITY_TYPE.byNameCodec(),
			ByteBufCodecs.registry(Registries.ENTITY_TYPE)
		)

	val UUID: DeferredHolder<DataComponentType<*>, DataComponentType<UUID>> =
		register("uuid", OtherUtil.UUID_CODEC, OtherUtil.UUID_STREAM_CODEC)

	val BIOME: DeferredHolder<DataComponentType<*>, DataComponentType<Holder<Biome>>> =
		register(
			"biome",
			Biome.CODEC,
			ByteBufCodecs.holderRegistry(Registries.BIOME)
		)

	val PLAYER: DeferredHolder<DataComponentType<*>, DataComponentType<SpecificEntity>> =
		register("player", SpecificEntity.CODEC, SpecificEntity.STREAM_CODEC)

	val ENTITY_IDENTIFIER: DeferredHolder<DataComponentType<*>, DataComponentType<SpecificEntity>> =
		register("entity_identifier", SpecificEntity.CODEC, SpecificEntity.STREAM_CODEC)

	val ENTITY_LIST: DeferredHolder<DataComponentType<*>, DataComponentType<List<CustomData>>> =
		register(
			"entity_list",
			CustomData.CODEC.listOf(),
			CustomData.STREAM_CODEC.apply(ByteBufCodecs.list())
		)

	val FLUID_TAGS: DeferredHolder<DataComponentType<*>, DataComponentType<List<TagKey<Fluid>>>> =
		register(
			"fluid_tags",
			TagKey.codec(Registries.FLUID).listOf(),
			OtherUtil.tagKeyStreamCodec(Registries.FLUID).apply(ByteBufCodecs.list())
		)

	val REDSTONE_REMOTE: DeferredHolder<DataComponentType<*>, DataComponentType<RedstoneRemoteDataComponent>> =
		register("redstone_remote", RedstoneRemoteDataComponent.CODEC, RedstoneRemoteDataComponent.STREAM_CODEC)

	@JvmField
	val LUBRICATED: DeferredHolder<DataComponentType<*>, DataComponentType<Unit>> =
		unit("lubricated")

	val DURATION: DeferredHolder<DataComponentType<*>, DataComponentType<Int>> =
		register("duration", Codec.INT, ByteBufCodecs.VAR_INT)

	val BIOME_POINTS: DeferredHolder<DataComponentType<*>, DataComponentType<BiomePointsDataComponent>> =
		register("biome_points", BiomePointsDataComponent.CODEC, BiomePointsDataComponent.STREAM_CODEC)

	val BLOCK_DATA: DeferredHolder<DataComponentType<*>, DataComponentType<BlockDataComponent>> =
		register("block_data", BlockDataComponent.CODEC, BlockDataComponent.STREAM_CODEC)

	val CHARGE: DeferredHolder<DataComponentType<*>, DataComponentType<Int>> =
		register("charge", Codec.INT, ByteBufCodecs.VAR_INT)

	val COOLDOWN: DeferredHolder<DataComponentType<*>, DataComponentType<Int>> =
		register("cooldown", Codec.INT, ByteBufCodecs.VAR_INT)

	val IS_ENABLED: DeferredHolder<DataComponentType<*>, DataComponentType<Unit>> =
		unit("is_enabled")

	val IS_INVERTED: DeferredHolder<DataComponentType<*>, DataComponentType<Unit>> =
		unit("is_inverted")

	val BLOCK_TAG: DeferredHolder<DataComponentType<*>, DataComponentType<TagKey<Block>>> =
		register("block_tag", TagKey.codec(Registries.BLOCK), OtherUtil.tagKeyStreamCodec(Registries.BLOCK))

	val IS_ANCHORED: DeferredHolder<DataComponentType<*>, DataComponentType<Unit>> =
		unit("is_anchored")

	val SIMPLE_FLUID_CONTENT: DeferredHolder<DataComponentType<*>, DataComponentType<SimpleFluidContent>> =
		register("simple_fluid_content", SimpleFluidContent.CODEC, SimpleFluidContent.STREAM_CODEC)

	val BLOCK: DeferredHolder<DataComponentType<*>, DataComponentType<Block>> =
		register("block", BuiltInRegistries.BLOCK.byNameCodec(), ByteBufCodecs.registry(Registries.BLOCK))

	val ITEM_FILTER: DeferredHolder<DataComponentType<*>, DataComponentType<ItemFilterDataComponent>> =
		register("item_filter", ItemFilterDataComponent.CODEC, ItemFilterDataComponent.STREAM_CODEC)

	val WEATHER: DeferredHolder<DataComponentType<*>, DataComponentType<WeatherEggItem.Weather>> =
		register("weather", WeatherEggItem.Weather.CODEC, WeatherEggItem.Weather.STREAM_CODEC)

	val FLOO_POWDER: DeferredHolder<DataComponentType<*>, DataComponentType<Int>> =
		register("floo_powder", Codec.INT, ByteBufCodecs.VAR_INT)

	val PORTKEY_DISGUISE: DeferredHolder<DataComponentType<*>, DataComponentType<PortkeyDisguiseDataComponent>> =
		register("portkey_disguise", PortkeyDisguiseDataComponent.CODEC, PortkeyDisguiseDataComponent.STREAM_CODEC)

	val ENDER_LETTER_CONTENTS: DeferredHolder<DataComponentType<*>, DataComponentType<EnderLetterContentsDataComponent>> =
		register("ender_letter_contents", EnderLetterContentsDataComponent.CODEC, EnderLetterContentsDataComponent.STREAM_CODEC)

	@JvmField
	val HAS_LUMINOUS_POWDER: DeferredHolder<DataComponentType<*>, DataComponentType<Unit>> =
		unit("has_luminous_powder")

	private fun <T> register(
		name: String,
		codec: Codec<T>,
		streamCodec: StreamCodec<in RegistryFriendlyByteBuf, T>
	): DeferredHolder<DataComponentType<*>, DataComponentType<T>> {
		return DATA_COMPONENT_REGISTRY.registerComponentType(name) {
			it.persistent(codec).networkSynchronized(streamCodec)
		}
	}

	private fun unit(name: String): DeferredHolder<DataComponentType<*>, DataComponentType<Unit>> {
		return register(name, Unit.CODEC, StreamCodec.unit(Unit.INSTANCE))
	}

}