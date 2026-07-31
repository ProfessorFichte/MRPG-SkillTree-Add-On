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
import net.spell_engine.api.spell.fx.ModelEffect;
import net.spell_engine.api.spell.fx.ModelEffectBuilder;
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

public class AirSkillSpells {
    public static final List<MrpgSkillSpells.Entry> all = new ArrayList<>();

    private static MrpgSkillSpells.Entry add(MrpgSkillSpells.Entry entry) {
        all.add(entry);
        return entry;
    }

    ///AIR MODIFIERS
    public static final MrpgSkillSpells.Entry air_tier_1_spell_1_modifier_1 = add(air_tier_1_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry air_tier_1_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_1_spell_1_modifier_1");
        var title = "Fast Winds";
        var description = "Increases the knockback of Air Cutter by {knockback_multiply_base}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.airWizardSchool;

        var bonus = 0.5F;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_air_cutter";
        modifier.knockback_multiply_base = bonus;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_1_spell_1_modifier_2 = add(air_tier_1_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry air_tier_1_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_1_spell_1_modifier_2");
        var title = "Air Cutting Pressure";
        var description = "Air Cutter deals {power_multiplier} more damage.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.airWizardSchool;

        var bonus = 0.2F;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_air_cutter";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.power_multiplier = bonus;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_2_spell_1_modifier_1 = add(air_tier_2_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry air_tier_2_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_2_spell_1_modifier_1");
        var title = "Slow Fall";
        var description = "Aeroblast has a {trigger_chance} to apply slow falling.";
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.AIR));
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
        area_impact.particles = new ParticleBatch[]{
                new ParticleBatch("more_rpg_classes:small_gust",
                        ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET,
                        25, 0.2F, 1.0F).extent(2)
        };


        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);
        modifier.replacing_area_impact = area_impact;

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.AIR));
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_4_spell_1_modifier_1 = add(air_tier_4_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry air_tier_4_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_4_spell_1_modifier_1");
        var title = "Wind shear";
        var description = "Increases the duration of Tornado by {spawn_duration_add} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.airWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_tornado";
        modifier.spawn_duration_add = 3;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_4_spell_1_modifier_2 = add(air_tier_4_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry air_tier_4_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_4_spell_1_modifier_2");
        var title = "Negative Pressure";
        var description = "Tornado critical damage hits are increased by {critical_damage_bonus}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.airWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_tornado";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.critical_damage_bonus = 0.4F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    ///AIR PASSIVES
    public static final MrpgSkillSpells.Entry air_tier_1_passive_1 = add(air_tier_1_passive_1());
    private static MrpgSkillSpells.Entry air_tier_1_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_1_passive_1");
        var effect = MrpgSkillEffects.IMPETUS;
        var title = effect.title;
        var description = "Air spell impacts have {trigger_chance} chance to increase the casters Spell Haste by {bonus} for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellPassive();
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description()
                    .replace("{bonus}", bonus);
        };
        spell.school = MrpgSkillSpells.airWizardSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.activeSpellHit(0.05F, "air");
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(), 5F, 0);
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.STRIPE,
                                SpellEngineParticles.MagicParticles.Motion.FLOAT).id().toString(),
                        ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET,
                        20, 0.05F, 0.1F)
                        .color(Colors.WHITE)
        };
        impact.sound = new Sound(MrpgSkillSounds.air_impetus_buff.id());
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 1F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_1_passive_2 = add(air_tier_1_passive_2());
    private static MrpgSkillSpells.Entry air_tier_1_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_1_passive_2");
        var effect = MrpgSkillEffects.EYE_OF_THE_STORM;
        var title = "Eye of the Storm";
        float healthThreshold = 0.3F;
        var description = "Air spell impacts on targets below {threshold} makes the target more vulnerable to spell crits.";
        var spell = SpellBuilder.createSpellPassive();
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var threshold = SpellTooltip.percent(healthThreshold);
            return args.description()
                    .replace("{threshold}", threshold);
        };
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_2_passive_1 = add(air_tier_2_passive_1());
    private static MrpgSkillSpells.Entry air_tier_2_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_2_passive_1");
        var effect = MrpgSkillEffects.TAILWIND;
        var title = "Tailwind";
        var description = "{trigger_chance} chance upon rolling to leave tailwind behind for {cloud_duration} sec, increasing movement speed by {bonus} for {effect_duration} sec.";

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
        cloud.client_data.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.STRIPE,
                                SpellEngineParticles.MagicParticles.Motion.FLOAT).id().toString(),
                        ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET,
                        20, 0.3F, 0.6F)
                        .color(Colors.WHITE).extent(3.5F),
                new ParticleBatch(
                        "more_rpg_classes:small_gust",
                        ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET,
                        15, 0.1F, 0.3F)
                        .color(Colors.WHITE).extent(3.5F)
        };
        spell.deliver.clouds = List.of(cloud);
        Spell.Impact buff = SpellBuilder.Impacts.effectSet(effect.id.toString(),6,0);
        buff.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.STRIPE,
                                SpellEngineParticles.MagicParticles.Motion.FLOAT).id().toString(),
                        ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET,
                        5, 1.0F, 2.0F)
                        .color(Colors.WHITE).extent(3.5F)
        };
        spell.impacts = List.of(buff);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_2_passive_2 = add(air_tier_2_passive_2());
    private static MrpgSkillSpells.Entry air_tier_2_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_2_passive_2");
        var title = "Strongwind";
        var description = "{trigger_chance} chance upon rolling to spawn a strongwind that deals {damage} damage to enemies.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.airWizardSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        trigger.chance = 0.5F;
        spell.passive.triggers = List.of(trigger);

        spell.deliver.type = Spell.Delivery.Type.CLOUD;
        Spell.Delivery.Cloud cloud = new Spell.Delivery.Cloud();
        cloud.volume.radius = 2.0F;
        cloud.volume.area.vertical_range_multiplier = 1.5F;
        cloud.delay_ticks = 5;
        cloud.impact_tick_interval = 20;
        cloud.time_to_live_seconds = 5;
        cloud.spawn = new Spell.Delivery.Cloud.Spawn();
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        cloud.presence_sound = Sound.withVolume(Identifier.of("more_rpg_classes:air_magic_cast1"),0.5F);
        int tornadoDurationTicks = (int) (cloud.time_to_live_seconds * 20);
        cloud.client_data.model_fx = List.of(
                ModelEffectBuilder.create("elemental_wizards_rpg:effect/tornado")
                        .scale(1.5F)
                        .light(LightEmission.NONE)
                        .duration(tornadoDurationTicks)
                        .rotate(0, -20 * tornadoDurationTicks, 0, 0, tornadoDurationTicks, ModelEffect.Easing.LINEAR)
                        .build()
        );
        cloud.client_data.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.1F, 0.5F)
        };
        spell.deliver.clouds = List.of(cloud);
        Spell.Impact damage = SpellBuilder.Impacts.damage(0.3F,0.0F);
        damage.sound = Sound.withVolume(Identifier.of("spell_engine:generic_wind_charging"),0.7F);
        damage.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.2F, 0.2F)
        };
        spell.impacts = List.of(damage);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_3_passive_1 = add(air_tier_3_passive_1());
    private static MrpgSkillSpells.Entry air_tier_3_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "air_tier_3_passive_1");
        var effect = MrpgSkillEffects.AIR_BUBBLE;
        var title = effect.title;
        var description = "Air spells have {trigger_chance_1} chance, to grant you " + effect.title + ", absorbing damage and knocking attackers back, lasts {stash_duration} sec.";
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
        damage.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.2F, 0.2F)
        };
        damage.sound = new Sound( "more_rpg_classes:air_magic_impact2");
        spell.impacts = List.of(damage);

        SpellBuilder.Cost.cooldown(spell, duration * 2);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
    public static final MrpgSkillSpells.Entry air_tier_3_passive_2 = add(air_tier_3_passive_2()); //
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
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        "more_rpg_classes:small_gust",
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.2F, 0.2F)
        };
        impact.sound = new Sound("more_rpg_classes:air_magic_impact3");
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 20F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.AIR));
    }
}
