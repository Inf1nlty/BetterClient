package net.fabricmc.betterclient.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.fabricmc.betterclient.imixin.GameSettingsAccessor;
import net.fabricmc.betterclient.xray.Xray;
import net.minecraft.class_327; // KeyBinding
import net.minecraft.class_347; // GameSettings

@Mixin(net.minecraft.class_347.class)
public class GameSettingsMixin implements GameSettingsAccessor {
    
    @Shadow
    public net.minecraft.class_327[] field_945; // keyBindings array
    
    // Enhanced key bindings
    public net.minecraft.class_327 keyBindingHoldLeft;
    public net.minecraft.class_327 keyBindingHoldRight;
    public net.minecraft.class_327 keyBindingXray;
    
    // X-ray specific fields
    private boolean renderXray = false;
    
    @Inject(method = "<init>", at = @At("TAIL"))
    public void initKeybindings(CallbackInfo ci) {
        // Initialize X-ray key binding - X key is 45
        keyBindingXray = new net.minecraft.class_327("X-ray", 45);
        
        // Initialize other key bindings if needed
        keyBindingHoldLeft = new net.minecraft.class_327("Hold Left", 0);
        keyBindingHoldRight = new net.minecraft.class_327("Hold Right", 0);
    }
    
    @Override
    public net.minecraft.class_327 betterClient$getKeyBindingHoldLeft() {
        return keyBindingHoldLeft;
    }
    
    @Override
    public net.minecraft.class_327 betterClient$getKeyBindingHoldRight() {
        return keyBindingHoldRight;
    }
    
    @Override
    public net.minecraft.class_327 betterClient$getKeyBindingXray() {
        return keyBindingXray;
    }
    
    @Override
    public void betterClient$toggleChunkBordersDisplay() {
        // Existing chunk borders functionality
    }
    
    @Override
    public boolean betterClient$getRenderChunkBorders() {
        return false; // Implementation needed
    }
    
    @Override
    public void betterClient$toggleBrightnessDisplay() {
        // Existing brightness functionality
    }
    
    @Override
    public boolean betterClient$getRenderBrightness() {
        return false; // Implementation needed
    }
    
    @Override
    public void betterClient$toggleXray() {
        renderXray = !renderXray;
        Xray.Xray = renderXray;
        
        // Force world refresh for immediate effect
        try {
            net.minecraft.class_1600 mc = net.minecraft.class_1600.method_2965();
            if (mc != null && mc.field_3762 != null) {
                // Trigger chunk refresh
                mc.field_3762.method_1608();
            }
        } catch (Exception e) {
            // Handle any issues silently
        }
    }
    
    @Override
    public boolean betterClient$getXrayEnabled() {
        return renderXray;
    }
}