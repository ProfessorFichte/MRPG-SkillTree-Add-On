package com.mrpgc_skill_tree.forge.client;

import com.mrpgc_skill_tree.client.MRPGCSkillTreeClient;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ForgeClientMod {
    public static void register(IEventBus modBus) {
        modBus.addListener(EventPriority.NORMAL, false, FMLClientSetupEvent.class, ForgeClientMod::onClientSetup);
    }

    private static void onClientSetup(FMLClientSetupEvent event) {
        MRPGCSkillTreeClient.init();
    }
}
