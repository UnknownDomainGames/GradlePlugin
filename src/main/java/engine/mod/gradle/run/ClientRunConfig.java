package engine.mod.gradle.run;

import java.util.List;

public class ClientRunConfig extends DefaultRunConfig {

    public ClientRunConfig() {
        super("Client");
        mainClass("engine.client.launch.Bootstrap");
        jvmArgs(List.of("--add-opens", "java.base/java.lang=ALL-UNNAMED"));
    }

}
