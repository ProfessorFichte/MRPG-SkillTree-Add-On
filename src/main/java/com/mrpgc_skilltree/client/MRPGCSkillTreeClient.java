package com.mrpgc_skilltree.client;

import com.mrpgc_skilltree.effect.MrpgSkillEffects;
import com.mrpgc_skilltree.skills.MrpgSkillSpells;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
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