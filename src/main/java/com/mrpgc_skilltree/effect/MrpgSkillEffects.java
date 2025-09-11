package com.mrpgc_skilltree.effect;

import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.skill_tree_rpgs.skills.SkillTreeSounds;
import net.spell_engine.api.config.AttributeModifier;
import net.spell_engine.api.config.ConfigFile;
import net.spell_engine.api.config.EffectConfig;
import net.spell_engine.api.effect.*;
import net.spell_engine.api.entity.SpellEngineAttributes;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellPowerMechanics;
import net.spell_power.api.SpellSchools;
import net.spell_power.api.statuseffects.SpellVulnerabilityStatusEffect;

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
    public static Effects.Entry OBSIDIAN_SKIN = add(new Effects.Entry(Identifier.of(MOD_ID, "obsidian_skin"),
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
    public static Effects.Entry BLIND_WITH_RAGE = add(new Effects.Entry(Identifier.of(MOD_ID, "blind_with_rage"),
            "Blind with Rage",
            "Reduces Damage Taken",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x9999ff),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    SpellEngineAttributes.DAMAGE_TAKEN.id.toString(),
                                    -0.15F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry BLOODFLOW = add(new Effects.Entry(Identifier.of(MOD_ID, "bloodflow"),
            "Bloodflow",
            "Increased Attack Damage",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x9999ff),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString(),
                                    0.1F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry DEADLY_PRECISION = add(new Effects.Entry(Identifier.of(MOD_ID, "deadly_precision"),
            "Deadly Precision",
            "Deal additional damage according to the targets max health.",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x9999ff),
            new EffectConfig(
                    List.of(
                    )
            )
    ));
    public static Effects.Entry RECKLESS_RAGE = add(new Effects.Entry(Identifier.of(MOD_ID, "reckless_rage"),
            "Reckless Rage",
            "Absorbs damage.",
            new AbsorptionEffect(StatusEffectCategory.BENEFICIAL, 0x9999ff),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_MAX_ABSORPTION.getIdAsString(),
                                    2,
                                    EntityAttributeModifier.Operation.ADD_VALUE
                            )
                    )
            )
    ));
    public static Effects.Entry CRYSTALLIZED_FISTS = add(new Effects.Entry(Identifier.of(MOD_ID, "crystallized_fists"),
            "Crystallized Fists",
            "Increases arcane spell power.",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, SpellSchools.ARCANE.color),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    SpellSchools.ARCANE.id,
                                    0.1F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry PUMPED_UP = add(new Effects.Entry(Identifier.of(MOD_ID, "pumped_up"),
            "Pumped Up",
            "Increased Attack Damage",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x9999ff),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString(),
                                    0.1F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry LEAPING_SWIFTNESS = add(new Effects.Entry(Identifier.of(MOD_ID, "leaping_swiftness"),
            "Leaping Swiftness",
            "Increased Movement Speed",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x9999ff),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_MOVEMENT_SPEED.getIdAsString(),
                                    0.15F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry HUNTING_INSTINCTS = add(new Effects.Entry(Identifier.of(MOD_ID, "hunting_instincts"),
            "Hunting Instincts",
            "Increased Frost Spell Power and Ranged Damage",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x9999ff),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    SpellSchools.FROST.id,
                                    0.1F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            ),
                            new AttributeModifier(
                                    EntityAttributes_RangedWeapon.DAMAGE.id,
                                    0.1F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )

                    )
            )
    ));
    public static Effects.Entry WINTERS_CLOAK = add(new Effects.Entry(Identifier.of(MOD_ID, "winters_cloak"),
            "Winters Cloak",
            "Absorbs damage.",
            new AbsorptionEffect(StatusEffectCategory.BENEFICIAL, 0x9999ff),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_MAX_ABSORPTION.getIdAsString(),
                                    2,
                                    EntityAttributeModifier.Operation.ADD_VALUE
                            )
                    )
            )
    ));
    public static Effects.Entry HUNTING_FEVER = add(new Effects.Entry(Identifier.of(MOD_ID, "hunting_fever"),
            "Hunting Fever",
            "Increased Frost Spell Power and Ranged Damage",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x9999ff),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_MOVEMENT_SPEED.getIdAsString(),
                                    0.2F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            ),
                            new AttributeModifier(
                                    EntityAttributes_RangedWeapon.HASTE.id.toString(),
                                    0.2F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry SMOKE_BOMB = add(new Effects.Entry(Identifier.of(MOD_ID, "smoke_bomb"),
            "Smoke Bomb",
            "Blindness and reduced Movement speed",
            new CustomStatusEffect(StatusEffectCategory.HARMFUL, 0x9999ff),
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
    public static Effects.Entry CAMOUFLAGED = add(new Effects.Entry(Identifier.of(MOD_ID, "camouflaged"),
            "Camouflaged",
            "Increased Evasion",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x9999ff),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    SpellEngineAttributes.EVASION_CHANCE.id.toString(),
                                    0.25F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry TOWER_PROTECTOR = add(new Effects.Entry(Identifier.of(MOD_ID, "tower_protector"),
            "Protector of the Tower",
            "Increased Armor and Knockback Resistance",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x9999ff),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_ARMOR.getIdAsString(),
                                    0.5F,
                                    EntityAttributeModifier.Operation.ADD_VALUE
                            ),
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE.getIdAsString(),
                                    0.2F,
                                    EntityAttributeModifier.Operation.ADD_VALUE
                            )
                    )
            )
    ));
    public static Effects.Entry LAST_STAND = add(new Effects.Entry(Identifier.of(MOD_ID, "last_stand"),
            "Last Stand",
            "Increases size",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x9999ff),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes_RangedWeapon.HASTE.id.toString(),
                                    0.25F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            ),
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_SCALE.getIdAsString(),
                                    0.15F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            ),
                            new AttributeModifier(
                                    SpellEngineAttributes.DAMAGE_TAKEN.id.toString(),
                                    -0.25F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry FORCE_RELEASE = add(new Effects.Entry(Identifier.of(MOD_ID, "force_release"),
            "Force Release",
            "Increased Spell Haste & Spell Crit Chance",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x9999ff),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    SpellPowerMechanics.HASTE.id.toString(),
                                    0.02F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            ),
                            new AttributeModifier(
                                    SpellPowerMechanics.CRITICAL_CHANCE.id.toString(),
                                    0.02F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry FLYING_FISTS = add(new Effects.Entry(Identifier.of(MOD_ID, "flying_fists"),
            "Flying Fists",
            "Increased Attack Speed",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x9999ff),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_ATTACK_SPEED.getIdAsString(),
                                    0.1F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry SURYS_TENACITY = add(new Effects.Entry(Identifier.of(MOD_ID, "surys_tenacity"),
            "Sury's Tenacity",
            "Increased Attack and Movement Speed, immune to harmful effects, but can't cast spells.",
            new ImmuneToHarmfulEffectsStatusEffect(StatusEffectCategory.BENEFICIAL, 0x9999ff),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_ATTACK_SPEED.getIdAsString(),
                                    0.1F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            ),
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_MOVEMENT_SPEED.getIdAsString(),
                                    0.1F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry SURYS_GRACE= add(new Effects.Entry(Identifier.of(MOD_ID, "surys_grace"),
            "Sury's Grace",
            "Increased Arcane Spell Power, Spell Haste & Spell Critical Damage",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x9999ff),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    SpellSchools.ARCANE.id.toString(),
                                    0.2F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            ),
                            new AttributeModifier(
                                    SpellPowerMechanics.HASTE.id.toString(),
                                    0.2F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry SPINNING_SLASH = add(new Effects.Entry(Identifier.of(MOD_ID, "spinning_slash"),
            "Spinning Slash",
            "Damaging nearby enemies.",
            new TickingStatusEffect(StatusEffectCategory.BENEFICIAL, 0x99ccff).interval(3),
            new EffectConfig(
                    List.of()
            )
    ));
    public static Effects.Entry BURST_OF_AGGRESSION= add(new Effects.Entry(Identifier.of(MOD_ID, "burst_of_aggression"),
            "Burst of Aggression",
            "Increased Movement Speed",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x9999ff),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_MOVEMENT_SPEED.getIdAsString(),
                                    0.15F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry RAGNAROK = add(new Effects.Entry(Identifier.of(MOD_ID, "ragnarok"),
            "Ragnarok",
            "Increased Movement Speed and immune to harmful effects.",
            new ImmuneToHarmfulEffectsStatusEffect(StatusEffectCategory.BENEFICIAL, 0x9999ff),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_MOVEMENT_SPEED.getIdAsString(),
                                    0.1F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry UNDYING_RAGE = add(new Effects.Entry(Identifier.of(MOD_ID, "undying_rage"),
            "Undying Rage",
            "Reduces damage taken.",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x9999ff),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    SpellEngineAttributes.DAMAGE_TAKEN.id,
                                    -1F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry HYDRATION = add(new Effects.Entry(Identifier.of(MOD_ID, "hydration"),
            "Hydration",
            "Regenerates health overtime.",
            new RegenerationStatusEffect(StatusEffectCategory.BENEFICIAL, MoreSpellSchools.WATER.color),
            new EffectConfig(
                    List.of(
                    )
            )
    ));
    public static Effects.Entry SPLASHDOWN = add(new Effects.Entry(Identifier.of(MOD_ID, "splashdown"),
            "Splashdown",
            "Knocks targets back.",
            new TickingStatusEffect(StatusEffectCategory.BENEFICIAL, MoreSpellSchools.WATER.color).interval(3),
            new EffectConfig(
                    List.of()
            )
    ));
    public static Effects.Entry CALMING_FLOW = add(new Effects.Entry(Identifier.of(MOD_ID, "calming_flow"),
            "Calming Flow",
            "Decreases Active Cooldowns of Water Spells",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, MoreSpellSchools.WATER.color),
            new EffectConfig(
                    List.of(
                    )
            )
    ));
    public static Effects.Entry TORRENT = add(new Effects.Entry(Identifier.of(MOD_ID, "torrent"),
            "Torrent",
            "Increases Water Spell Power and Spell Critical Chance",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, MoreSpellSchools.WATER.color),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    MoreSpellSchools.WATER.id,
                                    0.2F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            ),
                            new AttributeModifier(
                                    SpellPowerMechanics.CRITICAL_CHANCE.id,
                                    0.1F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )

                    )
            )
    ));
    public static Effects.Entry EARTHEN_BLESSING = add(new Effects.Entry(Identifier.of(MOD_ID, "earthen_blessing"),
            "Earthen Blessing",
            "Increases Armor.",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, MoreSpellSchools.EARTH.color),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_ARMOR.getIdAsString(),
                                    0.5F,
                                    EntityAttributeModifier.Operation.ADD_VALUE
                            )
                    )
            )
    ));
    public static Effects.Entry DIFFICULT_TERRAIN = add(new Effects.Entry(Identifier.of(MOD_ID, "difficult_terrain"),
            "Difficult Terrain",
            "Reduces Movement Speed",
            new CustomStatusEffect(StatusEffectCategory.HARMFUL, MoreSpellSchools.EARTH.color),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_MOVEMENT_SPEED.getIdAsString(),
                                    -0.3F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry SEISMIC_ENTRY = add(new Effects.Entry(Identifier.of(MOD_ID, "seismic_entry"),
            "Seismic Entry",
            "Knocks targets back.",
            new TickingStatusEffect(StatusEffectCategory.BENEFICIAL, MoreSpellSchools.EARTH.color).interval(3),
            new EffectConfig(
                    List.of()
            )
    ));
    public static Effects.Entry STONE_HEART = add(new Effects.Entry(Identifier.of(MOD_ID, "stone_heart"),
            "Stone Heart",
            "Absorbs damage.",
            new AbsorptionEffect(StatusEffectCategory.BENEFICIAL, MoreSpellSchools.EARTH.color),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_MAX_ABSORPTION.getIdAsString(),
                                    2,
                                    EntityAttributeModifier.Operation.ADD_VALUE
                            )
                    )
            )
    ));
    public static Effects.Entry IMPETUS = add(new Effects.Entry(Identifier.of(MOD_ID, "impetus"),
            "Impetus",
            "Increases Spell Haste.",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, MoreSpellSchools.AIR.color),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    SpellPowerMechanics.HASTE.id,
                                    0.05F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry EYE_OF_THE_STORM = add(new Effects.Entry(Identifier.of(MOD_ID, "eye_of_the_storm"),
            "Eye of the Storm",
            "Increased air spell critical Chance",
            new SpellVulnerabilityStatusEffect(StatusEffectCategory.HARMFUL, MoreSpellSchools.AIR.color)
                    .setVulnerability(MoreSpellSchools.AIR, new SpellPower.Vulnerability(0, 0.075F, 0))
            ,
            new EffectConfig(
                    List.of(
                    )
            )
    ));
    public static Effects.Entry TAILWIND = add(new Effects.Entry(Identifier.of(MOD_ID, "tailwind"),
            "Tailwind",
            "Increases Movement Speed.",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, MoreSpellSchools.AIR.color),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_MOVEMENT_SPEED.getIdAsString(),
                                    0.2F,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                            )
                    )
            )
    ));
    public static Effects.Entry AIR_BUBBLE = add(new Effects.Entry(Identifier.of(MOD_ID, "air_bubble"),
            "Air Bubble",
            "Absorbs damage.",
            new AbsorptionEffect(StatusEffectCategory.BENEFICIAL, MoreSpellSchools.AIR.color),
            new EffectConfig(
                    List.of(
                            new AttributeModifier(
                                    EntityAttributes.GENERIC_MAX_ABSORPTION.getIdAsString(),
                                    2,
                                    EntityAttributeModifier.Operation.ADD_VALUE
                            )
                    )
            )
    ));

    public static void register(ConfigFile.Effects config) {
        for (var entry : entries) {
            Synchronized.configure(entry.effect, true);
        }
        Effects.register(entries, config.effects);
        ActionImpairing.configure(SURYS_TENACITY.effect, EntityActionsAllowed.SILENCE);
        Protection.register(OBSIDIAN_SKIN.entry, new Protection.Pop(
                new ParticleBatch[]{  },
                SkillTreeSounds.rogue_shadows_impact.soundEvent()
        ));

    }
}
