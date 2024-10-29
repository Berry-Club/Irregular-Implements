package dev.aaronhowser.mods.irregular_implements.registries

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.item.FilterEntity
import dev.aaronhowser.mods.irregular_implements.item.FilterLocation
import dev.aaronhowser.mods.irregular_implements.item.StableEnderPearl
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister

object ModItems {

    val ITEM_REGISTRY: DeferredRegister.Items =
        DeferredRegister.createItems(IrregularImplements.ID)

    val ANALOG_EMITTER = basic("analog_emitter")
    val STABLE_ENDER_PEARL: DeferredItem<StableEnderPearl> =
        register("stable_ender_pearl") { StableEnderPearl() }
    val BIOME_CRYSTAL = basic("biome_crystal")
    val SUMMONING_PENDULUM = basic("summoning_pendulum")
    val BEAN = basic("bean")
    val LESSER_MAGIC_BEAN = basic("lesser_magic_bean")
    val MAGIC_BEAN = basic("magic_bean")
    val BEAN_STEW = basic("bean_stew")
    val WATER_WALKING_BOOTS = basic("water_walking_boots")
    val LOOT_GENERATOR = basic("loot_generator")
    val LAVA_CHARM = basic("lava_charm")
    val LAVA_WADERS = basic("lava_waders")
    val OBSIDIAN_SKULL = basic("obsidian_skull")
    val OBSIDIAN_SKULL_RING = basic("obsidian_skull_ring")      //Maybe you could just put the regular skull in the slot?
    val OBSIDIAN_WATER_WALKING_BOOTS = basic("obsidian_water_walking_boots")
    val MAGIC_HOOD = basic("magic_hood")
    val BOTTLE_OF_AIR = basic("bottle_of_air")
    val BLOOD_STONE = basic("blood_stone")
    val ENDER_LETTER = basic("ender_letter")
    val SAKANADE_SPORES = basic("sakanade_spores")
    val EVIL_TEAR = basic("evil_tear")
    val ECTOPLASM = basic("ectoplasm")
    val BIOME_SENSOR = basic("biome_sensor")
    val LUMINOUS_POWDER = basic("luminous_powder")
    val PLATE_BASE = basic("plate_base")
    val PRECIOUS_EMERALD = basic("precious_emerald")
    val LOTUS_BLOSSOM = basic("lotus_blossom")
    val GOLDEN_EGG = basic("golden_egg")
    val BLACKOUT_POWDER = basic("blackout_powder")
    val EMERALD_COMPASS = basic("emerald_compass")
    val BLAZE_AND_STEEL = basic("blaze_and_steel")
    val RUNE_PATTERN = basic("rune_pattern")
    val ID_CARD = basic("id_card")
    val PORTKEY = basic("portkey")
    val LOTUS_SEEDS = basic("lotus_seeds")
    val ESCAPE_ROPE = basic("escape_rope")
    val WEATHER_EGG = basic("weather_egg")  //Same item for all weathers
    val ENDER_BUCKET = basic("ender_bucket")
    val REINFORCED_ENDER_BUCKET = basic("reinforced_ender_bucket")
    val CHUNK_ANALYZER = basic("chunk_analyzer")
    val TIME_IN_A_BOTTLE = basic("time_in_a_bottle")
    val ECLIPSED_CLOCK = basic("eclipsed_clock")
    val GRASS_SEEDS = basic("grass_seeds")  //Same item for all colors
    val RUNE_DUST = basic("rune_dust") //Same item for all colors
    val DIVINING_ROD = basic("divining_rod")    // Same item for all ores

    // Filters
    val LOCATION_FILTER: DeferredItem<FilterLocation> =
        register("location_filter") { FilterLocation() }
    val ENTITY_FILTER: DeferredItem<FilterEntity> =
        register("entity_filter") { FilterEntity() }
    val ITEM_FILTER = basic("item_filter")

    // Imbues
    val FIRE_IMBUE = basic("fire_imbue")
    val POISON_IMBUE = basic("poison_imbue")
    val EXPERIENCE_IMBUE = basic("experience_imbue")
    val WITHER_IMBUE = basic("wither_imbue")

    // Spectre
    val SPECTRE_INGOT = basic("spectre_ingot")
    val SPECTRE_STRING = basic("spectre_string")
    val SPECTRE_ILLUMINATOR = basic("spectre_illuminator")
    val SPECTRE_KEY = basic("spectre_key")
    val SPECTRE_ANCHOR = basic("spectre_anchor")
    val SPECTRE_SWORD = basic("spectre_sword")
    val SPECTRE_PICKAXE = basic("spectre_pickaxe")
    val SPECTRE_AXE = basic("spectre_axe")
    val SPECTRE_SHOVEL = basic("spectre_shovel")
    val SPECTRE_CHARGER = basic("spectre_charger") //Same item for all tiers

    // Redstone
    val ADVANCED_REDSTONE_REPEATER = basic("advanced_redstone_repeater")
    val ADVANCED_REDSTONE_TORCH = basic("advanced_redstone_torch")
    val REDSTONE_TOOL = basic("redstone_tool")
    val REDSTONE_ACTIVATOR = basic("redstone_activator")
    val REDSTONE_REMOTE = basic("redstone_remote")

    // Lubricant
    val SUPER_LUBRICANT_TINCTURE = basic("super_lubricant_tincture")
    val SUPER_LUBRICANT_BOOTS = basic("super_lubricant_boots")  // What if you could instead apply the tincture to any boots?

    // Floo
    val FLOO_POWDER = basic("floo_powder")
    val FLOO_SIGN = basic("floo_sign")
    val FLOO_TOKEN = basic("floo_token")
    val FLOO_POUCH = basic("floo_pouch")

    // Sound
    val SOUND_PATTERN = basic("sound_pattern")
    val SOUND_RECORDER = basic("sound_recorder")
    val PORTABLE_SOUND_DAMPENER = basic("portable_sound_dampener")

    private fun basic(id: String): DeferredItem<Item> {
        return ITEM_REGISTRY.registerSimpleItem(id)
    }

    private fun <T : Item> register(id: String, itemBuilder: (Item.Properties) -> T): DeferredItem<T> {
        return ITEM_REGISTRY.registerItem(id, itemBuilder)
    }

}