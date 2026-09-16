package potatowolfie.dems_quivers.entity;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import potatowolfie.dems_quivers.DemSQuivers;
import potatowolfie.dems_quivers.entity.frozen_arrow.FrozenArrow;
import potatowolfie.dems_quivers.entity.ominous_arrow.OminousArrow;

public class DemSQuiversEntityTypes {

    public static final EntityType<FrozenArrow> FROZEN_ARROW = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(DemSQuivers.MOD_ID, "frozen_arrow"),
            EntityType.Builder.<FrozenArrow>of(FrozenArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(DemSQuivers.MOD_ID, "frozen_arrow"))));

    public static final EntityType<OminousArrow> OMINOUS_ARROW = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(DemSQuivers.MOD_ID, "ominous_arrow"),
            EntityType.Builder.<OminousArrow>of(OminousArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(DemSQuivers.MOD_ID, "ominous_arrow"))));

    public static void registerModEntities() {
        DemSQuivers.LOGGER.info("Registering Mod Entities for " + DemSQuivers.MOD_ID);
    }
}