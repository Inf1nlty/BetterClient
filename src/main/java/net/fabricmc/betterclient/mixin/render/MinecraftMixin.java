package net.fabricmc.betterclient.mixin.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.lwjgl.input.Keyboard;
import net.fabricmc.betterclient.BetterClient;
import net.fabricmc.betterclient.imixin.GameSettingsAccessor;
import net.fabricmc.betterclient.xray.Xray;
import net.minecraft.class_1600; // Minecraft

@Mixin(net.minecraft.class_1600.class)
public class MinecraftMixin {
    
    private static boolean xrayKeyPressed = false;
    private static long lastKeyPressTime = 0;
    private static final long KEY_DEBOUNCE_MS = 250; // Prevent rapid toggling
    
    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", ordinal = 4), remap = false)
    public void updateAchievementWindow(CallbackInfo info) {
        // Handle existing functionality
        if (Keyboard.isKeyDown(BetterClient.toggleChunkBordersDisplay)) {
            net.minecraft.class_1600 mc = net.minecraft.class_1600.method_2965();
            ((GameSettingsAccessor) mc.field_3823).betterClient$toggleChunkBordersDisplay();
        }
        
        if (Keyboard.isKeyDown(BetterClient.toggleBrightnessDisplay)) {
            net.minecraft.class_1600 mc = net.minecraft.class_1600.method_2965();
            ((GameSettingsAccessor) mc.field_3823).betterClient$toggleBrightnessDisplay();
        }
        
        // Enhanced X-ray key handling with debouncing
        handleXrayKey();
    }
    
    private void handleXrayKey() {
        net.minecraft.class_1600 mc = net.minecraft.class_1600.method_2965();
        if (mc == null || mc.field_3823 == null) return; // gameSettings field
        
        GameSettingsAccessor settings = (GameSettingsAccessor) mc.field_3823;
        net.minecraft.class_327 xrayKey = settings.betterClient$getKeyBindingXray();
        
        if (xrayKey == null) return;
        
        boolean isXrayKeyDown = xrayKey.field_889; // isPressed field
        long currentTime = System.currentTimeMillis();
        
        // Debounced key press detection
        if (isXrayKeyDown && !xrayKeyPressed && (currentTime - lastKeyPressTime) > KEY_DEBOUNCE_MS) {
            xrayKeyPressed = true;
            lastKeyPressTime = currentTime;
            
            // Toggle X-ray through settings accessor
            settings.betterClient$toggleXray();
            
            // Provide user feedback
            if (mc.field_3811 != null) { // thePlayer field
                String message = settings.betterClient$getXrayEnabled() ? "§aX-ray Enabled" : "§cX-ray Disabled";
                // Try to send message to player - would need proper obfuscated method
                // In 1.6.4, it might be something like addChatComponentMessage
            }
        } else if (!isXrayKeyDown) {
            xrayKeyPressed = false;
        }
    }
}