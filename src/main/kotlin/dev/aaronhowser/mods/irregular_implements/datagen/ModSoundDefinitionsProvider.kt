package dev.aaronhowser.mods.irregular_implements.datagen

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModSubtitleLang
import dev.aaronhowser.mods.irregular_implements.registry.ModSounds
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.common.data.ExistingFileHelper
import net.neoforged.neoforge.common.data.SoundDefinition
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider

class ModSoundDefinitionsProvider(
	output: PackOutput,
	helper: ExistingFileHelper
) : SoundDefinitionsProvider(output, IrregularImplements.MOD_ID, helper) {

	override fun registerSounds() {
		add(
			ModSounds.FART,
			SoundDefinition.definition()
				.with(
					sound(OtherUtil.modResource("fart"), SoundDefinition.SoundType.SOUND)
				)
				.subtitle(ModSubtitleLang.FART)
		)

		add(
			ModSounds.EXTRACTINATOR,
			SoundDefinition.definition()
				.with(
					sound(OtherUtil.modResource("extractinator"), SoundDefinition.SoundType.SOUND)
				)
		)

		add(
			ModSounds.NOTIFICATION,
			SoundDefinition.definition()
				.with(
					sound(OtherUtil.modResource("notification"), SoundDefinition.SoundType.SOUND)
				)
		)

		add(
			ModSounds.REVIVE,
			SoundDefinition.definition()
				.with(
					sound(OtherUtil.modResource("revive"), SoundDefinition.SoundType.SOUND)
				)
		)

		add(
			ModSounds.TELEPORT,
			SoundDefinition.definition()
				.with(
					sound(OtherUtil.modResource("teleport"), SoundDefinition.SoundType.SOUND)
				)
		)

		add(
			ModSounds.WHITE_STONE_ACTIVATE,
			SoundDefinition.definition()
				.with(
					sound(OtherUtil.modResource("white_stone_activate"), SoundDefinition.SoundType.SOUND)
				)
		)
	}
}