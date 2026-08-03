package com.mrpgc_skill_tree;

import com.mrpgc_skill_tree.effect.MrpgSkillEffects;
import com.mrpgc_skill_tree.skills.MrpgSkillDefinitions;
import com.mrpgc_skill_tree.skills.MrpgSkillSounds;
import com.mrpgc_skill_tree.skills.MrpgSkillSpells;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.puffish.skillsmod.reward.builtin.AttributeReward;
import net.skill_tree_rpgs.data_gen.SkillDefinitionGenerator;
import net.skill_tree_rpgs.node.SpellContainerReward;
import net.skill_tree_rpgs.utils.ResolvableTextContent;
import net.spell_engine.api.datagen.SimpleSoundGeneratorV2;
import net.spell_engine.api.datagen.SpellGenerator;
import net.spell_engine.client.gui.SpellTooltip;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.mrpgc_skill_tree.MRPGCSkillTreeAddOn.MOD_ID;

public class MRPGCSkillTreeAddOnDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(LangGenerator::new);
		pack.addProvider(SpellsGen::new);
		pack.addProvider(SkillDefinitionGen::new);
		pack.addProvider(SoundGen::new);
	}

	public static class LangGenerator extends FabricLanguageProvider {
		protected LangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(dataOutput, registryLookup);
		}

		@Override
		public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
			for (var skill: MrpgSkillDefinitions.ENTRIES) {
				if (skill.title() != null && !skill.title().isEmpty()) {
					translationBuilder.add(skill.titleTranslationKey(), skill.title());
				}
				if (skill.description() != null && !skill.description().isEmpty()) {
					translationBuilder.add(skill.descriptionTranslationKey(), skill.description());
				}
			}
			for (var skill: MrpgSkillDefinitions.WEAPON_ENTRIES) {
				if (skill.title() != null && !skill.title().isEmpty()) {
					translationBuilder.add(skill.titleTranslationKey(), skill.title());
				}
				if (skill.description() != null && !skill.description().isEmpty()) {
					translationBuilder.add(skill.descriptionTranslationKey(), skill.description());
				}
			}
			for (var entry: MrpgSkillSpells.all) {
				translationBuilder.add(SpellTooltip.spellTranslationKey(entry.id()), entry.title());
				translationBuilder.add(SpellTooltip.spellDescriptionTranslationKey(entry.id()), entry.description());
			}
			MrpgSkillEffects.entries.forEach(entry -> {
				translationBuilder.add(entry.effect.getTranslationKey(), entry.title);
				translationBuilder.add(entry.effect.getTranslationKey() + ".description", entry.description);
			});
		}
	}
	public static class SoundGen extends SimpleSoundGeneratorV2 {
		public SoundGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(dataOutput, registryLookup);
		}

		@Override
		public void generateSounds(Builder builder) {
			builder.entries.add(new Entry(MOD_ID,
							MrpgSkillSounds.entries.stream()
									.map(entry -> SoundEntry.withVariants(entry.id().getPath(), entry.variants()))
									.toList()
					)
			);
		}
	}

	public static class SpellsGen extends SpellGenerator {
		public SpellsGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(dataOutput, registryLookup);
		}

		@Override
		public void generateSpells(Builder builder) {
			for (var entry : MrpgSkillSpells.all) {
				builder.add(entry.id(), entry.spell());
			}
		}
	}
	public static class SkillDefinitionGen extends SkillDefinitionGenerator {
		public SkillDefinitionGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(dataOutput, registryLookup);
		}

		@Override
		public void generate(Builder builder) {
			builder.entries.add(new Entry(MrpgSkillDefinitions.CATEGORY_ID, buildDefinitions(MrpgSkillDefinitions.ENTRIES)));
			builder.entries.add(new Entry(MrpgSkillDefinitions.WEAPON_CATEGORY_ID, buildDefinitions(MrpgSkillDefinitions.WEAPON_ENTRIES)));
		}

		private LinkedHashMap<String, Format> buildDefinitions(List<MrpgSkillDefinitions.Entry> skills) {
			LinkedHashMap<String, Format> skillDefinitions = new LinkedHashMap<>();
			for (var skill : skills) {
				Translatable title = null;
				if (skill.title() != null && !skill.title().isEmpty()) {
					title = new Translatable(skill.titleTranslationKey());
				}
				Text description;
				if (skill.description() != null && !skill.description().isEmpty()) {
					description = Text.translatable(skill.descriptionTranslationKey());
				} else {
					description = MutableText.of(new ResolvableTextContent(skill.id()));
				}

				Icon icon = null;
				switch (skill.icon().type()) {
					case TEXTURE -> icon = Icon.texture(skill.icon().value());
					case ITEM -> icon = skill.icon().modelId() != null
							? Icon.itemWithModel(skill.icon().value(), skill.icon().modelId())
							: Icon.item(skill.icon().value());
					case EFFECT -> icon = Icon.effect(skill.icon().value());
				}
				ArrayList<Reward> rewards = new ArrayList<>();
				if (skill.attributeReward() != null) {
					var attribute = skill.attributeReward();
					rewards.add(new Reward(AttributeReward.ID.toString(), RewardAttribute.from(attribute.attribute(),  attribute.modifier())));
				}
				if(skill.spellReward() != null) {
					rewards.add(new Reward(SpellContainerReward.ID.toString(), new SpellContainerReward.DataStructure(skill.spellReward())));
				}
				var format = new Format(title, description, icon, rewards, skill.required_mods());
				skillDefinitions.put(skill.id(), format);
			}
			return skillDefinitions;
		}
	}
}
