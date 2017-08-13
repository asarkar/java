package org.abhijitsarkar;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Abhijit Sarkar
 */
class DeployMojoTest {
    private DeployMojo deployMojo = new DeployMojo();
    private ArtifactDeployer artifactDeployer;

    static Stream<Arguments> gavProvider() {
        return Stream.of(
                Arguments.of("name-and-version.jar", "java.util", "name-and-version", "1.0.0")
        );
    }

    @BeforeEach
    void beforeEach() throws URISyntaxException, MalformedURLException {
        artifactDeployer = mock(ArtifactDeployer.class);
        deployMojo.artifactDeployer = artifactDeployer;
        deployMojo.lib = new File(getClass().getResource("/").toURI());

        Map<String, URL> repositories = new HashMap<>();
        repositories.put("test", new URL("file://url"));
        deployMojo.repositories = repositories;
    }

    @ParameterizedTest(name = "parse {0}")
    @MethodSource("gavProvider")
    void testExecute(String fileName, String groupId, String artifactId, String version)
            throws MojoFailureException, MojoExecutionException {
        deployMojo.execute();

        verify(artifactDeployer).deployArtifact(
                argThat(file -> file.getName().equals(fileName)),
                argThat(artifact -> artifact.getGroupId().equals(groupId)
                        && artifact.getArtifactId().equals(artifactId)
                        && artifact.getVersion().equals(version)),
                argThat(repoId -> repoId.equals("test")),
                argThat(repoUrl -> repoUrl.equals("file://url"))
        );
    }
}