package net.fabricmc.betterclient;

import net.fabricmc.api.ModInitializer;

public class BetterClient implements ModInitializer {
    public static int port = 0;
    public static boolean showCoordinates = false;
    public static boolean showTime = false;
    public static int toggleChunkBordersDisplay = 0;
    public static int toggleBrightnessDisplay = 0;
    
    // X-ray configuration
    public static int xrayToggleKey = 45; // X key by default
    public static boolean xrayEnabled = false;
    
    @Override
    public void onInitialize() {
        // Initialize X-ray settings
        initializeXraySettings();
    }
    
    private void initializeXraySettings() {
        // Load settings from configuration if available
        // For now, use defaults
        xrayToggleKey = 45; // X key
        xrayEnabled = false;
    }
    
    // Methods for configuration management
    public static void setXrayToggleKey(int keyCode) {
        xrayToggleKey = keyCode;
        // Save to configuration
    }
    
    public static int getXrayToggleKey() {
        return xrayToggleKey;
    }
}