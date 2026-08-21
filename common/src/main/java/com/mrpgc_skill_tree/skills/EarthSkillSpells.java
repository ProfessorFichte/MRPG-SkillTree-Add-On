package com.mrpgc_skill_tree.skills;

import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.client.particle.MoreParticles;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.more_rpg_classes.effect.MRPGCEffects;
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
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import com.mrpgc_skill_tree.effect.MrpgSkillEffects;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static com.mrpgc_skill_tree.MRPGCSkillTreeAddOn.MOD_ID;
import static net.skill_tree_rpgs.skills.SkillsCommon.*;

public class EarthSkillSpells {
    public static final List<MrpgSkillSpells.Entry> all = new ArrayList<>();

    private static MrpgSkillSpells.Entry add(MrpgSkillSpells.Entry entry) {
        all.add(entry);
        return entry;
    }

    public static final Color EARTH_SPELL_COLOR = new Color(255.0F, 165.0F, 0.0F);
    ///EARTH MODIFIERS
    public static final MrpgSkillSpells.Entry earth_tier_2_spell_1_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.EARTH, MrpgSkillSpells.earthWizardSchool,
            "earth_tier_2_spell_1_root", "elemental_wizards_rpg:terra_stone_flesh", "Stone Flesh", 0.15F));
    public static final MrpgSkillSpells.Entry earth_tier_2_spell_1_modifier_1 = add(earth_tier_2_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry earth_tier_2_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "earth_tier_2_spell_1_modifier_1");
        var title = "Obsidian Skin";
        var description = "Stone Flesh grants you Obsidian Skin, protecting you from {effect_amplifier} incoming attack for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.earthWizardSchool;
        var effect = MrpgSkillEffects.OBSIDIAN_SKIN;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:terra_stone_flesh";
        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(),5,1);
        impact.action.apply_to_caster = true;

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.EARTH));
    }
    public static final MrpgSkillSpells.Entry earth_tier_2_spell_1_modifier_2 = add(earth_tier_2_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry earth_tier_2_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "earth_tier_2_spell_1_modifier_2");
        var title = "Earthbender";
        var effect = MrpgSkillEffects.EARTH_BENDER;
        // Single modifier (earth spell power), so the token's blank-attribute fallback is unambiguous.
        var description = "Stone Flesh grants you the Earthbender effect, increasing your earth spell power by "
                + TooltipTokens.effect(effect.id)
                + " for {effect_duration} seconds.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.earthWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:terra_stone_flesh";
        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(),6,0);
        impact.action.apply_to_caster = true;

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.EARTH));
    }
    public static final MrpgSkillSpells.Entry earth_tier_3_spell_1_root = add(MrpgSkillsCommon.radiusRoot(
            MrpgSkillSpells.Category.EARTH, MrpgSkillSpells.earthWizardSchool,
            "earth_tier_3_spell_1_root", "elemental_wizards_rpg:terra_drip_circle", "Terra Circle", 1F));
    public static final MrpgSkillSpells.Entry earth_tier_3_spell_1_modifier_1 = add(earth_tier_3_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry earth_tier_3_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "earth_tier_3_spell_1_modifier_1");
        var title = "Obstacle Dripstones";
        var effect = MrpgSkillEffects.DRIPSTONE_OBSTACLES;
        // Single modifier (movement speed -10%). The value is stored negative and the prose already
        // says "reducing ... by", hence `ABS` - the old mutator passed the raw value and rendered
        // "reducing movement speed by -10%".
        var description = "Terra Circle applies slowness, reducing movement speed by "
                + TooltipTokens.effect(effect.id, 0, null, TooltipTokens.Format.ABS)
                + ", stacking up to {effect_amplifier_cap} times, lasting {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.earthWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:terra_drip_circle";
        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 6, 0, 3);
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.EARTH));
    }
    public static final MrpgSkillSpells.Entry earth_tier_3_spell_1_modifier_2 = add(earth_tier_3_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry earth_tier_3_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "earth_tier_3_spell_1_modifier_2");
        var title = "Sharp Dripstones";
        var description = "Terra Circle has {critical_chance_bonus} increased critical strike chance.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.earthWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:terra_drip_circle";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.critical_chance_bonus = 0.1F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.EARTH));
    }
    public static final MrpgSkillSpells.Entry earth_tier_4_spell_1_root = add(MrpgSkillsCommon.radiusRoot(
            MrpgSkillSpells.Category.EARTH, MrpgSkillSpells.earthWizardSchool,
            "earth_tier_4_spell_1_root", "elemental_wizards_rpg:terra_earthquake", "Earthquake", 1F));
    public static final MrpgSkillSpells.Entry earth_tier_4_spell_1_modifier_1 = add(earth_tier_4_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry earth_tier_4_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "earth_tier_4_spell_1_modifier_1");
        var title = "Earthquake Concussion";
        var effect = MrpgSkillEffects.CONCUSSION;
        // Three modifiers (attack damage, ranged damage, spell power), all -30%. The status effect's
        // modifier map is unordered, so the attribute is named explicitly rather than read by list
        // position (`firstModifier()`). Values are stored negative and the prose says "reduces ...
        // by", hence `ABS` - the old mutator rendered "reduces offensive attributes by -30%".
        var description = "Earthquake reduces offensive attributes by "
                + TooltipTokens.effect(effect.id, 0,
                        Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),
                        TooltipTokens.Format.ABS)
                + " for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.earthWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:terra_earthquake";
        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(), 6, 0);
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.EARTH));
    }
    public static final MrpgSkillSpells.Entry earth_tier_4_spell_1_modifier_2 = add(earth_tier_4_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry earth_tier_4_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "earth_tier_4_spell_1_modifier_2");
        var title = "Magnitude 10";
        var description = "Increases the duration of Earthquake by {spawn_duration_add} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.earthWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:terra_earthquake";
        modifier.spawn_duration_add = 3;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.EARTH));
    }
    ///EARTH PASSIVES
    public static final MrpgSkillSpells.Entry earth_tier_1_passive_1 = add(earth_tier_1_passive_1());
    private static MrpgSkillSpells.Entry earth_tier_1_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "earth_tier_1_passive_1");
        var effect = MrpgSkillEffects.EARTHEN_BLESSING;
        var title = effect.title;
        // Single modifier (armor), so the token's blank-attribute fallback is unambiguous. Amplifier
        // 0 = the per-stack value, matching what the old mutator read straight off the config.
        var description = "Earth spell impacts have {trigger_chance} chance to apply Earthen Blessing effect."
                + " Increasing armor by " + TooltipTokens.effect(effect.id)
                + ", stacking up to {effect_amplifier_cap} times, lasting {effect_duration} sec.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.earthWizardSchool;
        spell.range = 0;
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.activeSpellHit(0.2F, "earth");
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 10, 1, 5);
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(MoreParticles.STONE_PARTICLE)
                        .batch(b -> b.shape(ParticleGroup.Shape.PILLAR)
                                .count(5).speed(0.1F, 0.8F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)
                                .extent(1.0F)));
        impact.sound = new Sound(MrpgSkillSounds.earthen_blessing.id());
        spell.impacts = List.of(impact);
        SpellBuilder.Cost.cooldown(spell, 5F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.EARTH));
    }
    public static final MrpgSkillSpells.Entry earth_tier_1_passive_2 = add(earth_tier_1_passive_2());
    private static MrpgSkillSpells.Entry earth_tier_1_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "earth_tier_1_passive_2");
        var title = "Serrated Stones";
        var description = "Earth spell impacts have {trigger_chance} chance to deal additional {damage} damage, if the target has a bad status effect.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.earthWizardSchool;
        spell.range = 0;
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.activeSpellHit(0.5F, "earth");
        var condition = new Spell.TargetCondition();
        condition.entity_predicate_id = SpellEntityPredicates.HAS_BAD_EFFECT.id().toString();
        trigger.target_conditions = List.of(condition);
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.damage(0.3F,0.0F);
        impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_spell, ParticleGroup.Motion.BURST,
                                EARTH_SPELL_COLOR)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(25).speed(0.2F, 0.25F)));
        spell.impacts = List.of(impact);
        SpellBuilder.Cost.cooldown(spell, 1F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.EARTH));
    }
    public static final MrpgSkillSpells.Entry earth_tier_2_passive_1 = add(earth_tier_2_passive_1());
    private static MrpgSkillSpells.Entry earth_tier_2_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "earth_tier_2_passive_1");
        var effect = MrpgSkillEffects.DIFFICULT_TERRAIN;
        var title = "Difficult Terrain";
        var description = "{trigger_chance} chance upon rolling to leave difficult terrain behind for {cloud_duration} sec, slowing for {effect_duration} sec and dealing {damage} damage to enemies.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.earthWizardSchool;
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
        cloud.client_data.particles = List.of(
                ParticleGroupBuilder.of(MoreParticles.STONE_TRAP)
                        .batch(b -> b.shape(ParticleGroup.Shape.PILLAR)
                                .count(2).speed(0F, 0F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)));
        spell.deliver.clouds = List.of(cloud);
        Spell.Impact debuff = SpellBuilder.Impacts.effectSet(effect.id.toString(),3,0);
        Spell.Impact damage = SpellBuilder.Impacts.damage(0.1F,0.0F);
        debuff.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_medium)
                        .batch(b -> b.shape(ParticleGroup.Shape.CIRCLE)
                                .count(10).speed(0.3F, 0.3F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)));
        spell.impacts = List.of(debuff, damage);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.EARTH));
    }
    public static final MrpgSkillSpells.Entry earth_tier_2_passive_2 = add(earth_tier_2_passive_2());
    private static MrpgSkillSpells.Entry earth_tier_2_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "earth_tier_2_passive_2");
        var title = "Seismic Entry";
        var description = "{trigger_chance_1} chance while rolling to deal {damage} damage and knock up nearby enemies.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.earthWizardSchool;
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
        MrpgSkillSpells.bossImmuneDeny(custom);
        custom.action = new Spell.Impact.Action();
        custom.action.custom = new Spell.Impact.Action.Custom();
        custom.action.type = Spell.Impact.Action.Type.CUSTOM;
        custom.action.custom.intent = SpellTarget.Intent.HARMFUL;
        custom.action.custom.handler = "more_rpg_classes:knock_up_fixed";
        spell.impacts = List.of(impact, custom);
        var areaImpact = new Spell.AreaImpact();
        areaImpact.radius = 2.5F;
        areaImpact.force_indirect = true;
        areaImpact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_medium)
                        .batch(b -> b.shape(ParticleGroup.Shape.CIRCLE)
                                .count(2).speed(0.1F, 0.1F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)));
        areaImpact.sound = new Sound(MrpgSkillSounds.seismic_entry.id());
        spell.area_impact = areaImpact;

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.EARTH));
    }
    public static final MrpgSkillSpells.Entry earth_tier_3_passive_1 = add(earth_tier_3_passive_1());
    private static MrpgSkillSpells.Entry earth_tier_3_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "earth_tier_3_passive_1");
        var title = "Stone Heart";
        var description = "Earth Spells have {trigger_chance} chance to absorb a huge amount of damage.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.earthWizardSchool;
        spell.range = 0;
        var duration = WIZARD_WARD_DURATION;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var spell_trigger = SpellBuilder.Triggers.activeSpellCast(MrpgSkillSpells.earthWizardSchool);
        spell_trigger.chance = WIZARD_WARD_CHANCE;
        spell_trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(spell_trigger);

        var effect = MrpgSkillEffects.STONE_HEART;
        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(),duration,0);
        impact.action.status_effect.amplifier_power_multiplier = 0.5F;
        impact.visuals = Fx.Visuals.of(
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_shield.id(), Color.fromRGBA(EARTH_SPELL_COLOR.toRGBA())));
        impact.sound = Sound.withVolume(Identifier.of("more_rpg_classes:earth_magic_cast1"),0.5F);
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, duration * 2);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.EARTH));
    }
    public static final MrpgSkillSpells.Entry earth_tier_3_passive_2 = add(earth_tier_3_passive_2());
    private static MrpgSkillSpells.Entry earth_tier_3_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "earth_tier_3_passive_2");
        var title = "Aftershock";
        float radius = 5F;
        var description = "Taking damage has {trigger_chance} chance to deal {damage} damage to nearby targets.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.earthWizardSchool;
        spell.range = radius;

        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();

        spell.release.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_spark, ParticleGroup.Motion.ASCEND)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(40).speed(0.6F, 0.8F)),
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_medium)
                        .batch(b -> b.shape(ParticleGroup.Shape.CIRCLE)
                                .count(20).speed(0.4F, 0.4F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)),
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_medium)
                        .batch(b -> b.shape(ParticleGroup.Shape.CIRCLE)
                                .count(20).speed(0.6F, 0.6F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)),
                // `Particles.area` now returns a finished ParticleGroup, so scale/colour move
                // onto its appearance block. Both are fixed sizes here, not range-scaled.
                SpellBuilder.Particles.area(SpellEngineParticles.area_effect_658.id())
                        .appearance(a -> a.scale(radius * 0.8F).color(EARTH_SPELL_COLOR.toRGBA())),
                SpellBuilder.Particles.area(SpellEngineParticles.area_effect_658.id())
                        .appearance(a -> a.scale(radius).color(EARTH_SPELL_COLOR.toRGBA())));
        spell.release.sound = Sound.withVolume(Identifier.of("more_rpg_classes:earth_magic_impact1"),0.7F);

        var trigger = SpellBuilder.Triggers.damageTaken();
        trigger.chance = 0.25F;
        trigger.aoe_source_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        var damage = SpellBuilder.Impacts.damage(0.5F,0.2F);
        damage.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(SpellEngineParticles.smoke_medium)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(20).speed(0.2F, 0.3F)));
        spell.impacts = List.of(damage);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.EARTH));
    }
}
