Experimental JPMS project
===

### Create output dir
```
$ mkdir -p out/classes/com.greetings
```
> No need though, Java 9+ compiler creates non-existent output directories (but jar doesn't :( )

### Compile
```
$ javac -d out/classes/org.astro \
  org.astro/src/main/java/module-info.java org.astro/src/main/java/org/astro/World.java
$ javac -d out/classes/com.greetings --module-path out/classes \
  com.greetings/src/main/java/module-info.java \
  com.greetings/src/main/java/com/greetings/Main.java
```
> Specifies a module path so that the reference to module org.astro and the types in its exported packages can be resolved

### Simpler compilation
```
$ javac -d out/classes --module-source-path "./*/src/main/java/" $(find . -name "*.java" ! -path "*/test/*")
```

### Run
```
$ java --module-path out/classes -m com.greetings/com.greetings.Main
```

### Make JAR
```
$ mkdir out/lib
$ jar --create --file=out/lib/org.astro@1.0.jar \
  --module-version=1.0 -C out/classes/org.astro .
$ jar --create --file=out/lib/com.greetings.jar \
  --main-class=com.greetings.Main -C out/classes/com.greetings .
```

## Run JAR
```
$ java -p out/lib -m com.greetings
```

## Notes
* The compiler and runtime option `--add-reads $module=$targets` adds readability edges from `$module` to all modules
in the comma-separated list `$targets`. This allows `$module` to access all public types in packages exported by
those modules even though `$module` has no `requires` clauses mentioning them. If `$targets` is set to `ALL-UNNAMED`,
`$module` can even read the unnamed module.
* The compiler and runtime option `--patch-module $module=$artifact` merges all classes from `$artifact` into `$module`.
* The compiler and runtime option `--add-modules $modules`, allows explicitly defining a comma-separated
list of root modules beyond the initial module.
* The compiler and runtime option `--add-exports $module/$package=$readingmodule` exports `$package` of `$module` to
`$readingmodule`. Code in `$readingmodule` can hence access all public types in `$package` but other modules can not.

## References
* https://openjdk.java.net/projects/jigsaw/quick-start
* https://www.baeldung.com/java-9-modularity
* https://blog.codefx.org/java/java-module-system-tutorial/
* http://tutorials.jenkov.com/java/modules.html
* https://dzone.com/articles/java-9-modules-introduction-part-1
* https://blog.codefx.org/java/five-command-line-options-hack-java-module-system/#Adding-Classes-To-Modules-With--patch-module
* http://branchandbound.net/blog/java/2017/12/automatic-module-name/

