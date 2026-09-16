package potatowolfie.dems_quivers.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import potatowolfie.dems_quivers.item.DemSQuiversItems;
import potatowolfie.dems_quivers.item.DemSQuiversTags;

import java.util.concurrent.CompletableFuture;

public class DemSQuiversItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
    public DemSQuiversItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        builder(DemSQuiversTags.QUIVERS)
                .add(BuiltInRegistries.ITEM.wrapAsHolder(DemSQuiversItems.QUIVER).unwrapKey().orElseThrow())
                .add(BuiltInRegistries.ITEM.wrapAsHolder(DemSQuiversItems.DYED_QUIVER.white()).unwrapKey().orElseThrow())
                .add(BuiltInRegistries.ITEM.wrapAsHolder(DemSQuiversItems.DYED_QUIVER.orange()).unwrapKey().orElseThrow())
                .add(BuiltInRegistries.ITEM.wrapAsHolder(DemSQuiversItems.DYED_QUIVER.magenta()).unwrapKey().orElseThrow())
                .add(BuiltInRegistries.ITEM.wrapAsHolder(DemSQuiversItems.DYED_QUIVER.lightBlue()).unwrapKey().orElseThrow())
                .add(BuiltInRegistries.ITEM.wrapAsHolder(DemSQuiversItems.DYED_QUIVER.yellow()).unwrapKey().orElseThrow())
                .add(BuiltInRegistries.ITEM.wrapAsHolder(DemSQuiversItems.DYED_QUIVER.lime()).unwrapKey().orElseThrow())
                .add(BuiltInRegistries.ITEM.wrapAsHolder(DemSQuiversItems.DYED_QUIVER.pink()).unwrapKey().orElseThrow())
                .add(BuiltInRegistries.ITEM.wrapAsHolder(DemSQuiversItems.DYED_QUIVER.gray()).unwrapKey().orElseThrow())
                .add(BuiltInRegistries.ITEM.wrapAsHolder(DemSQuiversItems.DYED_QUIVER.lightGray()).unwrapKey().orElseThrow())
                .add(BuiltInRegistries.ITEM.wrapAsHolder(DemSQuiversItems.DYED_QUIVER.cyan()).unwrapKey().orElseThrow())
                .add(BuiltInRegistries.ITEM.wrapAsHolder(DemSQuiversItems.DYED_QUIVER.purple()).unwrapKey().orElseThrow())
                .add(BuiltInRegistries.ITEM.wrapAsHolder(DemSQuiversItems.DYED_QUIVER.blue()).unwrapKey().orElseThrow())
                .add(BuiltInRegistries.ITEM.wrapAsHolder(DemSQuiversItems.DYED_QUIVER.brown()).unwrapKey().orElseThrow())
                .add(BuiltInRegistries.ITEM.wrapAsHolder(DemSQuiversItems.DYED_QUIVER.green()).unwrapKey().orElseThrow())
                .add(BuiltInRegistries.ITEM.wrapAsHolder(DemSQuiversItems.DYED_QUIVER.red()).unwrapKey().orElseThrow())
                .add(BuiltInRegistries.ITEM.wrapAsHolder(DemSQuiversItems.DYED_QUIVER.black()).unwrapKey().orElseThrow());

        builder(ItemTags.ARROWS)
                .add(BuiltInRegistries.ITEM.wrapAsHolder(DemSQuiversItems.FROZEN_ARROW).unwrapKey().orElseThrow())
                .add(BuiltInRegistries.ITEM.wrapAsHolder(DemSQuiversItems.OMINOUS_ARROW).unwrapKey().orElseThrow());
    }
}
