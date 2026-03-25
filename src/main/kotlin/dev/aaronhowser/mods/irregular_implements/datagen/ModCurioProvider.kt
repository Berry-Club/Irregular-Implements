package dev.aaronhowser.mods.irregular_implements.datagen

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.common.data.ExistingFileHelper
import top.theillusivec4.curios.api.CuriosDataProvider
import top.theillusivec4.curios.api.type.capability.ICurio
import java.util.concurrent.CompletableFuture

class ModCurioProvider(
	output: PackOutput,
	fileHelper: ExistingFileHelper,
	registries: CompletableFuture<HolderLookup.Provider>
) : CuriosDataProvider(IrregularImplements.MOD_ID, output, fileHelper, registries) {

	override fun generate(registries: HolderLookup.Provider, fileHelper: ExistingFileHelper) {
		this.createEntities(PLAYERS_RULE)
			.addPlayer()
			.addSlots(RING_SLOT)

		createSlot(RING_SLOT)
			.size(1)
	}

	companion object {
		const val RING_SLOT = "ring"
		const val PLAYERS_RULE = "players"
	}
}