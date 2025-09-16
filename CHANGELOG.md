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

# 1.0.0

## Added

- Golden Compass
- Biome Radar

### Changed

- Removed the Location component, replaced with a GlobalPos component
- Improved logic for Emerald Compass (which is also used in Golden Compass)
- Backend changes to configs
- Made several container blocks drop their items when broken