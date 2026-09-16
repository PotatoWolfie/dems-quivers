package potatowolfie.dems_quivers.entity;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;
import potatowolfie.dems_quivers.DemSQuivers;

public class DemSQuiversEntityModelLayers {

    public static final ModelLayerLocation FROZEN_ARROW =
            new ModelLayerLocation(Identifier.fromNamespaceAndPath(DemSQuivers.MOD_ID, "frozen_arrow"), "main");

    public static final ModelLayerLocation OMINOUS_ARROW =
            new ModelLayerLocation(Identifier.fromNamespaceAndPath(DemSQuivers.MOD_ID, "ominous_arrow"), "main");

}
