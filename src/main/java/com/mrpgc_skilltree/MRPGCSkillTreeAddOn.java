package com.mrpgc_skilltree;

import com.mrpgc_skilltree.effect.MrpgSkillEffects;
import com.mrpgc_skilltree.skills.CustomSpellImpacts;
import net.fabricmc.api.ModInitializer;
import com.mrpgc_skilltree.config.TweaksConfig;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.spell_engine.api.config.ConfigFile;
import net.tinyconfig.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MRPGCSkillTreeAddOn implements ModInitializer {
	public static final String MOD_ID = "mrpgc_skill_tree";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ConfigManager<ConfigFile.Effects> effectConfig = new ConfigManager<>
			("effects", new ConfigFile.Effects())
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();
	public static ConfigManager<TweaksConfig> tweaksConfig = new ConfigManager<>
			("tweaks", new TweaksConfig())
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();


	@Override
	public void onInitialize() {
		effectConfig.refresh();
		tweaksConfig.refresh();
		if (MRPGCSkillTreeAddOn.tweaksConfig.value.disable_mrpgc_skilltree_changes) {
			FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer -> {
				ResourceManagerHelper.registerBuiltinResourcePack(
						Identifier.of(MOD_ID, "mrpgc_skill_tree_changes"),
						modContainer,
						ResourcePackActivationType.ALWAYS_ENABLED
				);
			});
		}
		CustomSpellImpacts.registerCustomImpacts();
		MrpgSkillEffects.register(effectConfig.value);
		effectConfig.save();
	}
}