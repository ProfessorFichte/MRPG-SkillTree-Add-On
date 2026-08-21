package com.mrpgc_skill_tree.skills;

import java.util.ArrayList;
import java.util.List;

public class WitcherSkillSpells {
    public static final List<MrpgSkillSpells.Entry> all = new ArrayList<>();

    private static MrpgSkillSpells.Entry add(MrpgSkillSpells.Entry entry) {
        all.add(entry);
        return entry;
    }
    /// SIGN ROOTS
    public static final MrpgSkillSpells.Entry signs_tier_2_spell_1_root = add(MrpgSkillsCommon.critRoot(
            MrpgSkillSpells.Category.WITCHER, MrpgSkillSpells.witcherSignSchool,
            "signs_tier_2_spell_1_root", "witcher_rpg:igni", "Igni", 0.1F));
    public static final MrpgSkillSpells.Entry signs_tier_2_spell_2_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.WITCHER, MrpgSkillSpells.witcherSignSchool,
            "signs_tier_2_spell_2_root", "witcher_rpg:aard", "Aard", 0.1F));
    public static final MrpgSkillSpells.Entry signs_tier_3_spell_1_root = add(MrpgSkillsCommon.cooldownRoot(
            MrpgSkillSpells.Category.WITCHER, MrpgSkillSpells.witcherSignSchool,
            "signs_tier_3_spell_1_root", "witcher_rpg:quen", "Quen", 2F));
    public static final MrpgSkillSpells.Entry signs_tier_3_spell_2_root = add(MrpgSkillsCommon.cooldownRoot(
            MrpgSkillSpells.Category.WITCHER, MrpgSkillSpells.witcherSignSchool,
            "signs_tier_3_spell_2_root", "witcher_rpg:axii", "Axii", 2F));
    public static final MrpgSkillSpells.Entry signs_tier_4_spell_1_root = add(MrpgSkillsCommon.critRoot(
            MrpgSkillSpells.Category.WITCHER, MrpgSkillSpells.witcherSignSchool,
            "signs_tier_2_spell_1_root", "witcher_rpg:igni_firestream", "Igni Firestream", 0.1F));
    public static final MrpgSkillSpells.Entry signs_tier_4_spell_2_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.WITCHER, MrpgSkillSpells.witcherSignSchool,
            "signs_tier_2_spell_2_root", "witcher_rpg:aard_sweep", "Aard Sweep", 0.1F));


    /// FENCING ROOTS
    public static final MrpgSkillSpells.Entry fencing_tier_2_spell_1_root = add(MrpgSkillsCommon.critRoot(
            MrpgSkillSpells.Category.WITCHER, MrpgSkillSpells.witcherFencingSchool,
            "fencing_tier_2_spell_1_root", "witcher_rpg:fast_attack", "Fast Attacks", 0.08F));
    public static final MrpgSkillSpells.Entry fencing_tier_2_spell_2_root = add(MrpgSkillsCommon.critDamageRoot(
            MrpgSkillSpells.Category.WITCHER, MrpgSkillSpells.witcherFencingSchool,
            "fencing_tier_2_spell_2_root", "witcher_rpg:strong_attack", "Strong Attacks", 0.15F));
    public static final MrpgSkillSpells.Entry fencing_tier_3_spell_1_root = add(MrpgSkillsCommon.radiusRoot(
            MrpgSkillSpells.Category.WITCHER, MrpgSkillSpells.witcherFencingSchool,
            "fencing_tier_3_spell_1_root", "witcher_rpg:witcher_senses", "Witcher Senses", 3F));
    public static final MrpgSkillSpells.Entry fencing_tier_3_spell_2_root = add(MrpgSkillsCommon.cooldownRoot(
            MrpgSkillSpells.Category.WITCHER, MrpgSkillSpells.witcherFencingSchool,
            "fencing_tier_3_spell_2_root", "witcher_rpg:battle_trance", "Battle Trance", 3F));
    public static final MrpgSkillSpells.Entry fencing_tier_4_spell_1_root = add(MrpgSkillsCommon.critRoot(
            MrpgSkillSpells.Category.WITCHER, MrpgSkillSpells.witcherFencingSchool,
            "fencing_tier_4_spell_1_root", "witcher_rpg:whirl", "Whirl", 0.08F));
    public static final MrpgSkillSpells.Entry fencing_tier_4_spell_2_root = add(MrpgSkillsCommon.critDamageRoot(
            MrpgSkillSpells.Category.WITCHER, MrpgSkillSpells.witcherFencingSchool,
            "fencing_tier_4_spell_2_root", "witcher_rpg:rend", "Rend", 0.15F));
}
