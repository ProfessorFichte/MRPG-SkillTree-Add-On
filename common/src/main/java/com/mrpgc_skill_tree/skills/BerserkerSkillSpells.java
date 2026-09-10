package com.mrpgc_skill_tree.skills;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.effect.MRPGCEffects;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.effect.SpellEngineEffects;
import net.spell_engine.api.entity.SpellEntityPredicates;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.fx.Fx;
import net.spell_engine.api.spell.fx.ParticleGroup;
import net.spell_engine.api.spell.fx.ParticleGroupBuilder;
import net.spell_engine.api.spell.fx.Sound;
import net.spell_engine.api.util.TriState;
import net.spell_engine.api.spell.tooltip.TooltipTokens;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;
import net.spell_engine.fx.SpellEngineSounds;
import net.spell_engine.internals.target.SpellTarget;
import com.mrpgc_skill_tree.effect.MrpgSkillEffects;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static net.skill_tree_rpgs.skills.SkillsCommon.*;

public class BerserkerSkillSpells {
    public static final List<MrpgSkillSpells.Entry> all = new ArrayList<>();

    private static MrpgSkillSpells.Entry add(MrpgSkillSpells.Entry entry) {
        all.add(entry);
        return entry;
    }

    public static final MrpgSkillSpells.Entry berserker_tier_2_spell_1_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.BERSERKER, MrpgSkillSpells.berserkerSchool,
            "berserker_tier_2_spell_1_root", "berserker_rpg:wild_rage", "Wild Rage", 0.1F));
    public static final MrpgSkillSpells.Entry berserker_tier_2_spell_1_modifier_1 = add(berserker_tier_2_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry berserker_tier_2_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_2_spell_1_modifier_1");
        var title = "Raging Slashes";
        var description = "While enraged, melee hits have {trigger_chance} chance to inflict Bleeding for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.berserkerSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.meleeAttackImpact();
        trigger.chance = 0.25F;
        var condition = new Spell.TargetCondition();
        condition.entity_predicate_id = MrpgSkillSpells.HAS_RAGE.id().toString();
        trigger.caster_conditions = List.of(condition);
        spell.passive.triggers = List.of(trigger);

        var debuff = SpellBuilder.Impacts.effectAdd(SpellEngineEffects.BLEED.id.toString(), 6, 0, 3);
        MrpgSkillSpells.bleedingDeny(debuff);
        debuff.action.status_effect.refresh_duration = true;
        debuff.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.dripping_blood)
                        .color(Color.BLOOD)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(10).speed(0.01F, 0.1F)));
        spell.impacts = List.of(debuff);

        SpellBuilder.Cost.cooldown(spell, 1F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_2_spell_1_modifier_2 = add(berserker_tier_2_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry berserker_tier_2_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_2_spell_1_modifier_2");
        var title = "Blind with Rage";
        var effect = MrpgSkillEffects.BLIND_WITH_RAGE;
        // Single modifier (damage taken -15%), stored negative while the prose already says
        // "reduce ... by", hence `ABS` - the old mutator passed the raw value and rendered "-15%".
        var description = "Melee Hits with Wild Rage reduce incoming damage by "
                + TooltipTokens.effect(effect.id, 0, null, TooltipTokens.Format.ABS)
                + " for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.berserkerSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "berserker_rpg:wild_rage";

        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(), 10, 0);
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_2_spell_2_root = add(MrpgSkillsCommon.radiusRoot(
            MrpgSkillSpells.Category.BERSERKER, MrpgSkillSpells.berserkerSchool,
            "berserker_tier_2_spell_2_root", "berserker_rpg:apprehend", "Apprehend", 0.5F));
    public static final MrpgSkillSpells.Entry berserker_tier_2_spell_2_modifier_1 = add(berserker_tier_2_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry berserker_tier_2_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_2_spell_2_modifier_1");
        var title = "Extended Arm";
        var description = "Increases the range of Apprehend by {range_add} blocks.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.berserkerSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "berserker_rpg:apprehend";
        modifier.range_add = 1.5F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_2_spell_2_modifier_2 = add(berserker_tier_2_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry berserker_tier_2_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_2_spell_2_modifier_2");
        var title = "Weakening Grasp";
        var description = "Apprehend also inflicts Grievous Wounds, increasing damage taken for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.berserkerSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "berserker_rpg:apprehend";

        var debuff = SpellBuilder.Impacts.effectSet(MRPGCEffects.GRIEVOUS_WOUNDS.id.toString(), 6, 0);
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(debuff);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_3_spell_1_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.BERSERKER, MrpgSkillSpells.berserkerSchool,
            "berserker_tier_3_spell_1_root", "berserker_rpg:bloody_strike", "Bloody Strike", 0.15F));
    public static final MrpgSkillSpells.Entry berserker_tier_3_spell_1_modifier_1 = add(berserker_tier_3_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry berserker_tier_3_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_3_spell_1_modifier_1");
        var title = "Deadly Precision";
        // `{power_multiplier}` rendered literally here: it resolves from a `Spell.Modifier`'s
        // `power_modifier`, and this modifier has none - the value is the appended impact's own
        // coefficient against the target's max health. Resolved by `MrpgSkillSpells.registerTooltipTokens`.
        var description = "Bloody Strike deals an additional " + MrpgSkillSpells.maxHealthPercentToken
                + " of the target's max health as damage.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.berserkerSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "berserker_rpg:bloody_strike";

        var impact = SpellBuilder.Impacts.damage(0.1F, 0F);
        impact.attribute = EntityAttributes.GENERIC_MAX_HEALTH.getIdAsString();
        impact.attribute_from_target = true;
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
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
        area_impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.dripping_blood)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(20).speed(0.15F, 0.15F)));

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;

        var debuff = SpellBuilder.Impacts.effectSet(SpellEngineEffects.BLEED.id.toString(), 6, 0);
        MrpgSkillSpells.bleedingDeny(debuff);
        debuff.action.status_effect.amplifier_power_multiplier = 0.2F;
        debuff.action.status_effect.amplifier_cap = 2;
        debuff.action.status_effect.refresh_duration = true;
        modifier.impacts = List.of(debuff);
        modifier.replacing_area_impact = area_impact;

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_3_spell_2_root = add(MrpgSkillsCommon.cooldownRoot(
            MrpgSkillSpells.Category.BERSERKER, MrpgSkillSpells.berserkerSchool,
            "berserker_tier_3_spell_2_root", "berserker_rpg:outrage", "Outrage", 5F));
    public static final MrpgSkillSpells.Entry berserker_tier_3_spell_2_modifier_1 = add(berserker_tier_3_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry berserker_tier_3_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_3_spell_2_modifier_1");
        var title = "Savage Outrage";
        var description = "Outrage has {critical_damage_bonus} increased critical strike damage.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.berserkerSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "berserker_rpg:outrage";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.critical_damage_bonus = 0.3F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_3_spell_2_modifier_2 = add(berserker_tier_3_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry berserker_tier_3_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_3_spell_2_modifier_2");
        var title = "Reckless Outrage";
        var description = "Melee Hits grant Absorption in trade for reducing the player's health.";
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
        custom.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_stripe, ParticleGroup.Motion.ASCEND, Color.RAGE)
                        // V1 WIDE_PIPE = PIPE at double the entity radius
                        .batch(b -> b.shape(ParticleGroup.Shape.PIPE).widthFactor(2F)
                                .count(20).speed(0.2F, 0.25F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)
                                .extent(0.25F)
                                .invert(true)));

        var buff = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 12, 1,9);
        buff.action.status_effect.refresh_duration = false;
        buff.action.status_effect.amplifier_cap_power_multiplier = 0.2F;
        buff.action.apply_to_caster = true;

        spell.impacts = List.of(custom,buff);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_4_spell_1_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.BERSERKER, MrpgSkillSpells.berserkerSchool,
            "berserker_tier_4_spell_1_root", "berserker_rpg:blood_reckoning", "Blood Reckoning", 0.15F));
    public static final MrpgSkillSpells.Entry berserker_tier_4_spell_1_modifier_1 = add(berserker_tier_4_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry berserker_tier_4_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_4_spell_1_modifier_1");
        var title = "Pain Transmission";
        var description = "Taking damage has {trigger_chance} chance to reflect {damage} damage and bleeding back to the attacker.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.berserkerSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.damageTaken();
        trigger.chance = 0.25F;
        var condition = new Spell.TargetCondition();
        condition.entity_predicate_id = SpellEntityPredicates.HAS_BAD_EFFECT.id().toString();
        trigger.caster_conditions = List.of(condition);
        spell.passive.triggers = List.of(trigger);

        var damage = SpellBuilder.Impacts.damage(0.3F, 0F);
        damage.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.dripping_blood)
                        .color(Color.BLOOD)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(20).speed(0.2F, 0.4F)));
        var debuff = SpellBuilder.Impacts.effectAdd(SpellEngineEffects.BLEED.id.toString(), 6, 0, 3);
        MrpgSkillSpells.bleedingDeny(debuff);
        debuff.action.status_effect.refresh_duration = true;
        spell.impacts = List.of(damage, debuff);

        SpellBuilder.Cost.cooldown(spell, 3F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_4_spell_1_modifier_2 = add(berserker_tier_4_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry berserker_tier_4_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_4_spell_1_modifier_2");
        var title = "Norse Blood Ritual";
        var effect = MRPGCEffects.GRIEVOUS_WOUNDS;
        var description = "Bleeding targets near the caster receive Grievous Wounds, increasing damage taken and reducing healing received, for {effect_duration} sec.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.berserkerSchool;
        spell.range = 6;

        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;

        var trigger = SpellBuilder.Triggers.specificSpellCast("berserker_rpg:blood_reckoning");
        spell.passive.triggers = List.of(trigger);

        var debuff = SpellBuilder.Impacts.effectSet(effect.id.toString(), 6, 0);
        SpellBuilder.configureImpactEnableCondition(debuff,
                SpellBuilder.TargetConditions.ofPredicate(MrpgSkillSpells.HAS_BLEEDING));
        debuff.target_modifiers.get(0).execute = TriState.ALLOW;
        spell.impacts = List.of(debuff);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_4_spell_2_root = add(MrpgSkillsCommon.critDamageRoot(
            MrpgSkillSpells.Category.BERSERKER, MrpgSkillSpells.berserkerSchool,
            "berserker_tier_4_spell_2_root", "berserker_rpg:northerners_guillotine", "Northerners Guillotine", 0.2F));
    public static final MrpgSkillSpells.Entry berserker_tier_4_spell_2_modifier_1 = add(berserker_tier_4_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry berserker_tier_4_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_4_spell_2_modifier_1");
        var effect = MrpgSkillEffects.BLOODFLOW;
        var title = "Norse Warmonger";
        // Single modifier (attack damage), so the token's blank-attribute fallback is unambiguous.
        var description = "Killing a target with Northerners Guillotine grants you Bloodflow, increasing attack damage by "
                + TooltipTokens.effect(effect.id)
                + " for {effect_duration} sec.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.berserkerSchool;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = SpellBuilder.Triggers.specificSpellHit("berserker_rpg:northerners_guillotine");
        trigger.target_conditions = List.of(SpellBuilder.TargetConditions.dead());
        spell.passive.triggers = List.of(trigger);

        var buff = SpellBuilder.Impacts.effectSet(effect.id.toString(), 10, 0);
        buff.action.apply_to_caster = true;
        spell.impacts = List.of(buff);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_4_spell_2_modifier_2 = add(berserker_tier_4_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry berserker_tier_4_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_4_spell_2_modifier_2");
        var title = "Guillotine Ecstasy";
        var description = "Killing a target with Northerners Guillotine resets its cooldown.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.berserkerSchool;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = SpellBuilder.Triggers.specificSpellHit("berserker_rpg:northerners_guillotine");
        trigger.target_conditions = List.of(SpellBuilder.TargetConditions.dead());
        spell.passive.triggers = List.of(trigger);

        var reset = SpellBuilder.Impacts.resetCooldownActive("berserker_rpg:northerners_guillotine");
        reset.action.apply_to_caster = true;
        reset.sound = new Sound(SpellEngineSounds.SPELL_COOLDOWN_IMPACT.id());
        spell.impacts = List.of(reset);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    ///BERSERKER PASSIVES
    public static final MrpgSkillSpells.Entry berserker_tier_1_passive_1 = add(berserker_tier_1_passive_1());
    private static MrpgSkillSpells.Entry berserker_tier_1_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_1_passive_1");
        var title = "Cleave";
        var description = "Melee hits have {trigger_chance} chance to deal {damage} additional damage.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.berserkerSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = SpellBuilder.Triggers.meleeAttackImpact();
        trigger.chance = 0.3F;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.damage(0.2F, 0F);
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.dripping_blood)
                        .color(Color.BLOOD)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(10).speed(0.01F, 0.1F)),
                // V1 `aura_effect_409` was zone/effect_409 registered a second time camera-facing;
                // 1.10 keeps one entry and `Particles.aura` supplies the camera facing + attachment.
                SpellBuilder.Particles.aura(SpellEngineParticles.area_effect_409.id())
                        .appearance(a -> a.color(Color.RAGE.toRGBA())));
        impact.sound = new Sound(MrpgSkillSounds.cleave_impact.id());
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 1F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_1_passive_2 = add(berserker_tier_1_passive_2());
    private static MrpgSkillSpells.Entry berserker_tier_1_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_1_passive_2");
        var effect = MrpgSkillEffects.BLOODFLOW;
        var title = "Bloodfrenzy";
        // Single modifier (attack damage), so the token's blank-attribute fallback is unambiguous.
        var description = "Melee hits against Bleeding targets grant you Bloodflow, increasing attack damage by "
                + TooltipTokens.effect(effect.id)
                + " for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.berserkerSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.meleeAttackImpact();
        var condition = new Spell.TargetCondition();
        condition.entity_predicate_id = MrpgSkillSpells.HAS_BLEEDING.id().toString();
        trigger.target_conditions = List.of(condition);
        spell.passive.triggers = List.of(trigger);

        var buff = SpellBuilder.Impacts.effectSet(effect.id.toString(), 8, 0);
        buff.action.apply_to_caster = true;
        buff.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_heal, ParticleGroup.Motion.DECELERATE, Color.RAGE)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(10).speed(0.01F, 0.1F)));
        buff.sound = new Sound(MrpgSkillSounds.blood_frenzy_heal.id());
        spell.impacts = List.of(buff);

        SpellBuilder.Cost.cooldown(spell, 2F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_2_passive_2 = add(berserker_tier_2_passive_2());
    private static MrpgSkillSpells.Entry berserker_tier_2_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_2_passive_2");
        var title = "Burst of Aggression";
        var effect = MrpgSkillEffects.BURST_OF_AGGRESSION;
        // Two modifiers (movement speed, rage), both +15%. The status effect's modifier map is
        // unordered, so the attribute is named explicitly rather than read by list position.
        var description = "When the player is in rage and is rolling, you gain "
                + TooltipTokens.effect(effect.id, 0,
                        Identifier.of(EntityAttributes.GENERIC_MOVEMENT_SPEED.getIdAsString()))
                + " movement speed and rage for {effect_duration} sec.";

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
        buff.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_stripe, ParticleGroup.Motion.DECELERATE, Color.RAGE)
                        .attached()
                        // V1 WIDE_PIPE = PIPE at double the entity radius
                        .batch(b -> b.shape(ParticleGroup.Shape.PIPE).widthFactor(2F)
                                .count(25).speed(0.3F, 0.8F)
                                .extent(1.0F)),
                SpellBuilder.Particles.area(SpellEngineParticles.area_effect_658.id())
                        .appearance(a -> a.scale(1.5F)
                                .color(Color.RAGE.toRGBA())
                                .attachment(ParticleGroup.Attachment.POSITION))
                        // V1 `.origin(CENTER)` moved it off the ground, onto the entity's centre
                        .batch(b -> b.origin(ParticleGroup.Anchor.ENTITY, ParticleGroupBuilder.Batches.CENTER)));
        buff.sound = new Sound(MrpgSkillSounds.burst_of_aggression.id());
        spell.impacts = List.of(buff);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
    public static final MrpgSkillSpells.Entry berserker_tier_3_passive_1 = add(berserker_tier_3_passive_1());
    private static MrpgSkillSpells.Entry berserker_tier_3_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "berserker_tier_3_passive_1");
        var effect = MrpgSkillEffects.RAGNAROK;
        var title = effect.title;
        // Two modifiers that do NOT share a value: movement speed +30% and tenacity +100%. The old
        // `firstModifier()` read happened to land on movement speed, but the status effect's modifier
        // map is unordered, so it is named explicitly here - picking the wrong one would print "100%".
        var description = "Taking damage with a harmful effect grants you immunity to harmful effects and "
                + TooltipTokens.effect(effect.id, 0,
                        Identifier.of(EntityAttributes.GENERIC_MOVEMENT_SPEED.getIdAsString()))
                + " increased movement speed for {effect_duration} sec.";

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
        buff.visuals = Fx.Visuals.of(
                // V1 `aura_effect_728` was zone/effect_728 registered a second time camera-facing;
                // 1.10 keeps one entry and `Particles.aura` supplies the camera facing + attachment.
                SpellBuilder.Particles.aura(SpellEngineParticles.area_effect_728.id())
                        .appearance(a -> a.scale(1.2F)
                                .color(Color.RAGE.alpha(0.5F).toRGBA())),
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_stripe, ParticleGroup.Motion.FLOAT, Color.RAGE)
                        // V1 WIDE_PIPE = PIPE at double the entity radius
                        .batch(b -> b.shape(ParticleGroup.Shape.PIPE).widthFactor(2F)
                                .count(25).speed(0.2F, 0.6F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)
                                .extent(-0.2F)));
        buff.sound = new Sound(MrpgSkillSounds.ragnarok_release.id());
        spell.impacts = List.of(buff);

        SpellBuilder.Cost.cooldown(spell, 60F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
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
        buff.visuals = Fx.Visuals.of(
                SpellBuilder.Particles.area(SpellEngineParticles.area_effect_658.id())
                        .appearance(a -> a.scale(1.5F).color(Color.RAGE.toRGBA()))
                        // V1 `.origin(CENTER)` moved it off the ground, onto the entity's centre
                        .batch(b -> b.origin(ParticleGroup.Anchor.ENTITY, ParticleGroupBuilder.Batches.CENTER)),
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_stripe, ParticleGroup.Motion.DECELERATE, Color.RAGE)
                        // V1 WIDE_PIPE = PIPE at double the entity radius
                        .batch(b -> b.shape(ParticleGroup.Shape.PIPE).widthFactor(2F)
                                .count(15).speed(0.3F, 0.5F)
                                .invert(true)),
                // NOTE: `berserker_rpg:rage_particle` is a DEAD id — nothing registers a particle
                // in the `berserker_rpg` namespace; the real one is `more_rpg_classes:rage_particle`
                // (MoreParticles.RAGE_PAR). This has never rendered, in V1 or V2. Ported verbatim
                // rather than repaired, because repairing it is a behaviour change.
                ParticleGroupBuilder.of("berserker_rpg:rage_particle")
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(10).speed(0.1F, 0.5F)
                                .preTravel(7F)));
        buff.sound = Sound.withVolume(Identifier.of("berserker_rpg:wild_rage"), 1.3F);
        spell.impacts = List.of(buff);

        SpellBuilder.Cost.cooldown(spell, 60F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BERSERKER));
    }
}
