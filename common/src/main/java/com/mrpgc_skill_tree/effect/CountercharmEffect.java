package com.mrpgc_skill_tree.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;

import java.util.Comparator;
import java.util.function.Predicate;

public class CountercharmEffect extends StatusEffect {
    private static final double RANGE = 12.0;

    protected CountercharmEffect(StatusEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
    }

    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!(entity instanceof MobEntity mob)) {
            return true;
        }
        var target = mob.getTarget();
        if (target != null && target.isAlive() && target instanceof Monster) {
            return true;
        }
        var box = mob.getBoundingBox().expand(RANGE);
        Predicate<HostileEntity> predicate = other -> other != mob && other.isAlive();
        var newTarget = mob.getWorld().getEntitiesByClass(HostileEntity.class, box, predicate)
                .stream()
                .min(Comparator.comparingDouble(other -> mob.squaredDistanceTo(other)))
                .orElse(null);
        if (newTarget != null) {
            mob.setTarget(newTarget);
        }
        return true;
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}
