package com.mrpgc_skill_tree.skills;

import net.minecraft.util.Identifier;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.entity.SpellEntityPredicates;
import net.spell_engine.api.spell.ExternalSpellSchools;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.util.TriState;
import net.spell_engine.client.gui.SpellTooltip;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static com.mrpgc_skill_tree.MRPGCSkillTreeAddOn.MOD_ID;

public class MrpgSkillSpells {
    public static final String NAMESPACE = MOD_ID;
    public enum Category {
        AIR, EARTH, WATER, BERSERKER, FORCEMASTER, WAR_ARCHER, DEADEYE, TUNDRA_HUNTER
    }
    public record Entry(Identifier id, Spell spell, String title, String description,
                        @Nullable SpellTooltip.DescriptionMutator mutator, EnumSet<Category> categories) {
        public Entry(Identifier id, Spell spell, String title, String description,
                     @Nullable SpellTooltip.DescriptionMutator mutator, Category category) {
            this(id, spell, title, description, mutator, EnumSet.of(category));
        }
        public String key() {
            return id.getPath();
        }
    }

    static Spell createModifierAlikePassiveSpell() {
        var spell = SpellBuilder.createSpellPassive();
        spell.range = 0;
        spell.tooltip = new Spell.Tooltip();
        spell.tooltip.show_activation = false;
        return spell;
    }
    static Spell.Impact.TargetModifier createImpactModifier(String entityType) {
        var condition = new Spell.TargetCondition();
        condition.entity_type = entityType;
        var modifier = new Spell.Impact.TargetModifier();
        modifier.conditions = List.of(condition);
        return modifier;
    }
    static void undeadAllow(Spell.Impact impact) {
        var modifier = createImpactModifier("#minecraft:undead");
        modifier.execute = TriState.ALLOW;
        impact.target_modifiers = List.of(modifier);
    }
    static void bleedingDeny(Spell.Impact impact) {
        var modifier = createImpactModifier("#more_rpg_classes:bleeding_immune");
        modifier.execute = TriState.DENY;
        impact.target_modifiers = List.of(modifier);
    }
    static void poisonDeny(Spell.Impact impact) {
        var modifier = createImpactModifier("#minecraft:ignores_poison_and_regen");
        modifier.execute = TriState.DENY;
        impact.target_modifiers = List.of(modifier);
    }
    static void freezeImmuneDeny(Spell.Impact impact) {
        var modifier = createImpactModifier("#minecraft:freeze_immune_entity_types");
        modifier.execute = TriState.DENY;
        impact.target_modifiers = List.of(modifier);
    }
    static void bossImmuneDeny(Spell.Impact impact) {
        var modifier = createImpactModifier("#c:bosses");
        modifier.execute = TriState.DENY;
        impact.target_modifiers = List.of(modifier);
    }
    static final SpellEntityPredicates.Entry HAS_BLEEDING = SpellEntityPredicates.hasEffectOptimized(Identifier.of("more_rpg_classes", "bleeding"));
    static final SpellEntityPredicates.Entry HAS_FROSTED = SpellEntityPredicates.hasEffectOptimized(Identifier.of("more_rpg_classes", "frosted"));
    static final SpellEntityPredicates.Entry HAS_RAGE = SpellEntityPredicates.hasEffectOptimized(Identifier.of("berserker_rpg", "rage"));

    public static final SpellSchool airWizardSchool = MoreSpellSchools.AIR;
    public static final SpellSchool earthWizardSchool = MoreSpellSchools.EARTH;
    public static final SpellSchool waterWizardSchool = MoreSpellSchools.WATER;
    public static final SpellSchool berserkerSchool = MoreSpellSchools.RAGE_MELEE;
    public static final SpellSchool forcemasterFighterSchool = ExternalSpellSchools.PHYSICAL_MELEE;
    public static final SpellSchool forcemasterCasterSchool = SpellSchools.ARCANE;
    public static final SpellSchool warArcherSchool = MoreSpellSchools.FIRE_RANGED;
    public static final SpellSchool deadeyeSchool = ExternalSpellSchools.PHYSICAL_RANGED;
    public static final SpellSchool tundraHunterSchool = MoreSpellSchools.FROST_RANGED;

    public static final List<Entry> all;
    static {
        all = new ArrayList<>();
        all.addAll(AirSkillSpells.all);
        all.addAll(EarthSkillSpells.all);
        all.addAll(WaterSkillSpells.all);
        all.addAll(BerserkerSkillSpells.all);
        all.addAll(ForcemasterSkillSpells.all);
        all.addAll(WarArcherSkillSpells.all);
        all.addAll(DeadeyeSkillSpells.all);
        all.addAll(TundraHunterSkillSpells.all);
    }
}
