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

public class WarArcherSkillSpells {
    public static final List<MrpgSkillSpells.Entry> all = new ArrayList<>();

    private static MrpgSkillSpells.Entry add(MrpgSkillSpells.Entry entry) {
        all.add(entry);
        return entry;
    }

    ///WAR ARCHER MODIFIERS
    public static final MrpgSkillSpells.Entry war_archer_tier_1_spell_1_modifier_1 = add(war_archer_tier_1_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry war_archer_tier_1_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_1_spell_1_modifier_1");
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
        spell.school = MrpgSkillSpells.warArcherSchool;

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

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_1_spell_1_modifier_2 = add(war_archer_tier_1_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry war_archer_tier_1_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_1_spell_1_modifier_2");
        var title = "Explosive Push";
        var description = "Increases the knockback of Smoldering Arrow by {knockback_multiply_base}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.warArcherSchool;

        var bonus = 0.5F;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:smoldering_arrow";
        modifier.knockback_multiply_base = bonus;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_2_spell_1_modifier_1 = add(war_archer_tier_2_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry war_archer_tier_2_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_2_spell_1_modifier_1");
        var title = "Flaming Double Shot";
        var description = "Double Shot deals {power_multiplier} more damage and lights enemies on fire.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.warArcherSchool;

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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_2_spell_1_modifier_2 = add(war_archer_tier_2_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry war_archer_tier_2_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_2_spell_1_modifier_2");
        var title = "Heavy Arrow Tips";
        var description = "Increases the knockback of Double Shot by {knockback_multiply_base}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.warArcherSchool;

        var bonus = 1.0F;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:dual_shot";
        modifier.knockback_multiply_base = bonus;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_3_spell_1_modifier_1 = add(war_archer_tier_3_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry war_archer_tier_3_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_3_spell_1_modifier_1");
        var title = "Explosive Point Blank Shot";
        var description = "Damaging with Point Blank Shot causes small explosion, hitting enemies within {impact_range} blocks radius, dealing extra {damage} damage.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.warArcherSchool;

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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_3_spell_1_modifier_2 = add(war_archer_tier_3_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry war_archer_tier_3_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_3_spell_1_modifier_2");
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_4_spell_1_modifier_1 = add(war_archer_tier_4_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry war_archer_tier_4_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_4_spell_1_modifier_1");
        var title = "Combustive Shot";
        var description = "Pin Down leaves a burning area behind, dealing {damage} damage to enemies, for {cloud_duration} sec.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.warArcherSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.specificSpellCast("archers_expansion:pin_down");
        spell.passive.triggers = List.of(trigger);

        SpellBuilder.Complex.flameCloud(spell, 3.5F, 0.25F, 3, null);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_4_spell_1_modifier_2 = add(war_archer_tier_4_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry war_archer_tier_4_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_4_spell_1_modifier_2");
        var title = "Increased Pin Down";
        var description = "Increases the effect duration of Pin Down by {effect_duration_add}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.warArcherSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:pin_down";
        modifier.effect_duration_add = 3;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    ///WAR ARCHER PASSIVES
    public static final MrpgSkillSpells.Entry war_archer_tier_1_passive_1 = add(war_archer_tier_1_passive_1());
    private static MrpgSkillSpells.Entry war_archer_tier_1_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_1_passive_1");
        var title = "Bombardment";
        var description = "If the target is on fire, create a explosion dealing {damage} damage.";

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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_1_passive_2 = add(war_archer_tier_1_passive_2());
    private static MrpgSkillSpells.Entry war_archer_tier_1_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_1_passive_2");
        var title = "Tower's Watch";
        var description = "Your arrow hits have {trigger_chance} to increase your armor and knockback resistance for {effect_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.warArcherSchool;
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
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
        impact.particles = new ParticleBatch[]{
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_arrow.id(), Color.RED),
        };
        impact.sound = new Sound(SpellEngineSounds.GENERIC_FIRE_IGNITE.id());
        spell.impacts = List.of(impact);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, MrpgSkillSpells.Category.WAR_ARCHER);
    }
    public static final MrpgSkillSpells.Entry war_archer_tier_3_passive_2 = add(war_archer_tier_3_passive_2());
    private static MrpgSkillSpells.Entry war_archer_tier_3_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "war_archer_tier_3_passive_2");
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
        spell.school = MrpgSkillSpells.warArcherSchool;
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.WAR_ARCHER));
    }
}
