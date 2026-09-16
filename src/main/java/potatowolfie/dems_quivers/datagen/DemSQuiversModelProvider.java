package potatowolfie.dems_quivers.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.ColorCollection;
import potatowolfie.dems_quivers.item.DemSQuiversItems;
import potatowolfie.dems_quivers.item.custom.quiver.QuiverHasArrows;

public class DemSQuiversModelProvider extends FabricModelProvider {
    public DemSQuiversModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(DemSQuiversItems.FROZEN_ARROW, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(DemSQuiversItems.OMINOUS_ARROW, ModelTemplates.FLAT_ITEM);

        generateQuiverModels(itemModelGenerators, DemSQuiversItems.QUIVER);

        ColorCollection.zipApply(
                DemSQuiversItems.DYED_QUIVER,
                ColorCollection.VALUES,
                (item, color) -> generateQuiverModels(itemModelGenerators, item)
        );
    }

    private static void generateQuiverModels(ItemModelGenerators itemModelGenerators, Item quiver) {
        Identifier emptyModel = itemModelGenerators.createFlatItemModel(quiver, ModelTemplates.FLAT_ITEM);
        Identifier filledModel = itemModelGenerators.createFlatItemModel(quiver, "_filled", ModelTemplates.FLAT_ITEM);

        ItemModel.Unbaked emptyUnbaked = ItemModelUtils.plainModel(emptyModel);
        ItemModel.Unbaked filledUnbaked = ItemModelUtils.plainModel(filledModel);

        itemModelGenerators.generateBooleanDispatch(quiver, new QuiverHasArrows(), filledUnbaked, emptyUnbaked);
    }
}