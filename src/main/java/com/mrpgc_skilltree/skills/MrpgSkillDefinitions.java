package com.mrpgc_skilltree.skills;

import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.more_rpg_classes.entity.attribute.MRPGCEntityAttributes;
import net.puffish.skillsmod.common.IconType;
import net.spell_engine.api.spell.container.SpellContainer;
import net.spell_engine.api.spell.container.SpellContainerHelper;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.List;

import static com.mrpgc_skilltree.MRPGCSkillTreeAddOn.MOD_ID;


public class MrpgSkillDefinitions {
    public static final Identifier CATEGORY_ID = Identifier.of(MOD_ID, "skill_tree_rpgs");
    public record Icon(IconType type, String value) {
        public static Icon texture(String texture) {
            return new Icon(IconType.TEXTURE, texture);
        }
        public static Icon item(String item) {
            return new Icon(IconType.ITEM, item);
        }
        public static Icon effect(String effect) {
            return new Icon(IconType.EFFECT, effect);
        }
        public static Icon spell(Identifier spellId) {
            return texture(spellId.getNamespace() + ":textures/spell/" + spellId.getPath() + ".png");
        }
    }
    public record EntityAttributeReward(RegistryEntry<EntityAttribute> attribute, EntityAttributeModifier modifier) {
        public static EntityAttributeReward of(RegistryEntry<EntityAttribute> attribute, double value, EntityAttributeModifier.Operation operation) {
            return new EntityAttributeReward(attribute, new EntityAttributeModifier(Identifier.of(MOD_ID + ":attribute_reward"), value, operation));
        }
    }
    public record Entry(String id, String title, String description, Icon icon, List<SpellContainer> spellReward, EntityAttributeReward attributeReward) {
        public static Entry spell(String id, String title, String description, Icon icon, List<SpellContainer> spellReward) {
            return new Entry(id, title, description, icon, spellReward, null);
        }
        public static Entry attribute(String id, String title, String description, Icon icon,
                                      RegistryEntry<EntityAttribute> attribute, double value, EntityAttributeModifier.Operation operation) {
            return attribute(id, title, description, icon, EntityAttributeReward.of(attribute, value, operation));
        }
        public static Entry attribute(String id, String title, String description, Icon icon, EntityAttributeReward attributeReward) {
            return new Entry(id, title, description, icon, null, attributeReward);
        }
        public String titleTranslationKey() {
            return "skill." + MOD_ID+ "." + id + ".title";
        }
        public String descriptionTranslationKey() {
            return "skill." + MOD_ID + "." + id + ".description";
        }
        public Entry withIcon(Icon icon) {
            return new Entry(id, title, description, icon, spellReward, attributeReward);
        }
    }
    public static final ArrayList<Entry> ENTRIES = new ArrayList<>();
    private static Entry add(Entry entry) {
        ENTRIES.add(entry);
        return entry;
    }


    public static final float ROOT_MULTIPLIER = 0.01f;
    public static final float BOOST_MULTIPLIER = 0.01f;

    private static List<SpellContainer> dummyContainer() {
        return List.of(SpellContainerHelper.createForSpellHost(Identifier.of("wizards:fireball")));
    }

    private static Entry modifierSpell(MrpgSkillSpells.Entry entry) {
        var modifiedSpellId = Identifier.of(entry.spell().modifiers.getFirst().spell_pattern);
        return Entry.spell(entry.id().getPath(),
                entry.title(),
                null,
                Icon.spell(modifiedSpellId),
                List.of(SpellContainerHelper.createForModifier(entry.id()))
        );
    }

    private static Entry passiveSpell(MrpgSkillSpells.Entry entry) {
        return Entry.spell(entry.id().getPath(),
                entry.title(),
                null,
                Icon.spell(entry.id()),
                List.of(SpellContainerHelper.createForSpellHost(entry.id()))
        );
    }

