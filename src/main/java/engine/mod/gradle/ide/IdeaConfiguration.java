package engine.mod.gradle.ide;

import engine.mod.gradle.run.AbstractRunConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.gradle.api.Project;
import org.gradle.plugins.ide.idea.model.IdeaModel;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class IdeaConfiguration {

    public static void apply(Project project) {
        IdeaModel model = project.getExtensions().getByType(IdeaModel.class);
        model.getModule().getExcludeDirs().addAll(project.files(".gradle", "build", ".idea", "out").getFiles());
        model.getModule().setDownloadJavadoc(true);
        model.getModule().setDownloadSources(true);
        model.getModule().setInheritOutputDirs(true);
    }

    public static void createRunConfig(Project project, AbstractRunConfig config) {
        try {
            File runConfigsDir = new File(project.getRootProject().file(".idea"), "runConfigurations");
            assert runConfigsDir.exists() || runConfigsDir.mkdirs();

            String configName = "Engine " + config.getName()
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