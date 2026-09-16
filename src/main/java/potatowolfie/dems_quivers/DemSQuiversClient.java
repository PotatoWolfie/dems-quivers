package potatowolfie.dems_quivers;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.object.projectile.ArrowModel;
import net.minecraft.client.renderer.entity.EntityRenderers;
import potatowolfie.dems_quivers.entity.DemSQuiversEntityModelLayers;
import potatowolfie.dems_quivers.entity.DemSQuiversEntityTypes;
import potatowolfie.dems_quivers.entity.frozen_arrow.FrozenArrowRenderer;
import potatowolfie.dems_quivers.entity.ominous_arrow.OminousArrowRenderer;

public class DemSQuiversClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ModelLayerRegistry.registerModelLayer(
                DemSQuiversEntityModelLayers.FROZEN_ARROW,
                ArrowModel::createBodyLayer
        );
        EntityRenderers.register(DemSQuiversEntityTypes.FROZEN_ARROW, FrozenArrowRenderer::new);

        ModelLayerRegistry.registerModelLayer(
                DemSQuiversEntityModelLayers.OMINOUS_ARROW,
                ArrowModel::createBodyLayer
        );
        EntityRenderers.register(DemSQuiversEntityTypes.OMINOUS_ARROW, OminousArrowRenderer::new);
    }
}