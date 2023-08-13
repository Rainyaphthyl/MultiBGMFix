package io.github.rainyaphthyl.multibgmfix;

import com.mumfrey.liteloader.Configurable;
import com.mumfrey.liteloader.InitCompleteListener;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import io.github.rainyaphthyl.multibgmfix.config.ModConfigPanel;
import io.github.rainyaphthyl.multibgmfix.config.ModSettings;
import io.github.rainyaphthyl.multibgmfix.util.version.ModVersion;
import net.minecraft.client.Minecraft;

import java.io.File;

@SuppressWarnings("unused")
public class LiteModMultiBGMFix implements Configurable, InitCompleteListener {
    public static final String NAME = "Multi BGM Fix";
    public static final String VERSION = "0.1.4";
    public static final ModVersion versionObj = ModVersion.getVersion(VERSION);

    public ModSettings getSettings() {
        return ModSettings.INSTANCE;
    }

    /**
     * Get the mod version string
     *
     * @return the mod version as a string
     */
    @Override
    public String getVersion() {
        return VERSION;
    }

    /**
     * Do startup stuff here, minecraft is not fully initialised when this
     * function is called so mods <b>must not</b> interact with minecraft in any
     * way here.
     *
     * @param configPath Configuration path to use
     */
    @Override
    public void init(File configPath) {
        ModSettings.initConfig(configPath);
    }

    /**
     * Called when the loader detects that a version change has happened since
     * this mod was last loaded.
     *
     * @param version       new version
     * @param configPath    Path for the new version-specific config
     * @param oldConfigPath Path for the old version-specific config
     */
    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath) {
    }

    /**
     * Get the display name
     *
     * @return display name
     */
    @Override
    public String getName() {
        return NAME;
    }

    /**
     * Get the class of the configuration panel to use, the returned class must
     * have a default (no-arg) constructor
     *
     * @return configuration panel class
     */
    @Override
    public Class<? extends ConfigPanel> getConfigPanelClass() {
        return ModConfigPanel.class;
    }

    /**
     * Called as soon as the game is initialised and the main game loop is
     * running.
     *
     * @param minecraft Minecraft instance
     * @param loader    LiteLoader instance
     */
    @Override
    public void onInitCompleted(Minecraft minecraft, LiteLoader loader) {
    }
}
