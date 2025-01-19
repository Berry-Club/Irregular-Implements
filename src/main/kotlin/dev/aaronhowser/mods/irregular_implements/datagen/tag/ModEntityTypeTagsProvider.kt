package dev.aaronhowser.mods.irregular_implements.datagen.tag

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.EntityTypeTagsProvider
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.EntityType
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class ModEntityTypeTagsProvider(
    output: PackOutput,
    provider: CompletableFuture<HolderLookup.Provider>,
    existingFileHelper: ExistingFileHelper
) : EntityTypeTagsProvider(output, provider, IrregularImplements.ID, existingFileHelper) {

    companion object {
        private fun create(id: String): TagKey<EntityType<*>> {
            return TagKey.create(Registries.ENTITY_TYPE, OtherUtil.modResource(id))
        }

        val SUMMONING_PENDULUM_BLACKLIST = create("summoning_pendulum_blacklist")
    }

    override fun addTags(provider: HolderLookup.Provider) {

        this.tag(SUMMONING_PENDULUM_BLACKLIST)
            .add(
                EntityType.PLAYER,

                )

    }

}