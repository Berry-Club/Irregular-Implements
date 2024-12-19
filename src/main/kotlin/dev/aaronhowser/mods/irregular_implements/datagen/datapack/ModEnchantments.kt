package dev.aaronhowser.mods.irregular_implements.datagen.datapack

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModItemTagsProvider
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.Holder
import net.minecraft.core.HolderSet
import net.minecraft.core.RegistryAccess
import net.minecraft.core.component.DataComponentMap
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.item.enchantment.Enchantment
import java.util.*

object ModEnchantments {

    val MAGNETIC: ResourceKey<Enchantment> =
        ResourceKey.create(Registries.ENCHANTMENT, OtherUtil.modResource("magnetic"))

    fun getHolder(enchantment: ResourceKey<Enchantment>, registryAccess: RegistryAccess): Holder.Reference<Enchantment> {
        return registryAccess.registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(enchantment)
    }

    fun bootstrap(context: BootstrapContext<Enchantment>) {

        context.register(
            MAGNETIC,
            Enchantment(
                ModLanguageProvider.Misc.MAGNETIC_NAME.toComponent(),
                Enchantment.EnchantmentDefinition(
                    context.lookup(Registries.ITEM).getOrThrow(ModItemTagsProvider.ENCHANTABLE_MAGNETIC),
                    Optional.empty(),
                    3,
                    1,
                    Enchantment.Cost(5, 8),
                    Enchantment.Cost(55, 8),
                    2,
                    listOf(EquipmentSlotGroup.HAND)
                ),
                HolderSet.empty(),
                DataComponentMap
                    .builder()
                    .build()
            )
        )

    }

}