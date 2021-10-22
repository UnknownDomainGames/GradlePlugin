package engine.mod.gradle;

import engine.mod.gradle.extension.EngineGradleExtension;
import engine.mod.gradle.extension.EngineGradleExtensionImpl;
import engine.mod.gradle.ide.IdeaConfiguration;
import engine.mod.gradle.run.AbstractRunConfig;
import engine.mod.gradle.run.ClientRunConfig;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaLibraryPlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;
import org.gradle.plugins.ide.idea.IdeaPlugin;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class EngineGradlePlugin implements Plugin<Project> {

    public static final String EXTENSION_NAME = "engine";

    private final List<AbstractRunConfig> runConfigs = new ArrayList<>();

    @Override
    public void apply(Project project) {
        { // Other Plugins
            project.getPluginManager().apply(JavaPlugin.class);
            project.getPluginManager().apply(JavaLibraryPlugin.class);
            project.getPluginManager().apply(MavenPublishPlugin.class);
            //project.getPluginManager().apply(EclipsePlugin.class);
            project.getPluginManager().apply(IdeaPlugin.class);
            IdeaConfiguration.apply(project);
        }
        EngineGradleExtension extension = new EngineGradleExtensionImpl(project);
        project.getExtensions().add(EngineGradleExtension.class, EXTENSION_NAME, extension);
        runConfigs.add(new ClientRunConfig());
        runConfigs.forEach(config -> config.apply(project));
    }

    public List<AbstractRunConfig> getRunConfigs() {
        return runConfigs;
    }
}
