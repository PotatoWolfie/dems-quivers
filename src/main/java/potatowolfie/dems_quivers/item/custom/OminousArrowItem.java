package potatowolfie.dems_quivers.item.custom;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import potatowolfie.dems_quivers.entity.DemSQuiversEntityTypes;
import potatowolfie.dems_quivers.entity.ominous_arrow.OminousArrow;

public class OminousArrowItem extends ArrowItem {

    public OminousArrowItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity owner, @Nullable ItemStack firedFromWeapon) {
        return new OminousArrow(DemSQuiversEntityTypes.OMINOUS_ARROW, owner, level, itemStack.copyWithCount(1), firedFromWeapon);
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        OminousArrow arrow = new OminousArrow(DemSQuiversEntityTypes.OMINOUS_ARROW,
                position.x(), position.y(), position.z(), level, itemStack.copyWithCount(1), null);
        arrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return arrow;
    }
}