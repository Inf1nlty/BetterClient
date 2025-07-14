package net.fabricmc.betterclient.mixin.xray;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.fabricmc.betterclient.xray.Xray;
import net.minecraft.class_197; // Block
import net.minecraft.class_1158; // IBlockAccess

@Mixin(net.minecraft.class_197.class)
public class BlockMixin {

    @Inject(method = "shouldSideBeRendered", at = @At("RETURN"), cancellable = true)
    private void shouldSideBeRendered(net.minecraft.class_1158 blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide, CallbackInfoReturnable<Boolean> ci) {
        if (Xray.Xray) {
            Xray.shouldSideBeRendered(blockAccess, iNeighborI, iNeighborJ, iNeighborK, iSide, ci);
        }
    }
}