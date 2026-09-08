package potatowolfie.dems_quivers.mixin;

import net.minecraft.world.entity.LivingEntity;
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

import java.util.List;

@Mixin(ProjectileWeaponItem.class)
public abstract class ProjectileWeaponItemMixin {

    @Inject(method = "draw", at = @At("HEAD"))
    private static void onDrawProjectile(ItemStack weapon, ItemStack ammo, LivingEntity shooter, CallbackInfoReturnable<List<ItemStack>> cir) {
        if (shooter instanceof Player player && !player.hasInfiniteMaterials()) {
            deductFromQuiver(player);
        }
    }

    private static void deductFromQuiver(Player player) {
        Inventory inv = player.getInventory();

        ItemStack offhand = player.getOffhandItem();
        if (offhand.getItem() instanceof QuiverItem) {
            if (tryDeduct(offhand)) return;
        }

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.getItem() instanceof QuiverItem && stack != offhand) {
                if (tryDeduct(stack)) return;
            }
        }
    }

    private static boolean tryDeduct(ItemStack quiverStack) {
        QuiverContents contents = quiverStack.get(DemSQuiversComponents.QUIVER_CONTENTS);
        if (contents != null && !contents.isEmpty()) {
            QuiverContents.Mutable mutable = new QuiverContents.Mutable(contents);
            mutable.removeOne();
            quiverStack.set(DemSQuiversComponents.QUIVER_CONTENTS, mutable.toImmutable());
            return true;
        }
        return false;
    }
}