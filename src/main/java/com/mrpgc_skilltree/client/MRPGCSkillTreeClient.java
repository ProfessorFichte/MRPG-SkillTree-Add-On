package com.mrpgc_skilltree.client;

import com.mrpgc_skilltree.client.effect.EyeofTheStormRenderer;
import com.mrpgc_skilltree.effect.MrpgSkillEffects;
import com.mrpgc_skilltree.skills.MrpgSkillDefinitions;
import com.mrpgc_skilltree.skills.MrpgSkillSpells;
import com.mrpgc_skilltree.utils.MrpgTranslationUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.skill_tree_rpgs.utils.TranslationUtil;
import net.spell_engine.api.effect.CustomModelStatusEffect;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.api.render.BuffParticleSpawner;
import net.spell_engine.api.render.CustomModels;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.client.gui.SpellTooltip;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;

import java.util.List;

import static com.mrpgc_skilltree.MRPGCSkillTreeAddOn.MOD_ID;

public class MRPGCSkillTreeClient implements ClientModInitializer {
    private static final Identifier BLINDNESS_TEXTURE = Identifier.of("textures/misc/vignette.png");

    @Override
    public void onInitializeClient() {
        for (var spell: MrpgSkillSpells.all) {
            if (spell.mutator() != null) {
                SpellTooltip.addDescriptionMutator(spell.id(), spell.mutator());
            }
        }
        for (var entry: MrpgSkillDefinitions.ENTRIES) {
            var skillId = entry.id();
            if (entry.spellReward() != null) {
                var container = entry.spellReward().get(0);
                var id = Identifier.of(container.spell_ids().getFirst());
                TranslationUtil.resolvers.put(skillId, () -> TranslationUtil.resolveSpellDetails(id));
            }
            else if (entry.attributeReward() != null) {
                var attribute = entry.attributeReward();
                TranslationUtil.resolvers.put(skillId, () -> MrpgTranslationUtil.resolveAttributeModifierTooltip(attribute));
            }
        }

        HudRenderCallback.EVENT.register((guiGraphics, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null && client.player.hasStatusEffect(MrpgSkillEffects.SMOKE_BOMB.entry)) {
                renderBlindnessOverlay(guiGraphics, client);
            }
        });
        CustomModels.registerModelIds(List.of(
                EyeofTheStormRenderer.modelId,
                Identifier.of(MOD_ID, "projectile/wind_flurry")
        ));

        CustomModelStatusEffect.register(MrpgSkillEffects.EYE_OF_THE_STORM.effect, new EyeofTheStormRenderer());

        final Color EARTH_SPELL_COLOR = new Color(255.0F, 165.0F, 0.0F);
        final var speedParticles = new ParticleBatch(
                SpellEngineParticles.MagicParticles.get(
                        SpellEngineParticles.MagicParticles.Shape.STRIPE,
                        SpellEngineParticles.MagicParticles.Motion.FLOAT).id().toString(),
                ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET,
                0.3F, 0.05F, 0.15F)
                .extent(-0.2F);
        final var phaseShiftParticles = new ParticleBatch(
                SpellEngineParticles.aura_effect_668.id().toString(),
                ParticleBatch.Shape.LINE, ParticleBatch.Origin.CENTER,
                1, 0, 0)
                .scale(1.4F)
                .followEntity(true);
        final var barrierParticles = new ParticleBatch(
                SpellEngineParticles.aura_effect_622.id().toString(),
                ParticleBatch.Shape.LINE, ParticleBatch.Origin.CENTER,
                1, 0, 0)
                .scale(1.4F)
                .followEntity(true);

        CustomParticleStatusEffect.register(
                MrpgSkillEffects.TAILWIND.effect,
                new BuffParticleSpawner(new ParticleBatch[]{
                        speedParticles.copy()
                                .color(Color.WHITE.toRGBA())
                })
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.IMPETUS.effect,
                new BuffParticleSpawner(
                        phaseShiftParticles.color(Color.WHITE.toRGBA())
                ).withFrequency(20).scaleWithAmplifier(false)
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.AIR_BUBBLE.effect,
                new BuffParticleSpawner(
                        barrierParticles.copy().color(Color.WHITE.alpha(0.5F).toRGBA())
                ).withFrequency(30).scaleWithAmplifier(false)
        );
        CustomParticleStatusEffect.register(
                MrpgSkillEffects.STONE_HEART.effect,
                new BuffParticleSpawner(
                        barrierParticles.copy().color(EARTH_SPELL_COLOR.alpha(0.5F).toRGBA())
                ).withFrequency(30).scaleWithAmplifier(false)
        );
    }

    private void renderBlindnessOverlay(DrawContext drawContext, MinecraftClient client) {
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        drawContext.drawTexture(BLINDNESS_TEXTURE, 0, 0, 0, 0, width, height, width, height);
    }
}