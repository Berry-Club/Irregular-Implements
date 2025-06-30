package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.entity.TemporaryFlooFireplaceEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModParticleTypes
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

class FlooTokenItem(properties: Properties) : Item(properties) {

	override fun onEntityItemUpdate(stack: ItemStack, entity: ItemEntity): Boolean {

		val level = entity.level()

		if (level.isClientSide) {
			if (entity.age > 30) {
				level.addParticle(
					ModParticleTypes.FLOO_FLAME.get(),
					entity.x, entity.y, entity.z,
					0.0, level.random.nextDouble() * 0.5 + 0.1, 0.0
				)
			}

			return false
		}

		if (entity.age >= 100 && entity.onGround()) {
			val otherFireplaces = level.getEntitiesOfClass(
				TemporaryFlooFireplaceEntity::class.java,
				entity.boundingBox.inflate(5.0)
			)

			if (otherFireplaces.isEmpty()) {
				val tempFireplace = TemporaryFlooFireplaceEntity(level, entity.position())
				level.addFreshEntity(tempFireplace)
				entity.item.shrink(1)
			}
		}

		return false
	}

}