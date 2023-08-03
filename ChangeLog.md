# Multi BGM Fix 0.0.1

This is a **pre-release** version.

## Info

### Minecraft Versions

- Supported Minecraft version: `1.12.2`

### Mod Versions

- Current version: `0.1.0`
- Previous version: `0.0.1`
- Previous release version: `null`

## Abstract

## New Features

### Spectator BGM

| config key              | config display                              | 
|-------------------------|---------------------------------------------|
| `spectator-as-creative` | Play creative music for permitted spectator |

Creative music is enabled to be played during spectator mode (in overworld), if the player is permitted enough as a "creative" player.

For example, when the player in creative mode presses `F3 + N`, if the conversion to spectator mode is successful, the music will not stop immediately.

Otherwise, the "survival" music will be played, if the spectating player is considered a "visitor" in a survival world (i.e. without OP permissions).

## Modified Features

- Config `Enable the Fix` is renamed to `Fix the overlapping music` (where the key is `enable-mod`).

## Code Changes
