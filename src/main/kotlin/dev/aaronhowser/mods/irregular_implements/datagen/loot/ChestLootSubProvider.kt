package dev.aaronhowser.mods.irregular_implements.datagen.loot

import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.loot.LootTableSubProvider
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import java.util.concurrent.CompletableFuture
import java.util.function.BiConsumer

class ChestLootSubProvider(
    registries: CompletableFuture<HolderLookup.Provider>
) : LootTableSubProvider {

    override fun generate(output: BiConsumer<ResourceKey<LootTable>, LootTable.Builder>) {

        output.accept(
            LAVA_CHARM_POOL,
            LootTable.lootTable().withPool(lavaCharmPool)
        )

    }

    companion object {
        private fun createPoolRk(name: String): ResourceKey<LootTable> {
            return ResourceKey.create(Registries.LOOT_TABLE, OtherUtil.modResource(name))
        }

        val LAVA_CHARM_POOL: ResourceKey<LootTable> = createPoolRk("chest/lava_charm")

        private const val LAVA_CHARM_CHANCE = 5
        private val lavaCharmPool = LootPool.lootPool()
            .setRolls(ConstantValue.exactly(1f))
            .add(EmptyLootItem.emptyItem().setWeight(100 - LAVA_CHARM_CHANCE))
            .add(LootItem.lootTableItem(ModItems.LAVA_CHARM.get()).setWeight(LAVA_CHARM_CHANCE))
    }

}