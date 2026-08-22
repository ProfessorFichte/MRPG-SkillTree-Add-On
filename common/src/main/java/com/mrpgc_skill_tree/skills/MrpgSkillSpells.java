package com.mrpgc_skill_tree.skills;

import net.minecraft.util.Identifier;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.effect.SpellEngineEffects;
import net.spell_engine.api.entity.SpellEntityPredicates;
import net.spell_engine.api.spell.ExternalSpellSchools;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.tooltip.TooltipTokens;
import net.spell_engine.api.util.TriState;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static com.mrpgc_skill_tree.MRPGCSkillTreeAddOn.MOD_ID;

public class MrpgSkillSpells {
    public static final String NAMESPACE = MOD_ID;
    public enum Category {
        AIR, EARTH, WATER, BERSERKER, FORCEMASTER, WAR_ARCHER, DEADEYE, TUNDRA_HUNTER, BARD, WITCHER, WEAPON
    }
    public record Entry(Identifier id, Spell spell, String title, String description,
                        EnumSet<Category> categories) {
        public Entry(Identifier id, Spell spell, String title, String description, Category category) {
            this(id, spell, title, description, EnumSet.of(category));
        }
        public String key() {
            return id.getPath();
        }
    }

    /// Registers the description values that no declarative `{...}` token can express. Every one of
    /// this mod's former `SpellTooltip.DescriptionMutator`s became an `{effect|...}` token or a value
    /// baked into the description at build time; these two are the only survivors, and both read a
    /// spell's own impact coefficient as a *fraction of max health* — something the built-in
    /// `{damage}` / `{heal}` tokens can't say, since they estimate an absolute number.
    ///
    /// `TooltipTokens` is server-safe; this is called from client init only because the tooltip is a
    /// client concern.
    public static void registerTooltipTokens() {
        // Both were written as `{power_multiplier}` — a real built-in token, but one that resolves
        // only from a `Spell.Modifier.power_modifier`, which neither spell has. With nothing to
        // replace them they rendered as the literal text `{power_multiplier}`.
        maxHealthPercent(BerserkerSkillSpells.berserker_tier_3_spell_1_modifier_1.id(),
                Spell.Impact.Action.Type.DAMAGE);
        maxHealthPercent(ForcemasterSkillSpells.forcemaster_tier_4_spell_2_modifier_2.id(),
                Spell.Impact.Action.Type.HEAL);
    }

    /// Token used by [#maxHealthPercent]. Written into the description as a literal so the datagen'd
    /// lang value carries it; resolved at render time by the handler below.
    static final String maxHealthPercentToken = "{max_health_percent}";

    /// Resolves [#maxHealthPercentToken] to the spell's own impact coefficient, as a percentage. Read
    /// off the live registry entry, so a datapack override of the spell is reflected. Uses `percent`,
    /// not `bakedPercent`: the value is spliced in *after* translation, so `%` must not be doubled.
    private static void maxHealthPercent(Identifier spellId, Spell.Impact.Action.Type type) {
        TooltipTokens.registerCustom(spellId, args -> {
            var spell = args.spellEntry().value();
            var impacts = new ArrayList<Spell.Impact>(spell.impacts);
            if (spell.modifiers != null) {
                for (var modifier : spell.modifiers) {
                    impacts.addAll(modifier.impacts);
                }
            }
            for (var impact : impacts) {
                if (impact.action == null || impact.action.type != type) {
                    continue;
                }
                var coefficient = switch (type) {
                    case DAMAGE -> impact.action.damage != null ? impact.action.damage.spell_power_coefficient : null;
                    case HEAL -> impact.action.heal != null ? impact.action.heal.spell_power_coefficient : null;
                    default -> null;
                };
                if (coefficient != null) {
                    return args.description().replace(maxHealthPercentToken, TooltipTokens.percent(coefficient));
                }
            }
            return args.description();
        });
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
    static final SpellEntityPredicates.Entry HAS_BLEEDING = SpellEntityPredicates.hasEffectOptimized(SpellEngineEffects.BLEED.id);
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
    public static final SpellSchool bardSchool = SpellSchools.ARCANE;
    public static final SpellSchool witcherSignSchool = SpellSchools.ARCANE;
    public static final SpellSchool witcherFencingSchool = ExternalSpellSchools.PHYSICAL_MELEE;

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
        all.addAll(BardSkillSpells.all);
        all.addAll(WitcherSkillSpells.all);
        all.addAll(MrpgWeaponSkills.all);
    }
}
