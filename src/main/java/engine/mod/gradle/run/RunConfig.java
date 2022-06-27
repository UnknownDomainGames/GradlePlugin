package engine.mod.gradle.run;

import engine.mod.gradle.task.RunTask;
import org.gradle.api.Project;

import java.util.List;

public abstract class RunConfig {

    public abstract String getName();

    public abstract String getMainClass();

    public abstract List<String> getArgs();

    public abstract List<String> getJvmArgs();

    public void apply(Project project) {
        project.getTasks().create(getRunTaskName(), RunTask.class, this);
    }

    public String getRunTaskName() {
        return "run" + getName();
    }

    public String getIdeConfigurationBaseName() {
        return "Engine " + getName();
    }
}
