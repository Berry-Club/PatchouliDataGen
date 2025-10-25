package dev.aaronhowser.mods.patchoulidatagen.page

import com.google.gson.JsonObject
import net.minecraft.resources.ResourceLocation

interface PageType {

	fun getPageType(): String
	fun addToJson(json: JsonObject)

	/**
	 * Overwrite this if you have a custom page type
	 */
	fun getPageTypeLocation(): ResourceLocation {
		return ResourceLocation.fromNamespaceAndPath("patchouli", getPageType())
	}

	fun getPageTypeId(): String = getPageTypeLocation().toString()

}