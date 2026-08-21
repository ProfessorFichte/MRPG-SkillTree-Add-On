package com.mrpgc_skill_tree.skills;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.effect.MRPGCEffects;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.render.LightEmission;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.fx.Fx;
import net.spell_engine.api.spell.fx.ParticleGroup;
import net.spell_engine.api.spell.fx.ParticleGroupBuilder;
import net.spell_engine.api.spell.fx.Sound;
import net.spell_engine.api.spell.summon.AttributeScaling;
import net.spell_engine.api.util.TriState;
import net.spell_engine.api.spell.tooltip.TooltipTokens;
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

    public static final MrpgSkillSpells.Entry tundra_hunter_tier_2_spell_1_root = add(MrpgSkillsCommon.lingerRoot(
            MrpgSkillSpells.Category.TUNDRA_HUNTER, MrpgSkillSpells.tundraHunterSchool,
            "tundra_hunter_tier_2_spell_1_root", "archers_expansion:frozen_pact", "Frozen Pact", 1F));
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_2_spell_1_modifier_1 = add(tundra_hunter_tier_2_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_2_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_2_spell_1_modifier_1");
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_2_spell_1_modifier_2 = add(tundra_hunter_tier_2_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_2_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_2_spell_1_modifier_2");
        var title = "Polar Instincts";
        var effect = MrpgSkillEffects.HUNTING_INSTINCTS;
        // Two modifiers (frost spell power, ranged damage), both +10%. The status effect's modifier
        // map is unordered, so the attribute is named explicitly rather than read by list position.
        var description = "Frozen Pact increases Frost Power and Ranged Damage by "
                + TooltipTokens.effect(effect.id, 0, SpellSchools.FROST.id)
                + " for {effect_duration} secs.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:frozen_pact";
        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(),6,0);
        impact.action.apply_to_caster = true;

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_2_spell_2_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.TUNDRA_HUNTER, MrpgSkillSpells.tundraHunterSchool,
            "tundra_hunter_tier_2_spell_2_root", "archers_expansion:frozen_shot", "Frozen Shot", 0.1F));
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_2_spell_2_modifier_1 = add(tundra_hunter_tier_2_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_2_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_2_spell_2_modifier_1");
        var title = "Additional Frozen Shots";
        var description = "You gain {stash_amplifier_add} additional Frozen Shots.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:frozen_shot";
        modifier.stash_amplifier_add = 2;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_2_spell_2_modifier_2 = add(tundra_hunter_tier_2_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_2_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_2_spell_2_modifier_2");
        var title = "Frost Stalker";
        var effect = MrpgSkillEffects.HUNTING_FEVER;
        // Two modifiers (movement speed, ranged haste), both +20%. The status effect's modifier map
        // is unordered, so the attribute is named explicitly rather than read by list position.
        var description = "Hitting a Frosted target with Frozen Shot grants "
                + TooltipTokens.effect(effect.id, 0,
                        Identifier.of(EntityAttributes.GENERIC_MOVEMENT_SPEED.getIdAsString()))
                + " increased movement speed for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:frozen_shot";

        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(), 4, 0);
        impact.action.apply_to_caster = true;
        SpellBuilder.configureImpactEnableCondition(impact,
                SpellBuilder.TargetConditions.ofPredicate(MrpgSkillSpells.HAS_FROSTED));
        impact.target_modifiers.get(0).execute = TriState.ALLOW;

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_3_spell_1_root = add(MrpgSkillsCommon.critRoot(
            MrpgSkillSpells.Category.TUNDRA_HUNTER, MrpgSkillSpells.tundraHunterSchool,
            "tundra_hunter_tier_3_spell_1_root", "archers_expansion:arctic_volley", "Arctic Volley", 0.05F));
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_3_spell_1_modifier_1 = add(tundra_hunter_tier_3_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_3_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_3_spell_1_modifier_1");
        var title = "Extra Arctic Shots";
        var description = "Arctic Volley shoots {extra_launch} additional arctic arrows.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:arctic_volley";

        modifier.projectile_launch = Spell.LaunchProperties.EMPTY();
        modifier.projectile_launch.extra_launch_count = 2;
        modifier.projectile_launch.extra_launch_delay = 0;

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_3_spell_1_modifier_2 = add(tundra_hunter_tier_3_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_3_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_3_spell_1_modifier_2");
        var title = "Freezing Arrow Tips";
        var description = "Increases the effect duration of Arctic Volley's frost effects by {effect_duration_add} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:arctic_volley";
        modifier.effect_duration_add = 3;

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_3_spell_2_root = add(MrpgSkillsCommon.radiusRoot(
            MrpgSkillSpells.Category.TUNDRA_HUNTER, MrpgSkillSpells.tundraHunterSchool,
            "tundra_hunter_tier_3_spell_2_root", "archers_expansion:frozen_fusillade", "Frozen Fusillade", 1F));
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_3_spell_2_modifier_1 = add(tundra_hunter_tier_3_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_3_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_3_spell_2_modifier_1");
        var title = "Tundric Trap";
        var description = "Frozen Fusillade has {impact_chance} chance to freeze targets solid.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:frozen_fusillade";
        var impact = SpellBuilder.Impacts.effectSet(MRPGCEffects.FROZEN_SOLID.id.toString(), 2, 0);
        impact.chance = 0.3F;
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_3_spell_2_modifier_2 = add(tundra_hunter_tier_3_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_3_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_3_spell_2_modifier_2");
        var title = "Field Advantage";
        var effect = MrpgSkillEffects.FIELD_ADVANTAGE;
        var description = "Frozen Fusillade grants you Field Advantage, increasing Ranged Haste and reducing incoming damage for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:frozen_fusillade";
        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(), 6, 0);
        impact.action.apply_to_caster = true;
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_4_spell_1_root = add(MrpgSkillsCommon.critRoot(
            MrpgSkillSpells.Category.TUNDRA_HUNTER, MrpgSkillSpells.tundraHunterSchool,
            "tundra_hunter_tier_4_spell_1_root", "archers_expansion:enchanted_crystal_arrow", "Enchanted Crystal Arrow", 0.05F));
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_4_spell_1_modifier_1 = add(tundra_hunter_tier_4_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_4_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_4_spell_1_modifier_1");
        var title = "Shattering Ice Crystals";
        var bonus = 1.5F;
        // A compile-time constant of this mod, not anything the spell data carries, so it is baked
        // into the description (`bakedPercent` doubles the `%`: the lang value goes through
        // `I18n.translate` -> `String.format`).
        var description = "Increases the area of effect of Enchanted Crystal Arrow by "
                + TooltipTokens.bakedPercent(bonus) + ".";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:enchanted_crystal_arrow";
        var extendedRadius = 3.0F + bonus;
        Spell.AreaImpact area_impact = new Spell.AreaImpact();
        area_impact.radius = extendedRadius;
        area_impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.snowflake)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(25).speed(0.8F, 1.5F)
                                .alignment(ParticleGroup.Alignment.LOOK)));
        area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;

        modifier.replacing_area_impact = area_impact;

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_4_spell_1_modifier_2 = add(tundra_hunter_tier_4_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_4_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_4_spell_1_modifier_2");
        var title = "Precise Crystallized Shot";
        var description = "Enchanted Crystal Arrow has {critical_chance_bonus} increased critical strike chance.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:enchanted_crystal_arrow";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.critical_chance_bonus = 0.25F;

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_4_spell_2_root = add(MrpgSkillsCommon.companionRoot(
            MrpgSkillSpells.Category.TUNDRA_HUNTER, MrpgSkillSpells.tundraHunterSchool,
            "tundra_hunter_tier_4_spell_2_root", "archers_expansion:bearward", "Polar Bearward", 10));
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_4_spell_2_modifier_1 = add(tundra_hunter_tier_4_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_4_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_4_spell_2_modifier_1");
        var title = "Savage Ursine";
        var description = "The summoned bear has increased movement and attack speed.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:bearward";

        var movementSpeed = new AttributeScaling.Entry();
        movementSpeed.attribute_id = "minecraft:generic.movement_speed";
        movementSpeed.modifiers = List.of(new AttributeScaling.Entry.OwnerModifier(
                "minecraft:generic.movement_speed", EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE, 0.25, 0.0));
        var attackSpeed = new AttributeScaling.Entry();
        attackSpeed.attribute_id = "minecraft:generic.attack_speed";
        attackSpeed.modifiers = List.of(new AttributeScaling.Entry.OwnerModifier(
                "minecraft:generic.attack_speed", EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE, 0.25, 0.0));
        modifier.summon_attribute_scaling = new AttributeScaling();
        modifier.summon_attribute_scaling.entries = List.of(movementSpeed, attackSpeed);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_4_spell_2_modifier_2 = add(tundra_hunter_tier_4_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_4_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_4_spell_2_modifier_2");
        var title = "Pain-resistant Bear";
        var description = "The summoned bear takes reduced damage and has increased health.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:bearward";

        var damageTaken = new AttributeScaling.Entry();
        damageTaken.attribute_id = "spell_engine:damage_taken";
        damageTaken.modifiers = List.of(new AttributeScaling.Entry.OwnerModifier(
                "spell_engine:damage_taken", EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, -0.25, 0.0));
        var maxHealth = new AttributeScaling.Entry();
        maxHealth.attribute_id = "minecraft:generic.max_health";
        maxHealth.modifiers = List.of(new AttributeScaling.Entry.OwnerModifier(
                "minecraft:generic.max_health", EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE, 0.3, 0.0));
        modifier.summon_attribute_scaling = new AttributeScaling();
        modifier.summon_attribute_scaling.entries = List.of(damageTaken, maxHealth);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
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
        projectile.client_data.travel_particles = List.of(
                ParticleGroupBuilder.of(SpellEngineParticles.snowflake)
                        .batch(b -> b.shape(ParticleGroup.Shape.CIRCLE)
                                .count(3).speed(0, 0)
                                .alignment(ParticleGroup.Alignment.LOOK)));
        projectile.client_data.composite_model = SpellBuilder.ProjectileModels.single("more_rpg_classes:spell_projectile/falling_icicle", 0.75F, LightEmission.NONE);

        meteor.projectile = projectile;
        spell.deliver.meteor = meteor;


        var impact = SpellBuilder.Impacts.damage(0.35F, 0.5F);
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_frost, ParticleGroup.Motion.BURST,
                                Color.from(SpellSchools.FROST.color))
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(25).speed(0.45F, 0.85F)));
        impact.sound = new Sound(SpellEngineSounds.GENERIC_FROST_IMPACT.id());
        spell.impacts = List.of(impact);

        var area_impact = new Spell.AreaImpact();
        area_impact.radius = 2.5F;
        area_impact.area = new Spell.Target.Area();
        area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        area_impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.snowflake)
                        .batch(b -> b.shape(ParticleGroup.Shape.CIRCLE)
                                .count(5).speed(0.1F, 0.2F)
                                .alignment(ParticleGroup.Alignment.LOOK)));
        spell.area_impact = area_impact;

        SpellBuilder.Cost.cooldown(spell, 5F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
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
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.area_circle_1)
                        .attached()
                        .scale(0.8F)
                        // V1 max_age 0.8 (a lifetime multiplier) = playback speed 1 / 0.8
                        .playbackSpeed(1F / 0.8F)
                        .color(Color.FROST)
                        .batch(b -> b.shape(ParticleGroup.Shape.LINE_VERTICAL)
                                .count(1).speed(0.2F, 0.2F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)),
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_heal, ParticleGroup.Motion.DECELERATE,
                                Color.FROST)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(15).speed(0.2F, 0.25F)));
        impact.sound = new Sound(SpellEngineSounds.GENERIC_HEALING_IMPACT_3.id());
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 5F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
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
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.snowflake)
                        .batch(b -> b.shape(ParticleGroup.Shape.CIRCLE)
                                .count(30).speed(0.4F, 0.4F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)));
        impact.sound = new Sound(MrpgSkillSounds.winters_cloak_freeze.id());
        spell.impacts = List.of(impact);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
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
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_heal, ParticleGroup.Motion.DECELERATE,
                                Color.FROST)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(20).speed(0.25F, 0.3F)));
        spell.impacts = List.of(impact);


        spell.passive.triggers = List.of(trigger);


        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
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
        cloud.spawn.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.area_effect_293)
                        .scale(3.5F)
                        .color(Color.FROST)
                        // SPHERE at zero speed = a single motionless particle on the ground,
                        // exactly as in V1; the entry itself already faces GROUND
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(1).speed(0, 0)
                                .anchor(ParticleGroup.Anchor.GROUND)));
        cloud.volume.area.vertical_range_multiplier = 0.3F;
        cloud.spawn.sound = new Sound(MrpgSkillSounds.icy_rebirth_spawn.id());
        cloud.impact_tick_interval = 10;
        cloud.time_to_live_seconds = 5;
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        // Raw id on purpose: MoreParticles.ICE_TRAP lives in a client-only package of an
        // undeclared dependency, and this site overrides no appearance field, so the id
        // resolves through the same registry with the entry's own defaults intact.
        cloud.client_data.particles = List.of(
                ParticleGroupBuilder.of("more_rpg_classes:ice_trap")
                        .batch(b -> b.shape(ParticleGroup.Shape.PILLAR)
                                .count(4).speed(0, 0)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)));
        spell.deliver.clouds = List.of(cloud);

        var impact = SpellBuilder.Impacts.effectAdd(MRPGCEffects.FROSTED.id.toString(), 7, 0,6);
        MrpgSkillSpells.freezeImmuneDeny(impact);
        impact.action.status_effect.refresh_duration = true;
        var damage = SpellBuilder.Impacts.damage(0.4F, 0);
        damage.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.snowflake)
                        // V1 WIDE_PIPE = PIPE at double the radius
                        .batch(b -> b.shape(ParticleGroup.Shape.PIPE).widthFactor(2F)
                                .count(25).speed(0.1F, 0.3F)));
        damage.sound = new Sound(SpellEngineSounds.GENERIC_FROST_IMPACT.id());
        spell.impacts = List.of(impact,damage);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
    public static final MrpgSkillSpells.Entry tundra_hunter_tier_3_passive_2 = add(tundra_hunter_tier_3_passive_2());
    private static MrpgSkillSpells.Entry tundra_hunter_tier_3_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "tundra_hunter_tier_3_passive_2");
        var title = "Hunting Fever";
        var effect = MrpgSkillEffects.HUNTING_FEVER;
        // Two modifiers (movement speed, ranged haste), both +20%. The status effect's modifier map
        // is unordered, so the attribute is named explicitly rather than read by list position.
        var description = "Arrow hits have a {trigger_chance} chance to apply Hunting Fever, increasing your Movement Speed & Ranged Haste by "
                + TooltipTokens.effect(effect.id, 0,
                        Identifier.of(EntityAttributes.GENERIC_MOVEMENT_SPEED.getIdAsString()))
                + " for {effect_duration} sec.";

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

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.TUNDRA_HUNTER));
    }
}
