package dev.aaronhowser.mods.patchoulidatagen.page.defaults

import com.google.gson.JsonObject
import dev.aaronhowser.mods.patchoulidatagen.Util.addIfNotNull
import dev.aaronhowser.mods.patchoulidatagen.page.AbstractPage
import dev.aaronhowser.mods.patchoulidatagen.util.TripleEither
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike

typealias SpotlightItem = TripleEither<ItemLike, ItemStack, TagKey<Item>>

class SpotlightPage private constructor(
	private val spotlightItems: List<SpotlightItem>,
	private val linkRecipe: Boolean?,
	private val title: String?,
	private val text: String?,
	advancement: ResourceLocation?,
	flag: String?,
	anchor: String?
) : AbstractPage(advancement, flag, anchor) {

	override fun getPageType(): String = "spotlight"

	override fun addToJson(json: JsonObject) {
		super.addToJson(json)

		val sb = StringBuilder()
		val iterator = spotlightItems.iterator()
		while (iterator.hasNext()) {
			@Suppress("MoveVariableDeclarationIntoWhen")
			val next = iterator.next()

			when (next) {
				is TripleEither.First -> {
					val itemLike = next.value
					sb.append(itemLike.asItem().toString())
				}

				is TripleEither.Second -> {
					val itemStack = next.value
					sb.append(itemStack.item.toString())

					if (itemStack.count != 1) {
						sb.append("tag:").append(itemStack.count)
					}

//					val components = itemStack.componentsPatch
//					if (!components.isEmpty) {
//						sb.append(components.toString())
//					}
				}

				is TripleEither.Third -> {
					// TagKey<Item>
					sb.append("#").append(next.value.location.toString())
				}
			}

			if (iterator.hasNext()) {
				sb.append(",")
			}
		}

		json.apply {
			addProperty("item", sb.toString())
			addIfNotNull("title", title)
			addIfNotNull("text", text)
			addIfNotNull("link_recipe", linkRecipe)
		}
	}

	companion object {
		@JvmStatic
		fun builder() = Builder.builder()

		@JvmStatic
		fun basicPage(spotlightItemLike: ItemLike, title: String, text: String): SpotlightPage {
			return basicPage(TripleEither.First(spotlightItemLike), title, text)
		}

		@JvmStatic
		fun basicPage(spotlightItemStack: ItemStack, title: String, text: String): SpotlightPage {
			return basicPage(TripleEither.Second(spotlightItemStack), title, text)
		}

		@JvmStatic
		fun basicPage(spotlightItemTag: TagKey<Item>, title: String, text: String): SpotlightPage {
			return basicPage(TripleEither.Third(spotlightItemTag), title, text)
		}

		@JvmStatic
		fun basicPage(spotlightItem: SpotlightItem, title: String, text: String): SpotlightPage {
			return builder()
				.addSpotlightItem(spotlightItem)
				.title(title)
				.text(text)
				.build()
		}

		@JvmStatic
		fun basicPage(spotlightItemLike: ItemLike, text: String): SpotlightPage {
			return basicPage(TripleEither.First(spotlightItemLike), text)
		}

		@JvmStatic
		fun basicPage(spotlightItemStack: ItemStack, text: String): SpotlightPage {
			return basicPage(TripleEither.Second(spotlightItemStack), text)
		}

		@JvmStatic
		fun basicPage(spotlightItemTag: TagKey<Item>, text: String): SpotlightPage {
			return basicPage(TripleEither.Third(spotlightItemTag), text)
		}

		@JvmStatic
		fun basicPage(spotlightItem: SpotlightItem, text: String): SpotlightPage {
			return builder()
				.addSpotlightItem(spotlightItem)
				.text(text)
				.build()
		}

		@JvmStatic
		fun linkedPage(spotlightItemLike: ItemLike, title: String, text: String): SpotlightPage {
			return linkedPage(TripleEither.First(spotlightItemLike), title, text)
		}

		@JvmStatic
		fun linkedPage(spotlightItemStack: ItemStack, title: String, text: String): SpotlightPage {
			return linkedPage(TripleEither.Second(spotlightItemStack), title, text)
		}

		@JvmStatic
		fun linkedPage(spotlightItemTag: TagKey<Item>, title: String, text: String): SpotlightPage {
			return linkedPage(TripleEither.Third(spotlightItemTag), title, text)
		}

		@JvmStatic
		fun linkedPage(spotlightItem: SpotlightItem, title: String, text: String): SpotlightPage {
			return builder()
				.addSpotlightItem(spotlightItem)
				.title(title)
				.text(text)
				.linkRecipe(true)
				.build()
		}

		@JvmStatic
		fun linkedPage(spotlightItemLike: ItemLike, text: String): SpotlightPage {
			return linkedPage(TripleEither.First(spotlightItemLike), text)
		}

		@JvmStatic
		fun linkedPage(spotlightItemStack: ItemStack, text: String): SpotlightPage {
			return linkedPage(TripleEither.Second(spotlightItemStack), text)
		}

		@JvmStatic
		fun linkedPage(spotlightItemTag: TagKey<Item>, text: String): SpotlightPage {
			return linkedPage(TripleEither.Third(spotlightItemTag), text)
		}

		@JvmStatic
		fun linkedPage(spotlightItem: SpotlightItem, text: String): SpotlightPage {
			return builder()
				.addSpotlightItem(spotlightItem)
				.text(text)
				.linkRecipe(true)
				.build()
		}
	}

	class Builder private constructor() : AbstractPage.Builder<SpotlightPage, Builder>() {
		private var spotlightItems: MutableList<SpotlightItem> = mutableListOf()
		private var linkRecipe: Boolean? = null
		private var title: String? = null
		private var text: String? = null

		fun addItemLike(item: ItemLike): Builder {
			spotlightItems.add(TripleEither.First(item))
			return this
		}

		fun addItemStack(itemStack: ItemStack): Builder {
			spotlightItems.add(TripleEither.Second(itemStack))
			return this
		}

		fun addItemTag(tag: TagKey<Item>): Builder {
			spotlightItems.add(TripleEither.Third(tag))
			return this
		}

		fun addSpotlightItem(spotlightItem: SpotlightItem): Builder {
			spotlightItems.add(spotlightItem)
			return this
		}

		fun linkRecipe(linkRecipe: Boolean): Builder {
			this.linkRecipe = linkRecipe
			return this
		}

		fun title(title: String): Builder {
			this.title = title
			return this
		}

		fun title(title: Component): Builder {
			this.title = title.string
			return this
		}

		fun text(text: String): Builder {
			this.text = text
			return this
		}

		fun text(text: Component): Builder {
			this.text = text.string
			return this
		}

		override fun build(): SpotlightPage {
			require(spotlightItems.isNotEmpty()) { "At least one spotlight item must be set" }

			return SpotlightPage(
				spotlightItems = spotlightItems,
				linkRecipe = linkRecipe,
				title = title,
				text = text,
				advancement = advancement,
				flag = flag,
				anchor = anchor
			)
		}

		companion object {
			@JvmStatic
			fun builder() = Builder()
		}
	}

}