package engine.mod.gradle.extension;

import engine.mod.gradle.extension.artifact.EngineArtifactSettings;
import engine.mod.gradle.run.AbstractRunConfig;
import engine.mod.gradle.run.ClientRunConfig;
import engine.mod.gradle.run.ServerRunConfig;
import groovy.lang.Closure;
import groovy.lang.MissingPropertyException;
import org.gradle.api.JavaVersion;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginExtension;

public class EngineGradleExtensionImpl extends EngineGradleExtension {

    final Project project;
    boolean shouldSetJavaVersion = true;

    public EngineGradleExtensionImpl(Project project) {
        this.project = project;
        project.afterEvaluate(project1 -> {
            { // Add JitPack repository
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
                if (shouldSetJavaVersion) {
                    JavaPluginExtension java = project.getExtensions().getByType(JavaPluginExtension.class);
                    java.setSourceCompatibility(JavaVersion.VERSION_11);
                    java.setTargetCompatibility(JavaVersion.VERSION_11);
                }
            }
            { // Add engine dependencies
                if (artifact.addAnnotationProcessor) {
                    addEngineDependency(JavaPlugin.ANNOTATION_PROCESSOR_CONFIGURATION_NAME, "mod-annotation-processor");
                }
                for (String module : artifact.engineModules) {
                    addEngineDependency(module);
                }
            }
            { // Add lwjgl natives
                if (artifact.addLWJGLNatives) {
                    for (String module : artifact.lwjglNativesModules) {
                        project.getDependencies().add(JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME, "org.lwjgl:" + module
                                + ":" + artifact.lwjglNativesVersion + ":" + artifact.lwjglNativesPlatform);
                    }
                }
            }
        });
        run(new ClientRunConfig());
        run(new ServerRunConfig());
    }

    @Override
    public void artifact(Closure<Void> configurator) {
        project.configure(artifact, configurator);
    }

    @Override
    public void runs(Closure<Void> configurator) {
        getRunConfigs().forEach(config -> project.configure(config, configurator));
    }

    @Override
    public void run(AbstractRunConfig config) {
        getRunConfigs().add(config);
        config.apply(project);
    }

    private void addEngineDependency(String module) {
        addEngineDependency(artifact.dependencyConfiguration, module);
    }

    private void addEngineDependency(String configuration, String module) {
        if (artifact.version == null) {
            throw new MissingPropertyException("The version of engine not defined. Add 'engine{ version 'xxx' }' to build.gradle");
        }
        project.getDependencies().add(configuration, artifact.dependencyFormat.format(module, artifact.version));
    }
}
