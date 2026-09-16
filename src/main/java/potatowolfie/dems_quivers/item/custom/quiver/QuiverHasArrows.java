package potatowolfie.dems_quivers.item.custom.quiver;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import potatowolfie.dems_quivers.client.DemSQuiversComponents;

@Environment(EnvType.CLIENT)
public record QuiverHasArrows() implements ConditionalItemModelProperty {
    public static final MapCodec<QuiverHasArrows> MAP_CODEC = MapCodec.unit(new QuiverHasArrows());

    public QuiverHasArrows() {
    }

    public boolean get(final ItemStack itemStack, final @Nullable ClientLevel level, final @Nullable LivingEntity owner, final int seed, final ItemDisplayContext displayContext) {
        QuiverContents contents = itemStack.getOrDefault(DemSQuiversComponents.QUIVER_CONTENTS, QuiverContents.EMPTY);
        return !contents.isEmpty();
    }

    public MapCodec<QuiverHasArrows> type() {
        return MAP_CODEC;
    }
}