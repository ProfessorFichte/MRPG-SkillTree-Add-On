# 1.2.0 - 1.21.1
** New Content **
- Added a new layout for Weapon Skills & the Spell Skill Tree (Thanks LeDok!)
- Added Nodes for all the new Spell Expansion Spells
- Added Weapon Skill Modifiers for RPG Series Plus / More RPG Classes Weapons
- The Bard and Witcher Class now also received their own Skill Tree Branches!
**Technical Changes and Reworks**
- Adopt Spell Engine 1.10.* & Skill Tree (RPG-Series) Version 1.6.*
- Remove Forgified Fabric API (FFAPI) as a dependency
- The Witcher Passive and Modifiers Files are located in the Witcher mod itself, if you want to tweak those
- "Second Wave" Water passive: casting a Water spell now launches a ground-hugging wave projectile that pierces every target, damaging and knocking them back
- Cleave (Berserker): now a 30% chance on melee hits to deal flat bonus damage, instead of a 20% chance to stack Grievous Wounds
- Bloodfrenzy (Berserker): melee hits against Bleeding targets now grant Bloodflow (attack damage buff), instead of a chance to inflict Bleeding and self-heal
- Whirling Storm / "Strongwind" (Air): rolling now summons a real twister entity that chases and damages enemies, instead of a one-shot wind burst
- Deadly Precision (Berserker): Bloody Strike now deals additional damage as a percentage of the target's max health, instead of a flat damage increase
- Savage Outrage (Berserker): reworked into a modifier that gives Outrage +30% critical strike damage, instead of a passive that dealt AoE damage on cast
- Norse Blood Ritual (Berserker): nearby Bleeding targets now receive Grievous Wounds, instead of flat AoE damage
- Frost Stalker (Tundra Hunter): hitting a Frosted target with Frozen Shot now grants you a movement speed buff (Hunting Fever), instead of extending Frosted's duration
- Scald / "Hydro Boost" (Water): Hydro Beam now sets enemies ablaze and applies Scald (a custom debuff reducing spell power, attack damage & ranged weapon damage by 10% per stack, up to 3 stacks), instead of granting allies movement speed
- Apocalyptic Tornado / "Negative Pressure" (Air): Tornado now grows in size and deals damage in a wider area, instead of a critical damage bonus
- Powerful Asalraalaikum (Forcemaster): now grants +15% critical strike chance, instead of a flat damage increase
- Sharp Dripstones (Earth): now grants Terra Circle +10% critical strike chance, instead of a flat damage increase
- Magnitude 10 (Earth): now increases Earthquake's duration, instead of its range
- Arcane Regeneration (Forcemaster): killing a target with Asalraalaikum now heals you, instead of reducing its cooldown
- Recurring Burst / "Powerful Burst" (Knuckle weapon skill): reworked into a passive that fires a second Burstcrack shortly after, instead of a flat critical chance modifier
- Wounding Shot (Deadeye): Disabling Shot now has a 35% chance to stun the target, instead of inflicting Grievous Wounds on debuffed targets
- Bleeding nodes (Barbed Arrows, Blood Fists, Bloodfrenzy, Slicing Maelstorm, ...) now apply Spell Engine's own Bleed effect instead of the More RPG Classes one

# 1.1.2 - 1.21.1
- Fixed a crash due to a wrong model_id path in the tundra_hunter_spec_a_passive_1 spell

# 1.1.1 - 1.21.1
-  Github Issue: #5 Console log Spam with Blind with Rage: Fixed the Log Spam of the Blind with Rage Effect, because of a wrong particle id

# 1.1.0 - 1.21.1
- Update for the new Spell Engine 1.9 API Changes

# 1.0.8 - 1.21.1
- Add Logger with Instructions for NeoForge Servers where the Skill Tree Changes don't show up
- Fix MRPGC Skill Node Descriptions not being shown
- Fix Force Release Translation
- Add Tenacity Attribute to Ragnarok, Sury's Tenacity & Terrain Mastery

# 1.0.7 - 1.21.1
- Bump tweaks config version because of several issues
- Attempt to fix Skill Tree Change snot loading on Neoforge Server

# 1.0.6 - 1.21.1
- Update for new MRPG-Lib Version

# 1.0.5 - 1.21.1
- increase range of spinning slash and slightly increase its damaging duration
- Fix War Archer Spec A Passive
- Slightly Nerf Splish Splash Modifier for the Water Whip Spell
- Better scaling for Poison Skill Nodes from the Deadeye Class
- Fix Resourcepack not loading for Neoforge

# 1.0.4 - 1.21.1
- Move to Architectury Enviroment for Multiloader
- NeoForge Beta!
- add some missing status effects icons
- improve some status effect icons
- add some arrow indicators for buffs & debuffs for status effect icons that look like the spells icons

# 1.0.3 - 1.21.1
- Increase High Water pressure cooldown duration, nerf stun duration & trigger chance
- Fix Earthen Blessing not stacking
- Fix Norse Blood Ritual not working

# 1.0.2 - 1.21.1
- Fix wrong War Archer Node Connection
- Change confusing named Disable-Config
- Nerf Combustive Shot (War-Archer Spell Modifier 4)
- Add Cooldown to soothing mist

# 1.0.1 - 1.21.1
- The Skill Tree now still loads, if you do not have all the required classes installed

# 1.0.0 - 1.21.1
- Initial Release