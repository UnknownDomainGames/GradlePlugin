package engine.mod.gradle.run;

public class ServerRunConfig extends DefaultRunConfig {

    public ServerRunConfig() {
        super("Server");
        mainClass("engine.server.launch.Bootstrap");
    }

}
