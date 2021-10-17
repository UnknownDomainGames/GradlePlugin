package engine.mod.gradle.extension.artifact;

import org.gradle.api.plugins.JavaPlugin;
import org.gradle.internal.os.OperatingSystem;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class EngineArtifactSettings {

    public static final String JITPACK_NAME = "JitPack";
    public static final String JITPACK_URL = "https://jitpack.io/";

    public boolean addJitPack = true;
    public String version = null;
    public String dependencyConfiguration = JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME;
    public ArtifactNotationFormat dependencyFormat = ArtifactNotationFormat.JITPACK;
    public boolean addGameEngine = true;
    public boolean addClient = true;
    public boolean addAnnotationProcessor = true;
    public boolean addLWJGLNatives = true;
    public List<String> lwjglNativesModules = new ArrayList<>(List.of("lwjgl",
            "lwjgl-assimp", "lwjgl-glfw", "lwjgl-opengl", "lwjgl-openal", "lwjgl-shaderc", "lwjgl-stb", "lwjgl-tinyfd", "lwjgl-vma"));
    public String lwjglNativesVersion = "3.2.3";
    public String lwjglNativesPlatform = getCurrentLWJGLNativesPlatform();

    public void version(String version) {
        this.version = version;
    }

    public static String getCurrentLWJGLNativesPlatform() {
        String arch = System.getProperty("os.arch");
        OperatingSystem current = OperatingSystem.current();
        if (current.isLinux()) {
            return arch.startsWith("arm") || arch.startsWith("aarch64")
                    ? "natives-linux-" + (arch.contains("64") || arch.startsWith("armv8") ? "arm64" : "arm32") : "natives-linux";
        } else if (current.isMacOsX()) {
            return "natives-macos";
        } else if (current.isWindows()) {
            return arch.contains("64")
                    ? "natives-windows" + (arch.startsWith(" aarch64 ") ? "-arm64" : "") : "natives-windows-x86";
        } else throw new UnsupportedOperationException(current.toString());
    }
}
