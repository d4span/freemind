Design: GitHub Actions workflow for Maven build

Overview
--------
The workflow is intentionally small and deterministic:

- Trigger: on push (all branches)
- Runner: ubuntu-latest
- Java: Temurin JDK 25 (actions/setup-java)
- Maven invocation: run from repo root and build the aggregator / freemind
  module with the `with-local-libs` profile. Skip tests with `-DskipTests` as
  requested.
- Caching: use actions/setup-java's built-in maven cache to speed subsequent
  runs.

YAML Snippet
------------
Use this as the exact contents for `.github/workflows/maven-build.yml`:

```yaml
name: CI — Maven build

on:
  push:
    branches: ["**"]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Set up JDK 25 and cache Maven
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '25'
          cache: 'maven'

      - name: Build (Maven)
        run: mvn -B -Pwith-local-libs -DskipTests package
```

Notes and rationale
-------------------
- We build at the repository root which ensures the aggregator POM (already
  present) is used and `freemind` is built. The `with-local-libs` profile must
  be activated so the jars in `freemind/lib` are available (they're included
  in the repository and the profile uses system-scoped dependencies referencing
  those paths).
- `-DskipTests` preserves build speed and avoids test environment issues while
  the test suite is stabilized for CI.
- Caching Maven artifacts via `actions/setup-java` reduces build time for
  repeated pushes.

Ensuring Java 11 bytecode from a newer JDK
-----------------------------------------
Although the pipeline runs on JDK 25, the project POM targets Java 11. To
guarantee the produced classes are compatible with Java 11 at the bytecode
level when compiling on JDK 25, configure the maven-compiler-plugin to use
`<release>11</release>` (preferred) or set `--release 11` via the plugin's
configuration. This removes reliance on the bootclasspath and prevents subtle
compatibility differences.

Example plugin snippet (put in the freemind pom under `<build><plugins>`):

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-compiler-plugin</artifactId>
  <version>3.10.1</version>
  <configuration>
    <release>11</release>
    <fork>true</fork>
  </configuration>
</plugin>
```

Risks / Follow-ups
------------------
- The `with-local-libs` profile uses system-scoped dependencies pointing to
  repository-relative paths. If the workflow later moves to using published
  Maven artifacts, the profile can be removed and the build updated.
- If tests are later required, remove `-DskipTests` and address any
  environment-specific test failures (headless JVM, GUI dependencies, etc.).
