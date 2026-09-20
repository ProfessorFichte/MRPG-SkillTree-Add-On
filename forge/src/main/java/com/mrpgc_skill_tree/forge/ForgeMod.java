package com.mrpgc_skill_tree.forge;

import com.mrpgc_skill_tree.MRPGCSkillTreeAddOn;
import com.mrpgc_skill_tree.compat.CombatRollCompat;
import com.mrpgc_skill_tree.effect.MrpgSkillEffects;
import com.mrpgc_skill_tree.forge.client.ForgeClientMod;
import com.mrpgc_skill_tree.skills.MrpgSkillSounds;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.resource.PathPackResources;
import net.spell_engine.api.effect.Effects;

@Mod(MRPGCSkillTreeAddOn.MOD_ID)
public final class ForgeMod {
    private static final String CHANGES_PACK_ID = "mrpgc_skill_tree_changes";

    @SuppressWarnings("removal")
    public ForgeMod() {
        if (ModList.get().isLoaded("combatroll")) {
            CombatRollCompat.sender = new ForgeCombatRollSender();
        }
        MRPGCSkillTreeAddOn.init();
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(EventPriority.NORMAL, false, RegisterEvent.class, ForgeMod::register);
        modBus.addListener(EventPriority.NORMAL, false, AddPackFindersEvent.class, ForgeMod::addPackFinders);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ForgeClientMod.register(modBus);
        }
        MRPGCSkillTreeAddOn.tweaksConfig.save();
    }

    // Goes through the helper on purpose, on Forge 47.0-47.3 a plain Registry.register throws "Can not register to a locked registry".
    public static void register(RegisterEvent event) {
        event.register(RegistryKeys.SOUND_EVENT, helper -> {
            MrpgSkillSounds.soundsToRegister().forEach(helper::register);
            MrpgSkillSounds.linkEntries();
        });

        event.register(RegistryKeys.STATUS_EFFECT, helper -> {
            MRPGCSkillTreeAddOn.effectConfig.refresh();
            MrpgSkillEffects.effectsToRegister(MRPGCSkillTreeAddOn.effectConfig.value).forEach(helper::register);
            Effects.linkEntries(MrpgSkillEffects.entries);
            MrpgSkillEffects.installBehaviours();
            MRPGCSkillTreeAddOn.effectConfig.save();
        });
    }

    private static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() != ResourceType.SERVER_DATA
                || MRPGCSkillTreeAddOn.tweaksConfig.value.disable_mrpgc_skilltree_changes) {
            return;
        }
        var modFile = ModList.get().getModFileById(MRPGCSkillTreeAddOn.MOD_ID);
        if (modFile == null) {
            return;
        }
        var packRoot = modFile.getFile().findResource(MRPGCSkillTreeAddOn.CHANGES_PACK_PATH);
        event.addRepositorySource(profileAdder -> {
            var profile = ResourcePackProfile.create(
                    CHANGES_PACK_ID,
                    Text.literal("MRPGC Skill Tree Changes"),
                    true,
                    name -> new PathPackResources(name, true, packRoot),
                    ResourceType.SERVER_DATA,
                    ResourcePackProfile.InsertionPosition.TOP,
                    ResourcePackSource.BUILTIN);
            if (profile != null) {
                profileAdder.accept(profile);
            } else {
                MRPGCSkillTreeAddOn.LOGGER.warn("Built-in data pack '{}' could not be read from {}", CHANGES_PACK_ID, packRoot);
            }
        });
    }
}
