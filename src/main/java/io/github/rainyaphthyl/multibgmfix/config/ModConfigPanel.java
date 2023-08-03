package io.github.rainyaphthyl.multibgmfix.config;

import com.mumfrey.liteloader.client.gui.GuiCheckbox;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.AbstractConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

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
        if (host == null) {
            return;
        }
        mainSettings = ModSettings.INSTANCE;
        tempSettings.syncFrom(mainSettings);
        int posX = 0;
        int posY = 0;
        int id = 0;
        GuiCheckbox checkboxGlobalSwitch = addControl(new GuiCheckbox(id, posX, posY, I18n.format("multibgmfix.config.name.globalSwitch")),
                control -> {
                    control.checked = !control.checked;
                    tempSettings.enabled = control.checked;
                });
        checkboxGlobalSwitch.checked = tempSettings.enabled;
        ++id;
        posY += GENERAL_HEIGHT;
        GuiCheckbox checkboxSpectator = addControl(new GuiCheckbox(id, posX, posY, I18n.format("multibgmfix.config.name.spectatorAsCreative")),
                control -> {
                    control.checked = !control.checked;
                    tempSettings.spectatorAsCreative = control.checked;
                });
        checkboxSpectator.checked = tempSettings.spectatorAsCreative;
        ++id;
        posY += GENERAL_HEIGHT;
        GuiCheckbox checkboxMessageSwitch = addControl(new GuiCheckbox(id, posX, posY, I18n.format("multibgmfix.config.name.enableMusicMessage")),
                control -> {
                    control.checked = !control.checked;
                    tempSettings.musicMessageEnabled = control.checked;
                });
        checkboxMessageSwitch.checked = tempSettings.musicMessageEnabled;
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
