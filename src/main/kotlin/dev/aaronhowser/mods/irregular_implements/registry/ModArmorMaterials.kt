package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.Holder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.tags.TagKey
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

// Inspired largely by Advent of Ascension's class
object ModArmorMaterials {

    val ARMOR_MATERIAL_REGISTRY: DeferredRegister<ArmorMaterial> =
        DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, IrregularImplements.ID)

    val WATER_WALKING: DeferredHolder<ArmorMaterial, ArmorMaterial> =
        register("water_walking", Builder().repair(Tags.Items.INGOTS_IRON).boot(1))

    val OBSIDIAN_WATER_WALKING: DeferredHolder<ArmorMaterial, ArmorMaterial> =
        register("obsidian_water_walking", Builder().repair(Tags.Items.INGOTS_IRON).boot(1))

    val LAVA_WADERS: DeferredHolder<ArmorMaterial, ArmorMaterial> =
        register("lava_waders", Builder().repair(Tags.Items.INGOTS_IRON).boot(1))

    val MAGIC: DeferredHolder<ArmorMaterial, ArmorMaterial> =
        register("magic", Builder().repair(Tags.Items.LEATHERS).helmet(1))


    private fun register(id: String, builder: Builder): DeferredHolder<ArmorMaterial, ArmorMaterial> {
        val layer = ArmorMaterial.Layer(OtherUtil.modResource(id))

        return ARMOR_MATERIAL_REGISTRY.register(id, Supplier { builder.build(layer) })
    }

    private class Builder {
        private var bootsArmour = 0
        private var legsArmour = 0
        private var chestplateArmour = 0
        private var helmetArmour = 0
        private var toughness = 0f
        private var knockbackResist = 0f
        private var enchantValue = 10
        private var equipSound: Holder<SoundEvent> = SoundEvents.ARMOR_EQUIP_GENERIC
        private var repairIngredient = Supplier { Ingredient.EMPTY }

        fun helmet(armour: Int): Builder {
            helmetArmour = armour
            return this
        }

        fun boot(armour: Int): Builder {
            bootsArmour = armour
            return this
        }

        fun repair(tag: TagKey<Item>): Builder {
            repairIngredient = Supplier { Ingredient.of(tag) }
            return this
        }

        fun build(layer: ArmorMaterial.Layer): ArmorMaterial {
            return ArmorMaterial(
                mapOf(
                    ArmorItem.Type.BOOTS to bootsArmour,
                    ArmorItem.Type.LEGGINGS to legsArmour,
                    ArmorItem.Type.CHESTPLATE to chestplateArmour,
                    ArmorItem.Type.HELMET to helmetArmour
                ),
                enchantValue,
                equipSound,
                repairIngredient,
                listOf(layer),
                toughness,
                knockbackResist
            )
        }
    }

}