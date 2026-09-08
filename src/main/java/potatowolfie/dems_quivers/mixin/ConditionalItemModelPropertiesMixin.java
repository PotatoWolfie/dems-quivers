package potatowolfie.dems_quivers.mixin;

import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import potatowolfie.dems_quivers.DemSQuivers;
import potatowolfie.dems_quivers.item.QuiverHasArrows;

@Mixin(ConditionalItemModelProperties.class)
public class ConditionalItemModelPropertiesMixin {

    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void demsQuivers$registerQuiverHasArrows(CallbackInfo ci) {
        ConditionalItemModelProperties.ID_MAPPER.put(
                Identifier.fromNamespaceAndPath(DemSQuivers.MOD_ID, "quiver/has_arrows"),
                QuiverHasArrows.MAP_CODEC
        );
    }
}