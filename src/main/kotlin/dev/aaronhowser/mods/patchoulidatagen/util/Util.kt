package dev.aaronhowser.mods.patchoulidatagen.util

import com.google.gson.JsonObject
import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import dev.aaronhowser.mods.aaron.AaronExtensions.cast
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponentPatch
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.nbt.NbtOps
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentSerialization
import net.minecraft.resources.RegistryOps
import net.minecraft.resources.ResourceLocation

object Util {

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

	fun getComponentPatchString(
		componentPatch: DataComponentPatch,
		registries: HolderLookup.Provider
	): String {
		val sb = StringBuilder()

		sb.append("[")

		var first = true

		for ((key, value) in componentPatch.entrySet()) {
			val id = BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(key) ?: continue
			val codec = key.codec() ?: continue

			if (first) {
				first = false
			} else {
				sb.append(",")
			}

			if (value.isPresent) {
				sb.append(id).append("=")

				val v = if (codec == Codec.BOOL) {
					value.get()
				} else {
					val context = RegistryOps.create(NbtOps.INSTANCE, registries)
					codec.encodeStart(context, value.get().cast()).getOrThrow()
				}

				sb.append(v)
			} else {
				sb.append("!").append(id)
			}
		}

		sb.append("]")
		return sb.toString()
	}

}