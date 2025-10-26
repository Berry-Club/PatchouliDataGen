package dev.aaronhowser.mods.patchoulidatagen.book_element

import com.google.gson.JsonObject
import dev.aaronhowser.mods.patchoulidatagen.Util.addIfNotNull
import dev.aaronhowser.mods.patchoulidatagen.Util.isNotTrue
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.ItemLike
import java.util.function.Consumer

class BookCategory private constructor(
	private val name: String,
	private val description: String,
	private val icon: ItemLike,
	private val sortNum: Int?,
	private val secret: Boolean?,
	private val saveName: String,
	private val header: BookHeader
) : BookElement {

	override fun getSaveName(): String = this.saveName

	override fun toJson(): JsonObject {
		val json = JsonObject()

		json.apply {
			addProperty("name", name)
			addProperty("description", description)
			addProperty("icon", icon.asItem().toString()) //TODO: Is this correct?
			addIfNotNull("sort_num", sortNum)
			addIfNotNull("secret", secret)
		}

		return json
	}

	fun getCategoryId(): ResourceLocation {
		return ResourceLocation.fromNamespaceAndPath(header.getBookId(), this.getSaveName())
	}

	companion object {
		@JvmStatic
		fun builder(): Builder = Builder.category()
	}

	class Builder private constructor() {

		private var bookHeader: BookHeader? = null
		private var name: String? = null
		private var description: String? = null
		private var icon: ItemLike? = null
		private var sortNum: Int? = null
		private var secret: Boolean? = null

		fun header(header: BookHeader): Builder {
			this.bookHeader = header
			return this
		}

		fun setDisplay(
			name: Component,
			description: Component,
			icon: ItemLike
		): Builder {
			if (this.name != null || this.description != null || this.icon != null) {
				error("Display properties have already been set!")
			}

			if (bookHeader?.isTranslatable().isNotTrue()) {
				error("Cannot use a Component name or description with a non-translatable BookHeader")
			}

			this.name = name.string
			this.description = description.string
			this.icon = icon
			return this
		}

		fun setDisplay(
			name: String,
			description: String,
			icon: ItemLike
		): Builder {
			if (this.name != null || this.description != null || this.icon != null) {
				error("Display properties have already been set!")
			}

			this.name = name
			this.description = description
			this.icon = icon
			return this
		}

		fun sortNum(sortNum: Int): Builder {
			this.sortNum = sortNum
			return this
		}

		fun secret(secret: Boolean): Builder {
			this.secret = secret
			return this
		}

		fun secret(): Builder {
			this.secret = true
			return this
		}

		fun save(consumer: Consumer<BookElement>, saveName: String): BookCategory {
			val category = build(saveName)
			consumer.accept(category)
			return category
		}

		private fun build(saveName: String): BookCategory {
			if (name == null || description == null || icon == null) {
				error("Display properties have not been set!")
			}

			if (bookHeader == null) {
				error("BookHeader has not been set!")
			}

			val bookCategory = BookCategory(
				name = this.name!!,
				description = this.description!!,
				icon = this.icon!!,
				sortNum = this.sortNum,
				secret = this.secret,
				saveName = saveName,
				header = this.bookHeader!!
			)

			return bookCategory
		}

		companion object {
			@JvmStatic
			fun category(): Builder = Builder()
		}
	}

}