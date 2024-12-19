package dev.aaronhowser.mods.irregular_implements.datagen.tag

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModEnchantments
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.EnchantmentTagsProvider
import net.minecraft.tags.EnchantmentTags
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class ModEnchantmentTagsProvider(
    pOutput: PackOutput,
    pProvider: CompletableFuture<HolderLookup.Provider>,
    existingFileHelper: ExistingFileHelper
) : EnchantmentTagsProvider(pOutput, pProvider, IrregularImplements.ID, existingFileHelper) {

    override fun addTags(p0: HolderLookup.Provider) {
        this.tag(EnchantmentTags.NON_TREASURE)
            .add(ModEnchantments.MAGNETIC)
    }


}