package dev.aaronhowser.mods.irregular_implements;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;

public interface EnderBridgeCarrier {

	default LongOpenHashSet irregular_implements$getEnderBridges() {
		throw new IllegalStateException();
	}

}
