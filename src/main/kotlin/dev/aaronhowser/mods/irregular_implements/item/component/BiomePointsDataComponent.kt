package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.Holder
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtOps
import net.minecraft.nbt.Tag
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.biome.Biome
import kotlin.jvm.optionals.getOrNull

data class BiomePointsDataComponent(
	val biome: Holder<Biome>,
	val points: Int
) {

	fun withMorePoints(amount: Int): BiomePointsDataComponent {
		return copy(points = points + amount)
	}

	fun withLessPoints(amount: Int): BiomePointsDataComponent {
		return copy(points = points - amount)
	}

	fun save(): Tag {
		val dataResult = CODEC.encodeStart(NbtOps.INSTANCE, this)
		return dataResult.result().get()
	}

	companion object {
		const val NAME = "biome_points_data"

		val CODEC: Codec<BiomePointsDataComponent> =
			RecordCodecBuilder.create { instance ->
				instance.group(
					Biome.CODEC
						.fieldOf("biome")
						.forGetter(BiomePointsDataComponent::biome),
					Codec.INT
						.fieldOf("points")
						.forGetter(BiomePointsDataComponent::points)
				).apply(instance, ::BiomePointsDataComponent)
			}

		fun load(tag: CompoundTag): BiomePointsDataComponent? {
			val dataResult = CODEC.parse(NbtOps.INSTANCE, tag)
			return dataResult.result().getOrNull()
		}

		fun getFromStack(itemStack: ItemStack): BiomePointsDataComponent? {
			val tag = itemStack.tag ?: return null
			if (!tag.contains(NAME, Tag.TAG_COMPOUND.toInt())) return null

			return load(tag.getCompound(NAME))
		}
	}

}