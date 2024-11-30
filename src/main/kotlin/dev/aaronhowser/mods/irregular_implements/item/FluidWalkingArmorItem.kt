package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModFluidTagsProvider
import dev.aaronhowser.mods.irregular_implements.registries.ModItems
import net.minecraft.core.Holder
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.neoforged.neoforge.event.tick.EntityTickEvent

class FluidWalkingArmorItem(
    material: Holder<ArmorMaterial>,
    type: Type,
    properties: Properties
) : ArmorItem(material, type, properties) {


    companion object {

        fun moveToSurface(event: EntityTickEvent.Post) {
            val entity = event.entity as? LivingEntity ?: return
            if (entity is Player && entity.isShiftKeyDown) return

            val footArmor = entity.getItemBySlot(EquipmentSlot.FEET)

            val requiredTag = if (footArmor.`is`(ModItems.WATER_WALKING_BOOTS)) {
                ModFluidTagsProvider.ALLOWS_WATER_WALKING
            } else if (footArmor.`is`(ModItems.LAVA_WADERS)) {
                ModFluidTagsProvider.ALLOWS_LAVA_WALKING
            } else {
                return
            }

            val footPos = entity.blockPosition()
            val level = entity.level()

            if (!level.getFluidState(footPos).`is`(requiredTag)) return

            var checkedPos = footPos
            while (level.getFluidState(checkedPos).`is`(requiredTag)) {
                checkedPos = checkedPos.above()
            }

            val blockState = level.getBlockState(checkedPos)
            val blockStateAbove = level.getBlockState(checkedPos.above())

            if (blockState.isAir && blockStateAbove.isAir) {
                entity.teleportTo(checkedPos.x + 0.5, checkedPos.y.toDouble() + 0.1, checkedPos.z + 0.5)
            }
        }

    }

}