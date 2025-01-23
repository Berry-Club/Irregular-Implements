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
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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

    private fun compressedSlimeBlock() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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
            ModBlocks.BIOME_RADAR,
            "biome_radar"
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
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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

    private fun glowingMushroom() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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

    private fun contactLever() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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

    private fun ironDropper() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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

    private fun advancedRedstoneRepeater() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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

    private fun quartzGlass() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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

    private fun rainbowLamp() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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

    // Include anchor and other bridge
    private fun enderBridge() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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

    private fun autoPlacer() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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

    private fun chatDetector() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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

    private fun globalChatDetector() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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

    private fun entityDetector() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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
//            ""
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
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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

    // include both
    private fun redstoneInterface() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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

    private fun spectreEnergyInjector() {
//        val entry = object : BaseEntryProvider(
//            realThis,
//            "",
//            ,
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//            ""
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
//            ""
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