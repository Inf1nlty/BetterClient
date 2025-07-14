package net.fabricmc.betterclient.xray;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.class_1158; // IBlockAccess
import net.minecraft.class_197;  // Block

public class Xray {
    public static boolean Xray = false;
    
    // Optimized block lists for better performance
    private static final int[] VALUABLE_BLOCKS = {
        14, // Gold Ore
        15, // Iron Ore
        16, // Coal Ore
        21, // Lapis Ore
        56, // Diamond Ore
        73, // Redstone Ore
        74, // Redstone Ore (lit)
        129, // Emerald Ore (if available in 1.6.4)
        52, // Spawner
        54, // Chest
        146 // Trapped Chest
    };
    
    // Cache for performance optimization
    private static final boolean[] isValuableBlock = new boolean[256];
    
    static {
        // Initialize valuable blocks cache
        for (int blockId : VALUABLE_BLOCKS) {
            if (blockId < isValuableBlock.length) {
                isValuableBlock[blockId] = true;
            }
        }
    }
    
    public static void shouldSideBeRendered(net.minecraft.class_1158 blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide, CallbackInfoReturnable<Boolean> ci) {
        if (!Xray) return;
        
        // Get the neighbor block ID efficiently
        int neighborBlockID = getNeighborBlockID(blockAccess, iNeighborI, iNeighborJ, iNeighborK, iSide);
        
        if (neighborBlockID <= 0 || neighborBlockID >= isValuableBlock.length) {
            ci.setReturnValue(false);
            return;
        }
        
        // Only render sides if it's a valuable block or air
        boolean shouldRender = isValuableBlock[neighborBlockID] || neighborBlockID == 0;
        ci.setReturnValue(shouldRender);
    }
    
    public static int getNeighborBlockID(net.minecraft.class_1158 blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide) {
        switch (iSide) {
            case 0: // DOWN
                --iNeighborJ;
                break;
            case 1: // UP
                ++iNeighborJ;
                break;
            case 2: // NORTH
                --iNeighborK;
                break;
            case 3: // SOUTH
                ++iNeighborK;
                break;
            case 4: // WEST
                --iNeighborI;
                break;
            case 5: // EAST
                ++iNeighborI;
                break;
        }
        
        return blockAccess.method_3774(iNeighborI, iNeighborJ, iNeighborK);
    }
    
    // Method to check if a block should be rendered transparently
    public static boolean shouldBlockBeTransparent(int blockId) {
        if (!Xray) return false;
        return blockId > 0 && blockId < isValuableBlock.length && !isValuableBlock[blockId];
    }
    
    // Method to toggle X-ray with better state management
    public static void toggle() {
        Xray = !Xray;
        // Force a chunk refresh for immediate visual update
        refreshChunks();
    }
    
    // Optimized chunk refresh method
    private static void refreshChunks() {
        try {
            net.minecraft.class_1600 mc = net.minecraft.class_1600.method_2965();
            if (mc != null && mc.field_3762 != null) { // world field
                // Only refresh visible chunks to reduce lag
                mc.field_3762.method_1608(); // markBlockRangeForRenderUpdate or similar
            }
        } catch (Exception e) {
            // Silently handle any reflection issues
        }
    }
}