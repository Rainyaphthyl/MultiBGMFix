package io.github.rainyaphthyl.multibgmfix.config;

import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.AbstractConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

@SuppressWarnings("unused")
public class ModConfigPanel extends AbstractConfigPanel {
    private static final int GENERAL_HEIGHT = 16;
    private static final int CHECKBOX_HEIGHT = 12;
    private static final int OPTION_INTERVAL = 8;
    private static final int BUTTON_HEIGHT = 20;
    private static final int COLOR_NORMAL = 0xE0E0E0;
    private static final int COLOR_DISABLED = 0xA0A0A0;
    private static final int COLOR_HOVERED = 0xFFFFA0;
    private static final int COLOR_LABEL = 0x00E0E0;
    private final ModSettings tempSettings = new ModSettings(ModSettings.INSTANCE);
    private ModSettings mainSettings = null;

    /**
     * Stub for implementors, this is similar to {@link GuiScreen#initGui} and
     * consumers should add all of their controls here
     *
     * @param host the panel host
     */
    @Override
    protected void addOptions(ConfigPanelHost host) {
    }

    /**
     * Panels should return the text to display at the top of the config panel
     * window.
     */
    @Override
    public String getPanelTitle() {
        return I18n.format("multibgmfix.config.title");
    }

    /**
     * Called when the panel is closed, panel should save settings
     */
    @Override
    public void onPanelHidden() {
        if (mainSettings != null && !mainSettings.equals(tempSettings)) {
            boolean updated = mainSettings.syncFrom(tempSettings);
            if (updated) {
                LiteLoader.getInstance().writeConfig(mainSettings);
            }
            mainSettings = null;
        }
    }
}
