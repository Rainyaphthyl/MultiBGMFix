package io.github.rainyaphthyl.multibgmfix.mixin;

import io.github.rainyaphthyl.multibgmfix.config.ModSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.util.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

@Mixin(SoundHandler.class)
public abstract class MixinSoundHandler {
    @Redirect(method = "stopSounds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/audio/SoundManager;stopAllSounds()V"))
    public void reloadOnStopSounds(SoundManager sndManager) {
        if (ModSettings.INSTANCE.enabled && sndManager instanceof AccessSoundManager) {
            Minecraft minecraft = Minecraft.getMinecraft();
            if (minecraft.world != null) {
                Map<String, ISound> playingSounds = ((AccessSoundManager) sndManager).getPlayingSounds();
                for (Map.Entry<String, ISound> entry : playingSounds.entrySet()) {
                    ISound iSound = entry.getValue();
                    SoundCategory category = iSound.getCategory();
                    if ((category == SoundCategory.MUSIC || iSound.canRepeat()) && sndManager.isSoundPlaying(iSound)) {
                        sndManager.reloadSoundSystem();
                        return;
                    }
                }
            }
        }
        sndManager.stopAllSounds();
    }
}
