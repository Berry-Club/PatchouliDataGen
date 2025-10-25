package dev.aaronhowser.mods.patchoulidatagen.book_element

import com.google.gson.JsonObject
import dev.aaronhowser.mods.patchoulidatagen.Util.addIfNotNull
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

		json.apply {
			addProperty("title", title)
			addProperty("description", description)
			addProperty("icon", icon.asItem().toString()) //TODO: Is this correct?
			addIfNotNull("sort_num", sortNum)
			addProperty("secret", secret)
		}

		return json
	}

	fun getCategoryId(): ResourceLocation {
		return ResourceLocation.fromNamespaceAndPath(header.getBookId(), this.getSaveName())
	}
}