package engine.mod.gradle.extension;

import engine.mod.gradle.extension.artifact.EngineArtifactSettings;
import engine.mod.gradle.ide.IdeaConfiguration;
import engine.mod.gradle.run.RunConfig;
import engine.mod.gradle.run.ClientRunConfig;
import engine.mod.gradle.run.ServerRunConfig;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.lang.MissingPropertyException;
import org.gradle.api.JavaVersion;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginExtension;

public class EngineGradleExtensionImpl extends EngineGradleExtension {

    final Project project;
    public boolean shouldSetJavaVersion = true;
    public boolean generateIdeRuns = true;

    public EngineGradleExtensionImpl(Project project) {
        this.project = project;
        project.afterEvaluate(project1 -> {
            project.getLogger().trace("Applying Engine Gradle configuration");
            { // Add JitPack repository
                project.getLogger().trace("Adding JitPack repository");
                if (artifact.addJitPackRepo) {
                    project.getRepositories().mavenLocal();
                    project.getRepositories().maven(repo -> {
                        repo.setName(EngineArtifactSettings.JITPACK_REPO_NAME);
                        repo.setUrl(EngineArtifactSettings.JITPACK_REPO_URL);
                    });
                    project.getRepositories().mavenCentral();
                }
            }
            { // Set Java version
                project.getLogger().trace("Setting Java version to 11");
                if (shouldSetJavaVersion) {
                    JavaPluginExtension java = project.getExtensions().getByType(JavaPluginExtension.class);
                    java.setSourceCompatibility(JavaVersion.VERSION_11);
                    java.setTargetCompatibility(JavaVersion.VERSION_11);
                }
            }
            { // Add engine dependencies
                project.getLogger().trace("Adding dependencies");
                if (artifact.addAnnotationProcessor) {
                    project.getLogger().trace("Adding mod annotation processor");
                    addEngineDependency(JavaPlugin.ANNOTATION_PROCESSOR_CONFIGURATION_NAME, "mod-annotation-processor");
                }
                for (String module : artifact.engineModules) {
                    project.getLogger().trace("Adding engine module: " + module);
                    addEngineDependency(module);
                }
            }
            { // Add lwjgl natives
                if (artifact.addLWJGLNatives) {
                    project.getLogger().trace("Adding LWJGL natives, version: " + artifact.lwjglNativesVersion
                            + ", platform: " + artifact.lwjglNativesPlatform);
                    for (String module : artifact.lwjglNativesModules) {
                        project.getLogger().trace("Adding LWJGL natives: " + module);
                        project.getDependencies().add(JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME, "org.lwjgl:" + module
                                + ":" + artifact.lwjglNativesVersion + ":" + artifact.lwjglNativesPlatform);
                    }
                }
            }
            { // Generate IDE runs
                if (generateIdeRuns) {
                    for (RunConfig config : getRunConfigs()) {
                        IdeaConfiguration.generateRunConfig(project, config);
                    }
                }
            }
        });
        run(new ClientRunConfig());
        run(new ServerRunConfig());
    }

    @Override
    public void artifact(@DelegatesTo(EngineArtifactSettings.class) Closure<Void> configurator) {
        project.configure(artifact, configurator);
    }

    @Override
    public void runs(@DelegatesTo(RunConfig.class) Closure<Void> configurator) {
        getRunConfigs().forEach(config -> project.configure(config, configurator));
    }

    @Override
    public void run(RunConfig config) {
        getRunConfigs().add(config);
        config.apply(project);
    }

    @Override
    public void module(String name) {
        artifact.engineModules.add(name);
    }

    private void addEngineDependency(String module) {
        addEngineDependency(artifact.dependencyConfiguration, module);
    }

    private void addEngineDependency(String configuration, String module) {
        if (artifact.version == null) {
            throw new MissingPropertyException("The version of engine is undefined. Add 'engine{ version 'xxx' }' to build.gradle");
        }
        project.getDependencies().add(configuration, artifact.dependencyFormat.format(module, artifact.version));
    }
}
