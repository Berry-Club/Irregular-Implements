package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isServerSide
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB

class EntityDetectorBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntities.ENTITY_DETECTOR.get(), pos, blockState) {

	var filter: Filter = Filter.ALL
		private set

	var xRadius: Int = 1
		private set
	var yRadius: Int = 1
		private set
	var zRadius: Int = 1
		private set

	var inverted: Boolean = false
		private set

	var isActive: Boolean = false
		private set

	fun tick() {
		val level = level ?: return
		if (level.gameTime % 10 != 0L) return

		val filterFoundEntities = level.getEntities(
			null,
			AABB(blockPos).inflate(xRadius.toDouble(), yRadius.toDouble(), zRadius.toDouble()),
			filter.predicate(ItemStack.EMPTY)
		).isNotEmpty()

		val shouldBePowered = if (inverted) !filterFoundEntities else filterFoundEntities
		if (isActive == shouldBePowered) return

		isActive = shouldBePowered
		setChanged()

		level.updateNeighborsAt(blockPos, blockState.block)
	}

	fun cycleFilter() {
		val nextOrdinal = (filter.ordinal + 1) % Filter.entries.size
		filter = Filter.entries[nextOrdinal]
		setChanged()
	}

	companion object {
		fun tick(
			level: Level,
			pos: BlockPos,
			state: BlockState,
			blockEntity: EntityDetectorBlockEntity
		) {
			if (level.isServerSide) blockEntity.tick()
		}
	}

	enum class Filter(
		val entityClass: Class<out Entity>?,
		val unlocalizedName: String
	) {
		ALL(Entity::class.java, "all"),
		LIVING(LivingEntity::class.java, "living"),
		ANIMAL(Animal::class.java, "animal"),
		MONSTER(Monster::class.java, "monster"),
		PLAYER(Player::class.java, "player"),
		ITEM(ItemEntity::class.java, "item"),
		CUSTOM(null, "custom")

		;

		fun predicate(filterStack: ItemStack): (Entity) -> Boolean {
			return { entity ->
				entityClass?.isAssignableFrom(entity.javaClass)
					?: (entity.type == filterStack.get(ModDataComponents.ENTITY_TYPE))
			}
		}
	}

}