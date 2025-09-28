package dev.aaronhowser.mods.irregular_implements.command

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModMessageLang
import dev.aaronhowser.mods.irregular_implements.handler.floo.FlooNetworkSavedData
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.commands.arguments.ResourceLocationArgument
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.Level

object FireplaceCommand {

	private const val FIREPLACE_NAME = "fireplace_name"
	private const val DIMENSION = "dimension"

	fun register(): ArgumentBuilder<CommandSourceStack, *> {
		return Commands
			.literal("fireplace")
			.then(createListCommand())
			.then(createTeleportCommand())
	}

	private fun createListCommand(): ArgumentBuilder<CommandSourceStack, *> {
		return Commands.literal("list")
			.executes { ctx ->
				val source = ctx.source
				val levelRk = source.level.dimension()

				listFireplaces(source, levelRk)
			}
			.then(
				Commands.argument(DIMENSION, ResourceLocationArgument.id())
					.suggests(ModCommands.SUGGEST_LEVEL_RKS)
					.executes { ctx ->
						val source = ctx.source
						val levelRl = ResourceLocationArgument.getId(ctx, DIMENSION)
						val levelRk = ResourceKey.create(Registries.DIMENSION, levelRl)

						listFireplaces(source, levelRk)
					}
			)
	}

	private fun createTeleportCommand(): ArgumentBuilder<CommandSourceStack, *> {
		return Commands.literal("teleport")
			.requires { it.hasPermission(2) }
			.then(
				Commands.argument(FIREPLACE_NAME, StringArgumentType.greedyString())
					.executes { ctx ->
						val source = ctx.source
						val target = StringArgumentType.getString(ctx, FIREPLACE_NAME)

						teleportTo(source, target, null)
					}
			)
			.then(
				Commands.argument(DIMENSION, ResourceLocationArgument.id())
					.suggests(ModCommands.SUGGEST_LEVEL_RKS)
					.then(
						Commands.argument(FIREPLACE_NAME, StringArgumentType.greedyString())
							.executes { ctx ->
								val source = ctx.source
								val target = StringArgumentType.getString(ctx, FIREPLACE_NAME)
								val levelRl = ResourceLocationArgument.getId(ctx, DIMENSION)
								val levelRk = ResourceKey.create(Registries.DIMENSION, levelRl)

								teleportTo(source, target, levelRk)
							}
					)
			)
	}

	private fun teleportTo(
		source: CommandSourceStack,
		target: String,
		levelRk: ResourceKey<Level>?
	): Int {
		val player = source.playerOrException
		val level = if (levelRk == null) {
			player.serverLevel()
		} else {
			player.server.getLevel(levelRk)
		}

		if (level == null) {
			source.sendFailure(ModMessageLang.COMMAND_LEVEL_NOT_FOUND.toComponent(levelRk?.location() ?: "null"))
			return 0
		}

		val network = FlooNetworkSavedData.get(level)
		val fireplace = network.findFireplace(target)

		if (fireplace == null) {
			source.sendFailure(ModMessageLang.FIREPLACE_NOT_FOUND.toComponent(target))
			return 0
		}

		fireplace.teleportToThis(player)

		return 1
	}

	private fun listFireplaces(source: CommandSourceStack, levelRk: ResourceKey<Level>): Int {
		val level = source.server.getLevel(levelRk)

		if (level == null) {
			source.sendFailure(ModMessageLang.COMMAND_LEVEL_NOT_FOUND.toComponent(levelRk.location()))
			return 0
		}

		val network = FlooNetworkSavedData.get(level)
		val fireplaces = network.getFireplaces()

		val message = {
			val component = ModMessageLang.FIREPLACES_IN_DIMENSION
				.toComponent(levelRk.location().toString(), fireplaces.size)

			for (fireplace in fireplaces) {
				val name = fireplace.name ?: "<unnamed>"
				val pos = fireplace.masterBlockPos

				component.append("\n")
				component.append(
					ModMessageLang.FIREPLACE_LIST_ENTRY
						.toComponent(name, pos.x, pos.y, pos.z)
				)
			}

			component
		}

		source.sendSuccess(message, false)

		return 1
	}

	//FIXME: When I put it in the command, it didn't work
	val SUGGEST_FIREPLACES: SuggestionProvider<CommandSourceStack> =
		SuggestionProvider { ctx: CommandContext<CommandSourceStack>, builder: SuggestionsBuilder ->
			val level = ctx.source.level
			val network = FlooNetworkSavedData.get(level)

			val fireplaceNames = network.getFireplaces().mapNotNull { it.name }
			SharedSuggestionProvider.suggest(fireplaceNames, builder)
		}
}
