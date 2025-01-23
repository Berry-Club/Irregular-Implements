package dev.aaronhowser.mods.irregular_implements.datagen.tag

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.BiomeTagsProvider
import net.minecraft.data.tags.EnchantmentTagsProvider
import net.minecraft.tags.TagKey
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.Biomes
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class ModBiomeTagsProvider(
    pOutput: PackOutput,
    pProvider: CompletableFuture<HolderLookup.Provider>,
    existingFileHelper: ExistingFileHelper
) : BiomeTagsProvider(pOutput, pProvider, IrregularImplements.ID, existingFileHelper) {

    companion object {
        val BIOME_CRYSTAL_BLACKLIST: TagKey<Biome> = TagKey.create(Registries.BIOME, OtherUtil.modResource("biome_crystal_blacklist"))
    }

    override fun addTags(provider: HolderLookup.Provider) {
        // None by default
    }

}