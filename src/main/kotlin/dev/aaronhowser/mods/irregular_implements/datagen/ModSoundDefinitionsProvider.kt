package dev.aaronhowser.mods.irregular_implements.datagen

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registry.ModSounds
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.common.data.ExistingFileHelper
import net.neoforged.neoforge.common.data.SoundDefinition
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider

class ModSoundDefinitionsProvider(
    output: PackOutput,
    helper: ExistingFileHelper
) : SoundDefinitionsProvider(output, IrregularImplements.ID, helper) {

    override fun registerSounds() {

        add(
            ModSounds.FART,
            SoundDefinition.definition()
                .with(
                    sound(OtherUtil.modResource("fart"), SoundDefinition.SoundType.SOUND)
                )
                .subtitle(ModLanguageProvider.Subtitles.FART)
        )

    }
}