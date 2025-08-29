package com.mrpgc_skilltree.skills;

import com.mrpgc_skilltree.effect.MrpgSkillEffects;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.more_rpg_classes.effect.MRPGCEffects;
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
    private static final SpellEntityPredicates.Entry HAS_BLEEDING = SpellEntityPredicates.hasEffectOptimized(Identifier.of("more_rpg_classes", "bleeding"));

    ///AIR MODIFIERS
    public static final Entry air_spec_a_modifier_1 = add(air_spec_a_modifier_1());
    private static Entry air_spec_a_modifier_1() {
        var id = Identifier.of(NAMESPACE, "air_spec_a_modifier_1");
        var title = "Fast Winds";
        var description = "Increases the knockback of Air Cutter by {knockback_multiply_base}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MoreSpellSchools.AIR;

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
        spell.school = MoreSpellSchools.AIR;

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
        spell.school = MoreSpellSchools.AIR;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.specificSpellHit("elemental_wizards_rpg:wind_aeroblast");
        trigger.chance = 0.3F;
        spell.passive.triggers = List.of(trigger);

        var debuff = SpellBuilder.Impacts.effectSet(StatusEffects.SLOW_FALLING.getIdAsString(), 2, 1);
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
        spell.school = MoreSpellSchools.AIR;

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
        spell.school = MoreSpellSchools.AIR;

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
        spell.school = MoreSpellSchools.AIR;

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
        spell.school = MoreSpellSchools.AIR;

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
        spell.school = MoreSpellSchools.AIR;

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
        spell.school = MoreSpellSchools.EARTH;

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

        var debuff = SpellBuilder.Impacts.effectSet(effect.id.toString(), 5, 1);
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
        spell.school = ExternalSpellSchools.PHYSICAL_RANGED;

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
        spell.school = ExternalSpellSchools.PHYSICAL_RANGED;

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
        spell.school = ExternalSpellSchools.PHYSICAL_RANGED;

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
        spell.school = ExternalSpellSchools.PHYSICAL_RANGED;

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
        spell.school = ExternalSpellSchools.PHYSICAL_RANGED;

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
        spell.school = ExternalSpellSchools.PHYSICAL_RANGED;
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
        spell.school = ExternalSpellSchools.PHYSICAL_RANGED;
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
        spell.school = ExternalSpellSchools.PHYSICAL_RANGED;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:pin_down";
        modifier.effect_duration_add = 3;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null, EnumSet.of(Category.WAR_ARCHER));
    }
    ///WAR ARCHER PASSIVES
    //TO DO
}
