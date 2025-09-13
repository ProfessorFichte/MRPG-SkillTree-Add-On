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


    public static void register() {
        for (var entry: entries) {
            entry.register();
        }
    }
}
