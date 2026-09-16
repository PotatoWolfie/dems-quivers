package potatowolfie.dems_quivers;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import potatowolfie.dems_quivers.datagen.*;

public class DemSQuiversDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(DemSQuiversRegistryDataProvider::new);
		pack.addProvider(DemSQuiversModelProvider::new);
		pack.addProvider(DemSQuiversItemTagProvider::new);
		pack.addProvider(DemSQuiversRecipeGenerator::new);
		pack.addProvider(DemSQuiversVillagerTradeTags::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.VILLAGER_TRADE, DemSQuiversVillagerTrades::bootstrap);
	}
}
