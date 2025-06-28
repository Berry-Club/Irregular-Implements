package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModCurioProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModFluidTagsProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.network.chat.Component
import net.minecraft.tags.DamageTypeTags
import net.minecraft.util.Mth
import net.minecraft.world.damagesource.FallLocation
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.*
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.EntityCollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent
import net.neoforged.neoforge.registries.DeferredItem
import top.theillusivec4.curios.api.CuriosApi

object ModArmorItems {

	val MAGIC_HOOD_DEFAULT_PROPERTIES: Item.Properties =
		Item.Properties()
			.durability(ArmorItem.Type.HELMET.getDurability(15))
			.rarity(Rarity.RARE)

	val WATER_WALKING_BOOTS_DEFAULT_PROPERTIES: Item.Properties =
		Item.Properties()
			.durability(ArmorItem.Type.BOOTS.getDurability(15))
			.rarity(Rarity.RARE)
			.component(
				ModDataComponents.FLUID_TAGS,
				listOf(ModFluidTagsProvider.ALLOWS_WATER_WALKING)
			)

	val OBSIDIAN_WATER_WALKING_BOOTS_DEFAULT_PROPERTIES: Item.Properties =
		Item.Properties()
			.durability(ArmorItem.Type.BOOTS.getDurability(15))
			.rarity(Rarity.RARE)
			.fireResistant()
			.component(
				ModDataComponents.FLUID_TAGS,
				listOf(ModFluidTagsProvider.ALLOWS_WATER_WALKING)
			)

	val LAVA_WADERS_DEFAULT_PROPERTIES: Item.Properties =
		Item.Properties()
			.durability(ArmorItem.Type.BOOTS.getDurability(15))
			.rarity(Rarity.RARE)
			.fireResistant()
			.component(
				ModDataComponents.FLUID_TAGS,
				listOf(
					ModFluidTagsProvider.ALLOWS_LAVA_WALKING,
					ModFluidTagsProvider.ALLOWS_WATER_WALKING
				)
			)
			.component(ModDataComponents.CHARGE, LavaCharmItem.MAX_CHARGE)
			.component(ModDataComponents.COOLDOWN, 0)

	val SPECTRE_HELMET_DEFAULT_PROPERTIES: Item.Properties =
		Item.Properties()
			.durability(Mth.floor(Items.DIAMOND_HELMET.defaultInstance.maxDamage * 1.25))
			.rarity(Rarity.UNCOMMON)

	val SPECTRE_CHESTPLATE_DEFAULT_PROPERTIES: Item.Properties =
		Item.Properties()
			.durability(Mth.floor(Items.DIAMOND_CHESTPLATE.defaultInstance.maxDamage * 1.25))
			.rarity(Rarity.UNCOMMON)

	val SPECTRE_LEGGINGS_DEFAULT_PROPERTIES: Item.Properties =
		Item.Properties()
			.durability(Mth.floor(Items.DIAMOND_LEGGINGS.defaultInstance.maxDamage * 1.25))
			.rarity(Rarity.UNCOMMON)

	val SPECTRE_BOOTS_DEFAULT_PROPERTIES: Item.Properties =
		Item.Properties()
			.durability(Mth.floor(Items.DIAMOND_BOOTS.defaultInstance.maxDamage * 1.25))
			.rarity(Rarity.UNCOMMON)

	fun registerArmorItem(
		name: String,
		material: Holder<ArmorMaterial>,
		type: ArmorItem.Type,
		properties: Item.Properties
	): DeferredItem<ArmorItem> {
		return ModItems.ITEM_REGISTRY.registerItem(name) { ArmorItem(material, type, properties) }
	}

	fun shouldEntityStandOnFluid(livingEntity: LivingEntity, fluidState: FluidState): Boolean {
		if (livingEntity.isCrouching || livingEntity.isUnderWater) return false

		val footArmor = livingEntity.getItemBySlot(EquipmentSlot.FEET)
		val fluidTags = footArmor.get(ModDataComponents.FLUID_TAGS) ?: return false

		return fluidTags.any { fluidState.`is`(it) }
	}

	@JvmStatic
	fun checkCollisionShape(
		level: BlockGetter,
		pos: BlockPos,
		context: CollisionContext,
		original: VoxelShape?
	): VoxelShape? {
		if (context !is EntityCollisionContext) return null

		val fluidState = level.getFluidState(pos)
		val fluidHeight = fluidState.getHeight(level, pos)
		if (fluidHeight <= 0) return null

		val entity = context.entity as? LivingEntity ?: return null
		if (!shouldEntityStandOnFluid(entity, fluidState)) return null

		val shape = FLUID_SHAPES.computeIfAbsent(fluidHeight) {
			Block.box(0.0, 0.0, 0.0, 16.0, (it * 16).toDouble(), 16.0)
		}

		if (!context.isAbove(shape, pos, true)) return null

		return if (original == null) shape else Shapes.or(original, shape)
	}

	private val FLUID_SHAPES: MutableMap<Float, VoxelShape> = HashMap()

	fun tooltip(event: ItemTooltipEvent) {
		if (event.itemStack.has(ModDataComponents.LUBRICATED)) {
			event.toolTip.add(
				ModLanguageProvider.Tooltips.LUBRICATED
					.toComponent()
					.withColor(0xFCF4DD)
			)
		}
	}

	@JvmField
	val FLUID_BOOT_FALL = FallLocation("fluid_boot_fall")

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

		val bootWasResponsible = bootArmor.get(ModDataComponents.FLUID_TAGS)?.any { fluidBelow.`is`(it) }.isTrue
		return if (bootWasResponsible) ModLanguageProvider.Messages.FLUID_FALL_DEATH_BOOT.toComponent(
			entity.displayName ?: entity.name,
			fluidBelow.fluidType.description,
			bootArmor.displayName
		) else ModLanguageProvider.Messages.FLUID_FALL_DEATH_GENERIC.toComponent(
			entity.displayName ?: entity.name,
			fluidBelow.fluidType.description,
		)
	}

	fun tryBlockFireDamage(event: LivingIncomingDamageEvent) {
		if (event.isCanceled) return

		val target = event.entity
		val damageSource = event.source

		if (!damageSource.`is`(DamageTypeTags.IS_FIRE)) return

		val amount = event.amount
		val chance = (amount * amount * amount) / 100

		if (target.random.nextFloat() <= chance) return

		if (canBlockFireDamage(target)) event.isCanceled = true
	}

	private fun canBlockFireDamage(entity: LivingEntity): Boolean {

		val footItem = entity.getItemBySlot(EquipmentSlot.FEET)
		if (footItem.`is`(ModItems.LAVA_WADERS)
			|| footItem.`is`(ModItems.OBSIDIAN_WATER_WALKING_BOOTS)
		) return true

		var goodCurio = false
		CuriosApi.getCuriosInventory(entity).ifPresent { inventory ->
			inventory.getStacksHandler(ModCurioProvider.RING_SLOT).ifPresent { ringSlotHandler ->
				for (i in 0 until ringSlotHandler.slots) {
					val stack = ringSlotHandler.stacks.getStackInSlot(i)
					if (stack.`is`(ModItems.OBSIDIAN_SKULL_RING)) {
						goodCurio = true
						break
					}
				}
			}
		}
		if (goodCurio) return true

		if (entity is Player && entity.inventory.items.any { it.`is`(ModItems.OBSIDIAN_SKULL) }) return true

		return entity.handSlots.any { it.`is`(ModItems.OBSIDIAN_SKULL) }
	}


}