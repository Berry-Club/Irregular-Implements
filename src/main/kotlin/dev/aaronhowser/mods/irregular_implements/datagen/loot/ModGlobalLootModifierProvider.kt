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

        val simpleDungeon = arrayOf(LootTableIdCondition.Builder(BuiltInLootTables.SIMPLE_DUNGEON.location()).build())
        val netherBridge = arrayOf(LootTableIdCondition.Builder(BuiltInLootTables.NETHER_BRIDGE.location()).build())
        val abandonedMineshaft = arrayOf(LootTableIdCondition.Builder(BuiltInLootTables.ABANDONED_MINESHAFT.location()).build())
        val villageWeaponsmith = arrayOf(LootTableIdCondition.Builder(BuiltInLootTables.VILLAGE_WEAPONSMITH.location()).build())
        val strongholdCorridor = arrayOf(LootTableIdCondition.Builder(BuiltInLootTables.STRONGHOLD_CORRIDOR.location()).build())
        val jungleTemple = arrayOf(LootTableIdCondition.Builder(BuiltInLootTables.JUNGLE_TEMPLE.location()).build())
        val endCityTreasure = arrayOf(LootTableIdCondition.Builder(BuiltInLootTables.END_CITY_TREASURE.location()).build())

        add(
            "lava_charm_dungeon",
            AddTableLootModifier(
                simpleDungeon,
                ModChestLootSubprovider.LAVA_CHARM_DUNGEON
            )
        )

        add(
            "lava_charm_nether",
            AddTableLootModifier(
                netherBridge,
                ModChestLootSubprovider.LAVA_CHARM_NETHER
            )
        )

        add(
            "summoning_pendulum",
            AddTableLootModifier(
                simpleDungeon,
                ModChestLootSubprovider.SUMMONING_PENDULUM
            )
        )

        add(
            "magic_hood_dungeon",
            AddTableLootModifier(
                simpleDungeon,
                ModChestLootSubprovider.MAGIC_HOOD_DUNGEON
            )
        )

        add(
            "slime_cube",
            AddTableLootModifier(
                simpleDungeon,
                ModChestLootSubprovider.SLIME_CUBE
            )
        )

        add(
            "numbered_coil_dungeon",
            AddTableLootModifier(
                simpleDungeon,
                ModChestLootSubprovider.NUMBERED_COIL_DUNGEON
            )
        )

    }

}