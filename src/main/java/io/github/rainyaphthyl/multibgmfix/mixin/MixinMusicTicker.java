package io.github.rainyaphthyl.multibgmfix.mixin;

import io.github.rainyaphthyl.multibgmfix.config.ModSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.*;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(MusicTicker.class)
public abstract class MixinMusicTicker {
    @Shadow
    @Final
    private Minecraft mc;

    @Shadow
    private ISound currentMusic;

    @Inject(method = "playMusic", at = @At(value = "RETURN"))
    public void setMusicPlayingMessage(MusicTicker.MusicType requestedMusicType, CallbackInfo ci) {
        if (!ModSettings.INSTANCE.musicMessageEnabled) {
            return;
        }
        switch (requestedMusicType) {
            case MENU:
            case CREDITS:
                return;
        }
        Sound sound = currentMusic.getSound();
        if (SoundHandler.MISSING_SOUND.equals(sound)) {
            return;
        }
        Map<ISound, String> invPlayingSounds = null;
        SoundHandler soundHandler = mc.getSoundHandler();
        if (soundHandler instanceof AccessSoundHandler) {
            SoundManager soundManager = ((AccessSoundHandler) soundHandler).getSoundManager();
            if (soundManager instanceof AccessSoundManager) {
                invPlayingSounds = ((AccessSoundManager) soundManager).getInvPlayingSounds();
            }
        }
        if (invPlayingSounds != null && invPlayingSounds.containsKey(currentMusic)) {
            String pathName = sound.getSoundLocation().getPath();
            int subIndex = pathName.lastIndexOf('/') + 1;
            String subKey = pathName.substring(subIndex);
            String fullKey = "multibgmfix.music." + subKey;
            StringBuilder stringBuilder = new StringBuilder();
            if (I18n.hasKey(fullKey)) {
                String musicName = I18n.format(fullKey);
                stringBuilder.append(musicName).append(' ');
            }
            stringBuilder.append('(').append(subKey).append(')');
            String fullName = stringBuilder.toString();
            GuiIngame ingameGUI = mc.ingameGUI;
            ingameGUI.setRecordPlayingMessage(fullName);
            if (ingameGUI instanceof AccessGuiIngame) {
                ((AccessGuiIngame) ingameGUI).setOverlayMessageTime(120);
            }
        }
    }
}
