package com.mrpgc_skill_tree.skills;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.client.particle.MoreParticles;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.render.LightEmission;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.fx.Easing;
import net.spell_engine.api.spell.fx.Fx;
import net.spell_engine.api.spell.fx.ModelEffectBuilder;
import net.spell_engine.api.spell.fx.ParticleGroup;
import net.spell_engine.api.spell.fx.ParticleGroupBuilder;
import net.spell_engine.api.spell.fx.Sound;
import net.spell_engine.api.spell.tooltip.TooltipTokens;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;
import com.mrpgc_skill_tree.effect.MrpgSkillEffects;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static net.skill_tree_rpgs.skills.SkillsCommon.*;

public class AirSkillSpells {
    public static final List<MrpgSkillSpells.Entry> all = new ArrayList<>();

    private static MrpgSkillSpells.Entry add(MrpgSkillSpells.Entry entry) {
        all.add(entry);
        return entry;
    }

    public static final MrpgSkillSpells.Entry air_tier_2_spell_1_root = add(MrpgSkillsCommon.critRoot(
            MrpgSkillSpells.Category.AIR, MrpgSkillSpells.airWizardSchool,
            "air_tier_2_spell_1_root", "elemental_wizards_rpg:wind_aeroblast", "Aeroblast", 0.05F));
    public static final MrpgSkillSpells.Entry air_tier_2_spell_1_modifier_1 = add(air_tier_2_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry air_tier_2_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_2_spell_1_modifier_1");
        var title = "Slow Fall";
        var description = "Aeroblast has a {trigger_chance} to apply slow falling for {effect_duration} sec.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.airWizardSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.specificSpellHit("elemental_wizards_rpg:wind_aeroblast");
        trigger.chance = 0.3F;
        spell.passive.triggers = List.of(trigger);

        var debuff = SpellBuilder.Impacts.effectSet(StatusEffects.SLOW_FALLING.getIdAsString(), 2, 0);
        debuff.action.status_effect.amplifier_power_multiplier = 0.2F;
        debuff.action.status_effect.refresh_duration = true;
        spell.impacts = List.of(debuff);

        SpellBuilder.Cost.cooldown(spell, 0.5F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_2_spell_1_modifier_2 = add(air_tier_2_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry air_tier_2_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_2_spell_1_modifier_2");
        var title = "Aeroblast Field";
        var description = "Aeroblast now deals {damage} damage around the target.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.airWizardSchool;

        var radius = 2.0F;

        var impact = SpellBuilder.Impacts.damage(0.4F, 0);
        impact.action.allow_on_center_target = false;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_aeroblast";
        var area_impact = new Spell.AreaImpact();
        area_impact.execute_action_type = Spell.Impact.Action.Type.DAMAGE;
        area_impact.radius = radius;
        area_impact.area = new Spell.Target.Area();
        area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        area_impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(MoreParticles.SMALL_GUST)
                        .batch(b -> b.shape(ParticleGroup.Shape.PIPE).widthFactor(2F)
                                .count(25).speed(0.2F, 1.0F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)
                                .extent(2F)));


        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);
        modifier.replacing_area_impact = area_impact;

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_3_spell_1_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.AIR, MrpgSkillSpells.airWizardSchool,
            "air_tier_3_spell_1_root", "elemental_wizards_rpg:wind_updraft", "Updraft", 0.1F));
    public static final MrpgSkillSpells.Entry air_tier_3_spell_1_modifier_1 = add(air_tier_3_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry air_tier_3_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_3_spell_1_modifier_1");
        var title = "Aerial Swiftness";
        var description = "Reduces the cooldown of Updraft by {cooldown_duration_deduct} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.airWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_updraft";
        modifier.cooldown_duration_deduct = 4;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_3_spell_1_modifier_2 = add(air_tier_3_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry air_tier_3_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_3_spell_1_modifier_2");
        var title = "Aerial Precision";
        var description = "Updraft deals {critical_damage_bonus} critical damage bonus.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.airWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_updraft";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.critical_damage_bonus = 0.25F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_4_spell_1_root = add(MrpgSkillsCommon.companionRoot(
            MrpgSkillSpells.Category.AIR, MrpgSkillSpells.airWizardSchool,
            "air_tier_4_spell_1_root", "elemental_wizards_rpg:wind_tornado", "Tornado", 5));
    public static final MrpgSkillSpells.Entry air_tier_4_spell_1_modifier_1 = add(air_tier_4_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry air_tier_4_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_4_spell_1_modifier_1");
        var title = "Wind Shear";
        var description = "Increases the damage of Tornado by {power_multiplier} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.airWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_tornado";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.power_multiplier = 0.2F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_4_spell_1_modifier_2 = add(air_tier_4_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry air_tier_4_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_4_spell_1_modifier_2");
        var title = "Negative Pressure";
        var description = "The Tornados Impact Range grows {cloud_radius_add} blocks. ";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.airWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_tornado";
        modifier.cloud.radius_add = 6F;

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    ///AIR PASSIVES
    public static final MrpgSkillSpells.Entry air_tier_1_passive_1 = add(air_tier_1_passive_1());
    private static MrpgSkillSpells.Entry air_tier_1_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_1_passive_1");
        var effect = MrpgSkillEffects.IMPETUS;
        var title = effect.title;
        var description = "Air spell impacts have {trigger_chance} chance to increase the casters Spell Haste by "
                + TooltipTokens.effect(effect.id)
                + " for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.airWizardSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.activeSpellHit(0.05F, "air");
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(), 5F, 0);
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_stripe, ParticleGroup.Motion.FLOAT)
                        .batch(b -> b.shape(ParticleGroup.Shape.PIPE).widthFactor(2F)
                                .count(20).speed(0.05F, 0.1F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)));
        impact.sound = new Sound(MrpgSkillSounds.air_impetus_buff.id());
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 1F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_1_passive_2 = add(air_tier_1_passive_2());
    private static MrpgSkillSpells.Entry air_tier_1_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_1_passive_2");
        var effect = MrpgSkillEffects.EYE_OF_THE_STORM;
        var title = "Eye of the Storm";
        float healthThreshold = 0.3F;
        // The threshold is a compile-time constant of this mod, not anything the spell data carries,
        // so it is baked into the description instead of resolved at render time. `bakedPercent`
        // doubles the `%`: the lang value goes through `I18n.translate` -> `String.format`.
        var description = "Air spell impacts on targets below " + TooltipTokens.bakedPercent(healthThreshold)
                + " makes the target more vulnerable to spell crits for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.airWizardSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.spellHit(0.5F,"air");
        trigger.target_conditions = List.of(SpellBuilder.TargetConditions.lowHP(healthThreshold));
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(), 5F, 0);
        impact.sound = new Sound(MrpgSkillSounds.air_eye_of_the_storm.id());
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 1F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_2_passive_1 = add(air_tier_2_passive_1());
    private static MrpgSkillSpells.Entry air_tier_2_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_2_passive_1");
        var effect = MrpgSkillEffects.TAILWIND;
        var title = "Tailwind";
        var description = "{trigger_chance} chance upon rolling to leave tailwind behind for {cloud_duration} sec, increasing movement speed by "
                + TooltipTokens.effect(effect.id)
                + " for {effect_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.airWizardSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        trigger.chance = 0.5F;
        spell.passive.triggers = List.of(trigger);

        spell.deliver.type = Spell.Delivery.Type.CLOUD;
        spell.deliver.delay = 0;
        Spell.Delivery.Cloud cloud = new Spell.Delivery.Cloud();
        cloud.volume.radius = 3.5F;
        cloud.volume.area.vertical_range_multiplier = 1.5F;
        cloud.volume.sound = new Sound(MrpgSkillSounds.air_tailwind_loop.id());
        cloud.impact_tick_interval = 20;
        cloud.time_to_live_seconds = 5;
        cloud.spawn.sound = new Sound(MrpgSkillSounds.air_tailwind_release.id());
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        cloud.client_data.light_level = 0;
        cloud.client_data.particles = List.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_stripe, ParticleGroup.Motion.FLOAT)
                        .batch(b -> b.shape(ParticleGroup.Shape.PILLAR)
                                .count(20).speed(0.3F, 0.6F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)
                                .extent(3.5F)),
                ParticleGroupBuilder.of(MoreParticles.SMALL_GUST)
                        .batch(b -> b.shape(ParticleGroup.Shape.PILLAR)
                                .count(15).speed(0.1F, 0.3F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)
                                .extent(3.5F)));
        spell.deliver.clouds = List.of(cloud);
        Spell.Impact buff = SpellBuilder.Impacts.effectSet(effect.id.toString(),6,0);
        buff.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_stripe, ParticleGroup.Motion.FLOAT)
                        .batch(b -> b.shape(ParticleGroup.Shape.PIPE).widthFactor(2F)
                                .count(5).speed(1.0F, 2.0F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)
                                .extent(3.5F)));
        spell.impacts = List.of(buff);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_2_passive_2 = add(air_tier_2_passive_2());
    private static MrpgSkillSpells.Entry air_tier_2_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_2_passive_2");
        var title = "Strongwind";
        var description = "{trigger_chance} chance upon rolling to summon a twister that chases and damages nearby enemies.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.airWizardSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        trigger.chance = 0.5F;
        spell.passive.triggers = List.of(trigger);

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var stashEffect = MrpgSkillEffects.STRONGWIND;
        var stashTrigger = SpellBuilder.Triggers.effectTick(stashEffect.id.toString());
        SpellBuilder.Deliver.stash(spell, stashEffect.id.toString(), 0.6F, List.of(stashTrigger));
        spell.deliver.stash_effect.consume = 0;

        var spawn = new Spell.Impact();
        spawn.action = new Spell.Impact.Action();
        spawn.action.type = Spell.Impact.Action.Type.SPAWN;
        spawn.action.apply_to_caster = true;
        var twister = new Spell.Impact.Action.Spawn();
        twister.entity_type_id = "elemental_wizards_rpg:whirlwind";
        twister.time_to_live_seconds = 7;
        twister.placement.apply_yaw = true;
        spawn.action.spawns = List.of(twister);
        spawn.sound = Sound.withVolume(Identifier.of("more_rpg_classes:air_magic_cast1"), 0.5F);
        spawn.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_medium)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .verticalOrigin(ParticleGroupBuilder.Batches.CENTER)
                                .count(10).speed(0.1F, 0.5F)));
        spell.impacts = List.of(spawn);

        var areaImpact = new Spell.AreaImpact();
        areaImpact.radius = 2.5F;
        areaImpact.force_indirect = true;
        spell.area_impact = areaImpact;

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_3_passive_1 = add(air_tier_3_passive_1());
    private static MrpgSkillSpells.Entry air_tier_3_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_3_passive_1");
        var effect = MrpgSkillEffects.AIR_BUBBLE;
        var title = effect.title;
        var description = "Air spells have {trigger_chance_1} chance, to grant you " + effect.title + ", absorbing "
                + TooltipTokens.effect(effect.id) + " damage and knocking attackers back, lasts {stash_duration} sec.";
        var duration = WIZARD_WARD_DURATION;

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.airWizardSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        spell.release.sound = new Sound(MrpgSkillSounds.air_impetus_buff.id());

        var spell_trigger = SpellBuilder.Triggers.activeSpellCast(MoreSpellSchools.AIR);
        spell_trigger.chance = WIZARD_WARD_CHANCE;
        spell_trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(spell_trigger);

        spell.deliver.type = Spell.Delivery.Type.STASH_EFFECT;
        spell.deliver.stash_effect = new Spell.Delivery.StashEffect();
        spell.deliver.stash_effect.id = effect.id.toString();
        spell.deliver.stash_effect.duration = duration;
        spell.deliver.stash_effect.amplifier = 0;
        spell.deliver.stash_effect.amplifier_power_multiplier = 0.2F;
        spell.deliver.stash_effect.consume = 0;

        var stash_trigger = SpellBuilder.Triggers.damageTaken();
        spell.deliver.stash_effect.triggers = List.of(stash_trigger);

        var damage = SpellBuilder.Impacts.damage(0.0F, 1.5F);
        damage.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_medium)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(10).speed(0.2F, 0.2F)));
        damage.sound = new Sound( "more_rpg_classes:air_magic_impact2");
        spell.impacts = List.of(damage);

        SpellBuilder.Cost.cooldown(spell, duration * 2);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_3_passive_2 = add(air_tier_3_passive_2());
    private static MrpgSkillSpells.Entry air_tier_3_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_3_passive_2");
        var title = "Wind Flurry";
        var description = "Casting Air spells shoot piercing winds at the target, dealing {damage} damage.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.airWizardSchool;
        spell.range = 16;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.activeSpellCast(MrpgSkillSpells.airWizardSchool);
        trigger.chance = 1F;
        spell.passive.triggers = List.of(trigger);

        spell.deliver.type = Spell.Delivery.Type.PROJECTILE;
        spell.deliver.projectile = new Spell.Delivery.ShootProjectile();
        spell.deliver.projectile.launch_properties.sound = new Sound(MrpgSkillSounds.air_wind_flurry_release.id());
        spell.deliver.projectile.direct_towards_target = true;
        spell.deliver.projectile.launch_properties.velocity = 1.3F;
        spell.deliver.projectile.launch_properties.extra_launch_count =2;
        spell.deliver.projectile.launch_properties.extra_launch_delay = 5;
        spell.deliver.projectile.projectile = new Spell.ProjectileData();
        spell.deliver.projectile.projectile.perks = new Spell.ProjectileData.Perks();
        spell.deliver.projectile.projectile.perks.pierce = 999;
        spell.deliver.projectile.projectile.perks.ricochet_range = 0F;
        spell.deliver.projectile.projectile.perks.ricochet = 0;
        spell.deliver.projectile.projectile.perks.bounce = 0;

        var model = SpellBuilder.ProjectileModels.model("mrpgc_skill_tree:spell_projectile/wind_flurry", 1.0F, LightEmission.NONE);
        model.rotate_degrees_per_tick = 0F;

        spell.deliver.projectile.projectile.client_data = new Spell.ProjectileData.Client();
        spell.deliver.projectile.projectile.client_data.composite_model = SpellBuilder.ProjectileModels.composite(model);


        var impact = SpellBuilder.Impacts.damage(0.3F, 0F);
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(MoreParticles.SMALL_GUST)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(10).speed(0.2F, 0.2F)));
        impact.sound = new Sound("more_rpg_classes:air_magic_impact3");
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 20F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_2_spell_2_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.AIR, MrpgSkillSpells.airWizardSchool,
            "air_tier_2_spell_2_root", "elemental_wizards_rpg:helper/wind_twister_impact", "Twister", 0.1F));
    public static final MrpgSkillSpells.Entry air_tier_2_spell_2_modifier_1 = add(air_tier_2_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry air_tier_2_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_2_spell_2_modifier_1");
        var title = "Lingering Winds";
        var description = "Twister lasts {spawn_duration_add} sec longer.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.airWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_twister";
        modifier.spawn_duration_add = 4F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_2_spell_2_modifier_2 = add(air_tier_2_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry air_tier_2_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_2_spell_2_modifier_2");
        var title = "Farther Winds";
        var description = "Range of Twister increased by {range_add} blocks.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.airWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_twister";
        modifier.range_add = 6F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_3_spell_2_root = add(MrpgSkillsCommon.critRoot(
            MrpgSkillSpells.Category.AIR, MrpgSkillSpells.airWizardSchool,
            "air_tier_3_spell_2_root", "elemental_wizards_rpg:wind_windfield", "Windfield", 0.05F));
    public static final MrpgSkillSpells.Entry air_tier_3_spell_2_modifier_1 = add(air_tier_3_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry air_tier_3_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_3_spell_2_modifier_1");
        var title = "Howling Gale";
        var description = "Increases the duration of Windfield's slow by {effect_duration_add} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.airWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_windfield";
        modifier.effect_duration_add = 4F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_3_spell_2_modifier_2 = add(air_tier_3_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry air_tier_3_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_3_spell_2_modifier_2");
        var title = "Raging Squall";
        var bonus = 2.0F;
        var description = "Increases the area of effect of Windfield by "
                + TooltipTokens.bakedPercent(bonus) + ".";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.airWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_windfield";
        var extendedRadius = 3.0F * (1F + bonus);
        Spell.AreaImpact area_impact = new Spell.AreaImpact();
        area_impact.radius = extendedRadius;
        area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        area_impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_large)
                        .color(Color.WHITE)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(25F).speed(0.3F, 0.8F)),
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_medium)
                        .color(Color.WHITE)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(25F).speed(0.3F, 0.8F)
                                .extent(0.5F)));
        modifier.replacing_area_impact = area_impact;

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_4_spell_2_root = add(MrpgSkillsCommon.cooldownRoot(
            MrpgSkillSpells.Category.AIR, MrpgSkillSpells.airWizardSchool,
            "air_tier_4_spell_2_root", "elemental_wizards_rpg:wind_stormdraft", "Storm Draft", 3F));
    public static final MrpgSkillSpells.Entry air_tier_4_spell_2_modifier_1 = add(air_tier_4_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry air_tier_4_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_4_spell_2_modifier_1");
        var title = "Prolonged Gust";
        var description = "Channeling Storm Draft releases {channel_ticks_add} additional times.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.airWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_stormdraft";
        modifier.channel_ticks_add = 3;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_4_spell_2_modifier_2 = add(air_tier_4_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry air_tier_4_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_4_spell_2_modifier_2");
        var title = "Numbing Draft";
        var description = "Increases the stun duration of Storm Draft by {effect_duration_add} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.airWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_stormdraft";
        modifier.effect_duration_add = 2F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
}
