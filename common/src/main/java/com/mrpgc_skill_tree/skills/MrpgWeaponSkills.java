package com.mrpgc_skill_tree.skills;

import net.minecraft.util.Identifier;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.fx.Fx;
import net.spell_engine.api.spell.fx.ParticleGroup;
import net.spell_engine.api.spell.fx.ParticleGroupBuilder;
import net.spell_engine.api.spell.fx.Sound;
import net.more_rpg_classes.client.particle.MoreParticles;
import net.spell_engine.api.spell.tooltip.TooltipTokens;
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
        var description = "Burst Crack deals {critical_chance_bonus} critical chance bonus.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.forcemasterCasterSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:burstcrack";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.critical_chance_bonus = 0.1F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }
}
