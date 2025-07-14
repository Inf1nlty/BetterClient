#!/bin/bash
# Simple build script for BetterClient X-ray fixes

echo "Building BetterClient with X-ray improvements..."

# Create necessary directories
mkdir -p build/classes

# Set classpath (we'll need to download dependencies)
CLASSPATH="."

# Download required dependencies if they don't exist
if [ ! -f "libs/mixin.jar" ]; then
    echo "Downloading dependencies..."
    mkdir -p libs
    cd libs
    
    # Try to download common Fabric/Mixin dependencies from alternative sources
    curl -L -o fabric-loader.jar "https://github.com/FabricMC/fabric-loader/releases/download/0.14.19/fabric-loader-0.14.19.jar" 2>/dev/null || echo "Could not download Fabric loader"
    
    cd ..
fi

# Find all Java files
echo "Compiling Java sources..."

# Compile in the correct order to handle dependencies
echo "Compiling core classes..."
find . -name "*.java" -not -path "./src/*" | head -20 | while read file; do
    echo "Compiling $file"
    javac -cp "$CLASSPATH" "$file" 2>/dev/null || echo "Failed to compile $file (missing dependencies)"
done

echo "Build attempt completed. Note: Some files may not compile due to missing Minecraft/Mixin dependencies."
echo "This is expected in a development environment without the full Fabric toolchain."