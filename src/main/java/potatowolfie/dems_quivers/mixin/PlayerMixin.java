package potatowolfie.dems_quivers.mixin;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import potatowolfie.dems_quivers.client.DemSQuiversComponents;
import potatowolfie.dems_quivers.item.QuiverContents;
import potatowolfie.dems_quivers.item.QuiverItem;

import java.util.ArrayList;
import java.util.List;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Inject(method = "getProjectile", at = @At("HEAD"), cancellable = true)
    private void getQuiverProjectile(ItemStack weaponStack, CallbackInfoReturnable<ItemStack> cir) {
        Player player = (Player) (Object) this;

        List<ItemStack> quivers = getQuiverCandidates(player);

        for (ItemStack quiver : quivers) {
            QuiverContents contents = quiver.get(DemSQuiversComponents.QUIVER_CONTENTS);
            if (contents != null && !contents.isEmpty()) {

                ItemStack arrow = contents.getSelectedItem();
                if (arrow.isEmpty()) {
                    arrow = contents.items().get(0);
                }

                boolean isValidAmmo = false;
                if (weaponStack.getItem() instanceof ProjectileWeaponItem weapon) {
                    isValidAmmo = weapon.getSupportedHeldProjectiles().test(arrow);
                } else {
                    isValidAmmo = QuiverContents.isArrow(arrow);
                }

                if (isValidAmmo) {
                    ItemStack projectileCopy = arrow.copyWithCount(1);
                    cir.setReturnValue(projectileCopy);
                    return;
                }
            }
        }
    }

    private static List<ItemStack> getQuiverCandidates(Player player) {
        List<ItemStack> candidates = new ArrayList<>();

        ItemStack offhand = player.getOffhandItem();
        if (offhand.getItem() instanceof QuiverItem) {
            candidates.add(offhand);
        }

        Inventory inv = player.getInventory();
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.getItem() instanceof QuiverItem && stack != offhand) {
                candidates.add(stack);
            }
        }
        return candidates;
    }
}