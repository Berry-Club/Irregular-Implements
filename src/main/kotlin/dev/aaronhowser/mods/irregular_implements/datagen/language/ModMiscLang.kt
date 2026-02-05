package dev.aaronhowser.mods.irregular_implements.datagen.language

object ModMiscLang {

	const val MAGNETIC_NAME = "enchantment.irregular_implements.magnetic"
	const val MAGNETIC_DESC = "enchantment.irregular_implements.magnetic.desc"

	const val MAGNETIC_TAG = "tag.item.irregular_implements.enchantable.magnetic"
	const val GRASS_SEEDS_TAG = "tag.item.irregular_implements.grass_seeds"
	const val HIDE_NAME_HELMET_TAG = "tag.item.irregular_implements.hide_name_helmet"
	const val HIDE_POTION_HELMET_TAG = "tag.item.irregular_implements.hide_potion_helmet"
	const val SUPER_LUBRICATED_TAG = "tag.item.irregular_implements.super_lubricated"
	const val CUSTOM_CRAFTING_TABLE_ITEMS_TAG = "tag.item.irregular_implements.custom_crafting_table_items"
	const val SPECTRE_ANCHOR_BLACKLIST_TAG = "tag.item.irregular_implements.spectre_anchor_blacklist"

	const val C_BEAN_TAG = "tag.item.c.crops.bean"
	const val C_OBSIDIAN_RODS_TAG = "tag.item.c.rods.obsidian"

	const val ALLOWS_LAVA_WALKING = "tag.fluid.irregular_implements.allows_lava_walking"
	const val ALLOWS_WATER_WALKING = "tag.fluid.irregular_implements.allows_water_walking"

	fun add(provider: ModLanguageProvider) {
		provider.apply {
			add(MAGNETIC_NAME, "Magnetic")
			add(MAGNETIC_DESC, "Teleports fresh item drops towards you")
			add(MAGNETIC_TAG, "Magnetic Enchantable")
			add(GRASS_SEEDS_TAG, "Grass Seeds")
			add(HIDE_NAME_HELMET_TAG, "Hides Name Helmet")
			add(HIDE_POTION_HELMET_TAG, "Hides Potion Helmet")
			add(SUPER_LUBRICATED_TAG, "Super Lubricated Boots")
			add(CUSTOM_CRAFTING_TABLE_ITEMS_TAG, "Custom Crafting Table Items")
			add(SPECTRE_ANCHOR_BLACKLIST_TAG, "Spectre Anchor Blacklist")
			add(C_BEAN_TAG, "Beans")
			add(C_OBSIDIAN_RODS_TAG, "Obsidian Rods")
			add(ALLOWS_WATER_WALKING, "Allows Water Walking")
			add(ALLOWS_LAVA_WALKING, "Allows Lava Walking")
		}
	}

}