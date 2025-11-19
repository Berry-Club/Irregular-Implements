# 1.7.0

### Added

- Added the `/ii spectre-energy` command
  - Has both a `set <player> <amount>` and a `get <player>` subcommand

### Changed

- The mod now requires [Aaron](https://www.curseforge.com/minecraft/mc-mods/aaron), my new library mod
	- "Aaron" is so much funnier of a name than "Aaron Lib" or "Berry Lib" or whatever
	- The code should also be a lot cleaner, now
- Added a tooltip to certain items if Sodium or Embeddium are installed, saying that it doesn't fully work
	- I'll try to figure out how to make it work with those but my hopes aren't super high
	- This applies to:
		- Spectre Illuminator (doesn't change the block light level, only entities)
		- Divining Rod (doesn't render through blocks, which is kind of the point)
		- Portable Ender Bridge (doesn't render through blocks, which makes it harder to use but it still kind of functions)
- Stopped trying to use localized item tag names, since the class to check if it CAN be localized doesn't exist on the server (#47)
- Spiders now cannot climb on blocks with the block tag `#irregular_implements:super_lubricated` (#48)
- Renamed SavedData files
	- That is, the files stored in `/saves/<world>/data/`
	- Old ones are not deleted, but won't be read from if the new one exists.
		- `ender_letter_inventories` -> `ii_ender_letter_inventories`
        - `floo_network` -> `ii_floo_network`
        - `redstone_handler` -> `ii_redstone_handler`
		- `spectre_coil` -> `ii_spectre_energy`
		- `spectre_cube` -> `ii_spectre_cubes`
		- `spectre_illumination_handler` -> `ii_spectre_illumination`
- Renamed the `/ii fireplace` command to `/ii floo

### Fixed

- Hopefully fixed Advanced Redstone Interface unsetting its linked positions (#49)
- Fixed Advanced Redstone Interface not dropping its items when destroyed (#49)
- Marked the version range required for Kotlin for Forge
- Fixed a stack overflow error when wireless redstone blocks try to listen or talk to other wireless redstone blocks (#51)
	- Technically it works by making them ignore the destination block if it has the block tag `#irregular_implements:ignores_wireless_redstone`
- Fixed an integer overflow in the Spectre Energy Injector

# 1.6.1

### Added

- Russian translation, thanks to BazZziliuS on Github

### Changed

- The Slime Cube now has a default radius of 0, so it only effects the chunk it's in

### Fixed

- The Slime Cube doesn't crash the server when placed now (#43)
	- When making it, I reused a lot of code from the Rain Shield. Unfortunately, it was also checking for the Rain Shield's block property, instead of the Slime Cube's. That's fixed now.
- The Slime Cube actually works now
	- Aside from crashing, it also didn't actually work to begin with. I was packing a ChunkPos into a long and then treating it as a BlockPos, which does not work
- Hopefully fixed a crash when something tries to access the Pitcher Plant's fluid capability while the level was loading, maybe (#41)

# 1.6.0

### Added

- Patchouli documentation
	- I had to update a Patchouli DataGen mod from 1.19 for this lol
	- Not everything is documented, but most of it is. This update was getting a bit inflated and this is the biggest bottleneck, and I don't want to put off releasing all these changes and fixes just because I need to spend 5 more hours documenting every obscure item in the mod lol

### Changed

- Renamed the item tag `#irregular_implements:fluid_tags` to `#irregular_implements:can_stand_on_fluids`, since that's what it's used for
- The function to see if you can stand on a fluid now checks for the `#irregular_implements:can_stand_on_fluids` tag in all armor and curio slots, rather than just feet
	- Currently no non-footwear items use that tag, but now you can add them yourself
	- Note that this also applies to things like Horse Armor!
- The Biome Painter now defaults to having a radius of 2 instead of 0
	- Now it has a default volume of 5x5x5 instead of 1x1x1
- Ender Anchors now have a Block Entity, which allows them to do some more stuff
- When holding a Portable Ender Bridge, Ender Anchors now glow through blocks
- The Portable Ender Bridge now lets you teleport to Ender Anchors even if there's a block in the way
- The Portable Ender Bridge makes a sound when used
- The Ender Bridge now successfully doesn't work if it doesn't work, instead of searching for an infinite distance
	- Previously it would literally just check every (loaded) block that's in front of it, infinitely
	- Now that Anchors have a Block Entity, I can just search if any of those are in front of it instead
	- If there aren't, the Bridge stops "searching" after 5 seconds
	- If there are, it waits for (distance in blocks / Bridge search speed) ticks and then teleports you
- Ender Bridges now work if there's a block between it and the targeted Anchor
- Upon emptying the Summoning Pendulum, it removes the data component entirely instead of just setting it to an empty entity nbt list
- The Slime Cube's chunk radius is now configurable (it was before, but it was using the Rain Shield's radius)
- Inverted Diaphaneous Blocks pulsate at an inverted amplitude as regular ones
- Minor improvements to the Nature Core
	- It no longer spawns mobs in the air
	- It now plants saplings in a circle around it, rather than just on the -X -Z corner

### Fixed

- Massively changed how Spectre Illumination works, which should hopefully fix all its issues and maybe make some more exciting new ones instead
	- It's now a SavedData, synced using packets and stuff
- Added mining particles to Glowing Mushrooms (#32)
- Added an alias to ModBlockEntityTypes for `redstone_interface` -> `basic_redstone_interface` to prevent data loss, so 1.5.0 is no longer a breaking change
- The Escape Rope no longer duplicates itself
- Fixed the Obsidian Skull Ring not doing anything
	- It was looking for an Obsidian Skull in curio slots instead of the Ring
- Fixed the Biome Capsule not using its saved biome for its color
- Spectre Chargers should now charge the entire inventory, including armor, the offhand, and Curio slots (#35)

# 1.5.0

### Added

- Advanced Redstone Interface
- Advanced Redstone Torch

### Changed

- Renamed the Basic Redstone Interface block entity id from `redstone_interface` to `basic_redstone_interface`
	- This is a breaking change, any existing Redstone Interfaces will have their data lost
- When holding a Redstone Tool, linked positions show as blue instead of red now
	- For example, the Redstone Interface would have a red cube, and the block it's linked to would have a blue cube
- When holding a Redstone Tool, a line connects linkable blocks to what they're linked to
	- For example, connecting between a Redstone Interface and the block it's sending a signal to
- When holding a Redstone Tool, you can now see the link for every linkable block, rather than just the one the tool is linked to
- Added a new Colored Flame particle, used by the Biome Radar
- The duration that Trigger Glass stays triggered, as well as how far it propagates, are now configurable

### Fixed

- Indicator Cubes now always render in the correct place (#18, #12)
- The Redstone Tool would show an indicator on the linked block twice
- When a Redstone Interface changes what it's linked to, it now updates both the old and new linked blocks
- The Redstone Observer and Basic Redstone Interface now properly update on the client when their linked position changes
- When a Filtered Platform's inventory changes, it's instantly synced to the client, which prevents item entity desync

# 1.4.0

### Changed

- Added the Forest and Flower Forest to the biome tag `#irregular_implements:nature_core_oak`
- The Notification Interface now defaults to having no text in the title and description
- The Player Interface no longer renders the owner's skull above itself if there's a block there
- Glowing Mushrooms can now be used in _most_ brewing recipes that would use Glowstone (#29)
- Item Collectors now make a sound when picking up items
- Made Lotuses about 6 times more likely to spawn
	- 1 in 50 instead of 1 in 288
	- For reference, Sweet Berries have a 1 in 32 chance to spawn

### Fixed

- Player inventory slots were missing in the Notification Interface (#22)
- Notification Interface icon item not saving correctly
- Unable to type the letter E in Notification Interface text fields (#22)
- Broken particle effect when mining the Player Interface (#24)
- Lava Waders now properly recharge (#30)
- The Advanced Item Collector now properly saves its inventory
- The Advanced Item Collector's GUI now has the Filter slot in the right location (#19)

# 1.3.1

### Changed

- The Returning Block of Sticks now has no pickup delay when it expires and teleports to the nearest player (#13)
- Compressed Slime now fully negates any downward movement an entity may have, before the upwards movement is applied

### Fixed

- Fixed the Spectre Key sending you to a new Cube every time you reloaded the save (#10)
	- So the way it knows which Spectre Cube is yours is that there's a map of Player UUID to Spectre Cube
	- I was saving a compound tag of `{owner:uuid, cube:{cube tag}}`, which makes sense
	- But when loading, I was treating it as a list of cube tags. `SpectreCube.fromTag()` couldn't read the above json, so it assumed that there was no Cube.
	- Honestly, if you've been using the Spectre Key, you might want to delete the old data from your world.
		- Teleport to your old ones and get everything you want to keep out, then delete `/data/spectre_cube.dat` and `/dimensions/irregular_implements/spectre` from your world folder (while the game is off, obviously)
- Fixed several blocks emptying their inventory when its block state changes (#14)
	- This was a problem with the Global Chat Detector, Auto Placer, Biome Radar, Ender Energy Distributor, Imbuing Station, and Filteed Platform
- If the BlockToolModificationEvent is canceled, Slime Blocks will not be turned into Compressed Slime
- Fixed the Analog Emitter outputting a signal out of its input face (#11)
- Made Imbue items act more like Potions, in how they return an empty bottle when used (#17)

# 1.3.0

### Added

- Entity Detector

### Changed

- The notify command now accepts a `players` argument instead of `player`
- Updated the mod logo
- Spirits now correctly drop 1-2 Ectoplasm (#8)
- Spirits are now part of the Creature MobCategory instead of MISC
- Spirits die after 20 seconds, though this timer stops if they have a custom name (#9)
- Grass Seeds must now be used blocks that have the block tag `#irregular_implements:grass_seeds_compatible` instead of using the block tag `#minecraft:dirt` (#6)
	- That tag contains the dirt tag, but now can configure it more if you like
- Nature and Water Chests no longer double up when placed adjacent to each other (#5)
- Localized `+X` etc buttons

### Fixed

- Fixed the Advanced Item Collector's screen saying `-X` instead of `+Z`
- Fixed the Void Stone's screen having the inventory label in the wrong place
- Allowed enchanted items to damage Spirits
- Added some missing localization
- Spirits can now be damaged by damage types with the `#minecraft:bypasses_invulnerability` tag
- Fixed a crash when using a mod that adds more enum values to DyeColor (#7)
- Fixed the Redstone Tool cubes rendering in wildly the wrong place

# 1.2.0

### Added

- `/ii notify <player> <title> <body> <item>` command to send a toast notification to a player

### Changed

- Commands can now be accessed either through `/irregular_implements` or `/ii`
- Renamed `/ii fireplace teleport-to` to just `/ii fireplace teleport`

### Fixed

- Fixed experience orbs never dropping ([#3](https://github.com/Berry-Club/Irregular-Implements/issues/3))
	- When moving blocks with the Block Mover, the game thinks the block was broken normally, and tries to spawn the item and xp
	- I did the logic wrong, though, which is fixed
	- While I was at it, I made it use the entity type tag `#irregular_implements:not_dropped_when_moving_blocks` just in case some mod adds some funky whatever that happens on block breaking
- Fixed crash when certain items expected to have a data component but didn't ([#2](https://github.com/Berry-Club/Irregular-Implements/issues/2))

# 1.1.0

### Changed

- Gave the Rain Shield a model and shape
- Gave the Water Chest and Nature Chest item models
- The White Stone can now charge when dropped as an item, and it floats
- The Escape Rope can no longer be used while the player can see the sky
- The Bottle of Air can no longer be used while the player has full breath
- Added `#irregular_implements:nature_core_immune`, which is both a block tag and entity type tag
	- Prevents Sand blocks from being turned into Grass/Dirt
	- Prevents crops from being bonemealed
	- Prevents entity types from being spawned (keeping in mind that it only *tries* to spawn mobs that can generate in that biome naturally)
- The radii that the Nature Core can change blocks, spawn saplings, or spawn animals are now configurable
- The Nature Core now tries to spawn fish in water blocks
- The Nature Core now tries up to 50 times to locate a valid position to do its actions
- The Nature Core now only ticks on the server
- Removed `#irregular_implements:nature_core_possible_saplings` from the Spectre Sapling

### Fixed

- Fixed a crash when lighting Spectre trees on fire
- Fixed the Divining Rod not actually rendering anything

# 1.0.0

## Added

- Golden Compass
- Biome Radar

### Changed

- Removed the Location component, replaced with a GlobalPos component
- Improved logic for Emerald Compass (which is also used in Golden Compass)
- Backend changes to configs
- Made several container blocks drop their items when broken