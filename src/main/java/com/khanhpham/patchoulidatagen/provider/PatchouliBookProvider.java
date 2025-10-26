package com.khanhpham.patchoulidatagen.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.aaronhowser.mods.patchoulidatagen.book_element.BookCategory;
import dev.aaronhowser.mods.patchoulidatagen.book_element.BookElement;
import dev.aaronhowser.mods.patchoulidatagen.book_element.BookEntry;
import dev.aaronhowser.mods.patchoulidatagen.book_element.Book;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * @see net.minecraft.data.advancements.AdvancementProvider
 */
public abstract class PatchouliBookProvider implements DataProvider {
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	private final DataGenerator generator;
	@Nullable
	private final net.neoforged.neoforge.common.data.ExistingFileHelper fileHelper;
	protected final String modid;
	private final String bookName;

	public PatchouliBookProvider(
			DataGenerator generator, @Nullable ExistingFileHelper fileHelper,
			String bookName, String modid
	) {
		this.fileHelper = fileHelper;
		this.generator = generator;
		this.modid = modid;
		this.bookName = bookName;
	}

	public PatchouliBookProvider(DataGenerator generator, String bookName, String modid) {
		this(generator, null, bookName, modid);
	}

	@Override
	public @NotNull CompletableFuture<?> run(CachedOutput pOutput) {
		PackOutput dataFolder = generator.getPackOutput();
		Set<String> bookLocations = new HashSet<>();
		final String bookDefaultPath = "data/" + modid + "/patchouli_books/" + bookName + "/en_us/";
		Consumer<BookElement> elementConsumer = ((element) -> {
			if (bookLocations.add(element.getSaveName())) {
				if (element instanceof BookEntry entries) {
					Path entryFolder = resolvePath(
							dataFolder,
							bookDefaultPath + "entries/" + entries.getSaveName() + ".json"
					);
					this.saveData(gson, pOutput, entries, entryFolder);
				} else if (element instanceof BookCategory category) {
					Path categoryFolder = resolvePath(
							dataFolder,
							bookDefaultPath + "categories/" + category.getSaveName() + ".json"
					);
					this.saveData(gson, pOutput, category, categoryFolder);
				} else if (element instanceof Book header) {
					Path headerFolder = resolvePath(
							dataFolder,
							"data/" + modid + "/patchouli_books/" + bookName + "/book.json"
					);
					this.saveData(gson, pOutput, header, headerFolder);
				}

			} else {
				ResourceLocation bookLocation = ResourceLocation.fromNamespaceAndPath(modid, element.getSaveName());
				throw new IllegalStateException("Duplicate book page [" + bookLocation + "]");
			}
		});

		return CompletableFuture.runAsync(() -> {
			buildPages(elementConsumer);
		});
	}

	private Path resolvePath(PackOutput path, String pathOther) {
		return path.getOutputFolder().resolve(pathOther);
	}

	private <T extends BookElement> void saveData(
			Gson gson, CachedOutput cache, T jsonObject,
			Path bookElementPath
	) {
		DataProvider.saveStable(cache, jsonObject.toJson(), bookElementPath);
	}

	protected abstract void buildPages(Consumer<BookElement> consumer);

	@Override
	public @NotNull String getName() {
		return "Patchouli Book: " + bookName.toLowerCase();
	}
}
