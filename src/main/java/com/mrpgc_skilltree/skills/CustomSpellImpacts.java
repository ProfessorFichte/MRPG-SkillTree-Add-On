package com.mrpgc_skilltree.skills;

import com.mrpgc_skilltree.skills.custom_impacts.RecklessRageImpact;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.event.SpellHandlers;

import static com.mrpgc_skilltree.MRPGCSkillTreeAddOn.MOD_ID;

public class CustomSpellImpacts {
    public static void registerCustomImpacts(){
        SpellHandlers.registerCustomImpact(
                Identifier.of(MOD_ID, "reckless_rage"),
                new RecklessRageImpact()
        );
    }
}
