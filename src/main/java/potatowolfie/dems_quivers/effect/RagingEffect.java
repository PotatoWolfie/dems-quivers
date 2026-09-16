package potatowolfie.dems_quivers.effect;

import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import potatowolfie.dems_quivers.DemSQuivers;

public class RagingEffect extends MobEffect {

    private static final double SPEED_BONUS = 0.5;
    private static final double ATTACK_DAMAGE_BONUS = 1.0;

    protected RagingEffect(MobEffectCategory category, int color) {
        super(category, color);

        this.addAttributeModifier(
                Attributes.MOVEMENT_SPEED,
                Identifier.fromNamespaceAndPath(DemSQuivers.MOD_ID, "rage_speed"),
                SPEED_BONUS,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );

        this.addAttributeModifier(
                Attributes.ATTACK_DAMAGE,
                Identifier.fromNamespaceAndPath(DemSQuivers.MOD_ID, "rage_attack_damage"),
                ATTACK_DAMAGE_BONUS,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }
}