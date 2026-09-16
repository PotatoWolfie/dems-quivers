package potatowolfie.dems_quivers;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import potatowolfie.dems_quivers.effect.DemSQuiversEffects;
import potatowolfie.dems_quivers.item.DemSQuiversItems;
import potatowolfie.dems_quivers.network.DemSQuiversNetworking;

public class DemSQuivers implements ModInitializer {
	public static final String MOD_ID = "dems-quivers";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		DemSQuiversItems.registerModItems();
		DemSQuiversNetworking.registerCommon();
		DemSQuiversEffects.registerEffects();

		LOGGER.info("Hello Fabric world!");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
