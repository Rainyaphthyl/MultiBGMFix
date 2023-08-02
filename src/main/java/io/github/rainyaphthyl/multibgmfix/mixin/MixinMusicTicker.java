package io.github.rainyaphthyl.multibgmfix.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicTicker.class)
public class MixinMusicTicker {
    @Shadow
    @Final
    private Minecraft mc;

    @Shadow
    private ISound currentMusic;

    @Inject(method = "playMusic", at = @At(value = "RETURN"))
    public void setMusicPlayingMessage(MusicTicker.MusicType requestedMusicType, CallbackInfo ci) {
        if (mc.world != null) {
            Sound sound = currentMusic.getSound();
            if (!SoundHandler.MISSING_SOUND.equals(sound)) {
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
}
