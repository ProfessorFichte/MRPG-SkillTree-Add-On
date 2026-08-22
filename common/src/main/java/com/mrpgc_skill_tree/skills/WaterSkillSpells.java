package com.mrpgc_skill_tree.skills;

import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.client.particle.MoreParticles;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.more_rpg_classes.effect.MRPGCEffects;
import net.skill_tree_rpgs.skills.SkillSounds;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.entity.SpellEntityPredicates;
import net.spell_engine.api.render.LightEmission;
import net.spell_engine.api.spell.ExternalSpellSchools;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.fx.Fx;
import net.spell_engine.api.spell.fx.ParticleGroup;
import net.spell_engine.api.spell.fx.ParticleGroupBuilder;
import net.spell_engine.api.spell.fx.Sound;
import net.spell_engine.api.util.TriState;
import net.spell_engine.api.spell.tooltip.TooltipTokens;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;
import net.spell_engine.fx.SpellEngineSounds;
import net.spell_engine.internals.target.SpellTarget;
import net.spell_power.api.SpellPowerMechanics;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import com.mrpgc_skill_tree.effect.MrpgSkillEffects;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static com.mrpgc_skill_tree.MRPGCSkillTreeAddOn.MOD_ID;
import static net.skill_tree_rpgs.skills.SkillsCommon.*;

public class WaterSkillSpells {
    public static final List<MrpgSkillSpells.Entry> all = new ArrayList<>();

    private static MrpgSkillSpells.Entry add(MrpgSkillSpells.Entry entry) {
        all.add(entry);
        return entry;
    }

