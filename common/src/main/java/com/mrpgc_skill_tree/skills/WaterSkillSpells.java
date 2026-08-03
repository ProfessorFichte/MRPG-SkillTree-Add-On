package com.mrpgc_skill_tree.skills;

import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.more_rpg_classes.effect.MRPGCEffects;
import net.skill_tree_rpgs.skills.SkillSounds;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.entity.SpellEntityPredicates;
import net.spell_engine.api.render.LightEmission;
import net.spell_engine.api.spell.ExternalSpellSchools;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.api.spell.fx.Sound;
import net.spell_engine.api.util.TriState;
import net.spell_engine.client.gui.SpellTooltip;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;
import net.spell_engine.fx.SpellEngineSounds;
import net.spell_engine.internals.target.SpellTarget;
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
    public static final MrpgSkillSpells.Entry water_tier_2_spell_1_modifier_2 = add(water_tier_2_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry water_tier_2_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_2_spell_1_modifier_2");
        var title = "Persistent Bubbles";
        var effect = MrpgSkillEffects.PERSISTENT_BUBBLES;
        var description = "Bubble Beam hits have {trigger_chance} chance to decrease movement speed and attack speed by {bonus} for {effect_duration} seconds.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description()
                    .replace("{bonus}", bonus);
        };
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


        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.WATER));
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
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.SPARK,
                                SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        15, 0.6F, 0.6F)
                        .color(WATER_SPELL_COLOR.toRGBA()),
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.SPARK,
                                SpellEngineParticles.MagicParticles.Motion.ASCEND).id().toString(),
                        ParticleBatch.Shape.PIPE, ParticleBatch.Origin.CENTER,
                        10, 0.2F, 0.4F)
                        .color(WATER_SPELL_COLOR.toRGBA())
        };
        impact.sound = new Sound(SpellEngineSounds.GENERIC_DISPEL_1.id());
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;

        var impactUndead = SpellBuilder.Impacts.damage(0.15F, 0);
        MrpgSkillSpells.undeadAllow(impactUndead);
        impactUndead.target_modifiers = List.of(SpellBuilder.ImpactModifiers.alwaysCritAgainstUndead());
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.SPELL,
                                SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        15, 0.5F, 0.8F)
                        .color(Color.WHITE.toRGBA())
        };


        modifier.impacts = List.of(impact, impactUndead);
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WATER));
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
        cloud.client_data.particles = new ParticleBatch[]{
                new ParticleBatch(
                        "more_rpg_classes:bubble",
                        ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.GROUND,
                        10, 0.05F, 0.1F)
        };
        spell.deliver.clouds = List.of(cloud);

        var impact = SpellBuilder.Impacts.damage(0.2F, 0.3F);
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        "bubble_pop",
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
                        10, 0.1F, 0.2F),
                new ParticleBatch(
                        "more_rpg_classes:bubble",
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        5, 0.1F, 0.2F),
        };
        spell.impacts = List.of(impact);



        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
    public static final MrpgSkillSpells.Entry water_tier_3_spell_2_root = add(MrpgSkillsCommon.channelRoot(
            MrpgSkillSpells.Category.WATER, MrpgSkillSpells.waterWizardSchool,
            "water_tier_3_spell_2_root", "elemental_wizards_rpg:aqua_hydro_beam", "Hydro Beam", 1));
    public static final MrpgSkillSpells.Entry water_tier_3_spell_2_modifier_1 = add(water_tier_3_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry water_tier_3_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_3_spell_2_modifier_1");
        var effect = MrpgSkillEffects.HYDRO_BOOST;
        var title = "Hydro Boost";
        var description = "Hydro Beam increases the movement speed of allies by {bonus} for {effect_duration} seconds.";
        var spell = SpellBuilder.createSpellModifier();
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description()
                    .replace("{bonus}", bonus);
        };
        spell.school = MrpgSkillSpells.waterWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:aqua_hydro_beam";

        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(),10,1);

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.WATER));
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WATER));
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
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.area_circle_1.id().toString(),
                        ParticleBatch.Shape.LINE_VERTICAL, ParticleBatch.Origin.FEET,
                        1, 0.15F, 0.16F)
                        .followEntity(true)
                        .scale(0.8F)
                        .maxAge(0.8F)
                        .color(WATER_SPELL_COLOR.toRGBA()),
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.HEAL ,
                                SpellEngineParticles.MagicParticles.Motion.ASCEND).id().toString(),
                        ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.GROUND,
                        15, 0.02F, 0.15F)
                        .color(WATER_SPELL_COLOR.toRGBA()).extent(1.0F)
        };
        impact.sound = new Sound(SpellEngineSounds.GENERIC_HEALING_IMPACT_4.id());
        spell.impacts = List.of(impact);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WATER));
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
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        "more_rpg_classes:splash",
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET,
                        50, 0.1F, 0.3F).extent(0.5F)
        };
        impact.sound = new Sound(MrpgSkillSounds.second_wave.id());
        spell.impacts = List.of(impact);
        SpellBuilder.Cost.cooldown(spell, 5F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WATER));
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
        cloud.client_data.particles = new ParticleBatch[]{
                (new ParticleBatch("more_rpg_classes:water_mist",
                ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.GROUND, 20.0F, 0.0F, 0.0F))};
        spell.deliver.clouds = List.of(cloud);
        Spell.Impact cleanse = SpellBuilder.Impacts.effectCleanse();
        cleanse.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.area_circle_1.id().toString(),
                        ParticleBatch.Shape.LINE_VERTICAL, ParticleBatch.Origin.FEET,
                        1, 0.15F, 0.16F)
                        .followEntity(true)
                        .scale(0.8F)
                        .maxAge(0.8F)
                        .color(Color.WHITE.toRGBA()),
        };
        cleanse.sound = new Sound(MrpgSkillSounds.soothing_mist_cleanse.id());
        spell.impacts = List.of(cleanse);
        SpellBuilder.Cost.cooldown(spell, 20F);
        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WATER));
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

        spell.release.particles_scaled_with_ranged = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.area_effect_480.texture().id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.GROUND,
                        1, 0.0F, 0.F)
                        .scale(2.5F)
                        .color(WATER_SPELL_COLOR.alpha(0.75F).toRGBA())
        };

        var stashEffect = MrpgSkillEffects.SPLASHDOWN;
        var stashTrigger = SpellBuilder.Triggers.effectTick(stashEffect.id.toString());
        SpellBuilder.Deliver.stash(spell, stashEffect.id.toString(), 0.5F, List.of(stashTrigger));
        spell.deliver.stash_effect.consume = 0;

        var impact = SpellBuilder.Impacts.damage(0.0F, 2.5F);
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        "more_rpg_classes:splash",
                        ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET,
                        20, 0, 0)
        };
        spell.impacts = List.of(impact);
        var areaImpact = new Spell.AreaImpact();
        areaImpact.radius = 2.5F;
        areaImpact.force_indirect = true;
        areaImpact.sound = new Sound(MrpgSkillSounds.splashdown.id());
        spell.area_impact = areaImpact;

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WATER));
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

        impact.particles = new ParticleBatch[]{
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_hourglass.id(), WATER_SPELL_COLOR),
        };
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 45F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
    public static final MrpgSkillSpells.Entry water_tier_3_passive_2 = add(water_tier_3_passive_2());
    private static MrpgSkillSpells.Entry water_tier_3_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "water_tier_3_passive_2");
        var effect = MrpgSkillEffects.TORRENT;
        var title = effect.title;
        var healthThreshold = 0.3F;
        var description = "Falling under {threshold} health increases your water spell power by {bonus2} and spell crit chance & spell haste by {bonus} for {effect_duration} sec.";
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().attributes().get(1);
            var modifier2 = effect.config().attributes().get(0);
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            var bonus2 = SpellTooltip.bonus(modifier2.value, modifier2.operation);
            return args.description()
                    .replace("{bonus}", bonus)
                    .replace("{bonus2}", bonus2)
                    .replace("{threshold}", SpellTooltip.percent(healthThreshold));
        };

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.waterWizardSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        spell.release.sound = new Sound(MrpgSkillSounds.torrent.id());

        var trigger = SpellBuilder.Triggers.becomingLowHP(healthThreshold);
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(), 10, 0);
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.STRIPE,
                                SpellEngineParticles.MagicParticles.Motion.DECELERATE).id().toString(),
                        ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.GROUND,
                        30, 0.2F, 0.8F).extent(2.0F).invert()
                        .color(WATER_SPELL_COLOR.toRGBA())
        };
        impact.sound = new Sound(MrpgSkillSounds.torrent.id());
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 20F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.WATER));
    }
}
