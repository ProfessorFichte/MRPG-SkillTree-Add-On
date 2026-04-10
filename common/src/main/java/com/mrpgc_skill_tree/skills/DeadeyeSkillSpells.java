package com.mrpgc_skill_tree.skills;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.effect.MRPGCEffects;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.entity.SpellEntityPredicates;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.api.spell.fx.Sound;
import net.spell_engine.client.gui.SpellTooltip;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;
import net.spell_engine.fx.SpellEngineSounds;
import net.spell_engine.internals.target.SpellTarget;
import com.mrpgc_skill_tree.effect.MrpgSkillEffects;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class DeadeyeSkillSpells {
    public static final List<MrpgSkillSpells.Entry> all = new ArrayList<>();

    private static MrpgSkillSpells.Entry add(MrpgSkillSpells.Entry entry) {
        all.add(entry);
        return entry;
    }

    ///DEADEYE MODIFIERS
    public static final MrpgSkillSpells.Entry deadeye_tier_1_spell_1_modifier_1 = add(deadeye_tier_1_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry deadeye_tier_1_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_1_spell_1_modifier_1");
        var title = "Poisonous Sting";
        var description = "Fast Shot has {impact_chance} chance to apply stacking poison, lasting {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.deadeyeSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:fast_shot";
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.PREPEND;

        var debuff = SpellBuilder.Impacts.effectAdd(StatusEffects.POISON.getIdAsString(), 8, 1, 1);
        debuff.action.status_effect.amplifier_cap_power_multiplier = 0.5F;
        debuff.chance = 0.4F;
        debuff.action.status_effect.refresh_duration = true;
        debuff.particles = new ParticleBatch[]{(new ParticleBatch(
                SpellEngineParticles.MagicParticles.get(
                        SpellEngineParticles.MagicParticles.Shape.SPARK,
                        SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                10, 0.5F, 0.8F)
                .color(Color.POISON_MID.toRGBA())),
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.SKULL,
                                SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.5F, 0.8F)
                        .color(Color.POISON_DARK.toRGBA()),
        };
        modifier.impacts = List.of(debuff);

        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_1_spell_1_modifier_2 = add(deadeye_tier_1_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry deadeye_tier_1_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_1_spell_1_modifier_2");
        var title = "Fast Hands";
        var description = "Fast Shot applies {effect_amplifier_cap_add} additional Fast Shot stack.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.deadeyeSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:fast_shot";
        modifier.effect_amplifier_cap_add = 1;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_2_spell_1_modifier_1 = add(deadeye_tier_2_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry deadeye_tier_2_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_2_spell_1_modifier_1");
        var title = "Barbed Trick Arrows";
        var description = "Trick Shot's bleeding effect lasts {effect_duration_add} sec longer.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.deadeyeSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:trick_shot";
        modifier.effect_duration_add = 4;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_2_spell_1_modifier_2 = add(deadeye_tier_2_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry deadeye_tier_2_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_2_spell_1_modifier_2");
        var title = "Bouncing Trick Shots";
        var description = "Trick Shot now ricochets {ricochet} more times.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.deadeyeSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:trick_shot";
        modifier.projectile_perks = Spell.ProjectileData.Perks.EMPTY();
        modifier.projectile_perks.ricochet = 3;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_3_spell_1_modifier_1 = add(deadeye_tier_3_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry deadeye_tier_3_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_3_spell_1_modifier_1");
        var title = "Wounding Shot";
        var description = "If the target has a bad effect Disabling Shot inflicts grievous wounds for {effect_duration} sec.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.deadeyeSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = SpellBuilder.Triggers.specificSpellHit("archers_expansion:disabling_shot");
        var condition = new Spell.TargetCondition();
        condition.entity_predicate_id = SpellEntityPredicates.HAS_BAD_EFFECT.id().toString();
        trigger.target_conditions = List.of(condition);
        spell.passive.triggers = List.of(trigger);

        var debuff = SpellBuilder.Impacts.effectSet(MRPGCEffects.GRIEVOUS_WOUNDS.id.toString(), 6, 0);
        debuff.action.status_effect.amplifier_power_multiplier = 0.25F;
        debuff.action.status_effect.refresh_duration = true;
        spell.impacts = List.of(debuff);

        SpellBuilder.Cost.cooldown(spell, 0.5F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_3_spell_1_modifier_2 = add(deadeye_tier_3_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry deadeye_tier_3_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_3_spell_1_modifier_2");
        var title = "Leaping Swiftness";
        var description = "Disabling Shot increases movement speed by {bonus} for {effect_duration} secs.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.deadeyeSchool;
        spell.range = 0;
        var effect = MrpgSkillEffects.LEAPING_SWIFTNESS;
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(modifier.value, modifier.operation);
            return args.description()
                    .replace("{bonus}", bonus);
        };

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = SpellBuilder.Triggers.specificSpellHit("archers_expansion:disabling_shot");
        var condition = new Spell.TargetCondition();
        condition.entity_predicate_id = SpellEntityPredicates.HAS_BAD_EFFECT.id().toString();
        trigger.target_conditions = List.of(condition);
        spell.passive.triggers = List.of(trigger);

        spell.release.particles = new ParticleBatch[]{
                SpellBuilder.Particles.popUpSign(SpellEngineParticles.sign_speed.id(), Color.WHITE)
        };
        spell.release.sound = new Sound(SpellEngineSounds.SPEED_BOOST.id());

        var buff = SpellBuilder.Impacts.effectSet(effect.id.toString(), 5, 0);
        buff.action.status_effect.refresh_duration = true;
        buff.action.apply_to_caster = true;
        spell.impacts = List.of(buff);

        SpellBuilder.Cost.cooldown(spell, 0.5F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_4_spell_1_modifier_1 = add(deadeye_tier_4_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry deadeye_tier_4_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_4_spell_1_modifier_1");
        var title = "Persistent Gas Cloud";
        var description = "Choking Gas leaves a gas cloud behind, poisoning and dealing {damage} damage to enemies for {cloud_duration} sec.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.deadeyeSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.specificSpellCast("archers_expansion:choking_gas");
        spell.passive.triggers = List.of(trigger);

        spell.deliver.type = Spell.Delivery.Type.CLOUD;
        spell.deliver.delay = 8;
        Spell.Delivery.Cloud cloud = new Spell.Delivery.Cloud();
        cloud.volume.radius = 5;
        cloud.volume.area.vertical_range_multiplier = 0.3F;
        cloud.volume.sound = new Sound(SpellEngineSounds.POISON_CLOUD_TICK.id().toString());
        cloud.impact_tick_interval = 8;
        cloud.time_to_live_seconds = 5;
        cloud.spawn.sound = new Sound(SpellEngineSounds.POISON_CLOUD_SPAWN.id().toString());
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        cloud.client_data.light_level = 0;
        cloud.client_data.particles = new ParticleBatch[]{(new ParticleBatch(SpellEngineParticles.smoke_large.id().toString(), ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET, 1.0F, 0.01F, 0.02F)).color(2583652010L), (new ParticleBatch(SpellEngineParticles.smoke_large.id().toString(), ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET, 1.0F, 0.01F, 0.02F)).color(870134766L)};
        spell.deliver.clouds = List.of(cloud);
        Spell.Impact debuff = SpellBuilder.Impacts.effectSet("archers_expansion:choking_gas", 3, 1);
        debuff.action.status_effect.amplifier_power_multiplier = 0.3F;
        debuff.particles = new ParticleBatch[]{(new ParticleBatch(
                SpellEngineParticles.smoke_large.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                1.5F, 0.01F, 0.02F))
                .color(Color.POISON_MID.toRGBA()),
                new ParticleBatch(SpellEngineParticles.MagicParticles.get(SpellEngineParticles.MagicParticles.Shape.SKULL,
                        SpellEngineParticles.MagicParticles.Motion.DECELERATE).id().toString(), ParticleBatch.Shape.SPHERE,
                        ParticleBatch.Origin.CENTER, 3.0F, 0.1F, 0.2F)
                        .color(Color.POISON_MID.toRGBA())};
        MrpgSkillSpells.poisonDeny(debuff);
        var impact = SpellBuilder.Impacts.damage(0.1F, 0);
        spell.impacts = List.of(debuff,impact);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_4_spell_1_modifier_2 = add(deadeye_tier_4_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry deadeye_tier_4_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_4_spell_1_modifier_2");
        var title = "Bouncing Gas Arrow";
        var description = "The Choking Gas Arrow now ricochets {ricochet} times.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.deadeyeSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:choking_gas";
        modifier.projectile_perks = Spell.ProjectileData.Perks.EMPTY();
        modifier.projectile_perks.ricochet_range = 10;
        modifier.projectile_perks.ricochet = 3;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final Color SHADOW_COLOR = Color.from(0x00B0B0);
    ///DEADEYE PASSIVES
    public static final MrpgSkillSpells.Entry deadeye_tier_1_passive_1 = add(deadeye_tier_1_passive_1());
    private static MrpgSkillSpells.Entry deadeye_tier_1_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_1_passive_1");
        var title = "Barbed Arrows";
        var description = "Arrows have {trigger_chance} chance, to stack bleeding to the target for {effect_duration} sec.";
        var effect = MRPGCEffects.BLEEDING;

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.deadeyeSchool;
        spell.range = 0;
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.arrowHit();
        trigger.chance = 0.4F;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 7F, 0,6);
        impact.action.status_effect.refresh_duration = true;
        MrpgSkillSpells.bleedingDeny(impact);
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.dripping_blood.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.2F, 0.8F)
        };
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 2F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_1_passive_2 = add(deadeye_tier_1_passive_2());
    private static MrpgSkillSpells.Entry deadeye_tier_1_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_1_passive_2");
        var title = "Withdraw";
        var description = "Arrows have {trigger_chance} chance, to cure a negative condition and heal for {heal} hearts.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.deadeyeSchool;
        spell.range = 0;
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.arrowHit();
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        trigger.chance = 0.05F;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.heal(0.025F);
        impact.attribute = EntityAttributes.GENERIC_MAX_HEALTH.getIdAsString();
        impact.attribute_from_target = true;
        impact.action.apply_to_caster = true;
        var cleanse = SpellBuilder.Impacts.effectCleanse();
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.HEAL,
                                SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        25, 0.25F, 0.6F
                ).color(SHADOW_COLOR.toRGBA())
        };
        impact.sound = new Sound(SpellEngineSounds.GENERIC_HEALING_IMPACT_3.id());
        spell.impacts = List.of(impact,cleanse);

        SpellBuilder.Cost.cooldown(spell, 15F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_2_passive_1 = add(deadeye_tier_2_passive_1());
    private static MrpgSkillSpells.Entry deadeye_tier_2_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_2_passive_1");
        var title = "Poison Bomb";
        var description = "{trigger_chance} chance upon rolling to leave behind Choking Gas for {cloud_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.deadeyeSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        trigger.chance = 0.5F;
        spell.passive.triggers = List.of(trigger);

        spell.deliver.type = Spell.Delivery.Type.CLOUD;
        spell.deliver.delay = 5;
        Spell.Delivery.Cloud cloud = new Spell.Delivery.Cloud();
        cloud.volume.radius = 2.5F;
        cloud.volume.area.vertical_range_multiplier = 0.3F;
        cloud.volume.sound = new Sound(SpellEngineSounds.POISON_CLOUD_TICK.id().toString());
        cloud.impact_tick_interval = 8;
        cloud.time_to_live_seconds = 5;
        cloud.spawn.sound = new Sound(SpellEngineSounds.POISON_CLOUD_SPAWN.id().toString());
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        cloud.client_data.light_level = 0;
        cloud.client_data.particles = new ParticleBatch[]{(new ParticleBatch(SpellEngineParticles.smoke_large.id().toString(), ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET, 1.0F, 0.01F, 0.02F)).color(Color.POISON_DARK.toRGBA()), (new ParticleBatch(SpellEngineParticles.smoke_large.id().toString(), ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET, 1.0F, 0.01F, 0.02F)).color(Color.POISON_DARK.toRGBA())};
        spell.deliver.clouds = List.of(cloud);
        Spell.Impact debuff = SpellBuilder.Impacts.effectSet("archers_expansion:choking_gas", 1, 1);
        debuff.action.status_effect.amplifier_power_multiplier = 0.3F;
        debuff.particles = new ParticleBatch[]{(new ParticleBatch(
                SpellEngineParticles.smoke_large.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                1.5F, 0.01F, 0.02F))
                .color(Color.POISON_MID.toRGBA()),
                new ParticleBatch(SpellEngineParticles.MagicParticles.get(SpellEngineParticles.MagicParticles.Shape.SKULL,
                        SpellEngineParticles.MagicParticles.Motion.DECELERATE).id().toString(), ParticleBatch.Shape.SPHERE,
                        ParticleBatch.Origin.CENTER, 3.0F, 0.1F, 0.2F)
                        .color(Color.POISON_MID.toRGBA())};
        MrpgSkillSpells.poisonDeny(debuff);
        spell.impacts = List.of(debuff);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final Color SMOKE_BOMB_COLOR = Color.from(0x302c2c);
    public static final MrpgSkillSpells.Entry deadeye_tier_2_passive_2 = add(deadeye_tier_2_passive_2());
    private static MrpgSkillSpells.Entry deadeye_tier_2_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_2_passive_2");
        var title = "Smoke Bomb";
        var description = "{trigger_chance} chance upon rolling to leave behind a Smoke Bomb for {cloud_duration} sec. Blinding enemies and increasing Evasion for allies.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.deadeyeSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        trigger.chance = 0.5F;
        spell.passive.triggers = List.of(trigger);

        spell.deliver.type = Spell.Delivery.Type.CLOUD;
        spell.deliver.delay = 5;
        Spell.Delivery.Cloud cloud = new Spell.Delivery.Cloud();
        cloud.volume.radius = 2.5F;
        cloud.volume.area.vertical_range_multiplier = 0.3F;
        cloud.volume.sound = new Sound(MrpgSkillSounds.smokebomb_loop.id());
        cloud.impact_tick_interval = 10;
        cloud.time_to_live_seconds = 5;
        cloud.spawn.sound = new Sound(MrpgSkillSounds.smokebomb_release.id());
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        cloud.client_data.light_level = 0;
        cloud.client_data.particles = new ParticleBatch[]{(new ParticleBatch(SpellEngineParticles.smoke_large.id().toString(),
                ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET, 1.0F, 0.01F, 0.02F))
                .color(SMOKE_BOMB_COLOR.toRGBA()),
                (new ParticleBatch(SpellEngineParticles.smoke_large.id().toString(),
                        ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET, 1.0F, 0.01F, 0.02F))
                        .color(SMOKE_BOMB_COLOR.toRGBA())};
        spell.deliver.clouds = List.of(cloud);
        Spell.Impact debuff = SpellBuilder.Impacts.effectSet(MrpgSkillEffects.SMOKE_BOMB.id.toString(), 1, 0);
        debuff.action.status_effect.refresh_duration = true;
        Spell.Impact debuff2 = SpellBuilder.Impacts.effectSet("blindness", 1, 0);
        debuff2.action.status_effect.refresh_duration = true;
        Spell.Impact buff = SpellBuilder.Impacts.effectSet(MrpgSkillEffects.CAMOUFLAGED.id.toString(), 1, 0);
        buff.action.status_effect.refresh_duration = true;
        buff.action.apply_to_caster = true;
        buff.particles = new ParticleBatch[]{(
                new ParticleBatch(
                SpellEngineParticles.smoke_large.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                5.0F, 0.001F, 0.001F))
                .color(SMOKE_BOMB_COLOR.toRGBA()),
                (new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        5.0F, 0.001F, 0.001F))
                .color(SMOKE_BOMB_COLOR.toRGBA())
        };

        spell.impacts = List.of(debuff, debuff2, buff);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_3_passive_1 = add(deadeye_tier_3_passive_1());
    private static MrpgSkillSpells.Entry deadeye_tier_3_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_3_passive_1");
        var title = "Heartseeker";
        var description = "Arrows have {trigger_chance} chance, to deal more damage the less health the target has.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.deadeyeSchool;
        spell.range = 0;
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.arrowHit();
        trigger.chance = 0.2F;
        spell.passive.triggers = List.of(trigger);

        var custom = new Spell.Impact();
        custom.action = new Spell.Impact.Action();
        custom.action.custom = new Spell.Impact.Action.Custom();
        custom.action.type = Spell.Impact.Action.Type.CUSTOM;
        custom.action.custom.intent = SpellTarget.Intent.HARMFUL;
        custom.action.custom.handler = "more_rpg_classes:damage_according_to_missing_health";
        custom.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.dripping_blood.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        35, 0.4F, 1.0F),
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.2F, 0.5F).color(Color.RED.toRGBA())
        };

        spell.impacts = List.of(custom);

        SpellBuilder.Cost.cooldown(spell, 20F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, null, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
    public static final MrpgSkillSpells.Entry deadeye_tier_3_passive_2 = add(deadeye_tier_3_passive_2());
    private static MrpgSkillSpells.Entry deadeye_tier_3_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "deadeye_tier_3_passive_2");
        var title = "Shadow Refuge";
        final var healthThreshold = 0.35F;
        var description = "Upon taking damage below {threshold} health you create a area that heals you for {heal} hearts and gives you invisibility for {effect_duration} secs.";
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var threshold = SpellTooltip.percent(healthThreshold);
            return args.description()
                    .replace("{threshold}", threshold);
        };

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.deadeyeSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.becomingLowHP(healthThreshold);
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);


        float radius =5.0F;
        spell.deliver.type = Spell.Delivery.Type.CLOUD;
        spell.deliver.delay = 0;
        Spell.Delivery.Cloud cloud = new Spell.Delivery.Cloud();
        cloud.volume.radius = radius;
        cloud.volume.area.vertical_range_multiplier = 0.5F;

        cloud.impact_tick_interval = 20;
        cloud.time_to_live_seconds = 7;
        cloud.spawn.sound = new Sound(MrpgSkillSounds.shadow_refuge_release.id());
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        cloud.client_data.light_level = 10;
        var areaParticle = SpellEngineParticles.area_effect_658;
        cloud.client_data.particle_spawn_interval = 20;
        cloud.client_data.interval_particles = new ParticleBatch[] {
                new ParticleBatch(areaParticle.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.GROUND,
                        1, 0.0F, 0.F)
                        .scale(4)
                        .color(SHADOW_COLOR.alpha(0.75F).toRGBA()),
        };
        spell.deliver.clouds = List.of(cloud);

        var heal = SpellBuilder.Impacts.heal(0.05F);
        heal.attribute = EntityAttributes.GENERIC_MAX_HEALTH.getIdAsString();
        heal.attribute_from_target = true;
        heal.action.apply_to_caster = true;
        heal.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.HEAL,
                                SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.25F, 0.4F
                ).color(SHADOW_COLOR.toRGBA())
        };
        var buff = SpellBuilder.Impacts.effectSet(MrpgSkillEffects.SHADOWS_REFUGE.id.toString(), 2, 0);
        buff.sound = new Sound(MrpgSkillSounds.shadow_refuge_release.id());

        spell.impacts = List.of(heal,buff);

        SpellBuilder.Cost.cooldown(spell, 40F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, mutator, EnumSet.of(MrpgSkillSpells.Category.DEADEYE));
    }
}
