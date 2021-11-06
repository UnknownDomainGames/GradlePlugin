package engine.mod.gradle.extension;

import engine.mod.gradle.extension.artifact.EngineArtifactSettings;
import engine.mod.gradle.run.AbstractRunConfig;
import groovy.lang.Closure;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public abstract class EngineGradleExtension {

    public final EngineArtifactSettings artifact = new EngineArtifactSettings();

    private final List<AbstractRunConfig> runConfigs = new ArrayList<>();

    public abstract void artifact(Closure<Void> configurator);

    public abstract void runs(Closure<Void> configurator);

    public abstract void run(AbstractRunConfig config);

    public void version(String version) {
        artifact.version(version);
    }

    public List<AbstractRunConfig> getRunConfigs() {
        return runConfigs;
    }
}
