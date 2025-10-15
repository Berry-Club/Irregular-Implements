# 1.5.0

### Added

- Advanced Redstone Interface

### Changed

- Renamed the Basic Redstone Interface block entity id from `redstone_interface` to `basic_redstone_interface`
  - This is a breaking change, any existing Redstone Interfaces will have their data lost
- When holding a Redstone Tool, linked positions show as blue instead of red now
  - That is, the block the Tool is linked to is still red. Blocks that the linked blook is linked to are blue
- When holding a Redstone Tool, a line links the linked block to the block it's linked to
  - For example, connecting betwen a Redstone Interface and the block it's sending a signal to

### Fixed

- Indicator Cubes now always render in the correct place (#18, #12)
- The Redstone Tool would show an indicator on the linked block twice
- When a Redstone Interface changes what it's linked to, it now updates both the old and new linked blocks
- The Redstone Observer and Basic Redstone Interface now properly update on the client when their linked position changes

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