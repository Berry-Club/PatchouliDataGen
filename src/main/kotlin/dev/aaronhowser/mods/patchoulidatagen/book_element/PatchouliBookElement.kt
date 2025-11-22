package dev.aaronhowser.mods.patchoulidatagen.book_element

import com.google.gson.JsonObject
import net.minecraft.core.RegistryAccess

interface PatchouliBookElement {

	fun getSaveName(): String
	fun toJson(registryAccess: RegistryAccess): JsonObject

}