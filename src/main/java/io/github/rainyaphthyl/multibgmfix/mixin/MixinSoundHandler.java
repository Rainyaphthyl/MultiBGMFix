package io.github.rainyaphthyl.multibgmfix.mixin;

import io.github.rainyaphthyl.multibgmfix.config.ModSettings;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SoundHandler.class)
public abstract class MixinSoundHandler {
    @Redirect(method = "stopSounds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/audio/SoundManager;stopAllSounds()V"))
    public void reloadOnStopSounds(SoundManager soundManager) {
        if (ModSettings.INSTANCE.enabled) {
            soundManager.reloadSoundSystem();
        } else {
            soundManager.stopAllSounds();
        }
    }
}
