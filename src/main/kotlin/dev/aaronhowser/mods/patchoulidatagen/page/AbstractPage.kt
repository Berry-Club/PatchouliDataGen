package dev.aaronhowser.mods.patchoulidatagen.page

import com.google.gson.JsonObject
import dev.aaronhowser.mods.patchoulidatagen.Util.addIfNotNull
import net.minecraft.resources.ResourceLocation

abstract class AbstractPage(
	protected val advancement: ResourceLocation?,
	protected val flag: String?,
	protected val anchor: String?
) {

	abstract fun getPageType(): String

	open fun addToJson(json: JsonObject) {
		json.apply {
			addIfNotNull("advancement", advancement)
			addIfNotNull("flag", flag)
			addIfNotNull("anchor", anchor)
		}
	}

	/**
	 * Overwrite this if you have a custom page type
	 */
	open fun getPageTypeLocation(): ResourceLocation {
		return ResourceLocation.fromNamespaceAndPath("patchouli", getPageType())
	}

	open fun getPageTypeId(): String = getPageTypeLocation().toString()

	abstract class Builder protected constructor() {
		protected var advancement: ResourceLocation? = null
		protected var flag: String? = null
		protected var anchor: String? = null

		fun advancement(advancement: ResourceLocation): Builder {
			this.advancement = advancement
			return this
		}

		fun flag(flag: String): Builder {
			this.flag = flag
			return this
		}

		fun anchor(anchor: String): Builder {
			this.anchor = anchor
			return this
		}
	}

}