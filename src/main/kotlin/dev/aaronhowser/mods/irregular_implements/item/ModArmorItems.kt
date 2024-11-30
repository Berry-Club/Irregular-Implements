package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModFluidTagsProvider
import dev.aaronhowser.mods.irregular_implements.registries.ModArmorMaterials
import dev.aaronhowser.mods.irregular_implements.registries.ModDataComponents
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.Item
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.EntityCollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class ModArmorItems(
    material: Holder<ArmorMaterial>,
    type: Type,
    properties: Properties
) : ArmorItem(material, type, properties) {

    companion object {

        // These are lazy so they don't freak out due
        // to data components not being available yet when
        // BlockStateBaseMixin calls FluidWalkingBoots#checkCollisionShape

        val WATER_WALKING_BOOTS by lazy {
            ModArmorItems(
                ModArmorMaterials.WATER_WALKING,
                Type.BOOTS,
                Properties()
                    .durability(Type.BOOTS.getDurability(15))
                    .component(
                        ModDataComponents.FLUID_TAGS,
                        listOf(ModFluidTagsProvider.ALLOWS_WATER_WALKING)
                    )
            )
        }

        val OBSIDIAN_WATER_WALKING_BOOTS by lazy {
            ModArmorItems(
                ModArmorMaterials.OBSIDIAN_WATER_WALKING,
                Type.BOOTS,
                Properties()
                    .durability(Type.BOOTS.getDurability(15))
                    .component(
                        ModDataComponents.FLUID_TAGS,
                        listOf(ModFluidTagsProvider.ALLOWS_WATER_WALKING)
                    )
            )
        }

        val LAVA_WADERS by lazy {
            ModArmorItems(
                ModArmorMaterials.LAVA_WADERS,
                Type.BOOTS,
                Properties()
                    .durability(Type.BOOTS.getDurability(15))
                    .component(
                        ModDataComponents.FLUID_TAGS,
                        listOf(ModFluidTagsProvider.ALLOWS_LAVA_WALKING)
                    )
            )
        }

        val SUPER_LUBRICANT_BOOTS by lazy {
            ArmorItem(
                ModArmorMaterials.SUPER_LUBRICANT,
                Type.BOOTS,
                Properties().durability(Type.BOOTS.getDurability(15))
            )
        }

        val MAGIC_HOOD by lazy {
            ArmorItem(
                ModArmorMaterials.MAGIC,
                Type.HELMET,
                Properties().durability(Type.HELMET.getDurability(15))
            )
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

            if (entity.isCrouching || entity.isUnderWater) return null

            val footArmor = entity.getItemBySlot(EquipmentSlot.FEET)
            val fluidTags = footArmor.get(ModDataComponents.FLUID_TAGS) ?: return null

            val canStandOnFluid = fluidTags.any { fluidState.`is`(it) }
            if (!canStandOnFluid) return null

            val shape = SHAPES.computeIfAbsent(fluidHeight) {
                Block.box(0.0, 0.0, 0.0, 16.0, (it * 16).toDouble(), 16.0)
            }

            if (!context.isAbove(shape, pos, true)) return null

            return if (original == null) shape else Shapes.or(original, shape)
        }

        private val SHAPES: MutableMap<Float, VoxelShape> = HashMap()
    }

}