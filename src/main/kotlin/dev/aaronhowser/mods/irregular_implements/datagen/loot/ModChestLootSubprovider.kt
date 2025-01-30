package dev.aaronhowser.mods.irregular_implements.datagen.loot

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.loot.LootTableSubProvider
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import java.util.function.BiConsumer

class ModChestLootSubprovider(
    registries: HolderLookup.Provider
) : LootTableSubProvider {

    override fun generate(output: BiConsumer<ResourceKey<LootTable>, LootTable.Builder>) {

        output.accept(
            LAVA_CHARM,
            LootTable.lootTable().withPool(lavaCharmPool)
        )

        output.accept(
            SUMMONING_PENDULUM,
            LootTable.lootTable().withPool(summoningPendulumPool)
        )

        output.accept(
            MAGIC_HOOD,
            LootTable.lootTable().withPool(magicHoodPool)
        )

        output.accept(
            SLIME_CUBE,
            LootTable.lootTable().withPool(slimeCubePool)
        )

    }

    companion object {
        private fun createPoolRk(name: String): ResourceKey<LootTable> {
            return ResourceKey.create(Registries.LOOT_TABLE, OtherUtil.modResource("chest/$name"))
        }

        private fun singleItemPool(item: Item, chance: Int) = LootPool.lootPool()
            .setRolls(ConstantValue.exactly(1f))
            .add(EmptyLootItem.emptyItem().setWeight(100 - chance))
            .add(LootItem.lootTableItem(item).setWeight(chance))

        val LAVA_CHARM = createPoolRk("lava_charm")
        private const val LAVA_CHARM_CHANCE = 5
        private val lavaCharmPool = singleItemPool(ModItems.LAVA_CHARM.get(), LAVA_CHARM_CHANCE)

        val SUMMONING_PENDULUM = createPoolRk("summoning_pendulum")
        private const val SUMMONING_PENDULUM_CHANCE = 10
        private val summoningPendulumPool = singleItemPool(ModItems.SUMMONING_PENDULUM.get(), SUMMONING_PENDULUM_CHANCE)

        val MAGIC_HOOD = createPoolRk("magic_hood")
        private const val MAGIC_HOOD_CHANCE = 5
        private val magicHoodPool = singleItemPool(ModItems.MAGIC_HOOD.get(), MAGIC_HOOD_CHANCE)

        val SLIME_CUBE = createPoolRk("slime_cube")
        private const val SLIME_CUBE_CHANCE = 10
        private val slimeCubePool = singleItemPool(ModBlocks.SLIME_CUBE.asItem(), SLIME_CUBE_CHANCE)
    }

}