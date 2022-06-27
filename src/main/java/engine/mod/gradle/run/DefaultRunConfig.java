package engine.mod.gradle.run;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class DefaultRunConfig extends RunConfig {

    private final String name;
    private String mainClass;
    private List<String> args = new ArrayList<>();
    private List<String> jvmArgs = new ArrayList<>();

    public DefaultRunConfig(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMainClass() {
        return mainClass;
    }

    @Override
    public List<String> getArgs() {
        return args;
    }

    @Override
    public List<String> getJvmArgs() {
        return jvmArgs;
    }

    public void mainClass(String mainClass) {
        setMainClass(mainClass);
    }

    public void args(List<String> args) {
        this.args.addAll(args);
    }

    public void jvmArgs(List<String> jvmArgs) {
        this.jvmArgs.addAll(jvmArgs);
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public void setJvmArgs(List<String> jvmArgs) {
        this.jvmArgs = jvmArgs;
    }

}
