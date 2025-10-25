package dev.aaronhowser.mods.patchoulidatagen.book_element

import com.google.gson.JsonObject

interface BookElement {

	fun getSaveName(): String
	fun toJson(): JsonObject

}