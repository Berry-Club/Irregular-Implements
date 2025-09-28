package dev.aaronhowser.mods.irregular_implements.command

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.SendClientToast
import net.minecraft.commands.CommandBuildContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.commands.arguments.item.ItemArgument
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack

object NotifyCommand {

	private const val PLAYERS = "players"
	private const val TITLE = "title"
	private const val BODY = "body"
	private const val ITEM = "item"

	fun register(ctx: CommandBuildContext): ArgumentBuilder<CommandSourceStack, *> {
		return Commands
			.literal("notify")
			.then(
				Commands.argument(PLAYERS, EntityArgument.players())
					.then(
						Commands.argument(TITLE, StringArgumentType.string())
							.then(
								Commands.argument(BODY, StringArgumentType.string())
									.then(
										Commands.argument(ITEM, ItemArgument.item(ctx))
											.executes {
												val source = it.source

												val targets = EntityArgument.getPlayers(it, PLAYERS)
												val title = StringArgumentType.getString(it, TITLE)
												val body = StringArgumentType.getString(it, BODY)
												val item = ItemArgument.getItem(it, ITEM).createItemStack(1, false)

												notify(source, targets, title, body, item)
											})
							)
					)
			)
	}

	private fun notify(
		source: CommandSourceStack,
		targets: Collection<ServerPlayer>,
		title: String,
		body: String,
		item: ItemStack
	): Int {
		val packet = SendClientToast(title, body, item)

		for (target in targets) {
			packet.messagePlayer(target)
		}

		source.sendSuccess(
			{ Component.literal("Toast send to player!") },
			true
		)

		return 1
	}

}