package com.mrpgc_skill_tree.skills;

import net.minecraft.util.Identifier;
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
import com.mrpgc_skill_tree.effect.MrpgSkillEffects;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class WarArcherSkillSpells {
    public static final List<MrpgSkillSpells.Entry> all = new ArrayList<>();

    private static MrpgSkillSpells.Entry add(MrpgSkillSpells.Entry entry) {
        all.add(entry);
        return entry;
    }

    public static final MrpgSkillSpells.Entry war_archer_tier_2_spell_1_root = add(MrpgSkillsCommon.radiusRoot(
            MrpgSkillSpells.Category.WAR_ARCHER, MrpgSkillSpells.warArcherSchool,
            "war_archer_tier_2_spell_1_root", "archers_expansion:smoldering_arrow", "Smoldering Arrow", 0.5F));
    public static final MrpgSkillSpells.Entry war_archer_tier_2_spell_1_modifier_1 = add(war_archer_tier_2_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry war_archer_tier_2_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_2_spell_1_modifier_1");
        var title = "Expanded Smoldering Arrow";

        var bonus = 0.5F;

        // A compile-time constant of this mod, not anything the spell data carries, so it is baked
        // into the description (`bakedPercent` doubles the `%`: the lang value goes through
        // `I18n.translate` -> `String.format`).
        var description = "Increases the area of effect of Smoldering Arrow by "
                + TooltipTokens.bakedPercent(bonus) + ".";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.warArcherSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:smoldering_arrow";
        var extendedRadius = 2.0F * (1F + bonus);
        modifier.replacing_area_impact = new Spell.AreaImpact();
        Spell.AreaImpact area_impact = new Spell.AreaImpact();
        area_impact.radius = extendedRadius;
        area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        area_impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.fire_explosion)
                        .scale(extendedRadius / 2)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(1.0F).speed(0.0F, 0.0F)),
                ParticleGroupBuilder.of(SpellEngineParticles.flame_medium_b)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(25.0F).speed(0.1F, 0.3F).preTravel(2)),
                ParticleGroupBuilder.of(SpellEngineParticles.flame_medium_b)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(25.0F).speed(0.2F, 0.4F).preTravel(4)));
        modifier.replacing_area_impact.sound = new Sound("entity.generic.explode");

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_2_spell_1_modifier_2 = add(war_archer_tier_2_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry war_archer_tier_2_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_2_spell_1_modifier_2");
        var title = "Explosive Push";
        var description = "Increases the knockback of Smoldering Arrow by {knockback_multiply_base}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.warArcherSchool;

        var bonus = 0.5F;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:smoldering_arrow";
        modifier.knockback_multiply_base = bonus;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_2_spell_2_root = add(MrpgSkillsCommon.critRoot(
            MrpgSkillSpells.Category.WAR_ARCHER, MrpgSkillSpells.warArcherSchool,
            "war_archer_tier_2_spell_2_root", "archers_expansion:dual_shot", "Dual Shot", 0.05F));
    public static final MrpgSkillSpells.Entry war_archer_tier_2_spell_2_modifier_1 = add(war_archer_tier_2_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry war_archer_tier_2_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_2_spell_2_modifier_1");
        var title = "Instant Reload";
        var description = "Dual Shot has {trigger_chance} chance to reset its own cooldown.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.warArcherSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.specificSpellHit("archers_expansion:dual_shot");
        trigger.chance = 0.25F;
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        var reset = SpellBuilder.Impacts.resetCooldownActive("archers_expansion:dual_shot");
        reset.action.apply_to_caster = true;
        reset.sound = new Sound(SpellEngineSounds.SPELL_COOLDOWN_IMPACT.id());
        spell.impacts = List.of(reset);

        SpellBuilder.Cost.cooldown(spell, 5F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_2_spell_2_modifier_2 = add(war_archer_tier_2_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry war_archer_tier_2_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_2_spell_2_modifier_2");
        var title = "Heavy Arrow Tips";
        var description = "Increases the knockback of Dual Shot by {knockback_multiply_base}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.warArcherSchool;

        var bonus = 1.0F;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:dual_shot";
        modifier.knockback_multiply_base = bonus;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_3_spell_1_root = add(MrpgSkillsCommon.cooldownRoot(
            MrpgSkillSpells.Category.WAR_ARCHER, MrpgSkillSpells.warArcherSchool,
            "war_archer_tier_3_spell_1_root", "archers_expansion:explosive_barrel", "Explosive Barrel", 2F));
    public static final MrpgSkillSpells.Entry war_archer_tier_3_spell_1_modifier_1 = add(war_archer_tier_3_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry war_archer_tier_3_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_3_spell_1_modifier_1");
        var title = "Potent Saltpeter";
        var description = "Increases the range of the Explosive Barrel's explosion by {range_add} blocks.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.warArcherSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:explosive_barrel_explosion";
        modifier.range_add = 2F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_3_spell_1_modifier_2 = add(war_archer_tier_3_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry war_archer_tier_3_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_3_spell_1_modifier_2");
        var title = "Barrel Supply";
        var description = "Explosive Barrel has {impact_chance} chance to place 2 additional barrels nearby.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.warArcherSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:explosive_barrel";

        var spawnImpact = new Spell.Impact();
        spawnImpact.chance = 0.5F;
        spawnImpact.action = new Spell.Impact.Action();
        spawnImpact.action.type = Spell.Impact.Action.Type.SPAWN;
        spawnImpact.action.apply_to_caster = true;

        var spawnA = new Spell.Impact.Action.Spawn();
        spawnA.entity_type_id = "archers_expansion:explosive_barrel";
        spawnA.time_to_live_seconds = 32;
        spawnA.placement.location_offset_by_look = 2.0F;
        spawnA.placement.location_yaw_offset = 90F;
        spawnA.placement.apply_yaw = true;

        var spawnB = spawnA.copy();
        spawnB.placement = spawnA.placement.copy();
        spawnB.placement.location_yaw_offset = -90F;

        spawnImpact.action.spawns = List.of(spawnA, spawnB);

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(spawnImpact);
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_3_spell_2_root = add(MrpgSkillsCommon.critRoot(
            MrpgSkillSpells.Category.WAR_ARCHER, MrpgSkillSpells.warArcherSchool,
            "war_archer_tier_3_spell_2_root", "archers_expansion:point_blank_shot", "Point Blank Shot", 0.05F));
    public static final MrpgSkillSpells.Entry war_archer_tier_3_spell_2_modifier_1 = add(war_archer_tier_3_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry war_archer_tier_3_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_3_spell_2_modifier_1");
        var title = "Stronger Point Blank Shot";
        var description = "Point Blank Shot deals {power_multiplier} more damage.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.warArcherSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:point_blank_shot";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.power_multiplier = 0.3F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_3_spell_2_modifier_2 = add(war_archer_tier_3_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry war_archer_tier_3_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_3_spell_2_modifier_2");
        var title = "Heavy Point Blank Shot";
        var description = "Point Blank Shot has {trigger_chance} chance to stun the target.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.warArcherSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.specificSpellHit("archers_expansion:point_blank_shot");
        trigger.chance = 0.2F;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.stun(3F);
        spell.impacts = List.of(impact);
        SpellBuilder.Cost.cooldown(spell, 1F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_4_spell_1_root = add(MrpgSkillsCommon.radiusRoot(
            MrpgSkillSpells.Category.WAR_ARCHER, MrpgSkillSpells.warArcherSchool,
            "war_archer_tier_4_spell_1_root", "archers_expansion:scorched_earth", "Scorched Earth", 0.5F));
    private static List<Spell.EntityPlacement> scorchedEarthTrailPlacements(float fromOffset, float toOffset) {
        var placements = new ArrayList<Spell.EntityPlacement>();
        for (float offset = fromOffset; offset <= toOffset; offset += 3F) {
            var placement = new Spell.EntityPlacement();
            placement.delay_ticks = 2;
            placement.location_offset_by_look = offset;
            placements.add(placement);
        }
        return placements;
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_4_spell_1_modifier_1 = add(war_archer_tier_4_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry war_archer_tier_4_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_4_spell_1_modifier_1");
        var title = "Wild Flames";
        var description = "Extends the flame trail left behind by Scorched Earth much further.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.warArcherSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:scorched_earth";
        modifier.additional_placements = scorchedEarthTrailPlacements(53F, 80F);
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_4_spell_1_modifier_2 = add(war_archer_tier_4_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry war_archer_tier_4_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_4_spell_1_modifier_2");
        var effect = MrpgSkillEffects.BLOODFLOW;
        var title = "Charge from the Flames";
        // Single modifier (attack damage), so the token's blank-attribute fallback is unambiguous.
        var description = "Casting Scorched Earth grants you and nearby allies Bloodflow, increasing attack damage by "
                + TooltipTokens.effect(effect.id)
                + " for {effect_duration} sec.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.warArcherSchool;

        var trigger = SpellBuilder.Triggers.specificSpellCast("archers_expansion:scorched_earth");
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        spell.deliver.type = Spell.Delivery.Type.CLOUD;
        var cloud = new Spell.Delivery.Cloud();
        cloud.volume.radius = 3.0F;
        cloud.volume.area.vertical_range_multiplier = 0.5F;
        cloud.time_to_live_seconds = 1.5F;
        cloud.impact_tick_interval = 10;
        cloud.placement = new Spell.EntityPlacement();
        cloud.placement.location_offset_by_look = 2.0F;
        cloud.additional_placements = scorchedEarthTrailPlacements(5F, 50F);
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        cloud.client_data.particles = List.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_stripe, ParticleGroup.Motion.FLOAT)
                        .color(Color.RED.toRGBA())
                        .batch(b -> b.shape(ParticleGroup.Shape.PIPE).widthFactor(2F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)
                                .count(5).speed(0.1F, 0.2F)));
        spell.deliver.clouds = List.of(cloud);

        var buff = SpellBuilder.Impacts.effectSet(effect.id.toString(), 4, 0);
        buff.action.status_effect.refresh_duration = true;
        spell.impacts = List.of(buff);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_4_spell_2_root = add(MrpgSkillsCommon.lingerRoot(
            MrpgSkillSpells.Category.WAR_ARCHER, MrpgSkillSpells.warArcherSchool,
            "war_archer_tier_4_spell_2_root", "archers_expansion:pin_down", "Pin Down", 1F));
    public static final MrpgSkillSpells.Entry war_archer_tier_4_spell_2_modifier_1 = add(war_archer_tier_4_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry war_archer_tier_4_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_4_spell_2_modifier_1");
        var title = "Deep Wounding Shot";
        var description = "Pin Down additionally inflicts a strong Bleeding effect for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.warArcherSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:pin_down";

        var debuff = SpellBuilder.Impacts.effectAdd(SpellEngineEffects.BLEED.id.toString(), 6, 1, 2);
        debuff.action.status_effect.refresh_duration = true;
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(debuff);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_4_spell_2_modifier_2 = add(war_archer_tier_4_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry war_archer_tier_4_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_4_spell_2_modifier_2");
        var title = "Increased Pin Down";
        var description = "Increases the effect duration of Pin Down by {effect_duration_add}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.warArcherSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:pin_down";
        modifier.effect_duration_add = 3;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    ///WAR ARCHER PASSIVES
    public static final MrpgSkillSpells.Entry war_archer_tier_1_passive_1 = add(war_archer_tier_1_passive_1());
    private static MrpgSkillSpells.Entry war_archer_tier_1_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_1_passive_1");
        var title = "Bombardment";
        var description = "If the target is on fire, create an explosion dealing {damage} damage.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.warArcherSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.arrowHit();
        var condition = new Spell.TargetCondition();
        condition.entity_predicate_id = SpellEntityPredicates.IS_ON_FIRE.id().toString();
        trigger.target_conditions = List.of(condition);
        spell.passive.triggers = List.of(trigger);


        var impact = SpellBuilder.Impacts.damage(0.35F, 0.75F);
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.flame_medium_a)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(10).speed(0.1F, 0.5F)),
                ParticleGroupBuilder.of(SpellEngineParticles.flame_medium_b)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(10).speed(0.1F, 0.5F)));
        impact.sound = new Sound("entity.generic.explode");
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 5F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_1_passive_2 = add(war_archer_tier_1_passive_2());
    private static MrpgSkillSpells.Entry war_archer_tier_1_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_1_passive_2");
        var title = "Protector of the Tower";
        var description = "Your arrow hits have {trigger_chance} chance to increase your armor and knockback resistance for {effect_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.warArcherSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = SpellBuilder.Triggers.arrowHit();
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        trigger.chance = 0.35F;
        spell.passive.triggers = List.of(trigger);


        var impact = SpellBuilder.Impacts.effectAdd(MrpgSkillEffects.TOWER_PROTECTOR.id.toString(), 8,1,5);
        impact.visuals = Fx.Visuals.of(
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_shield.id(), Color.RED));
        impact.sound = new Sound(MrpgSkillSounds.protector_of_the_tower.id());
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 1F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_2_passive_1 = add(war_archer_tier_2_passive_1());
    private static MrpgSkillSpells.Entry war_archer_tier_2_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_2_passive_1");
        var title = "Reloading";
        var description = "When rolling you recharge Smoldering Arrows, you can now stack up to {effect_amplifier_cap} times.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.warArcherSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectAdd("archers_expansion:smoldering_arrows", 10, 1,4);
        impact.action.status_effect.refresh_duration = true;
        impact.visuals = Fx.Visuals.of(
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_arrow.id(), Color.RED));
        impact.sound = new Sound(SpellEngineSounds.GENERIC_FIRE_IGNITE.id());
        spell.impacts = List.of(impact);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_2_passive_2 = add(war_archer_tier_2_passive_2());
    private static MrpgSkillSpells.Entry war_archer_tier_2_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_2_passive_2");
        var title = "Reposition";
        var description = "Rolling has {trigger_chance} chance to clear 1 negative effect.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.warArcherSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        trigger.chance = 0.5F;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectCleanse();
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_spark, ParticleGroup.Motion.ASCEND, Color.RED)
                        .batch(b -> b.shape(ParticleGroup.Shape.PIPE)
                                .count(20).speed(0.2F, 0.4F)));
        impact.sound = new Sound(SpellEngineSounds.GENERIC_DISPEL_1.id());
        spell.impacts = List.of(impact);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static MrpgSkillSpells.Entry war_archer_tier_3_passive_1 = add(war_archer_tier_3_passive_1());
    private static MrpgSkillSpells.Entry war_archer_tier_3_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_3_passive_1");
        var title = "Rapid Fire";
        var description = "{trigger_chance} chance to shoot 4 additional arrows.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school =MrpgSkillSpells.warArcherSchool;

        var trigger = new Spell.Trigger();
        trigger.type = Spell.Trigger.Type.ARROW_SHOT;
        trigger.chance = 0.2F;
        spell.passive.triggers = List.of(trigger);

        spell.release.visuals = Fx.Visuals.of(
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_arrow.id(), Color.RED));

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

        return new MrpgSkillSpells.Entry(id, spell, title, description, MrpgSkillSpells.Category.WAR_ARCHER);
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_3_passive_2 = add(war_archer_tier_3_passive_2());
    private static MrpgSkillSpells.Entry war_archer_tier_3_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_3_passive_2");
        var title = "Last Stand";
        final var healthThreshold = 0.3F;
        var effect = MrpgSkillEffects.LAST_STAND;
        // The threshold is a compile-time constant of this mod, so it is baked into the description
        // (`bakedPercent` doubles the `%`: `I18n.translate` feeds the lang value to `String.format`).
        var description = "Upon taking damage below " + TooltipTokens.bakedPercent(healthThreshold)
                + " health you gain Last Stand effect, increasing your size, ranged haste & decreasing incoming damage for {effect_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.warArcherSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.becomingLowHP(healthThreshold);
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        var buff = SpellBuilder.Impacts.effectSet(effect.id.toString(), 7, 0);
        buff.visuals = Fx.Visuals.of(
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_shield.id(), Color.RED));
        buff.sound = new Sound(MrpgSkillSounds.last_stand.id());
        spell.impacts = List.of(buff);

        SpellBuilder.Cost.cooldown(spell, 30F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
}
