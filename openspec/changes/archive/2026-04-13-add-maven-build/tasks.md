Tasks for add-maven-build (initial Maven build)
==============================================

Goal
----
Add an initial Maven build for the core `freemind` module that compiles the
existing sources, runs the JiBX `gen` Ant target during `generate-sources`,
and packages a jar with the correct Main-Class manifest.

Tasks
-----
 - [x] Create `freemind/pom.xml`
    - groupId = `net.sourceforge.freemind`, artifactId = `freemind`, version =
      `1.1.0-SNAPSHOT`.
    - Configure `maven-compiler-plugin` to use Java 11.
    - Keep the existing source layout; configure `<sourceDirectory>` so the
      compiler picks up the current layout (sources live under the `freemind/`
      subdirectory).
    - Configure `maven-jar-plugin` to set Main-Class =
      `freemind.main.FreeMindStarter`.

 - [x] Add JiBX `gen` integration
    - Use `maven-antrun-plugin` bound to the `generate-sources` phase to run
      the Ant `gen` target from `freemind/build.xml` so generated binding
      classes/jar are available for compilation.

 - [x] Build verification
    - Run `mvn -f freemind/pom.xml -Pwith-local-libs generate-sources compile package` and
     verify it completes successfully.
    - Verify the produced jar manifest contains the Main-Class entry.

Acceptance Criteria
-------------------
- The Maven command above (use `-Pwith-local-libs`) completes without error,
  executes JiBX generation, and creates a jar with the manifest Main-Class.

Profile explanation
-------------------
When building locally, the project relies on third-party jars that live under
`freemind/lib` and are not available from a remote Maven repository. The
`with-local-libs` profile exposes those local jars to Maven (via system
dependencies) so the compiler sees the same classpath the Ant build used.

Notes
-----
- Do not change repository layout in this change; leave plugin and installer
  builds to Ant for later migration.
