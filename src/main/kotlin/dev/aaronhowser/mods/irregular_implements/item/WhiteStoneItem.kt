package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.Level

class WhiteStoneItem : Item(
    Properties()
        .rarity(Rarity.UNCOMMON)
        .stacksTo(1)
        .component(ModDataComponents.ENABLED, false)
) {

    override fun inventoryTick(stack: ItemStack, level: Level, entity: Entity, slotId: Int, isSelected: Boolean) {
        if (level.isClientSide
            || level.gameTime % 20 != 0L
            || stack.get(ModDataComponents.ENABLED.get()).isTrue
            || level.moonPhase != 0
            || !level.canSeeSky(entity.blockPosition())
            || level.dayTime !in 17000..19000
        ) return

        stack.set(ModDataComponents.ENABLED, true)
    }

    override fun isFoil(stack: ItemStack): Boolean {
        return stack.get(ModDataComponents.ENABLED.get()).isTrue
    }

}