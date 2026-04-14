Capability: build-core

Summary
-------
Provide a Maven build for the core freemind module that compiles the existing
sources in-place, runs the JiBX `gen` Ant target during `generate-sources`, and
produces a jar with Main-Class = `freemind.main.FreeMindStarter`.

What to change
--------------
- Add `freemind/pom.xml` configured to:
  - Use Java 11 for compilation.
  - Keep the existing source layout by setting `<sourceDirectory>` to
    `${project.basedir}/freemind`.
  - Add a `with-local-libs` profile that exposes local jars under
    `freemind/lib` as system-scoped dependencies for local builds.
  - Run the Ant `gen` target via `maven-antrun-plugin` during
    `generate-sources` so JiBX-generated artifacts are available for
    compilation.
  - Configure `maven-jar-plugin` to set `Main-Class` to
    `freemind.main.FreeMindStarter`.

Acceptance Criteria
-------------------
- Running `mvn -f freemind/pom.xml -Pwith-local-libs generate-sources compile package`
  completes successfully.
- The produced jar at `freemind/target` contains a manifest with
  `Main-Class: freemind.main.FreeMindStarter`.

Notes
-----
- This is an incremental change: do not change repository layout or move
  existing build responsibilities away from Ant for unrelated modules.
