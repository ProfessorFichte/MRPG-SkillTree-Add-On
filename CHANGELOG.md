# 1.3.0+1.20.1

> ### ⚠️ Read this before updating
>
> This release is a **major technical overhaul and is not backwards compatible.**
>
> - **Requires the matching Spell Engine, More RPG Library and Skill Tree releases.**
> - **Update the whole set together.** Mixing in an older add-on will break at startup or misbehave in play.
>
> **Back up your world before updating.**

- Ported to Minecraft 1.20.1 (Fabric + Forge 47). NeoForge is replaced by Forge on this line, the same Forge jar also loads on NeoForge 1.20.1.
- Requires the matching 1.20.1 releases of Spell Engine (1.10.5), Spell Power (1.6.0), More RPG Library (2.7.2), Skill Tree (1.6.1), Pufferfish's Skills (0.16.7) and Ranged Weapon API (2.3.4). Combat Roll (1.3.3) stays optional.
- 1.20.1 has no max absorption attribute, so the Air Bubble, Stone Heart, Bubble Shield, Reckless Rage, Winters Cloak and Warden's Barrier effects no longer carry the +2 absorption cap modifier, the absorption they grant is unchanged.
- 1.20.1 has no scale attribute, so Last Stand doesn't make you bigger anymore, it still gives the ranged haste and damage reduction. The Eye of the Storm overlay is drawn at normal size.
- Fixed the Igni and Aard Sweep tier 4 roots overwriting the tier 2 Igni and Aard roots because they shared the same id.