    ///AIR WIZARD
    public static final Entry AIR_ROOT = add(
            Entry.attribute("air_root",
                    "Path of Air",
                    null,
                    Icon.item("elemental_wizards_rpg:wind_spell_book"),
                    MoreSpellSchools.AIR.attributeEntry,
                    0.01,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            )
    );
    public static final Entry AIR_BOOST = add(
            Entry.attribute("air_boost",
                    "Air Attunement",
                    null,
                    Icon.item("elemental_wizards:wand_wind"),
                    AIR_ROOT.attributeReward())
    );
    public static final Entry AIR_SPEC_A_MODIFIER_1 = add(modifierSpell(MrpgSkillSpells.air_spec_a_modifier_1));
    public static final Entry AIR_SPEC_B_MODIFIER_1 = add(modifierSpell(MrpgSkillSpells.air_spec_b_modifier_1));
    public static final Entry AIR_SPEC_A_MODIFIER_2 = add(modifierSpell(MrpgSkillSpells.air_spec_a_modifier_2));
    public static final Entry AIR_SPEC_B_MODIFIER_2 = add(modifierSpell(MrpgSkillSpells.air_spec_b_modifier_2));
    public static final Entry AIR_SPEC_A_MODIFIER_3 = add(modifierSpell(MrpgSkillSpells.air_spec_a_modifier_3));
    public static final Entry AIR_SPEC_B_MODIFIER_3 = add(modifierSpell(MrpgSkillSpells.air_spec_b_modifier_3));
    public static final Entry AIR_SPEC_A_MODIFIER_4 = add(modifierSpell(MrpgSkillSpells.air_spec_a_modifier_4));
    public static final Entry AIR_SPEC_B_MODIFIER_4 = add(modifierSpell(MrpgSkillSpells.air_spec_b_modifier_4));
    public static final Entry AIR_SPEC_A_PASSIVE_1 = add(modifierSpell());
    public static final Entry AIR_SPEC_B_PASSIVE_1 = add(modifierSpell());
    public static final Entry AIR_SPEC_A_PASSIVE_2 = add(modifierSpell());
    public static final Entry AIR_SPEC_B_PASSIVE_2 = add(modifierSpell());
    public static final Entry AIR_SPEC_A_PASSIVE_3 = add(modifierSpell());
    public static final Entry AIR_SPEC_B_PASSIVE_3 = add(modifierSpell());

