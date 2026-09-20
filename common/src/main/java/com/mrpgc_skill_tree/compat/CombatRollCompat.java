package com.mrpgc_skill_tree.compat;

import com.mrpgc_skill_tree.utils.SpellLookup;
import net.combatroll.api.event.ServerSideRollEvents;
import net.combatroll.client.RollEffect;
import net.combatroll.network.Packets;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.spell_engine.Platform;
import net.spell_engine.internals.container.SpellContainerSource;

import java.util.ArrayList;

public class CombatRollCompat {
    public interface Sender {
        boolean canSend(ServerPlayerEntity player, Identifier channel);
        void send(ServerPlayerEntity player, Identifier channel, PacketByteBuf buf);
    }

    public static Sender sender;

    private static final Identifier SPINNING_SLASH_PASSIVE = new Identifier("mrpgc_skill_tree", "berserker_tier_2_passive_1");
    private static final Identifier SPINNING_SLASH_ANIMATION = new Identifier("mrpgc_skill_tree", "spinning_slash");

    public static void register() {
        ServerSideRollEvents.PLAYER_START_ROLLING.register((player, velocity) -> {
            var passive = SpellLookup.entry(player.getServerWorld(), SPINNING_SLASH_PASSIVE);
            if (sender == null || passive == null || !SpellContainerSource.getSpellsOf(player).passives().contains(passive)) {
                return;
            }
            var visuals = new RollEffect.Visuals(SPINNING_SLASH_ANIMATION.toString(), RollEffect.Particles.PUFF);
            var packet = new Packets.RollAnimation(player.getId(), visuals, velocity);
            var receivers = new ArrayList<>(Platform.tracking(player));
            receivers.add(player);
            for (var receiver : receivers) {
                if (sender.canSend(receiver, Packets.RollAnimation.ID)) {
                    sender.send(receiver, Packets.RollAnimation.ID, packet.write());
                }
            }
        });
    }
}
