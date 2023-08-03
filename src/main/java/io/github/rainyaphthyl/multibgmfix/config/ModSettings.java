package io.github.rainyaphthyl.multibgmfix.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigStrategy;
import com.mumfrey.liteloader.modconfig.Exposable;
import com.mumfrey.liteloader.modconfig.ExposableOptions;
import io.github.rainyaphthyl.multibgmfix.util.GenericHelper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@ExposableOptions(strategy = ConfigStrategy.Unversioned, filename = ModSettings.FILE_NAME)
public class ModSettings implements Exposable {
    public static final ModSettings INSTANCE = new ModSettings();
    public static final String FILE_NAME = "multibgmfix.json";
    public static final String BACKUP_POSTFIX = ".bak";
    public static final String BACKUP_NAME = FILE_NAME + BACKUP_POSTFIX;
    private static final int FIELD_MASK = Modifier.PUBLIC | Modifier.PROTECTED | Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL | Modifier.TRANSIENT;
    /**
     * The setting field should be {@code public, non-static, non-final, non-transient}
     */
    private static final int FIELD_PATTERN = Modifier.PUBLIC;
    @Expose
    @SerializedName("enable-mod")
    public boolean enabled = true;

    @Expose
    @SerializedName("spectator-as-creative")
    public boolean spectatorAsCreative = true;

    public ModSettings() {
    }

    public ModSettings(Object another) {
        if (another instanceof ModSettings) {
            syncFrom((ModSettings) another);
        }
    }

    public static void initConfig(File configPath) {
        LiteLoader liteLoader = LiteLoader.getInstance();
        boolean registered = false;
        if (configPath != null && configPath.isDirectory()) {
            File configFile = new File(configPath, FILE_NAME);
            File backupFile = new File(configPath, BACKUP_NAME);
            try {
                boolean backupRequiring = configFile.canRead();
                boolean protectRequiring;
                if (backupRequiring) {
                    protectRequiring = backupFile.canRead() && configFile.length() < backupFile.length() * 0.75;
                } else {
                    protectRequiring = backupFile.exists();
                }
                if (protectRequiring) {
                    // Protect the backup if the config is missing
                    Date dateObj = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS.ZZZZ", Locale.CANADA_FRENCH);
                    String dateTxt = dateFormat.format(dateObj);
                    String archiveName = FILE_NAME + "." + dateTxt + BACKUP_POSTFIX;
                    GenericHelper.copyFile(backupFile, new File(configPath, archiveName));
                }
                if (backupRequiring) {
                    if (protectRequiring) {
                        liteLoader.registerExposable(INSTANCE, FILE_NAME);
                        liteLoader.writeConfig(INSTANCE);
                        registered = true;
                    }
                    GenericHelper.copyFile(configFile, backupFile);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (!registered) {
            liteLoader.registerExposable(INSTANCE, FILE_NAME);
        }
    }

    /**
     * @param another The "template" mod setting instance
     * @param silent  Not updating the values when set to {@code true}
     * @return {@code true} if there were any differences between the two setting instances
     */
    public boolean syncFrom(ModSettings another, boolean silent) {
        if (another == null || another == this) {
            return false;
        }
        try {
            boolean different = false;
            Field[] fields = ModSettings.class.getFields();
            for (Field field : fields) {
                if ((field.getModifiers() & FIELD_MASK) == FIELD_PATTERN) {
                    if (field.getAnnotation(Expose.class) != null) {
                        Object aim = field.get(another);
                        Object curr = field.get(this);
                        if (!Objects.equals(curr, aim)) {
                            different = true;
                            if (silent) {
                                return true;
                            } else {
                                field.set(this, aim);
                            }
                        }
                    }
                }
            }
            return different;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Equivalent to {@link ModSettings#syncFrom(ModSettings, boolean)} {@code : syncFrom(another, false)}
     *
     * @param another The "template" mod setting instance
     * @return {@code true} if there were any differences between the two setting instances
     */
    public boolean syncFrom(ModSettings another) {
        return syncFrom(another, false);
    }
}
