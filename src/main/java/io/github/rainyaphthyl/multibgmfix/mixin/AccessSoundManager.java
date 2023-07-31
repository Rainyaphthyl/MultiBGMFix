package io.github.rainyaphthyl.multibgmfix.mixin;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(SoundManager.class)
public interface AccessSoundManager {
    @Accessor(value = "playingSounds")
    Map<String, ISound> getPlayingSounds();
}
