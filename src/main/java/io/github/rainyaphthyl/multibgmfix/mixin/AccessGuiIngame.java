package io.github.rainyaphthyl.multibgmfix.mixin;

import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiIngame.class)
public interface AccessGuiIngame {
    @Accessor(value = "overlayMessageTime")
    void setOverlayMessageTime(int valueIn);
}
