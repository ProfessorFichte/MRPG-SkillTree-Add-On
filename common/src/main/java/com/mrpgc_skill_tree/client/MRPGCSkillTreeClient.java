package com.mrpgc_skill_tree.client;

import com.mrpgc_skill_tree.client.effect.EyeofTheStormRenderer;
import com.mrpgc_skill_tree.effect.MrpgSkillEffects;
import com.mrpgc_skill_tree.skills.MrpgSkillDefinitions;
import com.mrpgc_skill_tree.skills.MrpgSkillSpells;
import com.mrpgc_skill_tree.utils.MrpgTranslationUtil;
import net.minecraft.util.Identifier;
import net.skill_tree_rpgs.utils.TranslationUtil;
import net.spell_engine.api.effect.CustomModelStatusEffect;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.api.render.BuffParticleSpawner;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.client.gui.SpellTooltip;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;

import static net.skill_tree_rpgs.skills.SkillsCommon.MIGHT_COLOR;

public class MRPGCSkillTreeClient {
    public static void init() {
        for (var spell: MrpgSkillSpells.all) {
            if (spell.mutator() != null) {
                SpellTooltip.addDescriptionMutator(spell.id(), spell.mutator());
            }
        }
        for (var entry: MrpgSkillDefinitions.ENTRIES) {
            var skillId = entry.id();
            if (entry.spellReward() != null) {
                var container = entry.spellReward().get(0);
                var id = Identifier.of(container.spell_ids().getFirst());
                TranslationUtil.resolvers.put(skillId, () -> TranslationUtil.resolveSpellDetails(id));
            }
            else if (entry.attributeReward() != null) {
                var attribute = entry.attributeReward();
                TranslationUtil.resolvers.put(skillId, () -> MrpgTranslationUtil.resolveAttributeModifierTooltip(attribute));
            }
        }
        registerEffectRenderers();
    }
    private static void registerEffectRenderers() {
        final Color EARTH_SPELL_COLOR = new Color(255.0F, 165.0F, 0.0F);
        final Color WATER_SPELL_COLOR = Color.from(0x4a8bff);
        final var speedParticles = new ParticleBatch(
                SpellEngineParticles.MagicParticles.get(
                        SpellEngineParticles.MagicParticles.Shape.STRIPE,
                        SpellEngineParticles.MagicParticles.Motion.FLOAT).id().toString(),
                ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET,
                0.3F, 0.05F, 0.15F)
                .extent(-0.2F);
        final var phaseShiftParticles = new ParticleBatch(
                SpellEngineParticles.aura_effect_668.id().toString(),
                ParticleBatch.Shape.LINE, ParticleBatch.Origin.CENTER,
                1, 0, 0)
                .scale(1.4F)
                .followEntity(true);
        final var barrierParticles = new ParticleBatch(
                SpellEngineParticles.aura_effect_622.id().toString(),
                ParticleBatch.Shape.LINE, ParticleBatch.Origin.CENTER,
                1, 0, 0)
                .scale(1.4F)
                .followEntity(true);
        final var groundSeismicParticles = new ParticleBatch(
                SpellEngineParticles.area_effect_293.id().toString(),
                ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.GROUND,
                1, 0, 0)
                .scale(2.5F)
                .followEntity(true);
        final var verticalCircleParticles = new ParticleBatch(
                SpellEngineParticles.area_circle_1.id().toString(),
                ParticleBatch.Shape.LINE_VERTICAL, ParticleBatch.Origin.FEET,
                1F, 0.05F, 0.05F)
                .color(Color.NATURE.toRGBA())
                .scale(0.75F)
                .followEntity(true);


        /// AIR WIZARD EFFECTS
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.IMPETUS.effect,
                new BuffParticleSpawner(
                        phaseShiftParticles.color(Color.WHITE.toRGBA())
                ).withFrequency(20).scaleWithAmplifier(false)
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.AIR_BUBBLE.effect,
                new BuffParticleSpawner(
                        barrierParticles.copy().color(Color.WHITE.alpha(0.5F).toRGBA())
                ).withFrequency(30).scaleWithAmplifier(false)
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.TAILWIND.effect,
                new BuffParticleSpawner(new ParticleBatch[]{
                        speedParticles.copy()
                                .color(Color.WHITE.toRGBA())
                })
        );
        CustomModelStatusEffect.register(MrpgSkillEffects.EYE_OF_THE_STORM.effect, new EyeofTheStormRenderer());
        /// EARTH WIZARD EFFECTS
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.EARTH_BENDER.effect,
                new BuffParticleSpawner(
                        phaseShiftParticles.color(EARTH_SPELL_COLOR.toRGBA())
                ).withFrequency(20).scaleWithAmplifier(false)
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.STONE_HEART.effect,
                new BuffParticleSpawner(
                        barrierParticles.copy().color(EARTH_SPELL_COLOR.alpha(0.5F).toRGBA())
                ).withFrequency(30).scaleWithAmplifier(false)
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.SEISMIC_ENTRY.effect,
                new BuffParticleSpawner(
                        groundSeismicParticles.copy().color(EARTH_SPELL_COLOR.alpha(0.5F).toRGBA())
                ).withFrequency(10).scaleWithAmplifier(false)
        );
        /// WATER WIZARD EFFECTS
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.CALMING_FLOW.effect,
                new BuffParticleSpawner(
                        phaseShiftParticles.color(WATER_SPELL_COLOR.toRGBA())
                ).withFrequency(20).scaleWithAmplifier(false)
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.PERSISTENT_BUBBLES.effect,
                new BuffParticleSpawner(new ParticleBatch[]{
                        new ParticleBatch(
                                "more_rpg_classes:bubble",
                                ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                                2.0F, 0F, 0.1F)
                })
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.TORRENT.effect,
                new BuffParticleSpawner(new ParticleBatch[]{
                        new ParticleBatch(
                                SpellEngineParticles.MagicParticles.get(
                                        SpellEngineParticles.MagicParticles.Shape.STRIPE,
                                        SpellEngineParticles.MagicParticles.Motion.ASCEND).id().toString(),
                                ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.GROUND,
                                10.0F, 0.05F, 0.5F)
                                .extent(0.5F).color(WATER_SPELL_COLOR.toRGBA())
                })
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.HYDRATION.effect,
                new BuffParticleSpawner(new ParticleBatch[]{
                        new ParticleBatch(
                                SpellEngineParticles.MagicParticles.get(
                                        SpellEngineParticles.MagicParticles.Shape.SPARK,
                                        SpellEngineParticles.MagicParticles.Motion.DECELERATE).id().toString(),
                                ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                                1.0F, 0.05F, 0.1F)
                                .extent(0.5F).color(WATER_SPELL_COLOR.toRGBA())
                })
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.HYDRO_BOOST.effect,
                new BuffParticleSpawner(new ParticleBatch[]{
                        speedParticles.copy()
                                .color(WATER_SPELL_COLOR.toRGBA())
                })
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.TORRENT.effect,
                new BuffParticleSpawner(new ParticleBatch[]{new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.STRIPE,
                                SpellEngineParticles.MagicParticles.Motion.FLOAT).id().toString(),
                        ParticleBatch.Shape.PIPE, ParticleBatch.Origin.CENTER,
                        20F, 0.1F, 0.3F).invert().followEntity(true).extent(0.2F).color(WATER_SPELL_COLOR.toRGBA())
                })
        );
        /// BERSERKER EFFECTS
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.UNDYING_RAGE.effect,
                new BuffParticleSpawner(new ParticleBatch[]{new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.STRIPE,
                                SpellEngineParticles.MagicParticles.Motion.FLOAT).id().toString(),
                        ParticleBatch.Shape.PIPE, ParticleBatch.Origin.CENTER,
                        20F, 0.1F, 0.3F).invert().followEntity(true).extent(0.2F).color(Color.RAGE.toRGBA())
                })
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.RAGNAROK.effect,
                new BuffParticleSpawner(new ParticleBatch[]{ new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.SPARK,
                                SpellEngineParticles.MagicParticles.Motion.FLOAT).id().toString(),
                        ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.CENTER,
                        0.5F, 0F, 0.2F)
                        .color(Color.RAGE.toRGBA()) })
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.BLOODFLOW.effect,
                new BuffParticleSpawner(new ParticleBatch[]{ new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.SPARK,
                                SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        2F, 0.45F, 0.75F)
                        .color(Color.BLOOD.toRGBA()) })
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.BLIND_WITH_RAGE.effect,
                new BuffParticleSpawner(new ParticleBatch[]{ new ParticleBatch(
                        "more_rpg_classes:rage_particle",
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        0.1F, 0.45F, 0.75F)
                        .color(Color.BLOOD.toRGBA()) })
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.BURST_OF_AGGRESSION.effect,
                new BuffParticleSpawner(new ParticleBatch[]{
                        speedParticles.copy()
                                .color(Color.RAGE.toRGBA())
                })
        );
        /// FORCEMASTER EFFECTS
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.SURYS_GRACE.effect,
                new BuffParticleSpawner(new ParticleBatch[]{
                        new ParticleBatch(
                                SpellEngineParticles.area_circle_1.id().toString(),
                                ParticleBatch.Shape.LINE_VERTICAL, ParticleBatch.Origin.FEET,
                                1F, 0.05F, 0.05F)
                                .color(Color.ARCANE.toRGBA())
                                .scale(0.75F)
                                .followEntity(true)
                }).withFrequency(40).invertFrequency());
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.SURYS_TENACITY.effect,
                new BuffParticleSpawner(new ParticleBatch[]{
                        new ParticleBatch(
                                SpellEngineParticles.area_circle_1.id().toString(),
                                ParticleBatch.Shape.LINE_VERTICAL, ParticleBatch.Origin.FEET,
                                1F, 0.05F, 0.05F)
                                .color(MIGHT_COLOR.toRGBA())
                                .scale(0.75F)
                                .followEntity(true)
                }).withFrequency(40).invertFrequency());
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.FLYING_FISTS.effect,
                new BuffParticleSpawner(new ParticleBatch[]{
                        speedParticles.copy()
                                .color(MIGHT_COLOR.toRGBA())
                })
        );
        /// DEADEYE EFFECTS
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.LEAPING_SWIFTNESS.effect,
                new BuffParticleSpawner(new ParticleBatch[]{
                        speedParticles.copy()
                                .color(Color.WHITE.toRGBA())
                })
        );
        /// TUNDRA HUNTER EFFECTS
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.WINTERS_CLOAK.effect,
                new BuffParticleSpawner(new ParticleBatch[]{new ParticleBatch(
                        SpellEngineParticles.snowflake.id().toString(),
                        ParticleBatch.Shape.PIPE, ParticleBatch.Origin.CENTER,
                        5F, 0.1F, 0.15F).invert().followEntity(true).extent(0.2F)
                })
        );
        /// WAR ARCHER EFFECTS
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.LAST_STAND.effect,
                new BuffParticleSpawner(new ParticleBatch[]{ new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.SPARK,
                                SpellEngineParticles.MagicParticles.Motion.FLOAT).id().toString(),
                        ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.CENTER,
                        0.5F, 0F, 0.2F)
                        .color(Color.RED.toRGBA()) })
        );
    }

}