package engine.mod.gradle.extension;

import engine.mod.gradle.extension.artifact.EngineArtifactSettings;
import groovy.lang.Closure;

@SuppressWarnings("unused")
public abstract class EngineGradleExtension {

    final EngineArtifactSettings artifact = new EngineArtifactSettings();

    public abstract void artifact(Closure<Void> configurator);

    public void version(String version) {
        artifact.version(version);
    }
}
