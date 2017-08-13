Deploys all jar files from a given directory to a given Maven repository
---

Configuration example:

```
<plugin>
  <groupId>org.abhijitsarkar</groupId>
  <artifactId>deploy-maven-plugin</artifactId>
  <version>1.0-SNAPSHOT</version>
  <configuration>
    <repositories>
      <!-- Tag is the repo id, text is the URL -->
      <local>file://${settings.localRepository}</local>
    </repositories>
    <!-- Absolute path to the lib dir -->
    <lib>${project.build.directory}</lib>
  </configuration>
  <executions>
    <execution>
      <id>deploy-test-artifact</id>
      <goals>
        <goal>deploy</goal>
      </goals>
      <!-- Default phase is deploy -->
      <phase>install</phase>
    </execution>
  </executions>
</plugin>
```

Tries to figure out the Maven group and version to use by reading the `MANIFEST.MF` from the jar file.

**Artifact id**

- Use filename without extension.
- Replace all `.` with `-`.
- Trim.

**Group id**

- If a single `Name` attribute is present, use it.
- Else if multiple `Name` attributes are present, use the shortest one.
- Else if no `Name` attribute is present, use the artifact id.
- Remove trailing `/`, if any.
- Replace all `/` with `.`.
- Trim.

**Version**

- If `Implementation-Version` attribute is present, use it.
- Else if `Specification-Version` attribute is present, use it.
- Else use `0.0.1`.
- Remove all whitespace.
- Trim.

**Packaging**

- jar.

To run:

```
mvn org.abhijitsarkar:deploy-maven-plugin:1.0-SNAPSHOT:deploy
```

To run with `info` logging level:

```
MAVEN_OPTS=-Dorg.slf4j.simpleLogger.defaultLogLevel=info mvn ...
```

