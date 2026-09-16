package potatowolfie.dems_quivers.item.custom.quiver;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import potatowolfie.dems_quivers.client.DemSQuiversComponents;

@Environment(EnvType.CLIENT)
public record QuiverFullness() implements RangeSelectItemModelProperty {
    public static final MapCodec<QuiverFullness> MAP_CODEC = MapCodec.unit(new QuiverFullness());

    public QuiverFullness() {
    }

    public float get(final ItemStack itemStack, final @Nullable ClientLevel level, final @Nullable ItemOwner owner, final int seed) {
        QuiverContents contents = itemStack.getOrDefault(DemSQuiversComponents.QUIVER_CONTENTS, QuiverContents.EMPTY);
        return contents.isEmpty() ? 0.0F : 1.0F;
    }

    public MapCodec<QuiverFullness> type() {
        return MAP_CODEC;
    }
}