package com.mrpgc_skill_tree.neoforge;

import com.mrpgc_skill_tree.MRPGCSkillTreeAddOn;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(MRPGCSkillTreeAddOn.MOD_ID)
public final class NeoForgeMod {
    public NeoForgeMod(IEventBus modBus) {
        MRPGCSkillTreeAddOn.init();
        // Register the built-in datapack early in initialization
        if (!MRPGCSkillTreeAddOn.tweaksConfig.value.disable_mrpgc_skilltree_changes) {
            FabricLoader.getInstance().getModContainer(MRPGCSkillTreeAddOn.MOD_ID).ifPresent(modContainer -> {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        Identifier.of(MRPGCSkillTreeAddOn.MOD_ID, "mrpgc_skill_tree_changes"),
                        modContainer,
                        ResourcePackActivationType.ALWAYS_ENABLED
                );
            });
        }
        MRPGCSkillTreeAddOn.tweaksConfig.save();

        modBus.addListener(RegisterEvent.class, NeoForgeMod::register);
    }

        public static void register(RegisterEvent event) {
            event.register(RegistryKeys.SOUND_EVENT, reg -> {
                MRPGCSkillTreeAddOn.registerSounds();
            });

            event.register(RegistryKeys.STATUS_EFFECT, reg -> {
                MRPGCSkillTreeAddOn.registerEffects();
            });
        }
}

