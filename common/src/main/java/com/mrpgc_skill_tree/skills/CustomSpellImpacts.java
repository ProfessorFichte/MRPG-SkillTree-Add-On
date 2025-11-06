package com.mrpgc_skill_tree.skills;

import com.mrpgc_skill_tree.skills.custom_impacts.RecklessRageImpact;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.event.SpellHandlers;

import static com.mrpgc_skill_tree.MRPGCSkillTreeAddOn.MOD_ID;

public class CustomSpellImpacts {
    public static void registerCustomImpacts(){
        SpellHandlers.registerCustomImpact(
                Identifier.of(MOD_ID, "reckless_rage"),
                new RecklessRageImpact()
        );
    }
}
