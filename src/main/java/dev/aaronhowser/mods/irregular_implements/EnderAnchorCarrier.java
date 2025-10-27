package dev.aaronhowser.mods.irregular_implements;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;

public interface EnderAnchorCarrier {

	default LongOpenHashSet irregular_implements$getEnderAnchorPositions() {
		throw new IllegalStateException();
	}

}
