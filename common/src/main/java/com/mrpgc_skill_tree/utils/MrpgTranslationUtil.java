package com.mrpgc_skill_tree.utils;

import com.mrpgc_skill_tree.skills.MrpgSkillDefinitions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.spell_engine.client.gui.SpellTooltip;
import net.spell_engine.mixin.client.ItemStackTooltipAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class MrpgTranslationUtil {
    public static final Map<String, Supplier<List<Text>>> resolvers = new HashMap<>();

    public static List<Text> resolve(String skillId) {
        var supplier = resolvers.get(skillId);
        if (supplier == null) {
            return List.of();
        }
        return supplier.get();
    }

    public static List<Text> resolveSpellDetails(Identifier spellId) {
        var player = MinecraftClient.getInstance().player;
        if (player == null) {
            return List.of();
        }
        return SpellTooltip.spellDescriptionWithDetails(spellId, player, ItemStack.EMPTY, 0);
    }

    public static List<Text> resolveAttributeModifierTooltip(MrpgSkillDefinitions.EntityAttributeReward attributeReward) {
        var player = MinecraftClient.getInstance().player;
        if (player == null) {
            return List.of();
        }
        RegistryEntry<EntityAttribute> attribute = attributeReward.attribute();
        if (attribute == null && attributeReward.attributeId() != null) {
            attribute = Registries.ATTRIBUTE.getEntry(Identifier.of(attributeReward.attributeId())).orElse(null);
        }
        if (attribute == null) {
            return List.of();
        }
        var tooltipUtil = (ItemStackTooltipAccessor) (Object) ItemStack.EMPTY;
        var bonusLines = new ArrayList<Text>();
        tooltipUtil
                .spellEngine_appendAttributeModifierTooltip(
                        bonusLines::add,
                        player,
                        attribute,
                        attributeReward.modifier()
                );
        return bonusLines;
    }
}
