package dev.aaronhowser.mods.patchoulidatagen.util

import com.google.gson.JsonObject
import com.mojang.serialization.JsonOps
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentSerialization
import net.minecraft.resources.ResourceLocation

object Util {

	fun Boolean?.isTrue(): Boolean = this == true
	fun Boolean?.isNotTrue(): Boolean = this != true

	@JvmStatic
	fun JsonObject.addProperty(key: String, value: ResourceLocation) {
		this.addProperty(key, value.toString())
	}

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
			this.addProperty(key, value.toString())
		}
	}

	@JvmStatic
	fun JsonObject.addIfNotNull(key: String, value: Component?) {
		if (value != null) {
			val element = ComponentSerialization.CODEC
				.encodeStart(JsonOps.INSTANCE, value)
				.result()
				.orElseThrow()
			this.add(key, element)
		}
	}

	@JvmStatic
	fun JsonObject.addIfNotNull(key: String, value: JsonObject?) {
		if (value != null) {
			this.add(key, value)
		}
	}

}