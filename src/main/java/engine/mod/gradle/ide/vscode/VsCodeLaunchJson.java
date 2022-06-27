package engine.mod.gradle.ide.vscode;

import java.util.LinkedList;
import java.util.List;

public class VsCodeLaunchJson {

    public String version = "0.2.0";
    public List<Config> configurations = new LinkedList();

    // @TODO: `type` field
    public static class Config {

        public String name;
        public String command;
        public String request = "launch";

    }

}
