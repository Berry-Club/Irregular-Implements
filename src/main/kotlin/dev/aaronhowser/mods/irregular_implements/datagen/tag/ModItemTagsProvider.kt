package dev.aaronhowser.mods.irregular_implements.datagen.tag

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.add
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.ItemTagsProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class ModItemTagsProvider(
	pOutput: PackOutput,
	pLookupProvider: CompletableFuture<HolderLookup.Provider>,
	pBlockTags: CompletableFuture<TagLookup<Block>>,
	existingFileHelper: ExistingFileHelper
) : ItemTagsProvider(pOutput, pLookupProvider, pBlockTags, IrregularImplements.MOD_ID, existingFileHelper) {

	//TODO: Copy all block tags to item tags

	override fun addTags(provider: HolderLookup.Provider) {
		tag(NATURE_CORE_FLOWERS)
			.addTag(ItemTags.FLOWERS)

		tag(NATURE_CORE_SAPLINGS)
			.addTag(ItemTags.SAPLINGS)

		tag(SPECTRE_ANCHOR_BLACKLIST)
			.add(
				ModItems.SPECTRE_ANCHOR
			)

		tag(CUSTOM_CRAFTING_TABLE_ITEMS)
			.addTags(
				ItemTags.PLANKS,
				ItemTags.LOGS
			)

		tag(ItemTags.MINING_LOOT_ENCHANTABLE)
			.add(
				ModItems.DIAMOND_BREAKER
			)

		tag(ItemTags.MINING_ENCHANTABLE)
			.add(
				ModItems.DIAMOND_BREAKER
			)

		tag(ItemTags.LOGS_THAT_BURN)
			.add(
				ModBlocks.SPECTRE_LOG.asItem(),
				ModBlocks.SPECTRE_WOOD.asItem()
			)

		tag(ItemTags.PLANKS)
			.add(
				ModBlocks.SPECTRE_PLANKS.asItem()
			)

		tag(ENCHANTABLE_MAGNETIC)
			.addTags(
				Tags.Items.MINING_TOOL_TOOLS,
				Tags.Items.MELEE_WEAPON_TOOLS
			)

		tag(RING_CURIO)
			.add(
				ModItems.OBSIDIAN_SKULL_RING,
				ModItems.LAVA_CHARM
			)

		copy(ModBlockTagsProvider.SUPER_LUBRICATED, SUPER_LUBRICATED_BLOCKS)

		tag(Tags.Items.MELEE_WEAPON_TOOLS)
			.add(
				ModItems.SPECTRE_SWORD
			)

		tag(Tags.Items.MINING_TOOL_TOOLS)
			.add(
				ModItems.SPECTRE_PICKAXE,
				ModItems.SPECTRE_SHOVEL,
				ModItems.SPECTRE_AXE
			)

		tag(ItemTags.AXES)
			.add(
				ModItems.SPECTRE_AXE
			)

		tag(ItemTags.BEACON_PAYMENT_ITEMS)
			.add(
				ModItems.SPECTRE_INGOT
			)

		tag(ItemTags.SWORDS)
			.add(
				ModItems.SPECTRE_SWORD
			)

		tag(ItemTags.SHOVELS)
			.add(
				ModItems.SPECTRE_SHOVEL
			)

		tag(ItemTags.PICKAXES)
			.add(
				ModItems.SPECTRE_PICKAXE
			)

		tag(ItemTags.TRIMMABLE_ARMOR)
			.remove(
				ModItems.SPECTRE_HELMET.get(),
				ModItems.SPECTRE_CHESTPLATE.get(),
				ModItems.SPECTRE_LEGGINGS.get(),
				ModItems.SPECTRE_BOOTS.get(),
				ModItems.WATER_WALKING_BOOTS.get(),
				ModItems.OBSIDIAN_WATER_WALKING_BOOTS.get(),
				ModItems.LAVA_WADERS.get()
			)

		tag(ItemTags.HEAD_ARMOR)
			.add(
				ModItems.MAGIC_HOOD,
				ModItems.SPECTRE_HELMET
			)

		tag(ItemTags.CHEST_ARMOR)
			.add(
				ModItems.SPECTRE_CHESTPLATE
			)

		tag(ItemTags.LEG_ARMOR)
			.add(
				ModItems.SPECTRE_LEGGINGS
			)

		tag(ItemTags.FOOT_ARMOR)
			.add(
				ModItems.SPECTRE_BOOTS,
				ModItems.WATER_WALKING_BOOTS,
				ModItems.OBSIDIAN_WATER_WALKING_BOOTS,
				ModItems.LAVA_WADERS
			)

		tag(C_RODS_OBSIDIAN)
			.add(
				ModItems.OBSIDIAN_ROD
			)

		tag(HIDE_NAME_HELMET)
			.add(
				ModItems.MAGIC_HOOD
			)

		tag(HIDE_POTION_HELMET)
			.add(
				ModItems.MAGIC_HOOD
			)

		tag(C_CROPS_BEAN)
			.add(ModItems.BEAN)

		tag(Tags.Items.CROPS)
			.addTag(C_CROPS_BEAN)

		tag(Tags.Items.SEEDS)
			.addTag(GRASS_SEEDS)
			.add(
				ModItems.LOTUS_SEEDS,
				ModItems.BEAN
			)

		tag(Tags.Items.FOODS)
			.add(
				ModItems.LOTUS_BLOSSOM,
				ModItems.BEAN_STEW
			)

		tag(GRASS_SEEDS)
			.add(
				ModItems.GRASS_SEEDS,
				ModItems.GRASS_SEEDS_WHITE,
				ModItems.GRASS_SEEDS_ORANGE,
				ModItems.GRASS_SEEDS_MAGENTA,
				ModItems.GRASS_SEEDS_LIGHT_BLUE,
				ModItems.GRASS_SEEDS_YELLOW,
				ModItems.GRASS_SEEDS_LIME,
				ModItems.GRASS_SEEDS_PINK,
				ModItems.GRASS_SEEDS_GRAY,
				ModItems.GRASS_SEEDS_LIGHT_GRAY,
				ModItems.GRASS_SEEDS_CYAN,
				ModItems.GRASS_SEEDS_PURPLE,
				ModItems.GRASS_SEEDS_BLUE,
				ModItems.GRASS_SEEDS_BROWN,
				ModItems.GRASS_SEEDS_GREEN,
				ModItems.GRASS_SEEDS_RED,
				ModItems.GRASS_SEEDS_BLACK,
			)

		tag(DAMAGES_SPIRITS)
			.add(
				ModItems.SPECTRE_SWORD
			)

		tag(NOT_YET_IMPLEMENTED)
			.add(
				ModBlocks.INVENTORY_REROUTER.asItem(),
				ModBlocks.POTION_VAPORIZER.asItem(),
				ModBlocks.ADVANCED_REDSTONE_REPEATER.asItem(),
				ModBlocks.PROCESSING_PLATE.asItem(),
				ModBlocks.FILTERED_REDIRECTOR_PLATE.asItem(),
				ModBlocks.REDSTONE_PLATE.asItem(),
				ModBlocks.EXTRACTION_PLATE.asItem(),
				ModBlocks.ANCIENT_BRICK.asItem()
			)
	}

	companion object {
		private fun create(namespace: String, path: String): TagKey<Item> = ItemTags.create(ResourceLocation.fromNamespaceAndPath(namespace, path))
		private fun create(id: String): TagKey<Item> = ItemTags.create(OtherUtil.modResource(id))
		private fun common(id: String): TagKey<Item> = create("c", id)

		val GRASS_SEEDS = create("grass_seeds")
		val SUPER_LUBRICATED_BLOCKS = create("super_lubricated")
		val NATURE_CORE_FLOWERS = create("nature_core_flowers")
		val NATURE_CORE_SAPLINGS = create("nature_core_saplings")
		val CUSTOM_CRAFTING_TABLE_ITEMS = create("custom_crafting_table_items")

		val ENCHANTABLE_MAGNETIC = create("enchantable/magnetic")
		val SPECTRE_ANCHOR_BLACKLIST = create("spectre_anchor_blacklist")

		val DAMAGES_SPIRITS = create("damages_spirits")
		val NOT_YET_IMPLEMENTED = create("not_yet_implemented")

		val C_CROPS_BEAN = common("crops/bean")
		val C_RODS_OBSIDIAN = common("rods/obsidian")
		val RING_CURIO = create("curios", "ring")

		@JvmField
		val HIDE_POTION_HELMET = create("hide_potion_helmet")

		@JvmField
		val HIDE_NAME_HELMET = create("hide_name_helmet")

	}

}