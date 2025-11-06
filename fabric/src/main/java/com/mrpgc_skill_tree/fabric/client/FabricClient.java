package com.mrpgc_skill_tree.fabric.client;

import com.mrpgc_skill_tree.client.MRPGCSkillTreeClient;
import net.fabricmc.api.ClientModInitializer;

public final class FabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MRPGCSkillTreeClient.init();
    }
}
