package io.github.rainyaphthyl.multibgmfix.mixin;

import io.github.rainyaphthyl.multibgmfix.config.ModSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.PlayerCapabilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nonnull;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Shadow
    public EntityPlayerSP player;

    /**
     * Allows permitted spectators to play creative music.
     *
     * @return creative or spectator as a creator
     */
    @Redirect(method = "getAmbientMusicType", at = @At(value = "FIELD",
            target = "Lnet/minecraft/entity/player/PlayerCapabilities;isCreativeMode:Z"))
    public boolean checkCreativeSpectator(@Nonnull PlayerCapabilities capabilities) {
        if (ModSettings.INSTANCE.spectatorAsCreative) {
            boolean flag;
            if (capabilities.isCreativeMode) {
                flag = true;
            } else {
                flag = capabilities.disableDamage && player.canUseCommand(2, "");
            }
            return flag;
        } else {
            return capabilities.isCreativeMode;
        }
    }
}
