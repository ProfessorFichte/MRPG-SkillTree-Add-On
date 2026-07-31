package com.mrpgc_skill_tree.skills;

import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.spell_engine.api.effect.SpellEngineEffects;
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

public class ForcemasterSkillSpells {
    public static final List<MrpgSkillSpells.Entry> all = new ArrayList<>();

    private static MrpgSkillSpells.Entry add(MrpgSkillSpells.Entry entry) {
        all.add(entry);
        return entry;
    }

    ///FORCEMASTER MODIFIERS
    public static final MrpgSkillSpells.Entry forcemaster_tier_1_spell_1_modifier_1 = add(forcemaster_tier_1_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry forcemaster_tier_1_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_1_spell_1_modifier_1");
        var title = "Extended Stonehand";
        var description = "Increases the amplifier of Stonehand by {stash_amplifier_add}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.forcemasterFighterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:stonehand";
        modifier.stash_amplifier_add = 1;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_1_spell_1_modifier_2 = add(forcemaster_tier_1_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry forcemaster_tier_1_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_1_spell_1_modifier_2");
        var title = "Arcane Fist";
        var description = "Casting Stonehand deals {damage} damage around the caster.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.forcemasterCasterSchool;
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_2_spell_1_modifier_1 = add(forcemaster_tier_2_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry forcemaster_tier_2_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_2_spell_1_modifier_1");
        var title = "Pumped Up";
        var description = "Burstcrack increases your attack damage by {bonus} for {effect_duration} seconds.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.forcemasterFighterSchool;
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_2_spell_1_modifier_2 = add(forcemaster_tier_2_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry forcemaster_tier_2_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_2_spell_1_modifier_2");
        var title = "Powerful Burst";
        var description = "Burst Crack deals {critical_chance_bonus} critical chance bonus.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.forcemasterCasterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:burstcrack";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.critical_chance_bonus = 0.1F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_3_spell_1_modifier_1 = add(forcemaster_tier_3_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry forcemaster_tier_3_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_3_spell_1_modifier_1");
        var title = "Powerful Belial Smashing";
        var description = "Belial Smashing has {trigger_chance} chance to knock up the target.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.forcemasterFighterSchool;
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_3_spell_1_modifier_2 = add(forcemaster_tier_3_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry forcemaster_tier_3_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_3_spell_1_modifier_2");
        var title = "Explosive Belial Smashing";
        var description = "Belial Smashing has {trigger_chance} chance create a arcane explosion, dealing {damage} damage.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.forcemasterCasterSchool;

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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_4_spell_1_modifier_1 = add(forcemaster_tier_4_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry forcemaster_tier_4_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_4_spell_1_modifier_1");
        var title = "Powerful Asalraalaikum";
        var description = " Asalraalaikum damage increased by {power_multiplier}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.forcemasterFighterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:asal";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.power_multiplier = 0.3F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_4_spell_1_modifier_2 = add(forcemaster_tier_4_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry forcemaster_tier_4_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_4_spell_1_modifier_2");
        var title = "Arcane Regeneration";
        var description = "Reduces the cooldown of Asalraalaikum  by {cooldown_duration_deduct} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.forcemasterCasterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:asal";
        modifier.cooldown_duration_deduct = 8;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    ///FORCEMASTER PASSIVES
    public static final MrpgSkillSpells.Entry forcemaster_tier_1_passive_1 = add(forcemaster_tier_1_passive_1());
    private static MrpgSkillSpells.Entry forcemaster_tier_1_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_1_passive_1");
        var title = "Blood Fists";
        var description = "Your melee hits have {trigger_chance} chance, to stack bleeding on the target for {effect_duration} sec.";
        var effect = SpellEngineEffects.BLEED;

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.forcemasterFighterSchool;
        spell.range = 0;
        
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.meleeAttackImpact();
        trigger.chance = 0.4F;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 7F, 0,3);
        MrpgSkillSpells.bleedingDeny(impact);
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_1_passive_2 = add(forcemaster_tier_1_passive_2());
    private static MrpgSkillSpells.Entry forcemaster_tier_1_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_1_passive_2");
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
        spell.school = MrpgSkillSpells.forcemasterCasterSchool;
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
        impact.sound = new Sound(SkillSounds.arcane_radiance.id());
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 1F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_2_passive_1 = add(forcemaster_tier_2_passive_1());
    private static MrpgSkillSpells.Entry forcemaster_tier_2_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_2_passive_1");
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
        spell.school = MrpgSkillSpells.forcemasterFighterSchool;
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_2_passive_2 = add(forcemaster_tier_2_passive_2());
    private static MrpgSkillSpells.Entry forcemaster_tier_2_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_2_passive_2");
        var title = "Calm Mind";
        var description = "Upon rolling, {trigger_chance} chance to slightly reduce active arcane cooldowns.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.forcemasterCasterSchool;
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_3_passive_1 = add(forcemaster_tier_3_passive_1());
    private static MrpgSkillSpells.Entry forcemaster_tier_3_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_3_passive_1");
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
        spell.school = MrpgSkillSpells.forcemasterFighterSchool;
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_3_passive_2 = add(forcemaster_tier_3_passive_2());
    private static MrpgSkillSpells.Entry forcemaster_tier_3_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_3_passive_2");
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
        spell.school = MrpgSkillSpells.forcemasterCasterSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.activeSpellCast(MrpgSkillSpells.forcemasterCasterSchool);
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
}
