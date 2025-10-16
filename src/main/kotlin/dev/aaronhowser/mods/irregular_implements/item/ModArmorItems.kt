package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModMessageLang
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModFluidTagsProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.network.chat.Component
import net.minecraft.tags.DamageTypeTags
import net.minecraft.tags.TagKey
import net.minecraft.util.Mth
import net.minecraft.world.damagesource.FallLocation
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.*
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.EntityCollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent
import net.neoforged.neoforge.registries.DeferredItem
import top.theillusivec4.curios.api.CuriosApi
import java.util.function.Supplier
import kotlin.jvm.optionals.getOrNull

object ModArmorItems {

	fun registerArmorItem(
		name: String,
		material: Holder<ArmorMaterial>,
		type: ArmorItem.Type,
		properties: Supplier<Item.Properties>
	): DeferredItem<ArmorItem> {
		return ModItems.ITEM_REGISTRY.registerItem(name) { ArmorItem(material, type, properties.get()) }
	}

	fun registerArmorItem(
		name: String,
		material: Holder<ArmorMaterial>,
		type: ArmorItem.Type,
		properties: Item.Properties
	): DeferredItem<ArmorItem> {
		return ModItems.ITEM_REGISTRY.registerItem(name) { ArmorItem(material, type, properties) }
	}

	val MAGIC_HOOD_PROPERTIES: Item.Properties =
		Item.Properties()
			.durability(ArmorItem.Type.HELMET.getDurability(15))
			.rarity(Rarity.RARE)

	val WATER_WALKING_BOOTS_PROPERTIES: Supplier<Item.Properties> = Supplier {
		Item.Properties()
			.durability(ArmorItem.Type.BOOTS.getDurability(15))
			.rarity(Rarity.RARE)
			.component(
				ModDataComponents.CAN_STAND_ON_FLUIDS,
				listOf(ModFluidTagsProvider.ALLOWS_WATER_WALKING)
			)
	}

	val OBSIDIAN_WATER_WALKING_BOOTS_PROPERTIES: Supplier<Item.Properties> = Supplier {
		Item.Properties()
			.durability(ArmorItem.Type.BOOTS.getDurability(15))
			.rarity(Rarity.RARE)
			.fireResistant()
			.component(
				ModDataComponents.CAN_STAND_ON_FLUIDS,
				listOf(ModFluidTagsProvider.ALLOWS_WATER_WALKING)
			)
	}

	val LAVA_WADERS_PROPERTIES: Supplier<Item.Properties> = Supplier {
		Item.Properties()
			.durability(ArmorItem.Type.BOOTS.getDurability(15))
			.rarity(Rarity.RARE)
			.fireResistant()
			.component(
				ModDataComponents.CAN_STAND_ON_FLUIDS,
				listOf(
					ModFluidTagsProvider.ALLOWS_LAVA_WALKING,
					ModFluidTagsProvider.ALLOWS_WATER_WALKING
				)
			)
			.component(ModDataComponents.CHARGE, LavaCharmItem.MAX_CHARGE)
			.component(ModDataComponents.COOLDOWN, 0)
	}

	val SPECTRE_HELMET_PROPERTIES: Item.Properties =
		Item.Properties()
			.durability(Mth.floor(Items.DIAMOND_HELMET.defaultInstance.maxDamage * 1.25))
			.rarity(Rarity.UNCOMMON)


	val SPECTRE_CHESTPLATE_PROPERTIES: Item.Properties =
		Item.Properties()
			.durability(Mth.floor(Items.DIAMOND_CHESTPLATE.defaultInstance.maxDamage * 1.25))
			.rarity(Rarity.UNCOMMON)


	val SPECTRE_LEGGINGS_PROPERTIES: Item.Properties =
		Item.Properties()
			.durability(Mth.floor(Items.DIAMOND_LEGGINGS.defaultInstance.maxDamage * 1.25))
			.rarity(Rarity.UNCOMMON)


	val SPECTRE_BOOTS_PROPERTIES: Item.Properties =
		Item.Properties()
			.durability(Mth.floor(Items.DIAMOND_BOOTS.defaultInstance.maxDamage * 1.25))
			.rarity(Rarity.UNCOMMON)


	fun shouldEntityStandOnFluid(livingEntity: LivingEntity, fluidState: FluidState): Boolean {
		if (livingEntity.isCrouching || livingEntity.isUnderWater) return false

		val fluidTags = mutableListOf<TagKey<Fluid>>()

		val footArmor = livingEntity.getItemBySlot(EquipmentSlot.FEET)
		fluidTags.addAll(footArmor.getOrDefault(ModDataComponents.CAN_STAND_ON_FLUIDS, emptyList()))

		CuriosApi.getCuriosInventory(livingEntity).ifPresent {
			for (slot in 0 until it.equippedCurios.slots) {
				val stack = it.equippedCurios.getStackInSlot(slot)
				fluidTags.addAll(stack.getOrDefault(ModDataComponents.CAN_STAND_ON_FLUIDS, emptyList()))
			}
		}

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

	fun lubricatedTooltip(event: ItemTooltipEvent) {
		if (event.itemStack.has(ModDataComponents.LUBRICATED)) {
			event.toolTip.add(
				ModTooltipLang.LUBRICATED
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

		val hasObsidianSkullRing =
			CuriosApi.getCuriosInventory(entity)
				.getOrNull()
				?.isEquipped(ModItems.OBSIDIAN_SKULL.get())
				.isTrue

		if (hasObsidianSkullRing) return true
		if (entity is Player && entity.inventory.items.any { it.`is`(ModItems.OBSIDIAN_SKULL) }) return true

		return entity.handSlots.any { it.`is`(ModItems.OBSIDIAN_SKULL) }
	}

	fun isWearingFullSpectreArmor(entity: LivingEntity): Boolean {
		return entity.getItemBySlot(EquipmentSlot.HEAD).`is`(ModItems.SPECTRE_HELMET.get())
				&& entity.getItemBySlot(EquipmentSlot.CHEST).`is`(ModItems.SPECTRE_CHESTPLATE.get())
				&& entity.getItemBySlot(EquipmentSlot.LEGS).`is`(ModItems.SPECTRE_LEGGINGS.get())
				&& entity.getItemBySlot(EquipmentSlot.FEET).`is`(ModItems.SPECTRE_BOOTS.get())
	}

}