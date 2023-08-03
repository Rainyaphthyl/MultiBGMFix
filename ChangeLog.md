# Multi BGM Fix 0.1.0

This is a **pre-release** version.

## Info

### Minecraft Versions

- Supported Minecraft version: `1.12.2`

### Mod Versions

- Current version: `0.1.0`
- Previous version: `0.0.1`
- Previous release version: `null`

## Abstract

- Spectator music as creative.
- Overlays record-like music message.

## New Features

### Spectator BGM

| config key              | config display                 | 
|-------------------------|--------------------------------|
| `spectator-as-creative` | Spectator plays creative music |

Creative music is enabled to be played during spectator mode (in overworld), if the player is permitted enough as a "creative" player.

For example, when the player in creative mode presses `F3 + N`, if the conversion to spectator mode is successful, the music will not stop immediately.

Otherwise, the "survival" music will be played, if the spectating player is considered a "visitor" in a survival world (i.e. without OP permissions).

### Music Message Overlay

| config key             | config display        | 
|------------------------|-----------------------|
| `enable-music-message` | Music message overlay |

When the background music is played, a message starting with "Now playing" will be rendered near the action bar, as if a record (Music Disc) is put into a Jukebox.

The message is rendered with the same animated colors, with a duration longer than the record message.

The message of music in Menu or Credits will not be rendered.

## Modified Features

- Config `Enable the Fix` is renamed to `Fix the overlapping music` (where the key is `enable-mod`).

## Code Changes
