package dev.aaronhowser.mods.patchoulidatagen.page.defaults

import com.google.gson.JsonObject
import com.khanhpham.patchoulidatagen.pages.pagetype.PageType

/**
 * This is an empty page with no text
 *
 * See [Page Types - Multiblock Pages](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/page-types/#multiblock-pages)
 */
class MultiblockPage : PageType {
	override fun getPageType(): String? {
		TODO("Not yet implemented")
	}

	override fun toJson(json: JsonObject?) {
		TODO("Not yet implemented")
	}
}