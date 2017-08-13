package org.abhijitsarkar;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;

import static java.util.Objects.isNull;

@Mojo(name = "deploy", defaultPhase = LifecyclePhase.DEPLOY)
public class DeployMojo extends AbstractMojo {
    @Parameter(required = true)
    Map<String, URL> repositories;
    @Parameter(required = true)
    File lib;

    @Parameter(defaultValue = "${session}", readonly = true)
    MavenSession mavenSession;

    @Component
    BuildPluginManager pluginManager;

    ArtifactDeployer artifactDeployer;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        // For unit testing
        if (isNull(artifactDeployer)) {
            artifactDeployer = new DefaultArtifactDeployer(mavenSession, pluginManager);
        }

        try {
            Files.find(lib.toPath(), 1, (path, attr) -> path.toFile().getName().endsWith("jar"))
                    .map(ArtifactParser::parse)
                    .forEach(e -> {
                        File file = e.getKey();
                        Artifact artifact = e.getValue();

                        repositories.forEach((id, url) -> {
                            try {
                                getLog().info(String.format("Deploying artifact: %s based on file: %s to URL: %s.",
                                        artifact, file.getAbsolutePath(), url));

                                artifactDeployer.deployArtifact(file, artifact, id, url);
                            } catch (MojoExecutionException ex) {
                                throw new RuntimeException(ex);
                            }
                        });
                    });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
