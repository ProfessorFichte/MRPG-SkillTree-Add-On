package com.mrpgc_skill_tree.compat;

import net.combat_roll.Platform;
import net.combat_roll.api.event.ServerSideRollEvents;
import net.combat_roll.client.RollEffect;
import net.combat_roll.network.Packets;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.internals.container.SpellContainerSource;

public class CombatRollCompat {
    public CombatRollCompat() {
    }
    public static void register() {
        ServerSideRollEvents.PLAYER_START_ROLLING.register((player, roll) -> {
            var spinningSlash = SpellRegistry.from(player.getServerWorld())
                    .getEntry(Identifier.of("mrpgc_skill_tree", "berserker_tier_2_passive_1"));
            if (spinningSlash.isEmpty()) {
                return;
            }
            if (SpellContainerSource.passiveSpellsOf(player).contains(spinningSlash.get())) {
                Packets.RollAnimation forwardPacket = new Packets.RollAnimation(player.getId(), new RollEffect.Visuals(Identifier.of("mrpgc_skill_tree", "spinning_slash").toString(), RollEffect.Particles.PUFF), roll);
                Platform.tracking(player).forEach((serverPlayer) -> {
                    try {
                        if (Platform.networkS2C_CanSend(serverPlayer, Packets.RollAnimation.PACKET_ID)) {
                            Platform.networkS2C_Send(serverPlayer, forwardPacket);
                        }
                    } catch (Exception var3) {
                        Exception e = var3;
                        e.printStackTrace();
                    }

                });
                Platform.networkS2C_Send(player, forwardPacket);
            }

        });
    }
}
