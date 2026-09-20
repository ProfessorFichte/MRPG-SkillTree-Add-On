package com.mrpgc_skill_tree.attributes;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;

public class MrpgIds {
    public static String attribute(EntityAttribute attribute) {
        return Registries.ATTRIBUTE.getId(attribute).toString();
    }

    public static String effect(StatusEffect effect) {
        return Registries.STATUS_EFFECT.getId(effect).toString();
    }
}
