package engine.mod.gradle.extension;

import engine.mod.gradle.extension.artifact.EngineArtifactSettings;
import engine.mod.gradle.run.RunConfig;
import engine.mod.gradle.run.DefaultRunConfig;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public abstract class EngineGradleExtension {

    public final EngineArtifactSettings artifact = new EngineArtifactSettings();

    private final List<RunConfig> runConfigs = new ArrayList<>();

    public abstract void artifact(@DelegatesTo(EngineArtifactSettings.class) Closure<Void> configurator);

    public abstract void runs(@DelegatesTo(RunConfig.class) Closure<Void> configurator);

    public abstract void run(RunConfig config);

    public abstract void module(String name);

    public void version(String version) {
        artifact.version(version);
    }

    public void run(String name, @DelegatesTo(RunConfig.class) Closure<Void> configurator) {
        run(new DefaultRunConfig(name));
    }

    public List<RunConfig> getRunConfigs() {
        return runConfigs;
    }
}
