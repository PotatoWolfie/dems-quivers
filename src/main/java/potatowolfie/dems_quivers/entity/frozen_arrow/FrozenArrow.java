package potatowolfie.dems_quivers.entity.frozen_arrow;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import potatowolfie.dems_quivers.effect.DemSQuiversEffects;
import potatowolfie.dems_quivers.item.DemSQuiversItems;

public class FrozenArrow extends AbstractArrow {

    public FrozenArrow(EntityType<? extends FrozenArrow> type, Level level) {
        super(type, level);
    }

    public FrozenArrow(EntityType<? extends FrozenArrow> type, double x, double y, double z,
                       Level level, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(type, x, y, z, level, pickupItemStack, firedFromWeapon);
    }

    public FrozenArrow(EntityType<? extends FrozenArrow> type, LivingEntity owner, Level level,
                       ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(type, owner, level, pickupItemStack, firedFromWeapon);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(DemSQuiversItems.FROZEN_ARROW);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity mob) {
        super.doPostHurtEffects(mob);
        mob.addEffect(new MobEffectInstance(DemSQuiversEffects.FREEZING, 140, 0));
    }
}