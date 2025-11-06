package com.mrpgc_skill_tree.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import static net.more_rpg_classes.util.CustomMethods.clearNegativeEffects;

public class ImmuneToHarmfulEffectsStatusEffect extends StatusEffect {
    protected ImmuneToHarmfulEffectsStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);

    }
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        clearNegativeEffects(entity,false);
        return true;
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
