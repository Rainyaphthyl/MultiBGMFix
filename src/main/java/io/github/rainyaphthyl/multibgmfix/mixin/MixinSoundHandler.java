package io.github.rainyaphthyl.multibgmfix.mixin;

import io.github.rainyaphthyl.multibgmfix.config.ModSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import paulscode.sound.SoundSystem;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Mixin(SoundHandler.class)
public abstract class MixinSoundHandler {
    @Unique
    private static final Logger multiBGMFix$LOGGER = LogManager.getLogger();
    @Unique
    private boolean multiBGMFix$neverMissed = true;

    @Unique
    private static SoundSystem multiBGMFix$getSoundSystem(SoundManager sndManager) {
        Field[] fields = SoundManager.class.getDeclaredFields();
        Field sysField = null;
        for (Field vField : fields) {
            Class<?> vType = vField.getType();
            if (SoundSystem.class.isAssignableFrom(vType)) {
                sysField = vField;
                break;
            }
        }
        SoundSystem soundSystem = null;
        if (sysField != null) {
            boolean accessible = sysField.isAccessible();
            sysField.setAccessible(true);
            try {
                Object sndSystemObj = sysField.get(sndManager);
                if (sndSystemObj instanceof SoundSystem) {
                    soundSystem = (SoundSystem) sndSystemObj;
                }
            } catch (IllegalAccessException e) {
                multiBGMFix$LOGGER.error(e.getMessage(), e);
            }
            sysField.setAccessible(accessible);
        }
        return soundSystem;
    }

    @Redirect(method = "stopSounds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/audio/SoundManager;stopAllSounds()V"))
    public void reloadOnStopSounds(SoundManager sndManager) {
        if (ModSettings.INSTANCE.enabled) {
            Minecraft minecraft = Minecraft.getMinecraft();
            if (minecraft.world != null && sndManager instanceof AccessSoundManager) {
                if (!multiBGMFix$neverMissed) {
                    sndManager.reloadSoundSystem();
                    multiBGMFix$neverMissed = true;
                    return;
                }
                SoundSystem soundSystem = multiBGMFix$getSoundSystem(sndManager);
                Map<String, ISound> playingSounds = ((AccessSoundManager) sndManager).getPlayingSounds();
                boolean successful = soundSystem != null;
                Set<Map.Entry<String, ISound>> entrySet = playingSounds.entrySet();
                Iterator<Map.Entry<String, ISound>> iterator = entrySet.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, ISound> entry = iterator.next();
                    ISound iSound = entry.getValue();
                    Sound sound = iSound.getSound();
                    // minimum necessary fixing
                    if (sound.isStreaming() || iSound.canRepeat()) {
                        String sourceKey = entry.getKey();
                        if (successful) {
                            soundSystem.stop(sourceKey);
                            soundSystem.removeSource(sourceKey);
                            multiBGMFix$LOGGER.info("Remove source '{}' -> '{}'", sourceKey, sound.getSoundAsOggLocation());
                            iterator.remove();
                        } else {
                            sndManager.reloadSoundSystem();
                            return;
                        }
                    }
                }
            }
        } else if (multiBGMFix$neverMissed) {
            multiBGMFix$neverMissed = false;
        }
        sndManager.stopAllSounds();
    }
}
