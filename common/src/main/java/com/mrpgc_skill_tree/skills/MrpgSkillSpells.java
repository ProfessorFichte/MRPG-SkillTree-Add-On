package com.mrpgc_skill_tree.skills;

import com.mrpgc_skill_tree.effect.MrpgSkillEffects;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.more_rpg_classes.effect.MRPGCEffects;
import net.skill_tree_rpgs.skills.SkillTreeSounds;
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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static com.mrpgc_skill_tree.MRPGCSkillTreeAddOn.MOD_ID;
import static net.skill_tree_rpgs.skills.Spells.*;

public class MrpgSkillSpells {
    public static final String NAMESPACE = MOD_ID;
    public enum Category {
        AIR, EARTH, WATER, BERSERKER, FORCEMASTER, WAR_ARCHER, DEADEYE, TUNDRA_HUNTER
    }
    public record Entry(Identifier id, Spell spell, String title, String description,
                        @Nullable SpellTooltip.DescriptionMutator mutator, EnumSet<Category> categories) {
        public Entry(Identifier id, Spell spell, String title, String description,
                     @Nullable SpellTooltip.DescriptionMutator mutator, Category category) {
            this(id, spell, title, description, mutator, EnumSet.of(category));
        }
        public String key() {
            return id.getPath();
        }
    }

    public static final List<Entry> all = new ArrayList<>();
    private static Entry add(Entry entry) {
        all.add(entry);
        return entry;
    }

    private static Spell createModifierAlikePassiveSpell() {
        var spell = SpellBuilder.createSpellPassive();
        spell.range = 0;
        spell.tooltip = new Spell.Tooltip();
        spell.tooltip.show_activation = false;
        return spell;
    }
    private static Spell.Impact.TargetModifier createImpactModifier(String entityType) {
        var condition = new Spell.TargetCondition();
        condition.entity_type = entityType;
        var modifier = new Spell.Impact.TargetModifier();
        modifier.conditions = List.of(condition);
        return modifier;
    }
    private static void undeadAllow(Spell.Impact impact) {
        var modifier = createImpactModifier("#minecraft:undead");
        modifier.execute = TriState.ALLOW;
        impact.target_modifiers = List.of(modifier);
    }
    private static void bleedingDeny(Spell.Impact impact) {
        var modifier = createImpactModifier("#more_rpg_classes:bleeding_immune");
        modifier.execute = TriState.DENY;
        impact.target_modifiers = List.of(modifier);
    }
    private static void poisonDeny(Spell.Impact impact) {
        var modifier = createImpactModifier("#minecraft:ignores_poison_and_regen");
        modifier.execute = TriState.DENY;
        impact.target_modifiers = List.of(modifier);
    }
    private static void freezeImmuneDeny(Spell.Impact impact) {
        var modifier = createImpactModifier("#minecraft:freeze_immune_entity_types");
        modifier.execute = TriState.DENY;
        impact.target_modifiers = List.of(modifier);
    }
    private static void bossImmuneDeny(Spell.Impact impact) {
        var modifier = createImpactModifier("#c:bosses");
        modifier.execute = TriState.DENY;
        impact.target_modifiers = List.of(modifier);
    }
    private static final SpellEntityPredicates.Entry HAS_BLEEDING = SpellEntityPredicates.hasEffectOptimized(Identifier.of("more_rpg_classes", "bleeding"));
    private static final SpellEntityPredicates.Entry HAS_FROSTED = SpellEntityPredicates.hasEffectOptimized(Identifier.of("more_rpg_classes", "frosted"));
    private static final SpellEntityPredicates.Entry HAS_RAGE = SpellEntityPredicates.hasEffectOptimized(Identifier.of("berserker_rpg", "rage"));

