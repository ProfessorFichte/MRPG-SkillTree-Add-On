package com.mrpgc_skilltree.skills;

import com.mrpgc_skilltree.effect.MrpgSkillEffects;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.client.particle.MoreParticles;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.more_rpg_classes.effect.MRPGCEffects;
import net.skill_tree_rpgs.skills.SkillTreeSounds;
import net.skill_tree_rpgs.skills.Spells;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.entity.SpellEntityPredicates;
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

import static com.mrpgc_skilltree.MRPGCSkillTreeAddOn.MOD_ID;

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
    private static final SpellEntityPredicates.Entry HAS_BLEEDING = SpellEntityPredicates.hasEffectOptimized(Identifier.of("more_rpg_classes", "bleeding"));
    private static final SpellEntityPredicates.Entry HAS_FROSTED = SpellEntityPredicates.hasEffectOptimized(Identifier.of("more_rpg_classes", "frosted"));

    public static final SpellSchool airWizardSchool = MoreSpellSchools.AIR;
    public static final SpellSchool earthWizardSchool = MoreSpellSchools.EARTH;
    public static final SpellSchool waterWizardSchool = MoreSpellSchools.WATER;
    public static final SpellSchool berserkerSchool = ExternalSpellSchools.PHYSICAL_MELEE;
    public static final SpellSchool forcemasterFighterSchool = ExternalSpellSchools.PHYSICAL_MELEE;
    public static final SpellSchool forcemasterCasterSchool = SpellSchools.ARCANE;
    public static final SpellSchool warArcherSchool = ExternalSpellSchools.PHYSICAL_RANGED;
    public static final SpellSchool deadeyeSchool = ExternalSpellSchools.PHYSICAL_RANGED;
    public static final SpellSchool tundraHunterSchool = ExternalSpellSchools.PHYSICAL_RANGED;
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
        var description = "Air Cutter deals deals {power_multiplier} more damage.";
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
        ///TO DO ADD PARTICLES


        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);
        modifier.replacing_area_impact = area_impact;

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
        modifier.power_modifier.critical_damage_bonus = 0.35F;
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
        var id = Identifier.of(NAMESPACE, "air_spec_a_modifier_4");
        var title = "Negative Pressure";
        var description = "Tornado deals {power_multiplier} more damage.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = airWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_tornado";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.critical_damage_bonus = 0.3F;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.AIR));
    }
    ///AIR PASSIVES
    //TO DO
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
        spell.school = MoreSpellSchools.EARTH;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:terra_stone_spear";
        var extendedRadius = 2.5F * (1F + bonus);
        modifier.replacing_area_impact = new Spell.AreaImpact();
        Spell.AreaImpact area_impact = new Spell.AreaImpact();
        area_impact.radius = extendedRadius;
        area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        area_impact.particles = new ParticleBatch[]{(new ParticleBatch("more_rpg_classes:stone_explosion", ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, 1.0F, 0.0F, 0.0F)).scale(extendedRadius/2)};
        area_impact.sound = Sound.withVolume(Identifier.of("block.pointed_dripstone.break"),1.5F);

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.EARTH));
    }
    public static final Entry earth_spec_a_modifier_2 = add(earth_spec_a_modifier_2());
    private static Entry earth_spec_a_modifier_2() {
        var id = Identifier.of(NAMESPACE, "earth_spec_a_modifier_2");
        var title = "Obsidian Skin";
        var description = "Stone Flesh grants you Obsidian Skin, protecting your from {effect_amplifier} incoming attack for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MoreSpellSchools.EARTH;
        var effect = MrpgSkillEffects.OBSIDIAN_SKIN;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:terra_stone_flesh";
        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(),5,2);
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
        spell.school = MoreSpellSchools.EARTH;
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
        spell.school = MoreSpellSchools.EARTH;
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

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.EARTH));
    }
    public static final Entry earth_spec_b_modifier_3 = add(earth_spec_b_modifier_3());
    private static Entry earth_spec_b_modifier_3() {
        var id = Identifier.of(NAMESPACE, "earth_spec_b_modifier_3");
        var title = "Sharp Dripstones";
        var description = "Terra Circle damage is increased by {power_multiplier}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MoreSpellSchools.EARTH;

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
        var description = "Earthquake reduces offensive attributes by {bonus} and for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        var effect = MrpgSkillEffects.CONCUSSION;
        spell.school = MoreSpellSchools.EARTH;
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
        spell.school = MoreSpellSchools.EARTH;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:terra_earthquake";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.range_add = 5;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.EARTH));
    }
    ///EARTH PASSIVES
    //TO DO
    ///WATER MODIFIER
    public static final Color WATER_SPELL_COLOR = Color.from(0x4a8bff);
    public static final Entry water_spec_a_modifier_1 = add(water_spec_a_modifier_1());
    private static Entry water_spec_a_modifier_1() {
        var id = Identifier.of(NAMESPACE, "water_spec_a_modifier_1");
        var title = "Strong Water Whip";
        var description = "Water Whip deals {knockback_multiply_base} more knockback.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MoreSpellSchools.WATER;

        var bonus = 0.5F;

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
        var description = "";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MoreSpellSchools.WATER;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:aqua_water_whip";
        var impact = SpellBuilder.Impacts.damage(0.35F, 0.5F);
        impact.action.allow_on_center_target = false;

        var radius = 3F;

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
        spell.school = MoreSpellSchools.WATER;

        var bonus = 0.2F;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:aqua_bubble_beam";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.power_multiplier = bonus;

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
        spell.school = MoreSpellSchools.WATER;
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
        spell.school = MoreSpellSchools.WATER;

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
        spell.school = MoreSpellSchools.WATER;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.specificSpellCast("elemental_wizards_rpg:aqua_springwater");
        spell.passive.triggers = List.of(trigger);

        spell.deliver.type = Spell.Delivery.Type.CLOUD;
        var cloud = new Spell.Delivery.Cloud();
        cloud.volume.radius = 4;
        cloud.impact_tick_interval = 10;
        cloud.time_to_live_seconds = 7;
        cloud.client_data.particles = new ParticleBatch[]{
                new ParticleBatch(
                        "more_rpg_classes:bubble",
                        ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.GROUND,
                        25, 0.05F, 0.2F)
        };
        spell.deliver.clouds = List.of(cloud);

        var impact = SpellBuilder.Impacts.damage(0.25F, 0.3F);
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
        spell.school = MoreSpellSchools.WATER;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:aqua_springwater";

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
        spell.school = MoreSpellSchools.WATER;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.specificSpellHit("elemental_wizards_rpg:aqua_hydro_beam");
        trigger.chance = 0.3F;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.stun(3.5F);
        spell.impacts = List.of(impact);
        SpellBuilder.Cost.cooldown(spell, 1F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WATER));
    }
    ///WATER PASSIVES
    //TO DO
    ///BERSERKER MODIFIERS
    public static final Entry berserker_spec_a_modifier_1 = add(berserker_spec_a_modifier_1());
    private static Entry berserker_spec_a_modifier_1() {
        var id = Identifier.of(NAMESPACE, "berserker_spec_a_modifier_1");
        var title = "Enraged";
        var description = "Increases the maximum number of Wild Rage stacks by {effect_amplifier_cap_add}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;

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
        var description = "Wild Rage reduces incoming damage by {bonus} for {effect_duration} sec.";
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description().replace("{bonus}", bonus);
        };
        var spell = SpellBuilder.createSpellModifier();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;

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
        var effect = MrpgSkillEffects.BLIND_WITH_RAGE;
        var description = "Blood Reckoning increases attack damage by {bonus} for {effect_duration} sec.";
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description().replace("{bonus}", bonus);
        };
        var spell = SpellBuilder.createSpellModifier();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "berserker_rpg:blood_reckoning";

        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(), 10, 0);
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
        var description = "Hitting Bleeding Targets with Blood Reckoning deals additional {damage} damage.";
        var spell = createModifierAlikePassiveSpell();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = SpellBuilder.Triggers.specificSpellHit("berserker_rpg:blood_reckoning");
        var condition = new Spell.TargetCondition();
        condition.entity_predicate_id = HAS_BLEEDING.id().toString();
        trigger.target_conditions = List.of(condition);
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.damage(0.5F, 0F);
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 0.5F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.BERSERKER));
    }
    public static final Entry berserker_spec_a_modifier_3 = add(berserker_spec_a_modifier_3());
    private static Entry berserker_spec_a_modifier_3() {
        var id = Identifier.of(NAMESPACE, "berserker_spec_a_modifier_3");
        var title = "Deadly Precision";
        var description = "After Casting Bloody Strike your melee hits deal additional damage according to {max_health_damage} of the targets max health for {stash_duration} seconds.";
        var spell = createModifierAlikePassiveSpell();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;
        spell.range = 0;
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifiedDescription = args.description();
            var max_health_damage = spell.impacts.get(0).action.damage;
            if (max_health_damage != null) {
                modifiedDescription = modifiedDescription.replace("{max_health_damage}", SpellTooltip.percent(max_health_damage.spell_power_coefficient));
            }
            return modifiedDescription;
        };

        var effect = MrpgSkillEffects.DEADLY_PRECISION;
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = SpellBuilder.Triggers.specificSpellCast("berserker_rpg:bloody_strike");
        spell.passive.triggers = List.of(trigger);

        var strashTrigger = SpellBuilder.Triggers.meleeAttack(false);
        SpellBuilder.Deliver.stash(spell, effect.id.toString(), 3, strashTrigger);
        spell.deliver.stash_effect.consume = 0;

        var damage = new Spell.Impact();
        damage.attribute = EntityAttributes.GENERIC_MAX_HEALTH.getIdAsString();
        damage.attribute_from_target = true;
        damage.action = new Spell.Impact.Action();
        damage.action.type = Spell.Impact.Action.Type.DAMAGE;
        damage.action.damage = new Spell.Impact.Action.Damage();
        damage.action.damage.spell_power_coefficient = 0.03F;
        damage.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.dripping_blood.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        20, 0.2F, 0.8F)
        };

        spell.impacts = List.of(damage);


        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.BERSERKER));
    }
    public static final Entry berserker_spec_b_modifier_3 = add(berserker_spec_b_modifier_3());
    private static Entry berserker_spec_b_modifier_3() {
        var id = Identifier.of(NAMESPACE, "berserker_spec_a_modifier_1");
        var title = "Slicing Maelstorm";
        var description = "Bloody Strike inflicts bleeding around the target for {effect_duration} seconds.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "berserker_rpg:bloody_strike";

        var radius = 2.5F;

        var area_impact = new Spell.AreaImpact();
        area_impact.execute_action_type = Spell.Impact.Action.Type.DAMAGE;
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
        var description = "Outrage deals {power_multiplier} more damage.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;

        var bonus = 0.5F;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "berserker_rpg:outrage";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.power_multiplier = bonus;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.BERSERKER));
    }
    public static final Entry berserker_spec_b_modifier_4 = add(berserker_spec_b_modifier_4());
    private static Entry berserker_spec_b_modifier_4() {
        var id = Identifier.of(NAMESPACE, "berserker_spec_b_modifier_4");
        var title = "Reckless Outrage";
        var description = "";
        var effect = MrpgSkillEffects.RECKLESS_RAGE;
        var spell = SpellBuilder.createSpellModifier();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "berserker_rpg:outrage";
        spell.modifiers = List.of(modifier);

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

        var buff = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 12, 0,9);
        buff.action.apply_to_caster = true;
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(custom, buff);

        spell.modifiers = List.of(modifier);
        return new Entry(id, spell, title, description, null, EnumSet.of(Category.BERSERKER));
    }
    ///BERSERKER PASSIVES
    //TO DO
    ///FORCEMASTER MODIFIERS
    public static final Entry forcemaster_spec_a_modifier_1 = add(forcemaster_spec_a_modifier_1());
    private static Entry forcemaster_spec_a_modifier_1() {
        var id = Identifier.of(NAMESPACE, "forcemaster_spec_a_modifier_1");
        var title = "Shattering Splitters";
        var description = "Melee hits with Stonehand deals {damage} damage around the target.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;

        var radius = 3.0F;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:stonehand";

        var impact = SpellBuilder.Impacts.damage(0.5F, 0);
        impact.action.allow_on_center_target = false;


        var area_impact = new Spell.AreaImpact();
        area_impact.execute_action_type = Spell.Impact.Action.Type.DAMAGE;
        area_impact.radius = radius;
        area_impact.area = new Spell.Target.Area();
        area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        area_impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        "more_rpg_classes:stone_particle",
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        5, 0.15F, 0.15F
                )
        };


        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);
        modifier.replacing_area_impact = area_impact;

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.FORCEMASTER));
    }
    public static final Entry forcemaster_spec_b_modifier_1 = add(forcemaster_spec_b_modifier_1());
    private static Entry forcemaster_spec_b_modifier_1() {
        var id = Identifier.of(NAMESPACE, "forcemaster_spec_b_modifier_1");
        var title = "Crystallized Fists";
        var description = "Stonehand grants you the Crystallized Fists effect, increasing your arcane spell power by {bonus} for {effect_duration} seconds.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = SpellSchools.ARCANE;
        var effect = MrpgSkillEffects.CRYSTALLIZED_FISTS;
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description()
                    .replace("{bonus}", bonus);
        };

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:stonehand";
        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(),10,0);
        impact.action.apply_to_caster = true;

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.FORCEMASTER));
    }
    public static final Entry forcemaster_spec_a_modifier_2 = add(forcemaster_spec_a_modifier_2());
    private static Entry forcemaster_spec_a_modifier_2() {
        var id = Identifier.of(NAMESPACE, "forcemaster_spec_a_modifier_2");
        var title = "Pumped Up";
        var description = "Burstcrack increases your attack damage by {bonus} for {effect_duration} seconds.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;
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
        spell.school = SpellSchools.ARCANE;

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
        var spell = SpellBuilder.createSpellModifier();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;
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
        var description = "Belial Smashing has {trigger_chance} chance to knock up the target.";
        var spell = createModifierAlikePassiveSpell();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;

        var radius = 5F;
        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:belial_smashing";
        var impact = SpellBuilder.Impacts.damage(0.7F, 0.5F);

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
        var id = Identifier.of(NAMESPACE, "forcemaster_spec_b_modifier_2");
        var title = "Powerful Asalraalaikum";
        var description = " Asalraalaikum damage increased by {power_multiplier}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;

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
        spell.school = SpellSchools.ARCANE;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:asal";
        modifier.cooldown_duration_deduct = 8;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.FORCEMASTER));
    }
    ///FORCEMASTER PASSIVES
    //TO DO
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
    public static final Entry war_archer_spec_b_modifier_2 = add(war_archer_spec_b_modifier_2());
    private static Entry war_archer_spec_b_modifier_2() {
        var id = Identifier.of(NAMESPACE, "war_archer_spec_b_modifier_2");
        var title = "Heavy Arrow Tips";
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
    public static final Entry war_archer_spec_a_modifier_3 = add(war_archer_spec_a_modifier_3());
    private static Entry war_archer_spec_a_modifier_3() {
        var id = Identifier.of(NAMESPACE, "war_archer_spec_a_modifier_3");
        var title = "Explosive Point Blank Shot";
        var description = "Damaging with Point Blank Shot causes small explosion, hitting enemies within {impact_range} blocks radius, dealing extra {damage} damage.";
        var spell = createModifierAlikePassiveSpell();
        spell.school = warArcherSchool;

        var radius = 2F;
        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:point_blank_shot";
        var impact = SpellBuilder.Impacts.damage(0.5F, 0F);

        var area_impact = new Spell.AreaImpact();
        area_impact.execute_action_type = Spell.Impact.Action.Type.DAMAGE;
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

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.PREPEND;
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

        SpellBuilder.Complex.flameCloud(spell, 5.0F, 0.75F, 8, null);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WAR_ARCHER));
    }
    public static final Entry war_archer_spec_b_modifier_4 = add(war_archer_spec_b_modifier_4());
    private static Entry war_archer_spec_b_modifier_4() {
        var id = Identifier.of(NAMESPACE, "war_archer_spec_b_modifier_4");
        var title = "Increased Pin Down";
        var description = "Increases the knockback of Double Shot by {knockback_multiply_base}.";
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
        condition.entity_predicate_id = SpellEntityPredicates.IS_ON_FIRE.toString();
        trigger.target_conditions = List.of(condition);
        spell.passive.triggers = List.of(trigger);


        var impact = SpellBuilder.Impacts.damage(0.35F, 0.75F);
        /// IMPROVE PARTICLE AND SOUND
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        "explosion",
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        2, 0.2F, 0.25F)
                        .scale(2.0F)
        };
        impact.sound = new Sound("entity.generic.explode");
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 5F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WAR_ARCHER));
    }
    public static final Entry war_archer_spec_b_passive_1 = add(war_archer_spec_b_passive_1());
    private static Entry war_archer_spec_b_passive_1() {
        var id = Identifier.of(NAMESPACE, "war_archer_spec_b_passive_1");
        var title = "Protector of the Tower";
        var description = "Your arrow hits have {trigger_chance} to increase your armor and knockback resistance for {effect_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = warArcherSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.arrowHit();
        trigger.chance = 0.35F;
        spell.passive.triggers = List.of(trigger);


        var impact = SpellBuilder.Impacts.effectAdd(MrpgSkillEffects.TOWER_PROTECTOR.toString(), 8,0,5);
        impact.particles = new ParticleBatch[]{
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_shield.id(), Color.WHITE),
        };

        /// IMPROVE SOUND
        impact.sound = new Sound("entity.generic.explode");
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 1F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WAR_ARCHER));
    }
    public static final Entry war_archer_spec_a_passive_2 = add(war_archer_spec_a_passive_2());
    private static Entry war_archer_spec_a_passive_2() {
        var id = Identifier.of(NAMESPACE, "war_archer_spec_a_passive_2");
        var title = "Reloading";
        var description = "When roaling you recharge Smoldering Arrows, you can now stack up to {effect_amplifier_cap} times.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = warArcherSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectAdd("archers_expansion:smoldering_arrows", 10, 0,4);
        impact.action.status_effect.refresh_duration = true;
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
                        10, 0.2F, 0.4F)
                        .color(Color.RAGE.toRGBA())
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
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_arrow.id(), Color.RAGE),
        };

        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();

        spell.deliver.type = Spell.Delivery.Type.SHOOT_ARROW;
        spell.deliver.shoot_arrow = new Spell.Delivery.ShootArrow();
        spell.deliver.shoot_arrow.launch_properties.velocity = 3.35F;
        spell.deliver.delay = 3;
        spell.deliver.shoot_arrow.launch_properties.extra_launch_count = 3;

        spell.arrow_perks = new Spell.ArrowPerks();
        spell.arrow_perks.damage_multiplier = 1F;
        spell.arrow_perks.bypass_iframes = true;
        spell.arrow_perks.knockback = 0.5F;

        SpellBuilder.Cost.cooldown(spell, 20);

        return new Entry(id, spell, title, description, null, Category.WAR_ARCHER);
    }
    public static final Entry war_archer_spec_b_passive_3 = add(war_archer_spec_b_passive_3());
    private static Entry war_archer_spec_b_passive_3() {
        var id = Identifier.of(NAMESPACE, "archer_spec_b_passive_3");
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
        /// ADD SOUNDS AND PARTICLES
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

        var debuff = SpellBuilder.Impacts.effectAdd(StatusEffects.POISON.getIdAsString(), 5, 1, 1);
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
                                SpellEngineParticles.MagicParticles.Shape.SPARK,
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
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.DEADEYE));
    }
    public static final Entry deadeye_spec_b_modifier_2 = add(deadeye_spec_b_modifier_2());
    private static Entry deadeye_spec_b_modifier_2() {
        var id = Identifier.of(NAMESPACE, "deadeye_spec_b_modifier_2");
        var title = "Bouncing Trick Shots";
        var description = "Trick Shot now ricochets {ricochet} times.";
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
        condition.entity_predicate_id = String.valueOf(SpellEntityPredicates.HAS_BAD_EFFECT);
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
        condition.entity_predicate_id = String.valueOf(SpellEntityPredicates.HAS_BAD_EFFECT);
        trigger.target_conditions = List.of(condition);
        spell.passive.triggers = List.of(trigger);

        spell.release.particles = new ParticleBatch[]{
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_speed.id(), Color.WHITE)
        };
        spell.release.sound = new Sound(SpellEngineSounds.SPEED_BOOST.id());

        var buff = SpellBuilder.Impacts.effectSet(effect.toString(), 5, 0);
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
        Spell.Impact debuff = SpellBuilder.Impacts.effectSet("archers_expansion:choking_gas", 3, 0);
        debuff.action.status_effect.amplifier_power_multiplier = 0.2F;
        debuff.particles = new ParticleBatch[]{(new ParticleBatch(
                SpellEngineParticles.smoke_large.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                1.5F, 0.01F, 0.02F))
                .color(Color.POISON_MID.toRGBA()),
                new ParticleBatch(SpellEngineParticles.MagicParticles.get(SpellEngineParticles.MagicParticles.Shape.SKULL,
                        SpellEngineParticles.MagicParticles.Motion.DECELERATE).id().toString(), ParticleBatch.Shape.SPHERE,
                        ParticleBatch.Origin.CENTER, 3.0F, 0.1F, 0.2F)
                        .color(Color.POISON_MID.toRGBA())};
        poisonDeny(debuff);
        var impact = SpellBuilder.Impacts.damage(0.25F, 0);
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
        impact.particles = new ParticleBatch[]{
                ///PARTICLE CHANGE
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_speed.id(), Color.WHITE)};
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 5F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.DEADEYE));
    }
    public static final Entry deadeye_spec_b_passive_1 = add(deadeye_spec_b_passive_1());
    private static Entry deadeye_spec_b_passive_1() {
        var id = Identifier.of(NAMESPACE, "deadeye_spec_b_passive_1");
        var title = "Withdraw";
        var description = "Arrows have {trigger_chance} chance, to cure a negative condition and heal for {heal} hearts.";
        var effect = MRPGCEffects.BLEEDING;

        var spell = SpellBuilder.createSpellPassive();
        spell.school = deadeyeSchool;
        spell.range = 0;
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.arrowHit();
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        trigger.chance = 0.2F;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.heal(0.025F);
        impact.attribute = EntityAttributes.GENERIC_MAX_HEALTH.getIdAsString();
        impact.attribute_from_target = true;
        impact.action.apply_to_caster = true;
        var cleanse = SpellBuilder.Impacts.effectCleanse();
        impact.action.status_effect.refresh_duration = true;
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.HEAL,
                                SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        5, 0.25F, 0.3F
                ).color(Color.POISON_DARK.toRGBA())
        };
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
        cloud.client_data.particles = new ParticleBatch[]{(new ParticleBatch(SpellEngineParticles.smoke_large.id().toString(), ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET, 1.0F, 0.01F, 0.02F)).color(2583652010L), (new ParticleBatch(SpellEngineParticles.smoke_large.id().toString(), ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET, 1.0F, 0.01F, 0.02F)).color(870134766L)};
        spell.deliver.clouds = List.of(cloud);
        Spell.Impact debuff = SpellBuilder.Impacts.effectSet("archers_expansion:choking_gas", 1, 0);
        debuff.action.status_effect.amplifier_power_multiplier = 0.2F;
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
        cloud.volume.sound = new Sound(SpellEngineSounds.POISON_CLOUD_TICK.id().toString());
        cloud.impact_tick_interval = 10;
        cloud.time_to_live_seconds = 5;
        cloud.spawn.sound = new Sound(SpellEngineSounds.POISON_CLOUD_SPAWN.id().toString());
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        cloud.client_data.light_level = 0;
        cloud.client_data.particles = new ParticleBatch[]{(new ParticleBatch(SpellEngineParticles.smoke_large.id().toString(),
                ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET, 1.0F, 0.01F, 0.02F))
                .color(SMOKE_BOMB_COLOR.toRGBA()),
                (new ParticleBatch(SpellEngineParticles.smoke_large.id().toString(),
                        ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET, 1.0F, 0.01F, 0.02F))
                        .color(SMOKE_BOMB_COLOR.toRGBA())};
        spell.deliver.clouds = List.of(cloud);
        Spell.Impact debuff = SpellBuilder.Impacts.effectSet(MrpgSkillEffects.SMOKE_BOMB.toString(), 1, 0);
        debuff.action.status_effect.refresh_duration = true;
        Spell.Impact buff = SpellBuilder.Impacts.effectSet(MrpgSkillEffects.CAMOUFLAGED.toString(), 1, 0);
        buff.action.status_effect.refresh_duration = true;
        buff.action.apply_to_caster = true;
        debuff.particles = new ParticleBatch[]{(
                new ParticleBatch(
                SpellEngineParticles.smoke_large.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                5.0F, 0.001F, 0.001F))
                .color(SMOKE_BOMB_COLOR.toRGBA()),
                (new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        5.0F, 0.001F, 0.001F))
                .color(SMOKE_BOMB_COLOR.toRGBA())
        };

        spell.impacts = List.of(debuff, buff);

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
                        35, 0.4F, 1.0F)
        };

        spell.impacts = List.of();

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
        spell.deliver.delay = 5;
        Spell.Delivery.Cloud cloud = new Spell.Delivery.Cloud();
        cloud.volume.radius = radius;
        cloud.volume.area.vertical_range_multiplier = 0.3F;
        ///IMPROVE SHADOW REFUGE SOUNDS
        cloud.volume.sound = new Sound(SpellEngineSounds.POISON_CLOUD_TICK.id().toString());
        cloud.impact_tick_interval = 10;
        cloud.time_to_live_seconds = 5;
        cloud.spawn.sound = new Sound(SpellEngineSounds.POISON_CLOUD_SPAWN.id().toString());
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        cloud.client_data.light_level = 0;
        cloud.client_data.interval_particles = new ParticleBatch[] {
                new ParticleBatch(
                        SpellEngineParticles.area_effect_715.id().toString(),
                        ParticleBatch.Shape.LINE, ParticleBatch.Origin.GROUND,
                        1, 0F, 0F)
                        .scale(radius * 1.5F)
                        .color(SMOKE_BOMB_COLOR.toRGBA())
        };
        spell.deliver.clouds = List.of(cloud);

        /// TOO DO SOUND AND PARTICLES
        var heal = SpellBuilder.Impacts.heal(0.05F);
        heal.attribute = EntityAttributes.GENERIC_MAX_HEALTH.getIdAsString();
        heal.attribute_from_target = true;
        heal.action.apply_to_caster = true;
        var buff = SpellBuilder.Impacts.effectSet("archers_expansion:infiltrators_arrow", 2, 0);

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
        modifier.effect_duration_add = 2;
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
        modifier.projectile_launch.extra_launch_count = 3;
        modifier.projectile_launch.extra_launch_delay = 3;
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

        var trigger = SpellBuilder.Triggers.specificSpellHit("archers_expansion:frozen_pact");
        trigger.chance = 0.3F;
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
        var title = "Earthen Blast";
        var bonus = 2.5F;
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
        var extendedRadius = 3.0F * (1F + bonus);
        modifier.replacing_area_impact = new Spell.AreaImpact();
        Spell.AreaImpact area_impact = new Spell.AreaImpact();
        area_impact.radius = extendedRadius;
        area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;

        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.TUNDRA_HUNTER));
    }
    public static final Entry tundra_hunter_spec_b_modifier_4 = add(tundra_hunter_spec_b_modifier_4());
    private static Entry tundra_hunter_spec_b_modifier_4() {
        var id = Identifier.of(NAMESPACE, "tundra_hunter_spec_b_modifier_4");
        var title = "Whirlwind Mastery";
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
        model.model_id = "more_rpg_classes:projectile/falling_icicle";
        model.scale = 0.75F;
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
        // CHANGE SOUND
        impact.sound = new Sound("spell_engine:generic_frost_impact");
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 5F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.TUNDRA_HUNTER));
    }
    public static final Entry tundra_hunter_spec_b_passive_1 = add(tundra_hunter_spec_b_passive_1());
    private static Entry tundra_hunter_spec_b_passive_1() {
        var id = Identifier.of(NAMESPACE, "tundra_hunter_spec_b_passive_1");
        var title = "Frozen Prey";
        var description = "If the target is frosted, you heal yourself for {heal}.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = tundraHunterSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.arrowHit();
        var condition = new Spell.TargetCondition();
        condition.entity_predicate_id = HAS_FROSTED.id().toString();
        trigger.target_conditions = List.of(condition);
        spell.passive.triggers = List.of(trigger);


        var impact = SpellBuilder.Impacts.heal(0.1F);
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
        //CHANGE SOUND
        impact.sound = new Sound("spell_engine:generic_healing_casting");
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 2F);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.TUNDRA_HUNTER));
    }
    public static final Entry tundra_hunter_spec_a_passive_2 = add(tundra_hunter_spec_a_passive_2());
    private static Entry tundra_hunter_spec_a_passive_2() {
        var id = Identifier.of(NAMESPACE, "tundra_hunter_spec_a_passive_2");
        var title = "Winter's Cloak";
        var description = "Upon rolling: {trigger_chance} chance to freeze enemies that hit you for {effect_duration} sec.";
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

        var impact = SpellBuilder.Impacts.effectAdd(MRPGCEffects.FROSTED.id.toString(), 7, 0,6);
        freezeImmuneDeny(impact);
        impact.action.status_effect.refresh_duration = true;
        /// CHANGE SOUND & ADD PARTICLES?
        impact.sound = new Sound(SpellEngineSounds.GENERIC_FROST_RELEASE.id());
        spell.impacts = List.of(impact);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.TUNDRA_HUNTER));
    }
    public static final Entry tundra_hunter_spec_b_passive_2 = add(tundra_hunter_spec_b_passive_2());
    private static Entry tundra_hunter_spec_b_passive_2() {
        var id = Identifier.of(NAMESPACE, "tundra_hunter_spec_b_passive_2");
        var title = "Terrain Mastery";
        var description = "Upon rolling: {trigger_chance} to cleanse a negative effect.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = tundraHunterSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        trigger.chance = 0.5F;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectCleanse();
        /// CHANGE PARTICLES & ADD SOUND?
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
        var description = "On kill:{trigger_chance_1} chance to spawn icicles on the ground for {cloud_duration}, dealing {damage} damage.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = tundraHunterSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.rangedKill(false);
        trigger.get(0).chance = 0.35F;
        trigger.get(1).chance = 0.35F;


        spell.deliver.type = Spell.Delivery.Type.CLOUD;
        var cloud = new Spell.Delivery.Cloud();
        cloud.volume.radius = 4.0F;
        cloud.volume.area.vertical_range_multiplier = 0.3F;
        ///CHANGE CLOUD SOUND
        cloud.volume.sound = new Sound(SpellEngineSounds.GENERIC_FROST_RELEASE.id());
        cloud.impact_tick_interval = 10;
        cloud.time_to_live_seconds = 5;
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        cloud.client_data.particles = new ParticleBatch[]{
                new ParticleBatch(
                        MoreParticles.ICE_TRAP.toString(),
                        ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET,
                        4, 0, 0)
        };
        spell.deliver.clouds = List.of(cloud);

        var impact = SpellBuilder.Impacts.effectAdd(MRPGCEffects.FROSTED.id.toString(), 7, 0,6);
        freezeImmuneDeny(impact);
        impact.action.status_effect.refresh_duration = true;
        /// CHANGE IMPACT SOUND & ADD PARTICLES?
        var damage = SpellBuilder.Impacts.damage(0.4F, 0);
        damage.sound = new Sound(SpellEngineSounds.GENERIC_FROST_IMPACT.id());
        spell.impacts = List.of(impact,damage);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.TUNDRA_HUNTER));
    }
    public static final Entry tundra_hunter_spec_b_passive_3 = add(tundra_hunter_spec_b_passive_3());
    private static Entry tundra_hunter_spec_b_passive_3() {
        var id = Identifier.of(NAMESPACE, "tundra_hunter_spec_b_passive_3");
        var title = "Hunting Fever";
        final var healthThreshold = 0.5F;
        var description = "Upon taking damage below {threshold} health you gain Hunting Fever effect, increasing your Movement Speed & Ranged Haste by {bonus} for {effect_duration} sec.";
        var effect = MrpgSkillEffects.HUNTING_FEVER;
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var threshold = SpellTooltip.percent(healthThreshold);
            return args.description()
                    .replace("{threshold}", threshold);
        };

        var spell = SpellBuilder.createSpellPassive();
        spell.school = tundraHunterSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.becomingLowHP(healthThreshold);
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        var buff = SpellBuilder.Impacts.effectSet(effect.id.toString(), 10, 2);
        /// CHANGE SOUNDS & PARTICLES
        //buff.sound = new Sound();
        spell.impacts = List.of(buff);

        SpellBuilder.Cost.cooldown(spell, 30F);

        return new Entry(id, spell, title, description, mutator, EnumSet.of(Category.TUNDRA_HUNTER));
    }
}
