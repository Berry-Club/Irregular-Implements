package dev.aaronhowser.mods.irregular_implements.util

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import net.minecraft.ChatFormatting
import net.minecraft.client.resources.language.I18n
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.monster.Spider
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.alchemy.PotionContents
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.saveddata.SavedData
import net.minecraft.world.level.storage.DimensionDataStorage
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.Vec3

object OtherUtil {

	@JvmStatic
	fun modResource(path: String): ResourceLocation =
		ResourceLocation.fromNamespaceAndPath(IrregularImplements.MOD_ID, path)

	fun getPotionStack(potion: Holder<Potion>): ItemStack {
		return PotionContents.createItemStack(Items.POTION, potion)
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

	fun lerpColor(progress: Float, start: Int, end: Int): Int {
		val startR = (start shr 16) and 0xFF
		val startG = (start shr 8) and 0xFF
		val startB = start and 0xFF

		val endR = (end shr 16) and 0xFF
		val endG = (end shr 8) and 0xFF
		val endB = end and 0xFF

		val r = Mth.lerp(progress, startR.toFloat(), endR.toFloat()).toInt()
		val g = Mth.lerp(progress, startG.toFloat(), endG.toFloat()).toInt()
		val b = Mth.lerp(progress, startB.toFloat(), endB.toFloat()).toInt()

		return (r shl 16) or (g shl 8) or b
	}

	@JvmStatic
	fun shouldSpiderNotClimb(spider: Spider): Boolean {
		if (!spider.horizontalCollision) return false

		val adjacentPositions = BlockPos.betweenClosed(
			spider.blockPosition().offset(-1, 0, -1),
			spider.blockPosition().offset(1, 0, 1)
		)

		val level = spider.level()

		for (pos in adjacentPositions) {
			if (level.getBlockState(pos).`is`(ModBlockTagsProvider.SUPER_LUBRICATED)) {
				return true
			}
		}

		return false
	}

	fun <T : SavedData> updateSavedDataLocation(
		storage: DimensionDataStorage,
		factory: SavedData.Factory<T>,
		newFileName: String,
		oldFileName: String
	): T {
		val newData = storage.get(factory, newFileName)
		val oldData = storage.get(factory, oldFileName)

		if (newData != null) {
			return newData
		}

		if (oldData != null) {
			storage.set(newFileName, oldData)
			oldData.setDirty(true)
			return oldData
		}

		// Neither exists → create new under the new name
		return storage.computeIfAbsent(factory, newFileName)
	}

}