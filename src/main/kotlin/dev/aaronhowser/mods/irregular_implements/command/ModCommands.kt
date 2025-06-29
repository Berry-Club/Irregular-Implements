package dev.aaronhowser.mods.irregular_implements.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.Level

object ModCommands {

	fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
		dispatcher.register(
			Commands.literal("irregular-implements")
				.then(FireplacesCommand.register())
		)

	}

	val SUGGEST_LEVEL_RKS: SuggestionProvider<CommandSourceStack> =
		SuggestionProvider { ctx: CommandContext<CommandSourceStack>, builder: SuggestionsBuilder ->
			SharedSuggestionProvider.suggestResource(
				ctx.source.server
					.levelKeys()
					.map(ResourceKey<Level>::location),
				builder
			)
		}

}