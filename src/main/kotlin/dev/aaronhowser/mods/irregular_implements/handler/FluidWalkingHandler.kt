package dev.aaronhowser.mods.irregular_implements.handler

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isFluid
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModMessageLang
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.tags.TagKey
import net.minecraft.world.damagesource.FallLocation
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.EntityCollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import top.theillusivec4.curios.api.CuriosApi

object FluidWalkingHandler {

	private val FLUID_SHAPES: MutableMap<Float, VoxelShape> = mutableMapOf()

	@JvmField
	val FLUID_BOOT_FALL = FallLocation("fluid_boot_fall")

	fun shouldEntityStandOnFluid(livingEntity: LivingEntity, fluidState: FluidState): Boolean {
		val fluidsEntityCanStandOn = getFluidsEntityCanStandOn(livingEntity)
		return fluidsEntityCanStandOn.any { fluidState.isFluid(it) }
	}

	fun getFluidsEntityCanStandOn(livingEntity: LivingEntity): Set<TagKey<Fluid>> {
		if (livingEntity.isCrouching || livingEntity.isUnderWater) return emptySet()

		val fluidTags = mutableSetOf<TagKey<Fluid>>()

		val bodySlots = EquipmentSlot.entries - EquipmentSlot.MAINHAND - EquipmentSlot.OFFHAND
		for (slot in bodySlots) {
			val armorItem = livingEntity.getItemBySlot(slot)
			fluidTags.addAll(
				armorItem.getOrDefault(
					ModDataComponents.CAN_STAND_ON_FLUIDS,
					emptyList()
				)
			)
		}

		CuriosApi.getCuriosInventory(livingEntity).ifPresent {
			for (slot in 0 until it.equippedCurios.slots) {
				val stack = it.equippedCurios.getStackInSlot(slot)
				fluidTags.addAll(stack.getOrDefault(ModDataComponents.CAN_STAND_ON_FLUIDS, emptyList()))
			}
		}

		return fluidTags
	}

	@JvmStatic
	fun checkCollisionShape(
		level: BlockGetter,
		pos: BlockPos,
		context: CollisionContext,
		original: VoxelShape?
	): VoxelShape? {
		if (context !is EntityCollisionContext) return null
		val entity = context.entity as? LivingEntity ?: return null

		val fluidState = level.getFluidState(pos)
		val fluidHeight = fluidState.getHeight(level, pos)
		if (fluidHeight <= 0) return null

		if (!shouldEntityStandOnFluid(entity, fluidState)) return null

		val shape = FLUID_SHAPES.computeIfAbsent(fluidHeight) {
			Block.box(0.0, 0.0, 0.0, 16.0, (it * 16).toDouble(), 16.0)
		}

		if (!context.isAbove(shape, pos, true)) return null

		return if (original == null) shape else Shapes.or(original, shape)
	}

	@JvmStatic
	fun fluidWalkingFallLocation(entity: LivingEntity): FallLocation? {
		val fluidBelow = entity.level().getFluidState(entity.blockPosition())
		if (!shouldEntityStandOnFluid(entity, fluidBelow)) return null

		return FLUID_BOOT_FALL
	}

	@JvmStatic
	fun fluidWalkingDeathMessage(entity: LivingEntity): Component {
		val fluidBelow = entity.level().getFluidState(entity.blockPosition())
		val bootArmor = entity.getItemBySlot(EquipmentSlot.FEET)

		val bootWasResponsible = bootArmor
			.getOrDefault(ModDataComponents.CAN_STAND_ON_FLUIDS, emptyList())
			.any { fluidBelow.`is`(it) }

		return if (bootWasResponsible) {
			ModMessageLang.FLUID_FALL_DEATH_BOOT.toComponent(
				entity.displayName ?: entity.name,
				fluidBelow.fluidType.description,
				bootArmor.displayName
			)
		} else {
			ModMessageLang.FLUID_FALL_DEATH_GENERIC.toComponent(
				entity.displayName ?: entity.name,
				fluidBelow.fluidType.description,
			)
		}
	}

}