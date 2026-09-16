package potatowolfie.dems_quivers.client;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import potatowolfie.dems_quivers.item.custom.quiver.QuiverContents;

public record QuiverTooltip(QuiverContents contents) implements TooltipComponent {
}