package com.mrpgc_skill_tree.neoforge;

import com.mrpgc_skill_tree.MRPGCSkillTreeAddOn;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(MRPGCSkillTreeAddOn.MOD_ID)
public final class NeoForgeMod {
    private static boolean datapackRegistered = false;
    private static boolean warningShown = false;

    public NeoForgeMod(IEventBus modBus) {
        MRPGCSkillTreeAddOn.init();

        modBus.addListener(RegisterEvent.class, NeoForgeMod::register);
        modBus.addListener(this::onAddPackFinders);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::onServerStarted);

        MRPGCSkillTreeAddOn.tweaksConfig.save();
    }

    private void onAddPackFinders(AddPackFindersEvent event) {
        if (datapackRegistered || MRPGCSkillTreeAddOn.tweaksConfig.value.disable_mrpgc_skilltree_changes) {
            return;
        }
        event.addPackFinders(
                Identifier.of(MRPGCSkillTreeAddOn.MOD_ID, "resourcepacks/mrpgc_skill_tree_changes"),
                ResourceType.SERVER_DATA,
                Text.of("MRPGC Skill Tree Changes"),
                ResourcePackSource.BUILTIN,
                true,
                ResourcePackProfile.InsertionPosition.TOP
        );
        datapackRegistered = true;
        MRPGCSkillTreeAddOn.LOGGER.info("Registered MRPGC Skill Tree Changes datapack");
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

