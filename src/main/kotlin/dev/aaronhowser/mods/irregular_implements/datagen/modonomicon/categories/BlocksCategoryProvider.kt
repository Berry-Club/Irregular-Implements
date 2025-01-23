package dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.categories

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel
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

    }

    private fun fertilizedDirt() {

    }

    private fun imbuingStation() {

    }

    private fun fluidDisplay() {

    }

    private fun enderMailbox() {

    }

    private fun potionVaporizer() {

    }

    private fun rainShield() {

    }

    private fun compressedSlimeBlock() {

    }

    private fun biomeRadar() {

    }

    private fun inventoryRerouter() {

    }

    private fun slimeCube() {

    }

    private fun peaceCandle() {

    }

    private fun soundBox() {

    }

    private fun soundDampener() {

    }

    private fun pitcherPlant() {

    }

    private fun glowingMushroom() {

    }

    private fun natureCore() {

    }

    private fun natureChest() {

    }

    private fun waterChest() {

    }

    private fun analogEmitter() {

    }

    private fun contactButton() {

    }

    private fun contactLever() {

    }

    private fun ironDropper() {

    }

    private fun igniter() {

    }

    private fun inventoryTester() {

    }

    private fun blockDestabilizer() {

    }

    private fun blockBreaker() {

    }

    private fun redstoneObserver() {

    }

    private fun sidedBlockOfRedstone() {

    }

    private fun advancedRedstoneRepeater() {

    }

    private fun advancedRedstoneTorch() {

    }

    // Include both
    private fun blockOfSticks() {

    }

    private fun customCraftingTable() {

    }

    private fun triggerGlass() {

    }

    private fun lapisGlass() {

    }

    private fun quartzGlass() {

    }

    private fun rainbowLamp() {

    }

    // Include anchor and other bridge
    private fun enderBridge() {

    }

    private fun superLube() {

    }

    private fun energyDistributor() {

    }

    private fun enderEnergyDistributor() {

    }

    private fun shockAbsorber() {

    }

    private fun autoPlacer() {

    }

    private fun blockTeleporter() {

    }

    private fun blockDetector() {

    }

    private fun moonPhaseDetector() {

    }

    private fun onlineDetector() {

    }

    private fun chatDetector() {

    }

    private fun globalChatDetector() {

    }

    private fun entityDetector() {

    }

    private fun entityInterface() {

    }

    private fun notificationInterface() {

    }

    // include both
    private fun redstoneInterface() {

    }

    private fun spectreLens() {

    }

    private fun spectreEnergyInjector() {

    }

    private fun spectreCoils() {

    }

    private fun spectreTrees() {

    }

    private fun biomeBlocks() {

    }

    private fun processingPlate() {

    }

    // Include filtered
    private fun redirectorPlate() {

    }

    private fun redstonePlate() {

    }

    private fun correctorPlate() {

    }

    private fun itemSealerPlate() {

    }

    private fun itemRejuvenatorPlate() {

    }

    private fun acceleratorPlate() {

    }

    private fun directionalAccelerationPlate() {

    }

    private fun bouncyPlate() {

    }

    private fun collectorPlate() {

    }

    private fun extractionPlate() {

    }

    // Should this include the filtered one?
    private fun plates() {

    }

    private fun luminousBlocks() {

    }


}