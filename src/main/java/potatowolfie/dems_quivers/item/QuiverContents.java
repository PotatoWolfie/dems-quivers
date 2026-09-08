package potatowolfie.dems_quivers.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class QuiverContents {

    public static final int MAX_CAPACITY = 256;
    public static final int NO_SELECTED_ITEM_INDEX = -1;

    public static final QuiverContents EMPTY = new QuiverContents(List.of());

    public static final Codec<QuiverContents> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ItemStack.CODEC.listOf().fieldOf("items").forGetter(QuiverContents::items),
                    Codec.INT.optionalFieldOf("selected_item", NO_SELECTED_ITEM_INDEX).forGetter(QuiverContents::getSelectedItemIndex)
            ).apply(instance, QuiverContents::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, QuiverContents> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()),
            QuiverContents::items,
            ByteBufCodecs.VAR_INT,
            QuiverContents::getSelectedItemIndex,
            QuiverContents::new
    );

    private final List<ItemStack> items;
    private final int selectedItem;

    private QuiverContents(List<ItemStack> items, int selectedItem) {
        this.items = items;
        this.selectedItem = selectedItem;
    }

    public QuiverContents(List<ItemStack> items) {
        this(items, NO_SELECTED_ITEM_INDEX);
    }

    public List<ItemStack> items() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getTotalCount() {
        return items.stream().mapToInt(ItemStack::getCount).sum();
    }

    public int size() {
        return items.size();
    }

    public int getSelectedItemIndex() {
        return selectedItem;
    }

    public ItemStack getSelectedItem() {
        return selectedItem == NO_SELECTED_ITEM_INDEX ? ItemStack.EMPTY : items.get(selectedItem);
    }

    public int getNumberOfItemsToShow() {
        int numberOfItemStacks = this.size();
        int availableItemsToShow = numberOfItemStacks > 12 ? 11 : 12;
        int itemsOnNonFullRow = numberOfItemStacks % 4;
        int emptySpaceOnNonFullRow = itemsOnNonFullRow == 0 ? 0 : 4 - itemsOnNonFullRow;
        return Math.min(numberOfItemStacks, availableItemsToShow - emptySpaceOnNonFullRow);
    }

    public static boolean isArrow(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        String path = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        return path.toLowerCase(Locale.ROOT).contains("arrow");
    }

    public static class Mutable {
        private final List<ItemStack> items;
        private int selectedItem;

        public Mutable(QuiverContents contents) {
            this.items = new ArrayList<>(contents.items.size());
            for (ItemStack stack : contents.items) {
                this.items.add(stack.copy());
            }
            this.selectedItem = contents.selectedItem;
        }

        public Mutable clear() {
            items.clear();
            selectedItem = NO_SELECTED_ITEM_INDEX;
            return this;
        }

        private int findStackIndex(ItemStack toAdd) {
            for (int i = 0; i < items.size(); i++) {
                if (ItemStack.isSameItemSameComponents(items.get(i), toAdd)) {
                    if (items.get(i).getCount() < items.get(i).getMaxStackSize()) {
                        return i;
                    }
                }
            }
            return -1;
        }

        public int tryInsert(ItemStack toAdd) {
            if (!QuiverContents.isArrow(toAdd)) {
                return 0;
            }

            int currentTotal = items.stream().mapToInt(ItemStack::getCount).sum();
            if (currentTotal >= MAX_CAPACITY) {
                return 0;
            }

            int amountToInsert = Math.min(MAX_CAPACITY - currentTotal, toAdd.getCount());
            if (amountToInsert <= 0) {
                return 0;
            }

            int totalInserted = 0;

            while (totalInserted < amountToInsert) {
                int stackIndex = findStackIndex(toAdd);
                if (stackIndex == -1) {
                    break;
                }

                ItemStack existing = items.get(stackIndex);
                int room = existing.getMaxStackSize() - existing.getCount();
                int amount = Math.min(room, amountToInsert - totalInserted);

                existing.grow(amount);
                totalInserted += amount;

                items.remove(stackIndex);
                items.add(0, existing);
            }

            while (totalInserted < amountToInsert && currentTotal + totalInserted < MAX_CAPACITY) {
                int amount = Math.min(toAdd.getMaxStackSize(), amountToInsert - totalInserted);
                items.add(0, toAdd.copyWithCount(amount));
                totalInserted += amount;
            }

            toAdd.shrink(totalInserted);
            return totalInserted;
        }

        public boolean removeOne() {
            if (items.isEmpty()) {
                return false;
            }

            int targetIndex = indexIsOutsideAllowedBounds(selectedItem) ? 0 : selectedItem;
            ItemStack stack = items.get(targetIndex);

            if (!stack.isEmpty()) {
                stack.shrink(1);

                if (stack.isEmpty()) {
                    items.remove(targetIndex);
                    toggleSelectedItem(NO_SELECTED_ITEM_INDEX);
                }
                return true;
            }

            return false;
        }

        public ItemStack removeStack() {
            if (items.isEmpty()) {
                return ItemStack.EMPTY;
            }

            int removeIndex = indexIsOutsideAllowedBounds(selectedItem) ? 0 : selectedItem;

            ItemStack stack = items.remove(removeIndex);

            toggleSelectedItem(NO_SELECTED_ITEM_INDEX);
            return stack;
        }

        public void toggleSelectedItem(int newSelectedItem) {
            this.selectedItem = this.selectedItem != newSelectedItem && !indexIsOutsideAllowedBounds(newSelectedItem)
                    ? newSelectedItem : NO_SELECTED_ITEM_INDEX;
        }

        private boolean indexIsOutsideAllowedBounds(int index) {
            return index < 0 || index >= items.size();
        }

        public QuiverContents toImmutable() {
            return new QuiverContents(List.copyOf(items), selectedItem);
        }
    }
}