package com.mrpgc_skill_tree.attributes;

// Plain string ids on purpose, touching MRPGCEntityAttributes registers its attributes and throws on Forge 47 outside the ATTRIBUTE RegisterEvent.
public class MrpgAttributeIds {
    private static final String NAMESPACE = "more_rpg_classes:";

    public static final String RAGE_MODIFIER = NAMESPACE + "rage_modifier";
    public static final String TENACITY = NAMESPACE + "tenacity";
    public static final String ARCANE_FUSE_MODIFIER = NAMESPACE + "arcane_fuse_modifier";
}
