package com.mrpgc_skill_tree.skills;

import net.minecraft.util.Identifier;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.spell.Spell;
import net.spell_power.api.SpellSchool;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;

public class MrpgSkillsCommon {

    public static MrpgSkillSpells.Entry spellRoot(MrpgSkillSpells.Category category, SpellSchool school,
                                                  String path, String spellPattern, String spellName,
                                                  String description, Consumer<Spell.Modifier> configure) {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, path);
        var spell = SpellBuilder.createSpellModifier();
        spell.school = school;
        var modifier = new Spell.Modifier();
        modifier.spell_pattern = spellPattern;
        configure.accept(modifier);
        spell.modifiers = List.of(modifier);
        return new MrpgSkillSpells.Entry(id, spell, "Improved " + spellName, description, null, EnumSet.of(category));
    }

    public static MrpgSkillSpells.Entry critRoot(MrpgSkillSpells.Category category, SpellSchool school,
                                                 String path, String spellPattern, String spellName, float chance) {
        return spellRoot(category, school, path, spellPattern, spellName,
                spellName + " has {critical_chance_bonus} increased critical strike chance.",
                modifier -> {
                    modifier.power_modifier = new Spell.Impact.Modifier();
                    modifier.power_modifier.critical_chance_bonus = chance;
                });
    }

    public static MrpgSkillSpells.Entry critDamageRoot(MrpgSkillSpells.Category category, SpellSchool school,
                                                       String path, String spellPattern, String spellName, float bonus) {
        return spellRoot(category, school, path, spellPattern, spellName,
                "Critical strikes of " + spellName + " deal {critical_damage_bonus} increased damage.",
                modifier -> {
                    modifier.power_modifier = new Spell.Impact.Modifier();
                    modifier.power_modifier.critical_damage_bonus = bonus;
                });
    }

    public static MrpgSkillSpells.Entry cooldownRoot(MrpgSkillSpells.Category category, SpellSchool school,
                                                     String path, String spellPattern, String spellName, float seconds) {
        return spellRoot(category, school, path, spellPattern, spellName,
                "Reduces the cooldown of " + spellName + " by {cooldown_duration_deduct} sec.",
                modifier -> modifier.cooldown_duration_deduct = seconds);
    }

    public static MrpgSkillSpells.Entry powerRoot(MrpgSkillSpells.Category category, SpellSchool school,
                                                  String path, String spellPattern, String spellName, float multiplier) {
        return spellRoot(category, school, path, spellPattern, spellName,
                spellName + " power increased by {power_multiplier}.",
                modifier -> {
                    modifier.power_modifier = new Spell.Impact.Modifier();
                    modifier.power_modifier.power_multiplier = multiplier;
                });
    }

    public static MrpgSkillSpells.Entry reachRoot(MrpgSkillSpells.Category category, SpellSchool school,
                                                  String path, String spellPattern, String spellName, float blocks) {
        return spellRoot(category, school, path, spellPattern, spellName,
                "Range of " + spellName + " increased by {range_add} blocks.",
                modifier -> modifier.range_add = blocks);
    }

    public static MrpgSkillSpells.Entry teleportRoot(MrpgSkillSpells.Category category, SpellSchool school,
                                                     String path, String spellPattern, String spellName, float blocks) {
        return spellRoot(category, school, path, spellPattern, spellName,
                spellName + " teleports {teleport_distance_add} blocks further.",
                modifier -> modifier.teleport_distance_add = blocks);
    }

    public static MrpgSkillSpells.Entry radiusRoot(MrpgSkillSpells.Category category, SpellSchool school,
                                                   String path, String spellPattern, String spellName, float blocks) {
        return spellRoot(category, school, path, spellPattern, spellName,
                "Radius of " + spellName + " increased by {range_add} blocks.",
                modifier -> modifier.range_add = blocks);
    }

    public static MrpgSkillSpells.Entry lingerRoot(MrpgSkillSpells.Category category, SpellSchool school,
                                                   String path, String spellPattern, String spellName, float seconds) {
        return spellRoot(category, school, path, spellPattern, spellName,
                "Effects applied by " + spellName + " last {effect_duration_add} sec longer.",
                modifier -> modifier.effect_duration_add = seconds);
    }

    public static MrpgSkillSpells.Entry fieldRoot(MrpgSkillSpells.Category category, SpellSchool school,
                                                  String path, String spellPattern, String spellName, float seconds) {
        return spellRoot(category, school, path, spellPattern, spellName,
                spellName + " persists {spawn_duration_add} sec longer.",
                modifier -> modifier.spawn_duration_add = seconds);
    }

    public static MrpgSkillSpells.Entry channelRoot(MrpgSkillSpells.Category category, SpellSchool school,
                                                    String path, String spellPattern, String spellName, int releases) {
        return spellRoot(category, school, path, spellPattern, spellName,
                "Channeling " + spellName + " releases {channel_ticks_add} additional times.",
                modifier -> modifier.channel_ticks_add = releases);
    }

    public static MrpgSkillSpells.Entry companionRoot(MrpgSkillSpells.Category category, SpellSchool school,
                                                      String path, String spellPattern, String spellName, int seconds) {
        return spellRoot(category, school, path, spellPattern, spellName,
                spellName + " lasts " + seconds + " sec longer.",
                modifier -> modifier.summon_behaviour.lifespan.active_seconds_add = seconds);
    }

    public static MrpgSkillSpells.Entry heftRoot(MrpgSkillSpells.Category category, SpellSchool school,
                                                 String path, String spellPattern, String spellName, float scale) {
        return spellRoot(category, school, path, spellPattern, spellName,
                spellName + " projectile is " + Math.round(scale * 100) + "% larger.",
                modifier -> modifier.projectile_scale_multiply = scale);
    }

    public static MrpgSkillSpells.Entry meleeRoot(MrpgSkillSpells.Category category, SpellSchool school,
                                                  String path, String spellPattern, String spellName, float multiplier) {
        return spellRoot(category, school, path, spellPattern, spellName,
                spellName + " deals {melee_damage_multiplier} increased damage.",
                modifier -> modifier.melee_damage_multiplier = multiplier);
    }
}
