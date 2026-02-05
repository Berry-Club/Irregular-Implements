package dev.aaronhowser.mods.irregular_implements.command

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModMessageLang
import dev.aaronhowser.mods.irregular_implements.handler.SpectreEnergyHandler
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.server.level.ServerPlayer

object SpectreEnergyCommand {

	private const val PLAYER = "player"
	private const val AMOUNT = "amount"

	fun register(): ArgumentBuilder<CommandSourceStack, *> {
		return Commands
			.literal("spectre-energy")
			.then(createGetCommand())
			.then(createSetCommand())
	}

	private fun createSetCommand(): LiteralArgumentBuilder<CommandSourceStack> {
		return Commands.literal("set")
			.requires { it.hasPermission(2) }
			.then(
				Commands.argument(AMOUNT, IntegerArgumentType.integer(0))
					.executes {
						val source = it.source
						val player = source.playerOrException
						val amount = IntegerArgumentType.getInteger(it, AMOUNT)
						setSpectreEnergy(source, player, amount)
					}
			)
			.then(
				Commands.argument(PLAYER, EntityArgument.player())
					.then(
						Commands.argument(AMOUNT, IntegerArgumentType.integer(0))
							.executes {
								val source = it.source
								val player = EntityArgument.getPlayer(it, PLAYER)
								val amount = IntegerArgumentType.getInteger(it, AMOUNT)
								setSpectreEnergy(source, player, amount)
							}
					)
			)
	}

	private fun createGetCommand(): LiteralArgumentBuilder<CommandSourceStack> {
		return Commands.literal("get")
			.requires { it.hasPermission(2) }
			.executes {
				val source = it.source
				val player = source.playerOrException
				getSpectreEnergy(source, player)
			}
			.then(
				Commands.argument(PLAYER, EntityArgument.player())
					.executes {
						val source = it.source
						val player = EntityArgument.getPlayer(it, PLAYER)
						getSpectreEnergy(source, player)
					}
			)
	}

	private fun setSpectreEnergy(
		source: CommandSourceStack,
		player: ServerPlayer,
		amount: Int
	): Int {
		val handler = SpectreEnergyHandler.get(player.serverLevel())
		handler.setStoredEnergy(player, amount)

		val amountString = String.format("%,d", amount)

		source.sendSuccess(
			{ ModMessageLang.SPECTRE_COMMAND_SET.toComponent(player.name, amountString) },
			true
		)

		return 1
	}

	private fun getSpectreEnergy(
		source: CommandSourceStack,
		player: ServerPlayer
	): Int {
		val handler = SpectreEnergyHandler.get(player.serverLevel())
		val stored = handler.getStoredEnergy(player)

		val amountString = String.format("%,d", stored)

		source.sendSuccess(
			{ ModMessageLang.SPECTRE_COMMAND_GET.toComponent(player.name, amountString) },
			false
		)

		return 1
	}

}