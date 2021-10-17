package engine.mod.gradle.task;

import org.gradle.api.internal.file.collections.FileCollectionAdapter;
import org.gradle.api.internal.file.collections.MinimalFileSet;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.JavaExec;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RunClientTask extends JavaExec {

    public RunClientTask() {
        super();
        getMainClass().set("engine.client.launch.Bootstrap");
        setArgs(List.of());
        setJvmArgs(List.of("--add-opens", "java.base/java.lang=ALL-UNNAMED","-agentlib:jdwp=transport=dt_socket,server=n,address=xtex:1075,suspend=y"));
        dependsOn(getProject().getTasks().getByName(JavaPlugin.JAR_TASK_NAME));
    }

    @Override
    public void exec() {
        { // Configure classpath
            setClasspath(new FileCollectionAdapter(new MinimalFileSet() {
                @Override
                public @Nonnull
                Set<File> getFiles() {
                    List<File> files = new ArrayList<>(getProject().getConfigurations().getByName(JavaPlugin.RUNTIME_CLASSPATH_CONFIGURATION_NAME).getFiles());
                    files.addAll(getProject().getTasks().getByName(JavaPlugin.COMPILE_JAVA_TASK_NAME).getOutputs().getFiles().getFiles());
                    files.addAll(getProject().getTasks().getByName(JavaPlugin.PROCESS_RESOURCES_TASK_NAME).getOutputs().getFiles().getFiles());
                    System.out.println(files);
                    return Set.copyOf(files);
                }

                @Override
                public @Nonnull
                String getDisplayName() {
                    return "Engine Runtime Classpath";
                }
            }));
        }

        super.exec();
    }
}
