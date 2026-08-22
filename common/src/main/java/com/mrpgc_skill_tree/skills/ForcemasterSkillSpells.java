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
import net.spell_power.api.SpellPowerMechanics;
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

    public static final MrpgSkillSpells.Entry forcemaster_tier_2_spell_1_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.FORCEMASTER, MrpgSkillSpells.forcemasterFighterSchool,
            "forcemaster_tier_2_spell_1_root", "forcemaster_rpg:stonehand", "Stonehand", 0.15F));
    public static final MrpgSkillSpells.Entry forcemaster_tier_2_spell_1_modifier_1 = add(forcemaster_tier_2_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry forcemaster_tier_2_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_2_spell_1_modifier_1");
        var title = "Shattering Splitters";
        var description = "Auto attacks have {trigger_chance} chance to create an extra area impact, dealing {damage} damage.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.forcemasterFighterSchool;
        spell.range = 3F;

        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;

        var trigger = SpellBuilder.Triggers.meleeAttackImpact();
        trigger.chance = 0.25F;
        spell.passive.triggers = List.of(trigger);

        var damage = SpellBuilder.Impacts.damage(0.2F, 0F);
        damage.action.allow_on_center_target = false;
        damage.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_spark, ParticleGroup.Motion.BURST, Color.ARCANE)
                        .batch(b -> b.shape(ParticleGroup.Shape.CIRCLE)
                                .count(15).speed(0.1F, 0.7F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)));
        spell.impacts = List.of(damage);

        SpellBuilder.Cost.cooldown(spell, 1F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_2_spell_1_modifier_2 = add(forcemaster_tier_2_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry forcemaster_tier_2_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_2_spell_1_modifier_2");
        var title = "Shattering Ground";
        var description = "Casting Stonehand deals {damage} damage around the caster.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.forcemasterCasterSchool;
        spell.range = 5;

        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;

        spell.release.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_spark, ParticleGroup.Motion.BURST, Color.ARCANE)
                        .batch(b -> b.shape(ParticleGroup.Shape.CIRCLE)
                                .count(15).speed(0.1F, 0.7F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)));
        var trigger = SpellBuilder.Triggers.specificSpellCast("forcemaster_rpg:stonehand");
        spell.passive.triggers = List.of(trigger);

        var damage = SpellBuilder.Impacts.damage(0.5F, 0.1F);
        damage.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_arcane, ParticleGroup.Motion.DECELERATE, Color.ARCANE)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(15).speed(0.1F, 0.4F)));
        spell.impacts = List.of(damage);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_3_spell_1_root = add(MrpgSkillsCommon.critRoot(
            MrpgSkillSpells.Category.FORCEMASTER, MrpgSkillSpells.forcemasterFighterSchool,
            "forcemaster_tier_3_spell_1_root", "forcemaster_rpg:belial_smashing", "Belial Smashing", 0.05F));
    public static final MrpgSkillSpells.Entry forcemaster_tier_3_spell_1_modifier_1 = add(forcemaster_tier_3_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry forcemaster_tier_3_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_3_spell_1_modifier_1");
        var title = "Uppercut";
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
        custom.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_medium)
                        .color(Color.WHITE)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(20).speed(0.1F, 0.3F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)
                                .extent(0.25F)));

        spell.impacts = List.of(custom);
        SpellBuilder.Cost.cooldown(spell, 0.5F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_3_spell_1_modifier_2 = add(forcemaster_tier_3_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry forcemaster_tier_3_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_3_spell_1_modifier_2");
        var title = "Belial Reach";
        var description = "Increases the reach of Belial Smashing by {range_add} blocks.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.forcemasterCasterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:belial_smashing";
        modifier.range_add = 1.5F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_4_spell_2_root = add(MrpgSkillsCommon.critRoot(
            MrpgSkillSpells.Category.FORCEMASTER, MrpgSkillSpells.forcemasterFighterSchool,
            "forcemaster_tier_4_spell_2_root", "forcemaster_rpg:asal", "Asalraalaikum", 0.05F));
    public static final MrpgSkillSpells.Entry forcemaster_tier_4_spell_2_modifier_1 = add(forcemaster_tier_4_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry forcemaster_tier_4_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_4_spell_2_modifier_1");
        var title = "Powerful Asalraalaikum";
        var description = "Asalraalaikum has {critical_chance_bonus} increased critical strike chance.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.forcemasterFighterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:asal";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.critical_chance_bonus = 0.15F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_4_spell_2_modifier_2 = add(forcemaster_tier_4_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry forcemaster_tier_4_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_4_spell_2_modifier_2");
        var title = "Arcane Regeneration";
        // `{power_multiplier}` rendered literally here: it resolves from a `Spell.Modifier`'s
        // `power_modifier`, and this spell has no modifiers at all - the value is the heal impact's
        // own coefficient against max health. Resolved by `MrpgSkillSpells.registerTooltipTokens`.
        var description = "Killing a target with Asalraalaikum heals you for " + MrpgSkillSpells.maxHealthPercentToken
                + " of your max health.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.forcemasterCasterSchool;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = SpellBuilder.Triggers.specificSpellHit("forcemaster_rpg:asal");
        trigger.target_conditions = List.of(SpellBuilder.TargetConditions.dead());
        spell.passive.triggers = List.of(trigger);

        var heal = SpellBuilder.Impacts.heal(0.5F);
        heal.attribute = EntityAttributes.GENERIC_MAX_HEALTH.getIdAsString();
        heal.attribute_from_target = false;
        heal.action.apply_to_caster = true;
        heal.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_heal, ParticleGroup.Motion.BURST, Color.ARCANE)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(25).speed(0.25F, 0.6F)));
        spell.impacts = List.of(heal);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_3_spell_2_root = add(MrpgSkillsCommon.critRoot(
            MrpgSkillSpells.Category.FORCEMASTER, MrpgSkillSpells.forcemasterFighterSchool,
            "forcemaster_tier_3_spell_2_root", "forcemaster_rpg:nen_sphere", "Nen Sphere", 0.05F));
    public static final MrpgSkillSpells.Entry forcemaster_tier_3_spell_2_modifier_1 = add(forcemaster_tier_3_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry forcemaster_tier_3_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_3_spell_2_modifier_1");
        var title = "Sphere Mastery";
        var description = "Nen Sphere has {critical_chance_bonus} increased critical strike chance.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.forcemasterFighterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:nen_sphere";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.critical_chance_bonus = 0.15F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_3_spell_2_modifier_2 = add(forcemaster_tier_3_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry forcemaster_tier_3_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_3_spell_2_modifier_2");
        var title = "Continuous Shooting";
        var description = "Casting Nen Sphere has {trigger_chance} chance to immediately fire an additional, weaker sphere.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.forcemasterCasterSchool;
        spell.range = 10;

        var trigger = SpellBuilder.Triggers.specificSpellCast("forcemaster_rpg:nen_sphere");
        trigger.chance = 0.2F;
        spell.passive.triggers = List.of(trigger);

        spell.target.type = Spell.Target.Type.NONE;
        spell.deliver.type = Spell.Delivery.Type.CUSTOM;
        spell.deliver.custom = new Spell.Delivery.Custom();
        spell.deliver.custom.handler = "forcemaster_rpg:nen_sphere";

        var damage = SpellBuilder.Impacts.damage(0.35F);
        damage.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_arcane, ParticleGroup.Motion.BURST, Color.ARCANE)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(10).speed(0.2F, 0.4F)));
        spell.impacts = List.of(damage);

        SpellBuilder.Cost.cooldown(spell, 1F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    ///FORCEMASTER PASSIVES
    public static final MrpgSkillSpells.Entry forcemaster_tier_1_passive_1 = add(forcemaster_tier_1_passive_1());
    private static MrpgSkillSpells.Entry forcemaster_tier_1_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_1_passive_1");
        var title = "Blood Fists";
        var description = "Your melee hits have {trigger_chance} chance to stack bleeding on the target for {effect_duration} sec.";
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
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.dripping_blood)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(20).speed(0.15F, 0.15F)));
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 5F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_1_passive_2 = add(forcemaster_tier_1_passive_2());
    private static MrpgSkillSpells.Entry forcemaster_tier_1_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_1_passive_2");
        var title = "Force Release";
        var effect = MrpgSkillEffects.FORCE_RELEASE;
        // Two modifiers (spell haste, spell crit chance), both +2%. The status effect's modifier map
        // is unordered, so the attribute is named explicitly rather than read by list position.
        var description = "Arcane spell impacts have {trigger_chance} chance to increase spell haste & spell crit chance by "
                + TooltipTokens.effect(effect.id, 0, SpellPowerMechanics.HASTE.id)
                + " for {effect_duration} sec. The effect can be stacked {effect_amplifier_cap} times.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.forcemasterCasterSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.activeSpellHit(0.25F,"arcane");
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        trigger.chance = 0.25F;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 10F, 1,5);
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.area_circle_1)
                        .color(Color.ARCANE)
                        .scale(0.8F)
                        // V1 max_age 0.8 -> playback_speed is its reciprocal
                        .playbackSpeed(1.25F)
                        .attached()
                        .batch(b -> b.shape(ParticleGroup.Shape.LINE_VERTICAL)
                                .count(1).speed(0.2F, 0.2F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)));
        impact.sound = new Sound(SkillSounds.arcane_radiance.id());
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 1F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_2_passive_1 = add(forcemaster_tier_2_passive_1());
    private static MrpgSkillSpells.Entry forcemaster_tier_2_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_2_passive_1");
        var title = "Flying Fists";
        var effect = MrpgSkillEffects.FLYING_FISTS;
        // Single modifier (attack speed), so the token's blank-attribute fallback is unambiguous.
        var description = "Upon rolling, attack speed gets increased by "
                + TooltipTokens.effect(effect.id)
                + " for {effect_duration} sec.";


        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.forcemasterFighterSchool;
        spell.range = 0;

        spell.passive.triggers = List.of(SpellBuilder.Triggers.roll());

        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 5, 0, 0);
        impact.sound = new Sound(MrpgSkillSounds.flying_fists.id());
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_stripe, ParticleGroup.Motion.FLOAT, MIGHT_COLOR)
                        // V1 WIDE_PIPE = PIPE at double the entity radius
                        .batch(b -> b.shape(ParticleGroup.Shape.PIPE).widthFactor(2F)
                                .count(20).speed(0.05F, 0.1F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)),
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_fist.id(), MIGHT_COLOR));
        spell.impacts = List.of(impact);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
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
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_stripe, ParticleGroup.Motion.FLOAT, Color.ARCANE)
                        // V1 WIDE_PIPE = PIPE at double the entity radius
                        .batch(b -> b.shape(ParticleGroup.Shape.PIPE).widthFactor(2F)
                                .count(20).speed(0.05F, 0.1F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)),
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_hourglass.id(), Color.ARCANE));
        spell.impacts = List.of(impact);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_3_passive_1 = add(forcemaster_tier_3_passive_1());
    private static MrpgSkillSpells.Entry forcemaster_tier_3_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_3_passive_1");
        var title = "Sury's Tenacity";
        final var healthThreshold = 0.3F;
        var effect = MrpgSkillEffects.SURYS_TENACITY;
        // Three modifiers that do NOT all share a value: attack speed +20%, movement speed +20% and
        // tenacity +100%. The old `firstModifier()` read happened to land on attack speed, but the
        // status effect's modifier map is unordered, so it is named explicitly - picking tenacity
        // would print "100%". The health threshold is a compile-time constant of this mod, so it is
        // baked in (`bakedPercent` doubles the `%` for `I18n.translate` -> `String.format`).
        var description = "When taking damage below " + TooltipTokens.bakedPercent(healthThreshold)
                + ", attack & movement speed gets increased by "
                + TooltipTokens.effect(effect.id, 0,
                        Identifier.of(EntityAttributes.GENERIC_ATTACK_SPEED.getIdAsString()))
                + " and the caster is immune to harmful effects for {effect_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.forcemasterFighterSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.becomingLowHP(healthThreshold);
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 5, 0, 0);
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_arcane, ParticleGroup.Motion.DECELERATE, MIGHT_COLOR)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(30).speed(0.5F, 0.5F)),
                // V1 `aura_effect_642` was zone/effect_642 registered a second time camera-facing.
                // 1.10 keeps one entry, so the aura role is the facing override.
                ParticleGroupBuilder.of(SpellEngineParticles.area_effect_642)
                        .facing(ParticleGroup.Facing.CAMERA)
                        .color(MIGHT_COLOR)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(1).speed(0F, 0F)));
        impact.sound = new Sound(MrpgSkillSounds.surys_tenacity.id());
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell,40);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_3_passive_2 = add(forcemaster_tier_3_passive_2());
    private static MrpgSkillSpells.Entry forcemaster_tier_3_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_3_passive_2");
        var title = "Sury's Grace";
        var effect = MrpgSkillEffects.SURYS_GRACE;
        // Two modifiers (arcane spell power, spell haste), both +20%. The status effect's modifier map
        // is unordered, so the attribute is named explicitly rather than read by list position.
        var description = "Casting Forcemaster Spells has a {trigger_chance} to increase arcane spell power & spell haste by "
                + TooltipTokens.effect(effect.id, 0, SpellSchools.ARCANE.id)
                + " for {effect_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.forcemasterCasterSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.activeSpellCast(MrpgSkillSpells.forcemasterCasterSchool);
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 10, 0, 0);
        impact.sound = new Sound(MrpgSkillSounds.surys_grace.id());
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_arcane, ParticleGroup.Motion.DECELERATE,
                                Color.from(SpellSchools.ARCANE.color))
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(30).speed(0.5F, 0.5F)),
                // V1 `aura_effect_642` was zone/effect_642 registered a second time camera-facing.
                ParticleGroupBuilder.of(SpellEngineParticles.area_effect_642)
                        .facing(ParticleGroup.Facing.CAMERA)
                        .color(Color.from(SpellSchools.ARCANE.color))
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(1).speed(0F, 0F)));
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell,60);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_2_spell_2_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.FORCEMASTER, MrpgSkillSpells.forcemasterCasterSchool,
            "forcemaster_tier_2_spell_2_root", "forcemaster_rpg:baraqijal_esna", "Baraqijal Esna", 0.15F));
    public static final MrpgSkillSpells.Entry forcemaster_tier_2_spell_2_modifier_1 = add(forcemaster_tier_2_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry forcemaster_tier_2_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_2_spell_2_modifier_1");
        var title = "Baraqijal's Wrath";
        var description = "Increases the duration of Baraqijal Esna's stacking debuff by {effect_duration_add} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.forcemasterCasterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:baraqijal_esna";
        modifier.effect_duration_add = 3F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_2_spell_2_modifier_2 = add(forcemaster_tier_2_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry forcemaster_tier_2_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_2_spell_2_modifier_2");
        var title = "Piercing Light";
        var description = "Baraqijal Esna pierces through targets, hitting up to 2 additional enemies.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.forcemasterCasterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:baraqijal_esna";
        modifier.projectile_perks = Spell.ProjectileData.Perks.EMPTY();
        modifier.projectile_perks.pierce = 2;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_4_spell_1_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.FORCEMASTER, MrpgSkillSpells.forcemasterFighterSchool,
            "forcemaster_tier_4_spell_1_root", "forcemaster_rpg:sonic_hand", "Sonic Hand", 0.05F));
    public static final MrpgSkillSpells.Entry forcemaster_tier_4_spell_1_modifier_1 = add(forcemaster_tier_4_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry forcemaster_tier_4_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_4_spell_1_modifier_1");
        var title = "Focused Palms";
        var description = "Casting Sonic Hand increases your attack damage by {bonus} for {effect_duration} seconds.";
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
        modifier.spell_pattern = "forcemaster_rpg:sonic_hand";
        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(), 8, 0);
        impact.action.apply_to_caster = true;

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
    public static final MrpgSkillSpells.Entry forcemaster_tier_4_spell_1_modifier_2 = add(forcemaster_tier_4_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry forcemaster_tier_4_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "forcemaster_tier_4_spell_1_modifier_2");
        var title = "Rapid Palms";
        var description = "Reduces the cooldown of Sonic Hand by {cooldown_duration_deduct} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.forcemasterFighterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:sonic_hand";
        modifier.cooldown_duration_deduct = 6F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.FORCEMASTER));
    }
}
