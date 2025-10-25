package dev.aaronhowser.mods.patchoulidatagen.page.defaults

import com.google.gson.JsonObject
import dev.aaronhowser.mods.patchoulidatagen.page.PageType

/**
 * This is an empty page with no text
 *
 * See [Page Types - Empty Pages](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/page-types/#empty-pages)
 */
class EmptyPage private constructor(
	private val drawFiller: Boolean
) : PageType {

	override fun getPageType(): String = "empty"

	override fun addToJson(json: JsonObject) {
		if (drawFiller) {
			json.addProperty("draw_filler", true)
		}
	}

	companion object {
		@JvmStatic
		fun setup() = EmptyPage(false)

		@JvmStatic
		fun setupWithFiller() = EmptyPage(true)
	}
}