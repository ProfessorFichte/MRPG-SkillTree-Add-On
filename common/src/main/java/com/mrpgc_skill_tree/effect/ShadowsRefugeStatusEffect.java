package com.mrpgc_skill_tree.effect;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.more_rpg_classes.effect.StealthStatusEffect;
import net.spell_engine.api.spell.fx.ParticleGroup;
import net.spell_engine.api.spell.fx.ParticleGroupBuilder;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;

public class ShadowsRefugeStatusEffect extends StealthStatusEffect {
    protected ShadowsRefugeStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    /// V1: `spell_engine:smoke_medium`, `CIRCLE` from `Origin.FEET` (= `height * 0.1`, not `0`),
    /// count 20, speed `0.18..0.2`, angle 0, tinted teal.
    private static final ParticleGroup POP_PARTICLES = ParticleGroupBuilder
            .of(SpellEngineParticles.smoke_medium)
            .color(Color.from(0x00B0B0).toRGBA())
            .batch(b -> b.shape(ParticleGroup.Shape.CIRCLE)
                    .count(20F).speed(0.18F, 0.2F)
                    .verticalOrigin(ParticleGroupBuilder.Batches.FEET));

    @Override
    public ParticleGroup stealthPopParticles() {
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
