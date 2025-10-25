package dev.aaronhowser.mods.patchoulidatagen.book_element

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import dev.aaronhowser.mods.patchoulidatagen.Util.addIfNotNull
import net.minecraft.network.chat.Component
import net.minecraft.world.level.ItemLike
import java.util.function.Consumer

class BookEntry private constructor(
	private val saveName: String,
	private val category: BookCategory,
	private val name: String,
	private val icon: ItemLike,
	private val pages: Set<PageType>,
	private val advancement: String?,
	private val configFlag: String?,
	private val priority: Boolean?,
	private val secret: Boolean?,
	private val readByDefault: Boolean?,
	private val sortNum: Int?,
	private val turnIn: String?
) : BookElement {

	override fun getSaveName(): String = saveName

	override fun toJson(): JsonObject {
		val json = JsonObject()

		json.apply {
			addProperty("name", name)
			addProperty("category", category.getCategoryId().toString())
			addProperty("icon", icon.asItem().toString())

			val pagesArray = JsonArray()
			for (page in pages) {
				val pageJson = JsonObject()

				pageJson.addProperty("type", page.pageTypeId)
				page.toJson(pageJson)

				pagesArray.add(pageJson)
			}
			add("advancement", pagesArray)

			addIfNotNull("advancement", advancement)
			addIfNotNull("config_flag", configFlag)
			addIfNotNull("priority", priority)
			addIfNotNull("secret", secret)
			addIfNotNull("read_by_default", readByDefault)
			addIfNotNull("sortnum", sortNum)
			addIfNotNull("turnin", turnIn)
		}

		return json
	}

	companion object {
		@JvmStatic
		fun setup() = Builder.entry()
	}

	class Builder private constructor() {

		private val pages: MutableSet<PageType> = mutableSetOf()
		private var category: BookCategory? = null
		private var name: String? = null
		private var icon: ItemLike? = null
		private var advancement: String? = null
		private var configFlag: String? = null
		private var priority: Boolean? = null
		private var secret: Boolean? = null
		private var readByDefault: Boolean? = null
		private var sortNum: Int? = null
		private var turnIn: String? = null

		fun category(category: BookCategory): Builder {
			this.category = category
			return this
		}

		fun setAdvancement(advancement: String): Builder {
			this.advancement = advancement
			return this
		}

		fun setConfigFlag(configFlag: String): Builder {
			this.configFlag = configFlag
			return this
		}

		fun setPriority(priority: Boolean): Builder {
			this.priority = priority
			return this
		}

		fun setSecret(secret: Boolean): Builder {
			this.secret = secret
			return this
		}

		fun setReadByDefault(readByDefault: Boolean): Builder {
			this.readByDefault = readByDefault
			return this
		}

		fun setSortNum(sortNum: Int): Builder {
			this.sortNum = sortNum
			return this
		}

		fun setTurnIn(turnIn: String): Builder {
			this.turnIn = turnIn
			return this
		}

		fun display(entryName: String, icon: ItemLike): Builder {
			this.name = entryName
			this.icon = icon
			return this
		}

		fun display(entryName: Component, icon: ItemLike): Builder {
			this.name = entryName.string
			this.icon = icon
			return this
		}

		fun addPage(page: PageType): Builder {
			this.pages.add(page)
			return this
		}

		fun build(consumer: Consumer<BookElement>, saveName: String): BookEntry {

			require(category != null) { "BookEntry $saveName is missing category!" }
			require(name != null && icon != null) { "BookEntry $saveName is missing display!" }

			val entry = BookEntry(
				saveName,
				category!!,
				name!!,
				icon!!,
				pages,
				advancement,
				configFlag,
				priority,
				secret,
				readByDefault,
				sortNum,
				turnIn
			)

			consumer.accept(entry)
			return entry
		}

		companion object {
			fun entry(): Builder = Builder()
		}

	}

}