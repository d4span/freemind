Tasks for add-parent-pom
========================

Goal
----
Add a repository-root aggregator POM named `freemind-parent` that includes the
existing `freemind` module.

Tasks
-----
- [x] Create `pom.xml` at repository root with these elements:
  - groupId: `net.sourceforge.freemind`
  - artifactId: `freemind-parent`
  - version: `1.1.0-SNAPSHOT`
  - packaging: `pom`
  - modules: include `<module>freemind</module>`
  - properties for UTF-8 encoding and Java 11 (for convenience)

- [x] Run verification commands from repository root:
  - `mvn -Pwith-local-libs -pl freemind -am generate-sources compile package`
  - confirm jar at `freemind/target` and JiBX generation ran

Notes
-----
- Keep the `with-local-libs` profile inside `freemind/pom.xml` so local builds
  remain unchanged.
- Consider converting the root POM to be a parent (and updating the child
  pom to reference it) as a follow up to centralize plugin/version management.
