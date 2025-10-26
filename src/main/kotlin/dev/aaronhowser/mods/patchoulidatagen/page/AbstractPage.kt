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

	@Suppress("UNCHECKED_CAST")
	abstract class Builder<T: Builder<T>> protected constructor() {
		protected var advancement: ResourceLocation? = null
		protected var flag: String? = null
		protected var anchor: String? = null

		fun advancement(advancement: ResourceLocation): T {
			this.advancement = advancement
			return this as T
		}

		fun flag(flag: String): T {
			this.flag = flag
			return this as T
		}

		fun anchor(anchor: String): T {
			this.anchor = anchor
			return this as T
		}
	}

}