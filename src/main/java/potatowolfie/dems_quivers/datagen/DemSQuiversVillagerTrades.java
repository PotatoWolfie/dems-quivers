package potatowolfie.dems_quivers.datagen;

import potatowolfie.dems_quivers.DemSQuivers;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import potatowolfie.dems_quivers.item.DemSQuiversItems;

import java.util.List;
import java.util.Optional;

public class DemSQuiversVillagerTrades {
    public static final ResourceKey<VillagerTrade> FLETCHER_2_EMERALD_QUIVER = createKey("fletcher/2/quiver");
    public static final ResourceKey<VillagerTrade> FLETCHER_5_EMERALD_FROZEN_ARROW = createKey("fletcher/5/frozen_arrow");
    public static final ResourceKey<VillagerTrade> FLETCHER_5_EMERALD_OMINOUS_ARROW = createKey("fletcher/5/ominous_arrow");

    public static void bootstrap(BootstrapContext<VillagerTrade> context) {

        context.register(FLETCHER_2_EMERALD_QUIVER, new VillagerTrade(
                new TradeCost(Items.EMERALD, 11),
                new ItemStackTemplate(DemSQuiversItems.QUIVER),
                4, 8, 0.05f,
                Optional.empty(), List.of()));

        context.register(FLETCHER_5_EMERALD_FROZEN_ARROW, new VillagerTrade(
                new TradeCost(Items.EMERALD, 5),
                Optional.of(new TradeCost(Items.ARROW, 5)),
                new ItemStackTemplate(DemSQuiversItems.FROZEN_ARROW, 5),
                12, 30, 0.05f,
                Optional.empty(), List.of()));

        context.register(FLETCHER_5_EMERALD_OMINOUS_ARROW, new VillagerTrade(
                new TradeCost(Items.EMERALD, 3),
                Optional.of(new TradeCost(Items.ARROW, 5)),
                new ItemStackTemplate(DemSQuiversItems.OMINOUS_ARROW, 5),
                12, 30, 0.05f,
                Optional.empty(), List.of()));

    }


    private static ResourceKey<VillagerTrade> createKey(String name) {
        return ResourceKey.create(Registries.VILLAGER_TRADE, Identifier.fromNamespaceAndPath(DemSQuivers.MOD_ID, name));
    }
}