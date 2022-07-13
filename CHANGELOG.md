# Change Log

## Version 1.16.5-1.3.0
- Updated forge minimum to recommended 36.2.31
- Fixed bug with Tetra enchantment causing crash on a server
- Added AoE defense to full set of armour (Armour Burst)

## Version 1.16.5-1.2.0
- Updated forge minimum to recommended 36.1.0
- Removed some dependency info, this should solve the start up issue with incompatible versions

## Version 1.16.5-1.1.1
- Fixed error in config names (only affects servers, only a name change should not affect anything but refresh the config file just incase)
- set base forge version to 34 so mod can be used on 1.16.3 (Mod version will still be 1.16.5 but should now work on 1.16.3 as well)

## Version 1.16.5-1.1.0
- Bump to version 1.16.5

## Version 1.16.4-1.0.2
### Fixes
- Fixed server crash when wearing full armour set

## Version 1.16.4-1.0.1
### Modifications
- Recipe changes to use forge tags (Thanks ModernGamingWorld!)

## Version 1.16.4-1.0.0
### Additions
- Added Vacuum Minecart (Chest minecart that will collect nearby items)
- Server-side configuration for enchant, ore grinder uses and vacuum minecart radius

### Modifications
- Armour now provides Fire Resistance if full set is worn
- Any item made from shockmetal now immune to fire damage
- Patchouli book now has entries for most items / blocks (WIP)
- Patchouli book now used Ore grinder in recipe
- Grenade damage increased slightly

## Version 1.16.4-0.3.3
### Modifications
- Altered armour toughness from 2.5 to 3
- Grenade does not apply damage to zombie pigmen when transforming pigs (explosive damage still applied though)

### Fixes
- Fixed missing shockmetal dust recipe with ore grinder

### Additions
- Added crushing recipe for shockmetal ingot to dust for Mekanism crusher

## Version 1.16.4-0.3.2
### Fixes
- Fixed Issue with incorrect and missing recipes for ore grinder
- Fixed texture issues with armour models

### Additions
- Added Atom Ripper enchantment, this will provide the same effects as the sword but without the random aspect. when applied to the sword it removes the chance effect.
- Added shockmetal improvement to tetra material, this will provide the same effects as the sword for all tetra tools.

### Modifications
- Ore grinder reduced to 50 uses


## Version 1.16.4-0.3.1
### Fixes
- Fixed Issue with tetra language file

## Version 1.16.4-0.3.0
**There are some recipe changes in this version, before updating please remove all ore and dusts from furnaces to avoid any issues**

Note: This is an early version for 1.16.4. It's been a long time since I made this mod and quite a lot has changed.
Some yet to be updated features include:
- Configuration

No server testing has been conducted so please report any issue you are having if you play multiplayer with this mod installed please :)

### Additions
#### Mod Integration
- Added Patchouli data for in-game documentation WIP (Probably won't make a custom book for the documentation, this is much easier to maintain. I'll still update github wiki with information )
- Added compatibility with Tetra (WIP)

#### Items
- Added shockrite dust
- Added netherite dust
- Added ore grinder (can turn items into there dust equivalents, based on tags so should work with other mods that add tagged dusts)

#### Tools / Weapons
- Added shock grenade

#### Recipes
- Added recipe for netherite dust

[comment]: <0.4.0> (- Added recipe for creating common dusts with ore grinder &#40;Iron, Gold, Copper, Tin, Aluminium, Lead, Silver, Redstone, Coal, Diamond&#41;)

### Modifications
#### Recipes
- Ore now has to be crushed with manual grinder and netherite dust to make 2 shockrite dust, this can then be smelted into shockmetal ingot.
- shockmetal ore can no longer be smelted
- shockmetal ingot made from smelting shockrite dust or shockmetal dust

#### Tools / Weapons
- All tools and armour immune to fire like netherite
- Sword now has a decreased enchantability compared to other tools (It has a special effect anyways)
- Sword can now preform AoE attack by sneaking and right clicking. Damage is based on a charge level. when you hit an entity the charge increase by 1.

#### Textures
- updated shockmetal ore texture with new netherack texture, also has new animated texture
- shockmetal ingot has new animated texture, more inline with other ingots

---

## Version 1.16.4-0.2.0
Note: This is an early version for 1.16.4. It's been a long time since I made this mod and quite a lot has changed.
Some yet to be updated features include:
- Configuration Screen
- Intractable Blocks
- Mod Integration (Mainly waiting for mods to be released for 1.16.4)
- Mod Guide

No server testing has been conducted so please report any issue you are having if you play multiplayer with this mod installed please :)


### Modifications
#### Mod Integration (Thermal Series)
- Added dust recipe for Shock Metal Ore
- Added dust recipe for Shock Metal Ingot

### Fixes
- Fixed Shock Metal Ore smelting recipe missing

---

## Version 1.16.4-0.1.0
Note: This is an early version for 1.16.4. It's been a long time since I made this mod and quite a lot has changed.
Thus, this update is just to get the basics working until I have enough time to fully fix everything. Some yet to be
updated features include:
- Configuration Screen
- Intractable Blocks
- Mod Integration (Mainly waiting for mods to be released for 1.16.4)
- Mod Guide

With the release of netherite I also plan to re-evaluate this mod. for now this provides a tier above netherite.

### Modifications
- Updated to Minecraft 1.16.4 - Forge 1.35.1.4+
#### World Gen
- Shock Metal Ore now found below Y=20 and in groups of upto 4 blocks. (Similar to Acient Debris)
#### Tools / Weapons
- Sword now has a 20% chance to set undead on fire and apply 5s Restoration effect to player
- Slight increase in durability to all tools and weapons
- Armour has knockback resistance same as Netherite
### Additions
#### Items
- Recipe to turn Shock Metal blocks back into ingots
- Dust can now be smelted into ingots (No recipe for dust atm)

---

## Version 1.1.1
### Fixes
- Fixed issue with Shockmetal ore harvest level ( for example, cobalt pickaxe being unable to harvest block )
---
## Version 1.1.0
### Additions
- Shock metal dust with ore dictionary (Any mod that supports dusts with oreDict should now show recipes for it)
- Framed item including
 Textures
 Crafting recipe
 MM Recipe
- Manual (incomplete content)	 
- Animated texture for Shock metal dust		 
- Added Molecular Manipulator (WIP)		 
- Configuration file added    
### Modifications
- Shock metal ingot now has new animated texture
- Shock metal sword now does more damage and sets un-dead mobs on fire
- Ore chance reduced from 10% to 5%
- Refactor of Integration system
- Better Tinkers' Integration (Materials, Part smelting, weapon trait)
---
## Version 1.0.1
### Fixes
 - Fixed tinkers' recipe now working

