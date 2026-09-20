package com.mrpgc_skill_tree;

import com.mrpgc_skill_tree.compat.CombatRollCompat;
import com.mrpgc_skill_tree.effect.MrpgSkillEffects;
import com.mrpgc_skill_tree.skills.CustomSpellImpacts;
import com.mrpgc_skill_tree.skills.MrpgSkillSounds;
import com.mrpgc_skill_tree.config.TweaksConfig;
import net.spell_engine.Platform;
import net.spell_engine.rpg_series.config.ConfigFile;
import net.tiny_config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MRPGCSkillTreeAddOn{
	public static final String MOD_ID = "mrpgc_skill_tree";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final String CHANGES_PACK_PATH = "resourcepacks/mrpgc_skill_tree_changes";
	public static ConfigManager<ConfigFile.Effects> effectConfig = new ConfigManager<>
			("effects_v0", new ConfigFile.Effects())
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();
	public static ConfigManager<TweaksConfig> tweaksConfig = new ConfigManager<TweaksConfig>
			("tweaks_v0", new TweaksConfig())
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();


	public static void init() {
		effectConfig.refresh();
		tweaksConfig.refresh();
		if (Platform.util().isModLoaded("combatroll")) {
			CombatRollCompat.register();
		}
		CustomSpellImpacts.registerCustomImpacts();

	}
	public static void registerSounds() {
		MrpgSkillSounds.register();
	}
	public static void registerEffects() {
		MrpgSkillEffects.register(effectConfig.value);
		effectConfig.save();
	}

}