    ///EARTH WIZARD
    public static final Entry EARTH_ROOT = add(
            Entry.attribute("earth_root",
                    "Path of Earth",
                    null,
                    Icon.item("elemental_wizards_rpg:terra_spell_book"),
                    MoreSpellSchools.EARTH.attributeEntry,
                    0.01,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            )
    );
    public static final Entry EARTH_BOOST = add(
            Entry.attribute("earth_boost",
                    "Earth Attunement",
                    null,
                    Icon.item("elemental_wizards:wand_terra"),
                    EARTH_ROOT.attributeReward())
    );
    public static final Entry EARTH_SPEC_A_MODIFIER_1 = add(modifierSpell(MrpgSkillSpells.earth_spec_a_modifier_1));
    public static final Entry EARTH_SPEC_B_MODIFIER_1 = add(modifierSpell(MrpgSkillSpells.earth_spec_b_modifier_1));
    public static final Entry EARTH_SPEC_A_MODIFIER_2 = add(modifierSpell(MrpgSkillSpells.earth_spec_a_modifier_2));
    public static final Entry EARTH_SPEC_B_MODIFIER_2 = add(modifierSpell(MrpgSkillSpells.earth_spec_b_modifier_2));
    public static final Entry EARTH_SPEC_A_MODIFIER_3 = add(modifierSpell(MrpgSkillSpells.earth_spec_a_modifier_3));
    public static final Entry EARTH_SPEC_B_MODIFIER_3 = add(modifierSpell(MrpgSkillSpells.earth_spec_b_modifier_3));
    public static final Entry EARTH_SPEC_A_MODIFIER_4 = add(modifierSpell(MrpgSkillSpells.earth_spec_a_modifier_4));
    public static final Entry EARTH_SPEC_B_MODIFIER_4 = add(modifierSpell(MrpgSkillSpells.earth_spec_b_modifier_4));
    public static final Entry EARTH_SPEC_A_PASSIVE_1 = add(modifierSpell());
    public static final Entry EARTH_SPEC_B_PASSIVE_1 = add(modifierSpell());
    public static final Entry EARTH_SPEC_A_PASSIVE_2 = add(modifierSpell());
    public static final Entry EARTH_SPEC_B_PASSIVE_2 = add(modifierSpell());
    public static final Entry EARTH_SPEC_A_PASSIVE_3 = add(modifierSpell());
    public static final Entry EARTH_SPEC_B_PASSIVE_3 = add(modifierSpell());
    ///WATER WIZARD
    public static final Entry WATER_ROOT = add(
            Entry.attribute("water_root",
                    "Path of Water",
                    null,
                    Icon.item("elemental_wizards_rpg:aqua_spell_book"),
                    MoreSpellSchools.WATER.attributeEntry,
                    0.01,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            )
    );
    public static final Entry WATER_BOOST = add(
            Entry.attribute("water_boost",
                    "Water Attunement",
                    null,
                    Icon.item("elemental_wizards:wand_aqua"),
                    WATER_ROOT.attributeReward())
    );
    public static final Entry WATER_SPEC_A_MODIFIER_1 = add(modifierSpell(MrpgSkillSpells.water_spec_a_modifier_1));
    public static final Entry WATER_SPEC_B_MODIFIER_1 = add(modifierSpell(MrpgSkillSpells.water_spec_b_modifier_1));
    public static final Entry WATER_SPEC_A_MODIFIER_2 = add(modifierSpell(MrpgSkillSpells.water_spec_a_modifier_2));
    public static final Entry WATER_SPEC_B_MODIFIER_2 = add(modifierSpell(MrpgSkillSpells.water_spec_b_modifier_2));
    public static final Entry WATER_SPEC_A_MODIFIER_3 = add(modifierSpell(MrpgSkillSpells.water_spec_a_modifier_3));
    public static final Entry WATER_SPEC_B_MODIFIER_3 = add(modifierSpell(MrpgSkillSpells.water_spec_b_modifier_3));
    public static final Entry WATER_SPEC_A_MODIFIER_4 = add(modifierSpell(MrpgSkillSpells.water_spec_a_modifier_4));
    public static final Entry WATER_SPEC_B_MODIFIER_4 = add(modifierSpell(MrpgSkillSpells.water_spec_b_modifier_4));
    public static final Entry WATER_SPEC_A_PASSIVE_1 = add(modifierSpell());
    public static final Entry WATER_SPEC_B_PASSIVE_1 = add(modifierSpell());
    public static final Entry WATER_SPEC_A_PASSIVE_2 = add(modifierSpell());
    public static final Entry WATER_SPEC_B_PASSIVE_2 = add(modifierSpell());
    public static final Entry WATER_SPEC_A_PASSIVE_3 = add(modifierSpell());
    public static final Entry WATER_SPEC_B_PASSIVE_3 = add(modifierSpell());
    ///BERSERKER
    public static final Entry BERSERKER_ROOT = add(
            Entry.attribute("berserker_root",
                    "Path of the Berserker",
                    null,
                    Icon.item("berserker_rpg:berserker_spell_book"),
                    MRPGCEntityAttributes.RAGE_MODIFIER,
                    0.01,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            )
    );
    public static final Entry BERSERKER_BOOST = add(
            Entry.attribute("berserker_boost",
                    "Path of the Berserker",
                    null,
                    Icon.item("berserker_rpg:iron_berserker_axe"),
                    BERSERKER_ROOT.attributeReward())
    );
    public static final Entry BERSERKER_SPEC_A_MODIFIER_1 = add(modifierSpell(MrpgSkillSpells.berserker_spec_a_modifier_1));
    public static final Entry BERSERKER_SPEC_B_MODIFIER_1 = add(modifierSpell(MrpgSkillSpells.berserker_spec_b_modifier_1));
    public static final Entry BERSERKER_SPEC_A_MODIFIER_2 = add(modifierSpell(MrpgSkillSpells.berserker_spec_a_modifier_2));
    public static final Entry BERSERKER_SPEC_B_MODIFIER_2 = add(modifierSpell(MrpgSkillSpells.berserker_spec_b_modifier_2));
    public static final Entry BERSERKER_SPEC_A_MODIFIER_3 = add(modifierSpell(MrpgSkillSpells.berserker_spec_a_modifier_3));
    public static final Entry BERSERKER_SPEC_B_MODIFIER_3 = add(modifierSpell(MrpgSkillSpells.berserker_spec_b_modifier_3));
    public static final Entry BERSERKER_SPEC_A_MODIFIER_4 = add(modifierSpell(MrpgSkillSpells.berserker_spec_a_modifier_4));
    public static final Entry BERSERKER_SPEC_B_MODIFIER_4 = add(modifierSpell(MrpgSkillSpells.berserker_spec_b_modifier_4));
    public static final Entry BERSERKER_SPEC_A_PASSIVE_1 = add(modifierSpell());
    public static final Entry BERSERKER_SPEC_B_PASSIVE_1 = add(modifierSpell());
    public static final Entry BERSERKER_SPEC_A_PASSIVE_2 = add(modifierSpell());
    public static final Entry BERSERKER_SPEC_B_PASSIVE_2 = add(modifierSpell());
    public static final Entry BERSERKER_SPEC_A_PASSIVE_3 = add(modifierSpell());
    public static final Entry BERSERKER_SPEC_B_PASSIVE_3 = add(modifierSpell());

