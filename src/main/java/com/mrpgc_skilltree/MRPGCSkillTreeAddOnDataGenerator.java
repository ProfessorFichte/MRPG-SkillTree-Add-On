package com.mrpgc_skilltree;

import com.mrpgc_skilltree.effect.MrpgSkillEffects;
import com.mrpgc_skilltree.skills.MrpgSkillSpells;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.spell_engine.api.datagen.SpellGenerator;
import net.spell_engine.client.gui.SpellTooltip;

import java.util.concurrent.CompletableFuture;

public class MRPGCSkillTreeAddOnDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(LangGenerator::new);
		pack.addProvider(SpellsGen::new);
		pack.addProvider(SkillDefinitionGen::new);
	}

	public static class LangGenerator extends FabricLanguageProvider {
		protected LangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(dataOutput, registryLookup);
		}

		@Override
		public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
			for (var skill: SkillDefinitions.ENTRIES) {
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
}
