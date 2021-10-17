package engine.mod.gradle.extension.artifact;

@FunctionalInterface
public interface ArtifactNotationFormat {

    ArtifactNotationFormat JITPACK = (module, version) -> "com.github.UnknownDomainGames.PanguEngine:" + module + ":" + version;

    String format(String module, String version);

}
