package com.mrpgc_skilltree.skills;

import com.mrpgc_skilltree.MRPGCSkillTreeAddOn;
import net.minecraft.util.Identifier;
import net.spell_engine.fx.SpellEngineSounds;

import java.util.ArrayList;
import java.util.List;

public class MrpgSkillSounds {
    public static final List<SpellEngineSounds.Entry> entries = new ArrayList<>();
    private static SpellEngineSounds.Entry add(SpellEngineSounds.Entry entry) {
        entries.add(entry);
        return entry;
    }
    private static SpellEngineSounds.Entry entry(String name) {
        return new SpellEngineSounds.Entry(Identifier.of(MRPGCSkillTreeAddOn.MOD_ID, name));
    }

    public static final SpellEngineSounds.Entry air_impetus_buff = add(entry("air_impetus_buff"));
    public static final SpellEngineSounds.Entry air_eye_of_the_storm = add(entry("air_eye_of_the_storm"));
    public static final SpellEngineSounds.Entry air_tailwind_release = add(entry("air_tailwind_release"));
    public static final SpellEngineSounds.Entry air_tailwind_loop = add(entry("air_tailwind_loop"));
    public static final SpellEngineSounds.Entry air_wind_flurry_release = add(entry("air_wind_flurry_release"));
    public static final SpellEngineSounds.Entry earthen_blessing = add(entry("earthen_blessing"));
    public static final SpellEngineSounds.Entry splashdown = add(entry("splashdown"));
    public static final SpellEngineSounds.Entry soothing_mist_cleanse = add(entry("soothing_mist_cleanse"));
    public static final SpellEngineSounds.Entry second_wave = add(entry("second_wave"));
    public static final SpellEngineSounds.Entry seismic_entry = add(entry("seismic_entry"));
    public static final SpellEngineSounds.Entry torrent = add(entry("torrent"));
    public static final SpellEngineSounds.Entry calming_flow_release = add(entry("calming_flow_release"));
    public static final SpellEngineSounds.Entry cleave_impact = add(entry("cleave_impact"));
    public static final SpellEngineSounds.Entry blood_frenzy_heal = add(entry("blood_frenzy_heal"));
    public static final SpellEngineSounds.Entry spinning_slash_impact = add(entry("spinning_slash_impact"));
    public static final SpellEngineSounds.Entry burst_of_aggression = add(entry("burst_of_aggression"));
    public static final SpellEngineSounds.Entry ragnarok_release = add(entry("ragnarok_release"));
    public static final SpellEngineSounds.Entry calm_mind = add(entry("calm_mind"));
    public static final SpellEngineSounds.Entry flying_fists = add(entry("flying_fists"));
    public static final SpellEngineSounds.Entry surys_grace = add(entry("surys_grace"));
    public static final SpellEngineSounds.Entry surys_tenacity = add(entry("surys_tenacity"));
    public static final SpellEngineSounds.Entry protector_of_the_tower = add(entry("protector_of_the_tower"));
    public static final SpellEngineSounds.Entry last_stand = add(entry("last_stand"));
    public static final SpellEngineSounds.Entry smokebomb_loop = add(entry("smokebomb_loop"));
    public static final SpellEngineSounds.Entry smokebomb_release = add(entry("smokebomb_release"));
    public static final SpellEngineSounds.Entry shadow_refuge_loop = add(entry("shadow_refuge_loop"));
    public static final SpellEngineSounds.Entry shadow_refuge_release = add(entry("shadow_refuge_release"));

    public static void register() {
        for (var entry: entries) {
            entry.register();
        }
    }
}
