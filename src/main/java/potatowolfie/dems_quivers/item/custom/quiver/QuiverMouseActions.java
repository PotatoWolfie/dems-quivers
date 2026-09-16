package potatowolfie.dems_quivers.item.custom.quiver;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ScrollWheelHandler;
import net.minecraft.client.gui.ItemSlotMouseAction;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector2i;
import potatowolfie.dems_quivers.client.DemSQuiversComponents;
import potatowolfie.dems_quivers.item.DemSQuiversTags;
import potatowolfie.dems_quivers.network.ServerboundSelectQuiverItemPacket;

@Environment(EnvType.CLIENT)
public class QuiverMouseActions implements ItemSlotMouseAction {
    private final Minecraft minecraft;
    private final ScrollWheelHandler scrollWheelHandler;

    public QuiverMouseActions(Minecraft minecraft) {
        this.minecraft = minecraft;
        this.scrollWheelHandler = new ScrollWheelHandler();
    }

    public boolean matches(Slot slot) {
        return slot.getItem().is(DemSQuiversTags.QUIVERS);
    }

    public boolean onMouseScrolled(double scrollX, double scrollY, int slotIndex, ItemStack itemStack) {
        int amountOfShownItems = QuiverItem.getNumberOfItemsToShow(itemStack);
        if (amountOfShownItems == 0) {
            return false;
        } else {
            Vector2i wheelXY = this.scrollWheelHandler.onMouseScroll(scrollX, scrollY);
            int wheel = wheelXY.y == 0 ? -wheelXY.x : wheelXY.y;
            if (wheel != 0) {
                int selectedItem = QuiverItem.getSelectedItemIndex(itemStack);
                int updatedSelectedItem = ScrollWheelHandler.getNextScrollWheelSelection((double)wheel, selectedItem, amountOfShownItems);
                if (selectedItem != updatedSelectedItem) {
                    this.toggleSelectedQuiverItem(itemStack, slotIndex, updatedSelectedItem);
                }
            }
            return true;
        }
    }

    public void onStopHovering(Slot hoveredSlot) {
        if (hoveredSlot != null && !hoveredSlot.getItem().isEmpty()) {
            ItemStack stack = hoveredSlot.getItem();
            if (stack.is(DemSQuiversTags.QUIVERS)) {
                QuiverContents contents = stack.get(DemSQuiversComponents.QUIVER_CONTENTS);
                if (contents != null) {
                    QuiverContents.Mutable mutable = new QuiverContents.Mutable(contents);
                    mutable.toggleSelectedItem(QuiverContents.NO_SELECTED_ITEM_INDEX);
                    stack.set(DemSQuiversComponents.QUIVER_CONTENTS, mutable.toImmutable());
                }

                if (this.minecraft.getConnection() != null) {
                    ClientPlayNetworking.send(new ServerboundSelectQuiverItemPacket(hoveredSlot.index, QuiverContents.NO_SELECTED_ITEM_INDEX));
                }
            }
        }
    }

    public void onSlotClicked(Slot slot, ContainerInput containerInput) {
        if (!slot.getItem().isEmpty() && (containerInput == ContainerInput.QUICK_MOVE || containerInput == ContainerInput.SWAP)) {
            this.unselectQuiverItem(slot.getItem(), slot.index);
        }
    }

    private void toggleSelectedQuiverItem(ItemStack quiverItem, int slotIndex, int selectedItem) {
        if (this.minecraft.getConnection() != null && selectedItem < QuiverItem.getNumberOfItemsToShow(quiverItem)) {
            QuiverItem.toggleSelectedItem(quiverItem, selectedItem);
            ClientPlayNetworking.send(new ServerboundSelectQuiverItemPacket(slotIndex, selectedItem));
        }
    }

    public void unselectQuiverItem(ItemStack quiverItem, int slotIndex) {
        this.toggleSelectedQuiverItem(quiverItem, slotIndex, -1);
    }
}