package com.mrpgc_skill_tree.skills;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.render.LightEmission;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.fx.Fx;
import net.spell_engine.api.spell.fx.ParticleGroup;
import net.spell_engine.api.spell.fx.ParticleGroupBuilder;
import net.spell_engine.api.spell.fx.Sound;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;
import net.spell_power.api.SpellSchools;
import com.mrpgc_skill_tree.effect.MrpgSkillEffects;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class BardSkillSpells {
    public static final List<MrpgSkillSpells.Entry> all = new ArrayList<>();

    private static MrpgSkillSpells.Entry add(MrpgSkillSpells.Entry entry) {
        all.add(entry);
        return entry;
    }

    /// MAGICAL BALLAD
    public static final MrpgSkillSpells.Entry bard_tier_2_spell_1_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.BARD, MrpgSkillSpells.bardSchool,
            "bard_tier_2_spell_1_root", "bards_rpg:magical_ballad", "Magical Ballad", 0.15F));
    public static final MrpgSkillSpells.Entry bard_tier_2_spell_1_modifier_1 = add(bard_tier_2_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry bard_tier_2_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "bard_tier_2_spell_1_modifier_1");
        var title = "Poignant Ballad";
        var description = "Magical Ballad pierces through {pierce} more targets.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.bardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "bards_rpg:magical_ballad";
        modifier.projectile_perks = Spell.ProjectileData.Perks.EMPTY();
        modifier.projectile_perks.pierce = 2;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BARD));
    }
    public static final MrpgSkillSpells.Entry bard_tier_2_spell_1_modifier_2 = add(bard_tier_2_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry bard_tier_2_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "bard_tier_2_spell_1_modifier_2");
        var title = "Evocative Ballad";
        // The literal `%` MUST be doubled: `I18n.translate` feeds the lang value to `String.format`,
        // and `"% l"` is not a valid conversion - this shipped rendering the whole tooltip line as
        // "Format error: Magical Ballad's projectile is 40% larger...".
        var description = "Magical Ballad's projectile is 40%% larger, increasing its hitbox size.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.bardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "bards_rpg:magical_ballad";
        modifier.projectile_scale_multiply = 0.4F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BARD));
    }

    /// VICIOUS MOCKERY
    public static final MrpgSkillSpells.Entry bard_tier_2_spell_2_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.BARD, MrpgSkillSpells.bardSchool,
            "bard_tier_2_spell_2_root", "bards_rpg:vicious_mockery", "Vicious Mockery", 0.15F));
    public static final MrpgSkillSpells.Entry bard_tier_2_spell_2_modifier_1 = add(bard_tier_2_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry bard_tier_2_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "bard_tier_2_spell_2_modifier_1");
        var title = "Dirty Tricks";
        var effect = MrpgSkillEffects.DIRTY_TRICKS;
        var description = "Vicious Mockery additionally applies Dirty Tricks, reducing the target's Ranged Haste, Attack Speed & Spell Haste for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.bardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "bards_rpg:vicious_mockery";
        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 5, 0, 0);
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BARD));
    }
    public static final MrpgSkillSpells.Entry bard_tier_2_spell_2_modifier_2 = add(bard_tier_2_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry bard_tier_2_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "bard_tier_2_spell_2_modifier_2");
        var title = "Loud Insults";
        var description = "Increases the range of Vicious Mockery by {range_add} blocks.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.bardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "bards_rpg:vicious_mockery";
        modifier.range_add = 5F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BARD));
    }

    /// ENCORE
    public static final MrpgSkillSpells.Entry bard_tier_3_spell_1_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.BARD, MrpgSkillSpells.bardSchool,
            "bard_tier_3_spell_1_root", "bards_rpg:encore", "Encore", 0.15F));
    public static final MrpgSkillSpells.Entry bard_tier_3_spell_1_modifier_1 = add(bard_tier_3_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry bard_tier_3_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "bard_tier_3_spell_1_modifier_1");
        var title = "Radiant Encore";
        var effect = MrpgSkillEffects.RADIANT_ENCORE;
        var description = "Casting Encore has {trigger_chance} chance to make your next spell an instant cast.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.bardSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.specificSpellCast("bards_rpg:encore");
        trigger.chance = 0.3F;
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 4, 0, 0);
        spell.impacts = List.of(impact);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BARD));
    }
    public static final MrpgSkillSpells.Entry bard_tier_3_spell_1_modifier_2 = add(bard_tier_3_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry bard_tier_3_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "bard_tier_3_spell_1_modifier_2");
        var title = "Refreshing Replay";
        var description = "Casting Encore has {trigger_chance} chance to heal yourself and nearby allies for {heal} of their max health.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.bardSchool;
        spell.range = 8;

        var trigger = SpellBuilder.Triggers.specificSpellCast("bards_rpg:encore");
        trigger.chance = 0.3F;
        spell.passive.triggers = List.of(trigger);

        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.include_caster = true;

        var heal = SpellBuilder.Impacts.heal(0.15F);
        heal.attribute = EntityAttributes.GENERIC_MAX_HEALTH.getIdAsString();
        heal.attribute_from_target = true;
        heal.school = SpellSchools.HEALING;
        heal.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_heal, ParticleGroup.Motion.BURST)
                        .color(Color.ARCANE.toRGBA())
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(20F).speed(0.2F, 0.5F)));
        spell.impacts = List.of(heal);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BARD));
    }

    /// WARDEN'S PAEAN
    public static final MrpgSkillSpells.Entry bard_tier_3_spell_2_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.BARD, MrpgSkillSpells.bardSchool,
            "bard_tier_3_spell_2_root", "bards_rpg:wardens_paean", "Warden's Paean", 0.15F));
    public static final MrpgSkillSpells.Entry bard_tier_3_spell_2_modifier_1 = add(bard_tier_3_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry bard_tier_3_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "bard_tier_3_spell_2_modifier_1");
        var title = "Warden's Barrier";
        var effect = MrpgSkillEffects.BARD_BARRIER;
        var description = "Warden's Paean additionally grants allies a barrier that absorbs damage, scaling with your healing power.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.bardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "bards_rpg:wardens_paean";
        var impact = SpellBuilder.Impacts.effectAdd_ScaledAmplifier(effect.id.toString(), 8, 0, 0.15F);
        var filter = new Spell.Modifier.ImpactFilter();
        filter.school = SpellSchools.HEALING;
        modifier.impact_filters = List.of(filter);
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BARD));
    }
    public static final MrpgSkillSpells.Entry bard_tier_3_spell_2_modifier_2 = add(bard_tier_3_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry bard_tier_3_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "bard_tier_3_spell_2_modifier_2");
        var title = "Weakening Paean";
        var effect = MrpgSkillEffects.WEAKENING_PAEAN;
        var description = "Warden's Paean additionally increases incoming damage for affected enemies for {effect_duration} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.bardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "bards_rpg:wardens_paean";
        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 8, 0, 0);
        var filter = new Spell.Modifier.ImpactFilter();
        filter.school = SpellSchools.ARCANE;
        modifier.impact_filters = List.of(filter);
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BARD));
    }

    /// ARMY'S PAEON
    public static final MrpgSkillSpells.Entry bard_tier_4_spell_1_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.BARD, MrpgSkillSpells.bardSchool,
            "bard_tier_4_spell_1_root", "bards_rpg:armys_paeon", "Army's Paeon", 0.15F));
    public static final MrpgSkillSpells.Entry bard_tier_4_spell_1_modifier_1 = add(bard_tier_4_spell_1_modifier_1());
    private static MrpgSkillSpells.Entry bard_tier_4_spell_1_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "bard_tier_4_spell_1_modifier_1");
        var title = "Repertoire";
        var effect = MrpgSkillEffects.REPERTOIRE;
        var description = "Army's Paeon additionally grants Repertoire, increasing Critical Chance and Spell Critical Chance.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.bardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "bards_rpg:armys_paeon";
        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 8, 1, 3);
        impact.action.status_effect.amplifier_cap_power_multiplier = 0.15F;
        modifier.mutate_impacts = Spell.Modifier.ImpactListModifier.APPEND;
        modifier.impacts = List.of(impact);
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BARD));
    }
    public static final MrpgSkillSpells.Entry bard_tier_4_spell_1_modifier_2 = add(bard_tier_4_spell_1_modifier_2());
    private static MrpgSkillSpells.Entry bard_tier_4_spell_1_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "bard_tier_4_spell_1_modifier_2");
        var title = "Army's Muse";
        // Three triggers (melee, arrow and spell impact), all at the same chance, so the renderer
        // emits `{trigger_chance_1..3}` and the bare token would render literally.
        var description = "Dealing damage with melee hits, arrows or spells has {trigger_chance_1} chance to slightly reduce your active spell cooldowns.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.bardSchool;
        spell.range = 0;

        var triggers = List.of(
                withChance(SpellBuilder.Triggers.meleeAttackImpact(), 0.15F),
                withChance(SpellBuilder.Triggers.arrowHit(), 0.15F),
                withChance(SpellBuilder.Triggers.spellHit(1.0F, null), 0.15F)
        );
        spell.passive.triggers = triggers;

        var impact = new Spell.Impact();
        impact.action = new Spell.Impact.Action();
        impact.action.type = Spell.Impact.Action.Type.COOLDOWN;
        impact.action.cooldown = new Spell.Impact.Action.Cooldown();
        impact.action.cooldown.actives = new Spell.Impact.Action.Cooldown.Modify();
        impact.action.cooldown.actives.duration_multiplier = 0.92F;
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 4F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BARD));
    }

    /// CRESCENDO
    public static final MrpgSkillSpells.Entry bard_tier_4_spell_2_root = add(MrpgSkillsCommon.powerRoot(
            MrpgSkillSpells.Category.BARD, MrpgSkillSpells.bardSchool,
            "bard_tier_4_spell_2_root", "bards_rpg:crescendo", "Crescendo", 0.15F));
    public static final MrpgSkillSpells.Entry bard_tier_4_spell_2_modifier_1 = add(bard_tier_4_spell_2_modifier_1());
    private static MrpgSkillSpells.Entry bard_tier_4_spell_2_modifier_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "bard_tier_4_spell_2_modifier_1");
        var title = "Reverberating Crescendo";
        var description = "Casting Crescendo has {trigger_chance} chance to send a second, weaker Crescendo shortly after.";
        var spell = MrpgSkillSpells.createModifierAlikePassiveSpell();
        spell.school = MrpgSkillSpells.bardSchool;
        spell.range = 30;

        var trigger = SpellBuilder.Triggers.specificSpellCast("bards_rpg:crescendo");
        trigger.chance = 0.3F;
        spell.passive.triggers = List.of(trigger);

        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.use_caster_as_fallback = true;

        spell.deliver.type = Spell.Delivery.Type.PROJECTILE;
        spell.deliver.delay = 10;
        spell.deliver.projectile = new Spell.Delivery.ShootProjectile();
        spell.deliver.projectile.launch_properties.velocity = 2.0F;
        spell.deliver.projectile.launch_properties.sound = Sound.of(Identifier.of("bards_rpg", "crescendo_launch"));

        var projectile = new Spell.ProjectileData();
        projectile.homing_angle = 0F;
        projectile.perks.pierce = 999999;
        projectile.client_data = new Spell.ProjectileData.Client();
        projectile.client_data.light_level = 12;
        var crescendoModel = SpellBuilder.ProjectileModels.model("bards_rpg:spell_projectile/crescendo", 2.5F, LightEmission.RADIATE);
        projectile.client_data.composite_model = SpellBuilder.ProjectileModels.composite(crescendoModel);
        projectile.hitbox = new Spell.ProjectileData.HitBox(2.2F, 0.8F);
        spell.deliver.projectile.projectile = projectile;

        var damage = SpellBuilder.Impacts.damage(0.4F, 0F);
        damage.sound = new Sound(Identifier.of("bards_rpg", "bard_impact"));
        var debuff = SpellBuilder.Impacts.effectAdd(Identifier.of("bards_rpg", "crescendo").toString(), 2.5F, 1, 3);
        debuff.action.status_effect.amplifier_cap_power_multiplier = 0.15F;
        spell.impacts = List.of(damage, debuff);

        SpellBuilder.Cost.cooldown(spell, 1F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BARD));
    }
    public static final MrpgSkillSpells.Entry bard_tier_4_spell_2_modifier_2 = add(bard_tier_4_spell_2_modifier_2());
    private static MrpgSkillSpells.Entry bard_tier_4_spell_2_modifier_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "bard_tier_4_spell_2_modifier_2");
        var title = "Everlasting Crescendo";
        var description = "Increases the duration of Crescendo's stun effect by {effect_duration_add} sec.";
        var spell = SpellBuilder.createSpellModifier();
        spell.school = MrpgSkillSpells.bardSchool;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "bards_rpg:crescendo";
        modifier.effect_duration_add = 3F;
        spell.modifiers = List.of(modifier);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BARD));
    }

    private static Spell.Trigger withChance(Spell.Trigger trigger, float chance) {
        trigger.chance = chance;
        return trigger;
    }

    /// BARD PASSIVES
    public static final MrpgSkillSpells.Entry bard_tier_1_passive_1 = add(bard_tier_1_passive_1());
    private static MrpgSkillSpells.Entry bard_tier_1_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "bard_tier_1_passive_1");
        var title = "Charismatic Performance";
        var effect = MrpgSkillEffects.CHARISMATIC_PERFORMANCE;
        var description = "Applying a status effect has {trigger_chance} chance to increase your Healing Spell Power & Spell Haste for {effect_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.bardSchool;
        spell.range = 0;

        var trigger = new Spell.Trigger();
        trigger.type = Spell.Trigger.Type.SPELL_IMPACT_SPECIFIC;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.type = Spell.Type.ACTIVE;
        trigger.impact = new Spell.Trigger.ImpactCondition();
        trigger.impact.impact_type = Spell.Impact.Action.Type.STATUS_EFFECT.toString();
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        trigger.chance = 0.35F;
        spell.passive.triggers = List.of(trigger);

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 8, 0, 0);
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 2F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BARD));
    }
    public static final MrpgSkillSpells.Entry bard_tier_1_passive_2 = add(bard_tier_1_passive_2());
    private static MrpgSkillSpells.Entry bard_tier_1_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "bard_tier_1_passive_2");
        var title = "Irresistible Dance";
        // Two triggers (melee impact + spell impact), both at the same chance, so the renderer emits
        // `{trigger_chance_1}` / `{trigger_chance_2}` and the bare token would render literally.
        var description = "Dealing damage has a low {trigger_chance_1} chance to briefly stun the target.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.bardSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var meleeTrigger = SpellBuilder.Triggers.meleeAttackImpact();
        meleeTrigger.chance = 0.08F;
        var spellTrigger = SpellBuilder.Triggers.spellHit(0.08F, null);
        spell.passive.triggers = List.of(meleeTrigger, spellTrigger);

        var impact = SpellBuilder.Impacts.stun(1.5F);
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 8F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BARD));
    }
    public static final MrpgSkillSpells.Entry bard_tier_2_passive_1 = add(bard_tier_2_passive_1());
    private static MrpgSkillSpells.Entry bard_tier_2_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "bard_tier_2_passive_1");
        var effect = MrpgSkillEffects.JOLLY_TIME;
        var title = "Jolly Time";
        var description = "{trigger_chance} chance upon rolling to create a buff zone for {cloud_duration} sec, increasing offensive haste and slightly healing allies within it.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.bardSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        trigger.chance = 0.4F;
        spell.passive.triggers = List.of(trigger);

        spell.deliver.type = Spell.Delivery.Type.CLOUD;
        spell.deliver.delay = 0;
        var cloud = new Spell.Delivery.Cloud();
        cloud.volume.radius = 4F;
        cloud.volume.area.vertical_range_multiplier = 1.5F;
        cloud.impact_tick_interval = 20;
        cloud.time_to_live_seconds = 6;
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        cloud.client_data.light_level = 0;
        cloud.client_data.particles = List.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_spark, ParticleGroup.Motion.FLOAT)
                        .color(Color.ARCANE.toRGBA())
                        .batch(b -> b.shape(ParticleGroup.Shape.PILLAR)
                                .count(15F).speed(0.2F, 0.4F)
                                .verticalOrigin(ParticleGroupBuilder.Batches.FEET)
                                .extent(4F)));
        spell.deliver.clouds = List.of(cloud);

        var buff = SpellBuilder.Impacts.effectSet(effect.id.toString(), 4, 0);
        var heal = SpellBuilder.Impacts.heal(0.1F);
        heal.school = SpellSchools.HEALING;
        spell.impacts = List.of(buff, heal);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BARD));
    }
    public static final MrpgSkillSpells.Entry bard_tier_2_passive_2 = add(bard_tier_2_passive_2());
    private static MrpgSkillSpells.Entry bard_tier_2_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "bard_tier_2_passive_2");
        var effect = MrpgSkillEffects.DANCING_FEET;
        var title = "Dancing Feet";
        // Two status-effect impacts (Dancing Feet + jump boost), so the renderer emits
        // `{effect_duration_1}` / `{effect_duration_2}` and the bare token would render literally.
        // Both last 3 sec, so the first one is the one to show.
        var description = "{trigger_chance} chance upon rolling to highly increase movement speed and jumping height for {effect_duration_1} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.bardSchool;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.roll();
        trigger.chance = 0.4F;
        spell.passive.triggers = List.of(trigger);

        var speed = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 3, 0, 0);
        var jump = SpellBuilder.Impacts.effectAdd("minecraft:jump_boost", 3, 1, 1);
        spell.impacts = List.of(speed, jump);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BARD));
    }
    public static final MrpgSkillSpells.Entry bard_tier_3_passive_1 = add(bard_tier_3_passive_1());
    private static MrpgSkillSpells.Entry bard_tier_3_passive_1() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "bard_tier_3_passive_1");
        var effect = MrpgSkillEffects.MELODIC_PROTECTION;
        var title = "Melodic Protection";
        // Two triggers (status-effect impact + heal impact), both at the same chance, so the
        // renderer emits `{trigger_chance_1}` / `{trigger_chance_2}` and the bare token would render literally.
        var description = "Applying status effects or healing has {trigger_chance_1} chance to grant a stack of Melodic Protection, nullifying the next hit. Healing Spell Power increases the amount of stacks.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.bardSchool;
        spell.range = 0;

        var effectTrigger = new Spell.Trigger();
        effectTrigger.type = Spell.Trigger.Type.SPELL_IMPACT_SPECIFIC;
        effectTrigger.spell = new Spell.Trigger.SpellCondition();
        effectTrigger.spell.type = Spell.Type.ACTIVE;
        effectTrigger.impact = new Spell.Trigger.ImpactCondition();
        effectTrigger.impact.impact_type = Spell.Impact.Action.Type.STATUS_EFFECT.toString();
        effectTrigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        effectTrigger.chance = 0.25F;
        var healTrigger = new Spell.Trigger();
        healTrigger.type = Spell.Trigger.Type.SPELL_IMPACT_SPECIFIC;
        healTrigger.spell = new Spell.Trigger.SpellCondition();
        healTrigger.spell.type = Spell.Type.ACTIVE;
        healTrigger.impact = new Spell.Trigger.ImpactCondition();
        healTrigger.impact.impact_type = Spell.Impact.Action.Type.HEAL.toString();
        healTrigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        healTrigger.chance = 0.25F;
        spell.passive.triggers = List.of(effectTrigger, healTrigger);

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var impact = SpellBuilder.Impacts.effectAdd_ScaledAmplifier(effect.id.toString(), 12, 0, 0.05F);
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 3F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BARD));
    }
    public static final MrpgSkillSpells.Entry bard_tier_3_passive_2 = add(bard_tier_3_passive_2());
    private static MrpgSkillSpells.Entry bard_tier_3_passive_2() {
        var id = Identifier.of(MrpgSkillSpells.NAMESPACE, "bard_tier_3_passive_2");
        var effect = MrpgSkillEffects.COUNTERCHARM;
        var title = "Countercharm";
        // Two triggers (melee impact + spell impact), both at the same chance, so the renderer emits
        // `{trigger_chance_1}` / `{trigger_chance_2}` and the bare token would render literally.
        var description = "Dealing damage has a low {trigger_chance_1} chance to turn the target against its allies for {effect_duration} sec.";

        var spell = SpellBuilder.createSpellPassive();
        spell.school = MrpgSkillSpells.bardSchool;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var meleeTrigger = SpellBuilder.Triggers.meleeAttackImpact();
        meleeTrigger.chance = 0.06F;
        var spellTrigger = SpellBuilder.Triggers.spellHit(0.06F, null);
        spell.passive.triggers = List.of(meleeTrigger, spellTrigger);

        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 6, 0, 0);
        spell.impacts = List.of(impact);

        SpellBuilder.Cost.cooldown(spell, 15F);

        return new MrpgSkillSpells.Entry(id, spell, title, description, EnumSet.of(MrpgSkillSpells.Category.BARD));
    }
}
