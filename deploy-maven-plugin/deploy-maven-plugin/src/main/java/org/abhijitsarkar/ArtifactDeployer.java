package org.abhijitsarkar;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.net.URL;

import static org.twdata.maven.mojoexecutor.MojoExecutor.artifactId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.configuration;
import static org.twdata.maven.mojoexecutor.MojoExecutor.element;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executeMojo;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executionEnvironment;
import static org.twdata.maven.mojoexecutor.MojoExecutor.goal;
import static org.twdata.maven.mojoexecutor.MojoExecutor.groupId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.name;
import static org.twdata.maven.mojoexecutor.MojoExecutor.plugin;
import static org.twdata.maven.mojoexecutor.MojoExecutor.version;

/**
 * @author Abhijit Sarkar
 */
public interface ArtifactDeployer {
    void deployArtifact(File file, Artifact artifact, String repoId, URL repoUrl) throws MojoExecutionException;
}

final class DefaultArtifactDeployer implements ArtifactDeployer {
    private final MavenSession mavenSession;

    private final BuildPluginManager pluginManager;

    DefaultArtifactDeployer(MavenSession mavenSession, BuildPluginManager pluginManager) {
        this.mavenSession = mavenSession;
        this.pluginManager = pluginManager;
    }

    @Override
    public void deployArtifact(File file, Artifact artifact, String repoId, URL repoUrl)
            throws MojoExecutionException {
        executeMojo(
                plugin(
                        groupId("org.apache.maven.plugins"),
                        artifactId("maven-deploy-plugin"),
                        version("2.8.2")
                ),
                goal("deploy-file"),
                configuration(
                        element(name("repositoryId"), repoId),
                        element(name("file"), file.getAbsolutePath()),
                        element(name("url"), repoUrl.toString()),
                        element(name("groupId"), artifact.getGroupId()),
                        element(name("artifactId"), artifact.getArtifactId()),
                        element(name("version"), artifact.getVersion()),
                        element(name("packaging"), artifact.getType())
                ),
                executionEnvironment(
                        mavenSession,
                        pluginManager
                )
        );
    }
}
