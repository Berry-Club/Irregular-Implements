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
			"summoning_pendulum_dungeon",
			AddTableLootModifier(
				simpleDungeon,
				ModChestLootSubprovider.SUMMONING_PENDULUM_DUNGEON
			)
		)

		add(
			"summoning_pendulum_stronghold",
			AddTableLootModifier(
				strongholdCorridor,
				ModChestLootSubprovider.SUMMONING_PENDULUM_STRONGHOLD
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
			"magic_hood_weaponsmith",
			AddTableLootModifier(
				villageWeaponsmith,
				ModChestLootSubprovider.MAGIC_HOOD_WEAPONSMITH
			)
		)

		add(
			"slime_cube_dungeon",
			AddTableLootModifier(
				simpleDungeon,
				ModChestLootSubprovider.SLIME_CUBE_DUNGEON
			)
		)

		add(
			"slime_cube_jungle_temple",
			AddTableLootModifier(
				jungleTemple,
				ModChestLootSubprovider.SLIME_CUBE_JUNGLE_TEMPLE
			)
		)

		add(
			"numbered_coil_dungeon",
			AddTableLootModifier(
				simpleDungeon,
				ModChestLootSubprovider.NUMBERED_COIL_DUNGEON
			)
		)

		add(
			"numbered_coil_mineshaft",
			AddTableLootModifier(
				abandonedMineshaft,
				ModChestLootSubprovider.NUMBERED_COIL_MINESHAFT
			)
		)

		add(
			"numbered_coil_end_city",
			AddTableLootModifier(
				endCityTreasure,
				ModChestLootSubprovider.NUMBERED_COIL_END_CITY
			)
		)

		add(
			"magic_bean_dungeon",
			AddTableLootModifier(
				strongholdCorridor,
				ModChestLootSubprovider.MAGIC_BEAN_DUNGEON
			)
		)

		val allChestLootTables = BuiltInLootTables.all().filter { it.location().path.startsWith("chests/") }
		for (lootTable in allChestLootTables) {
			val array = arrayOf(LootTableIdCondition.Builder(lootTable.location()).build())
			val string = lootTable.location().path
				.replace("chests/", "")
				.replace('/', '_')

			add(
				"biome_crystal_$string",
				AddTableLootModifier(
					array,
					ModChestLootSubprovider.BIOME_CRYSTAL
				)
			)
		}

	}

}