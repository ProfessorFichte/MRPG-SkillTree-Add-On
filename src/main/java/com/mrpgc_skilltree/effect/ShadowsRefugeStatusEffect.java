package com.mrpgc_skilltree.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.ParticleHelper;

public class ShadowsRefugeStatusEffect extends StatusEffect {
    protected ShadowsRefugeStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    public static final ParticleBatch POP_PARTICLES = new ParticleBatch(
            "spell_engine:smoke_medium",
            ParticleBatch.Shape.CIRCLE,
            ParticleBatch.Origin.FEET,
            null,
            20,
            0.18F,
            0.2F,
            0).color(Color.from(0x00B0B0).toRGBA());


    public static void onRemove(LivingEntity entity) {
        if (!entity.getWorld().isClient()) {
            ParticleHelper.sendBatches(entity, new ParticleBatch[]{POP_PARTICLES});
        }
    }
}
