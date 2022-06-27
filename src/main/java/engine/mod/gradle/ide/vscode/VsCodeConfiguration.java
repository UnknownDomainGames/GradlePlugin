package engine.mod.gradle.ide.vscode;

import com.google.gson.Gson;
import engine.mod.gradle.extension.EngineGradleExtension;
import engine.mod.gradle.run.RunConfig;
import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
import org.gradle.plugins.ide.internal.IdePlugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class VsCodeConfiguration {

    public static void generateRunConfig(Project project) {
        try {
            File configFile = new File(project.getRootProject().file(".vscode"), "launch.json");
            if (!configFile.getParentFile().exists()) {
                project.getLogger().trace(".vscode not found, VS Code run configurations will not generated");
                return;
            }

            VsCodeLaunchJson launchJson = new VsCodeLaunchJson();
            for (RunConfig config : project.getExtensions().getByType(EngineGradleExtension.class).getRunConfigs()) {
                String configName = config.getIdeConfigurationBaseName()
                        + ((project.getRootProject() == project) ? "" : " " + project.getPath().replace(':', '_'));
                VsCodeLaunchJson.Config launchConfig = new VsCodeLaunchJson.Config();

                launchConfig.name = configName;
                launchConfig.command = IdePlugin.toGradleCommand(project) + " " + project.getPath() + ":" + config.getRunTaskName();

                launchJson.configurations.add(launchConfig);
            }
            FileUtils.writeStringToFile(configFile, new Gson().toJson(launchJson), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
