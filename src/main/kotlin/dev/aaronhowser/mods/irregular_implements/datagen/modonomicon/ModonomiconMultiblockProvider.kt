package dev.aaronhowser.mods.irregular_implements.datagen.modonomicon

import com.klikli_dev.modonomicon.api.datagen.MultiblockProvider
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Blocks

class ModonomiconMultiblockProvider(
	output: PackOutput
) : MultiblockProvider(output, IrregularImplements.MOD_ID) {

	override fun buildMultiblocks() {
		val ironBars = { Blocks.IRON_BARS }

		val biomeRadar = DenseMultiblockBuilder()
			.layer(" I ", "I I", " I ")
			.layer(" E ", "SCN", " W ")
			.layer("   ", " I ", "   ")
			.layer("   ", " 0 ", "   ")
			.block('0', ModBlocks.BIOME_RADAR)
			.block('I', ironBars)
			.blockstate('C', ironBars, "[east=true,north=true,south=true,west=true]")
			.blockstate('N', ironBars, "[north=true]")
			.blockstate('E', ironBars, "[east=true]")
			.blockstate('S', ironBars, "[south=true]")
			.blockstate('W', ironBars, "[west=true]")

		add(BIOME_RADAR, biomeRadar.build(false))
	}

	companion object {
		val BIOME_RADAR: ResourceLocation =
			OtherUtil.modResource("biome_radar")
	}

}