package dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.categories

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel
import dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.entries.BaseEntryProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModItems

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

    }

    private fun stableEnderPearl() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Stable Entry Pearl",
            ModItems.STABLE_ENDER_PEARL,
            "stable_ender_pearl"
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
            ModItems.EVIL_TEAR,
            "evil_tear"
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
            ModItems.PORTKEY,
            "portkey"
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
            ModItems.BIOME_CRYSTAL,
            "biome_crystal"
        ) {
            override fun generatePages() {
                textPage(
                    "",
                    paragraphs(

                    )
                )

                spotlightPage(
                    ModItems.BIOME_CRYSTAL,
                    ""
                )
            }
        }

        this.add(entry.generate())
    }

    private fun summoningPendulum() {
        val entry = object : BaseEntryProvider(
            realThis,
            "Summoning Pendulum",
            ModItems.SUMMONING_PENDULUM,
            "summoning_pendulum"
        ) {
            override fun generatePages() {
                textPage(
                    "",
                    paragraphs(

                    )
                )

                spotlightPage(
                    ""
                )
            }
        }

        this.add(entry.generate())
    }

    private fun lootGenerator() {

    }

    private fun bottleOfAir() {

    }

    private fun enderLetter() {

    }

    private fun goldenEgg() {

    }

    private fun emeraldCompass() {

    }

    private fun blazeAndSteel() {

    }

    private fun escapeRope() {

    }

    private fun chunkAnalyzer() {

    }

    private fun lavaCharm() {

    }

    private fun obsidianSkull() {

    }

    private fun obsidianSkullRing() {

    }

    private fun diviningRod() {

    }

    private fun luminousPowder() {

    }

    // Merge all bucket stuff
    private fun enderBuckets() {

    }

    private fun sakanadeSpores() {

    }

    // Merge all lotus stuff
    private fun lotus() {

    }

    // Merge all bean stuff
    private fun bean() {

    }

    private fun magicHood() {

    }

    // And obsidian
    private fun waterWalkingBoots() {

    }

    private fun lavaWaders() {

    }

    private fun spectreArmor() {

    }

    private fun weatherEggs() {

    }

    private fun locationFilter() {

    }

    private fun itemFilter() {

    }

    private fun entityFilter() {

    }

    private fun idCard() {

    }

    private fun imbues() {

    }

    // And blackout powder
    private fun spectreIlluminator() {

    }

    private fun spectreKey() {

    }

    private fun spectreAnchor() {

    }

    private fun spectreChargers() {

    }

    private fun spectreTools() {

    }

    private fun redstoneTool() {

    }

    // Merge remote?
    private fun redstoneActivator() {

    }

    private fun redstoneRemote() {

    }

    private fun floo() {

    }

    private fun soundPattern() {

    }

    private fun soundRecorder() {

    }

    private fun portableSoundDampener() {

    }

    private fun biomeCapsule() {

    }

    private fun biomePainter() {

    }

    // include voiding
    private fun dropFilter() {

    }

    private fun voidStone() {

    }

    private fun whiteStone() {

    }

    private fun magneticForce() {

    }

    private fun portableEnderBridge() {

    }

    private fun blockMover() {

    }

    private fun diamondBreaker() {

    }

    private fun blockReplacer() {

    }

    private fun grassSeeds() {

    }

}