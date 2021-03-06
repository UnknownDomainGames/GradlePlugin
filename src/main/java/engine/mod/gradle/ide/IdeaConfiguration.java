package engine.mod.gradle.ide;

import engine.mod.gradle.run.RunConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.gradle.api.Project;
import org.gradle.plugins.ide.idea.model.IdeaModel;
import org.gradle.plugins.ide.idea.model.IdeaModule;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class IdeaConfiguration {

    public static void apply(Project project) {
        IdeaModel model = project.getExtensions().getByType(IdeaModel.class);
        IdeaModule module = model.getModule();
        module.getExcludeDirs().addAll(project.files(".gradle", ".idea", "out").getFiles());
        module.getExcludeDirs().add(project.getBuildDir());
        module.setDownloadJavadoc(true);
        module.setDownloadSources(true);
        module.setInheritOutputDirs(true);
    }

    public static void generateRunConfig(Project project, RunConfig config) {
        try {
            File runConfigsDir = new File(project.getRootProject().file(".idea"), "runConfigurations");
            if (!runConfigsDir.getParentFile().exists()) {
                project.getLogger().trace(".idea not found, IDEA run configurations will not generated");
                return;
            }
            if (!runConfigsDir.exists() && !runConfigsDir.mkdirs())
                throw new IOException("runConfigurations directory does not exist and unable to create");

            String configName = config.getIdeConfigurationBaseName()
                    + ((project.getRootProject() == project) ? "" : " " + project.getPath().replace(':', '_'));
            File configFile = new File(runConfigsDir, configName + ".xml");
            String configData = IOUtils.resourceToString("idea_run_config_template.xml", StandardCharsets.UTF_8, IdeaConfiguration.class.getClassLoader())
                    .replace("%NAME%", configName)
                    .replace("%MAIN_CLASS%", config.getMainClass())
                    //.replace("%ECLIPSE_PROJECT%", project.getExtensions().getByType(EclipseModel.class).getProject().getName())
                    .replace("%IDEA_MODULE%", project.getExtensions().getByType(IdeaModel.class).getModule().getName() + ".main")
                    .replace("%ARGS%", String.join(" ", config.getArgs()).replaceAll("\"", "&quot;"))
                    .replace("%JVM_ARGS%", String.join(" ", config.getJvmArgs()).replaceAll("\"", "&quot;"));

            if (!configFile.exists()) {
                FileUtils.writeStringToFile(configFile, configData, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
