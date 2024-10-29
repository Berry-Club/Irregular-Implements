package dev.aaronhowser.mods.irregular_implements.datagen

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registries.ModItems
import net.minecraft.data.PackOutput
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.neoforged.neoforge.common.data.LanguageProvider

class ModLanguageProvider(
    output: PackOutput
) : LanguageProvider(output, IrregularImplements.ID, "en_us") {

    companion object {
        fun String.toComponent(vararg args: Any?): MutableComponent = Component.translatable(this, *args)
    }

    object Items {
        const val CREATIVE_TAB = "itemGroup.irregular_implements"
    }

    override fun addTranslations() {

        addItem(ModItems.ANALOG_EMITTER, "Analog Emitter")
        addItem(ModItems.STABLE_ENDER_PEARL, "Stable Ender Pearl")
        addItem(ModItems.BIOME_CRYSTAL, "Biome Crystal")
        addItem(ModItems.POSITION_FILTER, "Position Filter")
        addItem(ModItems.SUMMONING_PENDULUM, "Summoning Pendulum")
        addItem(ModItems.BEAN, "Bean")
        addItem(ModItems.LESSER_MAGIC_BEAN, "Lesser Magic Bean")
        addItem(ModItems.MAGIC_BEAN, "Magic Bean")
        addItem(ModItems.BEAN_STEW, "Bean Stew")
        addItem(ModItems.WATER_WALKING_BOOTS, "Water Walking Boots")
        addItem(ModItems.LOOT_GENERATOR, "Loot Generator")
        addItem(ModItems.LAVA_CHARM, "Lava Charm")
        addItem(ModItems.LAVA_WADERS, "Lava Waders")
        addItem(ModItems.OBSIDIAN_SKULL, "Obsidian Skull")
        addItem(ModItems.OBSIDIAN_SKULL_RING, "Obsidian Skull Ring")
        addItem(ModItems.OBSIDIAN_WATER_WALKING_BOOTS, "Obsidian Water Walking Boots")
        addItem(ModItems.MAGIC_HOOD, "Magic Hood")
        addItem(ModItems.BOTTLE_OF_AIR, "Bottle of Air")
        addItem(ModItems.BLOOD_STONE, "Blood Stone")
        addItem(ModItems.ENDER_LETTER, "Ender Letter")
        addItem(ModItems.ENTITY_FILTER, "Entity Filter")
        addItem(ModItems.SAKANDE_SPORES, "Sakande Spores")
        addItem(ModItems.EVIL_TEAR, "Evil Tear")
        addItem(ModItems.ECTOPLASM, "Ectoplasm")
        addItem(ModItems.BIOME_SENSOR, "Biome Sensor")
        addItem(ModItems.LUMINOUS_POWDER, "Luminous Powder")
        addItem(ModItems.PLATE_BASE, "Plate Base")
        addItem(ModItems.PRECIOUS_EMERALD, "Precious Emerald")
        addItem(ModItems.LOTUS_BLOSSOM, "Lotus Blossom")
        addItem(ModItems.GOLDEN_EGG, "Golden Egg")
        addItem(ModItems.BLACKOUT_POWDER, "Blackout Powder")
        addItem(ModItems.ITEM_FILTER, "Item Filter")
        addItem(ModItems.GOLDEN_COMPASS, "Golden Compass")
        addItem(ModItems.EMERALD_COMPASS, "Emerald Compass")
        addItem(ModItems.BLAZE_AND_STEEL, "Blaze and Steel")
        addItem(ModItems.RUNE_PATTERN, "Rune Pattern")
        addItem(ModItems.ID_CARD, "ID Card")
        addItem(ModItems.PORTKEY, "Portkey")
        addItem(ModItems.LOTUS_SEEDS, "Lotus Seeds")
        addItem(ModItems.ESCAPE_ROPE, "Escape Rope")
        addItem(ModItems.WEATHER_EGG, "Weather Egg")
        addItem(ModItems.ENDER_BUCKET, "Ender Bucket")
        addItem(ModItems.REINFORCED_ENDER_BUCKET, "Reinforced Ender Bucket")
        addItem(ModItems.CHUNK_ANALYZER, "Chunk Analyzer")
        addItem(ModItems.TIME_IN_A_BOTTLE, "Time in a Bottle")
        addItem(ModItems.ECLIPSED_CLOCK, "Eclipsed Clock")
        addItem(ModItems.GRASS_SEEDS, "Grass Seeds")
        addItem(ModItems.RUNE_DUST, "Rune Dust")
        addItem(ModItems.DIVINING_ROD, "Divining Rod")

        addItem(ModItems.FIRE_IMBUE, "Fire Imbue")
        addItem(ModItems.POISON_IMBUE, "Poison Imbue")
        addItem(ModItems.EXPERIENCE_IMBUE, "Experience Imbue")
        addItem(ModItems.WITHER_IMBUE, "Wither Imbue")

        addItem(ModItems.SPECTRE_INGOT, "Spectre Ingot")
        addItem(ModItems.SPECTRE_STRING, "Spectre String")
        addItem(ModItems.SPECTRE_ILLUMINATOR, "Spectre Illuminator")
        addItem(ModItems.SPECTRE_KEY, "Spectre Key")
        addItem(ModItems.SPECTRE_ANCHOR, "Spectre Anchor")
        addItem(ModItems.SPECTRE_SWORD, "Spectre Sword")
        addItem(ModItems.SPECTRE_PICKAXE, "Spectre Pickaxe")
        addItem(ModItems.SPECTRE_AXE, "Spectre Axe")
        addItem(ModItems.SPECTRE_SHOVEL, "Spectre Shovel")
        addItem(ModItems.SPECTRE_CHARGER, "Spectre Charger")

        addItem(ModItems.ADVANCED_REDSTONE_REPEATER, "Advanced Redstone Repeater")
        addItem(ModItems.ADVANCED_REDSTONE_TORCH, "Advanced Redstone Torch")
        addItem(ModItems.REDSTONE_TOOL, "Redstone Tool")
        addItem(ModItems.REDSTONE_ACTIVATOR, "Redstone Activator")
        addItem(ModItems.REDSTONE_REMOTE, "Redstone Remote")

        addItem(ModItems.SUPER_LUBRICANT_TINCTURE, "Super Lubricant Tincture")
        addItem(ModItems.SUPER_LUBRICANT_BOOTS, "Super Lubricant Boots")

        addItem(ModItems.FLOO_POWDER, "Floo Powder")
        addItem(ModItems.FLOO_SIGN, "Floo Sign")
        addItem(ModItems.FLOO_TOKEN, "Floo Token")
        addItem(ModItems.FLOO_POUCH, "Floo Pouch")

        addItem(ModItems.SOUND_PATTERN, "Sound Pattern")
        addItem(ModItems.SOUND_RECORDER, "Sound Recorder")
        addItem(ModItems.PORTABLE_SOUND_DAMPENER, "Portable Sound Dampener")


    }
}