package com.mrpgc_skill_tree.effect;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.more_rpg_classes.effect.stealth.StealthStatusEffect;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.client.util.Color;

public class ShadowsRefugeStatusEffect extends StealthStatusEffect {
    protected ShadowsRefugeStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    private static final ParticleBatch POP_PARTICLES = new ParticleBatch(
            "spell_engine:smoke_medium",
            ParticleBatch.Shape.CIRCLE,
            ParticleBatch.Origin.FEET,
            null,
            20,
            0.18F,
            0.2F,
            0).color(Color.from(0x00B0B0).toRGBA());

    @Override
    public ParticleBatch stealthPopParticles() {
        return POP_PARTICLES;
    }

    @Override
    public double stealthFollowRange() {
        return 1.0;
    }

    @Override
    public double stealthVisibilityMultiplier() {
        return 0.1F;
    }
}
