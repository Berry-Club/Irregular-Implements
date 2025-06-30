package dev.aaronhowser.mods.irregular_implements.command

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import dev.aaronhowser.mods.irregular_implements.handler.floo.FlooNetworkSavedData
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.commands.arguments.ResourceLocationArgument
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.Level

object FireplaceCommand {

	private const val FIREPLACE_NAME = "fireplace_name"
	private const val DIMENSION = "dimension"

	fun register(): ArgumentBuilder<CommandSourceStack, *> {
		return Commands
			.literal("fireplace")
			.then(registerListCommand())
			.then(registerTeleportCommand())
	}

	private fun registerListCommand(): ArgumentBuilder<CommandSourceStack, *> {
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

	private fun registerTeleportCommand(): ArgumentBuilder<CommandSourceStack, *> {
		return Commands.literal("teleport-to")
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
			source.sendFailure(Component.literal("Could not get level ${levelRk?.location()}"))
			return 0
		}

		val network = FlooNetworkSavedData.get(level)
		val fireplace = network.findFireplace(target)

		if (fireplace == null) {
			source.sendFailure(Component.literal("Could not find fireplace named '$target'"))
			return 0
		}

		fireplace.teleportToThis(player)

		return 1
	}

	private fun listFireplaces(source: CommandSourceStack, levelRk: ResourceKey<Level>): Int {
		val level = source.server.getLevel(levelRk)

		if (level == null) {
			source.sendFailure(Component.literal("Could not get level ${levelRk.location()}"))
			return 0
		}

		val network = FlooNetworkSavedData.get(level)
		val fireplaces = network.getFireplaces()

		val message = {
			val component = Component.literal("Fireplaces in ${levelRk.location()}: ${fireplaces.size}")

			for (fireplace in fireplaces) {
				val name = fireplace.name ?: "<unnamed>"
				component.append(Component.literal("\n- $name at ${fireplace.masterBlockPos}"))
			}

			component
		}

		source.sendSuccess(message, false)

		return 1
	}
}
