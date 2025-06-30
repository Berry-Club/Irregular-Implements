package dev.aaronhowser.mods.irregular_implements.datagen

import dev.aaronhowser.mods.irregular_implements.registry.ModParticleTypes
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.common.data.ExistingFileHelper
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider

class ModParticleDescriptionProvider(
	output: PackOutput,
	fileHelper: ExistingFileHelper
) : ParticleDescriptionProvider(output, fileHelper) {

	override fun addDescriptions() {

		sprite(ModParticleTypes.FLOO_FLAME.get(), OtherUtil.modResource("floo"))

	}

}