package com.mrpgc_skill_tree.skills;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
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
import net.spell_engine.api.spell.tooltip.TooltipTokens;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;
import net.spell_engine.fx.SpellEngineSounds;
import net.spell_engine.internals.target.SpellTarget;
import com.mrpgc_skill_tree.effect.MrpgSkillEffects;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class DeadeyeSkillSpells {
    public static final List<MrpgSkillSpells.Entry> all = new ArrayList<>();

    private static MrpgSkillSpells.Entry add(MrpgSkillSpells.Entry entry) {
        all.add(entry);
        return entry;
    }

    public static final MrpgSkillSpells.Entry deadeye_tier_2_spell_1_root = add(MrpgSkillsCommon.critRoot(
            MrpgSkillSpells.Category.DEADEYE, MrpgSkillSpells.deadeyeSchool,
            "deadeye_tier_2_spell_1_root", "archers_expansion:bouncing_arrow", "Bouncing Arrow", 0.05F));
    public static final MrpgSkillSpells.Entry deadeye_tier_2_spell_1_modifier_1 = add(deadeye_tier_2_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry deadeye_tier_2_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_2_spell_1_modifier_1");
        var title = "Barbed Bouncing Arrows";
        var description = "Bouncing Arrow's bleeding effect lasts {effect_duration_add} sec longer.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.deadeyeSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:bouncing_arrow";
        modifier.effect_duration_add = 4;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_2_spell_1_modifier_2 = add(deadeye_tier_2_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry deadeye_tier_2_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_2_spell_1_modifier_2");
        var title = "Bouncing Bouncing Arrows";
        var description = "Bouncing Arrow now ricochets {ricochet} more times.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.deadeyeSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:bouncing_arrow";
        modifier.projectile_perks = Spell.ProjectileData.Perks.EMPTY();
        modifier.projectile_perks.ricochet = 3;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_2_spell_2_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.DEADEYE, MrpgSkillSpells.deadeyeSchool,
            "deadeye_tier_2_spell_2_root", "archers_expansion:fast_shot", "Fast Shot", 0.1F));
    public static final MrpgSkillSpells.Entry deadeye_tier_2_spell_2_modifier_1 = add(deadeye_tier_2_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry deadeye_tier_2_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_2_spell_2_modifier_1");
        var title = "Swift Shots";
        var effect = MrpgSkillEffects.HUNTING_FEVER;
        // Two modifiers (movement speed, ranged haste), both +20%. The status effect's modifier map
        // is unordered, so the attribute is named explicitly rather than read by list position.
        var description = "Fast Shot additionally increases movement speed by "
                + TooltipTokens.effect(effect.id, 0,
                        Identifier.of(EntityAttributes.GENERIC_MOVEMENT_SPEED.getIdAsString()))
                + " for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.deadeyeSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:fast_shot";
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;

        var buff = SpellBuilder.Impacts.effectSet(effect.id.toString(), 4, 0);
        buff.action.apply_to_caster = true;
        modifier.impacts = List.of(buff);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_2_spell_2_modifier_2 = add(deadeye_tier_2_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry deadeye_tier_2_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_2_spell_2_modifier_2");
        var title = "Fast Hands";
        var description = "Fast Shot applies {effect_amplifier_cap_add} additional Fast Shot stack.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.deadeyeSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:fast_shot";
        modifier.effect_amplifier_cap_add = 1;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_3_spell_1_root = add(MrpgSkillsCommon.radiusRoot(
            MrpgSkillSpells.Category.DEADEYE, MrpgSkillSpells.deadeyeSchool,
            "deadeye_tier_3_spell_1_root", "archers_expansion:venom_cask", "Venom Cask", 1F));
    public static final MrpgSkillSpells.Entry deadeye_tier_3_spell_1_modifier_1 = add(deadeye_tier_3_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry deadeye_tier_3_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_3_spell_1_modifier_1");
        var title = "Persistent Venom";
        var description = "Increases the effect amplifier cap of Venom Cask's poison by {effect_amplifier_cap_add}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.deadeyeSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:venom_cask";
        modifier.effect_amplifier_cap_add = 1;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_3_spell_1_modifier_2 = add(deadeye_tier_3_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry deadeye_tier_3_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_3_spell_1_modifier_2");
        var title = "Sticky Toxic Slime";
        var bonus = 0.3F;
        // A compile-time constant of this mod (it is the slow impact's `amplifier_power_multiplier`),
        // not anything a declarative token can name, so it is baked into the description.
        // `bakedPercent` doubles the `%`: the lang value goes through `I18n.translate` -> `String.format`.
        var description = "Venom Cask additionally slows targets by " + TooltipTokens.bakedPercent(bonus)
                + " for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.deadeyeSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:venom_cask";

        var slow = SpellBuilder.Impacts.effectSet(StatusEffects.SLOWNESS.getIdAsString(), 4, 0);
        slow.action.status_effect.amplifier_power_multiplier = bonus;
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(slow);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_3_spell_2_root = add(MrpgSkillsCommon.lingerRoot(
            MrpgSkillSpells.Category.DEADEYE, MrpgSkillSpells.deadeyeSchool,
            "deadeye_tier_3_spell_2_root", "archers_expansion:disabling_shot", "Disabling Shot", 1F));
    public static final MrpgSkillSpells.Entry deadeye_tier_3_spell_2_modifier_1 = add(deadeye_tier_3_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry deadeye_tier_3_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_3_spell_2_modifier_1");
        var title = "Wounding Shot";
        var description = "Disabling Shot has {impact_chance} chance to stun the target.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.deadeyeSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:disabling_shot";

        var stun = SpellBuilder.Impacts.stun(2F);
        stun.chance = 0.35F;
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(stun);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_3_spell_2_modifier_2 = add(deadeye_tier_3_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry deadeye_tier_3_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_3_spell_2_modifier_2");
        var title = "Leaping Swiftness";
        var effect = MrpgSkillEffects.LEAPING_SWIFTNESS;
        // Single modifier (movement speed), so the token's blank-attribute fallback is unambiguous.
        var description = "Disabling Shot increases movement speed by "
                + TooltipTokens.effect(effect.id)
                + " for {effect_duration} secs.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.deadeyeSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = SpellBuilder.Triggers.specificSpellHit("archers_expansion:disabling_shot");
        var condition = new Spell.TargetCondition();
        condition.entity_predicate_id = SpellEntityPredicates.HAS_BAD_EFFECT.id().toString();
        trigger.target_conditions = List.of(condition);
        spell.passive.triggers = List.of(trigger);

        spell.release.visuals = Fx.Visuals.of(
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_speed.id(), Color.WHITE));
        spell.release.sound = new Sound(SpellEngineSounds.SPEED_BOOST.id());

        var buff = SpellBuilder.Impacts.effectSet(effect.id.toString(), 5, 0);
        buff.action.status_effect.refresh_duration = true;
        buff.action.apply_to_caster = true;
        spell.impacts = List.of(buff);

        SpellBuilder.Cost.cooldown(spell, 0.5F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_4_spell_1_root = add(MrpgSkillsCommon.radiusRoot(
            MrpgSkillSpells.Category.DEADEYE, MrpgSkillSpells.deadeyeSchool,
            "deadeye_tier_4_spell_1_root", "archers_expansion:choking_gas", "Choking Gas", 1F));
    public static final MrpgSkillSpells.Entry deadeye_tier_4_spell_1_modifier_1 = add(deadeye_tier_4_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry deadeye_tier_4_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_4_spell_1_modifier_1");
        var title = "Persistent Gas Cloud";
        var description = "Choking Gas leaves a gas cloud behind, poisoning and dealing {damage} damage to enemies for {cloud_duration} sec.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.deadeyeSchool;
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
        // Two identically shaped smoke columns, differing only in tint (RGBA literals kept verbatim)
        cloud.client_data.particles = List.of(
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_large)
                        .color(2583652010L)
                        .batch(b -> b.shape(ParticleGroup.Shape.PILLAR)
                                .count(1.0F).speed(0.01F, 0.02F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)),
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_large)
                        .color(870134766L)
                        .batch(b -> b.shape(ParticleGroup.Shape.PILLAR)
                                .count(1.0F).speed(0.01F, 0.02F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)));
        spell.deliver.clouds = List.of(cloud);
        Spell.Impact debuff = SpellBuilder.Impacts.effectSet("archers_expansion:choking_gas", 3, 1);
        debuff.action.status_effect.amplifier_power_multiplier = 0.3F;
        debuff.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_large)
                        .color(Color.POISON_MID.toRGBA())
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(1.5F).speed(0.01F, 0.02F)),
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_skull, ParticleGroup.Motion.DECELERATE,
                                Color.POISON_MID)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(3.0F).speed(0.1F, 0.2F)));
        MrpgSkillSpells.poisonDeny(debuff);
        var impact = SpellBuilder.Impacts.damage(0.1F, 0);
        spell.impacts = List.of(debuff,impact);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_4_spell_1_modifier_2 = add(deadeye_tier_4_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry deadeye_tier_4_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_4_spell_1_modifier_2");
        var title = "Bouncing Gas Arrow";
        var description = "The Choking Gas Arrow now ricochets {ricochet} times.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.deadeyeSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:choking_gas";
        modifier.projectile_perks = Spell.ProjectileData.Perks.EMPTY();
        modifier.projectile_perks.ricochet_range = 10;
        modifier.projectile_perks.ricochet = 3;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_4_spell_2_root = add(MrpgSkillsCommon.companionRoot(
            MrpgSkillSpells.Category.DEADEYE, MrpgSkillSpells.deadeyeSchool,
            "deadeye_tier_4_spell_2_root", "archers_expansion:alter_ego", "Alter Ego", 5));
    public static final MrpgSkillSpells.Entry deadeye_tier_4_spell_2_modifier_1 = add(deadeye_tier_4_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry deadeye_tier_4_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_4_spell_2_modifier_1");
        var title = "Trickful Deceivers";
        var description = "Alter Ego spawns {summon_spawn_count_add} additional decoy copies.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.deadeyeSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:alter_ego";
        modifier.summon_spawn_count_add = 2;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_4_spell_2_modifier_2 = add(deadeye_tier_4_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry deadeye_tier_4_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_4_spell_2_modifier_2");
        var title = "Shocking Revelation";
        var description = "Casting Alter Ego stuns nearby enemies for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.deadeyeSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:alter_ego";

        var radius = 4F;
        var stun = SpellBuilder.Impacts.stun(2F);
        var area_impact = new Spell.AreaImpact();
        area_impact.execute_action_type = Spell.Impact.Action.Type.STATUS_EFFECT;
        area_impact.radius = radius;
        area_impact.area = new Spell.Target.Area();
        area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        area_impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_large)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(20).speed(0.2F, 0.4F)));

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(stun);
        modifier.replacing_area_impact = area_impact;

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final Color SHADOW_COLOR = Color.from(0x00B0B0);
    ///DEADEYE PASSIVES
    public static final MrpgSkillSpells.Entry deadeye_tier_1_passive_1 = add(deadeye_tier_1_passive_1());
    private static MrpgSkillSpells.Entry deadeye_tier_1_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_1_passive_1");
        var title = "Barbed Arrows";
        var description = "Arrows have {trigger_chance} chance to stack bleeding to the target for {effect_duration} sec.";
        var effect = SpellEngineEffects.BLEED;

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.deadeyeSchool;
        spell.range = 0;
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.arrowHit();
        trigger.chance = 0.4F;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 7F, 0,3);
        impact.action.status_effect.refresh_duration = true;
        MrpgSkillSpells.bleedingDeny(impact);
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.dripping_blood)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(10).speed(0.2F, 0.8F)));
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 2F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_1_passive_2 = add(deadeye_tier_1_passive_2());
    private static MrpgSkillSpells.Entry deadeye_tier_1_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_1_passive_2");
        var title = "Withdraw";
        var description = "Arrows have {trigger_chance} chance to cure a negative condition and heal for {heal} hearts.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.deadeyeSchool;
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
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_heal, ParticleGroup.Motion.BURST, SHADOW_COLOR)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(25).speed(0.25F, 0.6F)));
        impact.sound = new Sound(SpellEngineSounds.GENERIC_HEALING_IMPACT_3.id());
        spell.impacts = List.of(impact,cleanse);

        SpellBuilder.Cost.cooldown(spell, 15F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_2_passive_1 = add(deadeye_tier_2_passive_1());
    private static MrpgSkillSpells.Entry deadeye_tier_2_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_2_passive_1");
        var title = "Poison Bomb";
        var description = "{trigger_chance} chance upon rolling to leave behind Choking Gas for {cloud_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.deadeyeSchool;
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
        // Two identical smoke columns in V1 — kept as a pair, which is what doubles the density
        cloud.client_data.particles = List.of(
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_large)
                        .color(Color.POISON_DARK)
                        .batch(b -> b.shape(ParticleGroup.Shape.PILLAR)
                                .count(1.0F).speed(0.01F, 0.02F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)),
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_large)
                        .color(Color.POISON_DARK)
                        .batch(b -> b.shape(ParticleGroup.Shape.PILLAR)
                                .count(1.0F).speed(0.01F, 0.02F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)));
        spell.deliver.clouds = List.of(cloud);
        Spell.Impact debuff = SpellBuilder.Impacts.effectSet("archers_expansion:choking_gas", 1, 1);
        debuff.action.status_effect.amplifier_power_multiplier = 0.3F;
        debuff.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_large)
                        .color(Color.POISON_MID.toRGBA())
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(1.5F).speed(0.01F, 0.02F)),
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_skull, ParticleGroup.Motion.DECELERATE,
                                Color.POISON_MID)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(3.0F).speed(0.1F, 0.2F)));
        MrpgSkillSpells.poisonDeny(debuff);
        spell.impacts = List.of(debuff);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final Color SMOKE_BOMB_COLOR = Color.from(0x302c2c);
    public static final MrpgSkillSpells.Entry deadeye_tier_2_passive_2 = add(deadeye_tier_2_passive_2());
    private static MrpgSkillSpells.Entry deadeye_tier_2_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_2_passive_2");
        var title = "Smoke Bomb";
        var description = "{trigger_chance} chance upon rolling to leave behind a Smoke Bomb for {cloud_duration} sec. Blinding enemies and increasing Evasion for allies.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.deadeyeSchool;
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
        // Two identical smoke columns in V1 — kept as a pair, which is what doubles the density
        cloud.client_data.particles = List.of(
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_large)
                        .color(SMOKE_BOMB_COLOR)
                        .batch(b -> b.shape(ParticleGroup.Shape.PILLAR)
                                .count(1.0F).speed(0.01F, 0.02F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)),
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_large)
                        .color(SMOKE_BOMB_COLOR)
                        .batch(b -> b.shape(ParticleGroup.Shape.PILLAR)
                                .count(1.0F).speed(0.01F, 0.02F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)));
        spell.deliver.clouds = List.of(cloud);
        Spell.Impact debuff = SpellBuilder.Impacts.effectSet(MrpgSkillEffects.SMOKE_BOMB.id.toString(), 1, 0);
        debuff.action.status_effect.refresh_duration = true;
        Spell.Impact debuff2 = SpellBuilder.Impacts.effectSet("blindness", 1, 0);
        debuff2.action.status_effect.refresh_duration = true;
        Spell.Impact buff = SpellBuilder.Impacts.effectSet(MrpgSkillEffects.CAMOUFLAGED.id.toString(), 1, 0);
        buff.action.status_effect.refresh_duration = true;
        buff.action.apply_to_caster = true;
        buff.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_large)
                        .color(SMOKE_BOMB_COLOR)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(5.0F).speed(0.001F, 0.001F)),
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_medium)
                        .color(SMOKE_BOMB_COLOR)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(5.0F).speed(0.001F, 0.001F)));

        spell.impacts = List.of(debuff, debuff2, buff);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_3_passive_1 = add(deadeye_tier_3_passive_1());
    private static MrpgSkillSpells.Entry deadeye_tier_3_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_3_passive_1");
        var title = "Heartseeker";
        var description = "Arrows have {trigger_chance} chance to deal more damage the less health the target has.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.deadeyeSchool;
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
        custom.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.dripping_blood)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(35).speed(0.4F, 1.0F)),
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_medium)
                        .color(Color.RED.toRGBA())
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(10).speed(0.2F, 0.5F)));

        spell.impacts = List.of(custom);

        SpellBuilder.Cost.cooldown(spell, 20F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_3_passive_2 = add(deadeye_tier_3_passive_2());
    private static MrpgSkillSpells.Entry deadeye_tier_3_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_3_passive_2");
        var title = "Shadow Refuge";
        final var healthThreshold = 0.35F;
        // The threshold is a compile-time constant of this mod, so it is baked into the description
        // (`bakedPercent` doubles the `%`: `I18n.translate` feeds the lang value to `String.format`).
        var description = "Upon taking damage below " + TooltipTokens.bakedPercent(healthThreshold)
                + " health you create an area that heals you for {heal} hearts and gives you invisibility for {effect_duration} secs.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.deadeyeSchool;
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
        cloud.client_data.interval_particles = List.of(
                ParticleGroupBuilder.of(areaParticle)
                        .scale(4)
                        .color(SHADOW_COLOR.alpha(0.75F).toRGBA())
                        // SPHERE at zero speed = a single motionless particle on the ground,
                        // exactly as in V1; the entry itself already faces GROUND
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(1).speed(0.0F, 0.0F)
                                .anchor(ParticleGroup.Anchor.GROUND)));
        spell.deliver.clouds = List.of(cloud);

        var heal = SpellBuilder.Impacts.heal(0.05F);
        heal.attribute = EntityAttributes.GENERIC_MAX_HEALTH.getIdAsString();
        heal.attribute_from_target = true;
        heal.action.apply_to_caster = true;
        heal.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_heal, ParticleGroup.Motion.BURST, SHADOW_COLOR)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(10).speed(0.25F, 0.4F)));
        var buff = SpellBuilder.Impacts.effectSet(MrpgSkillEffects.SHADOWS_REFUGE.id.toString(), 2, 0);
        buff.sound = new Sound(MrpgSkillSounds.shadow_refuge_release.id());

        spell.impacts = List.of(heal,buff);

        SpellBuilder.Cost.cooldown(spell, 40F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
}
