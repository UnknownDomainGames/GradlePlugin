package engine.mod.gradle.run;

import java.util.List;

public class ClientRunConfig extends AbstractRunConfig {

    @Override
    public String getName() {
        return "Client";
    }

    @Override
    public String getMainClass() {
        return "engine.client.launch.Bootstrap";
    }

    @Override
    public List<String> getArgs() {
        return List.of();
    }

    @Override
    public List<String> getJvmArgs() {
        return List.of("--add-opens", "java.base/java.lang=ALL-UNNAMED");
    }
}
