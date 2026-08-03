package com.mrpgc_skill_tree.skills;

import net.minecraft.util.Identifier;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.api.spell.fx.Sound;
import net.spell_engine.client.gui.SpellTooltip;
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
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
        area_impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        "more_rpg_classes:big_splash",
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        20, 0.35F, 0.35F
                ),
                new ParticleBatch(
                        "more_rpg_classes:splash",
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        20, 0.15F, 0.15F
                )
        };

        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);
        modifier.replacing_area_impact = area_impact;

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_terra_staff_modifier_2 = add(weapon_terra_staff_modifier_2());
    private static MrpgSkillSpells.Entry weapon_terra_staff_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_terra_staff_modifier_2");
        var title = "Earthen Blast";
        var bonus = 0.5F;
        var description = "Increases the area of effect of Stone Spear by {bonus}.";
        var mutator = new SpellTooltip.DescriptionMutator() {
            @Override
            public String mutate(Args args) {
                return args.description().replace("{bonus}", SpellTooltip.percent(bonus));
            }
        };
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.earthWizardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "elemental_wizards_rpg:terra_stone_spear";
        var extendedRadius = 2.5F * (1F + bonus);
        Spell.AreaImpact area_impact = new Spell.AreaImpact();
        area_impact.radius = extendedRadius;
        area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        area_impact.particles = new ParticleBatch[]{(new ParticleBatch("more_rpg_classes:stone_explosion", ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, 1.0F, 0.0F, 0.0F)).scale(extendedRadius/2)};
        area_impact.sound = Sound.withVolume(Identifier.of("block.pointed_dripstone.break"),1.5F);
        modifier.replacing_area_impact = area_impact;

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }

    public static final MrpgSkillSpells.Entry weapon_knuckle_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.WEAPON, MrpgSkillSpells.forcemasterFighterSchool,
            "weapon_knuckle_root", "forcemaster_rpg:burstcrack", "Burstcrack", 0.05F));

    public static final MrpgSkillSpells.Entry weapon_knuckle_modifier_1 = add(weapon_knuckle_modifier_1());
    private static MrpgSkillSpells.Entry weapon_knuckle_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "weapon_knuckle_modifier_1");
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
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

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.WEAPON));
    }
}
