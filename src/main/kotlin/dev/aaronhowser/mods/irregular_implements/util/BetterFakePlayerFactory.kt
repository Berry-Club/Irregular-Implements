package dev.aaronhowser.mods.irregular_implements.util

import com.mojang.authlib.GameProfile
import net.minecraft.server.level.ServerLevel
import net.neoforged.neoforge.common.util.FakePlayer
import net.neoforged.neoforge.common.util.FakePlayerFactory

object BetterFakePlayerFactory {

	fun get(level: ServerLevel, username: GameProfile, create: () -> FakePlayer): FakePlayer {
		val key = FakePlayerFactory.FakePlayerKey(level, username)
		return FakePlayerFactory.fakePlayers.getOrPut(key, create)
	}

}