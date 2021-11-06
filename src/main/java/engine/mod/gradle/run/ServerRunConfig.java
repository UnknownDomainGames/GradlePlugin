package engine.mod.gradle.run;

import java.util.List;

public class ServerRunConfig extends AbstractRunConfig {

    @Override
    public String getName() {
        return "Server";
    }

    @Override
    public String getMainClass() {
        return "engine.server.launch.Bootstrap";
    }

    @Override
    public List<String> getArgs() {
        return List.of();
    }

    @Override
    public List<String> getJvmArgs() {
        return List.of();
    }

}
