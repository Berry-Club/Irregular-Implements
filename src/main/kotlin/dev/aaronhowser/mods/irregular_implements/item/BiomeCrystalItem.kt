package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.ChatFormatting
import net.minecraft.client.resources.language.I18n
import net.minecraft.core.Holder
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.biome.Biome

class BiomeCrystalItem : Item(
    Properties()
        .rarity(Rarity.UNCOMMON)
) {

    companion object {

        fun getAllCrystals(registries: HolderLookup.Provider): List<ItemStack> {
            return registries
                .lookupOrThrow(Registries.BIOME)
                .listElements()
                .map { getCrystal(it) }
                .toList()
        }

        fun getCrystal(biomeHolder: Holder<Biome>): ItemStack {
            val stack = ModItems.BIOME_CRYSTAL.toStack()
            stack.set(ModDataComponents.BIOME, biomeHolder)
            return stack
        }

    }

    override fun appendHoverText(
        stack: ItemStack,
        context: TooltipContext,
        tooltipComponents: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        val biomeHolder = stack.get(ModDataComponents.BIOME)
        val biome = biomeHolder?.key ?: return

        val probableTranslationKey = "biome.${biome.location().namespace}.${biome.location().path}"
        val hasTranslation = I18n.exists(probableTranslationKey)

        val component = if (hasTranslation) {
            Component.translatable(probableTranslationKey)
        } else {
            Component.literal(biome.location().toString())
        }.withStyle(ChatFormatting.GRAY)

        tooltipComponents.add(component)
    }

}