package potatowolfie.dems_quivers.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import potatowolfie.dems_quivers.DemSQuivers;

public class DemSQuiversTags {
    public static final TagKey<Item> QUIVERS = TagKey.create(Registries.ITEM, DemSQuivers.id("quivers"));
}