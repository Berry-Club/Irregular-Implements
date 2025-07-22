package dev.aaronhowser.mods.irregular_implements.datagen

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.language.*
import net.minecraft.ChatFormatting
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.PackOutput
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.level.ItemLike
import net.neoforged.neoforge.common.data.LanguageProvider

class ModLanguageProvider(
	output: PackOutput
) : LanguageProvider(output, IrregularImplements.ID, "en_us") {

	override fun addTranslations() {
		ModTooltipLang.add(this)
		ModItemLang.add(this)
		ModBlockLang.add(this)
		ModInfoLang.add(this)
		ModEffectLang.add(this)
		ModSubtitleLang.add(this)
		ModMessageLang.add(this)
		ModMiscLang.add(this)
		ModConfigLang.add(this)
		ModEntityTypeLang.add(this)
	}

	companion object {
		fun String.toComponent(vararg args: Any?): MutableComponent = Component.translatable(this, *args)
		fun String.toGrayComponent(vararg args: Any?): MutableComponent = Component.translatable(this, *args).withStyle(ChatFormatting.GRAY)
	}

}