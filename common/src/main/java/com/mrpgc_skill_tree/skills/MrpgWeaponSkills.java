package com.mrpgc_skill_tree.skills;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.fx.Fx;
import net.spell_engine.api.spell.fx.ParticleGroup;
import net.spell_engine.api.spell.fx.ParticleGroupBuilder;
import net.spell_engine.api.spell.fx.Sound;
import net.more_rpg_classes.client.particle.MoreParticles;
import net.spell_engine.api.spell.tooltip.TooltipTokens;
import net.spell_power.api.SpellSchools;
import com.mrpgc_skill_tree.effect.MrpgSkillEffects;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class MrpgWeaponSkills {
    public static final List<MrpgSkillSpells.Entry> all = new ArrayList<>();

    private static MrpgSkillSpells.Entry add(MrpgSkillSpells.Entry entry) {
        all.add(entry);
        return entry;
    }

    public static final MrpgSkillSpells.Entry weapon_aqua_staff_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.WEAPON, MrpgSkillSpells.waterWizardSchool,
            "weapon_aqua_staff_root", "elemental_wizards_rpg:aqua_water_whip", "Water Whip", 0.05F));

    public static final MrpgSkillSpells.Entry weapon_aqua_staff_modifier_1 = add(weapon_aqua_staff_modifier_1());
    private static MrpgSkillSpells.Entry weapon_aqua_staff_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_aqua_staff_modifier_1");
        var title = "Strong Water Whip";
        var description = "Water Whip deals {knockback_multiply_base} more knockback.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.waterWizardSchool;

        var bonus = 1.0F;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:aqua_water_whip";
        modifier.knockback_multiply_base = bonus;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_aqua_staff_modifier_2 = add(weapon_aqua_staff_modifier_2());
    private static MrpgSkillSpells.Entry weapon_aqua_staff_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_aqua_staff_modifier_2");
        var title = "Splish Splash";
        var description = "Water Whip deals {damage} damage around the target.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.waterWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:aqua_water_whip";
        var impact = SpellBuilder.Impacts.damage(0.25F, 0.3F);
        impact.action.allow_on_center_target = false;

        var radius = 2.0F;

        var area_impact = new Spell.AreaImpact();
        area_impact.execute_action_type = Spell.Impact.Action.Type.DAMAGE;
        area_impact.radius = radius;
        area_impact.area = new Spell.Target.Area();
        area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        area_impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(MoreParticles.BIG_SPLASH)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(20F).speed(0.35F, 0.35F)),
                ParticleGroupBuilder.of(MoreParticles.SPLASH)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(20F).speed(0.15F, 0.15F))
        );

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);
        modifier.replacing_area_impact = area_impact;

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_wind_staff_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.WEAPON, MrpgSkillSpells.airWizardSchool,
            "weapon_wind_staff_root", "elemental_wizards_rpg:wind_air_cutter", "Air Cutter", 0.05F));

    public static final MrpgSkillSpells.Entry weapon_wind_staff_modifier_1 = add(weapon_wind_staff_modifier_1());
    private static MrpgSkillSpells.Entry weapon_wind_staff_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_wind_staff_modifier_1");
        var title = "Fast Winds";
        var description = "Increases the knockback of Air Cutter by {knockback_multiply_base}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.airWizardSchool;

        var bonus = 0.5F;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_air_cutter";
        modifier.knockback_multiply_base = bonus;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_wind_staff_modifier_2 = add(weapon_wind_staff_modifier_2());
    private static MrpgSkillSpells.Entry weapon_wind_staff_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_wind_staff_modifier_2");
        var title = "Air Cutting Pressure";
        var description = "Air Cutter deals {power_multiplier} more damage.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.airWizardSchool;

        var bonus = 0.2F;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:wind_air_cutter";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.power_multiplier = bonus;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_terra_staff_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.WEAPON, MrpgSkillSpells.earthWizardSchool,
            "weapon_terra_staff_root", "elemental_wizards_rpg:terra_stone_spear", "Stone Spear", 0.05F));

    public static final MrpgSkillSpells.Entry weapon_terra_staff_modifier_1 = add(weapon_terra_staff_modifier_1());
    private static MrpgSkillSpells.Entry weapon_terra_staff_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_terra_staff_modifier_1");
        var title = "Sharpened Stones";
        var description = "Increases the duration of Bleeding by {effect_duration_add} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.earthWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:terra_stone_spear";
        modifier.effect_duration_add = 2;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_terra_staff_modifier_2 = add(weapon_terra_staff_modifier_2());
    private static MrpgSkillSpells.Entry weapon_terra_staff_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_terra_staff_modifier_2");
        var title = "Earthen Blast";
        var bonus = 0.5F;
        // A compile-time constant of this mod, not anything the spell data carries, so it is baked
        // into the description (`bakedPercent` doubles the `%`: the lang value goes through
        // `I18n.translate` -> `String.format`).
        var description = "Increases the area of effect of Stone Spear by "
                + TooltipTokens.bakedPercent(bonus) + ".";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.earthWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:terra_stone_spear";
        var extendedRadius = 2.5F * (1F + bonus);
        Spell.AreaImpact area_impact = new Spell.AreaImpact();
        area_impact.radius = extendedRadius;
        area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        // NOTE: in V1 this `.scale(...)` was dead. `more_rpg_classes:stone_explosion` was drawn by
        // a hand-written `CustomSpellExplosionParticle` that hard-set `scale = 0.8F` and never read
        // the batch appearance, so the explosion rendered at a fixed size whatever the radius. In
        // 1.10 that id is a generic `SpellParticle` entry, so the authored value now applies (and
        // multiplies the entry's own 0.8). Kept as authored — the intent is clearly "grow with the
        // enlarged radius" — but it is a visible size increase over what shipped.
        area_impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of(MoreParticles.STONE_EXPLOSION)
                        .scale(extendedRadius / 2)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(1.0F).speed(0.0F, 0.0F)));
        area_impact.sound = Sound.withVolume(Identifier.of("block.pointed_dripstone.break"),1.5F);
        modifier.replacing_area_impact = area_impact;

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_knuckle_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.WEAPON, MrpgSkillSpells.forcemasterFighterSchool,
            "weapon_knuckle_root", "forcemaster_rpg:burstcrack", "Burstcrack", 0.05F));

    public static final MrpgSkillSpells.Entry weapon_knuckle_modifier_1 = add(weapon_knuckle_modifier_1());
    private static MrpgSkillSpells.Entry weapon_knuckle_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_knuckle_modifier_1");
        var title = "Pumped Up";
        var effect = MrpgSkillEffects.PUMPED_UP;
        // Single modifier (attack damage), so the token's blank-attribute fallback is unambiguous.
        var description = "Burstcrack increases your attack damage by "
                + TooltipTokens.effect(effect.id)
                + " for {effect_duration} seconds.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.forcemasterFighterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:burstcrack";
        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(),8,0);
        impact.action.apply_to_caster = true;

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_knuckle_modifier_2 = add(weapon_knuckle_modifier_2());
    private static MrpgSkillSpells.Entry weapon_knuckle_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_knuckle_modifier_2");
        var title = "Powerful Burst";
        var description = "Burstcrack releases a second burst shortly after, dealing {damage} damage around the caster.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.forcemasterFighterSchool;
        spell.range = 5.5F;

        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.angle_degrees = 360F;

        var trigger = SpellBuilder.Triggers.specificSpellCast("forcemaster_rpg:burstcrack");
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        spell.deliver.delay = 10;

        var damage = SpellBuilder.Impacts.damage(0.35F, 0F);
        damage.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of("spell_engine:smoke_medium")
                        .batch(b -> b.shape(ParticleGroup.Shape.PIPE)
                                .verticalOrigin(ParticleGroupBuilder.Batches.CENTER)
                                .count(20).speed(0.1F, 3.0F)));
        damage.sound = new Sound("minecraft:entity.generic.explode");
        spell.impacts = List.of(damage);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_rapier_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.WEAPON, MrpgSkillSpells.forcemasterFighterSchool,
            "weapon_rapier_root", "more_rpg_classes:puncture", "Puncture", 0.05F));

    public static final MrpgSkillSpells.Entry weapon_rapier_modifier_1 = add(weapon_rapier_modifier_1());
    private static MrpgSkillSpells.Entry weapon_rapier_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_rapier_modifier_1");
        var title = "Phantom Execution";
        var description = "Killing a target with Puncture resets its cooldown.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.forcemasterFighterSchool;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = SpellBuilder.Triggers.specificSpellHit("more_rpg_classes:puncture");
        trigger.target_conditions = List.of(SpellBuilder.TargetConditions.dead());
        spell.passive.triggers = List.of(trigger);

        var reset = SpellBuilder.Impacts.resetCooldownActive("more_rpg_classes:puncture");
        reset.action.apply_to_caster = true;
        spell.impacts = List.of(reset);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_rapier_modifier_2 = add(weapon_rapier_modifier_2());
    private static MrpgSkillSpells.Entry weapon_rapier_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_rapier_modifier_2");
        var title = "Sword Mark";
        var effect = MrpgSkillEffects.PUMPED_UP;
        var description = "Damaging a target with Puncture increases your attack damage by "
                + TooltipTokens.effect(effect.id)
                + " for {effect_duration} seconds.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.forcemasterFighterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "more_rpg_classes:puncture";
        var impact = SpellBuilder.Impacts.effectSet(effect.id.toString(), 8, 0);
        impact.action.apply_to_caster = true;

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_lute_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.WEAPON, MrpgSkillSpells.bardSchool,
            "weapon_lute_root", "#bards_rpg:weapon/all_lute_songs", "Lute Songs", 0.05F));

    public static final MrpgSkillSpells.Entry weapon_lute_modifier_1 = add(weapon_lute_modifier_1());
    private static MrpgSkillSpells.Entry weapon_lute_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_lute_modifier_1");
        var title = "Raging Performer";
        var description = "Melee hits with a Lute have a {trigger_chance} chance to briefly stun the target.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.bardSchool;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = SpellBuilder.Triggers.meleeAttackImpact();
        trigger.chance = 0.1F;
        trigger.weapon_condition = "#bards_rpg:lutes";
        spell.passive.triggers = List.of(trigger);

        spell.impacts = List.of(SpellBuilder.Impacts.stun(1.5F));

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_lute_modifier_2 = add(weapon_lute_modifier_2());
    private static MrpgSkillSpells.Entry weapon_lute_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_lute_modifier_2");
        var title = "Outstanding Performance";
        var description = "Increases the max stacks of Lute Song effects by {effect_amplifier_cap_add}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.bardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "#bards_rpg:weapon/all_lute_songs";
        modifier.effect_amplifier_cap_add = 1;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_lyre_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.WEAPON, MrpgSkillSpells.bardSchool,
            "weapon_lyre_root", "#bards_rpg:weapon/all_lyre_songs", "Lyre Songs", 0.05F));

    public static final MrpgSkillSpells.Entry weapon_lyre_modifier_1 = add(weapon_lyre_modifier_1());
    private static MrpgSkillSpells.Entry weapon_lyre_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_lyre_modifier_1");
        var title = "Soothing Tunes";
        var description = "Increases the healing of Lyre Songs by {power_multiplier}.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.bardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "#bards_rpg:weapon/all_lyre_songs";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.power_multiplier = 0.2F;
        var filter = new Spell.Modifier.ImpactFilter();
        filter.school = SpellSchools.HEALING;
        filter.type = Spell.Impact.Action.Type.HEAL;
        modifier.impact_filters = List.of(filter);
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_lyre_modifier_2 = add(weapon_lyre_modifier_2());
    private static MrpgSkillSpells.Entry weapon_lyre_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_lyre_modifier_2");
        var title = "Angelic Strings";
        var description = "Increases the status effect duration of Lyre Songs by {effect_duration_add} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.bardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "#bards_rpg:weapon/all_lyre_songs";
        modifier.effect_duration_add = 2F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_berserker_axe_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.WEAPON, MrpgSkillSpells.berserkerSchool,
            "weapon_berserker_axe_root", "more_rpg_classes:decapitate", "Decapitate", 0.05F));

    public static final MrpgSkillSpells.Entry weapon_berserker_axe_modifier_1 = add(weapon_berserker_axe_modifier_1());
    private static MrpgSkillSpells.Entry weapon_berserker_axe_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_berserker_axe_modifier_1");
        var title = "Glorious Victor";
        var description = "Killing a target with Decapitate heals you for {power_multiplier} of your max health.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.berserkerSchool;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = SpellBuilder.Triggers.specificSpellHit("more_rpg_classes:decapitate");
        trigger.target_conditions = List.of(SpellBuilder.TargetConditions.dead());
        spell.passive.triggers = List.of(trigger);

        var heal = SpellBuilder.Impacts.heal(0.2F);
        heal.attribute = EntityAttributes.GENERIC_MAX_HEALTH.getIdAsString();
        heal.attribute_from_target = false;
        heal.action.apply_to_caster = true;

        spell.impacts = List.of(heal);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_berserker_axe_modifier_2 = add(weapon_berserker_axe_modifier_2());
    private static MrpgSkillSpells.Entry weapon_berserker_axe_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_berserker_axe_modifier_2");
        var title = "Executioner's Joy";
        var description = "Decapitate has a small chance to instantly kill targets under a certain health threshold. Does not affect bosses.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.berserkerSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "more_rpg_classes:decapitate";

        var execute = new Spell.Impact();
        execute.chance = 0.15F;
        execute.action = new Spell.Impact.Action();
        execute.action.type = Spell.Impact.Action.Type.DAMAGE;
        execute.action.damage = new Spell.Impact.Action.Damage();
        execute.action.damage.spell_power_coefficient = 5.0F;
        execute.action.damage.bypass_iframes = true;

        var healthCondition = new Spell.TargetCondition();
        healthCondition.health_percent_above = 0.15F;
        var healthModifier = new Spell.Impact.TargetModifier();
        healthModifier.conditions = List.of(healthCondition);
        healthModifier.execute = net.spell_engine.api.util.TriState.DENY;

        var bossCondition = new Spell.TargetCondition();
        bossCondition.entity_type = "#c:bosses";
        var bossModifier = new Spell.Impact.TargetModifier();
        bossModifier.conditions = List.of(bossCondition);
        bossModifier.execute = net.spell_engine.api.util.TriState.DENY;

        execute.target_modifiers = List.of(healthModifier, bossModifier);
        execute.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of("more_rpg_classes:stone_explosion")
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .verticalOrigin(ParticleGroupBuilder.Batches.CENTER)
                                .count(1.0F).speed(0.5F, 0.5F)));
        execute.sound = new Sound("entity.wither.break_block");

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(execute);
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_harp_crossbow_root = add(weapon_harp_crossbow_root());
    private static MrpgSkillSpells.Entry weapon_harp_crossbow_root() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_harp_crossbow_root");
        var title = "Harp Crossbow Specialisation";
        var description = "";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.bardSchool;

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_harp_crossbow_modifier_1 = add(weapon_harp_crossbow_modifier_1());
    private static MrpgSkillSpells.Entry weapon_harp_crossbow_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_harp_crossbow_modifier_1");
        var title = "Melodic Harp";
        var description = "After shooting with a Harp Crossbow, {trigger_chance} chance to heal allies around you, including yourself.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.bardSchool;
        spell.range = 6F;

        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.include_caster = true;

        var trigger = SpellBuilder.Triggers.arrowShot();
        trigger.chance = 0.2F;
        trigger.weapon_condition = "#bards_rpg:harp_crossbows";
        spell.passive.triggers = List.of(trigger);

        var heal = SpellBuilder.Impacts.heal(0.15F);
        heal.school = SpellSchools.HEALING;

        spell.impacts = List.of(heal);

        SpellBuilder.Cost.cooldown(spell, 4F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_harp_crossbow_modifier_2 = add(weapon_harp_crossbow_modifier_2());
    private static MrpgSkillSpells.Entry weapon_harp_crossbow_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_harp_crossbow_modifier_2");
        var title = "Tuneful Bolts";
        var description = "Harp Crossbow shots have {trigger_chance} chance to deal extra magic damage, based on your Arcane Spell Power.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.bardSchool;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.arrowHit();
        trigger.chance = 0.25F;
        trigger.weapon_condition = "#bards_rpg:harp_crossbows";
        spell.passive.triggers = List.of(trigger);

        var damage = SpellBuilder.Impacts.damage(0.3F);
        damage.school = SpellSchools.ARCANE;
        damage.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.of("more_rpg_classes:music_note")
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .verticalOrigin(ParticleGroupBuilder.Batches.CENTER)
                                .count(6).speed(0.1F, 0.2F)));

        spell.impacts = List.of(damage);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_witcher_swords_root = add(weapon_witcher_swords_root());
    private static MrpgSkillSpells.Entry weapon_witcher_swords_root() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_witcher_swords_root");
        var title = "Witcher Sword Specialisation";
        var description = "";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.witcherFencingSchool;

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }
}
