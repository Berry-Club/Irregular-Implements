package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModFluidTagsProvider
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModItemTagsProvider
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.EntityCollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class FluidWalkingBoots(
    material: Holder<ArmorMaterial>,
    type: Type,
    properties: Properties
) : ArmorItem(material, type, properties) {

    companion object {

        val WATER_WALKING_BOOTS = FluidWalkingBoots(
            ArmorMaterials.CHAIN,
            Type.BOOTS
        )

        val OBSIDIAN_WATER_WALKING_BOOTS = FluidWalkingBoots(
            ArmorMaterials.CHAIN,
            Type.BOOTS
        )

        val LAVA_WADERS = FluidWalkingBoots(
            ArmorMaterials.CHAIN,
            Type.BOOTS
        )

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

            if (entity.isCrouching || entity.isUnderWater) return null

            val footArmor = entity.getItemBySlot(EquipmentSlot.FEET)

            val standOnWater = footArmor.`is`(ModItemTagsProvider.WATER_WALKING_BOOTS)
            val standOnLava = footArmor.`is`(ModItemTagsProvider.LAVA_WALKING_BOOTS)

            if (standOnWater && !fluidState.`is`(ModFluidTagsProvider.ALLOWS_WATER_WALKING)) return null
            if (standOnLava && !fluidState.`is`(ModFluidTagsProvider.ALLOWS_LAVA_WALKING)) return null

            val shape = SHAPES.computeIfAbsent(
                fluidHeight
            ) { h: Float -> Block.box(0.0, 0.0, 0.0, 16.0, (h * 16).toDouble(), 16.0) }

            if (!context.isAbove(shape, pos, true)) return null

            return if ((original == null)
            ) shape
            else Shapes.or(original, shape)
        }

        private val SHAPES: MutableMap<Float, VoxelShape> = HashMap()
    }

}