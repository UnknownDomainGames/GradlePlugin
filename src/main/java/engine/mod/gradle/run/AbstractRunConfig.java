package engine.mod.gradle.run;

import engine.mod.gradle.ide.IdeaConfiguration;
import org.gradle.api.Project;

import java.util.List;

public abstract class AbstractRunConfig {

    public abstract String getName();

    public abstract String getMainClass();

    public abstract List<String> getArgs();

    public abstract List<String> getJvmArgs();

    public void apply(Project project) {
        project.getTasks().create("run" + getName(), RunTask.class, this);
        IdeaConfiguration.createRunConfig(project, this);
    }
}
