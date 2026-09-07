package com.mrpgc_skill_tree.fabric;

import com.mrpgc_skill_tree.MRPGCSkillTreeAddOn;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import static com.mrpgc_skill_tree.MRPGCSkillTreeAddOn.MOD_ID;

public final class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        MRPGCSkillTreeAddOn.init();
        registerResourcePack();
        MRPGCSkillTreeAddOn.registerSounds();
        MRPGCSkillTreeAddOn.registerEffects();
    }

    private void registerResourcePack() {
        if (!MRPGCSkillTreeAddOn.tweaksConfig.value.disable_mrpgc_skilltree_changes) {
            FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer -> {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        Identifier.of(MOD_ID, "mrpgc_skill_tree_changes"),
                        modContainer,
                        ResourcePackActivationType.ALWAYS_ENABLED
                );
            });
        }
        MRPGCSkillTreeAddOn.tweaksConfig.save();
    }
}
