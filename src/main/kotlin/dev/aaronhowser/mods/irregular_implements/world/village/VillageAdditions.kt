package dev.aaronhowser.mods.irregular_implements.world.village

import com.mojang.datafixers.util.Pair
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.config.StartupConfig
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent

object VillageAdditions {

	fun addPeaceCandleProcessors(event: ServerAboutToStartEvent) {
		val templatePoolRegistry = event.server
			.registryAccess()
			.registry(Registries.TEMPLATE_POOL)
			.orElseThrow()

		val poolIds = StartupConfig.CONFIG
			.peaceCandleVillageTemplatePools
			.get()
			.distinct()

		for (poolId in poolIds) {
			val poolLocation = ResourceLocation.parse(poolId)
			addProcessorsToPool(templatePoolRegistry, poolLocation)
		}
	}

	private fun addProcessorsToPool(
		templatePoolRegistry: Registry<StructureTemplatePool>,
		poolLocation: ResourceLocation
	) {
		val pool = templatePoolRegistry.get(poolLocation)

		if (pool == null) {
			IrregularImplements.LOGGER.warn("Could not find configured Village Template Pool: {}", poolLocation)
			return
		}

		val processedTemplates = mutableListOf<Pair<StructurePoolElement, Int>>()

		for (entry in pool.rawTemplates) {
			val element = entry.first
			val legacyElement = element as? LegacySinglePoolElement

			if (legacyElement == null) {
				processedTemplates.add(entry)
				continue
			}

			val processedElement = addPeaceCandleProcessor(legacyElement)
			processedTemplates.add(Pair(processedElement, entry.second))
		}

		pool.rawTemplates = processedTemplates
		pool.templates.clear()

		for (entry in processedTemplates) {
			for (i in 0 until entry.second) {
				pool.templates.add(entry.first)
			}
		}
	}

	private fun addPeaceCandleProcessor(element: LegacySinglePoolElement): StructurePoolElement {
		val structureProcessors = element
			.processors
			.value()
			.list()
			.toMutableList()

		val brewingStandReplacementRule = ProcessorRule(
			RandomBlockMatchTest(Blocks.BREWING_STAND, 1f / 3f),
			AlwaysTrueTest.INSTANCE,
			ModBlocks.PEACE_CANDLE.get().defaultBlockState()
		)

		structureProcessors.add(RuleProcessor(listOf(brewingStandReplacementRule)))

		val structureProcessorList = Holder.direct(StructureProcessorList(structureProcessors))
		val location = element.template.left().orElseThrow()

		return SinglePoolElement
			.legacy(location.toString(), structureProcessorList)
			.apply(element.projection)
	}

}