    ///FORCEMASTER
    public static final Entry FORCEMASTER_ROOT = add(
            Entry.attribute("forcemaster_root",
                    "Path of the Forcemaster",
                    null,
                    Icon.item("forcemaster_rpg:forcemaster_spell_book"),
                    EntityAttributes.GENERIC_ATTACK_SPEED,
                    0.01,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            )
    );
    public static final Entry FORCEMASTER_BOOST = add(
            Entry.attribute("forcemaster_boost",
                    "Forcemaster Empowerment",
                    null,
                    Icon.item("forcemaster_rpg:iron_knuckle"),
                    FORCEMASTER_ROOT.attributeReward())
    );
    public static final Entry FORCEMASTER_SPEC_A_MODIFIER_1 = add(modifierSpell());
    public static final Entry FORCEMASTER_SPEC_B_MODIFIER_1 = add(modifierSpell());
    public static final Entry FORCEMASTER_SPEC_A_MODIFIER_2 = add(modifierSpell());
    public static final Entry FORCEMASTER_SPEC_B_MODIFIER_2 = add(modifierSpell());
    public static final Entry FORCEMASTER_SPEC_A_MODIFIER_3 = add(modifierSpell());
    public static final Entry FORCEMASTER_SPEC_B_MODIFIER_3 = add(modifierSpell());
    public static final Entry FORCEMASTER_SPEC_A_MODIFIER_4 = add(modifierSpell());
    public static final Entry FORCEMASTER_SPEC_B_MODIFIER_4 = add(modifierSpell());
    public static final Entry FORCEMASTER_SPEC_A_PASSIVE_1 = add(modifierSpell());
    public static final Entry FORCEMASTER_SPEC_B_PASSIVE_1 = add(modifierSpell());
    public static final Entry FORCEMASTER_SPEC_A_PASSIVE_2 = add(modifierSpell());
    public static final Entry FORCEMASTER_SPEC_B_PASSIVE_2 = add(modifierSpell());
    public static final Entry FORCEMASTER_SPEC_A_PASSIVE_3 = add(modifierSpell());
    public static final Entry FORCEMASTER_SPEC_B_PASSIVE_3 = add(modifierSpell());
    ///WAR ARCHER
    public static final Entry WAR_ARCHER_ROOT = add(
            Entry.attribute("war_archer_root",
                    "Path of the War Archer",
                    null,
                    Icon.item("archers_expansion:war_archer_spell_book"),
                    EntityAttributes_RangedWeapon.DAMAGE.entry,
                    0.01,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            )
    );
    public static final Entry WAR_ARCHER_BOOST = add(
            Entry.attribute("war_archer_boost",
                    "War Archer Empowerment",
                    null,
                    Icon.item("archers:heavy_crossbow_standby"),
                    WAR_ARCHER_ROOT.attributeReward())
    );
    public static final Entry WAR_ARCHER_SPEC_A_MODIFIER_1 = add(modifierSpell());
    public static final Entry WAR_ARCHER_SPEC_B_MODIFIER_1 = add(modifierSpell());
    public static final Entry WAR_ARCHER_SPEC_A_MODIFIER_2 = add(modifierSpell());
    public static final Entry WAR_ARCHER_SPEC_B_MODIFIER_2 = add(modifierSpell());
    public static final Entry WAR_ARCHER_SPEC_A_MODIFIER_3 = add(modifierSpell());
    public static final Entry WAR_ARCHER_SPEC_B_MODIFIER_3 = add(modifierSpell());
    public static final Entry WAR_ARCHER_SPEC_A_MODIFIER_4 = add(modifierSpell());
    public static final Entry WAR_ARCHER_SPEC_B_MODIFIER_4 = add(modifierSpell());
    public static final Entry WAR_ARCHER_SPEC_A_PASSIVE_1 = add(modifierSpell());
    public static final Entry WAR_ARCHER_SPEC_B_PASSIVE_1 = add(modifierSpell());
    public static final Entry WAR_ARCHER_SPEC_A_PASSIVE_2 = add(modifierSpell());
    public static final Entry WAR_ARCHER_SPEC_B_PASSIVE_2 = add(modifierSpell());
    public static final Entry WAR_ARCHER_SPEC_A_PASSIVE_3 = add(modifierSpell());
    public static final Entry WAR_ARCHER_SPEC_B_PASSIVE_3 = add(modifierSpell());
    ///DEADEYE
    public static final Entry DEADEYE_ROOT = add(
            Entry.attribute("deadeye_root",
                    "Path of the Deadeye",
                    null,
                    Icon.item("archers_expansion:deadeye_spell_book"),
                    EntityAttributes_RangedWeapon.HASTE.entry,
                    0.01,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            )
    );
    public static final Entry DEADEYE_BOOST = add(
            Entry.attribute("deadeye_boost",
                    "Deadeye Empowerment",
                    null,
                    Icon.item("archers:mechanic_shortbow"),
                    DEADEYE_ROOT.attributeReward())
    );
    public static final Entry DEADEYE_SPEC_A_MODIFIER_1 = add(modifierSpell());
    public static final Entry DEADEYE_SPEC_B_MODIFIER_1 = add(modifierSpell());
    public static final Entry DEADEYE_SPEC_A_MODIFIER_2 = add(modifierSpell());
    public static final Entry DEADEYE_SPEC_B_MODIFIER_2 = add(modifierSpell());
    public static final Entry DEADEYE_SPEC_A_MODIFIER_3 = add(modifierSpell());
    public static final Entry DEADEYE_SPEC_B_MODIFIER_3 = add(modifierSpell());
    public static final Entry DEADEYE_SPEC_A_MODIFIER_4 = add(modifierSpell());
    public static final Entry DEADEYE_SPEC_B_MODIFIER_4 = add(modifierSpell());
    public static final Entry DEADEYE_SPEC_A_PASSIVE_1 = add(modifierSpell());
    public static final Entry DEADEYE_SPEC_B_PASSIVE_1 = add(modifierSpell());
    public static final Entry DEADEYE_SPEC_A_PASSIVE_2 = add(modifierSpell());
    public static final Entry DEADEYE_SPEC_B_PASSIVE_2 = add(modifierSpell());
    public static final Entry DEADEYE_SPEC_A_PASSIVE_3 = add(modifierSpell());
    public static final Entry DEADEYE_SPEC_B_PASSIVE_3 = add(modifierSpell());
    ///TUNDRA HUNTER
    public static final Entry TUNDRA_HUNTER_ROOT = add(
            Entry.attribute("tundra_hunter_root",
                    "Path of the Tundra Hunter",
                    null,
                    Icon.item("archers_expansion:tundra_hunter_spell_book"),
                    SpellSchools.FROST.attributeEntry,
                    0.2,
                    EntityAttributeModifier.Operation.ADD_VALUE
            )
    );
    public static final Entry TUNDRA_HUNTER_BOOST = add(
            Entry.attribute("tundra_hunter_boost",
                    "Tundra Hunter Empowerment",
                    null,
                    Icon.item("archers:rapid_crossbow_standby"),
                    TUNDRA_HUNTER_ROOT.attributeReward())
    );
    public static final Entry TUNDRA_HUNTER_SPEC_A_MODIFIER_1 = add(modifierSpell());
    public static final Entry TUNDRA_HUNTER_SPEC_B_MODIFIER_1 = add(modifierSpell());
    public static final Entry TUNDRA_HUNTER_SPEC_A_MODIFIER_2 = add(modifierSpell());
    public static final Entry TUNDRA_HUNTER_SPEC_B_MODIFIER_2 = add(modifierSpell());
    public static final Entry TUNDRA_HUNTER_SPEC_A_MODIFIER_3 = add(modifierSpell());
    public static final Entry TUNDRA_HUNTER_SPEC_B_MODIFIER_3 = add(modifierSpell());
    public static final Entry TUNDRA_HUNTER_SPEC_A_MODIFIER_4 = add(modifierSpell());
    public static final Entry TUNDRA_HUNTER_SPEC_B_MODIFIER_4 = add(modifierSpell());
    public static final Entry TUNDRA_HUNTER_SPEC_A_PASSIVE_1 = add(modifierSpell());
    public static final Entry TUNDRA_HUNTER_SPEC_B_PASSIVE_1 = add(modifierSpell());
    public static final Entry TUNDRA_HUNTER_SPEC_A_PASSIVE_2 = add(modifierSpell());
    public static final Entry TUNDRA_HUNTER_SPEC_B_PASSIVE_2 = add(modifierSpell());
    public static final Entry TUNDRA_HUNTER_SPEC_A_PASSIVE_3 = add(modifierSpell());
    public static final Entry TUNDRA_HUNTER_SPEC_B_PASSIVE_3 = add(modifierSpell());
    ///WITCHER
    public static final Entry WITCHER_ROOT = add(
            Entry.attribute("witcher_root",
                    "Path of the Witcher",
                    null,
                    Icon.item("witcher_rpg:master_spell_book"),
                    EntityAttributes.GENERIC_ATTACK_DAMAGE,
                    0.01,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            )
    );
    public static final Entry WITCHER_BOOST = add(
            Entry.attribute("witcher_boost",
                    "Witcher Empowerment",
                    null,
                    Icon.item("witcher_rpg:iron_witcher_sword"),
                    WITCHER_ROOT.attributeReward())
    );
    public static final Entry WITCHER_SPEC_A_MODIFIER_1 = add(modifierSpell());
    public static final Entry WITCHER_SPEC_B_MODIFIER_1 = add(modifierSpell());
    public static final Entry WITCHER_SPEC_A_MODIFIER_2 = add(modifierSpell());
    public static final Entry WITCHER_SPEC_B_MODIFIER_2 = add(modifierSpell());
    public static final Entry WITCHER_SPEC_A_MODIFIER_3 = add(modifierSpell());
    public static final Entry WITCHER_SPEC_B_MODIFIER_3 = add(modifierSpell());
    public static final Entry WITCHER_SPEC_A_MODIFIER_4 = add(modifierSpell());
    public static final Entry WITCHER_SPEC_B_MODIFIER_4 = add(modifierSpell());
    public static final Entry WITCHER_SPEC_A_PASSIVE_1 = add(modifierSpell());
    public static final Entry WITCHER_SPEC_B_PASSIVE_1 = add(modifierSpell());
    public static final Entry WITCHER_SPEC_A_PASSIVE_2 = add(modifierSpell());
    public static final Entry WITCHER_SPEC_B_PASSIVE_2 = add(modifierSpell());
    public static final Entry WITCHER_SPEC_A_PASSIVE_3 = add(modifierSpell());
    public static final Entry WITCHER_SPEC_B_PASSIVE_3 = add(modifierSpell());

    /*
    public static final Entry PRIEST_SPEC_A_PASSIVE_2 = add(
            Entry.spell("priest_spec_a_passive_2",
                    "Priest Spec A Passive 2",
                    "Placeholder",
                    Icon.spell(Identifier.of("paladins", "priest_spec_a_passive_2")),
                    dummyContainer()
            )
    );
     */

}
