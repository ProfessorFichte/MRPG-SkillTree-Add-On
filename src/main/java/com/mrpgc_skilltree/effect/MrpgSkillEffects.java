package com.mrpgc_skilltree.effect;

import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.spell_engine.api.config.AttributeModifier;
import net.spell_engine.api.config.ConfigFile;
import net.spell_engine.api.config.EffectConfig;
import net.spell_engine.api.effect.CustomStatusEffect;
import net.spell_engine.api.effect.Effects;
import net.spell_engine.api.effect.Protection;
import net.spell_engine.api.effect.Synchronized;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.List;

import static com.mrpgc_skilltree.MRPGCSkillTreeAddOn.MOD_ID;

public class MrpgSkillEffects {
    public static final List<Effects.Entry> entries = new ArrayList<>();
    private static Effects.Entry add(Effects.Entry entry) {
        entries.add(entry);
        return entry;
    }

    public static Effects.Entry EARTH_BENDER = add(new Effects.Entry(Identifier.of(MOD_ID, "earth_bender"),
            "Earth Bender",
            "Increases earth spell power.",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, MoreSpellSchools.EARTH.color),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    MoreSpellSchools.EARTH.id,
                                    0.1F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry OBSIDIAN_SKIN = add(new Effects.Entry(Identifier.of(MOD_ID, "obsidian skin"),
            "Obsidian Skin",
            "Protects you from the incoming attack",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, MoreSpellSchools.EARTH.color),
            new EffectConfig(
                    List.of(
                    )
            )
    ));
    public static Effects.Entry DRIPSTONE_OBSTACLES = add(new Effects.Entry(Identifier.of(MOD_ID, "dripstone_obstacles"),
            "Dripstone Obstacles",
            "Decreased movement speed.",
            new CustomStatusEffect(StatusEffectCategory.HARMFUL, MoreSpellSchools.EARTH.color),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_MOVEMENT_SPEED.getIdAsString(),
                                    -0.1F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry CONCUSSION = add(new Effects.Entry(Identifier.of(MOD_ID, "concussion"),
            "Dripstone Obstacles",
            "Decreased movement speed.",
            new CustomStatusEffect(StatusEffectCategory.HARMFUL, MoreSpellSchools.EARTH.color),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString(),
                                    -0.3F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            ),
                            new AttributeModifier(
                                    EntityAttributes_RangedWeapon.DAMAGE.id,
                                    -0.3F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            ),
                            new AttributeModifier(
                                    SpellSchools.GENERIC.id,
                                    -0.3F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )

                    )
            )
    ));
    public static Effects.Entry PERSISTENT_BUBBLES = add(new Effects.Entry(Identifier.of(MOD_ID, "persistent_bubbles"),
            "Persistent Bubbles",
            "Decreased movement & attack speed.",
            new CustomStatusEffect(StatusEffectCategory.HARMFUL, MoreSpellSchools.WATER.color),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_MOVEMENT_SPEED.getIdAsString(),
                                    -0.2F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            ),
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_ATTACK_SPEED.getIdAsString(),
                                    -0.2F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry HYDRO_BOOST = add(new Effects.Entry(Identifier.of(MOD_ID, "hydro_boost"),
            "Hydro Boost",
            "Increased movement speed.",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, MoreSpellSchools.WATER.color),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_MOVEMENT_SPEED.getIdAsString(),
                                    0.35F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));

    public static void register(ConfigFile.Effects config) {
        for (var entry : entries) {
            Synchronized.configure(entry.effect, true);
        }
        Effects.register(entries, config.effects);

        Protection.register(OBSIDIAN_SKIN.entry, new Protection.Pop(
                null,
                null));
    }
}
