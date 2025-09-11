package com.mrpgc_skilltree.client;

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
import net.spell_engine.client.gui.SpellTooltip;

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
    }

    private void renderBlindnessOverlay(DrawContext drawContext, MinecraftClient client) {
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        drawContext.drawTexture(BLINDNESS_TEXTURE, 0, 0, 0, 0, width, height, width, height);
    }
}