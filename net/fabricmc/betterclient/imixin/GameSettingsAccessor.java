package net.fabricmc.betterclient.imixin;

import net.minecraft.class_327; // KeyBinding

public interface GameSettingsAccessor {
    net.minecraft.class_327 betterClient$getKeyBindingHoldLeft();
    net.minecraft.class_327 betterClient$getKeyBindingHoldRight();
    net.minecraft.class_327 betterClient$getKeyBindingXray();
    
    void betterClient$toggleChunkBordersDisplay();
    boolean betterClient$getRenderChunkBorders();
    
    void betterClient$toggleBrightnessDisplay();
    boolean betterClient$getRenderBrightness();
    
    // Add X-ray specific methods
    void betterClient$toggleXray();
    boolean betterClient$getXrayEnabled();
}