    public static final SpellSchool airWizardSchool = MoreSpellSchools.AIR;
    public static final SpellSchool earthWizardSchool = MoreSpellSchools.EARTH;
    public static final SpellSchool waterWizardSchool = MoreSpellSchools.WATER;
    public static final SpellSchool berserkerSchool = MoreSpellSchools.RAGE_MELEE;
    public static final SpellSchool forcemasterFighterSchool = ExternalSpellSchools.PHYSICAL_MELEE;
    public static final SpellSchool forcemasterCasterSchool = SpellSchools.ARCANE;
    public static final SpellSchool warArcherSchool = MoreSpellSchools.FIRE_RANGED;
    public static final SpellSchool deadeyeSchool = ExternalSpellSchools.PHYSICAL_RANGED;
    public static final SpellSchool tundraHunterSchool = MoreSpellSchools.FROST_RANGED;
    ///AIR MODIFIERS
    public static final Entry air_spec_a_modifier_1 = add(air_spec_a_modifier_1());
    private static Entry air_spec_a_modifier_1() {
        var id = Identifier.of(NAMESPACE, "air_spec_a_modifier_1");
        var title = "Fast Winds";
        var description = "Increases the knockback of Air Cutter by {knockback_multiply_base}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = airWizardSchool;

        var bonus = 0.5F;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_air_cutter";
        modifier.knockback_multiply_base = bonus;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.AIR));
    }
    public static final Entry air_spec_b_modifier_1 = add(air_spec_b_modifier_1());
    private static Entry air_spec_b_modifier_1() {
        var id = Identifier.of(NAMESPACE, "air_spec_b_modifier_1");
        var title = "Air Cutting Pressure";
        var description = "Air Cutter deals {power_multiplier} more damage.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = airWizardSchool;

        var bonus = 0.2F;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_air_cutter";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.power_multiplier = bonus;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.AIR));
    }
    public static final Entry air_spec_a_modifier_2 = add(air_spec_a_modifier_2());
    private static Entry air_spec_a_modifier_2() {
        var id = Identifier.of(NAMESPACE, "air_spec_a_modifier_2");
        var title = "Slow Fall";
        var description = "Aeroblast has a {trigger_chance} to apply slow falling.";
        var spell = createModifierAlikePassiveSpell();
        spell.school = airWizardSchool;
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

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.AIR));
    }
    public static final Entry air_spec_b_modifier_2 = add(air_spec_b_modifier_2());
    private static Entry air_spec_b_modifier_2() {
        var id = Identifier.of(NAMESPACE, "air_spec_b_modifier_2");
        var title = "Aeroblast Field";
        var description = "Aeroblast now deals {damage} damage around the target.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = airWizardSchool;

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

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.AIR));
    }
    public static final Entry air_spec_a_modifier_3 = add(air_spec_a_modifier_3());
    private static Entry air_spec_a_modifier_3() {
        var id = Identifier.of(NAMESPACE, "air_spec_a_modifier_3");
        var title = "Aerial Swiftness";
        var description = "Reduces the cooldown of Updraft by {cooldown_duration_deduct} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = airWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_updraft";
        modifier.cooldown_duration_deduct = 4;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.AIR));
    }
    public static final Entry air_spec_b_modifier_3 = add(air_spec_b_modifier_3());
    private static Entry air_spec_b_modifier_3() {
        var id = Identifier.of(NAMESPACE, "air_spec_b_modifier_3");
        var title = "Aerial Precision";
        var description = "Updraft deals {critical_damage_bonus} critical damage bonus.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = airWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_updraft";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.critical_damage_bonus = 0.25F;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.AIR));
    }
    public static final Entry air_spec_a_modifier_4 = add(air_spec_a_modifier_4());
    private static Entry air_spec_a_modifier_4() {
        var id = Identifier.of(NAMESPACE, "air_spec_a_modifier_4");
        var title = "Wind shear";
        var description = "Increases the duration of Tornado by {spawn_duration_add} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = airWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_tornado";
        modifier.spawn_duration_add = 3;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.AIR));
    }
    public static final Entry air_spec_b_modifier_4 = add(air_spec_b_modifier_4());
    private static Entry air_spec_b_modifier_4() {
        var id = Identifier.of(NAMESPACE, "air_spec_b_modifier_4");
        var title = "Negative Pressure";
        var description = "Tornado critical damage hits are increased by {critical_damage_bonus}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = airWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_tornado";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.critical_damage_bonus = 0.4F;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.AIR));
    }
    ///AIR PASSIVES
    public static final Entry air_spec_a_passive_1 = add(air_spec_a_passive_1());
    private static Entry air_spec_a_passive_1() {
        var id = Identifier.of(NAMESPACE, "air_spec_a_passive_1");
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
        spell.school = airWizardSchool;
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

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.AIR));
    }
    public static final Entry air_spec_b_passive_1 = add(air_spec_b_passive_1());
    private static Entry air_spec_b_passive_1() {
        var id = Identifier.of(NAMESPACE, "air_spec_b_passive_1");
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
        spell.school = airWizardSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.spellHit(0.5F,"air");
        trigger.target_conditions = List.of(SpellBuilder.TargetConditions.lowHP(healthThreshold));
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(), 5F, 0);
        impact.sound = new Sound(MrpgSkillSounds.air_eye_of_the_storm.id());
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 1F);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.AIR));
    }
    public static final Entry air_spec_a_passive_2 = add(air_spec_a_passive_2());
    private static Entry air_spec_a_passive_2() {
        var id = Identifier.of(NAMESPACE, "air_spec_a_passive_2");
        var effect = MrpgSkillEffects.TAILWIND;
        var title = "Tailwind";
        var description = "{trigger_chance} chance upon rolling to leave tailwind behind for {cloud_duration} sec, increasing movement speed by {bonus} for {effect_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = airWizardSchool;
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

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.AIR));
    }
    public static final Entry air_spec_b_passive_2 = add(air_spec_b_passive_2());
    private static Entry air_spec_b_passive_2() {
        var id = Identifier.of(NAMESPACE, "air_spec_b_passive_2");
        var title = "Strongwind";
        var description = "{trigger_chance} chance upon rolling to spawn a strongwind that deals {damage} damage to enemies.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = airWizardSchool;
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
        cloud.client_data.model = new Spell.ProjectileModel();
        cloud.presence_sound = Sound.withVolume(Identifier.of("more_rpg_classes:air_magic_cast1"),0.5F);
        cloud.client_data.model.model_id = "elemental_wizards_rpg:effect/tornado";
        cloud.client_data.model.rotate_degrees_per_tick = -20;
        cloud.client_data.model.light_emission = LightEmission.NONE;
        cloud.client_data.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.1F, 0.5F)
        };
        cloud.client_data.model.scale = 1.5F;
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

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.AIR));
    }
    public static final Entry air_spec_a_passive_3 = add(air_spec_a_passive_3());
    private static Entry air_spec_a_passive_3() {
        var id = Identifier.of(NAMESPACE, "air_spec_a_passive_3");
        var effect = MrpgSkillEffects.AIR_BUBBLE;
        var title = effect.title;
        var description = "Air spells have {trigger_chance_1} chance, to grant you " + effect.title + ", absorbing damage and knocking attackers back, lasts {stash_duration} sec.";
        var duration = WIZARD_WARD_DURATION;

        var spell = SpellBuilder.createSpellPassive();
        spell.school = airWizardSchool;
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

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.AIR));
    }
    public static final Entry air_spec_b_passive_3 = add(air_spec_b_passive_3()); //
    private static Entry air_spec_b_passive_3() {
        var id = Identifier.of(NAMESPACE, "air_spec_b_passive_3");
        var title = "Wind Flurry";
        var description = "Casting Air spells shoot piercing winds at the target, dealing {damage} damage.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = airWizardSchool;
        spell.range = 16;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.activeSpellCast(airWizardSchool);
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

        var model = new Spell.ProjectileModel();
        model.light_emission = LightEmission.NONE;
        model.model_id = "mrpgc_skill_tree:spell_projectile/wind_flurry";
        model.scale = 1.0F;
        model.rotate_degrees_per_tick = 0F;

        spell.deliver.projectile.projectile.client_data = new Spell.ProjectileData.Client();
        spell.deliver.projectile.projectile.client_data.model = model;


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

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.AIR));
    }
    public static final Color EARTH_SPELL_COLOR = new Color(255.0F, 165.0F, 0.0F);
    ///EARTH MODIFIERS
    public static final Entry earth_spec_a_modifier_1 = add(earth_spec_a_modifier_1());
    private static Entry earth_spec_a_modifier_1() {
        var id = Identifier.of(NAMESPACE, "earth_spec_a_modifier_1");
        var title = "Sharpened Stone Spears";
        var description = "Increases the duration of Bleeding by {effect_duration_add} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = earthWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:terra_stone_spear";
        modifier.effect_duration_add = 2;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.EARTH));
    }
    public static final Entry earth_spec_b_modifier_1 = add(earth_spec_b_modifier_1());
    private static Entry earth_spec_b_modifier_1() {
        var id = Identifier.of(NAMESPACE, "earth_spec_b_modifier_1");
        var title = "Earthen Blast";
        var bonus = 0.5F;
        var description = "Increases the area of effect of Stone Spear by {bonus}.";
        var mutator = new SpellTooltip.DescriptionMutator() {
            @Override
            public String mutate(Args args) {
                return args.description().replace("{bonus}", SpellTooltip.percent(bonus));
            }
        };
        var spell = SpellBuilder.createSpellModifier();
        spell.school = earthWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:terra_stone_spear";
        var extendedRadius = 2.5F * (1F + bonus);
        Spell.AreaImpact area_impact = new Spell.AreaImpact();
        area_impact.radius = extendedRadius;
        area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        area_impact.particles = new ParticleBatch[]{(new ParticleBatch("more_rpg_classes:stone_explosion", ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, 1.0F, 0.0F, 0.0F)).scale(extendedRadius/2)};
        area_impact.sound = Sound.withVolume(Identifier.of("block.pointed_dripstone.break"),1.5F);
        modifier.replacing_area_impact = area_impact;

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.EARTH));
    }
    public static final Entry earth_spec_a_modifier_2 = add(earth_spec_a_modifier_2());
    private static Entry earth_spec_a_modifier_2() {
        var id = Identifier.of(NAMESPACE, "earth_spec_a_modifier_2");
        var title = "Obsidian Skin";
        var description = "Stone Flesh grants you Obsidian Skin, protecting you from {effect_amplifier} incoming attack for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = earthWizardSchool;
        var effect = MrpgSkillEffects.OBSIDIAN_SKIN;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:terra_stone_flesh";
        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(),5,1);
        impact.action.apply_to_caster = true;

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.EARTH));
    }
    public static final Entry earth_spec_b_modifier_2 = add(earth_spec_b_modifier_2());
    private static Entry earth_spec_b_modifier_2() {
        var id = Identifier.of(NAMESPACE, "earth_spec_b_modifier_2");
        var title = "Earthbender";
        var description = "Stone Flesh grants you the Earthbender effect, increasing your earth spell power by {bonus} for {effect_duration} seconds.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = earthWizardSchool;
        var effect = MrpgSkillEffects.EARTH_BENDER;
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description()
                    .replace("{bonus}", bonus);
        };

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:terra_stone_flesh";
        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(),6,0);
        impact.action.apply_to_caster = true;

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.EARTH));
    }
    public static final Entry earth_spec_a_modifier_3 = add(earth_spec_a_modifier_3());
    private static Entry earth_spec_a_modifier_3() {
        var id = Identifier.of(NAMESPACE, "earth_spec_a_modifier_3");
        var title = "Obstacle Dripstones";
        var description = "Terra Circle applies slowness, reducing movement speed by {bonus}, stacking up to {effect_amplifier_cap} times, lasting {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        var effect = MrpgSkillEffects.DRIPSTONE_OBSTACLES;
        spell.school = earthWizardSchool;
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description().replace("{bonus}", bonus);
        };

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:terra_drip_circle";
        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 6, 0, 3);
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.EARTH));
    }
    public static final Entry earth_spec_b_modifier_3 = add(earth_spec_b_modifier_3());
    private static Entry earth_spec_b_modifier_3() {
        var id = Identifier.of(NAMESPACE, "earth_spec_b_modifier_3");
        var title = "Sharp Dripstones";
        var description = "Terra Circle damage is increased by {power_multiplier}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = earthWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:terra_drip_circle";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.power_multiplier = 0.25F;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.EARTH));
    }
    public static final Entry earth_spec_a_modifier_4 = add(earth_spec_a_modifier_4());
    private static Entry earth_spec_a_modifier_4() {
        var id = Identifier.of(NAMESPACE, "earth_spec_a_modifier_4");
        var title = "Earthquake Concussion";
        var description = "Earthquake reduces offensive attributes by {bonus} for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        var effect = MrpgSkillEffects.CONCUSSION;
        spell.school = earthWizardSchool;
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description().replace("{bonus}", bonus);
        };

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:terra_earthquake";
        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(), 6, 0);
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.EARTH));
    }
    public static final Entry earth_spec_b_modifier_4 = add(earth_spec_b_modifier_4());
    private static Entry earth_spec_b_modifier_4() {
        var id = Identifier.of(NAMESPACE, "earth_spec_b_modifier_4");
        var title = "Magnitude 10";
        var description = "Increases the range of Earthquake by {range_add}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = earthWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:terra_earthquake";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.range_add = 5;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.EARTH));
    }
    ///EARTH PASSIVES
    public static final Entry earth_spec_a_passive_1 = add(earth_spec_a_passive_1());
    private static Entry earth_spec_a_passive_1() {
        var id = Identifier.of(NAMESPACE, "earth_spec_a_passive_1");
        var effect = MrpgSkillEffects.EARTHEN_BLESSING;
        var title = effect.title;
        var description = "Earth spell impacts have {trigger_chance} chance to apply Earthen Blessing effect."
                + " Increasing armor by {bonus}, stacking up to {effect_amplifier_cap} times, lasting {effect_duration} sec.";
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description()
                    .replace("{bonus}", bonus);
        };
        var spell = SpellBuilder.createSpellPassive();
        spell.school = earthWizardSchool;
        spell.range = 0;
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.activeSpellHit(0.2F, "earth");
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 10, 1, 5);
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        "more_rpg_classes:stone_particle",
                        ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET,
                        5, 0.1F, 0.8F).extent(1.0F)
        };
        impact.sound = new Sound(MrpgSkillSounds.earthen_blessing.id());
        spell.impacts = List.of(impact);
        SpellBuilder.Cost.cooldown(spell, 5F);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.EARTH));
    }
    public static final Entry earth_spec_b_passive_1 = add(earth_spec_b_passive_1());
    private static Entry earth_spec_b_passive_1() {
        var id = Identifier.of(NAMESPACE, "earth_spec_b_passive_1");
        var title = "Serrated Stones";
        var description = "Earth spell impacts have {trigger_chance} chance to deal additional {damage} damage, if the target has a bad status effect.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = earthWizardSchool;
        spell.range = 0;
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.activeSpellHit(0.5F, "earth");
        var condition = new Spell.TargetCondition();
        condition.entity_predicate_id = SpellEntityPredicates.HAS_BAD_EFFECT.id().toString();
        trigger.target_conditions = List.of(condition);
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.damage(0.3F,0.0F);
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.SPELL,
                                SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        25, 0.2F, 0.25F)
                        .color(EARTH_SPELL_COLOR.toRGBA())
        };
        spell.impacts = List.of(impact);
        SpellBuilder.Cost.cooldown(spell, 1F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.EARTH));
    }
    public static final Entry earth_spec_a_passive_2 = add(earth_spec_a_passive_2());
    private static Entry earth_spec_a_passive_2() {
        var id = Identifier.of(NAMESPACE, "earth_spec_a_passive_2");
        var effect = MrpgSkillEffects.DIFFICULT_TERRAIN;
        var title = "Difficult Terrain";
        var description = " {trigger_chance} chance upon rolling to leave difficult terrain behind for {cloud_duration} sec, slowing for {effect_duration} sec and dealing {damage} damage to enemies.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = earthWizardSchool;
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
        cloud.spawn.sound = new Sound("block.pointed_dripstone.break");
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        cloud.client_data.light_level = 0;
        cloud.client_data.particles = new ParticleBatch[]{
                new ParticleBatch(
                        "more_rpg_classes:stone_trap",
                        ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET,
                        2, 0, 0)
        };
        spell.deliver.clouds = List.of(cloud);
        Spell.Impact debuff = SpellBuilder.Impacts.effectSet(effect.id.toString(),3,0);
        Spell.Impact damage = SpellBuilder.Impacts.damage(0.1F,0.0F);
        debuff.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET,
                        10, 0.3F, 0.3F)
        };
        spell.impacts = List.of(debuff, damage);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.EARTH));
    }
    public static final Entry earth_spec_b_passive_2 = add(earth_spec_b_passive_2());
    private static Entry earth_spec_b_passive_2() {
        var id = Identifier.of(NAMESPACE, "earth_spec_b_passive_2");
        var title = "Seismic Entry";
        var description = " {trigger_chance_1} chance while rolling, to deal {damage} damage and knock up nearby enemies.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = earthWizardSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        trigger.chance = 0.35F;
        spell.passive.triggers = List.of(trigger);

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var stashEffect = MrpgSkillEffects.SEISMIC_ENTRY;
        var stashTrigger = SpellBuilder.Triggers.effectTick(stashEffect.id.toString());
        SpellBuilder.Deliver.stash(spell, stashEffect.id.toString(), 1.5F, List.of(stashTrigger));
        spell.deliver.stash_effect.consume = 0;

        var impact = SpellBuilder.Impacts.damage(0.1F, 0F);
        var custom = new Spell.Impact();
        bossImmuneDeny(custom);
        custom.action = new Spell.Impact.Action();
        custom.action.custom = new Spell.Impact.Action.Custom();
        custom.action.type = Spell.Impact.Action.Type.CUSTOM;
        custom.action.custom.intent = SpellTarget.Intent.HARMFUL;
        custom.action.custom.handler = "more_rpg_classes:knock_up_fixed";
        spell.impacts = List.of(impact, custom);
        var areaImpact = new Spell.AreaImpact();
        areaImpact.radius = 2.5F;
        areaImpact.force_indirect = true;
        areaImpact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET,
                        2, 0.1F, 0.1F)
        };
        areaImpact.sound = new Sound(MrpgSkillSounds.seismic_entry.id());
        spell.area_impact = areaImpact;

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.EARTH));
    }
    public static final Entry earth_spec_a_passive_3 = add(earth_spec_a_passive_3());
    private static Entry earth_spec_a_passive_3() {
        var id = Identifier.of(NAMESPACE, "earth_spec_a_passive_3");
        var title = "Stone Heart";
        var description = "Earth Spells have {trigger_chance} chance to absorb a huge amount of damage.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = earthWizardSchool;
        spell.range = 0;
        var duration = WIZARD_WARD_DURATION;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var spell_trigger = SpellBuilder.Triggers.activeSpellCast(earthWizardSchool);
        spell_trigger.chance = WIZARD_WARD_CHANCE;
        spell_trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(spell_trigger);

        var effect = MrpgSkillEffects.STONE_HEART;
        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(),duration,0);
        impact.action.status_effect.amplifier_power_multiplier = 0.5F;
        impact.particles = new ParticleBatch[]{
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_shield.id(), Color.fromRGBA(EARTH_SPELL_COLOR.toRGBA())),
        };
        impact.sound = Sound.withVolume(Identifier.of("more_rpg_classes:earth_magic_cast1"),0.5F);
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, duration * 2);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.EARTH));
    }
    public static final Entry earth_spec_b_passive_3 = add(earth_spec_b_passive_3());
    private static Entry earth_spec_b_passive_3() {
        var id = Identifier.of(NAMESPACE, "earth_spec_b_passive_3");
        var title = "Aftershock";
        float radius = 5F;
        var description = "Taking damage has {trigger_chance} chance to deal {damage} to nearby targets.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = earthWizardSchool;
        spell.range = radius;

        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();

        spell.release.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.SPARK,
                                SpellEngineParticles.MagicParticles.Motion.ASCEND).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        40, 0.6F, 0.8F),
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET,
                        20, 0.4F, 0.4F),
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET,
                        20, 0.6F, 0.6F),
                SpellBuilder.Particles.area(SpellEngineParticles.area_effect_658.id())
                        .scale(radius * 0.8F)
                        .color(EARTH_SPELL_COLOR.toRGBA()),
                SpellBuilder.Particles.area(SpellEngineParticles.area_effect_658.id())
                        .scale(radius)
                        .color(EARTH_SPELL_COLOR.toRGBA())
        };
        spell.release.sound = Sound.withVolume(Identifier.of("more_rpg_classes:earth_magic_impact1"),0.7F);

        var trigger = SpellBuilder.Triggers.damageTaken();
        trigger.chance = 0.25F;
        trigger.aoe_source_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        var damage = SpellBuilder.Impacts.damage(0.5F,0.2F);
        damage.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        20, 0.2F, 0.3F)
        };
        spell.impacts = List.of(damage);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.EARTH));
    }
    ///WATER MODIFIER
    public static final Color WATER_SPELL_COLOR = Color.from(0x4a8bff);
    public static final Entry water_spec_a_modifier_1 = add(water_spec_a_modifier_1());
    private static Entry water_spec_a_modifier_1() {
        var id = Identifier.of(NAMESPACE, "water_spec_a_modifier_1");
        var title = "Strong Water Whip";
        var description = "Water Whip deals {knockback_multiply_base} more knockback.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = waterWizardSchool;

        var bonus = 1.0F;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:aqua_water_whip";
        modifier.knockback_multiply_base = bonus;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WATER));
    }
    public static final Entry water_spec_b_modifier_1 = add(water_spec_b_modifier_1());
    private static Entry water_spec_b_modifier_1() {
        var id = Identifier.of(NAMESPACE, "water_spec_b_modifier_1");
        var title = "Splish Splash";
        var description = "Water Whip deals {damage} damage around the target.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = waterWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:aqua_water_whip";
        var impact = SpellBuilder.Impacts.damage(0.25F, 0.3F);
        impact.action.allow_on_center_target = false;

        var radius = 2.0F;

        var area_impact = new Spell.AreaImpact();
        area_impact.execute_action_type = Spell.Impact.Action.Type.DAMAGE;
        area_impact.radius = radius;
        area_impact.area = new Spell.Target.Area();
        area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        area_impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        "more_rpg_classes:big_splash",
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        20, 0.35F, 0.35F
                ),
                new ParticleBatch(
                        "more_rpg_classes:splash",
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        20, 0.15F, 0.15F
                )
        };

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);
        modifier.replacing_area_impact = area_impact;

        spell.modifiers = List.of(modifier);


        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WATER));
    }
    public static final Entry water_spec_a_modifier_2 = add(water_spec_a_modifier_2());
    private static Entry water_spec_a_modifier_2() {
        var id = Identifier.of(NAMESPACE, "water_spec_a_modifier_2");
        var title = "Bubble Pop";
        var description = "Bubble Beam's healing & damage is increased by by {power_multiplier}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = waterWizardSchool;

        var bonus = 0.2F;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:aqua_bubble_beam";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.power_multiplier = bonus;

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WATER));
    }
    public static final Entry water_spec_b_modifier_2 = add(water_spec_b_modifier_2());
    private static Entry water_spec_b_modifier_2() {
        var id = Identifier.of(NAMESPACE, "water_spec_b_modifier_2");
        var title = "Persistant Bubbles";
        var effect = MrpgSkillEffects.PERSISTENT_BUBBLES;
        var description = "Bubble Beam hits have {trigger_chance} to decrease the movement- & attackspeed by {bonus} for {effect_duration} seconds.";
        var spell = createModifierAlikePassiveSpell();
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description()
                    .replace("{bonus}", bonus);
        };
        spell.school = waterWizardSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = SpellBuilder.Triggers.specificSpellHit("elemental_wizards_rpg:aqua_bubble_beam");
        trigger.chance = 0.15F;
        spell.passive.triggers = List.of(trigger);

        var debuff = SpellBuilder.Impacts.effectSet(effect.id.toString(), 5, 0);
        debuff.action.status_effect.refresh_duration = true;
        spell.impacts = List.of(debuff);

        SpellBuilder.Cost.cooldown(spell, 1F);


        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.WATER));
    }
    public static final Entry water_spec_a_modifier_3 = add(water_spec_a_modifier_3());
    private static Entry water_spec_a_modifier_3() {
        var id = Identifier.of(NAMESPACE, "water_spec_a_modifier_3");
        var title = "Holy Water Spring";
        var description = "Springwater removes {effect_amplifier} negative effect from allies and deals extra {damage} damage to undead.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = waterWizardSchool;

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
        undeadAllow(impactUndead);
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

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WATER));
    }
    public static final Entry water_spec_b_modifier_3 = add(water_spec_b_modifier_3());
    private static Entry water_spec_b_modifier_3() {
        var id = Identifier.of(NAMESPACE, "water_spec_b_modifier_3");
        var title = "Bubbling Spring";
        var description = "Springwater leaves a bubbling area behind, dealing {damage} damage to enemies, for {cloud_duration} sec.";
        var spell = createModifierAlikePassiveSpell();
        spell.school = waterWizardSchool;
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



        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WATER));
    }
    public static final Entry water_spec_a_modifier_4 = add(water_spec_a_modifier_4());
    private static Entry water_spec_a_modifier_4() {
        var id = Identifier.of(NAMESPACE, "water_spec_a_modifier_4");
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
        spell.school = waterWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:aqua_hydro_beam";

        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(),10,1);

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.WATER));
    }
    public static final Entry water_spec_b_modifier_4 = add(water_spec_b_modifier_4());
    private static Entry water_spec_b_modifier_4() {
        var id = Identifier.of(NAMESPACE, "water_spec_b_modifier_4");
        var title = "High Water Pressure";
        var description = "Hydro Beam has {trigger_chance} chance to stun the target.";
        var spell = createModifierAlikePassiveSpell();
        spell.school = waterWizardSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.specificSpellHit("elemental_wizards_rpg:aqua_hydro_beam");
        trigger.chance = 0.2F;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.stun(1.5F);
        spell.impacts = List.of(impact);
        SpellBuilder.Cost.cooldown(spell, 2F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WATER));
    }
    ///WATER PASSIVES
    public static final Entry water_spec_a_passive_1 = add(water_spec_a_passive_1());
    private static Entry water_spec_a_passive_1() {
        var id = Identifier.of(NAMESPACE, "water_spec_a_passive_1");
        var effect = MrpgSkillEffects.HYDRATION;
        var title = "Hydration";
        var description = "Healing spells applies Hydration regenerating health for {effect_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = waterWizardSchool;
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

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WATER));
    }
    public static final Entry water_spec_b_passive_1 = add(water_spec_b_passive_1());
    private static Entry water_spec_b_passive_1() {
        var id = Identifier.of(NAMESPACE, "water_spec_b_passive_1");
        var title = "Second Wave";
        var description = "Water Spells have {trigger_chance} to knock the target back.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = waterWizardSchool;
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

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WATER));
    }
    public static final Entry water_spec_a_passive_2 = add(water_spec_a_passive_2());
    private static Entry water_spec_a_passive_2() {
        var id = Identifier.of(NAMESPACE, "water_spec_a_passive_2");
        var title = "Soothing Mist";
        var description = "{trigger_chance} chance upon rolling to leave Soothing Mist behind for {cloud_duration} sec, cleansing negative conditions.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = waterWizardSchool;
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
        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WATER));
    }
    public static final Entry water_spec_b_passive_2 = add(water_spec_b_passive_2());
    private static Entry water_spec_b_passive_2() {
        var id = Identifier.of(NAMESPACE, "water_spec_b_passive_2");
        var title = "Splashdown";
        var description = "While rolling, you knockback nearby enemies.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = waterWizardSchool;
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

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WATER));
    }
    public static final Entry water_spec_a_passive_3 = add(water_spec_a_passive_3());
    private static Entry water_spec_a_passive_3() {
        var id = Identifier.of(NAMESPACE, "water_spec_a_passive_3");
        var effect = MrpgSkillEffects.CALMING_FLOW;
        var title = "Calming Flow";
        var description = "Water Spell Hits and heals have {trigger_chance_1} chance to enter in a calming flow, reducing active water spell cooldowns while casting spells for a {stash_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = waterWizardSchool;
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

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WATER));
    }
    public static final Entry water_spec_b_passive_3 = add(water_spec_b_passive_3());
    private static Entry water_spec_b_passive_3() {
        var id = Identifier.of(NAMESPACE, "water_spec_b_passive_3");
        var effect = MrpgSkillEffects.TORRENT;
        var title = effect.title;
        var healthThreshold = 0.3F;
        var description = "Falling under {threshold} health, increases water spell power for {bonus2} and spell crit chance & spell haste for {bonus} for {effect_duration} sec.";
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
        spell.school = waterWizardSchool;
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

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.WATER));
    }
    ///BERSERKER MODIFIERS
    public static final Entry berserker_spec_a_modifier_1 = add(berserker_spec_a_modifier_1());
    private static Entry berserker_spec_a_modifier_1() {
        var id = Identifier.of(NAMESPACE, "berserker_spec_a_modifier_1");
        var title = "Enraged";
        var description = "Increases the maximum number of Wild Rage stacks by {effect_amplifier_cap_add}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = berserkerSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "berserker_rpg:wild_rage";
        modifier.effect_amplifier_cap_add = 2;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.BERSERKER));
    }
    public static final Entry berserker_spec_b_modifier_1 = add(berserker_spec_b_modifier_1());
    private static Entry berserker_spec_b_modifier_1() {
        var id = Identifier.of(NAMESPACE, "berserker_spec_b_modifier_1");
        var title = "Blind with Rage";
        var effect = MrpgSkillEffects.BLIND_WITH_RAGE;
        var description = "Melee Hits with Wild Rage reduces incoming damage by {bonus} for {effect_duration} sec.";
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description().replace("{bonus}", bonus);
        };
        var spell = SpellBuilder.createSpellModifier();
        spell.school = berserkerSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "berserker_rpg:wild_rage";

        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(), 10, 0);
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.BERSERKER));
    }
    public static final Entry berserker_spec_a_modifier_2 = add(berserker_spec_a_modifier_2());
    private static Entry berserker_spec_a_modifier_2() {
        var id = Identifier.of(NAMESPACE, "berserker_spec_a_modifier_2");
        var title = "Bloodflow";
        var effect = MrpgSkillEffects.BLOODFLOW;
        var description = "Blood Reckoning increases attack damage by {bonus} for {effect_duration} sec.";
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description().replace("{bonus}", bonus);
        };
        var spell = SpellBuilder.createSpellModifier();
        spell.school = berserkerSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "berserker_rpg:blood_reckoning";

        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(), 8, 0);
        impact.action.apply_to_caster = true;
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.BERSERKER));
    }
    public static final Entry berserker_spec_b_modifier_2 = add(berserker_spec_b_modifier_2());
    private static Entry berserker_spec_b_modifier_2() {
        var id = Identifier.of(NAMESPACE, "berserker_spec_b_modifier_2");
        var title = "Norse Blood Ritual";
        var description = "Bleeding Targets near the caster now receive {damage} damage.";
        var spell = createModifierAlikePassiveSpell();
        spell.school = berserkerSchool;
        spell.range = 6;

        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;

        var trigger = SpellBuilder.Triggers.specificSpellCast("berserker_rpg:blood_reckoning");
        spell.passive.triggers = List.of(trigger);


        var damage = SpellBuilder.Impacts.damage(0.4F, 0F);
        SpellBuilder.configureImpactEnableCondition(damage,
                SpellBuilder.TargetConditions.ofPredicate(HAS_BLEEDING));
        damage.target_modifiers.get(0).execute = TriState.ALLOW;
        spell.impacts = List.of(damage);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.BERSERKER));
    }
    public static final Entry berserker_spec_a_modifier_3 = add(berserker_spec_a_modifier_3());
    private static Entry berserker_spec_a_modifier_3() {
        var id = Identifier.of(NAMESPACE, "berserker_spec_a_modifier_3");
        var title = "Deadly Precision";
        var description = "Bloody Strike deals {power_multiplier} more damage.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = berserkerSchool;
        spell.range = 0;

        var bonus = 0.25F;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "berserker_rpg:bloody_strike";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.power_multiplier = bonus;
        spell.modifiers = List.of(modifier);


        return new Entry(id, spell, title, description, null, EnumSet.of(Category.BERSERKER));
    }
    public static final Entry berserker_spec_b_modifier_3 = add(berserker_spec_b_modifier_3());
    private static Entry berserker_spec_b_modifier_3() {
        var id = Identifier.of(NAMESPACE, "berserker_spec_b_modifier_3");
        var title = "Slicing Maelstorm";
        var description = "Bloody Strike inflicts bleeding around the target for {effect_duration} seconds.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = berserkerSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "berserker_rpg:bloody_strike";

        var radius = 2.5F;

        var area_impact = new Spell.AreaImpact();
        area_impact.execute_action_type = Spell.Impact.Action.Type.STATUS_EFFECT;
        area_impact.radius = radius;
        area_impact.area = new Spell.Target.Area();
        area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        area_impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.dripping_blood.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        20, 0.15F, 0.15F
                )
        };

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;

        var debuff = SpellBuilder.Impacts.effectSet(MRPGCEffects.BLEEDING.id.toString(), 6, 0);
        bleedingDeny(debuff);
        debuff.action.status_effect.amplifier_power_multiplier = 0.2F;
        debuff.action.status_effect.refresh_duration = true;
        modifier.impacts = List.of(debuff);
        modifier.replacing_area_impact = area_impact;

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.BERSERKER));
    }
    public static final Entry berserker_spec_a_modifier_4 = add(berserker_spec_a_modifier_4());
    private static Entry berserker_spec_a_modifier_4() {
        var id = Identifier.of(NAMESPACE, "berserker_spec_a_modifier_4");
        var title = "Savage Outrage";
        var description = "Outrage now deals {damage} damage around the caster.";
        var spell = createModifierAlikePassiveSpell();
        spell.school = berserkerSchool;
        spell.range_mechanic = Spell.RangeMechanic.MELEE;

        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        var trigger = SpellBuilder.Triggers.specificSpellCast("berserker_rpg:outrage");
        spell.passive.triggers = List.of(trigger);

        var damage = SpellBuilder.Impacts.damage(0.3F, 0.2F);
        spell.impacts = List.of(damage);


        return new Entry(id, spell, title, description, null, EnumSet.of(Category.BERSERKER));
    }
    public static final Entry berserker_spec_b_modifier_4 = add(berserker_spec_b_modifier_4());
    private static Entry berserker_spec_b_modifier_4() {
        var id = Identifier.of(NAMESPACE, "berserker_spec_b_modifier_4");
        var title = "Reckless Outrage";
        var description = "Melee Hits grants Absorption in trade for reducing the players health.";
        var effect = MrpgSkillEffects.RECKLESS_RAGE;
        var spell = createModifierAlikePassiveSpell();
        spell.school = berserkerSchool;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = SpellBuilder.Triggers.specificSpellCast("berserker_rpg:outrage");
        spell.passive.triggers = List.of(trigger);

        var stashTrigger = SpellBuilder.Triggers.meleeAttackImpact();
        SpellBuilder.Deliver.stash(spell, effect.id.toString(), 6, stashTrigger);
        spell.deliver.stash_effect.consume = 0;

        var custom = new Spell.Impact();
        custom.action = new Spell.Impact.Action();
        custom.action.custom = new Spell.Impact.Action.Custom();
        custom.action.type = Spell.Impact.Action.Type.CUSTOM;
        custom.action.custom.intent = SpellTarget.Intent.HARMFUL;
        custom.action.custom.handler = "mrpgc_skill_tree:reckless_rage";
        custom.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.STRIPE,
                                SpellEngineParticles.MagicParticles.Motion.ASCEND).id().toString(),
                        ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET,
                        20, 0.2F, 0.25F)
                        .extent(0.25F)
                        .invert()
                        .color(Color.RAGE.toRGBA()),
        };

        var buff = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 12, 1,9);
        buff.action.status_effect.refresh_duration = false;
        buff.action.status_effect.amplifier_cap_power_multiplier = 0.2F;
        buff.action.apply_to_caster = true;

        spell.impacts = List.of(custom,buff);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.BERSERKER));
    }
    ///BERSERKER PASSIVES
    public static final Entry berserker_spec_a_passive_1 = add(berserker_spec_a_passive_1());
    private static Entry berserker_spec_a_passive_1() {
        var id = Identifier.of(NAMESPACE, "berserker_spec_a_passive_1");
        var title = "Cleave";
        var description = "Hitting enemies has {trigger_chance} to stack grievous wounds up to {effect_amplifier_cap} for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = berserkerSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = SpellBuilder.Triggers.meleeAttackImpact();
        trigger.chance = 0.2F;
        spell.passive.triggers = List.of(trigger);

        var debuff = SpellBuilder.Impacts.effectAdd(MRPGCEffects.GRIEVOUS_WOUNDS.id.toString(), 6, 0,3);
        debuff.action.status_effect.refresh_duration = true;
        debuff.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.dripping_blood.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.01F, 0.1F)
                        .color(Color.BLOOD.toRGBA()),
                SpellBuilder.Particles.aura(SpellEngineParticles.aura_effect_409.id())
                        .color(Color.RAGE.toRGBA())
        };
        debuff.sound = new Sound(MrpgSkillSounds.cleave_impact.id());
        spell.impacts = List.of(debuff);

        SpellBuilder.Cost.cooldown(spell, 1F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.BERSERKER));
    }
    public static final Entry berserker_spec_b_passive_1 = add(berserker_spec_b_passive_1());
    private static Entry berserker_spec_b_passive_1() {
        var id = Identifier.of(NAMESPACE, "berserker_spec_b_passive_1");
        var title = "Bloodfrenzy";
        var description = "Melee Hits have a {trigger_chance} to inflict bleeding and healing yourself for {heal} hearts.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = berserkerSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.meleeAttackImpact();
        trigger.chance = 0.2F;
        spell.passive.triggers = List.of(trigger);

        var debuff = SpellBuilder.Impacts.effectAdd(MRPGCEffects.BLEEDING.id.toString(), 6, 0,5);
        bleedingDeny(debuff);
        debuff.action.status_effect.refresh_duration = true;
        debuff.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.dripping_blood.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.01F, 0.1F)
                        .color(Color.BLOOD.toRGBA()),
        };

        var heal = SpellBuilder.Impacts.heal(0.1F);
        heal.action.apply_to_caster = true;
        heal.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                        SpellEngineParticles.MagicParticles.Shape.HEAL,
                                        SpellEngineParticles.MagicParticles.Motion.DECELERATE).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.01F, 0.1F)
                        .color(Color.RAGE.toRGBA()),
                new ParticleBatch(
                        SpellEngineParticles.ground_glow.id().toString(),
                        ParticleBatch.Shape.LINE_VERTICAL, ParticleBatch.Origin.GROUND,
                        1, 0.0F, 0.F)
                        .followEntity(true)
                        .scale(1.2F)
                        .color(Color.RAGE.alpha(0.35F).toRGBA())
        };
        heal.sound = new Sound(MrpgSkillSounds.blood_frenzy_heal.id());
        spell.impacts = List.of(debuff,heal);

        SpellBuilder.Cost.cooldown(spell, 2F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.BERSERKER));
    }
    public static final Entry berserker_spec_a_passive_2 = add(berserker_spec_a_passive_2());
    private static Entry berserker_spec_a_passive_2() {
        var id = Identifier.of(NAMESPACE, "berserker_spec_a_passive_2");
        var title = "Spinning Slash";
        var description = "While rolling, you slash and deal {damage} damage to nearby enemies.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = berserkerSchool;
        spell.range_mechanic = Spell.RangeMechanic.MELEE;
        spell.range = 1.0F;

        var trigger = SpellBuilder.Triggers.roll();
        spell.passive.triggers = List.of(trigger);

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var stashEffect = MrpgSkillEffects.SPINNING_SLASH;
        var stashTrigger = SpellBuilder.Triggers.effectTick(stashEffect.id.toString());
        SpellBuilder.Deliver.stash(spell, stashEffect.id.toString(), 0.7F, List.of(stashTrigger));
        spell.deliver.stash_effect.consume = 0;

        var impact = SpellBuilder.Impacts.damage(0.5F, 0F);
        spell.impacts = List.of(impact);
        var areaImpact = new Spell.AreaImpact();
        areaImpact.radius = 2F;
        areaImpact.force_indirect = true;
        areaImpact.sound = new Sound(MrpgSkillSounds.spinning_slash_impact.id());
        spell.area_impact = areaImpact;

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.BERSERKER));
    }
    public static final Entry berserker_spec_b_passive_2 = add(berserker_spec_b_passive_2());
    private static Entry berserker_spec_b_passive_2() {
        var id = Identifier.of(NAMESPACE, "berserker_spec_b_passive_2");
        var title = "Burst of Aggression";
        var effect = MrpgSkillEffects.BURST_OF_AGGRESSION;
        var description = "When the player is in rage and is rolling, you gain {bonus} movement speed and rage for {effect_duration} sec.";
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description()
                    .replace("{bonus}", bonus);
        };

        var spell = SpellBuilder.createSpellPassive();
        spell.school = berserkerSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        var condition = new Spell.TargetCondition();
        condition.entity_predicate_id = HAS_RAGE.id().toString();
        trigger.target_conditions = List.of(condition);
        spell.passive.triggers = List.of(trigger);

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var buff = SpellBuilder.Impacts.effectSet(effect.id.toString(), 5F, 0);
        buff.action.status_effect.refresh_duration = true;
        buff.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.STRIPE,
                                SpellEngineParticles.MagicParticles.Motion.DECELERATE).id().toString(),
                        ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.CENTER,
                        25, 0.3F, 0.8F).extent(1.0F)
                        .color(Color.RAGE.toRGBA()).followEntity(true),
                SpellBuilder.Particles.area(SpellEngineParticles.area_effect_658.id())
                        .origin(ParticleBatch.Origin.CENTER)
                        .scale(1.5F)
                        .color(Color.RAGE.toRGBA()).followEntity(true)
        };
        buff.sound = new Sound(MrpgSkillSounds.burst_of_aggression.id());
        spell.impacts = List.of(buff);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.BERSERKER));
    }
    public static final Entry berserker_spec_a_passive_3 = add(berserker_spec_a_passive_3());
    private static Entry berserker_spec_a_passive_3() {
        var id = Identifier.of(NAMESPACE, "berserker_spec_a_passive_3");
        var effect = MrpgSkillEffects.RAGNAROK;
        var title = effect.title;
        var description = "Taking damage with a harmful effect grants you immunity to harmful effects and {bonus} increased movement speed for {effect_duration} sec.";
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description()
                    .replace("{bonus}", bonus);
        };

        var spell = SpellBuilder.createSpellPassive();
        spell.school = berserkerSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.damageTaken();
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        var condition = new Spell.TargetCondition();
        condition.entity_predicate_id = SpellEntityPredicates.HAS_BAD_EFFECT.id().toString();
        trigger.caster_conditions = List.of(condition);
        spell.passive.triggers = List.of(trigger);

        var buff = SpellBuilder.Impacts.effectSet(effect.id.toString(), 5, 0);
        buff.action.apply_to_caster = true;
        buff.particles = new ParticleBatch[]{
                SpellBuilder.Particles.aura(SpellEngineParticles.aura_effect_728.id())
                        .scale(1.2F)
                        .color(Color.RAGE.alpha(0.5F).toRGBA()),
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.STRIPE,
                                SpellEngineParticles.MagicParticles.Motion.FLOAT).id().toString(),
                        ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET,
                        25, 0.2F, 0.6F)
                        .extent(-0.2F)
                        .color(Color.RAGE.toRGBA()),
        };
        buff.sound = new Sound(MrpgSkillSounds.ragnarok_release.id());
        spell.impacts = List.of(buff);

        SpellBuilder.Cost.cooldown(spell, 60F);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.BERSERKER));
    }
    public static final Entry berserker_spec_b_passive_3 = add(berserker_spec_b_passive_3());
    private static Entry berserker_spec_b_passive_3() {
        var id = Identifier.of(NAMESPACE, "berserker_spec_b_passive_3");
        var effect = MrpgSkillEffects.UNDYING_RAGE;
        var title = effect.title;
        var description = "When taking damage that would be fatal, you become invulnerable for {effect_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = berserkerSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.damageIncomingFatal();
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        var buff = SpellBuilder.Impacts.effectSet(effect.id.toString(), 3, 0);
        buff.action.apply_to_caster = true;
        buff.particles = new ParticleBatch[]{
                SpellBuilder.Particles.area(SpellEngineParticles.area_effect_658.id())
                        .origin(ParticleBatch.Origin.CENTER)
                        .scale(1.5F)
                        .color(Color.RAGE.toRGBA()),
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.STRIPE,
                                SpellEngineParticles.MagicParticles.Motion.DECELERATE).id().toString(),
                        ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.CENTER,
                        15, 0.3F, 0.5F)
                        .invert()
                        .color(Color.RAGE.toRGBA()),
                new ParticleBatch("berserker_rpg:rage_particle",
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.1F, 0.5F)
                        .preSpawnTravel(7)
        };
        buff.sound = Sound.withVolume(Identifier.of("berserker_rpg:wild_rage"), 1.3F);
        spell.impacts = List.of(buff);

        SpellBuilder.Cost.cooldown(spell, 60F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.BERSERKER));
    }
    ///FORCEMASTER MODIFIERS
    public static final Entry forcemaster_spec_a_modifier_1 = add(forcemaster_spec_a_modifier_1());
    private static Entry forcemaster_spec_a_modifier_1() {
        var id = Identifier.of(NAMESPACE, "forcemaster_spec_a_modifier_1");
        var title = "Extended Stonehand";
        var description = "Increases the amplifier of Stonehand by {stash_amplifier_add}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = forcemasterFighterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:stonehand";
        modifier.stash_amplifier_add = 1;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.FORCEMASTER));
    }
    public static final Entry forcemaster_spec_b_modifier_1 = add(forcemaster_spec_b_modifier_1());
    private static Entry forcemaster_spec_b_modifier_1() {
        var id = Identifier.of(NAMESPACE, "forcemaster_spec_b_modifier_1");
        var title = "Arcane Fist";
        var description = "Casting Stonehand deals {damage} damage around the caster.";
        var spell = createModifierAlikePassiveSpell();
        spell.school = forcemasterCasterSchool;
        spell.range = 5;

        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;

        spell.release.particles =  new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.SPARK,
                                SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET,
                        15, 0.1F, 0.7F)
                        .color(Color.ARCANE.toRGBA()),
        };
        var trigger = SpellBuilder.Triggers.specificSpellCast("forcemaster_rpg:stonehand");
        spell.passive.triggers = List.of(trigger);

        var damage = SpellBuilder.Impacts.damage(0.5F, 0.1F);
        damage.particles =  new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.ARCANE,
                                SpellEngineParticles.MagicParticles.Motion.DECELERATE).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        15, 0.1F, 0.4F)
                        .color(Color.ARCANE.toRGBA()),
        };
        spell.impacts = List.of(damage);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.FORCEMASTER));
    }
    public static final Entry forcemaster_spec_a_modifier_2 = add(forcemaster_spec_a_modifier_2());
    private static Entry forcemaster_spec_a_modifier_2() {
        var id = Identifier.of(NAMESPACE, "forcemaster_spec_a_modifier_2");
        var title = "Pumped Up";
        var description = "Burstcrack increases your attack damage by {bonus} for {effect_duration} seconds.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = forcemasterFighterSchool;
        var effect = MrpgSkillEffects.PUMPED_UP;
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description()
                    .replace("{bonus}", bonus);
        };

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:burstcrack";
        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(),8,0);
        impact.action.apply_to_caster = true;

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.FORCEMASTER));
    }
    public static final Entry forcemaster_spec_b_modifier_2 = add(forcemaster_spec_b_modifier_2());
    private static Entry forcemaster_spec_b_modifier_2() {
        var id = Identifier.of(NAMESPACE, "forcemaster_spec_b_modifier_2");
        var title = "Powerful Burst";
        var description = "Burst Crack deals {critical_chance_bonus} critical chance bonus.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = forcemasterCasterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:burstcrack";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.critical_chance_bonus = 0.1F;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.FORCEMASTER));
    }
    public static final Entry forcemaster_spec_a_modifier_3 = add(forcemaster_spec_a_modifier_3());
    private static Entry forcemaster_spec_a_modifier_3() {
        var id = Identifier.of(NAMESPACE, "forcemaster_spec_a_modifier_3");
        var title = "Powerful Belial Smashing";
        var description = "Belial Smashing has {trigger_chance} chance to knock up the target.";
        var spell = createModifierAlikePassiveSpell();
        spell.school = forcemasterFighterSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.specificSpellHit("forcemaster_rpg:belial_smashing");
        trigger.chance = 0.25F;
        spell.passive.triggers = List.of(trigger);

        var custom = new Spell.Impact();
        custom.action = new Spell.Impact.Action();
        custom.action.custom = new Spell.Impact.Action.Custom();
        custom.action.type = Spell.Impact.Action.Type.CUSTOM;
        custom.action.custom.intent = SpellTarget.Intent.HARMFUL;
        custom.action.custom.handler = "more_rpg_classes:knock_up_fixed";
        custom.particles = new ParticleBatch[]{
                new ParticleBatch(
                SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.FEET,
                        20, 0.1F, 0.3F)
                        .extent(0.25F)
                        .color(Color.WHITE.toRGBA()),
        };

        spell.impacts = List.of(custom);
        SpellBuilder.Cost.cooldown(spell, 0.5F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.FORCEMASTER));
    }
    public static final Entry forcemaster_spec_b_modifier_3 = add(forcemaster_spec_b_modifier_3());
    private static Entry forcemaster_spec_b_modifier_3() {
        var id = Identifier.of(NAMESPACE, "forcemaster_spec_b_modifier_3");
        var title = "Explosive Belial Smashing";
        var description = "Belial Smashing has {trigger_chance} chance create a arcane explosion, dealing {damage} damage.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = forcemasterCasterSchool;

        var radius = 5F;
        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:belial_smashing";
        var impact = SpellBuilder.Impacts.damage(0.7F, 0.5F);

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var area_impact = new Spell.AreaImpact();
        area_impact.execute_action_type = Spell.Impact.Action.Type.DAMAGE;
        area_impact.radius = radius;
        area_impact.area = new Spell.Target.Area();
        area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        area_impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.ARCANE,
                                SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        25, 0.35F, 0.35F
                ).color(Color.ARCANE.toRGBA()),
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.SPELL,
                                SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        25, 0.5F, 0.5F
                ).color(Color.ARCANE.toRGBA())
        };

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);
        modifier.replacing_area_impact = area_impact;

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.FORCEMASTER));
    }
    public static final Entry forcemaster_spec_a_modifier_4 = add(forcemaster_spec_a_modifier_4());
    private static Entry forcemaster_spec_a_modifier_4() {
        var id = Identifier.of(NAMESPACE, "forcemaster_spec_a_modifier_4");
        var title = "Powerful Asalraalaikum";
        var description = " Asalraalaikum damage increased by {power_multiplier}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = forcemasterFighterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:asal";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.power_multiplier = 0.3F;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.FORCEMASTER));
    }
    public static final Entry forcemaster_spec_b_modifier_4 = add(forcemaster_spec_b_modifier_4());
    private static Entry forcemaster_spec_b_modifier_4() {
        var id = Identifier.of(NAMESPACE, "forcemaster_spec_b_modifier_4");
        var title = "Arcane Regeneration";
        var description = "Reduces the cooldown of Asalraalaikum  by {cooldown_duration_deduct} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = forcemasterCasterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:asal";
        modifier.cooldown_duration_deduct = 8;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.FORCEMASTER));
    }
    ///FORCEMASTER PASSIVES
    public static final Entry forcemaster_spec_a_passive_1 = add(forcemaster_spec_a_passive_1());
    private static Entry forcemaster_spec_a_passive_1() {
        var id = Identifier.of(NAMESPACE, "forcemaster_spec_a_passive_1");
        var title = "Blood Fists";
        var description = "Your melee hits have {trigger_chance} chance, to stack bleeding on the target for {effect_duration} sec.";
        var effect = MRPGCEffects.BLEEDING;

        var spell = SpellBuilder.createSpellPassive();
        spell.school = forcemasterFighterSchool;
        spell.range = 0;
        
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.meleeAttackImpact();
        trigger.chance = 0.4F;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 7F, 0,6);
        bleedingDeny(impact);
        impact.action.status_effect.refresh_duration = true;
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.dripping_blood.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        20, 0.15F, 0.15F
                )
        };
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 5F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.FORCEMASTER));
    }
    public static final Entry forcemaster_spec_b_passive_1 = add(forcemaster_spec_b_passive_1());
    private static Entry forcemaster_spec_b_passive_1() {
        var id = Identifier.of(NAMESPACE, "forcemaster_spec_b_passive_1");
        var title = "Force Release";
        var description = "Arcane spell impacts have {trigger_chance} chance, to increase spell haste & spell crit chance by {bonus} for {effect_duration} sec. The effect can be stacked {effect_amplifier_cap} times.";
        var effect = MrpgSkillEffects.FORCE_RELEASE;
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description()
                    .replace("{bonus}", bonus);
        };

        var spell = SpellBuilder.createSpellPassive();
        spell.school = forcemasterCasterSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.activeSpellHit(0.25F,"arcane");
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        trigger.chance = 0.25F;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 10F, 1,5);
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.area_circle_1.id().toString(),
                        ParticleBatch.Shape.LINE_VERTICAL, ParticleBatch.Origin.FEET,
                        1, 0.2F, 0.2F)
                        .followEntity(true)
                        .scale(0.8F)
                        .maxAge(0.8F)
                        .color(Color.ARCANE.toRGBA()),
        };
        impact.sound = new Sound(SkillTreeSounds.arcane_radiance.id());
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 1F);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.FORCEMASTER));
    }
    public static final Entry forcemaster_spec_a_passive_2 = add(forcemaster_spec_a_passive_2());
    private static Entry forcemaster_spec_a_passive_2() {
        var id = Identifier.of(NAMESPACE, "forcemaster_spec_a_passive_2");
        var title = "Flying Fists";
        var description = "Upon rolling, attack speed gets increased by {bonus} for {effect_duration} sec.";
        var effect = MrpgSkillEffects.FLYING_FISTS;
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description()
                    .replace("{bonus}", bonus);
        };


        var spell = SpellBuilder.createSpellPassive();
        spell.school = forcemasterFighterSchool;
        spell.range = 0;

        spell.passive.triggers = List.of(SpellBuilder.Triggers.roll());

        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 5, 0, 0);
        impact.sound = new Sound(MrpgSkillSounds.flying_fists.id());
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.STRIPE,
                                SpellEngineParticles.MagicParticles.Motion.FLOAT).id().toString(),
                        ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET,
                        20, 0.05F, 0.1F)
                        .color(MIGHT_COLOR.toRGBA()),
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_fist.id(), MIGHT_COLOR)
        };
        spell.impacts = List.of(impact);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.FORCEMASTER));
    }
    public static final Entry forcemaster_spec_b_passive_2 = add(forcemaster_spec_b_passive_2());
    private static Entry forcemaster_spec_b_passive_2() {
        var id = Identifier.of(NAMESPACE, "forcemaster_spec_b_passive_2");
        var title = "Calm Mind";
        var description = "Upon rolling, {trigger_chance} chance to slightly reduce active arcane cooldowns.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = forcemasterCasterSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        trigger.chance = 0.5F;
        spell.passive.triggers = List.of(trigger);

        Spell.Impact impact = new Spell.Impact();
        impact.sound = new Sound(MrpgSkillSounds.calm_mind.id());
        impact.action = new Spell.Impact.Action();
        impact.action.type = net.spell_engine.api.spell.Spell.Impact.Action.Type.COOLDOWN;
        impact.action.cooldown = new Spell.Impact.Action.Cooldown();
        impact.action.cooldown.actives = new Spell.Impact.Action.Cooldown.Modify();
        impact.action.cooldown.actives.school = "arcane";
        impact.action.cooldown.actives.duration_multiplier = 0.75F;
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.STRIPE,
                                SpellEngineParticles.MagicParticles.Motion.FLOAT).id().toString(),
                        ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET,
                        20, 0.05F, 0.1F)
                        .color(Color.ARCANE.toRGBA()),
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_hourglass.id(), Color.ARCANE)
        };
        spell.impacts = List.of(impact);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.FORCEMASTER));
    }
    public static final Entry forcemaster_spec_a_passive_3 = add(forcemaster_spec_a_passive_3());
    private static Entry forcemaster_spec_a_passive_3() {
        var id = Identifier.of(NAMESPACE, "forcemaster_spec_a_passive_3");
        var title = "Sury's Tenacity";
        final var healthThreshold = 0.3F;
        var description = "When taking damage below {threshold}, attack & movement speed gets increased by {bonus} and the caster is immune to harmful effects for {effect_duration} sec.";
        var effect = MrpgSkillEffects.SURYS_TENACITY;
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var threshold = SpellTooltip.percent(healthThreshold);
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description()
                    .replace("{bonus}", bonus)
                    .replace("{threshold}", threshold);
        };

        var spell = SpellBuilder.createSpellPassive();
        spell.school = forcemasterFighterSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.becomingLowHP(healthThreshold);
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 5, 0, 0);
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.ARCANE,
                                SpellEngineParticles.MagicParticles.Motion.DECELERATE
                        ).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        30, 0.5F, 0.5F)
                        .color(MIGHT_COLOR.toRGBA()),
                new ParticleBatch(
                        SpellEngineParticles.aura_effect_642.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        1, 0, 0)
                        .color(MIGHT_COLOR.toRGBA()),
        };
        impact.sound = new Sound(MrpgSkillSounds.surys_tenacity.id());
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell,40);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.FORCEMASTER));
    }
    public static final Entry forcemaster_spec_b_passive_3 = add(forcemaster_spec_b_passive_3());
    private static Entry forcemaster_spec_b_passive_3() {
        var id = Identifier.of(NAMESPACE, "forcemaster_spec_b_passive_3");
        var title = "Sury's Grace";
        var description = "Casting Forcemaster Spells has a {trigger_chance} to increase arcane spell power & spell haste by {bonus} for {effect_duration} sec.";
        var effect = MrpgSkillEffects.SURYS_GRACE;
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description()
                    .replace("{bonus}", bonus);
        };

        var spell = SpellBuilder.createSpellPassive();
        spell.school = forcemasterCasterSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.activeSpellCast(forcemasterCasterSchool);
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 10, 0, 0);
        impact.sound = new Sound(MrpgSkillSounds.surys_grace.id());
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.ARCANE,
                                SpellEngineParticles.MagicParticles.Motion.DECELERATE
                        ).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        30, 0.5F, 0.5F)
                        .color(Color.from(SpellSchools.ARCANE.color).toRGBA()),
                new ParticleBatch(
                        SpellEngineParticles.aura_effect_642.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        1, 0, 0)
                        .color(Color.from(SpellSchools.ARCANE.color).toRGBA()),
        };
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell,60);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.FORCEMASTER));
    }
    ///WAR ARCHER MODIFIERS
    public static final Entry war_archer_spec_a_modifier_1 = add(war_archer_spec_a_modifier_1());
    private static Entry war_archer_spec_a_modifier_1() {
        var id = Identifier.of(NAMESPACE, "war_archer_spec_a_modifier_1");
        var title = "Expanded Smoldering Arrow";

        var bonus = 0.5F;

        var description = "Increases the area of effect of Smoldering Arrow by {bonus}.";
        var mutator = new SpellTooltip.DescriptionMutator() {
            @Override
            public String mutate(Args args) {
                return args.description().replace("{bonus}", SpellTooltip.percent(bonus));
            }
        };
        var spell = SpellBuilder.createSpellModifier();
        spell.school = warArcherSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:smoldering_arrow";
        var extendedRadius = 2.0F * (1F + bonus);
        modifier.replacing_area_impact = new Spell.AreaImpact();
        Spell.AreaImpact area_impact = new Spell.AreaImpact();
        area_impact.radius = extendedRadius;
        area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        area_impact.particles = new ParticleBatch[]{(new ParticleBatch("spell_engine:fire_explosion",
                ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, 1.0F, 0.0F, 0.0F)).scale(extendedRadius/2),
               new ParticleBatch("spell_engine:flame_medium_b",
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, 25.0F, 0.1F, 0.3F).preSpawnTravel(2),
                new ParticleBatch("spell_engine:flame_medium_b",
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, 25.0F, 0.2F, 0.4F).preSpawnTravel(4)
        };
        modifier.replacing_area_impact.sound = new Sound("entity.generic.explode");

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.WAR_ARCHER));
    }
    public static final Entry war_archer_spec_b_modifier_1 = add(war_archer_spec_b_modifier_1());
    private static Entry war_archer_spec_b_modifier_1() {
        var id = Identifier.of(NAMESPACE, "war_archer_spec_b_modifier_1");
        var title = "Explosive Push";
        var description = "Increases the knockback of Smoldering Arrow by {knockback_multiply_base}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = warArcherSchool;

        var bonus = 0.5F;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:smoldering_arrow";
        modifier.knockback_multiply_base = bonus;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WAR_ARCHER));
    }
    public static final Entry war_archer_spec_a_modifier_2 = add(war_archer_spec_a_modifier_2());
    private static Entry war_archer_spec_a_modifier_2() {
        var id = Identifier.of(NAMESPACE, "war_archer_spec_a_modifier_2");
        var title = "Flaming Double Shot";
        var description = "Double Shot deals {power_multiplier} more damage and lights enemies on fire.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = warArcherSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:dual_shot";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.power_multiplier = 0.1F;

        var impact = SpellBuilder.Impacts.fire(2F);
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.flame_medium_a.id().toString(),
                        ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET,
                        1, 0.1F, 0.2F),
                new ParticleBatch(
                        SpellEngineParticles.flame_medium_b.id().toString(),
                        ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET,
                        1, 0.1F, 0.2F)
        };
        impact.sound = Sound.withVolume(SpellEngineSounds.GENERIC_FIRE_IGNITE.id(), 0.6F);
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WAR_ARCHER));
    }
    public static final Entry war_archer_spec_b_modifier_2 = add(war_archer_spec_b_modifier_2());
    private static Entry war_archer_spec_b_modifier_2() {
        var id = Identifier.of(NAMESPACE, "war_archer_spec_b_modifier_2");
        var title = "Heavy Arrow Tips";
        var description = "Increases the knockback of Double Shot by {knockback_multiply_base}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = warArcherSchool;

        var bonus = 1.0F;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:dual_shot";
        modifier.knockback_multiply_base = bonus;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WAR_ARCHER));
    }
    public static final Entry war_archer_spec_a_modifier_3 = add(war_archer_spec_a_modifier_3());
    private static Entry war_archer_spec_a_modifier_3() {
        var id = Identifier.of(NAMESPACE, "war_archer_spec_a_modifier_3");
        var title = "Explosive Point Blank Shot";
        var description = "Damaging with Point Blank Shot causes small explosion, hitting enemies within {impact_range} blocks radius, dealing extra {damage} damage.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = warArcherSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:point_blank_shot";
        var impact = SpellBuilder.Impacts.damage(0.5F, 0.0F);
        impact.action.allow_on_center_target = false;

        var radius = 2F;

        var area_impact = new Spell.AreaImpact();
        area_impact.execute_action_type = Spell.Impact.Action.Type.DAMAGE;
        area_impact.sound = new Sound("entity.generic.explode");
        area_impact.radius = radius;
        area_impact.area = new Spell.Target.Area();
        area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        area_impact.particles = new ParticleBatch[]{(new ParticleBatch("spell_engine:fire_explosion",
                ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, 1.0F, 0.0F, 0.0F)).scale(2),
                new ParticleBatch("spell_engine:flame_medium_b",
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, 25.0F, 0.1F, 0.3F).preSpawnTravel(2).extent(2),
                new ParticleBatch("spell_engine:flame_medium_b",
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, 25.0F, 0.2F, 0.4F).preSpawnTravel(4).extent(4)
        };

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);
        modifier.replacing_area_impact = area_impact;

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WAR_ARCHER));
    }
    public static final Entry war_archer_spec_b_modifier_3 = add(war_archer_spec_b_modifier_3());
    private static Entry war_archer_spec_b_modifier_3() {
        var id = Identifier.of(NAMESPACE, "war_archer_spec_b_modifier_3");
        var title = "Heavy Point Blank Shot";
        var description = "Point Blank Shot has {trigger_chance} chance to stun the target.";
        var spell = createModifierAlikePassiveSpell();
        spell.school = warArcherSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.specificSpellHit("archers_expansion:point_blank_shot");
        trigger.chance = 0.2F;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.stun(3F);
        spell.impacts = List.of(impact);
        SpellBuilder.Cost.cooldown(spell, 1F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WAR_ARCHER));
    }
    public static final Entry war_archer_spec_a_modifier_4 = add(war_archer_spec_a_modifier_4());
    private static Entry war_archer_spec_a_modifier_4() {
        var id = Identifier.of(NAMESPACE, "war_archer_spec_a_modifier_4");
        var title = "Combustive Shot";
        var description = "Pin Down leaves a burning area behind, dealing {damage} damage to enemies, for {cloud_duration} sec.";
        var spell = createModifierAlikePassiveSpell();
        spell.school = warArcherSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.specificSpellCast("archers_expansion:pin_down");
        spell.passive.triggers = List.of(trigger);

        SpellBuilder.Complex.flameCloud(spell, 3.5F, 0.25F, 3, null);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WAR_ARCHER));
    }
    public static final Entry war_archer_spec_b_modifier_4 = add(war_archer_spec_b_modifier_4());
    private static Entry war_archer_spec_b_modifier_4() {
        var id = Identifier.of(NAMESPACE, "war_archer_spec_b_modifier_4");
        var title = "Increased Pin Down";
        var description = "Increases the effect duration of Pin Down by {effect_duration_add}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = warArcherSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:pin_down";
        modifier.effect_duration_add = 3;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WAR_ARCHER));
    }
    ///WAR ARCHER PASSIVES
    public static final Entry war_archer_spec_a_passive_1 = add(war_archer_spec_a_passive_1());
    private static Entry war_archer_spec_a_passive_1() {
        var id = Identifier.of(NAMESPACE, "war_archer_spec_a_passive_1");
        var title = "Bombardment";
        var description = "If the target is on fire, create a explosion dealing {damage} damage.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = warArcherSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.arrowHit();
        var condition = new Spell.TargetCondition();
        condition.entity_predicate_id = SpellEntityPredicates.IS_ON_FIRE.id().toString();
        trigger.target_conditions = List.of(condition);
        spell.passive.triggers = List.of(trigger);


        var impact = SpellBuilder.Impacts.damage(0.35F, 0.75F);
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.flame_medium_a.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.1F, 0.5F),
                new ParticleBatch(
                        SpellEngineParticles.flame_medium_b.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.1F, 0.5F)
        };
        impact.sound = new Sound("entity.generic.explode");
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 5F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WAR_ARCHER));
    }
    public static final Entry war_archer_spec_b_passive_1 = add(war_archer_spec_b_passive_1());
    private static Entry war_archer_spec_b_passive_1() {
        var id = Identifier.of(NAMESPACE, "war_archer_spec_b_passive_1");
        var title = "Tower's Watch";
        var description = "Your arrow hits have {trigger_chance} to increase your armor and knockback resistance for {effect_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = warArcherSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = SpellBuilder.Triggers.arrowHit();
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        trigger.chance = 0.35F;
        spell.passive.triggers = List.of(trigger);


        var impact = SpellBuilder.Impacts.effectAdd(MrpgSkillEffects.TOWER_PROTECTOR.id.toString(), 8,1,5);
        impact.particles = new ParticleBatch[]{
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_shield.id(), Color.RED),
        };
        impact.sound = new Sound(MrpgSkillSounds.protector_of_the_tower.id());
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 1F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WAR_ARCHER));
    }
    public static final Entry war_archer_spec_a_passive_2 = add(war_archer_spec_a_passive_2());
    private static Entry war_archer_spec_a_passive_2() {
        var id = Identifier.of(NAMESPACE, "war_archer_spec_a_passive_2");
        var title = "Reloading";
        var description = "When rolling you recharge Smoldering Arrows, you can now stack up to {effect_amplifier_cap} times.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = warArcherSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectAdd("archers_expansion:smoldering_arrows", 10, 1,4);
        impact.action.status_effect.refresh_duration = true;
        impact.particles = new ParticleBatch[]{
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_arrow.id(), Color.RED),
        };
        impact.sound = new Sound(SpellEngineSounds.GENERIC_FIRE_IGNITE.id());
        spell.impacts = List.of(impact);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WAR_ARCHER));
    }
    public static final Entry war_archer_spec_b_passive_2 = add(war_archer_spec_b_passive_2());
    private static Entry war_archer_spec_b_passive_2() {
        var id = Identifier.of(NAMESPACE, "war_archer_spec_b_passive_2");
        var title = "Reposition";
        var description = "Rolling has {trigger_chance} chance to clear 1 negative effect.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = warArcherSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        trigger.chance = 0.5F;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectCleanse();
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.SPARK,
                                SpellEngineParticles.MagicParticles.Motion.ASCEND).id().toString(),
                        ParticleBatch.Shape.PIPE, ParticleBatch.Origin.CENTER,
                        20, 0.2F, 0.4F)
                        .color(Color.RED.toRGBA())
        };
        impact.sound = new Sound(SpellEngineSounds.GENERIC_DISPEL_1.id());
        spell.impacts = List.of(impact);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WAR_ARCHER));
    }
    public static Entry war_archer_spec_a_passive_3 = add(war_archer_spec_a_passive_3());
    private static Entry war_archer_spec_a_passive_3() {
        var id = Identifier.of(NAMESPACE, "war_archer_spec_a_passive_3");
        var title = "Rapid Fire";
        var description = "{trigger_chance} chance to shoot 4 additional arrows.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school =warArcherSchool;

        var trigger = new Spell.Trigger();
        trigger.type = Spell.Trigger.Type.ARROW_SHOT;
        trigger.chance = 0.2F;
        spell.passive.triggers = List.of(trigger);

        spell.release.particles = new ParticleBatch[]{
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_arrow.id(), Color.RED),
        };

        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();

        spell.deliver.type = Spell.Delivery.Type.SHOOT_ARROW;
        spell.deliver.shoot_arrow = new Spell.Delivery.ShootArrow();
        spell.deliver.shoot_arrow.launch_properties.velocity = 3.35F;
        spell.deliver.delay = 3;
        spell.deliver.shoot_arrow.launch_properties.extra_launch_count = 3;

        spell.arrow_perks = new Spell.ArrowPerks();
        spell.arrow_perks.damage_multiplier = 0.75F;
        spell.arrow_perks.bypass_iframes = true;
        spell.arrow_perks.knockback = 0.5F;

        SpellBuilder.Cost.cooldown(spell, 20);

        return new Entry(id, spell, title, description, null, Category.WAR_ARCHER);
    }
    public static final Entry war_archer_spec_b_passive_3 = add(war_archer_spec_b_passive_3());
    private static Entry war_archer_spec_b_passive_3() {
        var id = Identifier.of(NAMESPACE, "war_archer_spec_b_passive_3");
        var title = "Last Stand";
        final var healthThreshold = 0.3F;
        var description = "Upon taking damage below {threshold} health you gain Last Stand effect, increasing your size, ranged haste & decreasing incoming damage for {effect_duration} sec.";
        var effect = MrpgSkillEffects.LAST_STAND;
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var threshold = SpellTooltip.percent(healthThreshold);
            return args.description()
                    .replace("{threshold}", threshold);
        };

        var spell = SpellBuilder.createSpellPassive();
        spell.school = warArcherSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.becomingLowHP(healthThreshold);
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        var buff = SpellBuilder.Impacts.effectSet(effect.id.toString(), 7, 0);
        buff.particles = new ParticleBatch[]{
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_shield.id(), Color.RED),
        };
        buff.sound = new Sound(MrpgSkillSounds.last_stand.id());
        spell.impacts = List.of(buff);

        SpellBuilder.Cost.cooldown(spell, 30F);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.WAR_ARCHER));
    }
    ///DEADEYE MODIFIERS
    public static final Entry deadeye_spec_a_modifier_1 = add(deadeye_spec_a_modifier_1());
    private static Entry deadeye_spec_a_modifier_1() {
        var id = Identifier.of(NAMESPACE, "deadeye_spec_a_modifier_1");
        var title = "Poisonous Sting";
        var description = "Fast Shot has {impact_chance} chance to apply stacking poison, lasting {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = deadeyeSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:fast_shot";
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.PREPEND;

        var debuff = SpellBuilder.Impacts.effectAdd(StatusEffects.POISON.getIdAsString(), 8, 1, 1);
        debuff.action.status_effect.amplifier_cap_power_multiplier = 0.5F;
        debuff.chance = 0.4F;
        debuff.action.status_effect.refresh_duration = true;
        debuff.particles = new ParticleBatch[]{(new ParticleBatch(
                SpellEngineParticles.MagicParticles.get(
                        SpellEngineParticles.MagicParticles.Shape.SPARK,
                        SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                10, 0.5F, 0.8F)
                .color(Color.POISON_MID.toRGBA())),
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.SKULL,
                                SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.5F, 0.8F)
                        .color(Color.POISON_DARK.toRGBA()),
        };
        modifier.impacts = List.of(debuff);

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.DEADEYE));
    }
    public static final Entry deadeye_spec_b_modifier_1 = add(deadeye_spec_b_modifier_1());
    private static Entry deadeye_spec_b_modifier_1() {
        var id = Identifier.of(NAMESPACE, "deadeye_spec_b_modifier_1");
        var title = "Fast Hands";
        var description = "Fast Shot applies {effect_amplifier_cap_add} additional Fast Shot stack.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = deadeyeSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:fast_shot";
        modifier.effect_amplifier_cap_add = 1;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.DEADEYE));
    }
    public static final Entry deadeye_spec_a_modifier_2 = add(deadeye_spec_a_modifier_2());
    private static Entry deadeye_spec_a_modifier_2() {
        var id = Identifier.of(NAMESPACE, "deadeye_spec_a_modifier_2");
        var title = "Barbed Trick Arrows";
        var description = "Trick Shot's bleeding effect lasts {effect_duration_add} sec longer.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = deadeyeSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:trick_shot";
        modifier.effect_duration_add = 4;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.DEADEYE));
    }
    public static final Entry deadeye_spec_b_modifier_2 = add(deadeye_spec_b_modifier_2());
    private static Entry deadeye_spec_b_modifier_2() {
        var id = Identifier.of(NAMESPACE, "deadeye_spec_b_modifier_2");
        var title = "Bouncing Trick Shots";
        var description = "Trick Shot now ricochets {ricochet} more times.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = deadeyeSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:trick_shot";
        modifier.projectile_perks = Spell.ProjectileData.Perks.EMPTY();
        modifier.projectile_perks.ricochet = 3;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.DEADEYE));
    }
    public static final Entry deadeye_spec_a_modifier_3 = add(deadeye_spec_a_modifier_3());
    private static Entry deadeye_spec_a_modifier_3() {
        var id = Identifier.of(NAMESPACE, "deadeye_spec_a_modifier_3");
        var title = "Wounding Shot";
        var description = "If the target has a bad effect Disabling Shot inflicts grievous wounds for {effect_duration} sec.";
        var spell = createModifierAlikePassiveSpell();
        spell.school = deadeyeSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = SpellBuilder.Triggers.specificSpellHit("archers_expansion:disabling_shot");
        var condition = new Spell.TargetCondition();
        condition.entity_predicate_id = SpellEntityPredicates.HAS_BAD_EFFECT.id().toString();
        trigger.target_conditions = List.of(condition);
        spell.passive.triggers = List.of(trigger);

        var debuff = SpellBuilder.Impacts.effectSet(MRPGCEffects.GRIEVOUS_WOUNDS.id.toString(), 6, 0);
        debuff.action.status_effect.amplifier_power_multiplier = 0.25F;
        debuff.action.status_effect.refresh_duration = true;
        spell.impacts = List.of(debuff);

        SpellBuilder.Cost.cooldown(spell, 0.5F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.DEADEYE));
    }
    public static final Entry deadeye_spec_b_modifier_3 = add(deadeye_spec_b_modifier_3());
    private static Entry deadeye_spec_b_modifier_3() {
        var id = Identifier.of(NAMESPACE, "deadeye_spec_b_modifier_3");
        var title = "Leaping Swiftness";
        var description = "Disabling Shot increases movement speed by {bonus} for {effect_duration} secs.";
        var spell = createModifierAlikePassiveSpell();
        spell.school = deadeyeSchool;
        spell.range = 0;
        var effect = MrpgSkillEffects.LEAPING_SWIFTNESS;
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description()
                    .replace("{bonus}", bonus);
        };

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = SpellBuilder.Triggers.specificSpellHit("archers_expansion:disabling_shot");
        var condition = new Spell.TargetCondition();
        condition.entity_predicate_id = SpellEntityPredicates.HAS_BAD_EFFECT.id().toString();
        trigger.target_conditions = List.of(condition);
        spell.passive.triggers = List.of(trigger);

        spell.release.particles = new ParticleBatch[]{
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_speed.id(), Color.WHITE)
        };
        spell.release.sound = new Sound(SpellEngineSounds.SPEED_BOOST.id());

        var buff = SpellBuilder.Impacts.effectSet(effect.id.toString(), 5, 0);
        buff.action.status_effect.refresh_duration = true;
        buff.action.apply_to_caster = true;
        spell.impacts = List.of(buff);

        SpellBuilder.Cost.cooldown(spell, 0.5F);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.DEADEYE));
    }
    public static final Entry deadeye_spec_a_modifier_4 = add(deadeye_spec_a_modifier_4());
    private static Entry deadeye_spec_a_modifier_4() {
        var id = Identifier.of(NAMESPACE, "deadeye_spec_a_modifier_4");
        var title = "Persistent Gas Cloud";
        var description = "Choking Gas leaves a gas cloud behind, poisoning and dealing {damage} damage to enemies for {cloud_duration} sec.";
        var spell = createModifierAlikePassiveSpell();
        spell.school = deadeyeSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.specificSpellCast("archers_expansion:choking_gas");
        spell.passive.triggers = List.of(trigger);

        spell.deliver.type = Spell.Delivery.Type.CLOUD;
        spell.deliver.delay = 8;
        Spell.Delivery.Cloud cloud = new Spell.Delivery.Cloud();
        cloud.volume.radius = 5;
        cloud.volume.area.vertical_range_multiplier = 0.3F;
        cloud.volume.sound = new Sound(SpellEngineSounds.POISON_CLOUD_TICK.id().toString());
        cloud.impact_tick_interval = 8;
        cloud.time_to_live_seconds = 5;
        cloud.spawn.sound = new Sound(SpellEngineSounds.POISON_CLOUD_SPAWN.id().toString());
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        cloud.client_data.light_level = 0;
        cloud.client_data.particles = new ParticleBatch[]{(new ParticleBatch(SpellEngineParticles.smoke_large.id().toString(), ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET, 1.0F, 0.01F, 0.02F)).color(2583652010L), (new ParticleBatch(SpellEngineParticles.smoke_large.id().toString(), ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET, 1.0F, 0.01F, 0.02F)).color(870134766L)};
        spell.deliver.clouds = List.of(cloud);
        Spell.Impact debuff = SpellBuilder.Impacts.effectSet("archers_expansion:choking_gas", 3, 1);
        debuff.action.status_effect.amplifier_power_multiplier = 0.3F;
        debuff.particles = new ParticleBatch[]{(new ParticleBatch(
                SpellEngineParticles.smoke_large.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                1.5F, 0.01F, 0.02F))
                .color(Color.POISON_MID.toRGBA()),
                new ParticleBatch(SpellEngineParticles.MagicParticles.get(SpellEngineParticles.MagicParticles.Shape.SKULL,
                        SpellEngineParticles.MagicParticles.Motion.DECELERATE).id().toString(), ParticleBatch.Shape.SPHERE,
                        ParticleBatch.Origin.CENTER, 3.0F, 0.1F, 0.2F)
                        .color(Color.POISON_MID.toRGBA())};
        poisonDeny(debuff);
        var impact = SpellBuilder.Impacts.damage(0.1F, 0);
        spell.impacts = List.of(debuff,impact);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.DEADEYE));
    }
    public static final Entry deadeye_spec_b_modifier_4 = add(deadeye_spec_b_modifier_4());
    private static Entry deadeye_spec_b_modifier_4() {
        var id = Identifier.of(NAMESPACE, "deadeye_spec_b_modifier_4");
        var title = "Bouncing Gas Arrow";
        var description = "The Choking Gas Arrow now ricochets {ricochet} times.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = deadeyeSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:choking_gas";
        modifier.projectile_perks = Spell.ProjectileData.Perks.EMPTY();
        modifier.projectile_perks.ricochet_range = 10;
        modifier.projectile_perks.ricochet = 3;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.DEADEYE));
    }
    public static final Color SHADOW_COLOR = Color.from(0x00B0B0);
    ///DEADEYE PASSIVES
    public static final Entry deadeye_spec_a_passive_1 = add(deadeye_spec_a_passive_1());
    private static Entry deadeye_spec_a_passive_1() {
        var id = Identifier.of(NAMESPACE, "deadeye_spec_a_passive_1");
        var title = "Barbed Arrows";
        var description = "Arrows have {trigger_chance} chance, to stack bleeding to the target for {effect_duration} sec.";
        var effect = MRPGCEffects.BLEEDING;

        var spell = SpellBuilder.createSpellPassive();
        spell.school = deadeyeSchool;
        spell.range = 0;
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.arrowHit();
        trigger.chance = 0.4F;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 7F, 0,6);
        impact.action.status_effect.refresh_duration = true;
        bleedingDeny(impact);
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.dripping_blood.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.2F, 0.8F)
        };
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 2F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.DEADEYE));
    }
    public static final Entry deadeye_spec_b_passive_1 = add(deadeye_spec_b_passive_1());
    private static Entry deadeye_spec_b_passive_1() {
        var id = Identifier.of(NAMESPACE, "deadeye_spec_b_passive_1");
        var title = "Withdraw";
        var description = "Arrows have {trigger_chance} chance, to cure a negative condition and heal for {heal} hearts.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = deadeyeSchool;
        spell.range = 0;
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.arrowHit();
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        trigger.chance = 0.05F;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.heal(0.025F);
        impact.attribute = EntityAttributes.GENERIC_MAX_HEALTH.getIdAsString();
        impact.attribute_from_target = true;
        impact.action.apply_to_caster = true;
        var cleanse = SpellBuilder.Impacts.effectCleanse();
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.HEAL,
                                SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        25, 0.25F, 0.6F
                ).color(SHADOW_COLOR.toRGBA())
        };
        impact.sound = new Sound(SpellEngineSounds.GENERIC_HEALING_IMPACT_3.id());
        spell.impacts = List.of(impact,cleanse);

        SpellBuilder.Cost.cooldown(spell, 15F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.DEADEYE));
    }
    public static final Entry deadeye_spec_a_passive_2 = add(deadeye_spec_a_passive_2());
    private static Entry deadeye_spec_a_passive_2() {
        var id = Identifier.of(NAMESPACE, "deadeye_spec_a_passive_2");
        var title = "Poison Bomb";
        var description = "{trigger_chance} chance upon rolling to leave behind Choking Gas for {cloud_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = deadeyeSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        trigger.chance = 0.5F;
        spell.passive.triggers = List.of(trigger);

        spell.deliver.type = Spell.Delivery.Type.CLOUD;
        spell.deliver.delay = 5;
        Spell.Delivery.Cloud cloud = new Spell.Delivery.Cloud();
        cloud.volume.radius = 2.5F;
        cloud.volume.area.vertical_range_multiplier = 0.3F;
        cloud.volume.sound = new Sound(SpellEngineSounds.POISON_CLOUD_TICK.id().toString());
        cloud.impact_tick_interval = 8;
        cloud.time_to_live_seconds = 5;
        cloud.spawn.sound = new Sound(SpellEngineSounds.POISON_CLOUD_SPAWN.id().toString());
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        cloud.client_data.light_level = 0;
        cloud.client_data.particles = new ParticleBatch[]{(new ParticleBatch(SpellEngineParticles.smoke_large.id().toString(), ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET, 1.0F, 0.01F, 0.02F)).color(Color.POISON_DARK.toRGBA()), (new ParticleBatch(SpellEngineParticles.smoke_large.id().toString(), ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET, 1.0F, 0.01F, 0.02F)).color(Color.POISON_DARK.toRGBA())};
        spell.deliver.clouds = List.of(cloud);
        Spell.Impact debuff = SpellBuilder.Impacts.effectSet("archers_expansion:choking_gas", 1, 1);
        debuff.action.status_effect.amplifier_power_multiplier = 0.3F;
        debuff.particles = new ParticleBatch[]{(new ParticleBatch(
                SpellEngineParticles.smoke_large.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                1.5F, 0.01F, 0.02F))
                .color(Color.POISON_MID.toRGBA()),
                new ParticleBatch(SpellEngineParticles.MagicParticles.get(SpellEngineParticles.MagicParticles.Shape.SKULL,
                        SpellEngineParticles.MagicParticles.Motion.DECELERATE).id().toString(), ParticleBatch.Shape.SPHERE,
                        ParticleBatch.Origin.CENTER, 3.0F, 0.1F, 0.2F)
                        .color(Color.POISON_MID.toRGBA())};
        poisonDeny(debuff);
        spell.impacts = List.of(debuff);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.DEADEYE));
    }
    public static final Color SMOKE_BOMB_COLOR = Color.from(0x302c2c);
    public static final Entry deadeye_spec_b_passive_2 = add(deadeye_spec_b_passive_2());
    private static Entry deadeye_spec_b_passive_2() {
        var id = Identifier.of(NAMESPACE, "deadeye_spec_b_passive_2");
        var title = "Smoke Bomb";
        var description = "{trigger_chance} chance upon rolling to leave behind a Smoke Bomb for {cloud_duration} sec. Blinding enemies and increasing Evasion for allies.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = deadeyeSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        trigger.chance = 0.5F;
        spell.passive.triggers = List.of(trigger);

        spell.deliver.type = Spell.Delivery.Type.CLOUD;
        spell.deliver.delay = 5;
        Spell.Delivery.Cloud cloud = new Spell.Delivery.Cloud();
        cloud.volume.radius = 2.5F;
        cloud.volume.area.vertical_range_multiplier = 0.3F;
        cloud.volume.sound = new Sound(MrpgSkillSounds.smokebomb_loop.id());
        cloud.impact_tick_interval = 10;
        cloud.time_to_live_seconds = 5;
        cloud.spawn.sound = new Sound(MrpgSkillSounds.smokebomb_release.id());
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        cloud.client_data.light_level = 0;
        cloud.client_data.particles = new ParticleBatch[]{(new ParticleBatch(SpellEngineParticles.smoke_large.id().toString(),
                ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET, 1.0F, 0.01F, 0.02F))
                .color(SMOKE_BOMB_COLOR.toRGBA()),
                (new ParticleBatch(SpellEngineParticles.smoke_large.id().toString(),
                        ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET, 1.0F, 0.01F, 0.02F))
                        .color(SMOKE_BOMB_COLOR.toRGBA())};
        spell.deliver.clouds = List.of(cloud);
        Spell.Impact debuff = SpellBuilder.Impacts.effectSet(MrpgSkillEffects.SMOKE_BOMB.id.toString(), 1, 0);
        debuff.action.status_effect.refresh_duration = true;
        Spell.Impact debuff2 = SpellBuilder.Impacts.effectSet("blindness", 1, 0);
        debuff2.action.status_effect.refresh_duration = true;
        Spell.Impact buff = SpellBuilder.Impacts.effectSet(MrpgSkillEffects.CAMOUFLAGED.id.toString(), 1, 0);
        buff.action.status_effect.refresh_duration = true;
        buff.action.apply_to_caster = true;
        buff.particles = new ParticleBatch[]{(
                new ParticleBatch(
                SpellEngineParticles.smoke_large.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                5.0F, 0.001F, 0.001F))
                .color(SMOKE_BOMB_COLOR.toRGBA()),
                (new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        5.0F, 0.001F, 0.001F))
                .color(SMOKE_BOMB_COLOR.toRGBA())
        };

        spell.impacts = List.of(debuff, debuff2, buff);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.DEADEYE));
    }
    public static final Entry deadeye_spec_a_passive_3 = add(deadeye_spec_a_passive_3());
    private static Entry deadeye_spec_a_passive_3() {
        var id = Identifier.of(NAMESPACE, "deadeye_spec_a_passive_3");
        var title = "Heartseeker";
        var description = "Arrows have {trigger_chance} chance, to deal more damage the less health the target has.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = deadeyeSchool;
        spell.range = 0;
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.arrowHit();
        trigger.chance = 0.2F;
        spell.passive.triggers = List.of(trigger);

        var custom = new Spell.Impact();
        custom.action = new Spell.Impact.Action();
        custom.action.custom = new Spell.Impact.Action.Custom();
        custom.action.type = Spell.Impact.Action.Type.CUSTOM;
        custom.action.custom.intent = SpellTarget.Intent.HARMFUL;
        custom.action.custom.handler = "more_rpg_classes:damage_according_to_missing_health";
        custom.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.dripping_blood.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        35, 0.4F, 1.0F),
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.2F, 0.5F).color(Color.RED.toRGBA())
        };

        spell.impacts = List.of(custom);

        SpellBuilder.Cost.cooldown(spell, 20F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.DEADEYE));
    }
    public static final Entry deadeye_spec_b_passive_3 = add(deadeye_spec_b_passive_3());
    private static Entry deadeye_spec_b_passive_3() {
        var id = Identifier.of(NAMESPACE, "deadeye_spec_b_passive_3");
        var title = "Shadow Refuge";
        final var healthThreshold = 0.35F;
        var description = "Upon taking damage below {threshold} health you create a area that heals you for {heal} hearts and gives you invisibility for {effect_duration} secs.";
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var threshold = SpellTooltip.percent(healthThreshold);
            return args.description()
                    .replace("{threshold}", threshold);
        };

        var spell = SpellBuilder.createSpellPassive();
        spell.school = deadeyeSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.becomingLowHP(healthThreshold);
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);


        float radius =5.0F;
        spell.deliver.type = Spell.Delivery.Type.CLOUD;
        spell.deliver.delay = 0;
        Spell.Delivery.Cloud cloud = new Spell.Delivery.Cloud();
        cloud.volume.radius = radius;
        cloud.volume.area.vertical_range_multiplier = 0.5F;

        cloud.impact_tick_interval = 20;
        cloud.time_to_live_seconds = 7;
        cloud.spawn.sound = new Sound(MrpgSkillSounds.shadow_refuge_release.id());
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        cloud.client_data.light_level = 10;
        var areaParticle = SpellEngineParticles.area_effect_658;
        cloud.client_data.particle_spawn_interval = 20;
        cloud.client_data.interval_particles = new ParticleBatch[] {
                new ParticleBatch(areaParticle.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.GROUND,
                        1, 0.0F, 0.F)
                        .scale(4)
                        .color(SHADOW_COLOR.alpha(0.75F).toRGBA()),
        };
        spell.deliver.clouds = List.of(cloud);

        var heal = SpellBuilder.Impacts.heal(0.05F);
        heal.attribute = EntityAttributes.GENERIC_MAX_HEALTH.getIdAsString();
        heal.attribute_from_target = true;
        heal.action.apply_to_caster = true;
        heal.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.HEAL,
                                SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.25F, 0.4F
                ).color(SHADOW_COLOR.toRGBA())
        };
        var buff = SpellBuilder.Impacts.effectSet(MrpgSkillEffects.SHADOWS_REFUGE.id.toString(), 2, 0);
        buff.sound = new Sound(MrpgSkillSounds.shadow_refuge_release.id());

        spell.impacts = List.of(heal,buff);

        SpellBuilder.Cost.cooldown(spell, 40F);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.DEADEYE));
    }
    ///TUNDRA HUNTER MODIFIERS
    public static final Entry tundra_hunter_spec_a_modifier_1 = add(tundra_hunter_spec_a_modifier_1());
    private static Entry tundra_hunter_spec_a_modifier_1() {
        var id = Identifier.of(NAMESPACE, "tundra_hunter_spec_a_modifier_1");
        var title = "Additional Frozen Shots";
        var description = "You gain {stash_amplifier_add} additional Frozen Shots.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:frozen_shot";
        modifier.stash_amplifier_add = 2;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.TUNDRA_HUNTER));
    }
    public static final Entry tundra_hunter_spec_b_modifier_1 = add(tundra_hunter_spec_b_modifier_1());
    private static Entry tundra_hunter_spec_b_modifier_1() {
        var id = Identifier.of(NAMESPACE, "tundra_hunter_spec_b_modifier_1");
        var title = "Frost Stalker";
        var description = "Increases the duration of Frosted by {effect_duration_add} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:frozen_shot";
        modifier.effect_duration_add = 3;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.TUNDRA_HUNTER));
    }
    public static final Entry tundra_hunter_spec_a_modifier_2 = add(tundra_hunter_spec_a_modifier_2());
    private static Entry tundra_hunter_spec_a_modifier_2() {
        var id = Identifier.of(NAMESPACE, "tundra_hunter_spec_a_modifier_2");
        var title = "Extra Arctic Shots";
        var description = "Arctic Volley shoots {extra_launch} additional arctic arrows.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:arctic_volley";

        modifier.projectile_launch = Spell.LaunchProperties.EMPTY();
        modifier.projectile_launch.extra_launch_count = 2;
        modifier.projectile_launch.extra_launch_delay = 0;
        modifier.power_modifier = new Spell.Impact.Modifier();

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.TUNDRA_HUNTER));
    }
    public static final Entry tundra_hunter_spec_b_modifier_2 = add(tundra_hunter_spec_b_modifier_2());
    private static Entry tundra_hunter_spec_b_modifier_2() {
        var id = Identifier.of(NAMESPACE, "tundra_hunter_spec_b_modifier_2");
        var title = "Arctic Blessing";
        var description = "Reduces the cooldown of Arctic Volley by {cooldown_duration_deduct} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:arctic_volley";
        modifier.cooldown_duration_deduct = 2;
        modifier.power_modifier = new Spell.Impact.Modifier();

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.TUNDRA_HUNTER));
    }
    public static final Entry tundra_hunter_spec_a_modifier_3 = add(tundra_hunter_spec_a_modifier_3());
    private static Entry tundra_hunter_spec_a_modifier_3() {
        var id = Identifier.of(NAMESPACE, "tundra_hunter_spec_a_modifier_3");
        var title = "Arctic Pact";
        var description = "Frozen Pact has a {trigger_chance} chance to freeze targets solid.";
        var spell = createModifierAlikePassiveSpell();
        spell.school = tundraHunterSchool;
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

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.TUNDRA_HUNTER));
    }
    public static final Entry tundra_hunter_spec_b_modifier_3 = add(tundra_hunter_spec_b_modifier_3());
    private static Entry tundra_hunter_spec_b_modifier_3() {
        var id = Identifier.of(NAMESPACE, "tundra_hunter_spec_b_modifier_3");
        var title = "Hunting Instincts";
        var description = "Frozen Pact increases Frost Power and Ranged Damage by {bonus} for {effect_duration} secs.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = tundraHunterSchool;
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

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.TUNDRA_HUNTER));
    }
    public static final Entry tundra_hunter_spec_a_modifier_4 = add(tundra_hunter_spec_a_modifier_4());
    private static Entry tundra_hunter_spec_a_modifier_4() {
        var id = Identifier.of(NAMESPACE, "tundra_hunter_spec_a_modifier_4");
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
        spell.school = tundraHunterSchool;

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

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.TUNDRA_HUNTER));
    }
    public static final Entry tundra_hunter_spec_b_modifier_4 = add(tundra_hunter_spec_b_modifier_4());
    private static Entry tundra_hunter_spec_b_modifier_4() {
        var id = Identifier.of(NAMESPACE, "tundra_hunter_spec_b_modifier_4");
        var title = "Deep Crystallized Arrow";
        var description = "Enchanted Crystal Arrow deals {power_multiplier} more damage.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = tundraHunterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:enchanted_crystal_arrow";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.power_multiplier = 0.3F;

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.TUNDRA_HUNTER));
    }
    ///TUNDRA HUNTER PASSIVES
    public static final Entry tundra_hunter_spec_a_passive_1 = add(tundra_hunter_spec_a_passive_1());
    private static Entry tundra_hunter_spec_a_passive_1() {
        var id = Identifier.of(NAMESPACE, "tundra_hunter_spec_a_passive_1");
        var title = "Hail";
        var description = "Arrows have a {trigger_chance} chance to launch falling icicles dealing {damage} damage.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = tundraHunterSchool;
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
        var model = new Spell.ProjectileModel();
        model.model_id = "more_rpg_classes:spell_projectile/falling_icicle";
        model.scale = 0.75F;
        model.light_emission = LightEmission.NONE;
        projectile.client_data.model = model;

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

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.TUNDRA_HUNTER));
    }
    public static final Entry tundra_hunter_spec_b_passive_1 = add(tundra_hunter_spec_b_passive_1());
    private static Entry tundra_hunter_spec_b_passive_1() {
        var id = Identifier.of(NAMESPACE, "tundra_hunter_spec_b_passive_1");
        var title = "Frozen Prey";
        var description = "If the target is frosted, you heal yourself for {heal} hearts.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = tundraHunterSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.arrowHit();
        var condition = new Spell.TargetCondition();
        condition.entity_predicate_id = HAS_FROSTED.id().toString();
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

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.TUNDRA_HUNTER));
    }
    public static final Entry tundra_hunter_spec_a_passive_2 = add(tundra_hunter_spec_a_passive_2());
    private static Entry tundra_hunter_spec_a_passive_2() {
        var id = Identifier.of(NAMESPACE, "tundra_hunter_spec_a_passive_2");
        var title = "Winter's Cloak";
        var description = "When rolling you have {trigger_chance_1} chance to freeze enemies solid that hit you for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = tundraHunterSchool;
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
        freezeImmuneDeny(impact);
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.snowflake.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET,
                        30, 0.4F, 0.4F)
        };
        impact.sound = new Sound(MrpgSkillSounds.winters_cloak_freeze.id());
        spell.impacts = List.of(impact);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.TUNDRA_HUNTER));
    }
    public static final Entry tundra_hunter_spec_b_passive_2 = add(tundra_hunter_spec_b_passive_2());
    private static Entry tundra_hunter_spec_b_passive_2() {
        var id = Identifier.of(NAMESPACE, "tundra_hunter_spec_b_passive_2");
        var title = "Terrain Mastery";
        var description = "When rolling you have a {trigger_chance} chance to stay immune from harmful effects for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellPassive();
        var effect = MrpgSkillEffects.TERRAIN_MASTERY;
        spell.school = tundraHunterSchool;
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


        return new Entry(id, spell, title, description, null, EnumSet.of(Category.TUNDRA_HUNTER));
    }
    public static final Entry tundra_hunter_spec_a_passive_3 = add(tundra_hunter_spec_a_passive_3());
    private static Entry tundra_hunter_spec_a_passive_3() {
        var id = Identifier.of(NAMESPACE, "tundra_hunter_spec_a_passive_3");
        var title = "Icy Rebirth";
        var description = "When killing targets you have a {trigger_chance_1} chance to spawn icicles on the ground for {cloud_duration}, dealing {damage} damage.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = tundraHunterSchool;
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
        skillTrigger.spell.school = tundraHunterSchool.id.toString();
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
        freezeImmuneDeny(impact);
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

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.TUNDRA_HUNTER));
    }
    public static final Entry tundra_hunter_spec_b_passive_3 = add(tundra_hunter_spec_b_passive_3());
    private static Entry tundra_hunter_spec_b_passive_3() {
        var id = Identifier.of(NAMESPACE, "tundra_hunter_spec_b_passive_3");
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
        spell.school = tundraHunterSchool;
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

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.TUNDRA_HUNTER));
    }
}
