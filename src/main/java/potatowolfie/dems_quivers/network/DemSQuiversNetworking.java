package potatowolfie.dems_quivers.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import potatowolfie.dems_quivers.item.QuiverItem;

public class DemSQuiversNetworking {

    public static void registerCommon() {
        PayloadTypeRegistry.serverboundPlay().register(
                ServerboundSelectQuiverItemPacket.TYPE, ServerboundSelectQuiverItemPacket.STREAM_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(ServerboundSelectQuiverItemPacket.TYPE, (payload, context) -> {
            ServerPlayer player = context.player();
            player.level().getServer().execute(() -> {
                AbstractContainerMenu menu = player.containerMenu;
                if (payload.slotId() >= 0 && payload.slotId() < menu.slots.size()) {
                    Slot slot = menu.getSlot(payload.slotId());
                    ItemStack stack = slot.getItem();
                    if (stack.getItem() instanceof QuiverItem) {
                        QuiverItem.toggleSelectedItem(stack, payload.selectedItemIndex());
                        menu.broadcastChanges();
                    }
                }
            });
        });
    }
}