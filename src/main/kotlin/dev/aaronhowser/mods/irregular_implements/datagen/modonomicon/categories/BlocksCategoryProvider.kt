package dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.categories

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel
import dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.entries.BaseEntryProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks

class BlocksCategoryProvider(
    parent: ModonomiconProviderBase
) : CategoryProvider(parent) {

    private val realThis = this

    override fun categoryId(): String = "blocks"
    override fun categoryName(): String = "Blocks"
    override fun categoryIcon(): BookIconModel = BookIconModel.create(ModBlocks.ENDER_ANCHOR)

    override fun generateEntryMap(): Array<String> {
        return emptyArray()     // Return nothing because it's going to use the list mode
    }

    override fun generateEntries() {

        fertilizedDirt()
        imbuingStation()
        fluidDisplay()
        enderMailbox()
        potionVaporizer()
        rainShield()
        compressedSlimeBlock()
        biomeRadar()
        inventoryRerouter()
        slimeCube()
        peaceCandle()
        soundBox()
        soundDampener()
        pitcherPlant()
        glowingMushroom()
        natureCore()
        natureChest()
        waterChest()
        analogEmitter()
        contactButton()
        contactLever()
        ironDropper()
        igniter()
        inventoryTester()
        blockDestabilizer()
        blockBreaker()
        redstoneObserver()
        sidedBlockOfRedstone()
        advancedRedstoneRepeater()
        advancedRedstoneTorch()
        blockOfSticks()
        customCraftingTable()
        triggerGlass()
        lapisGlass()
        quartzGlass()
        rainbowLamp()
        enderBridge()
        superLube()
        energyDistributor()
        enderEnergyDistributor()
        shockAbsorber()
        autoPlacer()
        blockTeleporter()
        blockDetector()
        moonPhaseDetector()
        onlineDetector()
        chatDetector()
        globalChatDetector()
        entityDetector()
        entityInterface()
        notificationInterface()
        redstoneInterface()
        spectreLens()
        spectreEnergyInjector()
        spectreCoils()
        spectreTrees()
        biomeBlocks()
        processingPlate()
        redirectorPlate()
        redstonePlate()
        correctorPlate()
        itemSealerPlate()
        itemRejuvenatorPlate()
        acceleratorPlate()
        directionalAccelerationPlate()
        bouncyPlate()
        collectorPlate()
        extractionPlate()
        platforms()
        luminousBlocks()

    }

    private fun fertilizedDirt() {
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

    private fun imbuingStation() {
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

    private fun fluidDisplay() {
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

    private fun enderMailbox() {
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

    private fun potionVaporizer() {
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

    private fun rainShield() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Rain Shield",
            ModBlocks.RAIN_SHIELD,
        ) {
            override fun generatePages() {
                textPage(
                    "Rain Shield",
                    paragraphs(
                        "The ${major("Rain Shield")} will ${minor("prevent rain in a 5 chunk radius")}.",
                        "This radius can be changed in the server config."
                    )
                )

                spotlightPage(
                    paragraphs(
                        "You can disable it by providing a redstone signal!"
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun compressedSlimeBlock() {
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

    private fun biomeRadar() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Biome Radar",
            ModBlocks.BIOME_RADAR
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

    private fun inventoryRerouter() {
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

    private fun slimeCube() {
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

    private fun peaceCandle() {
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

    private fun soundBox() {
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

    private fun soundDampener() {
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

    private fun pitcherPlant() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Pitcher Plant",
            ModBlocks.PITCHER_PLANT,
        ) {
            override fun generatePages() {
                textPage(
                    "Pitcher Plant",
                    paragraphs(
                        "The ${major("Pitcher Plant")} is a flower that ${minor("acts as an infinite source of water")}.",
                        "It also occasionally ${minor("fills adjacent tanks")} with water!"
                    )
                )

                spotlightPage(
                    paragraphs(
                        "It can be found in biomes with the tag ${minor("#c:is_wet/overworld")}, most notably Swamps and Jungles."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun glowingMushroom() {
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

    private fun natureCore() {
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

    private fun natureChest() {
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

    private fun waterChest() {
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

    private fun analogEmitter() {
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

    private fun contactButton() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Contact Button",
            ModBlocks.CONTACT_BUTTON,
        ) {
            override fun generatePages() {
                spotlightPage(
                    ModBlocks.CONTACT_BUTTON.toStack(),
                    "Contact Button",
                    paragraphs(
                        "The ${major("Contact Button")} acts like a Stone Button, but instead of clicking _it_, you click the block it's facing.",
                        "That is, if the Contact Button is facing up, clicking the block above it will make it emit a redstone signal."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun contactLever() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Contact Lever",
            ModBlocks.CONTACT_LEVER,
        ) {
            override fun generatePages() {
                spotlightPage(
                    ModBlocks.CONTACT_LEVER.toStack(),
                    "Contact Lever",
                    paragraphs(
                        "The ${major("Contact Lever")} acts like a Lever, but instead of clicking _it_, you click the block it's facing.",
                        "That is, if the Contact Lever is facing up, clicking the block above it will toggle if it emits a redstone signal."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun ironDropper() {
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

    private fun igniter() {
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

    private fun inventoryTester() {
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

    private fun blockDestabilizer() {
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

    private fun blockBreaker() {
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

    private fun redstoneObserver() {
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

    private fun sidedBlockOfRedstone() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Sided Block of Redstone",
            ModBlocks.SIDED_BLOCK_OF_REDSTONE,
        ) {
            override fun generatePages() {
                spotlightPage(
                    ModBlocks.SIDED_BLOCK_OF_REDSTONE.toStack(),
                    "Sided Block of Redstone",
                    paragraphs(
                        "The ${major("Sided Block of Redstone")} emits power from only one of its sides."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun advancedRedstoneRepeater() {
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

    private fun advancedRedstoneTorch() {
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

    // Include both
    private fun blockOfSticks() {
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

    private fun customCraftingTable() {
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

    private fun triggerGlass() {
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

    private fun lapisGlass() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Lapis Glass",
            ModBlocks.LAPIS_GLASS,
        ) {
            override fun generatePages() {
                spotlightPage(
                    ModBlocks.LAPIS_GLASS.toStack(),
                    "Lapis Glass",
                    paragraphs(
                        "${major("Lapis Glass")} is a special type of glass that is solid for players, but ${minor("everything else will pass through")}.",
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun quartzGlass() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Quartz Glass",
            ModBlocks.QUARTZ_GLASS,
        ) {
            override fun generatePages() {
                spotlightPage(
                    ModBlocks.QUARTZ_GLASS.toStack(),
                    "Quartz Glass",
                    paragraphs(
                        "${major("Quartz Glass")} is a special type of glass that ${minor("players pass through")}, but is solid for everything else."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun rainbowLamp() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Rainbow Lamp",
            ModBlocks.RAINBOW_LAMP,
        ) {
            override fun generatePages() {
                spotlightPage(
                    ModBlocks.RAINBOW_LAMP.toStack(),
                    "Rainbow Lamp",
                    paragraphs(
                        "The ${major("Rainbow Lamp")} will be a different color for each possible redstone signal strength (0-15)."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    // Include anchor and other bridge
    private fun enderBridge() {
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

    private fun superLube() {
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

    private fun energyDistributor() {
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

    private fun enderEnergyDistributor() {
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

    private fun shockAbsorber() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Shock Absorber",
            ModBlocks.SHOCK_ABSORBER,
        ) {
            override fun generatePages() {
                spotlightPage(
                    ModBlocks.SHOCK_ABSORBER.toStack(),
                    "Shock Absorber",
                    paragraphs(
                        "The ${major("Shock Absorber")} will ${minor("absorb fall damage")}.",
                        "Additionally, the higher the fall, the greater a redstone signal it emits."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun autoPlacer() {
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

    private fun blockTeleporter() {
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

    private fun blockDetector() {
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

    private fun moonPhaseDetector() {
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

    private fun onlineDetector() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Online Detector",
            ModBlocks.ONLINE_DETECTOR,
        ) {
            override fun generatePages() {
                textPage(
                    "Online Detector",
                    paragraphs(
                        "The ${major("Online Detector")} will emit a redstone signal when the set player is online."
                    )
                )

                spotlightPage(
                    paragraphs(
                        "Right-click it to open its menu, where you can type the player's username."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun chatDetector() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Chat Detector",
            ModBlocks.CHAT_DETECTOR,
        ) {
            override fun generatePages() {
                textPage(
                    "Chat Detector",
                    paragraphs(
                        "The ${major("Chat Detector")} will emit a redstone pulse when the block's owner sends a specific chat message.",
                        "Use the block to open its gui, where you can type a ${minor("regex pattern")} that will be tested on every message the owner sends.",
                        "You don't have to do any fancy regex, though. The regex \"example\" will match any chat message that contains the word \"example\".",
                        "Its owner is automatically set to the player who placed it."
                    )
                )

                spotlightPage(
                    paragraphs(
                        "In its menu, you can also set it to ${minor("consume")} the message.",
                        "If enabled, the message will not actually be sent to chat, but will still emit a redstone pulse."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun globalChatDetector() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Global Chat Detector",
            ModBlocks.GLOBAL_CHAT_DETECTOR,
        ) {
            override fun generatePages() {
                textPage(
                    "Global Chat Detector",
                    paragraphs(
                        "The ${major("Global Chat Detector")} works like the ${block("Chat Detector", "chat_detector")}, but works for all players.",
                        "In its menu, you can also supply up to 9 ${item("ID Cards", "id_card")}."
                    )
                )

                spotlightPage(
                    paragraphs(
                        "If the GCD is set to consume messages, it will only consume messages sent from the players set by the ID Cards."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun entityDetector() {
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

    private fun entityInterface() {
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

    private fun notificationInterface() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Notification Interface",
            ModBlocks.NOTIFICATION_INTERFACE,
        ) {
            override fun generatePages() {
                textPage(
                    "Notification Interface",
                    paragraphs(
                        "When the ${major("Notification Interface")} receives a redstone signal, it will ${minor("display a notification toast")} in the top right of the owner's screen.",
                        "Its menu has 2 text boxes, one for the title and one for the description."
                    )
                )

                spotlightPage(
                    paragraphs(
                        "You can set the toast's icon by inserting an item into the slot on the left."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    // include both
    private fun redstoneInterface() {
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

    private fun spectreLens() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Spectre Lens",
            ModBlocks.SPECTRE_LENS,
        ) {
            override fun generatePages() {
                spotlightPage(
                    "Spectre Lens",
                    paragraphs(
                        "The ${major("Spectre Lens")} can be placed on Beacons to ${minor("allow them to effect you no matter the distance.")}",
                        "However, you ${bad("must be in the same dimension")} as the Lens, and it must be loaded."
                    )
                )
            }
        }

        this.add(entry.generate())
    }

    private fun spectreEnergyInjector() {
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

    private fun spectreCoils() {
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

    private fun spectreTrees() {
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

    private fun biomeBlocks() {
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

    private fun processingPlate() {
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

    // Include filtered
    private fun redirectorPlate() {
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

    private fun redstonePlate() {
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

    private fun correctorPlate() {
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

    private fun itemSealerPlate() {
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

    private fun itemRejuvenatorPlate() {
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

    private fun acceleratorPlate() {
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

    private fun directionalAccelerationPlate() {
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

    private fun bouncyPlate() {
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

    private fun collectorPlate() {
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

    private fun extractionPlate() {
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

    // Should this include the filtered one?
    private fun platforms() {
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

    private fun luminousBlocks() {
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


}