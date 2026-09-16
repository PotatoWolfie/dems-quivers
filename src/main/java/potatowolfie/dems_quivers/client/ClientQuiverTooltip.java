package potatowolfie.dems_quivers.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import potatowolfie.dems_quivers.item.custom.quiver.QuiverContents;

import java.util.List;
import java.util.Objects;

public class ClientQuiverTooltip implements ClientTooltipComponent {
    private static final Identifier PROGRESSBAR_BORDER_SPRITE = Identifier.withDefaultNamespace("container/bundle/bundle_progressbar_border");
    private static final Identifier PROGRESSBAR_FILL_SPRITE = Identifier.withDefaultNamespace("container/bundle/bundle_progressbar_fill");
    private static final Identifier PROGRESSBAR_FULL_SPRITE = Identifier.withDefaultNamespace("container/bundle/bundle_progressbar_full");
    private static final Identifier SLOT_HIGHLIGHT_BACK_SPRITE = Identifier.withDefaultNamespace("container/bundle/slot_highlight_back");
    private static final Identifier SLOT_HIGHLIGHT_FRONT_SPRITE = Identifier.withDefaultNamespace("container/bundle/slot_highlight_front");
    private static final Identifier SLOT_BACKGROUND_SPRITE = Identifier.withDefaultNamespace("container/bundle/slot_background");
    private static final int SLOT_SIZE = 24;
    private static final int GRID_WIDTH = 96;
    private static final int PROGRESSBAR_HEIGHT = 13;
    private static final int PROGRESSBAR_FILL_MAX = 94;
    private static final Component QUIVER_FULL_TEXT = Component.translatable("item.minecraft.bundle.full");
    private static final Component QUIVER_EMPTY_TEXT = Component.translatable("item.minecraft.bundle.empty");
    private static final Component QUIVER_EMPTY_DESCRIPTION = Component.translatable("item.minecraft.bundle.empty.description");

    private final QuiverContents contents;

    public ClientQuiverTooltip(QuiverContents contents) {
        this.contents = contents;
    }

    public int getHeight(Font font) {
        return contents.isEmpty() ? getEmptyDescriptionHeight(font) + PROGRESSBAR_HEIGHT + 8 : backgroundHeight();
    }

    public int getWidth(Font font) {
        return GRID_WIDTH;
    }

    public boolean showTooltipWithItemInHand() {
        return true;
    }

    private int backgroundHeight() {
        return itemGridHeight() + PROGRESSBAR_HEIGHT + 8;
    }

    private int itemGridHeight() {
        return gridSizeY() * SLOT_SIZE;
    }

    private static int getContentXOffset(int tooltipWidth) {
        return (tooltipWidth - GRID_WIDTH) / 2;
    }

    private int gridSizeY() {
        return Mth.positiveCeilDiv(slotCount(), 4);
    }

    private int slotCount() {
        return Math.min(12, contents.size());
    }

    private static int getEmptyDescriptionHeight(Font font) {
        int lines = font.split(QUIVER_EMPTY_DESCRIPTION, GRID_WIDTH).size();
        Objects.requireNonNull(font);
        return lines * 9;
    }

    public void extractImage(Font font, int x, int y, int w, int h, GuiGraphicsExtractor graphics) {
        if (contents.isEmpty()) {
            int left = x + getContentXOffset(w);
            graphics.textWithWordWrap(font, QUIVER_EMPTY_DESCRIPTION, left, y, GRID_WIDTH, -5592406);
            extractProgressbar(left, y + getEmptyDescriptionHeight(font) + 4, font, graphics, 0);
            return;
        }

        boolean isOverflowing = contents.size() > 12;
        List<ItemStack> shownItems = getShownItems(contents.getNumberOfItemsToShow());
        int xStartPos = x + getContentXOffset(w) + GRID_WIDTH;
        int yStartPos = y + gridSizeY() * SLOT_SIZE;
        int slotNumber = 1;

        for (int rowNumber = 1; rowNumber <= gridSizeY(); ++rowNumber) {
            for (int columnNumber = 1; columnNumber <= 4; ++columnNumber) {
                int drawX = xStartPos - columnNumber * SLOT_SIZE;
                int drawY = yStartPos - rowNumber * SLOT_SIZE;
                if (shouldRenderSurplusText(isOverflowing, columnNumber, rowNumber)) {
                    extractCount(drawX, drawY, getAmountOfHiddenItems(shownItems), font, graphics);
                } else if (shownItems.size() >= slotNumber) {
                    extractSlot(slotNumber, drawX, drawY, shownItems, font, graphics);
                    ++slotNumber;
                }
            }
        }

        extractSelectedItemTooltip(font, graphics, x, y, w);
        extractProgressbar(x + getContentXOffset(w), y + itemGridHeight() + 4, font, graphics, contents.getTotalCount());
    }

