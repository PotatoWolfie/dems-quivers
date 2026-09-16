package potatowolfie.dems_quivers.client;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import potatowolfie.dems_quivers.DemSQuivers;
import potatowolfie.dems_quivers.item.custom.quiver.QuiverContents;

public class DemSQuiversComponents {

    public static final DataComponentType<QuiverContents> QUIVER_CONTENTS = register("quiver_contents",
            DataComponentType.<QuiverContents>builder()
                    .persistent(QuiverContents.CODEC)
                    .networkSynchronized(QuiverContents.STREAM_CODEC)
                    .build());

    private static <T> DataComponentType<T> register(String name, DataComponentType<T> type) {
        ResourceKey<DataComponentType<?>> key = ResourceKey.create(Registries.DATA_COMPONENT_TYPE,
                Identifier.fromNamespaceAndPath(DemSQuivers.MOD_ID, name));
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, key, type);
    }

    public static void registerModComponents() {
        DemSQuivers.LOGGER.info("Registering Mod Data Components for " + DemSQuivers.MOD_ID);
    }
}