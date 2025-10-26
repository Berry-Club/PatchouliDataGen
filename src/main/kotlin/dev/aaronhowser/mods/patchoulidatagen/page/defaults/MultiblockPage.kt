package dev.aaronhowser.mods.patchoulidatagen.page.defaults

import com.google.gson.JsonObject
import dev.aaronhowser.mods.patchoulidatagen.Util.addIfNotNull
import dev.aaronhowser.mods.patchoulidatagen.multiblock.Multiblock
import dev.aaronhowser.mods.patchoulidatagen.page.AbstractPage
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

/**
 * This is an empty page with no text
 *
 * See [Page Types - Multiblock Pages](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/page-types/#multiblock-pages)
 */
class MultiblockPage private constructor(
	private val multiblockName: String,
	private val multiblock: Multiblock?,
	private val multiblockId: String?,
	private val enableVisualize: Boolean?,
	private val text: Component?,
	advancement: ResourceLocation?,
	flag: String?,
	anchor: String?
) : AbstractPage(advancement, flag, anchor) {

	override fun getPageType(): String = "multiblock"

	override fun addToJson(json: JsonObject) {
		super.addToJson(json)

		json.apply {
			addProperty("name", multiblockName)

			addIfNotNull("multiblock", multiblock?.toJson())
			addIfNotNull("multiblock_id", multiblockId)
			addIfNotNull("enable_visualize", enableVisualize)
			addIfNotNull("text", text)
		}
	}

	companion object {
		@JvmStatic
		fun builder() = Builder.setup()
	}

	class Builder private constructor(): AbstractPage.Builder<MultiblockPage, Builder>() {
		private var multiBlockName: String? = null
		private var multiblock: Multiblock? = null
		private var multiblockId: String? = null
		private var enableVisualize: Boolean? = null
		private var text: Component? = null

		fun multiblock(
			multiblockName: String,
			multiblockBuilder: Multiblock.Builder
		): Builder {
			this.multiBlockName = multiblockName
			this.multiblock = multiblockBuilder.build()
			return this
		}

		fun multiblockId(id: String): Builder {
			this.multiblockId = id
			return this
		}

		fun disableVisualize(): Builder {
			this.enableVisualize = false
			return this
		}

		private fun pageText(text: Component): Builder {
			this.text = text
			return this
		}

		override fun build(): MultiblockPage {
			require(multiBlockName != null && multiblock != null) { "Multiblock name must be set" }

			return MultiblockPage(
				multiblockName = multiBlockName!!,
				multiblock = multiblock!!,
				multiblockId = multiblockId,
				enableVisualize = enableVisualize,
				text = text,
				advancement = advancement,
				flag = flag,
				anchor = anchor
			)
		}

		companion object {
			fun setup() = Builder()
		}

	}

}