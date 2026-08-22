package com.mrpgc_skill_tree.client;

import com.mrpgc_skill_tree.client.effect.EyeofTheStormRenderer;
import com.mrpgc_skill_tree.effect.MrpgSkillEffects;
import com.mrpgc_skill_tree.skills.MrpgSkillDefinitions;
import com.mrpgc_skill_tree.skills.MrpgSkillSpells;
import com.mrpgc_skill_tree.utils.MrpgTranslationUtil;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.client.particle.MoreParticles;
import net.skill_tree_rpgs.utils.TranslationUtil;
import net.spell_engine.api.effect.CustomModelStatusEffect;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.api.render.BuffParticleSpawner;
import net.spell_engine.api.spell.fx.ParticleGroup;
import net.spell_engine.api.spell.fx.ParticleGroupBuilder;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;

import static net.skill_tree_rpgs.skills.SkillsCommon.MIGHT_COLOR;

public class MRPGCSkillTreeClient {
    public static void init() {
        // Description values that aren't expressible as declarative `{token}`s. `TooltipTokens` is
        // server-safe; it is registered here simply because the tooltip is a client concern.
        MrpgSkillSpells.registerTooltipTokens();

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

    // MARK: - Shared buff particle shapes
    //
    // V1 held these as `ParticleBatch` prototypes and re-coloured `.copy()`s of them per effect.
    // A `ParticleGroup` is mutable the same way, so each site now gets a freshly built one from a
    // factory instead — same spec, no shared instance to alias.
    //
    // BEHAVIOUR CHANGE, deliberate: the V1 `phaseShiftParticles` prototype was re-coloured WITHOUT
    // a `.copy()` at three sites (Impetus, Earth Bender, Calming Flow), and `BuffParticleSpawner`
    // stores the reference it is handed — so all three ended up rendering whichever colour was
    // assigned last (Calming Flow's blue). Each now renders the colour it was written with.
    // `barrierParticles`, `groundSeismicParticles` and `speedParticles` all copied correctly and
    // were never affected.

    /// V1: the `magic_stripe_float` id, `WIDE_PIPE`, `Origin.FEET`, count `0.3`, speed
    /// `0.05..0.15`, extent `-0.2`. 1.10 carries the motion as an appearance payload rather than
    /// baking it into the id, so this has to be built through `ParticleGroupBuilder.magic` —
    /// `magic_stripe_float` is no longer a registered type. `Batches.casting` is exactly
    /// `PIPE` + `widthFactor(2)` + `verticalOrigin(FEET)`, i.e. V1's `WIDE_PIPE` from the feet.
    ///
    /// The fractional count stays a count: `BuffParticleSpawner` emits per tick and its default
    /// `Spacing.RANDOM` turns the leftover fraction into a per-tick roll, which is what V1 did.
    private static ParticleGroup speedParticles(long color) {
        return ParticleGroupBuilder.magic(SpellEngineParticles.magic_stripe, ParticleGroup.Motion.FLOAT)
                .color(color)
                .batch(ParticleGroupBuilder.Batches.casting(0.3F, 0.15F)
                        .andThen(b -> b.speed(0.05F, 0.15F).extent(-0.2F)));
    }

    /// V1: an `aura_effect_<n>` id — the upright twin of the `area_effect_<n>` texture. 1.10
    /// dropped the twins and keeps one entry per texture, orientation chosen at the call site,
    /// so the `aura_*` look is `area_effect_<n>` + `Facing.CAMERA`.
    ///
    /// `LINE` at zero speed is a single billboard placed at the entity's centre, and
    /// `followEntity(true)` is `Attachment.POSITION`.
    private static ParticleGroup auraEffect(SpellEngineParticles.Entry entry, long color) {
        return ParticleGroupBuilder.of(entry)
                .facing(ParticleGroup.Facing.CAMERA)
                .color(color)
                .scale(1.4F)
                .attached()
                .batch(b -> b.shape(ParticleGroup.Shape.LINE).count(1F).speed(0F, 0F));
    }

    /// V1: `area_effect_293`, `SPHERE` at zero speed anchored on the ground, scale `2.5`,
    /// following the entity. `area_*` entries already default to `Facing.GROUND` in 1.10.
    private static ParticleGroup groundSeismicParticles(long color) {
        return ParticleGroupBuilder.of(SpellEngineParticles.area_effect_293)
                .color(color)
                .scale(2.5F)
                .attached()
                .batch(b -> b.shape(ParticleGroup.Shape.SPHERE).count(1F).speed(0F, 0F)
                        .anchor(ParticleGroup.Anchor.GROUND));
    }

    /// V1: `area_circle_1`, `LINE_VERTICAL` from `Origin.FEET`, count `1`, speed `0.05`,
    /// scale `0.75`, following the entity.
    private static ParticleGroup circleParticles(long color) {
        return ParticleGroupBuilder.of(SpellEngineParticles.area_circle_1)
                .color(color)
                .scale(0.75F)
                .attached()
                .batch(b -> b.shape(ParticleGroup.Shape.LINE_VERTICAL).count(1F).speed(0.05F, 0.05F)
                        .verticalOrigin(ParticleGroupBuilder.Batches.FEET));
    }

    private static void registerEffectRenderers() {
        final Color EARTH_SPELL_COLOR = new Color(255.0F, 165.0F, 0.0F);
        final Color WATER_SPELL_COLOR = Color.from(0x4a8bff);

        /// AIR WIZARD EFFECTS
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.IMPETUS.effect,
                new BuffParticleSpawner(
                        auraEffect(SpellEngineParticles.area_effect_668, Color.WHITE.toRGBA())
                ).withFrequency(20).scaleWithAmplifier(false)
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.AIR_BUBBLE.effect,
                new BuffParticleSpawner(
                        auraEffect(SpellEngineParticles.area_effect_622, Color.WHITE.alpha(0.5F).toRGBA())
                ).withFrequency(30).scaleWithAmplifier(false)
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.TAILWIND.effect,
                new BuffParticleSpawner(
                        speedParticles(Color.WHITE.toRGBA())
                )
        );
        CustomModelStatusEffect.register(MrpgSkillEffects.EYE_OF_THE_STORM.effect, new EyeofTheStormRenderer());
        /// EARTH WIZARD EFFECTS
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.EARTH_BENDER.effect,
                new BuffParticleSpawner(
                        auraEffect(SpellEngineParticles.area_effect_668, EARTH_SPELL_COLOR.toRGBA())
                ).withFrequency(20).scaleWithAmplifier(false)
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.STONE_HEART.effect,
                new BuffParticleSpawner(
                        auraEffect(SpellEngineParticles.area_effect_622, EARTH_SPELL_COLOR.alpha(0.5F).toRGBA())
                ).withFrequency(30).scaleWithAmplifier(false)
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.SEISMIC_ENTRY.effect,
                new BuffParticleSpawner(
                        groundSeismicParticles(EARTH_SPELL_COLOR.alpha(0.5F).toRGBA())
                ).withFrequency(10).scaleWithAmplifier(false)
        );
        /// WATER WIZARD EFFECTS
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.CALMING_FLOW.effect,
                new BuffParticleSpawner(
                        auraEffect(SpellEngineParticles.area_effect_668, WATER_SPELL_COLOR.toRGBA())
                ).withFrequency(20).scaleWithAmplifier(false)
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.PERSISTENT_BUBBLES.effect,
                new BuffParticleSpawner(
                        ParticleGroupBuilder.of(MoreParticles.BUBBLE)
                                .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                        .count(2F).speed(0F, 0.1F))
                )
        );
        // NOTE: this TORRENT registration is immediately superseded by the second one further
        // below, which registers the same effect again. Ported as authored — the duplicate is a
        // pre-existing bug, not something to resolve as part of the particle port.
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.TORRENT.effect,
                new BuffParticleSpawner(
                        ParticleGroupBuilder.magic(SpellEngineParticles.magic_stripe, ParticleGroup.Motion.ASCEND)
                                .color(WATER_SPELL_COLOR.toRGBA())
                                .batch(b -> b.shape(ParticleGroup.Shape.PIPE).widthFactor(2F)
                                        .anchor(ParticleGroup.Anchor.GROUND)
                                        .count(10F).speed(0.05F, 0.5F).extent(0.5F))
                )
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.HYDRATION.effect,
                new BuffParticleSpawner(
                        ParticleGroupBuilder.magic(SpellEngineParticles.magic_spark, ParticleGroup.Motion.DECELERATE)
                                .color(WATER_SPELL_COLOR.toRGBA())
                                .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                        .count(1F).speed(0.05F, 0.1F).extent(0.5F))
                )
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.HYDRO_BOOST.effect,
                new BuffParticleSpawner(
                        speedParticles(WATER_SPELL_COLOR.toRGBA())
                )
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.TORRENT.effect,
                new BuffParticleSpawner(
                        ParticleGroupBuilder.magic(SpellEngineParticles.magic_stripe, ParticleGroup.Motion.FLOAT)
                                .color(WATER_SPELL_COLOR.toRGBA())
                                .attached()
                                .batch(b -> b.shape(ParticleGroup.Shape.PIPE)
                                        .count(20F).speed(0.1F, 0.3F).extent(0.2F).invert(true))
                )
        );
        /// BERSERKER EFFECTS
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.UNDYING_RAGE.effect,
                new BuffParticleSpawner(
                        ParticleGroupBuilder.magic(SpellEngineParticles.magic_stripe, ParticleGroup.Motion.FLOAT)
                                .color(Color.RAGE.toRGBA())
                                .attached()
                                .batch(b -> b.shape(ParticleGroup.Shape.PIPE)
                                        .count(20F).speed(0.1F, 0.3F).extent(0.2F).invert(true))
                )
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.RAGNAROK.effect,
                new BuffParticleSpawner(
                        ParticleGroupBuilder.magic(SpellEngineParticles.magic_spark, ParticleGroup.Motion.FLOAT)
                                .color(Color.RAGE.toRGBA())
                                .batch(b -> b.shape(ParticleGroup.Shape.PIPE).widthFactor(2F)
                                        .count(0.5F).speed(0F, 0.2F))
                )
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.BLOODFLOW.effect,
                new BuffParticleSpawner(
                        ParticleGroupBuilder.magic(SpellEngineParticles.magic_spark, ParticleGroup.Motion.BURST)
                                .color(Color.BLOOD.toRGBA())
                                .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                        .count(2F).speed(0.45F, 0.75F))
                )
        );
        // NOTE: in V1 `more_rpg_classes:rage_particle` was drawn by a hand-written `RageParticle`
        // that set its own red tint and its own slow upward velocity in the constructor, ignoring
        // the batch entirely — so neither the colour nor the `0.45..0.75` speed below did anything.
        // In 1.10 that id is a generic `SpellParticle` entry and both now apply. The colour lands
        // close to what V1 hard-coded, but the speed does not: these will be flung outward rather
        // than drifting up. Ported as authored rather than inventing a replacement speed; worth
        // re-tuning by eye.
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.BLIND_WITH_RAGE.effect,
                new BuffParticleSpawner(
                        ParticleGroupBuilder.of(MoreParticles.RAGE_PAR)
                                .color(Color.BLOOD.toRGBA())
                                .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                        .count(0.1F).speed(0.45F, 0.75F))
                )
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.BURST_OF_AGGRESSION.effect,
                new BuffParticleSpawner(
                        speedParticles(Color.RAGE.toRGBA())
                )
        );
        /// FORCEMASTER EFFECTS
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.SURYS_GRACE.effect,
                new BuffParticleSpawner(
                        circleParticles(Color.ARCANE.toRGBA())
                ).withFrequency(40).invertFrequency());
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.SURYS_TENACITY.effect,
                new BuffParticleSpawner(
                        circleParticles(MIGHT_COLOR.toRGBA())
                ).withFrequency(40).invertFrequency());
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.FLYING_FISTS.effect,
                new BuffParticleSpawner(
                        speedParticles(MIGHT_COLOR.toRGBA())
                )
        );
        /// DEADEYE EFFECTS
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.LEAPING_SWIFTNESS.effect,
                new BuffParticleSpawner(
                        speedParticles(Color.WHITE.toRGBA())
                )
        );
        /// TUNDRA HUNTER EFFECTS
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.WINTERS_CLOAK.effect,
                new BuffParticleSpawner(
                        ParticleGroupBuilder.of(SpellEngineParticles.snowflake)
                                .attached()
                                .batch(b -> b.shape(ParticleGroup.Shape.PIPE)
                                        .count(5F).speed(0.1F, 0.15F).extent(0.2F).invert(true))
                )
        );
        /// WAR ARCHER EFFECTS
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.LAST_STAND.effect,
                new BuffParticleSpawner(
                        ParticleGroupBuilder.magic(SpellEngineParticles.magic_spark, ParticleGroup.Motion.FLOAT)
                                .color(Color.RED.toRGBA())
                                .batch(b -> b.shape(ParticleGroup.Shape.PIPE).widthFactor(2F)
                                        .count(0.5F).speed(0F, 0.2F))
                )
        );
    }

}
