package com.mrpgc_skill_tree.attributes;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.skill_tree_rpgs.attributes.ModifierCondition;

import java.util.LinkedHashMap;
import java.util.Locale;

import static com.mrpgc_skill_tree.MRPGCSkillTreeAddOn.MOD_ID;

public class MrpgModifierConditions {

    public static final LinkedHashMap<ModifierCondition, String> TRANSLATIONS = new LinkedHashMap<>();

    private static ModifierCondition mainhand(TagKey<Item> tag, String translationKeyCore, String displayText) {
        return create(tag, EquipmentSlot.MAINHAND, translationKeyCore, displayText);
    }

    private static ModifierCondition create(TagKey<Item> tag, EquipmentSlot slot, String translationKeyCore, String displayText) {
        String translationKey = "modifier_condition." + MOD_ID + "." + translationKeyCore.toLowerCase(Locale.ROOT);
        var condition = new ModifierCondition(new ModifierCondition.Equipment(slot, tag), translationKey);
        TRANSLATIONS.put(condition, displayText);
        return condition;
    }

    public static final ModifierCondition BERSERKER_AXE = mainhand(
            TagKey.of(RegistryKeys.ITEM, Identifier.of("berserker_rpg", "berserker_axes")),
            "berserker_axe", "While holding a Berserker Axe:");
}
