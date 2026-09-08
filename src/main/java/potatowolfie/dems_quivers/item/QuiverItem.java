package potatowolfie.dems_quivers.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import potatowolfie.dems_quivers.client.DemSQuiversComponents;
import potatowolfie.dems_quivers.client.QuiverTooltip;

import java.util.Optional;

public class QuiverItem extends Item {

    public QuiverItem(Item.Properties properties) {
        super(properties);
    }

    public static float getFullnessDisplay(ItemStack itemStack) {
        QuiverContents contents = itemStack.getOrDefault(DemSQuiversComponents.QUIVER_CONTENTS, QuiverContents.EMPTY);
        return (float) contents.getTotalCount() / QuiverContents.MAX_CAPACITY;
    }

    public boolean overrideStackedOnOther(ItemStack self, Slot slot, ClickAction clickAction, Player player) {
        QuiverContents initialContents = self.get(DemSQuiversComponents.QUIVER_CONTENTS);
        if (initialContents == null) {
            return false;
        }

        ItemStack other = slot.getItem();
        QuiverContents.Mutable contents = new QuiverContents.Mutable(initialContents);

        if (clickAction == ClickAction.PRIMARY && !other.isEmpty()) {
            if (contents.tryInsert(other) > 0) {
                playInsertSound(player);
            } else {
                playInsertFailSound(player);
            }
            self.set(DemSQuiversComponents.QUIVER_CONTENTS, contents.toImmutable());
            broadcastChangesOnContainerMenu(player);
            return true;
        } else if (clickAction == ClickAction.SECONDARY && other.isEmpty()) {
            ItemStack removed = contents.removeStack();
            if (!removed.isEmpty()) {
                ItemStack remainder = slot.safeInsert(removed);
                if (!remainder.isEmpty()) {
                    contents.tryInsert(remainder);
                } else {
                    playRemoveOneSound(player);
                }
            }
            self.set(DemSQuiversComponents.QUIVER_CONTENTS, contents.toImmutable());
            broadcastChangesOnContainerMenu(player);
            return true;
        }

        return false;
    }

    public boolean overrideOtherStackedOnMe(ItemStack self, ItemStack other, Slot slot, ClickAction clickAction,
                                            Player player, SlotAccess carriedItem) {
        QuiverContents initialContents = self.get(DemSQuiversComponents.QUIVER_CONTENTS);
        if (initialContents == null) {
            return false;
        }

        QuiverContents.Mutable contents = new QuiverContents.Mutable(initialContents);

        if (clickAction == ClickAction.PRIMARY && !other.isEmpty()) {
            if (slot.allowModification(player) && contents.tryInsert(other) > 0) {
                playInsertSound(player);
            } else {
                playInsertFailSound(player);
            }
            self.set(DemSQuiversComponents.QUIVER_CONTENTS, contents.toImmutable());
            broadcastChangesOnContainerMenu(player);
            return true;
        } else if (clickAction == ClickAction.SECONDARY && other.isEmpty()) {
            if (slot.allowModification(player)) {
                ItemStack removed = contents.removeStack();
                if (!removed.isEmpty()) {
                    playRemoveOneSound(player);
                    carriedItem.set(removed);
                }
            }
            self.set(DemSQuiversComponents.QUIVER_CONTENTS, contents.toImmutable());
            broadcastChangesOnContainerMenu(player);
            return true;
        }

        return false;
    }

    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack self = player.getItemInHand(hand);
        QuiverContents initialContents = self.get(DemSQuiversComponents.QUIVER_CONTENTS);
        if (initialContents == null || initialContents.isEmpty()) {
            return InteractionResult.PASS;
        }

        QuiverContents.Mutable contents = new QuiverContents.Mutable(initialContents);
        ItemStack removed = contents.removeStack();
        if (removed.isEmpty()) {
            return InteractionResult.PASS;
        }

        playRemoveOneSound(player);
        self.set(DemSQuiversComponents.QUIVER_CONTENTS, contents.toImmutable());
        if (!player.getInventory().add(removed)) {
            player.drop(removed, false);
        }
        return InteractionResult.SUCCESS;
    }

    public boolean isBarVisible(ItemStack stack) {
        return !stack.getOrDefault(DemSQuiversComponents.QUIVER_CONTENTS, QuiverContents.EMPTY).isEmpty();
    }

    public int getBarWidth(ItemStack stack) {
        int filled = stack.getOrDefault(DemSQuiversComponents.QUIVER_CONTENTS, QuiverContents.EMPTY).getTotalCount();
        return Math.min(1 + (filled * 12) / QuiverContents.MAX_CAPACITY, 13);
    }

    public int getBarColor(ItemStack stack) {
        int filled = stack.getOrDefault(DemSQuiversComponents.QUIVER_CONTENTS, QuiverContents.EMPTY).getTotalCount();
        return filled >= QuiverContents.MAX_CAPACITY ? 0xFFE0A951 : 0xFF55A5FF;
    }

    private static void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private static void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private static void playInsertFailSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT_FAIL, 1.0F, 1.0F);
    }

    private void broadcastChangesOnContainerMenu(Player player) {
        AbstractContainerMenu containerMenu = player.containerMenu;
        if (containerMenu != null) {
            containerMenu.slotsChanged(player.getInventory());
        }
    }

    public static void toggleSelectedItem(ItemStack stack, int selectedItem) {
        QuiverContents initialContents = stack.get(DemSQuiversComponents.QUIVER_CONTENTS);
        if (initialContents != null) {
            QuiverContents.Mutable contents = new QuiverContents.Mutable(initialContents);
            contents.toggleSelectedItem(selectedItem);
            stack.set(DemSQuiversComponents.QUIVER_CONTENTS, contents.toImmutable());
        }
    }

    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        TooltipDisplay display = stack.getOrDefault(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT);
        return !display.shows(DemSQuiversComponents.QUIVER_CONTENTS) ? Optional.empty()
                : Optional.ofNullable(stack.get(DemSQuiversComponents.QUIVER_CONTENTS)).map(QuiverTooltip::new);
    }

    public static int getSelectedItemIndex(ItemStack stack) {
        return stack.getOrDefault(DemSQuiversComponents.QUIVER_CONTENTS, QuiverContents.EMPTY).getSelectedItemIndex();
    }

    public static ItemStack getSelectedItem(ItemStack stack) {
        return stack.getOrDefault(DemSQuiversComponents.QUIVER_CONTENTS, QuiverContents.EMPTY).getSelectedItem();
    }

    public static int getNumberOfItemsToShow(ItemStack stack) {
        return stack.getOrDefault(DemSQuiversComponents.QUIVER_CONTENTS, QuiverContents.EMPTY).getNumberOfItemsToShow();
    }
}