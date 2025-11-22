package dev.aaronhowser.mods.patchoulidatagen.example_java;

import dev.aaronhowser.mods.patchoulidatagen.PatchouliDataGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.ExecutionException;

@EventBusSubscriber(modid = PatchouliDataGen.MOD_ID)
public class ModDatagenJava {

	@SubscribeEvent
	public static void onGatherData(GatherDataEvent event) throws ExecutionException, InterruptedException {
		DataGenerator generator = event.getGenerator();

		HolderLookup.Provider registries = event.getLookupProvider().get();

		ExampleBookProviderJava bookProvider = new ExampleBookProviderJava(
				generator,
				registries,
				"example_book_java",
				PatchouliDataGen.MOD_ID
		);
		generator.addProvider(event.includeClient(), bookProvider);
	}

}
