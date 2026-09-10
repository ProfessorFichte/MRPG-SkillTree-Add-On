package com.mrpgc_skill_tree.effect;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.more_rpg_classes.effect.ControlEnemyStatusEffect;

public class CountercharmEffect extends ControlEnemyStatusEffect {
    protected CountercharmEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public double controlRange() {
        return 12.0;
    }
}
