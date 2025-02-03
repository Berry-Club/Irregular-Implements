package dev.aaronhowser.mods.irregular_implements.world.village

import com.mojang.datafixers.util.Pair
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent

// It disturbs me viscerally that it has to be done this way and not via datapacks or whatever
object VillageAdditions {

    private val EMPTY_PROCESSOR_LIST_KEY: ResourceKey<StructureProcessorList> =
        ResourceKey.create(Registries.PROCESSOR_LIST, ResourceLocation.withDefaultNamespace("empty"))

    private fun addBuildingToPool(
        templatePoolRegistry: Registry<StructureTemplatePool>,
        processorListRegistry: Registry<StructureProcessorList>,
        poolRl: ResourceLocation,
        nbtPiece: String,
        weight: Int
    ) {
        // Grabs the processor list we want to use along with our piece.
        // This is a requirement as using the ProcessorLists.EMPTY field will cause the game to throw errors.
        // The reason why is the empty processor list in the world's registry is not the same instance as in that field once the world is started up.
        val emptyProcessorList = processorListRegistry.getHolderOrThrow(EMPTY_PROCESSOR_LIST_KEY)

        val pool = templatePoolRegistry.get(poolRl) ?: return

        val piece = SinglePoolElement.legacy(nbtPiece, emptyProcessorList)
            .apply(StructureTemplatePool.Projection.RIGID)

        for (i in 0 until weight) {
            pool.templates.add(piece)
        }

        val listOfPieceEntries = pool.rawTemplates.toMutableList()
        listOfPieceEntries.add(Pair(piece, weight))
        pool.rawTemplates = listOfPieceEntries
    }

    fun addNewVillageBuildings(event: ServerAboutToStartEvent) {
        val templatePoolRegistry = event.server.registryAccess().registry(Registries.TEMPLATE_POOL).orElseThrow()
        val processorListRegistry = event.server.registryAccess().registry(Registries.PROCESSOR_LIST).orElseThrow()

        addBuildingToPool(
            templatePoolRegistry,
            processorListRegistry,
            ResourceLocation.withDefaultNamespace("village/plains/houses"),
            OtherUtil.modResource("candle_temple/plains_1").toString(),
            2
        )

        addBuildingToPool(
            templatePoolRegistry,
            processorListRegistry,
            ResourceLocation.withDefaultNamespace("village/plains/houses"),
            OtherUtil.modResource("candle_temple/plains_2").toString(),
            2
        )

        addBuildingToPool(
            templatePoolRegistry,
            processorListRegistry,
            ResourceLocation.withDefaultNamespace("village/desert/houses"),
            OtherUtil.modResource("candle_temple/desert_1").toString(),
            2
        )

        addBuildingToPool(
            templatePoolRegistry,
            processorListRegistry,
            ResourceLocation.withDefaultNamespace("village/desert/houses"),
            OtherUtil.modResource("candle_temple/desert_2").toString(),
            2
        )

        addBuildingToPool(
            templatePoolRegistry,
            processorListRegistry,
            ResourceLocation.withDefaultNamespace("village/savanna/houses"),
            OtherUtil.modResource("candle_temple/savanna_1").toString(),
            2
        )

        addBuildingToPool(
            templatePoolRegistry,
            processorListRegistry,
            ResourceLocation.withDefaultNamespace("village/savanna/houses"),
            OtherUtil.modResource("candle_temple/savanna_2").toString(),
            2
        )

        addBuildingToPool(
            templatePoolRegistry,
            processorListRegistry,
            ResourceLocation.withDefaultNamespace("village/snowy/houses"),
            OtherUtil.modResource("candle_temple/snowy_1").toString(),
            2
        )

        addBuildingToPool(
            templatePoolRegistry,
            processorListRegistry,
            ResourceLocation.withDefaultNamespace("village/taiga/houses"),
            OtherUtil.modResource("candle_temple/taiga_2").toString(),
            2
        )

    }

}