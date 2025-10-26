package dev.aaronhowser.mods.patchoulidatagen.provider

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookCategory
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookElement
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookEntry
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBook
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataGenerator
import net.minecraft.data.DataProvider
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import java.nio.file.Path
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

abstract class PatchouliBookProvider(
	protected val generator: DataGenerator,
	protected val bookName: String,
	protected val modId: String
) : DataProvider {

	protected val gson: Gson = GsonBuilder().setPrettyPrinting().create()

	override fun getName(): String {
		return "Patchouli Book: $bookName"
	}

	override fun run(output: CachedOutput): CompletableFuture<*> {
		val dataFolder = generator.packOutput

		val bookLocations = mutableSetOf<String>()
		val bookDefaultPath = "assets/$modId/patchouli_books/$bookName/en_us"

		val futures = mutableListOf<CompletableFuture<*>>()

		val elementConsumer: Consumer<PatchouliBookElement> = Consumer { element ->
			val addedSuccessfully = bookLocations.add(element.getSaveName())

			if (!addedSuccessfully) {
				val rl = ResourceLocation.fromNamespaceAndPath(modId, element.getSaveName())
				error("Duplicate book element [$rl]")
			}

			when (element) {
				is PatchouliBookEntry -> {
					val entryFolder = resolvePath(
						dataFolder,
						"$bookDefaultPath/entries/${element.getSaveName()}.json"
					)

					saveData(futures, gson, output, element, entryFolder)
				}

				is PatchouliBookCategory -> {
					val categoryFolder = resolvePath(
						dataFolder,
						"$bookDefaultPath/categories/${element.getSaveName()}.json"
					)

					saveData(futures, gson, output, element, categoryFolder)
				}

				is PatchouliBook -> {
					val headerFolder = resolvePath(
						dataFolder,
						"data/$modId/patchouli_books/$bookName/${element.getSaveName()}.json"
					)

					saveData(futures, gson, output, element, headerFolder)
				}
			}

		}

		buildPages(elementConsumer)

		return CompletableFuture.allOf(*futures.toTypedArray())
	}

	private fun <T : PatchouliBookElement> saveData(
		futures: MutableList<CompletableFuture<*>>,
		gson: Gson,    // unused currently
		cache: CachedOutput,
		bookElement: T,
		bookElementPath: Path
	) {
		val future = DataProvider.saveStable(cache, bookElement.toJson(), bookElementPath)
		futures.add(future)
	}

	private fun resolvePath(path: PackOutput, pathOther: String): Path {
		return path.outputFolder.resolve(pathOther)
	}

	abstract fun buildPages(consumer: Consumer<PatchouliBookElement>)

}