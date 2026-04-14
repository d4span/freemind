Capability: maven-build

Summary
-------
Define the repository Maven build surface required for the freemind module:
- A module-level POM at `freemind/pom.xml`.
- A repository-root aggregator POM at `pom.xml` (repo root) that includes the
  `freemind` module.

The build must execute JiBX code generation during the Maven `generate-sources`
phase and produce an executable jar with manifest `Main-Class: freemind.main.FreeMindStarter`.

What to change
--------------
- freemind/pom.xml (module POM):
  - Use Java 11 for compilation.
  - Ensure JiBX code generation runs during `generate-sources` and produced
    artifacts are available for compilation.
  - Provide a `with-local-libs` profile that includes local jars under
    `freemind/lib` for local development builds.
  - Produce a jar whose manifest sets `Main-Class: freemind.main.FreeMindStarter`.

- Repository-root `pom.xml` (aggregator):
  - groupId: `net.sourceforge.freemind`
  - artifactId: `freemind-parent`
  - version: `1.1.0-SNAPSHOT` (or appropriate snapshot/version)
  - packaging: `pom`
  - include `<module>freemind</module>` so the freemind module is built from
    the repository root.
  - Declare shared properties (for example: file.encoding, java.version) as
    appropriate.

Acceptance Criteria
-------------------
- Module build: From the `freemind` directory, running
  `mvn -f freemind/pom.xml -Pwith-local-libs generate-sources compile package`
  succeeds and produces `freemind/target/*.jar` with a manifest entry
  `Main-Class: freemind.main.FreeMindStarter`.
- Root build: From the repository root (aggregator present), building the
  repository (for example `mvn -Pwith-local-libs -pl freemind -am
  generate-sources compile package` or `mvn package` at root) builds the
  freemind module and produces the same jar at `freemind/target`.
- JiBX generation: Generated sources are produced during `generate-sources`
  and are present for compilation.

Notes
-----
- Preserve the `with-local-libs` profile to avoid breaking local developer
  workflows that depend on local jars.
- Avoid moving or reorganizing source files as part of this capability.
- This spec focuses on required outcomes; implementers may choose specific
  plugins or configuration details as needed.

CI / Workflow
--------------
Add a repository-level GitHub Actions workflow at `.github/workflows/maven-build.yml`
to validate the repository build on push and pull requests. The workflow should
check out the repository, set up the required JDK, and run a Maven invocation
equivalent to the acceptance criteria (for example: `mvn -B -Pwith-local-libs -DskipTests package`).
It should complete a successful build for the `freemind` module and produce the
expected artifacts. If the CI environment cannot use the `with-local-libs`
profile, adapt the workflow to provide or restore required dependencies so the
build remains reproducible in CI.
