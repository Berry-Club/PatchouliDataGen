package dev.aaronhowser.mods.patchoulidatagen.example_java;

import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookElement;
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider;
import net.minecraft.data.DataGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ExampleBookProviderJava extends PatchouliBookProvider {

	public ExampleBookProviderJava(
			@NotNull DataGenerator generator,
			@NotNull String bookName,
			@NotNull String modId
	) {
		super(generator, bookName, modId);
	}

	@Override
	public void buildPages(@NotNull Consumer<@NotNull PatchouliBookElement> consumer) {

	}
}
