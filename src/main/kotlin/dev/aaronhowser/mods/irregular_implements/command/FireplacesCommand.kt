package dev.aaronhowser.mods.irregular_implements.command

import com.mojang.brigadier.builder.ArgumentBuilder
import dev.aaronhowser.mods.irregular_implements.handler.floo.FlooNetworkSavedData
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.Level

object FireplacesCommand {

	fun register(): ArgumentBuilder<CommandSourceStack, *> {
		return Commands
			.literal("fireplaces")
			.then(
				Commands.literal("list")
					.executes { ctx ->
						val source = ctx.source
						val levelRk = source.level.dimension()
						listFireplaces(source, levelRk)
					}
			)
	}

	private fun listFireplaces(source: CommandSourceStack, levelRk: ResourceKey<Level>): Int {
		val level = source.server.getLevel(levelRk)
		if (level == null) {
			source.sendFailure(Component.literal("Could not get level ${levelRk.location()}"))
			return 0
		}

		val component = {
			val network = FlooNetworkSavedData.get(level)
			val fireplaces = network.getFireplaces()

			val component = Component.literal("Fireplaces in ${levelRk.location()}: ${fireplaces.size}")
			for (fp in fireplaces) {
				val name = fp.name ?: "<unnamed>"
				component.append(Component.literal("\n- $name at ${fp.masterBlockPos}"))
			}

			component
		}

		source.sendSuccess(component, false)

		return 1
	}

}