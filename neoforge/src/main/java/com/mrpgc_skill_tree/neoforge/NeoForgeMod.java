package com.mrpgc_skill_tree.neoforge;

import com.mrpgc_skill_tree.MRPGCSkillTreeAddOn;
import net.minecraft.registry.RegistryKeys;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(MRPGCSkillTreeAddOn.MOD_ID)
public final class NeoForgeMod {
    public NeoForgeMod(IEventBus modBus) {
            // Run our common setup.
        MRPGCSkillTreeAddOn.init();
            modBus.addListener(RegisterEvent.class, NeoForgeMod::register);
        }

        public static void register(RegisterEvent event) {
            event.register(RegistryKeys.ITEM, reg -> {
                MRPGCSkillTreeAddOn.registerItems();
            });
            event.register(RegistryKeys.SOUND_EVENT, reg -> {
                MRPGCSkillTreeAddOn.registerSounds();
            });

            event.register(RegistryKeys.STATUS_EFFECT, reg -> {
                MRPGCSkillTreeAddOn.registerEffects();
            });
        }
}

