package dev.aaronhowser.mods.irregular_implements.datagen.loot

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider
import net.neoforged.neoforge.common.loot.AddTableLootModifier
import net.neoforged.neoforge.common.loot.LootTableIdCondition
import java.util.concurrent.CompletableFuture

class ModGlobalLootModifierProvider(
    packOutput: PackOutput,
    registries: CompletableFuture<HolderLookup.Provider>
) : GlobalLootModifierProvider(packOutput, registries, IrregularImplements.ID) {
    override fun start() {
        add(
            "add_to_simple_dungeon",
            AddTableLootModifier(
                arrayOf(LootTableIdCondition.builder(OtherUtil.modResource("test")).build()),
                ResourceKey.create(Registries.LOOT_TABLE, OtherUtil.modResource("test2"))
            )
        )

    }

    companion object {
        private const val LAVA_CHARM_CHANCE = 5

        private val lavaCharmPool = LootPool.lootPool()
            .setRolls(ConstantValue.exactly(1f))
            .add(EmptyLootItem.emptyItem().setWeight(100 - LAVA_CHARM_CHANCE))
            .add(LootItem.lootTableItem(ModItems.LAVA_CHARM.get()).setWeight(LAVA_CHARM_CHANCE))
    }

}