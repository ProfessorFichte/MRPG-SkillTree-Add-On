package com.mrpgc_skill_tree.neoforge.client;

import com.mrpgc_skill_tree.MRPGCSkillTreeAddOn;
import com.mrpgc_skill_tree.client.MRPGCSkillTreeClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = MRPGCSkillTreeAddOn.MOD_ID, value = Dist.CLIENT)
public class NeoForgeClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MRPGCSkillTreeClient.init();
    }
}