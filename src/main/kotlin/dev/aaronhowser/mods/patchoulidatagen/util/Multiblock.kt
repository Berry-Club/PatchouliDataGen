package dev.aaronhowser.mods.patchoulidatagen.util

import com.google.gson.JsonObject

interface Multiblock {

	fun toJson(): JsonObject

	interface Builder {
		fun build(): Multiblock
	}

}