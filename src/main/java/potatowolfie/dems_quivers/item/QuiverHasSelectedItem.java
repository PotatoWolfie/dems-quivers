package potatowolfie.dems_quivers.item;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public record QuiverHasSelectedItem() implements ConditionalItemModelProperty {
    public static final MapCodec<QuiverHasSelectedItem> MAP_CODEC = MapCodec.unit(new QuiverHasSelectedItem());

    public boolean get(final ItemStack itemStack, final @Nullable ClientLevel level, final @Nullable LivingEntity owner, final int seed, final ItemDisplayContext displayContext) {
        return !QuiverItem.getSelectedItem(itemStack).isEmpty();
    }

    public MapCodec<QuiverHasSelectedItem> type() {
        return MAP_CODEC;
    }
}