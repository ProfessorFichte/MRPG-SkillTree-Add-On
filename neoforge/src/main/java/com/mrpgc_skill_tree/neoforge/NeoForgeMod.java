package com.mrpgc_skill_tree.neoforge;

import com.mrpgc_skill_tree.MRPGCSkillTreeAddOn;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(MRPGCSkillTreeAddOn.MOD_ID)
public final class NeoForgeMod {
    private static boolean datapackRegistered = false;
    private static boolean warningShown = false;

    public NeoForgeMod(IEventBus modBus) {
        MRPGCSkillTreeAddOn.init();

        modBus.addListener(EventPriority.LOWEST, this::onCommonSetup);
        modBus.addListener(RegisterEvent.class, NeoForgeMod::register);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::onServerStarted);

        MRPGCSkillTreeAddOn.tweaksConfig.save();
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        // Register early so it's available
        event.enqueueWork(() -> {
            if (!datapackRegistered && !MRPGCSkillTreeAddOn.tweaksConfig.value.disable_mrpgc_skilltree_changes) {
                FabricLoader.getInstance().getModContainer(MRPGCSkillTreeAddOn.MOD_ID).ifPresent(modContainer -> {
                    ResourceManagerHelper.registerBuiltinResourcePack(
                            Identifier.of(MRPGCSkillTreeAddOn.MOD_ID, "mrpgc_skill_tree_changes"),
                            modContainer,
                            ResourcePackActivationType.ALWAYS_ENABLED
                    );
                    datapackRegistered = true;
                    MRPGCSkillTreeAddOn.LOGGER.info("Registered MRPGC Skill Tree Changes datapack");
                });
            }
        });
    }

    private void onServerStarted(ServerStartedEvent event) {
        if (!warningShown && !MRPGCSkillTreeAddOn.tweaksConfig.value.disable_mrpgc_skilltree_changes) {
            warningShown = true;
            MRPGCSkillTreeAddOn.LOGGER.info("==================================================");
            MRPGCSkillTreeAddOn.LOGGER.info("MRPGC Skill Tree Add-On loaded successfully!");
            MRPGCSkillTreeAddOn.LOGGER.info("");
            MRPGCSkillTreeAddOn.LOGGER.info("IMPORTANT: If skill tree changes are NOT working:");
            MRPGCSkillTreeAddOn.LOGGER.info("The datapack may need to be reordered. Run these commands:");
            MRPGCSkillTreeAddOn.LOGGER.info("  1. /datapack disable \"mrpgc_skill_tree:mrpgc_skill_tree_changes\"");
            MRPGCSkillTreeAddOn.LOGGER.info("  2. /datapack enable \"mrpgc_skill_tree:mrpgc_skill_tree_changes\" last");
            MRPGCSkillTreeAddOn.LOGGER.info("  3. /reload");
            MRPGCSkillTreeAddOn.LOGGER.info("");
            MRPGCSkillTreeAddOn.LOGGER.info("This only needs to be done once - the order will persist.");
            MRPGCSkillTreeAddOn.LOGGER.info("See DATAPACK_LOAD_ORDER.md for more information.");
            MRPGCSkillTreeAddOn.LOGGER.info("==================================================");
        }
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

