package com.mrpgc_skill_tree.skills;

import net.minecraft.util.Identifier;
import net.more_rpg_classes.effect.MRPGCEffects;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.render.LightEmission;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.api.spell.fx.Sound;
import net.spell_engine.client.gui.SpellTooltip;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;
import net.spell_engine.fx.SpellEngineSounds;
import net.spell_power.api.SpellSchools;
import com.mrpgc_skill_tree.effect.MrpgSkillEffects;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class TundraHunterSkillSpells {
    public static final List<MrpgSkillSpells.Entry> all = new ArrayList<>();

    private static MrpgSkillSpells.Entry add(MrpgSkillSpells.Entry entry) {
        all.add(entry);
        return entry;
    }

    ///TUNDRA HUNTER MODIFIERS
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_1_spell_1_modifier_1 = add(tundra_hunter_tier_1_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_1_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_1_spell_1_modifier_1");
        var title = "Additional Frozen Shots";
        var description = "You gain {stash_amplifier_add} additional Frozen Shots.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:frozen_shot";
        modifier.stash_amplifier_add = 2;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_1_spell_1_modifier_2 = add(tundra_hunter_tier_1_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_1_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_1_spell_1_modifier_2");
        var title = "Frost Stalker";
        var description = "Increases the duration of Frosted by {effect_duration_add} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:frozen_shot";
        modifier.effect_duration_add = 3;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_2_spell_1_modifier_1 = add(tundra_hunter_tier_2_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_2_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_2_spell_1_modifier_1");
        var title = "Extra Arctic Shots";
        var description = "Arctic Volley shoots {extra_launch} additional arctic arrows.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:arctic_volley";

        modifier.projectile_launch = Spell.LaunchProperties.EMPTY();
        modifier.projectile_launch.extra_launch_count = 2;
        modifier.projectile_launch.extra_launch_delay = 0;
        modifier.power_modifier = new Spell.Impact.Modifier();

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_2_spell_1_modifier_2 = add(tundra_hunter_tier_2_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_2_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_2_spell_1_modifier_2");
        var title = "Arctic Blessing";
        var description = "Reduces the cooldown of Arctic Volley by {cooldown_duration_deduct} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:arctic_volley";
        modifier.cooldown_duration_deduct = 2;
        modifier.power_modifier = new Spell.Impact.Modifier();

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_3_spell_1_modifier_1 = add(tundra_hunter_tier_3_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_3_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_3_spell_1_modifier_1");
        var title = "Arctic Pact";
        var description = "Frozen Pact has a {trigger_chance} chance to freeze targets solid.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.tundraHunterSchool;
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        Spell.Trigger trigger = new Spell.Trigger();
        trigger.impact = new Spell.Trigger.ImpactCondition();
        trigger.impact.impact_type = Spell.Impact.Action.Type.STATUS_EFFECT.toString();
        trigger.type = Spell.Trigger.Type.SPELL_IMPACT_SPECIFIC;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.id = "archers_expansion:frozen_pact";
        trigger.chance = 0.2F;
        spell.passive.triggers = List.of(trigger);

        var debuff = SpellBuilder.Impacts.effectSet(MRPGCEffects.FROZEN_SOLID.id.toString(), 2, 0);
        spell.impacts = List.of(debuff);

        SpellBuilder.Cost.cooldown(spell, 0.5F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_3_spell_1_modifier_2 = add(tundra_hunter_tier_3_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_3_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_3_spell_1_modifier_2");
        var title = "Hunting Instincts";
        var description = "Frozen Pact increases Frost Power and Ranged Damage by {bonus} for {effect_duration} secs.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.tundraHunterSchool;
        var effect = MrpgSkillEffects.HUNTING_INSTINCTS;
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description()
                    .replace("{bonus}", bonus);
        };

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:frozen_pact";
        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(),6,0);
        impact.action.apply_to_caster = true;

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_4_spell_1_modifier_1 = add(tundra_hunter_tier_4_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_4_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_4_spell_1_modifier_1");
        var title = "Icicle Crystals";
        var bonus = 1.5F;
        var description = "Increases the area of effect of Enchanted Crystal Arrow by {bonus}.";
        var mutator = new SpellTooltip.DescriptionMutator() {
            @Override
            public String mutate(Args args) {
                return args.description().replace("{bonus}", SpellTooltip.percent(bonus));
            }
        };
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:enchanted_crystal_arrow";
        var extendedRadius = 3.0F + bonus;
        Spell.AreaImpact area_impact = new Spell.AreaImpact();
        area_impact.radius = extendedRadius;
        area_impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.snowflake.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK,
                        25, 0.8F, 1.5F,0)
        };
        area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;

        modifier.replacing_area_impact = area_impact;

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_4_spell_1_modifier_2 = add(tundra_hunter_tier_4_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_4_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_4_spell_1_modifier_2");
        var title = "Deep Crystallized Arrow";
        var description = "Enchanted Crystal Arrow deals {power_multiplier} more damage.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:enchanted_crystal_arrow";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.power_multiplier = 0.3F;

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    ///TUNDRA HUNTER PASSIVES
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_1_passive_1 = add(tundra_hunter_tier_1_passive_1());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_1_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_1_passive_1");
        var title = "Hail";
        var description = "Arrows have a {trigger_chance} chance to launch falling icicles dealing {damage} damage.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.tundraHunterSchool;
        spell.range = 30;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.arrowHit();
        trigger.chance = 0.2F;
        spell.passive.triggers = List.of(trigger);

        spell.deliver.type = Spell.Delivery.Type.METEOR;
        var meteor = new Spell.Delivery.Meteor();
        meteor.launch_height = 10;
        meteor.launch_radius = 1.0F;
        meteor.launch_properties.velocity = 1.5F;
        meteor.launch_properties.extra_launch_count = 4;
        meteor.launch_properties.extra_launch_delay = 2;
        var projectile = new Spell.ProjectileData();
        projectile.divergence = 0;
        projectile.client_data = new Spell.ProjectileData.Client();
        projectile.client_data.light_level = 10;
        projectile.client_data.travel_particles = new ParticleBatch[] {
                new ParticleBatch(
                        SpellEngineParticles.snowflake.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK,
                        3, 0, 0,0)

        };
        projectile.client_data.composite_model = SpellBuilder.ProjectileModels.single("more_rpg_classes:spell_projectile/falling_icicle", 0.75F, LightEmission.NONE);

        meteor.projectile = projectile;
        spell.deliver.meteor = meteor;


        var impact = SpellBuilder.Impacts.damage(0.35F, 0.5F);
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.FROST,
                                SpellEngineParticles.MagicParticles.Motion.BURST
                        ).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        25, 0.45F, 0.85F)
                        .color(Color.from(SpellSchools.FROST.color).toRGBA()),
        };
        impact.sound = new Sound(SpellEngineSounds.GENERIC_FROST_IMPACT.id());
        spell.impacts = List.of(impact);

        var area_impact = new Spell.AreaImpact();
        area_impact.radius = 2.5F;
        area_impact.area = new Spell.Target.Area();
        area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        area_impact.particles = new ParticleBatch[] {
                new ParticleBatch(
                        SpellEngineParticles.snowflake.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK,
                        5, 0.1F, 0.2F,0)

        };
        spell.area_impact = area_impact;

        SpellBuilder.Cost.cooldown(spell, 5F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_1_passive_2 = add(tundra_hunter_tier_1_passive_2());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_1_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_1_passive_2");
        var title = "Frozen Prey";
        var description = "If the target is frosted, you heal yourself for {heal} hearts.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.tundraHunterSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.arrowHit();
        var condition = new Spell.TargetCondition();
        condition.entity_predicate_id = MrpgSkillSpells.HAS_FROSTED.id().toString();
        trigger.target_conditions = List.of(condition);
        spell.passive.triggers = List.of(trigger);


        var impact = SpellBuilder.Impacts.heal(0.05F);
        impact.action.apply_to_caster = true;
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.area_circle_1.id().toString(),
                        ParticleBatch.Shape.LINE_VERTICAL, ParticleBatch.Origin.FEET,
                        1, 0.2F, 0.2F)
                        .followEntity(true)
                        .scale(0.8F)
                        .maxAge(0.8F)
                        .color(Color.FROST.toRGBA()),
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.HEAL,
                                SpellEngineParticles.MagicParticles.Motion.DECELERATE).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        15, 0.2F, 0.25F)
                        .color(Color.FROST.toRGBA()),
        };
        impact.sound = new Sound(SpellEngineSounds.GENERIC_HEALING_IMPACT_3.id());
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 5F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_2_passive_1 = add(tundra_hunter_tier_2_passive_1());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_2_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_2_passive_1");
        var title = "Winter's Cloak";
        var description = "When rolling you have {trigger_chance_1} chance to freeze enemies solid that hit you for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.tundraHunterSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        trigger.chance = 0.35F;
        spell.passive.triggers = List.of(trigger);

        var effect = MrpgSkillEffects.WINTERS_CLOAK;

        var trigger_stash_damage_taken = new Spell.Trigger();
        trigger_stash_damage_taken.type = Spell.Trigger.Type.DAMAGE_TAKEN;
        spell.deliver.type = Spell.Delivery.Type.STASH_EFFECT;
        spell.deliver.stash_effect = new Spell.Delivery.StashEffect();
        spell.deliver.stash_effect.duration = 5;
        spell.deliver.stash_effect.amplifier = 0;
        spell.deliver.stash_effect.amplifier_power_multiplier = 0.2F;
        spell.deliver.stash_effect.id = effect.id.toString();
        spell.deliver.stash_effect.consume = 0;
        spell.deliver.stash_effect.triggers = List.of(trigger_stash_damage_taken);

        var impact = SpellBuilder.Impacts.effectSet(MRPGCEffects.FROZEN_SOLID.id.toString(), 3, 0);
        MrpgSkillSpells.freezeImmuneDeny(impact);
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.snowflake.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET,
                        30, 0.4F, 0.4F)
        };
        impact.sound = new Sound(MrpgSkillSounds.winters_cloak_freeze.id());
        spell.impacts = List.of(impact);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_2_passive_2 = add(tundra_hunter_tier_2_passive_2());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_2_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_2_passive_2");
        var title = "Terrain Mastery";
        var description = "When rolling you have a {trigger_chance} chance to stay immune from harmful effects for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellPassive();
        var effect = MrpgSkillEffects.TERRAIN_MASTERY;
        spell.school = MrpgSkillSpells.tundraHunterSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        trigger.chance = 0.3F;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(),3,0);
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.HEAL,
                                SpellEngineParticles.MagicParticles.Motion.DECELERATE).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        20, 0.25F, 0.3F
                ).color(Color.FROST.toRGBA())
        };
        spell.impacts = List.of(impact);


        spell.passive.triggers = List.of(trigger);


        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_3_passive_1 = add(tundra_hunter_tier_3_passive_1());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_3_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_3_passive_1");
        var title = "Icy Rebirth";
        var description = "When killing targets you have a {trigger_chance_1} chance to spawn icicles on the ground for {cloud_duration}, dealing {damage} damage.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.tundraHunterSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        Spell.TargetCondition deadCondition = SpellBuilder.TargetConditions.dead();
        Spell.Trigger arrowTrigger = SpellBuilder.Triggers.rangedAttackImpact();
        arrowTrigger.chance = 0.35F;
        arrowTrigger.target_conditions = List.of(deadCondition);
        Spell.Trigger skillTrigger = new Spell.Trigger();
        skillTrigger.chance = 0.35F;
        skillTrigger.type = net.spell_engine.api.spell.Spell.Trigger.Type.SPELL_IMPACT_SPECIFIC;
        skillTrigger.spell = new Spell.Trigger.SpellCondition();
        skillTrigger.spell.school = MrpgSkillSpells.tundraHunterSchool.id.toString();
        skillTrigger.target_conditions = List.of(deadCondition);
        spell.passive.triggers = List.of(arrowTrigger,skillTrigger);


        spell.deliver.type = Spell.Delivery.Type.CLOUD;
        var cloud = new Spell.Delivery.Cloud();
        cloud.volume.radius = 4.0F;
        cloud.spawn.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.area_effect_293.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.GROUND,
                        1, 0,0)
                        .scale(3.5F)
                        .color(Color.FROST.toRGBA())
        };
        cloud.volume.area.vertical_range_multiplier = 0.3F;
        cloud.spawn.sound = new Sound(MrpgSkillSounds.icy_rebirth_spawn.id());
        cloud.impact_tick_interval = 10;
        cloud.time_to_live_seconds = 5;
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        cloud.client_data.particles = new ParticleBatch[]{
                new ParticleBatch(
                        "more_rpg_classes:ice_trap",
                        ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET,
                        4, 0, 0)
        };
        spell.deliver.clouds = List.of(cloud);

        var impact = SpellBuilder.Impacts.effectAdd(MRPGCEffects.FROSTED.id.toString(), 7, 0,6);
        MrpgSkillSpells.freezeImmuneDeny(impact);
        impact.action.status_effect.refresh_duration = true;
        var damage = SpellBuilder.Impacts.damage(0.4F, 0);
        damage.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.snowflake.id().toString(),
                        ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.CENTER,
                        25, 0.1F, 0.3F),
        };
        damage.sound = new Sound(SpellEngineSounds.GENERIC_FROST_IMPACT.id());
        spell.impacts = List.of(impact,damage);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_3_passive_2 = add(tundra_hunter_tier_3_passive_2());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_3_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_3_passive_2");
        var title = "Hunting Fever";
        var description = "Arrow hits have a {trigger_chance} to apply Hunting Fever, increasing your Movement Speed & Ranged Haste by {bonus} for {effect_duration} sec.";
        var effect = MrpgSkillEffects.HUNTING_FEVER;
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description()
                    .replace("{bonus}", bonus);
        };

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.tundraHunterSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.arrowHit();
        trigger.chance = 0.2F;
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        var buff = SpellBuilder.Impacts.effectSet(effect.id.toString(), 10, 0);
        buff.sound = new Sound(MrpgSkillSounds.hunting_fever.id());
        spell.impacts = List.of(buff);

        SpellBuilder.Cost.cooldown(spell, 30F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
}
