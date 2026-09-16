package potatowolfie.dems_quivers.item;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.ColorCollection;
import potatowolfie.dems_quivers.DemSQuivers;
import potatowolfie.dems_quivers.client.DemSQuiversComponents;
import potatowolfie.dems_quivers.item.custom.FrozenArrowItem;
import potatowolfie.dems_quivers.item.custom.OminousArrowItem;
import potatowolfie.dems_quivers.item.custom.quiver.QuiverContents;
import potatowolfie.dems_quivers.item.custom.quiver.QuiverItem;

import java.util.List;

public class DemSQuiversItems {

    public static final Item QUIVER = registerItem("quiver",
            new QuiverItem(new Item.Properties()
                    .stacksTo(1)
                    .component(DemSQuiversComponents.QUIVER_CONTENTS, QuiverContents.EMPTY)
                    .setId(createItemRegistryKey("quiver"))));

    public static final ColorCollection<Item> DYED_QUIVER = ColorCollection.registerItems(
            ColorCollection.prefixWithColor(ColorCollection.create("quiver")),
            (name, color) -> registerItem(name, new QuiverItem(new Item.Properties()
                    .stacksTo(1)
                    .component(DemSQuiversComponents.QUIVER_CONTENTS, QuiverContents.EMPTY)
                    .setId(createItemRegistryKey(name))))
    );

    public static final Item FROZEN_ARROW = registerItem("frozen_arrow",
            new FrozenArrowItem(new Item.Properties()
                    .setId(createItemRegistryKey("frozen_arrow"))));

    public static final Item OMINOUS_ARROW = registerItem("ominous_arrow",
            new OminousArrowItem(new Item.Properties()
                    .setId(createItemRegistryKey("ominous_arrow"))));

    private static ResourceKey<Item> createItemRegistryKey(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DemSQuivers.MOD_ID, name));
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, createItemRegistryKey(name), item);
    }

    public static void registerModItems() {
        DemSQuiversComponents.registerModComponents();
        DemSQuivers.LOGGER.info("Registering Mod Items for " + DemSQuivers.MOD_ID);

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register(output -> {

                    output.insertAfter(Items.DYED_BUNDLE.black(), List.of(
                            new ItemStack(QUIVER),
                            new ItemStack(DYED_QUIVER.white()),
                            new ItemStack(DYED_QUIVER.orange()),
                            new ItemStack(DYED_QUIVER.magenta()),
                            new ItemStack(DYED_QUIVER.lightBlue()),
                            new ItemStack(DYED_QUIVER.yellow()),
                            new ItemStack(DYED_QUIVER.lime()),
                            new ItemStack(DYED_QUIVER.pink()),
                            new ItemStack(DYED_QUIVER.gray()),
                            new ItemStack(DYED_QUIVER.lightGray()),
                            new ItemStack(DYED_QUIVER.cyan()),
                            new ItemStack(DYED_QUIVER.purple()),
                            new ItemStack(DYED_QUIVER.blue()),
                            new ItemStack(DYED_QUIVER.brown()),
                            new ItemStack(DYED_QUIVER.green()),
                            new ItemStack(DYED_QUIVER.red()),
                            new ItemStack(DYED_QUIVER.black())
                    ), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT)
                .register(output -> {
                    output.insertAfter(Items.SPECTRAL_ARROW, List.of(
                            new ItemStack(FROZEN_ARROW),
                            new ItemStack(OMINOUS_ARROW)
                    ), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                });
    }
}