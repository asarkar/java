package org.abhijitsarkar;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.handler.DefaultArtifactHandler;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import static java.util.Objects.nonNull;

/**
 * @author Abhijit Sarkar
 */
public final class ArtifactParser {
    private ArtifactParser() {
    }

    public static Map.Entry<File, Artifact> parse(Path path) {
        File file = path.toFile();

        try {
            JarFile jarFile = new JarFile(file);
            Manifest manifest = jarFile.getManifest();

            Attributes attributes = manifest.getMainAttributes();

            String name = file.getName();
            int idx = name.lastIndexOf(".");

            String artifactId = name.substring(0, idx)
                    .replaceAll("\\.", "-")
                    .trim();

            String groupId = Optional.ofNullable(attributes.getValue("Name"))
                    .orElseGet(() -> manifest.getEntries().keySet().stream()
                            .sorted()
                            .findAny()
                            .orElse(artifactId)
                    )
                    .replaceAll("/", ".")
                    .trim();

            if (groupId.endsWith(".")) {
                groupId = groupId.substring(0, groupId.length() - 1);
            }

            String version = Optional.ofNullable(attributes.getValue("Implementation-Version"))
                    .orElseGet(() -> attributes.getValue("Specification-Version"));

            version = (nonNull(version) ? version : "0.0.1")
                    .replaceAll("\\s", "")
                    .trim();

            jarFile.close();

            return new AbstractMap.SimpleImmutableEntry<>(file,
                    new DefaultArtifact(groupId, artifactId, version, "compile", "jar",
                            null, new DefaultArtifactHandler()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
