package dev.aaronhowser.mods.irregular_implements.datagen.loot

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.world.level.storage.loot.BuiltInLootTables
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider
import net.neoforged.neoforge.common.loot.AddTableLootModifier
import net.neoforged.neoforge.common.loot.LootTableIdCondition
import java.util.concurrent.CompletableFuture

class ModGlobalLootModifierProvider(
    output: PackOutput,
    registries: CompletableFuture<HolderLookup.Provider>
) : GlobalLootModifierProvider(output, registries, IrregularImplements.ID) {

    override fun start() {

        add(
            "lava_charm",
            AddTableLootModifier(
                arrayOf(LootTableIdCondition.Builder(BuiltInLootTables.SIMPLE_DUNGEON.location()).build()),
                ModChestLootSubprovider.LAVA_CHARM
            )
        )

        add(
            "summoning_pendulum",
            AddTableLootModifier(
                arrayOf(LootTableIdCondition.Builder(BuiltInLootTables.SIMPLE_DUNGEON.location()).build()),
                ModChestLootSubprovider.SUMMONING_PENDULUM
            )
        )

        add(
            "magic_hood",
            AddTableLootModifier(
                arrayOf(LootTableIdCondition.Builder(BuiltInLootTables.SIMPLE_DUNGEON.location()).build()),
                ModChestLootSubprovider.MAGIC_HOOD
            )
        )

        add(
            "slime_cube",
            AddTableLootModifier(
                arrayOf(LootTableIdCondition.Builder(BuiltInLootTables.SIMPLE_DUNGEON.location()).build()),
                ModChestLootSubprovider.SLIME_CUBE
            )
        )

    }

}