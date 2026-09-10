package com.mrpgc_skill_tree.skills;

import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.more_rpg_classes.entity.attribute.MRPGCEntityAttributes;
import net.puffish.skillsmod.common.IconType;
import net.skill_tree_rpgs.node.ConditionalAttributeReward;
import net.spell_engine.api.spell.container.SpellContainer;
import net.spell_engine.api.spell.container.SpellContainers;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.List;

import static com.mrpgc_skill_tree.MRPGCSkillTreeAddOn.MOD_ID;


public class MrpgSkillDefinitions {
    public static final Identifier CATEGORY_ID = Identifier.of(MOD_ID, "skill_tree_rpgs");
    public static final Identifier WEAPON_CATEGORY_ID = Identifier.of(MOD_ID, "weapon_skills");
    public record Icon(IconType type, String value, String modelId) {
        public static Icon texture(String texture) {
            return new Icon(IconType.TEXTURE, texture, null);
        }
        public static Icon item(String item) {
            return new Icon(IconType.ITEM, item, null);
        }
        public static Icon itemWithModel(String item, String modelId) {
            return new Icon(IconType.ITEM, item, modelId);
        }
        public static Icon effect(String effect) {
            return new Icon(IconType.EFFECT, effect, null);
        }
        public static Icon spell(Identifier spellId) {
            return texture(spellId.getNamespace() + ":textures/spell/" + spellId.getPath() + ".png");
        }
    }
    public record EntityAttributeReward(RegistryEntry<EntityAttribute> attribute, String attributeId, EntityAttributeModifier modifier) {
        public static EntityAttributeReward of(RegistryEntry<EntityAttribute> attribute, double value, EntityAttributeModifier.Operation operation) {
            return new EntityAttributeReward(attribute, null, new EntityAttributeModifier(Identifier.of(MOD_ID + ":attribute_reward"), value, operation));
        }
        public static EntityAttributeReward ofId(String attributeId, double value, EntityAttributeModifier.Operation operation) {
            return new EntityAttributeReward(null, attributeId, new EntityAttributeModifier(Identifier.of(MOD_ID + ":attribute_reward"), value, operation));
        }
    }
    public record Entry(String id, String title, String description, Icon icon, List<SpellContainer> spellReward, EntityAttributeReward attributeReward,
                         ConditionalAttributeReward.DataStructure conditionalAttributeReward, List<String> required_mods) {
        public static Entry spell(String id, String title, String description, Icon icon, List<SpellContainer> spellReward) {
            return new Entry(id, title, description, icon, spellReward, null, null, null);
        }
        public static Entry attribute(String id, String title, String description, Icon icon,
                                      RegistryEntry<EntityAttribute> attribute, double value, EntityAttributeModifier.Operation operation) {
            return attribute(id, title, description, icon, EntityAttributeReward.of(attribute, value, operation));
        }
        public static Entry attribute(String id, String title, String description, Icon icon,
                                      String attributeId, double value, EntityAttributeModifier.Operation operation) {
            return attribute(id, title, description, icon, EntityAttributeReward.ofId(attributeId, value, operation));
        }
        public static Entry attribute(String id, String title, String description, Icon icon, EntityAttributeReward attributeReward) {
            return new Entry(id, title, description, icon, null, attributeReward, null, null);
        }
        public static Entry conditionalAttribute(String id, String title, String description, Icon icon) {
            return new Entry(id, title, description, icon, null, null, null, null);
        }
        public String titleTranslationKey() {
            return "skill." + MOD_ID + "." + id + ".title";
        }
        public String descriptionTranslationKey() {
            return "skill." + MOD_ID + "." + id + ".description";
        }
        public Entry withIcon(Icon icon) {
            return new Entry(id, title, description, icon, spellReward, attributeReward, conditionalAttributeReward, required_mods);
        }
        public Entry require(String modId) {
            return new Entry(id, title, description, icon, spellReward, attributeReward, conditionalAttributeReward, List.of(modId));
        }
        public Entry withConditionalAttribute(String attribute, String fallbackAttribute, double value, String equipmentTag, String translationKey) {
            var equipment = new ConditionalAttributeReward.DataStructure.ConditionData.EquipmentData("mainhand", equipmentTag);
            var condition = new ConditionalAttributeReward.DataStructure.ConditionData(translationKey, equipment);
            var reward = new ConditionalAttributeReward.DataStructure(attribute, fallbackAttribute, value, "multiply_base", condition);
            return new Entry(id, title, description, icon, spellReward, attributeReward, reward, required_mods);
        }
    }
    public static final ArrayList<Entry> ENTRIES = new ArrayList<>();
    private static Entry add(Entry entry) {
        ENTRIES.add(entry);
        return entry;
    }

    public static final ArrayList<Entry> WEAPON_ENTRIES = new ArrayList<>();
    private static Entry addWeapon(Entry entry) {
        WEAPON_ENTRIES.add(entry);
        return entry;
    }

    public static final String ELEMENTAL_WIZARDS = "elemental_wizards_rpg";
    public static final String ARCHERS_EXPANSION = "archers_expansion";
    public static final String BERSERKER = "berserker_rpg";
    public static final String FORCEMASTER = "forcemaster_rpg";
    public static final String WITCHER = "witcher_rpg";
    public static final String BARD = "bards_rpg";


    public static final float ROOT_MULTIPLIER = 0.01f;
    public static final float BOOST_MULTIPLIER = 0.01f;

    private static List<SpellContainer> dummyContainer() {
        return List.of(SpellContainers.forWeapon(SpellContainer.ContentType.MAGIC, List.of(Identifier.of("wizards:fireball"))));
    }

    private static Entry modifierSpell(MrpgSkillSpells.Entry entry) {
        var pattern = entry.spell().modifiers.getFirst().spell_pattern;
        var icon = pattern.startsWith("#") ? null : Icon.spell(Identifier.of(pattern));
        return Entry.spell(entry.id().getPath(),
                entry.title(),
                null,
                icon,
                List.of(SpellContainers.forModifier(entry.id()))
        );
    }

    private static Entry passiveSpell(MrpgSkillSpells.Entry entry) {
        return Entry.spell(entry.id().getPath(),
                entry.title(),
                null,
                Icon.spell(entry.id()),
                List.of(SpellContainers.forWeapon(SpellContainer.ContentType.MAGIC, List.of(entry.id())))
        );
    }

