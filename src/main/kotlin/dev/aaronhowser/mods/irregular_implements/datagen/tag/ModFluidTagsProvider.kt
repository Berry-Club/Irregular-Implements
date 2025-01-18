package dev.aaronhowser.mods.irregular_implements.datagen.tag

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.FluidTagsProvider
import net.minecraft.tags.FluidTags
import net.minecraft.tags.TagKey
import net.minecraft.world.level.material.Fluid
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class ModFluidTagsProvider(
    output: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>,
    existingFileHelper: ExistingFileHelper?
) : FluidTagsProvider(output, lookupProvider, IrregularImplements.ID, existingFileHelper) {

    companion object {
        private fun create(id: String): TagKey<Fluid> {
            return FluidTags.create(OtherUtil.modResource(id))
        }

        @JvmField
        val ALLOWS_WATER_WALKING = create("allows_water_walking")

        @JvmField
        val ALLOWS_LAVA_WALKING = create("allows_lava_walking")
    }

    override fun addTags(provider: HolderLookup.Provider) {

        this.tag(ALLOWS_WATER_WALKING)
            .addTag(FluidTags.WATER)

        this.tag(ALLOWS_LAVA_WALKING)
            .addTag(FluidTags.LAVA)

    }
}