package engine.mod.gradle.task;

import engine.mod.gradle.run.RunConfig;
import org.gradle.api.internal.file.collections.FileCollectionAdapter;
import org.gradle.api.internal.file.collections.MinimalFileSet;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.JavaExec;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RunTask extends JavaExec {

    private final RunConfig config;

    @Inject
    public RunTask(RunConfig config) {
        this.config = config;

        getMainClass().set(config.getMainClass());
        workingDir(getProject().getRootDir());
        args(config.getArgs());
        jvmArgs(config.getJvmArgs());
        setClasspath(new FileCollectionAdapter(new MinimalFileSet() {
            @Override
            @Nonnull
            public Set<File> getFiles() {
                List<File> files = new ArrayList<>(getProject().getConfigurations().getByName(JavaPlugin.RUNTIME_CLASSPATH_CONFIGURATION_NAME).getFiles());
                files.addAll(getProject().getTasks().getByName(JavaPlugin.COMPILE_JAVA_TASK_NAME).getOutputs().getFiles().getFiles());
                files.addAll(getProject().getTasks().getByName(JavaPlugin.PROCESS_RESOURCES_TASK_NAME).getOutputs().getFiles().getFiles());
                return Set.copyOf(files);
            }

            @Override
            @Nonnull
            public String getDisplayName() {
                return "Engine " + config.getName() + " Runtime Classpath";
            }
        }));
        dependsOn(getProject().getTasks().getByName(JavaPlugin.COMPILE_JAVA_TASK_NAME));
        dependsOn(getProject().getTasks().getByName(JavaPlugin.PROCESS_RESOURCES_TASK_NAME));
    }

    @Internal
    public RunConfig getConfig() {
        return config;
    }
}
