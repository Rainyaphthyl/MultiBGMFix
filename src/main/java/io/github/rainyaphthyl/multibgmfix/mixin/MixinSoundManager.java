package io.github.rainyaphthyl.multibgmfix.mixin;

import io.github.rainyaphthyl.multibgmfix.config.ModSettings;
import io.github.rainyaphthyl.multibgmfix.util.GenericHelper;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundManager;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;
import java.util.Map;

@Mixin(SoundManager.class)
public abstract class MixinSoundManager {
    @Shadow
    @Final
    private Map<ISound, String> invPlayingSounds;
    @Unique
    private boolean multiBGMFix$currStreaming = false;

    @Redirect(method = "playSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/audio/Sound;isStreaming()Z"))
    public boolean markSoundStreaming(@Nonnull Sound sound) {
        boolean streaming = sound.isStreaming();
        if (ModSettings.INSTANCE.enabled && streaming) {
            multiBGMFix$currStreaming = true;
        }
        return streaming;
    }

    @Inject(method = "playSound", at = @At(value = "FIELD", target = "Lnet/minecraft/client/audio/SoundManager;categorySounds:Lcom/google/common/collect/Multimap;", opcode = Opcodes.GETFIELD))
    public void debugStreamingSound(@Nonnull ISound p_sound, CallbackInfo ci) {
        if (multiBGMFix$currStreaming) {
            multiBGMFix$currStreaming = false;
            Sound sound = p_sound.getSound();
            String sourceKey = invPlayingSounds.get(p_sound);
            GenericHelper.LOGGER.info("Add source '{}' -> '{}'", sourceKey, sound.getSoundAsOggLocation());
        }
    }
}
