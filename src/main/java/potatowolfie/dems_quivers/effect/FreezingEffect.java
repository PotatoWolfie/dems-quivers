package potatowolfie.dems_quivers.effect;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class FreezingEffect extends MobEffect {

    private static final int TICKS_FROZEN_PER_TICK = 4;
    private static final int TICKS_FROZEN_CAP = 148;

    private static final int DAMAGE_INTERVAL_TICKS = 40;
    private final Map<UUID, Integer> damageTimers = new HashMap<>();

    protected FreezingEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return true;
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplification) {
        int updated = Math.min(mob.getTicksFrozen() + TICKS_FROZEN_PER_TICK, TICKS_FROZEN_CAP);
        mob.setTicksFrozen(updated);

        if (!mob.canFreeze() && mob.isFullyFrozen()) {
            UUID id = mob.getUUID();
            int timer = damageTimers.getOrDefault(id, 0) + 1;
            if (timer >= DAMAGE_INTERVAL_TICKS) {
                mob.hurtServer(serverLevel, mob.damageSources().freeze(), 1.0F);
                timer = 0;
            }
            damageTimers.put(id, timer);
        } else {
            damageTimers.remove(mob.getUUID());
        }

        return true;
    }

    @Override
    public void onMobRemoved(ServerLevel level, LivingEntity mob, int amplifier, Entity.RemovalReason reason) {
        damageTimers.remove(mob.getUUID());
    }
}