Design: Maven build for core freemind module
=========================================

Overview
--------
This design documents the concrete implementation added by the change
`add-maven-build`.

What was implemented
---------------------
- `freemind/pom.xml` was added with the following key elements:
  - Java 11 compiler settings via `maven-compiler-plugin`.
  - `<sourceDirectory>` set to `${project.basedir}/freemind` so existing
    source layout remains unchanged.
  - `maven-antrun-plugin` execution bound to `generate-sources` that runs the
    Ant `gen` target from `freemind/build.xml` to produce JiBX-generated
    artifacts before compilation.
  - `maven-jar-plugin` configured to set `Main-Class` to
    `freemind.main.FreeMindStarter`.
  - A `with-local-libs` profile that exposes local jars under `freemind/lib`
    as system-scoped dependencies for local development builds.

Verification
------------
- Executed: `mvn -f freemind/pom.xml -Pwith-local-libs generate-sources compile package`
  - Build completed successfully (BUILD SUCCESS).
  - JiBX generation ran via the antrun executions.
  - Produced jar: `freemind/target/freemind-1.1.0-SNAPSHOT.jar`.
  - Jar manifest contains `Main-Class: freemind.main.FreeMindStarter`.

Notes and decisions
-------------------
- System-scoped dependencies are used in `with-local-libs` for expediency and to
  avoid changing repository layout; this is acceptable for local builds but
  may be replaced by installing local jars into the local Maven repository in
  later work.
