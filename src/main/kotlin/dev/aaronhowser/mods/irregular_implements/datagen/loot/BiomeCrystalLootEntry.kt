package dev.aaronhowser.mods.irregular_implements.datagen.loot

import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.storage.loot.LootContext
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer
import net.minecraft.world.level.storage.loot.functions.LootItemFunction
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition
import java.util.function.Consumer

class BiomeCrystalLootEntry(
    weight: Int,
    quality: Int,
    conditions: MutableList<LootItemCondition>,
    functions: MutableList<LootItemFunction>
) : LootPoolSingletonContainer(weight, quality, conditions, functions) {

    companion object {
        fun get(): Builder<*> {
            return simpleBuilder(::BiomeCrystalLootEntry)
        }
    }

    override fun getType(): LootPoolEntryType {
        TODO("Not yet implemented")
    }

    override fun createItemStack(stackConsumer: Consumer<ItemStack>, lootContext: LootContext) {
        TODO("Not yet implemented")
    }
}