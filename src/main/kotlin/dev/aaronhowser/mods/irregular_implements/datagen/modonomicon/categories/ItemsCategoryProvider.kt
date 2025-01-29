package dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.categories

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel
import dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.entries.BaseEntryProvider
import dev.aaronhowser.mods.irregular_implements.item.DiviningRodItem
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.tags.BlockTags

class ItemsCategoryProvider(
    parent: ModonomiconProviderBase
) : CategoryProvider(parent) {

    private val realThis = this

    override fun categoryId(): String = "items"
    override fun categoryName(): String = "Items"
    override fun categoryIcon(): BookIconModel = BookIconModel.create(ModItems.LAVA_CHARM)

    override fun generateEntryMap(): Array<String> {
        return emptyArray()     // Return nothing because it's going to use the list mode
    }

    override fun generateEntries() {

        stableEnderPearl()
        evilTear()
        portkey()
        biomeCrystal()
        summoningPendulum()
        lootGenerator()
        bottleOfAir()
        enderLetter()
        goldenEgg()
        emeraldCompass()
        blazeAndSteel()
        escapeRope()
        chunkAnalyzer()
        lavaCharm()
        obsidianSkull()
        obsidianSkullRing()
        diviningRod()
        luminousPowder()
        enderBuckets()
        sakanadeSpores()
        lotus()
        bean()
        magicHood()
        waterWalkingBoots()
        lavaWaders()
        spectreArmor()
        weatherEggs()
        locationFilter()
        itemFilter()
        entityFilter()
        idCard()
        imbues()
        spectreIlluminator()
        spectreKey()
        spectreAnchor()
        spectreChargers()
        spectreTools()
        redstoneTool()
        redstoneActivator()
        redstoneRemote()
        floo()
        soundPattern()
        soundRecorder()
        portableSoundDampener()
        biomeCapsule()
        biomePainter()
        dropFilter()
        voidStone()
        whiteStone()
        magneticForce()
        portableEnderBridge()
        blockMover()
        diamondBreaker()
        blockReplacer()
        grassSeeds()
    }

    private fun stableEnderPearl() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Stable Ender Pearl",
            ModItems.STABLE_ENDER_PEARL
        ) {
            override fun generatePages() {
                textPage(
                    "Stable Ender Pearl",
                    paragraphs(
                        "The ${major("Stable Ender Pearl")} functions somewhat differently to a regular Ender Pearl.",
                        "When right-clicked, instead of throwing the Pearl, it instead ${minor("binds the Pearl to you")}.",
                        "When dropped, after a short delay, the Pearl will ${minor("teleport you to its location")}.",
                    )
                )

                spotlightPage(
                    "If the Pearl has not been bound to anything, it will instead grab a random Entity within 10 blocks, and teleport that instead."
                )
            }
        }

        this.add(entry.generate())
    }

    //TODO: Include a picture of the structure
    private fun evilTear() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Evil Tear",
            ModItems.EVIL_TEAR
        ) {
            override fun generatePages() {
                textPage(
                    "Evil Tear",
                    paragraphs(
                        "The ${major("Evil Tear")} allows you to create an ${minor("Artificial End Portal")}.",
                        "Make a 3x3 platform of End Stone, and make a 5x5 ring of Obsidian one block above it.",
                        "Five blocks above the center End Stone, place another End Stone, with an End Rod on its bottom."
                    )
                )

                spotlightPage(
                    "Use an Evil Tear on the End Rod to open the portal."
                )
            }
        }

        this.add(entry.generate())
    }

    private fun portkey() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Portkey",
            ModItems.PORTKEY
        ) {
            override fun generatePages() {
                textPage(
                    "Portkey",
                    paragraphs(
                        "The ${major("Portkey")} can be bound to a location and then ${minor("teleports anyone who picks it up")} after it's been dropped.",
                        "Use it on a block to set its location. After dropping it, it'll activate after a short delay. You can tell it's active when it stops glowing."
                    )
                )

                // TODO: Either implement this or remove the text
                spotlightPage(
                    "You can craft the Portkey with any other item to ${minor("disguise it")}, making it look like that item."
                )
            }
        }

        this.add(entry.generate())
    }

    private fun biomeCrystal() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Biome Crystal",
            ModItems.BIOME_CRYSTAL
        ) {
            override fun generatePages() {
                textPage(
                    "Biome Crystal",
                    paragraphs(
                        "Found in ${minor("dungeon chests")}, ${major("Biome Crystals")} are meant to be used in the ${block("Biome Radar", "biome_radar")} to locate specific biomes.",
                    )
                )

                spotlightPage(
                    ModItems.BIOME_CRYSTAL,
                    paragraphs(
                        "There's a Biome Crystal for almost every biome in the game.",
                        "However, any biome with the tag ${bad("#irregular_implements:biome_crystal_blacklist")} are excluded."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun summoningPendulum() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Summoning Pendulum",
            ModItems.SUMMONING_PENDULUM
        ) {
            override fun generatePages() {

                textPage(
                    "Summoning Pendulum",
                    paragraphs(
                        "The ${major("Summoning Pendulum")} can be used to pick up and place entities.",
                        "Right-click an entity to pick it up, right-click the ground to place it down."
                    )
                )

                spotlightPage(
                    "Found in ${minor("dungeon chests")}"
                )
            }
        }

        this.add(entry.generate())
    }

    private fun lootGenerator() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun bottleOfAir() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Bottle of Air",
            ModItems.BOTTLE_OF_AIR
        ) {
            override fun generatePages() {
                textPage(
                    "Bottle of Air",
                    "The ${major("Bottle of Air")} can be used underwater to refill your air supply."
                )

                spotlightPage(
                    "These can be found in ${minor("Ocean Monuments")}"
                )
            }
        }

        this.add(entry.generate())
    }

    private fun enderLetter() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun goldenEgg() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun emeraldCompass() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Emerald Compass",
            ModItems.EMERALD_COMPASS
        ) {
            override fun generatePages() {
                textPage(
                    "Emerald Compass",
                    paragraphs(
                        "The ${major("Emerald Compass")} can be used to locate players.",
                    )
                )

                spotlightPage(
                    "Craft it with an ${item("ID Card", "id_card")}, and it will point to the player the ID Card is set to.",
                )
            }
        }

        this.add(entry.generate())
    }

    private fun blazeAndSteel() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Blaze and Steel",
            ModItems.BLAZE_AND_STEEL
        ) {
            override fun generatePages() {
                spotlightPage(
                    "Blaze and Steel",
                    "The ${major("Blaze and Steel")} lights a ${minor("much more aggressive fire")}.",
                )
            }
        }

        this.add(entry.generate())
    }

    private fun escapeRope() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Escape Rope",
            ModItems.ESCAPE_ROPE
        ) {
            override fun generatePages() {
                textPage(
                    "Escape Rope",
                    paragraphs(
                        "The ${major("Escape Rope")} can be used to ${minor("teleport you out of caves")} quickly.",
                        "It does this by attempting to pathfind to anywhere that has a clear view of the sky.",
                        "Hold right-click to continue the search, and it'll either succeed on its own or fail after enough time."
                    )
                )

                spotlightPage(
                    paragraphs(
                        "On success, it will ${bad("damage the item")}, with a maximum of 20 uses.",
                        "On fail, it'll simply be dropped out of your hands."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun chunkAnalyzer() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Chunk Analyzer",
            ModItems.CHUNK_ANALYZER
        ) {
            override fun generatePages() {
                textPage(
                    "Chunk Analyzer",
                    paragraphs(
                        "The ${major("Chunk Analyzer")} can be used to ${minor("analyze the contents of a chunk")}.",
                        "Right-click it to open a menu, which lists all the blocks in the chunk, and their quantities."
                    )
                )

                spotlightPage(
                    "It will also list all the ${minor("entities")} in the chunk as well!"
                )
            }
        }

        this.add(entry.generate())
    }

    private fun lavaCharm() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun obsidianSkull() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun obsidianSkullRing() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun diviningRod() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Divining Rod",
            ModItems.DIVINING_ROD
        ) {
            override fun generatePages() {
                textPage(
                    "Divining Rod",
                    paragraphs(
                        "When held in your hand, the ${major("Divining Rod")} will ${minor("show you nearby ores through walls")}.",
                        "It has a 20 block radius by default, though this can be configured."
                    )
                )

                spotlightPage(
                    DiviningRodItem.getRodForBlockTag(BlockTags.DIAMOND_ORES),
                    paragraphs(
                        "There is a Divining Rod for ${minor("each ore")}, as well as one that shows ${minor("all ores")}.",
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun luminousPowder() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    // Merge all bucket stuff
    private fun enderBuckets() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Ender Buckets",
            ModItems.ENDER_BUCKET
        ) {
            override fun generatePages() {
                spotlightPage(
                    ModItems.ENDER_BUCKET.toStack(),
                    "Ender Bucket",
                    paragraphs(
                        "The ${major("Ender Bucket")} can pick up fluids ${minor("when used on non-source blocks")}.",
                        "It'll search for the nearest source block and pick that up instead."
                    )
                )

                spotlightPage(
                    ModItems.REINFORCED_ENDER_BUCKET.toStack(),
                    "Reinforced Ender Bucket",
                    paragraphs(
                        "The ${major("Reinforced Ender Bucket")} works similarly, but ${minor("can hold 10 fluid sources")}!",
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun sakanadeSpores() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    // Merge all lotus stuff
    private fun lotus() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    // Merge all bean stuff
    private fun bean() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun magicHood() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Magic Hood",
            ModItems.MAGIC_HOOD,
        ) {
            override fun generatePages() {
                textPage(
                    "Magic Hood",
                    paragraphs(
                        "The ${major("Magic Hood")} disables your name plate and hides your potion particles."
                    )
                )

                spotlightPage(
                    paragraphs(
                        "It can only be found in ${minor("dungeon chests")} and ${minor("blacksmith village buildings")}."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    // And obsidian
    private fun waterWalkingBoots() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun lavaWaders() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun spectreArmor() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Spectre Armor",
            ModItems.SPECTRE_CHESTPLATE,
        ) {
            override fun generatePages() {
                textPage(
                    "Spectre Armor",
                    paragraphs(
                        "${major("Spectre Armor")} is comparable to Diamond, but has ${minor("higher durability and enchantability.")}",
                    )
                )

                spotlightPage(
                    paragraphs(
                        "It also makes you slightly transparent!"
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun weatherEggs() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun locationFilter() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Location Filter",
            ModItems.LOCATION_FILTER,
        ) {
            override fun generatePages() {
                textPage(
                    "Location Filter",
                    paragraphs(
                        "The ${major("Location Filter")} is an item representation of a location in the world.",
                        "Use it on a block to save its location to the Filter. Sneak right-click to clear it."
                    )
                )

                spotlightPage(
                    paragraphs(
                        "Holding it in your hand will show the location you set!"
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun itemFilter() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Item Filter",
            ModItems.ITEM_FILTER,
        ) {
            override fun generatePages() {
                textPage(
                    "Item Filter",
                    paragraphs(
                        "${major("Item Filters")} can be used to filter items in several places.",
                        "Use it to open its menu, which has 9 slots. Insert an item into a slot to add it to the filter.",
                        "There are two buttons above each non-empty slot. The left button toggles if that slot filters by ${minor("Item")} or ${minor("Item Tag")}."
                    )
                )

                spotlightPage(
                    paragraphs(
                        "The right button does one of two things.",
                        "If the slot is filtering by Item, then it toggles if it requires the item to have the same ${minor("components")}.",
                        "If the slot is filtering by Tag, then it cycles which tag it's filtering.",
                        "Additionally, you can set the entire filter to be a ${minor("blacklist")} or ${minor("whitelist")} using the button on the top right."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun entityFilter() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Entity Filter",
            ModItems.ENTITY_FILTER,
        ) {
            override fun generatePages() {
                spotlightPage(
                    "Entity Filter",
                    paragraphs(
                        "${major("Entity Filters")} can be used to filter entities in several places.",
                        "Use it on an entity to save its type to the Filter. Use it while not looking at an entity to set it to players."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun idCard() {
        val entry = object : BaseEntryProvider(
            realThis,
            "ID Card",
            ModItems.ID_CARD
        ) {
            override fun generatePages() {
                spotlightPage(
                    "ID Card",
                    paragraphs(
                        "${major("ID Cards")} can be used to ${minor("identify players")}.",
                        "Use it on a player to set it to them, or use it while not looking at a player to set it to you."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun imbues() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    // And blackout powder
    private fun spectreIlluminator() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun spectreKey() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun spectreAnchor() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Spectre Anchor",
            ModItems.SPECTRE_ANCHOR,
        ) {
            override fun generatePages() {
                textPage(
                    "Spectre Anchor",
                    paragraphs(
                        "${major("Spectre Anchors")} can be crafted with any item to anchor them to you, allowing them to ${minor("be kept on death")}."
                    )
                )

                spotlightPage(
                    paragraphs(
                        "After the items are returned to you, ${bad("the Spectre Anchor is consumed")}.",
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun spectreChargers() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun spectreTools() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun redstoneTool() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    // Merge remote?
    private fun redstoneActivator() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun redstoneRemote() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun floo() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun soundPattern() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun soundRecorder() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun portableSoundDampener() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun biomeCapsule() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Biome Capsule",
            ModItems.BIOME_CAPSULE,
        ) {
            override fun generatePages() {
                textPage(
                    "Biome Capsule",
                    paragraphs(
                        "The ${major("Biome Capsule")} works with the ${item("Biome Painter", "biome_painter")} to ${minor("change the biome of an area")}.",
                        "Throw a Biome Capsule on the ground, and it will start ${minor("generating Biome Points for the biome it's in")}, storing it in the Capsule."
                    )
                )

                spotlightPage(
                    paragraphs(
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun biomePainter() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Biome Painter AAAAAAAAAAAAAA",
            ModItems.BIOME_PAINTER,
        ) {
            override fun generatePages() {
                textPage(
                    "",
                    paragraphs(
                    )
                )

                spotlightPage(
                    paragraphs(
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun dropFilter() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Drop Filter",
            ModItems.DROP_FILTER,
        ) {
            override fun generatePages() {
                spotlightPage(
                    ModItems.DROP_FILTER.toStack(),
                    "Drop Filter",
                    paragraphs(
                        "The ${major("Drop Filter")} can be used to ${minor("prevent certain items from being picked up")}.",
                        "Use it to open its menu, and insert an ${item("Item Filter", "item_filter")}."
                    )
                )

                spotlightPage(
                    ModItems.VOIDING_DROP_FILTER.toStack(),
                    "Voiding Drop Filter",
                    paragraphs(
                        "The ${major("Voiding Drop Filter")} works similarly, but ${minor("destroys the items instead")}.",
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun voidStone() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Void Stone",
            ModItems.VOID_STONE,
        ) {
            override fun generatePages() {
                spotlightPage(
                    paragraphs(
                        "The ${major("Void Stone")} is essentially a portable trash can.",
                        "Open its menu, and any items inserted will be deleted."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun whiteStone() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun magneticForce() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun portableEnderBridge() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun blockMover() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Block Mover",
            ModItems.BLOCK_MOVER,
        ) {
            override fun generatePages() {
                textPage(
                    "Block Mover",
                    paragraphs(
                        "The ${major("Block Mover")} can be used to ${minor("pick up and place blocks")}, including block entities!",
                        "Right-click a block to pick it up, and right-click the ground to place it back down."
                    )
                )

                spotlightPage(
                    paragraphs(
                        "Unbreakable blocks, as well as blocks that have the tag #irregular_implements:block_mover_blacklist, cannot be picked up."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun diamondBreaker() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun blockReplacer() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//        ) {
//            override fun generatePages() {
//                textPage(
//                    "",
//                    paragraphs(
//                    )
//                )
//
//                spotlightPage(
//                    paragraphs(
//                    )
//                )
//            }
//        }
//
//        this.add(entry.generate())
    }

    private fun grassSeeds() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Grass Seeds",
            ModItems.GRASS_SEEDS,
        ) {
            override fun generatePages() {
                spotlightPage(
                    ModItems.GRASS_SEEDS.toStack(),
                    "Grass Seeds",
                    paragraphs(
                        "${major("Grass Seeds")} can be used to ${minor("spread grass")}.",
                    )
                )

                spotlightPage(
                    ModItems.GRASS_SEEDS_RED.toStack(),
                    "Colored Grass Seeds",
                    paragraphs(
                        "There are also ${major("16 colored variants")}, which can be used to spread colored grass."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

}