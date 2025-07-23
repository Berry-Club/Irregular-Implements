package dev.aaronhowser.mods.irregular_implements.util

import com.mojang.serialization.Codec
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.entity.IndicatorDisplayEntity
import io.netty.buffer.ByteBuf
import net.minecraft.ChatFormatting
import net.minecraft.client.resources.language.I18n
import net.minecraft.core.*
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.util.Mth
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.alchemy.PotionContents
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.Vec3
import java.util.*

object OtherUtil {

	@Suppress("UsePropertyAccessSyntax")
	fun DyeColor.getDyeName(): String = this.getName()

	@Suppress("UsePropertyAccessSyntax")
	fun Direction.getDirectionName(): String = this.getName()

	@JvmStatic
	fun modResource(path: String): ResourceLocation =
		ResourceLocation.fromNamespaceAndPath(IrregularImplements.ID, path)

	val Boolean?.isTrue: Boolean
		inline get() = this == true

	val Entity.isClientSide: Boolean
		get() = this.level().isClientSide

	fun getPotionStack(potion: Holder<Potion>): ItemStack {
		return PotionContents.createItemStack(Items.POTION, potion)
	}

	fun Vec3i.toVec3(): Vec3 {
		return Vec3(this.x.toDouble(), this.y.toDouble(), this.z.toDouble())
	}

	fun getPovResult(level: Level, entity: LivingEntity, range: Number): BlockHitResult {
		return level.clip(
			ClipContext(
				entity.eyePosition,
				entity.eyePosition.add(entity.lookAngle.scale(range.toDouble())),
				ClipContext.Block.OUTLINE,
				ClipContext.Fluid.NONE,
				entity
			)
		)
	}

	fun <T> tagKeyStreamCodec(registry: ResourceKey<out Registry<T>>): StreamCodec<ByteBuf, TagKey<T>> {
		return ResourceLocation.STREAM_CODEC.map(
			{ TagKey.create(registry, it) },
			{ it.location() }
		)
	}

	fun spawnIndicatorBlockDisplay(
		level: Level,
		pos: BlockPos,
		color: Int = 0xFFFFFF,
		duration: Int = 5
	): IndicatorDisplayEntity? {
		if (level.isClientSide) return null

		val indicatorDisplay = IndicatorDisplayEntity(
			level,
			Blocks.GLASS.defaultBlockState(),
			color,
			duration
		)

		indicatorDisplay.setPos(pos.x + 0.25, pos.y + 0.25, pos.z + 0.25)
		level.addFreshEntity(indicatorDisplay)

		return indicatorDisplay
	}

	fun getBiomeComponent(biomeHolder: Holder<Biome>): Component {
		val biomeKey = biomeHolder.key!!

		val probableTranslationKey = "biome.${biomeKey.location().namespace}.${biomeKey.location().path}"
		val hasTranslation = I18n.exists(probableTranslationKey)

		return if (hasTranslation) {
			Component.translatable(probableTranslationKey)
		} else {
			Component.literal(biomeKey.location().toString())
		}.withStyle(ChatFormatting.GRAY)
	}

	fun getDimensionComponent(dimensionResourceKey: ResourceKey<Level>): Component {
		val location = dimensionResourceKey.location()

		val probableTranslationKey = "dimension.${location.namespace}.${location.path}"
		val hasTranslation = I18n.exists(probableTranslationKey)

		return if (hasTranslation) {
			Component.translatable(probableTranslationKey)
		} else {
			Component.literal(location.toString())
		}.withStyle(ChatFormatting.GRAY)
	}

	fun flattenStacks(input: List<ItemStack>): List<ItemStack> {
		val output = mutableListOf<ItemStack>()

		for (stack in input.filter { !it.isEmpty }.map { it.copy() }) {
			val matchingStack = output.firstOrNull { ItemStack.isSameItemSameComponents(it, stack) }

			if (matchingStack != null) {
				val amountToAdd = minOf(stack.count, matchingStack.maxStackSize - matchingStack.count)

				if (amountToAdd > 0) {
					matchingStack.grow(amountToAdd)
					stack.shrink(amountToAdd)
				}
			}

			if (!stack.isEmpty) {
				output.add(stack)
			}
		}

		return output
	}

	fun giveOrDropStack(itemStack: ItemStack, player: Player): Boolean {
		return player.inventory.add(itemStack)
				|| dropStackAt(itemStack, player, true)
	}

	fun dropStackAt(itemStack: ItemStack, entity: Entity, instantPickup: Boolean = false): Boolean {
		return dropStackAt(itemStack, entity.level(), entity.position(), instantPickup)
	}

	fun dropStackAt(itemStack: ItemStack, level: Level, pos: Vec3, instantPickup: Boolean = false): Boolean {
		val itemEntity = ItemEntity(level, pos.x, pos.y, pos.z, itemStack)
		if (instantPickup) itemEntity.setNoPickUpDelay()
		return level.addFreshEntity(itemEntity)
	}

	fun CompoundTag.getUuidOrNull(key: String): UUID? {
		return if (this.hasUUID(key)) this.getUUID(key) else null
	}

	fun TagKey<Item>.getComponent(): MutableComponent {
		val tagLocation = this.location
		val possibleLangKey = StringBuilder()
			.append("tag.item.")
			.append(tagLocation.namespace)
			.append(".")
			.append(tagLocation.path)
			.toString()

		return if (I18n.exists(possibleLangKey)) {
			Component.translatable(possibleLangKey)
		} else {
			Component.literal(tagLocation.toString())
		}
	}

	val VEC3_STREAM_CODEC: StreamCodec<ByteBuf, Vec3> = object : StreamCodec<ByteBuf, Vec3> {
		override fun decode(buffer: ByteBuf): Vec3 = Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble())
		override fun encode(buffer: ByteBuf, value: Vec3) {
			buffer.writeDouble(value.x)
			buffer.writeDouble(value.y)
			buffer.writeDouble(value.z)
		}
	}

	val UUID_CODEC: Codec<UUID> = Codec.STRING.xmap(
		UUID::fromString,
		UUID::toString
	)

	val UUID_STREAM_CODEC: StreamCodec<ByteBuf, UUID> = ByteBufCodecs.STRING_UTF8.map(
		UUID::fromString,
		UUID::toString
	)

	val STACK_LIST_STREAM_CODEC: StreamCodec<ByteBuf, NonNullList<ItemStack>> =
		ByteBufCodecs.fromCodec(NonNullList.codecOf(ItemStack.OPTIONAL_CODEC))

	fun Player.status(message: Component) = this.displayClientMessage(message, true)

	fun BlockPos.toTag(): CompoundTag {
		val tag = CompoundTag()
		tag.putInt("x", this.x)
		tag.putInt("y", this.y)
		tag.putInt("z", this.z)
		return tag
	}

	fun CompoundTag.toBlockPos(): BlockPos {
		return BlockPos(
			this.getInt("x"),
			this.getInt("y"),
			this.getInt("z")
		)
	}

	fun RandomSource.nextRange(min: Float, max: Float): Float = Mth.lerp(nextFloat(), min, max)
	fun RandomSource.nextRange(min: Double, max: Double): Double = Mth.lerp(nextDouble(), min, max)

}