package dev.aaronhowser.mods.irregular_implements.registries

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.item.component.ItemStackComponent
import dev.aaronhowser.mods.irregular_implements.item.component.LocationItemComponent
import dev.aaronhowser.mods.irregular_implements.item.component.SpecificEntityItemComponent
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.Holder
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.tags.TagKey
import net.minecraft.util.Unit
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.material.Fluid
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.*

object ModDataComponents {

    val DATA_COMPONENT_REGISTRY: DeferredRegister.DataComponents =
        DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, IrregularImplements.ID)

    val LOCATION: DeferredHolder<DataComponentType<*>, DataComponentType<LocationItemComponent>> =
        DATA_COMPONENT_REGISTRY.registerComponentType("location") {
            it
                .persistent(LocationItemComponent.CODEC)
                .networkSynchronized(LocationItemComponent.STREAM_CODEC)
        }

    val ENTITY_TYPE: DeferredHolder<DataComponentType<*>, DataComponentType<EntityType<*>>> =
        DATA_COMPONENT_REGISTRY.registerComponentType("entity_type") {
            it
                .persistent(BuiltInRegistries.ENTITY_TYPE.byNameCodec())
                .networkSynchronized(ByteBufCodecs.registry(Registries.ENTITY_TYPE))
        }

    val UUID: DeferredHolder<DataComponentType<*>, DataComponentType<UUID>> =
        DATA_COMPONENT_REGISTRY.registerComponentType("uuid") { builder ->
            builder
                .persistent(SpecificEntityItemComponent.UUID_CODEC)
                .networkSynchronized(SpecificEntityItemComponent.UUID_STREAM_CODEC)
        }

    val BIOME: DeferredHolder<DataComponentType<*>, DataComponentType<Holder<Biome>>> =
        DATA_COMPONENT_REGISTRY.registerComponentType("biome") {
            it
                .persistent(Biome.CODEC)
                .networkSynchronized(ByteBufCodecs.holderRegistry(Registries.BIOME))
        }

    val PLAYER: DeferredHolder<DataComponentType<*>, DataComponentType<SpecificEntityItemComponent>> =
        DATA_COMPONENT_REGISTRY.registerComponentType("player") {
            it
                .persistent(SpecificEntityItemComponent.CODEC)
                .networkSynchronized(SpecificEntityItemComponent.STREAM_CODEC)
        }

    val ITEMSTACK: DeferredHolder<DataComponentType<*>, DataComponentType<ItemStackComponent>> =
        DATA_COMPONENT_REGISTRY.registerComponentType("itemstack") {
            it
                .persistent(ItemStackComponent.CODEC)
                .networkSynchronized(ItemStackComponent.STREAM_CODEC)
        }

    val FLUID_TAGS: DeferredHolder<DataComponentType<*>, DataComponentType<List<TagKey<Fluid>>>> =
        DATA_COMPONENT_REGISTRY.registerComponentType("fluid_tags") {
            it
                .persistent(
                    TagKey.codec(Registries.FLUID)
                        .listOf()
                )
                .networkSynchronized(
                    OtherUtil.tagKeyStreamCodec(Registries.FLUID)
                        .apply(ByteBufCodecs.list())
                )
        }

    @JvmStatic
    val LUBRICATED: DeferredHolder<DataComponentType<*>, DataComponentType<Unit>> =
        DATA_COMPONENT_REGISTRY.registerComponentType("lubricated") {
            it
                .persistent(Unit.CODEC)
                .networkSynchronized(StreamCodec.unit(Unit.INSTANCE))
        }

}