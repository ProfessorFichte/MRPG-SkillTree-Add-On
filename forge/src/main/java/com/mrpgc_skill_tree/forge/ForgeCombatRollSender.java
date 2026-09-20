package com.mrpgc_skill_tree.forge;

import com.mrpgc_skill_tree.compat.CombatRollCompat;
import net.combatroll.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ForgeCombatRollSender implements CombatRollCompat.Sender {
    @Override
    public boolean canSend(ServerPlayerEntity player, Identifier channel) {
        return ServerPlayNetworking.canSend(player, channel);
    }

    @Override
    public void send(ServerPlayerEntity player, Identifier channel, PacketByteBuf buf) {
        ServerPlayNetworking.send(player, channel, buf);
    }
}