    ///AIR WIZARD
    public static final Entry AIR_ROOT = add(
            Entry.attribute("air_root",
                    "Path of Air",
                    null,
                    Icon.itemWithModel("spell_engine:spell_book", "elemental_wizards_rpg:item/spell_book/wind"),
                    MoreSpellSchools.AIR.attributeEntry,
                    0.01,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ).require(ELEMENTAL_WIZARDS)
    );
    public static final Entry AIR_BOOST = add(
            Entry.attribute("air_boost",
                    "Air Attunement",
                    null,
                    Icon.item("elemental_wizards_rpg:wand_wind"),
                    AIR_ROOT.attributeReward()).require(ELEMENTAL_WIZARDS)
    );
    public static final Entry AIR_TIER_2_SPELL_1_ROOT = add(modifierSpell(AirSkillSpells.air_tier_2_spell_1_root).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_2_SPELL_1_MODIFIER_1 = add(passiveSpell(AirSkillSpells.air_tier_2_spell_1_modifier_1)
            .withIcon(Icon.spell(Identifier.of("elemental_wizards_rpg", "wind_aeroblast")))
            .require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_2_SPELL_1_MODIFIER_2 = add(modifierSpell(AirSkillSpells.air_tier_2_spell_1_modifier_2).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_3_SPELL_1_ROOT = add(modifierSpell(AirSkillSpells.air_tier_3_spell_1_root).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_3_SPELL_1_MODIFIER_1 = add(modifierSpell(AirSkillSpells.air_tier_3_spell_1_modifier_1).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_3_SPELL_1_MODIFIER_2 = add(modifierSpell(AirSkillSpells.air_tier_3_spell_1_modifier_2).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_4_SPELL_1_ROOT = add(modifierSpell(AirSkillSpells.air_tier_4_spell_1_root).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_4_SPELL_1_MODIFIER_1 = add(modifierSpell(AirSkillSpells.air_tier_4_spell_1_modifier_1).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_4_SPELL_1_MODIFIER_2 = add(modifierSpell(AirSkillSpells.air_tier_4_spell_1_modifier_2).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_1_PASSIVE_1 = add(passiveSpell(AirSkillSpells.air_tier_1_passive_1).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_1_PASSIVE_2 = add(passiveSpell(AirSkillSpells.air_tier_1_passive_2).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_2_PASSIVE_1 = add(passiveSpell(AirSkillSpells.air_tier_2_passive_1).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_2_PASSIVE_2 = add(passiveSpell(AirSkillSpells.air_tier_2_passive_2).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_3_PASSIVE_1 = add(passiveSpell(AirSkillSpells.air_tier_3_passive_1).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_3_PASSIVE_2 = add(passiveSpell(AirSkillSpells.air_tier_3_passive_2).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_2_SPELL_2_ROOT = add(modifierSpell(AirSkillSpells.air_tier_2_spell_2_root).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_2_SPELL_2_MODIFIER_1 = add(modifierSpell(AirSkillSpells.air_tier_2_spell_2_modifier_1).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_2_SPELL_2_MODIFIER_2 = add(modifierSpell(AirSkillSpells.air_tier_2_spell_2_modifier_2).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_3_SPELL_2_ROOT = add(modifierSpell(AirSkillSpells.air_tier_3_spell_2_root).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_3_SPELL_2_MODIFIER_1 = add(modifierSpell(AirSkillSpells.air_tier_3_spell_2_modifier_1).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_3_SPELL_2_MODIFIER_2 = add(modifierSpell(AirSkillSpells.air_tier_3_spell_2_modifier_2).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_4_SPELL_2_ROOT = add(modifierSpell(AirSkillSpells.air_tier_4_spell_2_root).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_4_SPELL_2_MODIFIER_1 = add(modifierSpell(AirSkillSpells.air_tier_4_spell_2_modifier_1).require(ELEMENTAL_WIZARDS));
    public static final Entry AIR_TIER_4_SPELL_2_MODIFIER_2 = add(modifierSpell(AirSkillSpells.air_tier_4_spell_2_modifier_2).require(ELEMENTAL_WIZARDS));

    ///EARTH WIZARD
    public static final Entry EARTH_ROOT = add(
            Entry.attribute("earth_root",
                    "Path of Earth",
                    null,
                    Icon.itemWithModel("spell_engine:spell_book", "elemental_wizards_rpg:item/spell_book/terra"),
                    MoreSpellSchools.EARTH.attributeEntry,
                    0.01,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ).require(ELEMENTAL_WIZARDS)
    );
    public static final Entry EARTH_BOOST = add(
            Entry.attribute("earth_boost",
                    "Earth Attunement",
                    null,
                    Icon.item("elemental_wizards_rpg:wand_terra"),
                    EARTH_ROOT.attributeReward()).require(ELEMENTAL_WIZARDS)
    );
    public static final Entry EARTH_TIER_2_SPELL_1_ROOT = add(modifierSpell(EarthSkillSpells.earth_tier_2_spell_1_root).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_2_SPELL_1_MODIFIER_1 = add(modifierSpell(EarthSkillSpells.earth_tier_2_spell_1_modifier_1).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_2_SPELL_1_MODIFIER_2 = add(modifierSpell(EarthSkillSpells.earth_tier_2_spell_1_modifier_2).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_3_SPELL_1_ROOT = add(modifierSpell(EarthSkillSpells.earth_tier_3_spell_1_root).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_3_SPELL_1_MODIFIER_1 = add(modifierSpell(EarthSkillSpells.earth_tier_3_spell_1_modifier_1).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_3_SPELL_1_MODIFIER_2 = add(modifierSpell(EarthSkillSpells.earth_tier_3_spell_1_modifier_2).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_4_SPELL_1_ROOT = add(modifierSpell(EarthSkillSpells.earth_tier_4_spell_1_root).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_4_SPELL_1_MODIFIER_1 = add(modifierSpell(EarthSkillSpells.earth_tier_4_spell_1_modifier_1).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_4_SPELL_1_MODIFIER_2 = add(modifierSpell(EarthSkillSpells.earth_tier_4_spell_1_modifier_2).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_1_PASSIVE_1 = add(passiveSpell(EarthSkillSpells.earth_tier_1_passive_1).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_1_PASSIVE_2 = add(passiveSpell(EarthSkillSpells.earth_tier_1_passive_2).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_2_PASSIVE_1 = add(passiveSpell(EarthSkillSpells.earth_tier_2_passive_1).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_2_PASSIVE_2 = add(passiveSpell(EarthSkillSpells.earth_tier_2_passive_2).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_3_PASSIVE_1 = add(passiveSpell(EarthSkillSpells.earth_tier_3_passive_1).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_3_PASSIVE_2 = add(passiveSpell(EarthSkillSpells.earth_tier_3_passive_2).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_2_SPELL_2_ROOT = add(modifierSpell(EarthSkillSpells.earth_tier_2_spell_2_root).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_2_SPELL_2_MODIFIER_1 = add(modifierSpell(EarthSkillSpells.earth_tier_2_spell_2_modifier_1).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_2_SPELL_2_MODIFIER_2 = add(modifierSpell(EarthSkillSpells.earth_tier_2_spell_2_modifier_2).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_3_SPELL_2_ROOT = add(modifierSpell(EarthSkillSpells.earth_tier_3_spell_2_root).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_3_SPELL_2_MODIFIER_1 = add(modifierSpell(EarthSkillSpells.earth_tier_3_spell_2_modifier_1).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_3_SPELL_2_MODIFIER_2 = add(modifierSpell(EarthSkillSpells.earth_tier_3_spell_2_modifier_2).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_4_SPELL_2_ROOT = add(modifierSpell(EarthSkillSpells.earth_tier_4_spell_2_root).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_4_SPELL_2_MODIFIER_1 = add(modifierSpell(EarthSkillSpells.earth_tier_4_spell_2_modifier_1).require(ELEMENTAL_WIZARDS));
    public static final Entry EARTH_TIER_4_SPELL_2_MODIFIER_2 = add(modifierSpell(EarthSkillSpells.earth_tier_4_spell_2_modifier_2).require(ELEMENTAL_WIZARDS));
    ///WATER WIZARD
    public static final Entry WATER_ROOT = add(
            Entry.attribute("water_root",
                    "Path of Water",
                    null,
                    Icon.itemWithModel("spell_engine:spell_book", "elemental_wizards_rpg:item/spell_book/aqua"),
                    MoreSpellSchools.WATER.attributeEntry,
                    0.01,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ).require(ELEMENTAL_WIZARDS)
    );
    public static final Entry WATER_BOOST = add(
            Entry.attribute("water_boost",
                    "Water Attunement",
                    null,
                    Icon.item("elemental_wizards_rpg:wand_aqua"),
                    WATER_ROOT.attributeReward()).require(ELEMENTAL_WIZARDS)
    );
    public static final Entry WATER_TIER_2_SPELL_1_ROOT = add(modifierSpell(WaterSkillSpells.water_tier_2_spell_1_root).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_2_SPELL_1_MODIFIER_1 = add(modifierSpell(WaterSkillSpells.water_tier_2_spell_1_modifier_1).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_2_SPELL_1_MODIFIER_2 = add(passiveSpell(WaterSkillSpells.water_tier_2_spell_1_modifier_2)
            .withIcon(Icon.spell(Identifier.of("elemental_wizards_rpg", "aqua_bubble_beam"))).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_3_SPELL_1_ROOT = add(modifierSpell(WaterSkillSpells.water_tier_3_spell_1_root).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_3_SPELL_1_MODIFIER_1 = add(modifierSpell(WaterSkillSpells.water_tier_3_spell_1_modifier_1).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_3_SPELL_1_MODIFIER_2 = add(passiveSpell(WaterSkillSpells.water_tier_3_spell_1_modifier_2)
            .withIcon(Icon.spell(Identifier.of("elemental_wizards_rpg", "aqua_springwater"))).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_3_SPELL_2_ROOT = add(modifierSpell(WaterSkillSpells.water_tier_3_spell_2_root).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_3_SPELL_2_MODIFIER_1 = add(modifierSpell(WaterSkillSpells.water_tier_3_spell_2_modifier_1).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_3_SPELL_2_MODIFIER_2 = add(passiveSpell(WaterSkillSpells.water_tier_3_spell_2_modifier_2)
            .withIcon(Icon.spell(Identifier.of("elemental_wizards_rpg", "aqua_hydro_beam"))).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_1_PASSIVE_1 = add(passiveSpell(WaterSkillSpells.water_tier_1_passive_1).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_1_PASSIVE_2 = add(passiveSpell(WaterSkillSpells.water_tier_1_passive_2).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_2_PASSIVE_1 = add(passiveSpell(WaterSkillSpells.water_tier_2_passive_1).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_2_PASSIVE_2 = add(passiveSpell(WaterSkillSpells.water_tier_2_passive_2).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_3_PASSIVE_1 = add(passiveSpell(WaterSkillSpells.water_tier_3_passive_1).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_3_PASSIVE_2 = add(passiveSpell(WaterSkillSpells.water_tier_3_passive_2).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_2_SPELL_2_ROOT = add(modifierSpell(WaterSkillSpells.water_tier_2_spell_2_root).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_2_SPELL_2_MODIFIER_1 = add(modifierSpell(WaterSkillSpells.water_tier_2_spell_2_modifier_1).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_2_SPELL_2_MODIFIER_2 = add(modifierSpell(WaterSkillSpells.water_tier_2_spell_2_modifier_2).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_4_SPELL_1_ROOT = add(modifierSpell(WaterSkillSpells.water_tier_4_spell_1_root).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_4_SPELL_1_MODIFIER_1 = add(modifierSpell(WaterSkillSpells.water_tier_4_spell_1_modifier_1).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_4_SPELL_1_MODIFIER_2 = add(modifierSpell(WaterSkillSpells.water_tier_4_spell_1_modifier_2).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_4_SPELL_2_ROOT = add(modifierSpell(WaterSkillSpells.water_tier_4_spell_2_root).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_4_SPELL_2_MODIFIER_1 = add(modifierSpell(WaterSkillSpells.water_tier_4_spell_2_modifier_1).require(ELEMENTAL_WIZARDS));
    public static final Entry WATER_TIER_4_SPELL_2_MODIFIER_2 = add(modifierSpell(WaterSkillSpells.water_tier_4_spell_2_modifier_2).require(ELEMENTAL_WIZARDS));
    ///BERSERKER
    public static final Entry BERSERKER_ROOT = add(
            Entry.attribute("berserker_root",
                    "Path of the Berserker",
                    null,
                    Icon.itemWithModel("spell_engine:spell_book", "berserker_rpg:item/spell_book/berserker"),
                    MRPGCEntityAttributes.RAGE_MODIFIER,
                    0.01,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ).require(BERSERKER)
    );
    public static final Entry BERSERKER_BOOST = add(
            Entry.attribute("berserker_boost",
                    "Berserker Empowerment",
                    null,
                    Icon.item("berserker_rpg:iron_berserker_axe"),
                    BERSERKER_ROOT.attributeReward()).require(BERSERKER)
    );
    public static final Entry BERSERKER_TIER_2_SPELL_1_ROOT = add(modifierSpell(BerserkerSkillSpells.berserker_tier_2_spell_1_root).require(BERSERKER));
    public static final Entry BERSERKER_TIER_2_SPELL_1_MODIFIER_1 = add(passiveSpell(BerserkerSkillSpells.berserker_tier_2_spell_1_modifier_1)
            .withIcon(Icon.spell(Identifier.of("berserker_rpg", "wild_rage"))).require(BERSERKER));
    public static final Entry BERSERKER_TIER_2_SPELL_1_MODIFIER_2 = add(modifierSpell(BerserkerSkillSpells.berserker_tier_2_spell_1_modifier_2).require(BERSERKER));
    public static final Entry BERSERKER_TIER_2_SPELL_2_ROOT = add(modifierSpell(BerserkerSkillSpells.berserker_tier_2_spell_2_root).require(BERSERKER));
    public static final Entry BERSERKER_TIER_2_SPELL_2_MODIFIER_1 = add(modifierSpell(BerserkerSkillSpells.berserker_tier_2_spell_2_modifier_1).require(BERSERKER));
    public static final Entry BERSERKER_TIER_2_SPELL_2_MODIFIER_2 = add(modifierSpell(BerserkerSkillSpells.berserker_tier_2_spell_2_modifier_2).require(BERSERKER));
    public static final Entry BERSERKER_TIER_3_SPELL_1_ROOT = add(modifierSpell(BerserkerSkillSpells.berserker_tier_3_spell_1_root).require(BERSERKER));
    public static final Entry BERSERKER_TIER_3_SPELL_1_MODIFIER_1 = add(modifierSpell(BerserkerSkillSpells.berserker_tier_3_spell_1_modifier_1).require(BERSERKER));
    public static final Entry BERSERKER_TIER_3_SPELL_1_MODIFIER_2 = add(modifierSpell(BerserkerSkillSpells.berserker_tier_3_spell_1_modifier_2).require(BERSERKER));
    public static final Entry BERSERKER_TIER_3_SPELL_2_ROOT = add(modifierSpell(BerserkerSkillSpells.berserker_tier_3_spell_2_root).require(BERSERKER));
    public static final Entry BERSERKER_TIER_3_SPELL_2_MODIFIER_1 = add(modifierSpell(BerserkerSkillSpells.berserker_tier_3_spell_2_modifier_1).require(BERSERKER));
    public static final Entry BERSERKER_TIER_3_SPELL_2_MODIFIER_2 = add(passiveSpell(BerserkerSkillSpells.berserker_tier_3_spell_2_modifier_2)
            .withIcon(Icon.spell(Identifier.of("berserker_rpg", "outrage"))).require(BERSERKER));
    public static final Entry BERSERKER_TIER_4_SPELL_1_ROOT = add(modifierSpell(BerserkerSkillSpells.berserker_tier_4_spell_1_root).require(BERSERKER));
    public static final Entry BERSERKER_TIER_4_SPELL_1_MODIFIER_1 = add(passiveSpell(BerserkerSkillSpells.berserker_tier_4_spell_1_modifier_1)
            .withIcon(Icon.spell(Identifier.of("berserker_rpg", "blood_reckoning"))).require(BERSERKER));
    public static final Entry BERSERKER_TIER_4_SPELL_1_MODIFIER_2 = add(passiveSpell(BerserkerSkillSpells.berserker_tier_4_spell_1_modifier_2)
            .withIcon(Icon.spell(Identifier.of("berserker_rpg", "blood_reckoning"))).require(BERSERKER));
    public static final Entry BERSERKER_TIER_4_SPELL_2_ROOT = add(modifierSpell(BerserkerSkillSpells.berserker_tier_4_spell_2_root).require(BERSERKER));
    public static final Entry BERSERKER_TIER_4_SPELL_2_MODIFIER_1 = add(passiveSpell(BerserkerSkillSpells.berserker_tier_4_spell_2_modifier_1)
            .withIcon(Icon.spell(Identifier.of("berserker_rpg", "northerners_guillotine"))).require(BERSERKER));
    public static final Entry BERSERKER_TIER_4_SPELL_2_MODIFIER_2 = add(passiveSpell(BerserkerSkillSpells.berserker_tier_4_spell_2_modifier_2)
            .withIcon(Icon.spell(Identifier.of("berserker_rpg", "northerners_guillotine"))).require(BERSERKER));
    public static final Entry BERSERKER_TIER_1_PASSIVE_1 = add(passiveSpell(BerserkerSkillSpells.berserker_tier_1_passive_1).require(BERSERKER));
    public static final Entry BERSERKER_TIER_1_PASSIVE_2 = add(passiveSpell(BerserkerSkillSpells.berserker_tier_1_passive_2).require(BERSERKER));
    public static final Entry BERSERKER_TIER_2_PASSIVE_1 = add(passiveSpell(BerserkerSkillSpells.berserker_tier_2_passive_1).require(BERSERKER));
    public static final Entry BERSERKER_TIER_2_PASSIVE_2 = add(passiveSpell(BerserkerSkillSpells.berserker_tier_2_passive_2).require(BERSERKER));
    public static final Entry BERSERKER_TIER_3_PASSIVE_1 = add(passiveSpell(BerserkerSkillSpells.berserker_tier_3_passive_1).require(BERSERKER));
    public static final Entry BERSERKER_TIER_3_PASSIVE_2 = add(passiveSpell(BerserkerSkillSpells.berserker_tier_3_passive_2).require(BERSERKER));

    ///FORCEMASTER
    public static final Entry FORCEMASTER_ROOT = add(
            Entry.attribute("forcemaster_root",
                    "Path of the Forcemaster",
                    null,
                    Icon.itemWithModel("spell_engine:spell_book", "forcemaster_rpg:item/spell_book/forcemaster"),
                    MRPGCEntityAttributes.ARCANE_FUSE_MODIFIER,
                    0.01,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ).require(FORCEMASTER)
    );
    public static final Entry FORCEMASTER_BOOST = add(
            Entry.attribute("forcemaster_boost",
                    "Forcemaster Empowerment",
                    null,
                    Icon.item("forcemaster_rpg:iron_knuckle"),
                    FORCEMASTER_ROOT.attributeReward()).require(FORCEMASTER)
    );
    public static final Entry FORCEMASTER_TIER_2_SPELL_1_ROOT = add(modifierSpell(ForcemasterSkillSpells.forcemaster_tier_2_spell_1_root).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_2_SPELL_1_MODIFIER_1 = add(passiveSpell(ForcemasterSkillSpells.forcemaster_tier_2_spell_1_modifier_1)
            .withIcon(Icon.spell(Identifier.of("forcemaster_rpg", "stonehand"))).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_2_SPELL_1_MODIFIER_2 = add(passiveSpell(ForcemasterSkillSpells.forcemaster_tier_2_spell_1_modifier_2)
            .withIcon(Icon.spell(Identifier.of("forcemaster_rpg", "stonehand"))).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_3_SPELL_1_ROOT = add(modifierSpell(ForcemasterSkillSpells.forcemaster_tier_3_spell_1_root).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_3_SPELL_1_MODIFIER_1 = add(passiveSpell(ForcemasterSkillSpells.forcemaster_tier_3_spell_1_modifier_1)
            .withIcon(Icon.spell(Identifier.of("forcemaster_rpg", "belial_smashing"))).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_3_SPELL_1_MODIFIER_2 = add(modifierSpell(ForcemasterSkillSpells.forcemaster_tier_3_spell_1_modifier_2).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_4_SPELL_2_ROOT = add(modifierSpell(ForcemasterSkillSpells.forcemaster_tier_4_spell_2_root).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_4_SPELL_2_MODIFIER_1 = add(modifierSpell(ForcemasterSkillSpells.forcemaster_tier_4_spell_2_modifier_1).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_4_SPELL_2_MODIFIER_2 = add(passiveSpell(ForcemasterSkillSpells.forcemaster_tier_4_spell_2_modifier_2)
            .withIcon(Icon.spell(Identifier.of("forcemaster_rpg", "asal"))).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_3_SPELL_2_ROOT = add(modifierSpell(ForcemasterSkillSpells.forcemaster_tier_3_spell_2_root).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_3_SPELL_2_MODIFIER_1 = add(modifierSpell(ForcemasterSkillSpells.forcemaster_tier_3_spell_2_modifier_1).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_3_SPELL_2_MODIFIER_2 = add(passiveSpell(ForcemasterSkillSpells.forcemaster_tier_3_spell_2_modifier_2)
            .withIcon(Icon.spell(Identifier.of("forcemaster_rpg", "nen_sphere"))).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_1_PASSIVE_1 = add(passiveSpell(ForcemasterSkillSpells.forcemaster_tier_1_passive_1).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_1_PASSIVE_2 = add(passiveSpell(ForcemasterSkillSpells.forcemaster_tier_1_passive_2).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_2_PASSIVE_1 = add(passiveSpell(ForcemasterSkillSpells.forcemaster_tier_2_passive_1).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_2_PASSIVE_2 = add(passiveSpell(ForcemasterSkillSpells.forcemaster_tier_2_passive_2).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_3_PASSIVE_1 = add(passiveSpell(ForcemasterSkillSpells.forcemaster_tier_3_passive_1).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_3_PASSIVE_2 = add(passiveSpell(ForcemasterSkillSpells.forcemaster_tier_3_passive_2).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_2_SPELL_2_ROOT = add(modifierSpell(ForcemasterSkillSpells.forcemaster_tier_2_spell_2_root).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_2_SPELL_2_MODIFIER_1 = add(modifierSpell(ForcemasterSkillSpells.forcemaster_tier_2_spell_2_modifier_1).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_2_SPELL_2_MODIFIER_2 = add(modifierSpell(ForcemasterSkillSpells.forcemaster_tier_2_spell_2_modifier_2).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_4_SPELL_1_ROOT = add(modifierSpell(ForcemasterSkillSpells.forcemaster_tier_4_spell_1_root).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_4_SPELL_1_MODIFIER_1 = add(modifierSpell(ForcemasterSkillSpells.forcemaster_tier_4_spell_1_modifier_1).require(FORCEMASTER));
    public static final Entry FORCEMASTER_TIER_4_SPELL_1_MODIFIER_2 = add(modifierSpell(ForcemasterSkillSpells.forcemaster_tier_4_spell_1_modifier_2).require(FORCEMASTER));
    ///WAR ARCHER
    public static final Entry WAR_ARCHER_ROOT = add(
            Entry.attribute("war_archer_root",
                    "Path of the War Archer",
                    null,
                    Icon.itemWithModel("spell_engine:spell_book", "archers_expansion:item/spell_book/war_archer"),
                    EntityAttributes_RangedWeapon.DAMAGE.entry,
                    0.01,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ).require(ARCHERS_EXPANSION)
    );
    public static final Entry WAR_ARCHER_BOOST = add(
            Entry.attribute("war_archer_boost",
                    "War Archer Empowerment",
                    null,
                    Icon.item("archers:heavy_crossbow"),
                    WAR_ARCHER_ROOT.attributeReward()).require(ARCHERS_EXPANSION)
    );
    public static final Entry WAR_ARCHER_TIER_2_SPELL_1_ROOT = add(modifierSpell(WarArcherSkillSpells.war_archer_tier_2_spell_1_root).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_2_SPELL_1_MODIFIER_1 = add(modifierSpell(WarArcherSkillSpells.war_archer_tier_2_spell_1_modifier_1).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_2_SPELL_1_MODIFIER_2 = add(modifierSpell(WarArcherSkillSpells.war_archer_tier_2_spell_1_modifier_2).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_2_SPELL_2_ROOT = add(modifierSpell(WarArcherSkillSpells.war_archer_tier_2_spell_2_root).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_2_SPELL_2_MODIFIER_1 = add(passiveSpell(WarArcherSkillSpells.war_archer_tier_2_spell_2_modifier_1)
            .withIcon(Icon.spell(Identifier.of("archers_expansion", "dual_shot"))).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_2_SPELL_2_MODIFIER_2 = add(modifierSpell(WarArcherSkillSpells.war_archer_tier_2_spell_2_modifier_2).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_3_SPELL_1_ROOT = add(modifierSpell(WarArcherSkillSpells.war_archer_tier_3_spell_1_root).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_3_SPELL_1_MODIFIER_1 = add(modifierSpell(WarArcherSkillSpells.war_archer_tier_3_spell_1_modifier_1)
            .withIcon(Icon.spell(Identifier.of("archers_expansion", "explosive_barrel"))).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_3_SPELL_1_MODIFIER_2 = add(modifierSpell(WarArcherSkillSpells.war_archer_tier_3_spell_1_modifier_2).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_3_SPELL_2_ROOT = add(modifierSpell(WarArcherSkillSpells.war_archer_tier_3_spell_2_root).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_3_SPELL_2_MODIFIER_1 = add(modifierSpell(WarArcherSkillSpells.war_archer_tier_3_spell_2_modifier_1).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_3_SPELL_2_MODIFIER_2 = add(passiveSpell(WarArcherSkillSpells.war_archer_tier_3_spell_2_modifier_2)
            .withIcon(Icon.spell(Identifier.of("archers_expansion", "point_blank_shot"))).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_4_SPELL_1_ROOT = add(modifierSpell(WarArcherSkillSpells.war_archer_tier_4_spell_1_root).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_4_SPELL_1_MODIFIER_1 = add(modifierSpell(WarArcherSkillSpells.war_archer_tier_4_spell_1_modifier_1).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_4_SPELL_1_MODIFIER_2 = add(passiveSpell(WarArcherSkillSpells.war_archer_tier_4_spell_1_modifier_2)
            .withIcon(Icon.spell(Identifier.of("archers_expansion", "scorched_earth"))).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_4_SPELL_2_ROOT = add(modifierSpell(WarArcherSkillSpells.war_archer_tier_4_spell_2_root).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_4_SPELL_2_MODIFIER_1 = add(modifierSpell(WarArcherSkillSpells.war_archer_tier_4_spell_2_modifier_1).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_4_SPELL_2_MODIFIER_2 = add(modifierSpell(WarArcherSkillSpells.war_archer_tier_4_spell_2_modifier_2).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_1_PASSIVE_1 = add(passiveSpell(WarArcherSkillSpells.war_archer_tier_1_passive_1).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_1_PASSIVE_2 = add(passiveSpell(WarArcherSkillSpells.war_archer_tier_1_passive_2).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_2_PASSIVE_1 = add(passiveSpell(WarArcherSkillSpells.war_archer_tier_2_passive_1).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_2_PASSIVE_2 = add(passiveSpell(WarArcherSkillSpells.war_archer_tier_2_passive_2).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_3_PASSIVE_1 = add(passiveSpell(WarArcherSkillSpells.war_archer_tier_3_passive_1).require(ARCHERS_EXPANSION));
    public static final Entry WAR_ARCHER_TIER_3_PASSIVE_2 = add(passiveSpell(WarArcherSkillSpells.war_archer_tier_3_passive_2).require(ARCHERS_EXPANSION));
    ///DEADEYE
    public static final Entry DEADEYE_ROOT = add(
            Entry.attribute("deadeye_root",
                    "Path of the Deadeye",
                    null,
                    Icon.itemWithModel("spell_engine:spell_book", "archers_expansion:item/spell_book/deadeye"),
                    EntityAttributes_RangedWeapon.HASTE.entry,
                    0.01,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ).require(ARCHERS_EXPANSION)
    );
    public static final Entry DEADEYE_BOOST = add(
            Entry.attribute("deadeye_boost",
                    "Deadeye Empowerment",
                    null,
                    Icon.item("archers:mechanic_shortbow"),
                    DEADEYE_ROOT.attributeReward()).require(ARCHERS_EXPANSION)
    );
    public static final Entry DEADEYE_TIER_2_SPELL_1_ROOT = add(modifierSpell(DeadeyeSkillSpells.deadeye_tier_2_spell_1_root).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_2_SPELL_1_MODIFIER_1 = add(modifierSpell(DeadeyeSkillSpells.deadeye_tier_2_spell_1_modifier_1).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_2_SPELL_1_MODIFIER_2 = add(modifierSpell(DeadeyeSkillSpells.deadeye_tier_2_spell_1_modifier_2).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_2_SPELL_2_ROOT = add(modifierSpell(DeadeyeSkillSpells.deadeye_tier_2_spell_2_root).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_2_SPELL_2_MODIFIER_1 = add(modifierSpell(DeadeyeSkillSpells.deadeye_tier_2_spell_2_modifier_1).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_2_SPELL_2_MODIFIER_2 = add(modifierSpell(DeadeyeSkillSpells.deadeye_tier_2_spell_2_modifier_2).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_3_SPELL_1_ROOT = add(modifierSpell(DeadeyeSkillSpells.deadeye_tier_3_spell_1_root).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_3_SPELL_1_MODIFIER_1 = add(modifierSpell(DeadeyeSkillSpells.deadeye_tier_3_spell_1_modifier_1).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_3_SPELL_1_MODIFIER_2 = add(modifierSpell(DeadeyeSkillSpells.deadeye_tier_3_spell_1_modifier_2).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_3_SPELL_2_ROOT = add(modifierSpell(DeadeyeSkillSpells.deadeye_tier_3_spell_2_root).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_3_SPELL_2_MODIFIER_1 = add(modifierSpell(DeadeyeSkillSpells.deadeye_tier_3_spell_2_modifier_1).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_3_SPELL_2_MODIFIER_2 = add(passiveSpell(DeadeyeSkillSpells.deadeye_tier_3_spell_2_modifier_2)
            .withIcon(Icon.spell(Identifier.of("archers_expansion", "disabling_shot"))).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_4_SPELL_1_ROOT = add(modifierSpell(DeadeyeSkillSpells.deadeye_tier_4_spell_1_root).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_4_SPELL_1_MODIFIER_1 = add(passiveSpell(DeadeyeSkillSpells.deadeye_tier_4_spell_1_modifier_1)
            .withIcon(Icon.spell(Identifier.of("archers_expansion", "choking_gas"))).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_4_SPELL_1_MODIFIER_2 = add(modifierSpell(DeadeyeSkillSpells.deadeye_tier_4_spell_1_modifier_2).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_4_SPELL_2_ROOT = add(modifierSpell(DeadeyeSkillSpells.deadeye_tier_4_spell_2_root).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_4_SPELL_2_MODIFIER_1 = add(modifierSpell(DeadeyeSkillSpells.deadeye_tier_4_spell_2_modifier_1).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_4_SPELL_2_MODIFIER_2 = add(passiveSpell(DeadeyeSkillSpells.deadeye_tier_4_spell_2_modifier_2)
            .withIcon(Icon.spell(Identifier.of("archers_expansion", "alter_ego"))).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_1_PASSIVE_1 = add(passiveSpell(DeadeyeSkillSpells.deadeye_tier_1_passive_1).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_1_PASSIVE_2 = add(passiveSpell(DeadeyeSkillSpells.deadeye_tier_1_passive_2).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_2_PASSIVE_1 = add(passiveSpell(DeadeyeSkillSpells.deadeye_tier_2_passive_1).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_2_PASSIVE_2 = add(passiveSpell(DeadeyeSkillSpells.deadeye_tier_2_passive_2).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_3_PASSIVE_1 = add(passiveSpell(DeadeyeSkillSpells.deadeye_tier_3_passive_1).require(ARCHERS_EXPANSION));
    public static final Entry DEADEYE_TIER_3_PASSIVE_2 = add(passiveSpell(DeadeyeSkillSpells.deadeye_tier_3_passive_2).require(ARCHERS_EXPANSION));
    ///TUNDRA HUNTER
    public static final Entry TUNDRA_HUNTER_ROOT = add(
            Entry.attribute("tundra_hunter_root",
                    "Path of the Tundra Hunter",
                    null,
                    Icon.itemWithModel("spell_engine:spell_book", "archers_expansion:item/spell_book/tundra_hunter"),
                    SpellSchools.FROST.attributeEntry,
                    0.2,
                    EntityAttributeModifier.Operation.ADD_VALUE
            ).require(ARCHERS_EXPANSION)
    );
    public static final Entry TUNDRA_HUNTER_BOOST = add(
            Entry.attribute("tundra_hunter_boost",
                    "Tundra Hunter Empowerment",
                    null,
                    Icon.item("archers:rapid_crossbow"),
                    TUNDRA_HUNTER_ROOT.attributeReward()).require(ARCHERS_EXPANSION)
    );
    public static final Entry TUNDRA_HUNTER_TIER_2_SPELL_1_ROOT = add(modifierSpell(TundraHunterSkillSpells.tundra_hunter_tier_2_spell_1_root).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_2_SPELL_1_MODIFIER_1 = add(passiveSpell(TundraHunterSkillSpells.tundra_hunter_tier_2_spell_1_modifier_1)
            .withIcon(Icon.spell(Identifier.of("archers_expansion", "frozen_pact"))).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_2_SPELL_1_MODIFIER_2 = add(modifierSpell(TundraHunterSkillSpells.tundra_hunter_tier_2_spell_1_modifier_2).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_2_SPELL_2_ROOT = add(modifierSpell(TundraHunterSkillSpells.tundra_hunter_tier_2_spell_2_root).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_2_SPELL_2_MODIFIER_1 = add(modifierSpell(TundraHunterSkillSpells.tundra_hunter_tier_2_spell_2_modifier_1).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_2_SPELL_2_MODIFIER_2 = add(modifierSpell(TundraHunterSkillSpells.tundra_hunter_tier_2_spell_2_modifier_2).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_3_SPELL_1_ROOT = add(modifierSpell(TundraHunterSkillSpells.tundra_hunter_tier_3_spell_1_root).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_3_SPELL_1_MODIFIER_1 = add(modifierSpell(TundraHunterSkillSpells.tundra_hunter_tier_3_spell_1_modifier_1).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_3_SPELL_1_MODIFIER_2 = add(modifierSpell(TundraHunterSkillSpells.tundra_hunter_tier_3_spell_1_modifier_2).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_3_SPELL_2_ROOT = add(modifierSpell(TundraHunterSkillSpells.tundra_hunter_tier_3_spell_2_root).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_3_SPELL_2_MODIFIER_1 = add(modifierSpell(TundraHunterSkillSpells.tundra_hunter_tier_3_spell_2_modifier_1).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_3_SPELL_2_MODIFIER_2 = add(modifierSpell(TundraHunterSkillSpells.tundra_hunter_tier_3_spell_2_modifier_2).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_4_SPELL_1_ROOT = add(modifierSpell(TundraHunterSkillSpells.tundra_hunter_tier_4_spell_1_root).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_4_SPELL_1_MODIFIER_1 = add(modifierSpell(TundraHunterSkillSpells.tundra_hunter_tier_4_spell_1_modifier_1).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_4_SPELL_1_MODIFIER_2 = add(modifierSpell(TundraHunterSkillSpells.tundra_hunter_tier_4_spell_1_modifier_2).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_4_SPELL_2_ROOT = add(modifierSpell(TundraHunterSkillSpells.tundra_hunter_tier_4_spell_2_root).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_4_SPELL_2_MODIFIER_1 = add(modifierSpell(TundraHunterSkillSpells.tundra_hunter_tier_4_spell_2_modifier_1).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_4_SPELL_2_MODIFIER_2 = add(modifierSpell(TundraHunterSkillSpells.tundra_hunter_tier_4_spell_2_modifier_2).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_1_PASSIVE_1 = add(passiveSpell(TundraHunterSkillSpells.tundra_hunter_tier_1_passive_1).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_1_PASSIVE_2 = add(passiveSpell(TundraHunterSkillSpells.tundra_hunter_tier_1_passive_2).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_2_PASSIVE_1 = add(passiveSpell(TundraHunterSkillSpells.tundra_hunter_tier_2_passive_1).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_2_PASSIVE_2 = add(passiveSpell(TundraHunterSkillSpells.tundra_hunter_tier_2_passive_2).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_3_PASSIVE_1 = add(passiveSpell(TundraHunterSkillSpells.tundra_hunter_tier_3_passive_1).require(ARCHERS_EXPANSION));
    public static final Entry TUNDRA_HUNTER_TIER_3_PASSIVE_2 = add(passiveSpell(TundraHunterSkillSpells.tundra_hunter_tier_3_passive_2).require(ARCHERS_EXPANSION));

    ///BARD
    public static final Entry BARD_ROOT = add(
            Entry.attribute("bard_root",
                    "Path of the Bard",
                    null,
                    Icon.itemWithModel("spell_engine:spell_book", "bards_rpg:item/spell_book/bard"),
                    EntityAttributes.GENERIC_MOVEMENT_SPEED,
                    0.01,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ).require(BARD)
    );
    public static final Entry BARD_BOOST = add(
            Entry.attribute("bard_boost",
                    "Bard Empowerment",
                    null,
                    Icon.item("bards_rpg:iron_rapier"),
                    BARD_ROOT.attributeReward()).require(BARD)
    );
    public static final Entry BARD_TIER_2_SPELL_1_ROOT = add(modifierSpell(BardSkillSpells.bard_tier_2_spell_1_root).require(BARD));
    public static final Entry BARD_TIER_2_SPELL_1_MODIFIER_1 = add(modifierSpell(BardSkillSpells.bard_tier_2_spell_1_modifier_1).require(BARD));
    public static final Entry BARD_TIER_2_SPELL_1_MODIFIER_2 = add(modifierSpell(BardSkillSpells.bard_tier_2_spell_1_modifier_2).require(BARD));
    public static final Entry BARD_TIER_2_SPELL_2_ROOT = add(modifierSpell(BardSkillSpells.bard_tier_2_spell_2_root).require(BARD));
    public static final Entry BARD_TIER_2_SPELL_2_MODIFIER_1 = add(modifierSpell(BardSkillSpells.bard_tier_2_spell_2_modifier_1).require(BARD));
    public static final Entry BARD_TIER_2_SPELL_2_MODIFIER_2 = add(modifierSpell(BardSkillSpells.bard_tier_2_spell_2_modifier_2).require(BARD));
    public static final Entry BARD_TIER_3_SPELL_1_ROOT = add(modifierSpell(BardSkillSpells.bard_tier_3_spell_1_root).require(BARD));
    public static final Entry BARD_TIER_3_SPELL_1_MODIFIER_1 = add(passiveSpell(BardSkillSpells.bard_tier_3_spell_1_modifier_1)
            .withIcon(Icon.spell(Identifier.of("bards_rpg", "encore"))).require(BARD));
    public static final Entry BARD_TIER_3_SPELL_1_MODIFIER_2 = add(passiveSpell(BardSkillSpells.bard_tier_3_spell_1_modifier_2)
            .withIcon(Icon.spell(Identifier.of("bards_rpg", "encore"))).require(BARD));
    public static final Entry BARD_TIER_3_SPELL_2_ROOT = add(modifierSpell(BardSkillSpells.bard_tier_3_spell_2_root).require(BARD));
    public static final Entry BARD_TIER_3_SPELL_2_MODIFIER_1 = add(modifierSpell(BardSkillSpells.bard_tier_3_spell_2_modifier_1).require(BARD));
    public static final Entry BARD_TIER_3_SPELL_2_MODIFIER_2 = add(modifierSpell(BardSkillSpells.bard_tier_3_spell_2_modifier_2).require(BARD));
    public static final Entry BARD_TIER_4_SPELL_1_ROOT = add(modifierSpell(BardSkillSpells.bard_tier_4_spell_1_root).require(BARD));
    public static final Entry BARD_TIER_4_SPELL_1_MODIFIER_1 = add(modifierSpell(BardSkillSpells.bard_tier_4_spell_1_modifier_1).require(BARD));
    public static final Entry BARD_TIER_4_SPELL_1_MODIFIER_2 = add(passiveSpell(BardSkillSpells.bard_tier_4_spell_1_modifier_2)
            .withIcon(Icon.spell(Identifier.of("bards_rpg", "armys_paeon"))).require(BARD));
    public static final Entry BARD_TIER_4_SPELL_2_ROOT = add(modifierSpell(BardSkillSpells.bard_tier_4_spell_2_root).require(BARD));
    public static final Entry BARD_TIER_4_SPELL_2_MODIFIER_1 = add(passiveSpell(BardSkillSpells.bard_tier_4_spell_2_modifier_1)
            .withIcon(Icon.spell(Identifier.of("bards_rpg", "crescendo"))).require(BARD));
    public static final Entry BARD_TIER_4_SPELL_2_MODIFIER_2 = add(modifierSpell(BardSkillSpells.bard_tier_4_spell_2_modifier_2).require(BARD));
    public static final Entry BARD_TIER_1_PASSIVE_1 = add(passiveSpell(BardSkillSpells.bard_tier_1_passive_1).require(BARD));
    public static final Entry BARD_TIER_1_PASSIVE_2 = add(passiveSpell(BardSkillSpells.bard_tier_1_passive_2).require(BARD));
    public static final Entry BARD_TIER_2_PASSIVE_1 = add(passiveSpell(BardSkillSpells.bard_tier_2_passive_1).require(BARD));
    public static final Entry BARD_TIER_2_PASSIVE_2 = add(passiveSpell(BardSkillSpells.bard_tier_2_passive_2).require(BARD));
    public static final Entry BARD_TIER_3_PASSIVE_1 = add(passiveSpell(BardSkillSpells.bard_tier_3_passive_1).require(BARD));
    public static final Entry BARD_TIER_3_PASSIVE_2 = add(passiveSpell(BardSkillSpells.bard_tier_3_passive_2).require(BARD));

    public static final Entry WEAPON_AQUA_STAFF_ROOT = addWeapon(modifierSpell(MrpgWeaponSkills.weapon_aqua_staff_root)
            .withIcon(Icon.item("elemental_wizards_rpg:staff_aqua")).require(ELEMENTAL_WIZARDS));
    public static final Entry WEAPON_AQUA_STAFF_MODIFIER_1 = addWeapon(modifierSpell(MrpgWeaponSkills.weapon_aqua_staff_modifier_1).require(ELEMENTAL_WIZARDS));
    public static final Entry WEAPON_AQUA_STAFF_MODIFIER_2 = addWeapon(modifierSpell(MrpgWeaponSkills.weapon_aqua_staff_modifier_2).require(ELEMENTAL_WIZARDS));
    public static final Entry WEAPON_WIND_STAFF_ROOT = addWeapon(modifierSpell(MrpgWeaponSkills.weapon_wind_staff_root)
            .withIcon(Icon.item("elemental_wizards_rpg:staff_wind")).require(ELEMENTAL_WIZARDS));
    public static final Entry WEAPON_WIND_STAFF_MODIFIER_1 = addWeapon(modifierSpell(MrpgWeaponSkills.weapon_wind_staff_modifier_1).require(ELEMENTAL_WIZARDS));
    public static final Entry WEAPON_WIND_STAFF_MODIFIER_2 = addWeapon(modifierSpell(MrpgWeaponSkills.weapon_wind_staff_modifier_2).require(ELEMENTAL_WIZARDS));
    public static final Entry WEAPON_TERRA_STAFF_ROOT = addWeapon(modifierSpell(MrpgWeaponSkills.weapon_terra_staff_root)
            .withIcon(Icon.item("elemental_wizards_rpg:staff_terra")).require(ELEMENTAL_WIZARDS));
    public static final Entry WEAPON_TERRA_STAFF_MODIFIER_1 = addWeapon(modifierSpell(MrpgWeaponSkills.weapon_terra_staff_modifier_1).require(ELEMENTAL_WIZARDS));
    public static final Entry WEAPON_TERRA_STAFF_MODIFIER_2 = addWeapon(modifierSpell(MrpgWeaponSkills.weapon_terra_staff_modifier_2).require(ELEMENTAL_WIZARDS));
    public static final Entry WEAPON_KNUCKLE_ROOT = addWeapon(
            Entry.conditionalAttribute("weapon_knuckle_root", "Knuckle Specialisation", null,
                    Icon.item("forcemaster_rpg:iron_knuckle"))
            .withConditionalAttribute("minecraft:generic.attack_damage", null, 0.05,
                    "forcemaster_rpg:knuckles", "modifier_condition.mrpgc_skill_tree.knuckles").require(FORCEMASTER));
    public static final Entry WEAPON_KNUCKLE_MODIFIER_1 = addWeapon(modifierSpell(MrpgWeaponSkills.weapon_knuckle_modifier_1).require(FORCEMASTER));
    public static final Entry WEAPON_KNUCKLE_MODIFIER_2 = addWeapon(passiveSpell(MrpgWeaponSkills.weapon_knuckle_modifier_2)
            .withIcon(Icon.spell(Identifier.of("forcemaster_rpg", "burstcrack"))).require(FORCEMASTER));

    public static final Entry WEAPON_BERSERKER_AXE_ROOT = addWeapon(
            Entry.conditionalAttribute("weapon_berserker_axe_root", "Berserker Axe Specialisation", null,
                    Icon.item("berserker_rpg:iron_berserker_axe"))
            .withConditionalAttribute("minecraft:generic.attack_damage", null, 0.05,
                    "berserker_rpg:berserker_axes", "modifier_condition.mrpgc_skill_tree.berserker_axe").require(BERSERKER));
    public static final Entry WEAPON_BERSERKER_AXE_MODIFIER_1 = addWeapon(passiveSpell(MrpgWeaponSkills.weapon_berserker_axe_modifier_1)
            .withIcon(Icon.spell(Identifier.of("more_rpg_classes", "decapitate"))).require(BERSERKER));
    public static final Entry WEAPON_BERSERKER_AXE_MODIFIER_2 = addWeapon(modifierSpell(MrpgWeaponSkills.weapon_berserker_axe_modifier_2).require(BERSERKER));

    public static final Entry WEAPON_RAPIER_ROOT = addWeapon(
            Entry.conditionalAttribute("weapon_rapier_root", "Rapier Specialisation", null,
                    Icon.item("bards_rpg:iron_rapier"))
            .withConditionalAttribute("minecraft:generic.attack_damage", null, 0.05,
                    "bards_rpg:rapiers", "modifier_condition.mrpgc_skill_tree.rapier").require(BARD));
    public static final Entry WEAPON_RAPIER_MODIFIER_1 = addWeapon(passiveSpell(MrpgWeaponSkills.weapon_rapier_modifier_1)
            .withIcon(Icon.spell(Identifier.of("more_rpg_classes", "puncture"))).require(BARD));
    public static final Entry WEAPON_RAPIER_MODIFIER_2 = addWeapon(modifierSpell(MrpgWeaponSkills.weapon_rapier_modifier_2).require(BARD));

    public static final Entry WEAPON_LUTE_ROOT = addWeapon(modifierSpell(MrpgWeaponSkills.weapon_lute_root)
            .withIcon(Icon.item("bards_rpg:wooden_lute")).require(BARD));
    public static final Entry WEAPON_LUTE_MODIFIER_1 = addWeapon(passiveSpell(MrpgWeaponSkills.weapon_lute_modifier_1)
            .withIcon(Icon.item("bards_rpg:wooden_lute")).require(BARD));
    public static final Entry WEAPON_LUTE_MODIFIER_2 = addWeapon(modifierSpell(MrpgWeaponSkills.weapon_lute_modifier_2)
            .withIcon(Icon.item("bards_rpg:wooden_lute")).require(BARD));

    public static final Entry WEAPON_LYRE_ROOT = addWeapon(modifierSpell(MrpgWeaponSkills.weapon_lyre_root)
            .withIcon(Icon.item("bards_rpg:golden_lyre")).require(BARD));
    public static final Entry WEAPON_LYRE_MODIFIER_1 = addWeapon(modifierSpell(MrpgWeaponSkills.weapon_lyre_modifier_1)
            .withIcon(Icon.item("bards_rpg:golden_lyre")).require(BARD));
    public static final Entry WEAPON_LYRE_MODIFIER_2 = addWeapon(modifierSpell(MrpgWeaponSkills.weapon_lyre_modifier_2)
            .withIcon(Icon.item("bards_rpg:golden_lyre")).require(BARD));

    public static final Entry WEAPON_HARP_CROSSBOW_ROOT = addWeapon(
            Entry.conditionalAttribute("weapon_harp_crossbow_root", "Harp Crossbow Specialisation", null,
                    Icon.item("bards_rpg:harp_crossbow"))
            .withConditionalAttribute("ranged_weapon:damage", "minecraft:generic.attack_damage", 0.05,
                    "bards_rpg:harp_crossbows", "modifier_condition.mrpgc_skill_tree.harp_crossbow").require(BARD));
    public static final Entry WEAPON_HARP_CROSSBOW_MODIFIER_1 = addWeapon(passiveSpell(MrpgWeaponSkills.weapon_harp_crossbow_modifier_1)
            .withIcon(Icon.item("bards_rpg:harp_crossbow")).require(BARD));
    public static final Entry WEAPON_HARP_CROSSBOW_MODIFIER_2 = addWeapon(passiveSpell(MrpgWeaponSkills.weapon_harp_crossbow_modifier_2)
            .withIcon(Icon.item("bards_rpg:harp_crossbow")).require(BARD));

    public static final Entry WEAPON_WITCHER_SWORDS_ROOT = addWeapon(
            Entry.conditionalAttribute("weapon_witcher_swords_root", "Witcher Sword Specialisation", null,
                    Icon.item("witcher_rpg:iron_witcher_sword"))
            .withConditionalAttribute("minecraft:generic.attack_damage", null, 0.05,
                    "witcher_rpg:witcher_swords", "modifier_condition.mrpgc_skill_tree.witcher_swords").require(WITCHER));
    public static final Entry WEAPON_WITCHER_SWORDS_MODIFIER_1 = addWeapon(
            Entry.spell("weapon_witcher_swords_modifier_1", "Counterattack", null,
                    Icon.spell(Identifier.of("witcher_rpg", "defensive_witcher_mechanics")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "defensive_witcher_mechanics")))
            ).require(WITCHER));
    public static final Entry WEAPON_WITCHER_SWORDS_MODIFIER_2 = addWeapon(
            Entry.spell("weapon_witcher_swords_modifier_2", "Arrow Deflection", null,
                    Icon.spell(Identifier.of("witcher_rpg", "defensive_witcher_mechanics")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "defensive_witcher_mechanics")))
            ).require(WITCHER));

    ///WITCHER CLASS ANCHOR
    public static final Entry SIGN_ROOT = add(
            Entry.attribute("sign_root", "Path of the Witcher", null,
                    Icon.itemWithModel("spell_engine:spell_book", "witcher_rpg:item/spell_book/signs"),
                    "witcher_rpg:sign_intensity", 0.01, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ).require(WITCHER)
    );
    public static final Entry SIGN_BOOST = add(
            Entry.attribute("sign_boost", "Sign Empowerment", null,
                    Icon.item("witcher_rpg:silver_witcher_sword"),
                    SIGN_ROOT.attributeReward()).require(WITCHER)
    );

    public static final Entry FENCING_ROOT = add(
            Entry.attribute("fencing_root", "Path of the Witcher", null,
                    Icon.itemWithModel("spell_engine:spell_book", "witcher_rpg:item/spell_book/fencing"),
                    EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.01, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ).require(WITCHER)
    );
    public static final Entry FENCING_BOOST = add(
            Entry.attribute("fencing_boost", "Fencing Empowerment", null,
                    Icon.item("witcher_rpg:steel_witcher_sword"),
                    FENCING_ROOT.attributeReward()).require(WITCHER)
    );
    ///WITCHER SIGNS
    public static final Entry SIGN_TIER_SPELL_2_ROOT_1 = add(modifierSpell(WitcherSkillSpells.sign_tier_spell_2_root_1).require(WITCHER));
    public static final Entry SIGN_TIER_SPELL_2_MODIFIER_1 = add(
            Entry.spell("sign_tier_spell_2_modifier_1", "Molten Armor", null,
                    Icon.spell(Identifier.of("witcher_rpg", "igni")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/igni_melt_armor")))
            ).require(WITCHER));
    public static final Entry SIGN_TIER_SPELL_2_MODIFIER_2 = add(
            Entry.spell("sign_tier_spell_2_modifier_2", "Combustion", null,
                    Icon.spell(Identifier.of("witcher_rpg", "igni")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/igni_combustion")))
            ).require(WITCHER));

    public static final Entry SIGN_TIER_SPELL_2_ROOT_2 = add(modifierSpell(WitcherSkillSpells.sign_tier_spell_2_root_2).require(WITCHER));
    public static final Entry SIGN_TIER_SPELL_2_MODIFIER_3 = add(
            Entry.spell("sign_tier_spell_2_modifier_3", "Far-Reaching Aard", null,
                    Icon.spell(Identifier.of("witcher_rpg", "aard")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/aard_far_reach")))
            ).require(WITCHER));
    public static final Entry SIGN_TIER_SPELL_2_MODIFIER_4 = add(
            Entry.spell("sign_tier_spell_2_modifier_4", "Shockwave", null,
                    Icon.spell(Identifier.of("witcher_rpg", "aard")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/aard_shockwave")))
            ).require(WITCHER));

    public static final Entry SIGN_TIER_SPELL_3_ROOT_1 = add(modifierSpell(WitcherSkillSpells.sign_tier_spell_3_root_1).require(WITCHER));
    public static final Entry SIGN_TIER_SPELL_3_MODIFIER_1 = add(
            Entry.spell("sign_tier_spell_3_modifier_1", "Explosive Shield", null,
                    Icon.spell(Identifier.of("witcher_rpg", "quen")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/quen_exploding_shield")))
            ).require(WITCHER));
    public static final Entry SIGN_TIER_SPELL_3_MODIFIER_2 = add(
            Entry.spell("sign_tier_spell_3_modifier_2", "Discharge", null,
                    Icon.spell(Identifier.of("witcher_rpg", "quen")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/quen_discharge")))
            ).require(WITCHER));

    public static final Entry SIGN_TIER_SPELL_3_ROOT_2 = add(modifierSpell(WitcherSkillSpells.sign_tier_spell_3_root_2).require(WITCHER));
    public static final Entry SIGN_TIER_SPELL_3_MODIFIER_3 = add(
            Entry.spell("sign_tier_spell_3_modifier_3", "Link", null,
                    Icon.spell(Identifier.of("witcher_rpg", "axii")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/axii_link")))
            ).require(WITCHER));
    public static final Entry SIGN_TIER_SPELL_3_MODIFIER_4 = add(
            Entry.spell("sign_tier_spell_3_modifier_4", "Lethargy", null,
                    Icon.spell(Identifier.of("witcher_rpg", "axii")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/axii_lethargy")))
            ).require(WITCHER));
    public static final Entry SIGN_TIER_SPELL_4_ROOT_1 = add(modifierSpell(WitcherSkillSpells.sign_tier_spell_4_root_1).require(WITCHER));
    public static final Entry SIGN_TIER_SPELL_4_MODIFIER_1 = add(
            Entry.spell("sign_tier_spell_4_modifier_1", "Pyromaniac", null,
                    Icon.spell(Identifier.of("witcher_rpg", "igni_firestream")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/igni_pyromaniac")))
            ).require(WITCHER));
    public static final Entry SIGN_TIER_SPELL_4_MODIFIER_2 = add(
            Entry.spell("sign_tier_spell_4_modifier_2", "Firestreams", null,
                    Icon.spell(Identifier.of("witcher_rpg", "igni_firestream")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/igni_firestreams")))
            ).require(WITCHER));
    public static final Entry SIGN_TIER_SPELL_4_ROOT_2 = add(modifierSpell(WitcherSkillSpells.sign_tier_spell_4_root_2).require(WITCHER));
    public static final Entry SIGN_TIER_SPELL_4_MODIFIER_3 = add(
            Entry.spell("sign_tier_spell_4_modifier_3", "Frostbite", null,
                    Icon.spell(Identifier.of("witcher_rpg", "aard_sweep")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/aard_frostbite")))
            ).require(WITCHER));
    public static final Entry SIGN_TIER_SPELL_4_MODIFIER_4 = add(
            Entry.spell("sign_tier_spell_4_modifier_4", "Aard Whirlwind", null,
                    Icon.spell(Identifier.of("witcher_rpg", "aard_sweep")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/aard_whirlwind")))
            ).require(WITCHER));

    ///WITCHER SIGN PASSIVES
    public static final Entry SIGN_TIER_1_PASSIVE_1  = add(
            Entry.spell("sign_tier_1_passive_1", "Binding Glyphs", null,
                    Icon.spell(Identifier.of("witcher_rpg", "spell_modifiers/yrden_binding_glyphs")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/yrden_binding_glyphs")))
            ).require(WITCHER));
    public static final Entry SIGN_TIER_1_PASSIVE_2 = add(
            Entry.spell("sign_tier_1_passive_2", "Super Charged Glyphs", null,
                    Icon.spell(Identifier.of("witcher_rpg", "spell_modifiers/yrden_supercharged_glyphs")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/yrden_supercharged_glyphs")))
            ).require(WITCHER));
    public static final Entry SIGN_TIER_2_PASSIVE_1 = add(
            Entry.spell("sign_tier_2_passive_1", "Yrden Roll", null,
                    Icon.spell(Identifier.of("witcher_rpg", "passives/yrden_roll")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "passives/yrden_roll")))
            ).require(WITCHER));
    public static final Entry SIGN_TIER_2_PASSIVE_2 = add(
            Entry.spell("sign_tier_2_passive_2", "Igni Roll", null,
                    Icon.spell(Identifier.of("witcher_rpg", "passives/igni_roll")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "passives/igni_roll")))
            ).require(WITCHER));
    public static final Entry SIGN_TIER_3_PASSIVE_1 = add(
            Entry.spell("sign_tier_3_passive_1", "Griffin School Techniques", null,
                    Icon.item("witcher_rpg:griffin_school_medallion"),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "passives/griffin_school_technique")))
            ).require(WITCHER));
    public static final Entry SIGN_TIER_3_PASSIVE_2 = add(
            Entry.spell("sign_tier_3_passive_2", "Wolf School Techniques", null,
                    Icon.item("witcher_rpg:wolf_school_medallion"),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "passives/wolf_school_technique")))
            ).require(WITCHER));

    ///WITCHER FENCING
    public static final Entry FENCING_TIER_SPELL_2_ROOT_1 = add(modifierSpell(WitcherSkillSpells.fencing_tier_spell_2_root_1).require(WITCHER));
    public static final Entry FENCING_TIER_SPELL_2_MODIFIER_1 = add(
            Entry.spell("fencing_tier_spell_2_modifier_1", "Precise Blows", null,
                    Icon.spell(Identifier.of("witcher_rpg", "fast_attack")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/fast_precise_blows")))
            ).require(WITCHER));
    public static final Entry FENCING_TIER_SPELL_2_MODIFIER_2 = add(
            Entry.spell("fencing_tier_spell_2_modifier_2", "Muscle Memory", null,
                    Icon.spell(Identifier.of("witcher_rpg", "fast_attack")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/muscle_memory")))
            ).require(WITCHER));

    public static final Entry FENCING_TIER_SPELL_2_ROOT_2 = add(modifierSpell(WitcherSkillSpells.fencing_tier_spell_2_root_2).require(WITCHER));
    public static final Entry FENCING_TIER_SPELL_2_MODIFIER_3 = add(
            Entry.spell("fencing_tier_spell_2_modifier_3", "Crushing Blows", null,
                    Icon.spell(Identifier.of("witcher_rpg", "strong_attack")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/strong_crushing_blows")))
            ).require(WITCHER));
    public static final Entry FENCING_TIER_SPELL_2_MODIFIER_4 = add(
            Entry.spell("fencing_tier_spell_2_modifier_4", "Strength Training", null,
                    Icon.spell(Identifier.of("witcher_rpg", "strong_attack")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/strength_training")))
            ).require(WITCHER));

    public static final Entry FENCING_TIER_SPELL_3_ROOT_1 = add(modifierSpell(WitcherSkillSpells.fencing_tier_spell_3_root_1).require(WITCHER));
    public static final Entry FENCING_TIER_SPELL_3_MODIFIER_1 = add(
            Entry.spell("fencing_tier_spell_3_modifier_1", "Monster Expert", null,
                    Icon.spell(Identifier.of("witcher_rpg", "witcher_senses")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/witcher_senses_monster_expert")))
            ).require(WITCHER));
    public static final Entry FENCING_TIER_SPELL_3_MODIFIER_2 = add(
            Entry.spell("fencing_tier_spell_3_modifier_2", "Enemy Knowledge", null,
                    Icon.spell(Identifier.of("witcher_rpg", "witcher_senses")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/witcher_senses_enemy_knowledge")))
            ).require(WITCHER));

    public static final Entry FENCING_TIER_SPELL_3_ROOT_2 = add(modifierSpell(WitcherSkillSpells.fencing_tier_spell_3_root_2).require(WITCHER));
    public static final Entry FENCING_TIER_SPELL_3_MODIFIER_3 = add(
            Entry.spell("fencing_tier_spell_3_modifier_3", "Resolve", null,
                    Icon.spell(Identifier.of("witcher_rpg", "battle_trance")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/battle_trance_resolve")))
            ).require(WITCHER));
    public static final Entry FENCING_TIER_SPELL_3_MODIFIER_4 = add(
            Entry.spell("fencing_tier_spell_3_modifier_4", "Undying", null,
                    Icon.spell(Identifier.of("witcher_rpg", "battle_trance")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/battle_trance_undying")))
            ).require(WITCHER));

    public static final Entry FENCING_TIER_SPELL_4_ROOT_1 = add(modifierSpell(WitcherSkillSpells.fencing_tier_spell_4_root_1).require(WITCHER));
    public static final Entry FENCING_TIER_SPELL_4_MODIFIER_1 = add(
            Entry.spell("fencing_tier_spell_4_modifier_1", "Quick Hands", null,
                    Icon.spell(Identifier.of("witcher_rpg", "whirl")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/whirl_quick_hands")))
            ).require(WITCHER));
    public static final Entry FENCING_TIER_SPELL_4_MODIFIER_2 = add(
            Entry.spell("fencing_tier_spell_4_modifier_2", "Precise Slasher", null,
                    Icon.spell(Identifier.of("witcher_rpg", "whirl")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/whirl_precise_slasher")))
            ).require(WITCHER));

    public static final Entry FENCING_TIER_SPELL_4_ROOT_2 = add(modifierSpell(WitcherSkillSpells.fencing_tier_spell_4_root_2).require(WITCHER));
    public static final Entry FENCING_TIER_SPELL_4_MODIFIER_3 = add(
            Entry.spell("fencing_tier_spell_4_modifier_3", "Heavy Swing", null,
                    Icon.spell(Identifier.of("witcher_rpg", "rend")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/rend_heavy_swing")))
            ).require(WITCHER));
    public static final Entry FENCING_TIER_SPELL_4_MODIFIER_4 = add(
            Entry.spell("fencing_tier_spell_4_modifier_4", "Devastating Slash", null,
                    Icon.spell(Identifier.of("witcher_rpg", "rend")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "spell_modifiers/rend_devastating_slash")))
            ).require(WITCHER));

    ///WITCHER FENCING PASSIVES
    public static final Entry FENCING_TIER_1_PASSIVE_1 = add(
            Entry.spell("fencing_tier_1_passive_1", "Crippling Strikes", null,
                    Icon.spell(Identifier.of("witcher_rpg", "passives/strong_crippling_strikes")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "passives/strong_crippling_strikes")))
            ).require(WITCHER));
    public static final Entry FENCING_TIER_1_PASSIVE_2 = add(
            Entry.spell("fencing_tier_1_passive_2", "Sunder Armor", null,
                    Icon.spell(Identifier.of("witcher_rpg", "passives/strong_sunder_armor")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "passives/strong_sunder_armor")))
            ).require(WITCHER));
    public static final Entry FENCING_TIER_2_PASSIVE_1 = add(
            Entry.spell("fencing_tier_2_passive_1", "Footwork", null,
                    Icon.spell(Identifier.of("witcher_rpg", "passives/footwork")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "passives/footwork")))
            ).require(WITCHER));
    public static final Entry FENCING_TIER_2_PASSIVE_2 = add(
            Entry.spell("fencing_tier_2_passive_2", "Flood of Anger", null,
                    Icon.spell(Identifier.of("witcher_rpg", "passives/flood_of_anger")),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "passives/flood_of_anger")))
            ).require(WITCHER));
    public static final Entry FENCING_TIER_3_PASSIVE_1 = add(
            Entry.spell("fencing_tier_3_passive_1", "Cat School Techniques", null,
                    Icon.item("witcher_rpg:cat_school_medallion"),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "passives/cat_school_technique")))
            ).require(WITCHER));
    public static final Entry FENCING_TIER_3_PASSIVE_2 = add(
            Entry.spell("fencing_tier_3_passive_2", "Bear School Techniques", null,
                    Icon.item("witcher_rpg:bear_school_medallion"),
                    List.of(SpellContainers.forModifier(Identifier.of("witcher_rpg", "passives/bear_school_technique")))
            ).require(WITCHER));

}
