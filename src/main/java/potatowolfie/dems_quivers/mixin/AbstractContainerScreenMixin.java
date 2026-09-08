package potatowolfie.dems_quivers.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import potatowolfie.dems_quivers.item.QuiverMouseActions;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin {

    @Shadow @org.jspecify.annotations.Nullable protected Slot hoveredSlot;

    private static final ThreadLocal<QuiverMouseActions> QUIVER_ACTIONS =
            ThreadLocal.withInitial(() -> new QuiverMouseActions(Minecraft.getInstance()));

    @Inject(method = "mouseScrolled", at = @At("HEAD"), cancellable = true)
    private void handleQuiverScroll(double mouseX, double mouseY, double scrollX, double scrollY, CallbackInfoReturnable<Boolean> cir) {
        if (this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
            QuiverMouseActions actions = QUIVER_ACTIONS.get();
            if (actions.matches(this.hoveredSlot)) {
                boolean handled = actions.onMouseScrolled(scrollX, scrollY, this.hoveredSlot.index, this.hoveredSlot.getItem());
                if (handled) {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}