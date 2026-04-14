Design: Add repository-level aggregator POM
==========================================

Overview
--------
This change adds a repository-root `pom.xml` that acts as an aggregator for the
existing `freemind` module. The aggregator is intentionally minimal and does
not modify or act as a parent for the existing module POM. This keeps the
change low-risk while enabling reactor builds from the repo root.

Structure
---------
The root POM will contain:

- modelVersion, groupId, artifactId = `freemind-parent`, version
  `1.1.0-SNAPSHOT`, packaging `pom`.
- A `<modules>` section with a single `<module>freemind</module>` entry.
- Properties for `project.build.sourceEncoding`, `maven.compiler.source`,
  and `maven.compiler.target` to document the Java 11 requirement at the
  repository level. Child POMs may still override these values; this is purely
  a convenience and does not change child behavior.

Why not make this a parent?
---------------------------
The goal is to be minimally invasive. Converting the root POM into a parent
and updating `freemind/pom.xml` to add a `<parent>` block is useful for
centralization, but it's a slightly larger change and can be done as a followup
once the aggregator is in place.

Behavioral notes
----------------
- Reactor builds: running `mvn package` at repo root will invoke the `freemind`
  module's build as part of the reactor. The child module's antrun invocation
  uses `${project.basedir}/build.xml` and will run in the module directory as
  before.
- The `with-local-libs` profile remains in `freemind/pom.xml` and must be
  activated with `-Pwith-local-libs` when building locally.
