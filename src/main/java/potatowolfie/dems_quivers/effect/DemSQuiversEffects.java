package potatowolfie.dems_quivers.effect;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import potatowolfie.dems_quivers.DemSQuivers;

public class DemSQuiversEffects {

    public static final Holder<MobEffect> FREEZING = registerStatusEffect("freezing",
            new FreezingEffect(MobEffectCategory.HARMFUL, 0x6ACCE6));

    public static final Holder<MobEffect> RAGING = registerStatusEffect("raging",
            new RagingEffect(MobEffectCategory.BENEFICIAL, 0xFF0048));

    private static Holder<MobEffect> registerStatusEffect(String name, MobEffect effect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
                Identifier.fromNamespaceAndPath(DemSQuivers.MOD_ID, name), effect);
    }

    public static void registerEffects() {
        DemSQuivers.LOGGER.info("Registering Mod Effects for " + DemSQuivers.MOD_ID);
    }
}