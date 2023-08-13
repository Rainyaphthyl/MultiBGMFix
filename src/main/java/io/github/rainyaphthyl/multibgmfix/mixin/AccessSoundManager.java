package io.github.rainyaphthyl.multibgmfix.mixin;

import com.google.common.collect.Multimap;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.util.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(SoundManager.class)
public interface AccessSoundManager {
    @Accessor(value = "playingSounds")
    Map<String, ISound> getPlayingSounds();

    @Accessor(value = "invPlayingSounds")
    Map<ISound, String> getInvPlayingSounds();

    @Accessor(value = "categorySounds")
    Multimap<SoundCategory, String> getCategorySounds();

    @Accessor(value = "loaded")
    boolean isLoaded();

    @Accessor(value = "loaded")
    void setLoaded(boolean loadedIn);
}
