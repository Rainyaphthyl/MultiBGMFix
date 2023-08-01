# Multi BGM Fix

Fix the multi-BGM overlapping bug.

## Description

This is a client-side mod for Minecraft 1.12.2, powered by liteloader.

## Usage

### Deployment

1. Confirm that your Minecraft version is `Java Edition 1.12.2`. If you want something in versions of `1.13+`,
   press `ALT + F4`.
2. Ensure that you have installed a liteloader for your Minecraft client.
3. Download the latest release, which should be a LITEMOD file `mod-multibgmfix-<version>-mc1.12.2.litemod`.
4. Move the file into the `.minecraft/mods/` directory of your game, and then launch the game.

### Features and Functions

- [ChangeLog](ChangeLog.md)

This mod fixes [MC-35856](https://bugs.mojang.com/browse/MC-35856)
and [MC-102403](https://bugs.mojang.com/browse/MC-102403).

## Development

### Setup and Edit

- Clone the repository.
- You MAY have to run `gradle setupDecompWorkspace`.

### Build and Release

Before release, ensure the mod version is correct:

- [gradle.properties](gradle.properties)
- [LiteModMultiBGMFix.java](src/main/java/io/github/rainyaphthyl/multibgmfix/LiteModMultiBGMFix.java)

Run your task by one of the following commands:

- Run `gradle build`, and the release will be a file with postfix `.litemod` in the `build/libs/` directory.
- Run `gradle runClient` to test the mod on the IDE.
