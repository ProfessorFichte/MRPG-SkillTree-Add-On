package com.mrpgc_skilltree.skills.custom_impacts;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.event.SpellHandlers;
import net.spell_engine.internals.SpellHelper;
import net.spell_power.api.SpellPower;

public class RecklessRageImpact implements SpellHandlers.CustomImpact {
    @Override
    public SpellHandlers.ImpactResult onSpellImpact(
            RegistryEntry<Spell> spell,
            SpellPower.Result powerResult,
            LivingEntity caster,
            Entity target,
            SpellHelper.ImpactContext context
    ) {
        if(caster instanceof PlayerEntity playerEntity){
            var max_health = playerEntity.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH);
            float actual_health_player = playerEntity.getHealth();
            float self_damage_calc = (float) (max_health * 0.05F);
            if(actual_health_player <= 0.5F){
                return new SpellHandlers.ImpactResult(false, false);
            }else{
                if(self_damage_calc > actual_health_player){
                    playerEntity.setHealth(0.5F);
                }else{
                    playerEntity.setHealth(actual_health_player- self_damage_calc);
                }
            }
        }
        return new SpellHandlers.ImpactResult(true, false);
    }
}