    private List<ItemStack> getShownItems(int amountOfItemsToShow) {
        int lastToDisplay = Math.min(contents.size(), amountOfItemsToShow);
        return contents.items().subList(0, lastToDisplay);
    }

    private static boolean shouldRenderSurplusText(boolean isOverflowing, int column, int row) {
        return isOverflowing && column * row == 1;
    }

    private int getAmountOfHiddenItems(List<ItemStack> shownItems) {
        return contents.items().stream().skip(shownItems.size()).mapToInt(ItemStack::getCount).sum();
    }

    private void extractSlot(int slotNumber, int drawX, int drawY, List<ItemStack> shownItems, Font font, GuiGraphicsExtractor graphics) {
        int itemVisualOrderIndex = shownItems.size() - slotNumber;
        boolean hasHighlight = itemVisualOrderIndex == contents.getSelectedItemIndex();
        ItemStack item = shownItems.get(itemVisualOrderIndex);

        graphics.blitSprite(RenderPipelines.GUI_TEXTURED,
                hasHighlight ? SLOT_HIGHLIGHT_BACK_SPRITE : SLOT_BACKGROUND_SPRITE, drawX, drawY, SLOT_SIZE, SLOT_SIZE);
        graphics.item(item, drawX + 4, drawY + 4, slotNumber);
        graphics.itemDecorations(font, item, drawX + 4, drawY + 4);
        if (hasHighlight) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_HIGHLIGHT_FRONT_SPRITE, drawX, drawY, SLOT_SIZE, SLOT_SIZE);
        }
    }

    private static void extractCount(int drawX, int drawY, int hiddenItemCount, Font font, GuiGraphicsExtractor graphics) {
        graphics.centeredText(font, "+" + hiddenItemCount, drawX + 12, drawY + 10, -1);
    }

    private void extractSelectedItemTooltip(Font font, GuiGraphicsExtractor graphics, int x, int y, int w) {
        ItemStack selectedItem = contents.getSelectedItem();
        if (!selectedItem.isEmpty()) {
            Component name = selectedItem.getStyledHoverName();
            int textWidth = font.width(name.getVisualOrderText());
            int center = x + w / 2 - 12;
            ClientTooltipComponent nameTooltip = ClientTooltipComponent.create(name.getVisualOrderText());
            graphics.tooltip(font, List.of(nameTooltip), center - textWidth / 2, y - 15,
                    DefaultTooltipPositioner.INSTANCE, selectedItem.get(DataComponents.TOOLTIP_STYLE));
        }
    }

    private static void extractProgressbar(int x, int y, Font font, GuiGraphicsExtractor graphics, int totalArrows) {
        boolean isFull = totalArrows >= QuiverContents.MAX_CAPACITY;
        int fillWidth = Mth.clamp((totalArrows * PROGRESSBAR_FILL_MAX) / QuiverContents.MAX_CAPACITY, 0, PROGRESSBAR_FILL_MAX);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, isFull ? PROGRESSBAR_FULL_SPRITE : PROGRESSBAR_FILL_SPRITE,
                x + 1, y, fillWidth, PROGRESSBAR_HEIGHT);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, PROGRESSBAR_BORDER_SPRITE, x, y, GRID_WIDTH, PROGRESSBAR_HEIGHT);
        Component text = totalArrows == 0 ? QUIVER_EMPTY_TEXT : (isFull ? QUIVER_FULL_TEXT : null);
        if (text != null) {
            graphics.centeredText(font, text, x + GRID_WIDTH / 2, y + 3, -1);
        }
    }
}