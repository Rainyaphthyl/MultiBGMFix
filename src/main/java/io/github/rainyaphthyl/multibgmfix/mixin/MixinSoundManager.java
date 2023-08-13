package io.github.rainyaphthyl.multibgmfix.mixin;

import io.github.rainyaphthyl.multibgmfix.util.GenericHelper;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;
import java.util.Map;

@Mixin(SoundManager.class)
public abstract class MixinSoundManager {
    @Shadow
    @Final
    private Map<ISound, String> invPlayingSounds;

    @Inject(method = "playSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/audio/SoundManager$SoundSystemStarterThread;newStreamingSource(ZLjava/lang/String;Ljava/net/URL;Ljava/lang/String;ZFFFIF)V"))
    public void debugMusicPlaying(@Nonnull ISound p_sound, CallbackInfo ci) {
        Sound sound = p_sound.getSound();
        String sourceKey = invPlayingSounds.get(p_sound);
        GenericHelper.LOGGER.info("Add source '{}' -> '{}'", sourceKey, sound.getSoundAsOggLocation());
    }
}
