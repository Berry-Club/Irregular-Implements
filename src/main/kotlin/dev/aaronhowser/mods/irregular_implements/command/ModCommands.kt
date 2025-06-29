package dev.aaronhowser.mods.irregular_implements.command

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands

object ModCommands {

	fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
		dispatcher.register(
			Commands.literal("irregular-implements")
				.then(FireplacesCommand.register())
		)

	}

}