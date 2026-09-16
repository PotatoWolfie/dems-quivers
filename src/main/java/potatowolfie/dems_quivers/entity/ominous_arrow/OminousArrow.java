package potatowolfie.dems_quivers.entity.ominous_arrow;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import potatowolfie.dems_quivers.effect.DemSQuiversEffects;
import potatowolfie.dems_quivers.item.DemSQuiversItems;

public class OminousArrow extends AbstractArrow {

    public OminousArrow(EntityType<? extends OminousArrow> type, Level level) {
        super(type, level);
    }

    public OminousArrow(EntityType<? extends OminousArrow> type, double x, double y, double z,
                        Level level, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(type, x, y, z, level, pickupItemStack, firedFromWeapon);
    }

    public OminousArrow(EntityType<? extends OminousArrow> type, LivingEntity owner, Level level,
                        ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(type, owner, level, pickupItemStack, firedFromWeapon);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(DemSQuiversItems.OMINOUS_ARROW);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity mob) {
        super.doPostHurtEffects(mob);
        mob.addEffect(new MobEffectInstance(DemSQuiversEffects.RAGING, 100, 0));
    }
}