package potatowolfie.dems_quivers.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.VillagerTradeTags;
import net.minecraft.world.item.trading.VillagerTrade;

import java.util.concurrent.CompletableFuture;

public class DemSQuiversVillagerTradeTags extends FabricTagsProvider<VillagerTrade> {
    public DemSQuiversVillagerTradeTags(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, Registries.VILLAGER_TRADE, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        getOrCreateRawBuilder(VillagerTradeTags.FLETCHER_LEVEL_2)
                .add(TagEntry.element(DemSQuiversVillagerTrades.FLETCHER_2_EMERALD_QUIVER.identifier()));

        getOrCreateRawBuilder(VillagerTradeTags.FLETCHER_LEVEL_5)
                .add(TagEntry.element(DemSQuiversVillagerTrades.FLETCHER_5_EMERALD_FROZEN_ARROW.identifier()))
                .add(TagEntry.element(DemSQuiversVillagerTrades.FLETCHER_5_EMERALD_OMINOUS_ARROW.identifier()));

    }
}