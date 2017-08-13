package org.abhijitsarkar;

import org.apache.maven.artifact.Artifact;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author Abhijit Sarkar
 */
class ArtifactParserTest {
    static Stream<Arguments> gavProvider() {
        return Stream.of(
                Arguments.of("impl-and-spec.jar", "com.ibm.mq", "impl-and-spec", "6.0.0.0-j000-L050519.1"),
                Arguments.of("multi-name.jar", "oracle.sql", "multi-name", "11.2.0.2.0"),
                Arguments.of("name-and-version.jar", "java.util", "name-and-version", "1.0.0"),
                Arguments.of("no-info.jar", "no-info", "no-info", "0.0.1"),
                Arguments.of("no-name.jar", "no-name", "no-name", "11.2.0.2.0"),
                Arguments.of("no-version.jar", "oracle.jms", "no-version", "0.0.1")
        );
    }

    @ParameterizedTest(name = "parse {0}")
    @MethodSource("gavProvider")
    void testParse(String fileName, String groupId, String artifactId, String version) throws URISyntaxException {
        URI lib = getClass().getResource("/").toURI();

        Artifact artifact = ArtifactParser.parse(Paths.get(lib).resolve(fileName)).getValue();

        assertThat(artifact.getGroupId()).isEqualTo(groupId);
        assertThat(artifact.getArtifactId()).isEqualTo(artifactId);
        assertThat(artifact.getVersion()).isEqualTo(version);
    }
}