    public static final Color WATER_SPELL_COLOR = Color.from(0x4a8bff);
    public static final MrpgSkillSpells.Entry water_tier_2_spell_1_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.WATER, MrpgSkillSpells.waterWizardSchool,
            "water_tier_2_spell_1_root", "elemental_wizards_rpg:aqua_bubble_beam", "Bubble Beam", 0.15F));
    public static final MrpgSkillSpells.Entry water_tier_2_spell_1_modifier_1 = add(water_tier_2_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry water_tier_2_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_2_spell_1_modifier_1");
        var effect = MrpgSkillEffects.BUBBLE_SHIELD;
        var title = "Protecting Bubbles";
        var description = "Bubble Beam grants allies " + effect.title + ", absorbing damage, for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.waterWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:aqua_bubble_beam";

        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(), 6, 0);
        var impactFilter = new Spell.Modifier.ImpactFilter();
        impactFilter.type = Spell.Impact.Action.Type.HEAL;
        modifier.impact_filters = List.of(impactFilter);
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
    public static final MrpgSkillSpells.Entry water_tier_2_spell_1_modifier_2 = add(water_tier_2_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry water_tier_2_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_2_spell_1_modifier_2");
        var title = "Persistent Bubbles";
        var effect = MrpgSkillEffects.PERSISTENT_BUBBLES;
        // Two modifiers (movement speed, attack speed), both -20%. The status effect's modifier map
        // is unordered, so the attribute is named explicitly rather than read by list position. Both
        // are stored negative and the prose says "decrease ... by", hence `ABS` - the old mutator
        // passed the raw value and rendered "-20%".
        var description = "Bubble Beam hits have {trigger_chance} chance to decrease movement speed and attack speed by "
                + TooltipTokens.effect(effect.id, 0,
                        Identifier.of(EntityAttributes.GENERIC_MOVEMENT_SPEED.getIdAsString()),
                        TooltipTokens.Format.ABS)
                + " for {effect_duration} seconds.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.waterWizardSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = SpellBuilder.Triggers.specificSpellHit("elemental_wizards_rpg:aqua_bubble_beam");
        trigger.chance = 0.15F;
        spell.passive.triggers = List.of(trigger);

        var debuff = SpellBuilder.Impacts.effectSet(effect.id.toString(), 5, 0);
        debuff.action.status_effect.refresh_duration = true;
        spell.impacts = List.of(debuff);

        SpellBuilder.Cost.cooldown(spell, 1F);


        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
    public static final MrpgSkillSpells.Entry water_tier_3_spell_1_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.WATER, MrpgSkillSpells.waterWizardSchool,
            "water_tier_3_spell_1_root", "elemental_wizards_rpg:aqua_springwater", "Springwater", 0.15F));
    public static final MrpgSkillSpells.Entry water_tier_3_spell_1_modifier_1 = add(water_tier_3_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry water_tier_3_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_3_spell_1_modifier_1");
        var title = "Holy Water Spring";
        var description = "Springwater removes {effect_amplifier} negative effect from allies and deals extra {damage} damage to undead.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.waterWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:aqua_springwater";

        var impact = SpellBuilder.Impacts.effectCleanse();
        // NOTE (pre-existing, ported as-is): this bundle is overwritten a few lines below by a
        // second assignment to the same `impact`, so in V1 only the white SPELL/BURST burst ever
        // rendered. `impactUndead` was most likely the intended target of that second assignment.
        // Repairing it would change behaviour, so the shadowing is preserved verbatim.
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_spark, ParticleGroup.Motion.BURST,
                                WATER_SPELL_COLOR)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(15).speed(0.6F, 0.6F)),
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_spark, ParticleGroup.Motion.ASCEND,
                                WATER_SPELL_COLOR)
                        .batch(b -> b.shape(ParticleGroup.Shape.PIPE)
                                .count(10).speed(0.2F, 0.4F)));
        impact.sound = new Sound(SpellEngineSounds.GENERIC_DISPEL_1.id());
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;

        var impactUndead = SpellBuilder.Impacts.damage(0.15F, 0);
        MrpgSkillSpells.undeadAllow(impactUndead);
        impactUndead.target_modifiers = List.of(SpellBuilder.ImpactModifiers.alwaysCritAgainstUndead());
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_spell, ParticleGroup.Motion.BURST,
                                Color.WHITE)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(15).speed(0.5F, 0.8F)));


        modifier.impacts = List.of(impact, impactUndead);
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
    public static final MrpgSkillSpells.Entry water_tier_3_spell_1_modifier_2 = add(water_tier_3_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry water_tier_3_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_3_spell_1_modifier_2");
        var title = "Bubbling Spring";
        var description = "Springwater leaves a bubbling area behind, dealing {damage} damage to enemies, for {cloud_duration} sec.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.waterWizardSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.specificSpellHit("elemental_wizards_rpg:aqua_springwater");
        spell.passive.triggers = List.of(trigger);

        spell.deliver.type = Spell.Delivery.Type.CLOUD;
        var cloud = new Spell.Delivery.Cloud();
        cloud.volume.radius = 2.0F;
        cloud.impact_tick_interval = 10;
        cloud.time_to_live_seconds = 3;
        cloud.client_data.particles = List.of(
                ParticleGroupBuilder.of(MoreParticles.BUBBLE)
                        .batch(b -> b.shape(ParticleGroup.Shape.PILLAR)
                                .count(10).speed(0.05F, 0.1F)
                                .anchor(ParticleGroup.Anchor.GROUND)));
        spell.deliver.clouds = List.of(cloud);

        var impact = SpellBuilder.Impacts.damage(0.2F, 0.3F);
        impact.visuals = Fx.Visuals.of(
                // Unnamespaced "bubble_pop" resolved to the vanilla particle, not
                // more_rpg_classes:bubble_pop — spelled out here, behaviour unchanged.
                ParticleGroupBuilder.of("minecraft:bubble_pop")
                        .batch(b -> b.shape(ParticleGroup.Shape.CIRCLE)
                                .count(10).speed(0.1F, 0.2F)),
                ParticleGroupBuilder.of(MoreParticles.BUBBLE)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(5).speed(0.1F, 0.2F)));
        spell.impacts = List.of(impact);



        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
    public static final MrpgSkillSpells.Entry water_tier_3_spell_2_root = add(MrpgSkillsCommon.channelRoot(
            MrpgSkillSpells.Category.WATER, MrpgSkillSpells.waterWizardSchool,
            "water_tier_3_spell_2_root", "elemental_wizards_rpg:aqua_hydro_beam", "Hydro Beam", 1));
    public static final MrpgSkillSpells.Entry water_tier_3_spell_2_modifier_1 = add(water_tier_3_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry water_tier_3_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_3_spell_2_modifier_1");
        var title = "Hydro Boost";
        // Single modifier (movement speed), so the token's blank-attribute fallback is unambiguous.
        // FIXME: the impact below applies this effect at amplifier *1*, so the player actually gets
        // double the value shown. Amplifier 0 is kept here because it reproduces the old mutator's
        // output exactly; whether the number or the amplifier is the mistake is a balance call.
        var description = "Hydro Beam increases the movement speed of allies by "
                + TooltipTokens.effect(effect.id)
                + " for {effect_duration} seconds.";
        var description = "Hydro Beam scalds enemies, setting them ablaze, and stacks Weakness on them up to 3 times.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.waterWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:aqua_hydro_beam";

        var scald = new Spell.Impact();
        scald.action = new Spell.Impact.Action();
        scald.action.type = Spell.Impact.Action.Type.FIRE;
        scald.action.fire = new Spell.Impact.Action.Fire();
        scald.action.fire.duration = 4F;
        scald.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.SPARK,
                                SpellEngineParticles.MagicParticles.Motion.ASCEND).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.1F, 0.2F)
                        .color(Color.RED.toRGBA())
        };
        scald.sound = new Sound(SpellEngineSounds.GENERIC_FIRE_IGNITE.id());

        var weaken = SpellBuilder.Impacts.effectAdd(StatusEffects.WEAKNESS.getIdAsString(), 6, 0, 2);
        weaken.action.status_effect.refresh_duration = true;

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(scald, weaken);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WATER));
        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
    public static final MrpgSkillSpells.Entry water_tier_3_spell_2_modifier_2 = add(water_tier_3_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry water_tier_3_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_3_spell_2_modifier_2");
        var title = "High Water Pressure";
        var description = "Hydro Beam has {trigger_chance} chance to stun the target.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.waterWizardSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.specificSpellHit("elemental_wizards_rpg:aqua_hydro_beam");
        trigger.chance = 0.2F;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.stun(1.5F);
        spell.impacts = List.of(impact);
        SpellBuilder.Cost.cooldown(spell, 2F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
    ///WATER PASSIVES
    public static final MrpgSkillSpells.Entry water_tier_1_passive_1 = add(water_tier_1_passive_1());
    private static MrpgSkillSpells.Entry water_tier_1_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_1_passive_1");
        var effect = MrpgSkillEffects.HYDRATION;
        var title = "Hydration";
        var description = "Healing spells applies Hydration regenerating health for {effect_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.waterWizardSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.activeSpellHeal(1F);
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 4, 0, 3);
        impact.visuals = Fx.Visuals.of(
                // V1 maxAge 0.8 (a lifetime multiplier) is the reciprocal as a playback speed.
                ParticleGroupBuilder.of(SpellEngineParticles.area_circle_1)
                        .color(WATER_SPELL_COLOR)
                        .scale(0.8F)
                        .playbackSpeed(1.25F)
                        .attached()
                        .batch(b -> b.shape(ParticleGroup.Shape.LINE_VERTICAL)
                                .count(1).speed(0.15F, 0.16F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)),
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_heal, ParticleGroup.Motion.ASCEND,
                                WATER_SPELL_COLOR)
                        .batch(b -> b.shape(ParticleGroup.Shape.PIPE).widthFactor(2F)
                                .count(15).speed(0.02F, 0.15F)
                                .anchor(ParticleGroup.Anchor.GROUND)
                                .extent(1.0F)));
        impact.sound = new Sound(SpellEngineSounds.GENERIC_HEALING_IMPACT_4.id());
        spell.impacts = List.of(impact);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
    public static final MrpgSkillSpells.Entry water_tier_1_passive_2 = add(water_tier_1_passive_2());
    private static MrpgSkillSpells.Entry water_tier_1_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_1_passive_2");
        var title = "Second Wave";
        var description = "Water Spells have {trigger_chance} chance to knock the target back.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.waterWizardSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.spellHit(0.3F,"water");
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.damage(0.0F,1.5F);
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(MoreParticles.SPLASH)
                        .batch(b -> b.shape(ParticleGroup.Shape.CIRCLE)
                                .count(50).speed(0.1F, 0.3F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)
                                .extent(0.5F)));
        impact.sound = new Sound(MrpgSkillSounds.second_wave.id());
        spell.impacts = List.of(impact);
        SpellBuilder.Cost.cooldown(spell, 5F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
    public static final MrpgSkillSpells.Entry water_tier_2_passive_1 = add(water_tier_2_passive_1());
    private static MrpgSkillSpells.Entry water_tier_2_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_2_passive_1");
        var title = "Soothing Mist";
        var description = "{trigger_chance} chance upon rolling to leave Soothing Mist behind for {cloud_duration} sec, cleansing negative conditions.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.waterWizardSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        trigger.chance = 0.5F;
        spell.passive.triggers = List.of(trigger);

        spell.deliver.type = Spell.Delivery.Type.CLOUD;
        spell.deliver.delay = 5;
        Spell.Delivery.Cloud cloud = new Spell.Delivery.Cloud();
        cloud.volume.radius = 2.5F;
        cloud.volume.area.vertical_range_multiplier = 0.3F;
        cloud.impact_tick_interval = 20;
        cloud.time_to_live_seconds = 5;
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        cloud.client_data.light_level = 0;
        cloud.client_data.particles = List.of(
                ParticleGroupBuilder.of(MoreParticles.WATER_MIST)
                        .batch(b -> b.shape(ParticleGroup.Shape.PILLAR)
                                .count(20F).speed(0.0F, 0.0F)
                                .anchor(ParticleGroup.Anchor.GROUND)));
        spell.deliver.clouds = List.of(cloud);
        Spell.Impact cleanse = SpellBuilder.Impacts.effectCleanse();
        cleanse.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.area_circle_1)
                        .color(Color.WHITE)
                        .scale(0.8F)
                        .playbackSpeed(1.25F)   // V1 maxAge 0.8
                        .attached()
                        .batch(b -> b.shape(ParticleGroup.Shape.LINE_VERTICAL)
                                .count(1).speed(0.15F, 0.16F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)));
        cleanse.sound = new Sound(MrpgSkillSounds.soothing_mist_cleanse.id());
        spell.impacts = List.of(cleanse);
        SpellBuilder.Cost.cooldown(spell, 20F);
        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
    public static final MrpgSkillSpells.Entry water_tier_2_passive_2 = add(water_tier_2_passive_2());
    private static MrpgSkillSpells.Entry water_tier_2_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_2_passive_2");
        var title = "Splashdown";
        var description = "While rolling, you knockback nearby enemies.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.waterWizardSchool;
        spell.range = 3F;

        var trigger = SpellBuilder.Triggers.roll();
        spell.passive.triggers = List.of(trigger);

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        // Ported from `release.particles_scaled_with_ranged`: range scaling is now declared on the
        // effect itself. The authored `scale(2.5F)` is dropped because V1's SpellHelper overwrote
        // it with the range (`particles.copy().scale(range)`), so it never rendered.
        // WARNING (pre-existing): the id is `area_effect_480.texture().id()` = the TEXTURE id
        // `spell_engine:zone/effect_480`, not the particle id `spell_engine:area_effect_480`.
        // No such particle type is registered, so this batch renders nothing — as in V1. Kept
        // unrepaired because fixing it would make a never-seen effect start appearing.
        spell.release.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.area_effect_480.texture().id())
                        .color(WATER_SPELL_COLOR.alpha(0.75F))
                        .scaleWith(Fx.ScaleWith.RANGE)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(1).speed(0.0F, 0.0F)
                                .anchor(ParticleGroup.Anchor.GROUND)));

        var stashEffect = MrpgSkillEffects.SPLASHDOWN;
        var stashTrigger = SpellBuilder.Triggers.effectTick(stashEffect.id.toString());
        SpellBuilder.Deliver.stash(spell, stashEffect.id.toString(), 0.5F, List.of(stashTrigger));
        spell.deliver.stash_effect.consume = 0;

        var impact = SpellBuilder.Impacts.damage(0.0F, 2.5F);
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(MoreParticles.SPLASH)
                        .batch(b -> b.shape(ParticleGroup.Shape.PILLAR)
                                .count(20).speed(0F, 0F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)));
        spell.impacts = List.of(impact);
        var areaImpact = new Spell.AreaImpact();
        areaImpact.radius = 2.5F;
        areaImpact.force_indirect = true;
        areaImpact.sound = new Sound(MrpgSkillSounds.splashdown.id());
        spell.area_impact = areaImpact;

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
    public static final MrpgSkillSpells.Entry water_tier_3_passive_1 = add(water_tier_3_passive_1());
    private static MrpgSkillSpells.Entry water_tier_3_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_3_passive_1");
        var effect = MrpgSkillEffects.CALMING_FLOW;
        var title = "Calming Flow";
        var description = "Water Spell Hits and heals have {trigger_chance_1} chance to enter a calming flow, reducing active water spell cooldowns while casting spells for a {stash_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.waterWizardSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        spell.release.sound = new Sound(MrpgSkillSounds.calming_flow_release.id());

        var trigger = SpellBuilder.Triggers.activeSpellHit(0.25F, "water");
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        var trigger2 = SpellBuilder.Triggers.activeSpellHeal(0.25F);
        trigger2.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger, trigger2);

        SpellBuilder.Deliver.stash(spell, effect.id.toString(), 6, SpellBuilder.Triggers.activeSpellCast("water"));
        spell.deliver.stash_effect.consume = 0;

        Spell.Impact impact = new Spell.Impact();
        impact.action = new Spell.Impact.Action();
        impact.action.apply_to_caster = true;
        impact.action.type = net.spell_engine.api.spell.Spell.Impact.Action.Type.COOLDOWN;
        impact.action.cooldown = new Spell.Impact.Action.Cooldown();
        impact.action.cooldown.actives = new Spell.Impact.Action.Cooldown.Modify();
        impact.action.cooldown.actives.school = "water";
        impact.action.cooldown.actives.duration_multiplier = 0.8F;

        impact.visuals = Fx.Visuals.of(
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_hourglass.id(), WATER_SPELL_COLOR));
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 45F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
    public static final MrpgSkillSpells.Entry water_tier_3_passive_2 = add(water_tier_3_passive_2());
    private static MrpgSkillSpells.Entry water_tier_3_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_3_passive_2");
        var effect = MrpgSkillEffects.TORRENT;
        var title = effect.title;
        var healthThreshold = 0.3F;
        // Three modifiers (water spell power +30%, spell crit chance +10%, spell haste +10%), read
        // by list position via `attributes().get(0)` / `.get(1)`. The status effect's modifier map is
        // unordered, so each is now named explicitly. The health threshold is a compile-time constant
        // of this mod, so it is baked in (`bakedPercent` doubles the `%`: the lang value goes through
        // `I18n.translate` -> `String.format`).
        var description = "Falling under " + TooltipTokens.bakedPercent(healthThreshold)
                + " health increases your water spell power by "
                + TooltipTokens.effect(effect.id, 0, MoreSpellSchools.WATER.id)
                + " and spell crit chance & spell haste by "
                + TooltipTokens.effect(effect.id, 0, SpellPowerMechanics.CRITICAL_CHANCE.id)
                + " for {effect_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.waterWizardSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        spell.release.sound = new Sound(MrpgSkillSounds.torrent.id());

        var trigger = SpellBuilder.Triggers.becomingLowHP(healthThreshold);
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(), 10, 0);
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_stripe, ParticleGroup.Motion.DECELERATE,
                                WATER_SPELL_COLOR)
                        .batch(b -> b.shape(ParticleGroup.Shape.PIPE).widthFactor(2F)
                                .count(30).speed(0.2F, 0.8F)
                                .anchor(ParticleGroup.Anchor.GROUND)
                                .extent(2.0F).invert(true)));
        impact.sound = new Sound(MrpgSkillSounds.torrent.id());
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 20F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
    public static final MrpgSkillSpells.Entry water_tier_2_spell_2_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.WATER, MrpgSkillSpells.waterWizardSchool,
            "water_tier_2_spell_2_root", "elemental_wizards_rpg:aqua_waterball", "Waterballs", 0.15F));
    public static final MrpgSkillSpells.Entry water_tier_2_spell_2_modifier_1 = add(water_tier_2_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry water_tier_2_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_2_spell_2_modifier_1");
        var title = "Soothing Balls";
        var description = "Increases the healing of Waterballs by {power_multiplier}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.waterWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:aqua_waterball";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.power_multiplier = 0.2F;
        var filter = new Spell.Modifier.ImpactFilter();
        filter.type = Spell.Impact.Action.Type.HEAL;
        modifier.impact_filters = List.of(filter);
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
    public static final MrpgSkillSpells.Entry water_tier_2_spell_2_modifier_2 = add(water_tier_2_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry water_tier_2_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_2_spell_2_modifier_2");
        var title = "Rapid Currents";
        var description = "Reduces the cooldown of Waterballs by {cooldown_duration_deduct} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.waterWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:aqua_waterball";
        modifier.cooldown_duration_deduct = 2F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
    public static final MrpgSkillSpells.Entry water_tier_4_spell_1_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.WATER, MrpgSkillSpells.waterWizardSchool,
            "water_tier_4_spell_1_root", "elemental_wizards_rpg:aqua_healing_rain", "Healing Rain", 0.15F));
    public static final MrpgSkillSpells.Entry water_tier_4_spell_1_modifier_1 = add(water_tier_4_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry water_tier_4_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_4_spell_1_modifier_1");
        var title = "Persistent Downpour";
        var description = "Healing Rain's cloud lasts {spawn_duration_add} sec longer.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.waterWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:aqua_healing_rain";
        modifier.spawn_duration_add = 6F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
    public static final MrpgSkillSpells.Entry water_tier_4_spell_1_modifier_2 = add(water_tier_4_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry water_tier_4_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_4_spell_1_modifier_2");
        var title = "Cleansing Storm";
        var description = "Reduces the cooldown of Healing Rain by {cooldown_duration_deduct} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.waterWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:aqua_healing_rain";
        modifier.cooldown_duration_deduct = 8F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
    public static final MrpgSkillSpells.Entry water_tier_4_spell_2_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.WATER, MrpgSkillSpells.waterWizardSchool,
            "water_tier_4_spell_2_root", "elemental_wizards_rpg:aqua_tidal_wave", "Tidal Wave", 0.15F));
    public static final MrpgSkillSpells.Entry water_tier_4_spell_2_modifier_1 = add(water_tier_4_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry water_tier_4_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_4_spell_2_modifier_1");
        var title = "Endless Tide";
        var description = "Tidal Wave travels {spawn_duration_add} sec longer.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.waterWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:aqua_tidal_wave";
        modifier.spawn_duration_add = 6F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
    public static final MrpgSkillSpells.Entry water_tier_4_spell_2_modifier_2 = add(water_tier_4_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry water_tier_4_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_4_spell_2_modifier_2");
        var title = "Rising Tide";
        var description = "Reduces the cooldown of Tidal Wave by {cooldown_duration_deduct} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.waterWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:aqua_tidal_wave";
        modifier.cooldown_duration_deduct = 8F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
}
