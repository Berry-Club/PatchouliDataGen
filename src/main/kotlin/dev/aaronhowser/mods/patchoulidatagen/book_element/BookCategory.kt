package dev.aaronhowser.mods.patchoulidatagen.book_element

import com.google.gson.JsonObject
import net.minecraft.world.level.ItemLike

class BookCategory private constructor(
	private val title: String,
	private val description: String,
	private val icon: ItemLike,
	private val sortNum: Int?,
	private val secret: Boolean,
	private val saveName: String,
	private val header: BookHeader
) : BookElement {

	override fun getSaveName(): String = this.saveName

	override fun toJson(): JsonObject {
		val json = JsonObject()

		json.addProperty()

	}

	companion object {
		const val NAME = "name"
		const val DESCRIPTION = "description"
		const val ICON = "icon"
		const val SORT_NUM = "sort_num"
		const val SECRET
	}
}