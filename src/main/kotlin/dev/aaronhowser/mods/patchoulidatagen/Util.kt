package dev.aaronhowser.mods.patchoulidatagen

import com.google.gson.JsonObject
import net.minecraft.resources.ResourceLocation

object Util {

	fun Boolean?.isTrue(): Boolean = this == true
	fun Boolean?.isNotTrue(): Boolean = this != true

	@JvmStatic
	fun JsonObject.addIfNotNull(key: String, value: String?) {
		if (value != null) {
			this.addProperty(key, value)
		}
	}

	@JvmStatic
	fun JsonObject.addIfNotNull(key: String, value: Number?) {
		if (value != null) {
			this.addProperty(key, value)
		}
	}

	@JvmStatic
	fun JsonObject.addIfNotNull(key: String, value: Boolean?) {
		if (value != null) {
			this.addProperty(key, value)
		}
	}

	@JvmStatic
	fun JsonObject.addIfNotNull(key: String, value: ResourceLocation?) {
		if (value != null) {
			this.add(key, value)
		}
	}

}