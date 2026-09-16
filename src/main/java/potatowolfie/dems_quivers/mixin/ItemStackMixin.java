package potatowolfie.dems_quivers.mixin;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import potatowolfie.dems_quivers.item.custom.quiver.QuiverContents;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "shrink", at = @At("HEAD"))
    private void onShrinkQuiverArrow(int decrement, CallbackInfo ci) {
        ItemStack self = (ItemStack) (Object) this;

        if (QuiverContents.isArrow(self)) {
        }
    }
}