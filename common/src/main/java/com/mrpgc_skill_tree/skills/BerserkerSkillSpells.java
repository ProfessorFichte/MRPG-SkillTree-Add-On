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
import net.spell_engine.api.effect.SpellEngineEffects;
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

public class BerserkerSkillSpells {
    public static final List<MrpgSkillSpells.Entry> all = new ArrayList<>();

    private static MrpgSkillSpells.Entry add(MrpgSkillSpells.Entry entry) {
        all.add(entry);
        return entry;
    }

    ///BERSERKER MODIFIERS
    public static final MrpgSkillSpells.Entry berserker_tier_1_spell_1_modifier_1 = add(berserker_tier_1_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry berserker_tier_1_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_1_spell_1_modifier_1");
        var title = "Enraged";
        var description = "Increases the maximum number of Wild Rage stacks by {effect_amplifier_cap_add}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.berserkerSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "berserker_rpg:wild_rage";
        modifier.effect_amplifier_cap_add = 2;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_1_spell_1_modifier_2 = add(berserker_tier_1_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry berserker_tier_1_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_1_spell_1_modifier_2");
        var title = "Blind with Rage";
        var effect = MrpgSkillEffects.BLIND_WITH_RAGE;
        var description = "Melee Hits with Wild Rage reduces incoming damage by {bonus} for {effect_duration} sec.";
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description().replace("{bonus}", bonus);
        };
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.berserkerSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "berserker_rpg:wild_rage";

        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(), 10, 0);
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_2_spell_1_modifier_1 = add(berserker_tier_2_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry berserker_tier_2_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_2_spell_1_modifier_1");
        var title = "Bloodflow";
        var effect = MrpgSkillEffects.BLOODFLOW;
        var description = "Blood Reckoning increases attack damage by {bonus} for {effect_duration} sec.";
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description().replace("{bonus}", bonus);
        };
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.berserkerSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "berserker_rpg:blood_reckoning";

        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(), 8, 0);
        impact.action.apply_to_caster = true;
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_2_spell_1_modifier_2 = add(berserker_tier_2_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry berserker_tier_2_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_2_spell_1_modifier_2");
        var title = "Norse Blood Ritual";
        var description = "Bleeding Targets near the caster now receive {damage} damage.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.berserkerSchool;
        spell.range = 6;

        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;

        var trigger = SpellBuilder.Triggers.specificSpellCast("berserker_rpg:blood_reckoning");
        spell.passive.triggers = List.of(trigger);


        var damage = SpellBuilder.Impacts.damage(0.4F, 0F);
        SpellBuilder.configureImpactEnableCondition(damage,
                SpellBuilder.TargetConditions.ofPredicate(MrpgSkillSpells.HAS_BLEEDING));
        damage.target_modifiers.get(0).execute = TriState.ALLOW;
        spell.impacts = List.of(damage);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_3_spell_1_modifier_1 = add(berserker_tier_3_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry berserker_tier_3_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_3_spell_1_modifier_1");
        var title = "Deadly Precision";
        var description = "Bloody Strike deals {power_multiplier} more damage.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.berserkerSchool;
        spell.range = 0;

        var bonus = 0.25F;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "berserker_rpg:bloody_strike";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.power_multiplier = bonus;
        spell.modifiers = List.of(modifier);


        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_3_spell_1_modifier_2 = add(berserker_tier_3_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry berserker_tier_3_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_3_spell_1_modifier_2");
        var title = "Slicing Maelstorm";
        var description = "Bloody Strike inflicts bleeding around the target for {effect_duration} seconds.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.berserkerSchool;

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

        var debuff = SpellBuilder.Impacts.effectSet(SpellEngineEffects.BLEED.id.toString(), 6, 0);
        MrpgSkillSpells.bleedingDeny(debuff);
        debuff.action.status_effect.amplifier_power_multiplier = 0.2F;
        debuff.action.status_effect.amplifier_cap = 2;
        debuff.action.status_effect.refresh_duration = true;
        modifier.impacts = List.of(debuff);
        modifier.replacing_area_impact = area_impact;

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_4_spell_1_modifier_1 = add(berserker_tier_4_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry berserker_tier_4_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_4_spell_1_modifier_1");
        var title = "Savage Outrage";
        var description = "Outrage now deals {damage} damage around the caster.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.berserkerSchool;
        spell.range_mechanic = Spell.RangeMechanic.MELEE;

        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        var trigger = SpellBuilder.Triggers.specificSpellCast("berserker_rpg:outrage");
        spell.passive.triggers = List.of(trigger);

        var damage = SpellBuilder.Impacts.damage(0.3F, 0.2F);
        spell.impacts = List.of(damage);


        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_4_spell_1_modifier_2 = add(berserker_tier_4_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry berserker_tier_4_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_4_spell_1_modifier_2");
        var title = "Reckless Outrage";
        var description = "Melee Hits grants Absorption in trade for reducing the players health.";
        var effect = MrpgSkillEffects.RECKLESS_RAGE;
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.berserkerSchool;

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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    ///BERSERKER PASSIVES
    public static final MrpgSkillSpells.Entry berserker_tier_1_passive_1 = add(berserker_tier_1_passive_1());
    private static MrpgSkillSpells.Entry berserker_tier_1_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_1_passive_1");
        var title = "Cleave";
        var description = "Hitting enemies has {trigger_chance} to stack grievous wounds up to {effect_amplifier_cap} for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.berserkerSchool;
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_1_passive_2 = add(berserker_tier_1_passive_2());
    private static MrpgSkillSpells.Entry berserker_tier_1_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_1_passive_2");
        var title = "Bloodfrenzy";
        var description = "Melee Hits have a {trigger_chance} to inflict bleeding and healing yourself for {heal} hearts.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.berserkerSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.meleeAttackImpact();
        trigger.chance = 0.2F;
        spell.passive.triggers = List.of(trigger);

        var debuff = SpellBuilder.Impacts.effectAdd(SpellEngineEffects.BLEED.id.toString(), 6, 0,3);
        MrpgSkillSpells.bleedingDeny(debuff);
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_2_passive_1 = add(berserker_tier_2_passive_1());
    private static MrpgSkillSpells.Entry berserker_tier_2_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_2_passive_1");
        var title = "Spinning Slash";
        var description = "While rolling, you slash and deal {damage} damage to nearby enemies.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.berserkerSchool;
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_2_passive_2 = add(berserker_tier_2_passive_2());
    private static MrpgSkillSpells.Entry berserker_tier_2_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_2_passive_2");
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
        spell.school = MrpgSkillSpells.berserkerSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        var condition = new Spell.TargetCondition();
        condition.entity_predicate_id = MrpgSkillSpells.HAS_RAGE.id().toString();
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_3_passive_1 = add(berserker_tier_3_passive_1());
    private static MrpgSkillSpells.Entry berserker_tier_3_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_3_passive_1");
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
        spell.school = MrpgSkillSpells.berserkerSchool;
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_3_passive_2 = add(berserker_tier_3_passive_2());
    private static MrpgSkillSpells.Entry berserker_tier_3_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_3_passive_2");
        var effect = MrpgSkillEffects.UNDYING_RAGE;
        var title = effect.title;
        var description = "When taking damage that would be fatal, you become invulnerable for {effect_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.berserkerSchool;
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
}
