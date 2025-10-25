package dev.aaronhowser.mods.patchoulidatagen.book_element

import com.google.gson.JsonObject
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
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

		json.addProperty(TITLE, this.title)
		json.addProperty(DESCRIPTION, this.description)
		json.addProperty(ICON, BuiltInRegistries.ITEM.getKey(this.icon.asItem()).toString())

		if (this.sortNum != null) {
			json.addProperty(SORT_NUM, this.sortNum)
		}

		json.addProperty(SECRET, this.secret)

		return json
	}

	fun getCategoryId(): ResourceLocation {
		return ResourceLocation.fromNamespaceAndPath(header.getBookId(), this.getSaveName())
	}

	companion object {
		const val TITLE = "title"
		const val DESCRIPTION = "description"
		const val ICON = "icon"
		const val SORT_NUM = "sort_num"
		const val SECRET = "secret"
	}
}