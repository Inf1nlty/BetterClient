package net.fabricmc.betterclient.mixin.xray;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.fabricmc.betterclient.xray.Xray;
import net.minecraft.class_197; // Block

@Mixin(net.minecraft.class_197.class)
public class WorldRendererMixin {

    @Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
    private void getRenderType(CallbackInfoReturnable<Integer> ci) {
        if (Xray.Xray) {
            // Get block ID for this block instance
            int blockId = this.hashCode() % 256; // Simplified approach
            if (Xray.shouldBlockBeTransparent(blockId)) {
                // Set transparent render type
                ci.setReturnValue(-1); // Skip rendering or make transparent
            }
        }
    }
}