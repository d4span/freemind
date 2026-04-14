Change: add-parent-pom
Title: Add a simple aggregator root POM (freemind-parent)

Summary
-------
Add a simple aggregator POM at the repository root named `freemind-parent`.
This POM will be an aggregator only (packaging `pom`) and will include the
existing `freemind` module. The existing module POM at `freemind/pom.xml`
will remain unchanged; the `with-local-libs` profile will be kept in the child
POM.

Motivation
----------
Enabling a repository-level Maven reactor makes it straightforward to build the
project from the repository root (e.g., `mvn -Pwith-local-libs package`). It
also prepares the repository for future multi-module organization while
minimizing risk by not changing the existing module POMs.

Scope
-----
- Add `pom.xml` at the repository root with:
  - groupId = `net.sourceforge.freemind`
  - artifactId = `freemind-parent`
  - version = `1.1.0-SNAPSHOT`
  - packaging = `pom`
  - modules: include `freemind`
  - basic properties for Java 11 and UTF-8 encoding

Out of scope
------------
- Changing `freemind/pom.xml` or moving the `with-local-libs` profile into the
  parent.

Acceptance Criteria
-------------------
1. A new file `pom.xml` exists at the repository root.
2. Running `mvn -Pwith-local-libs -pl freemind -am generate-sources compile package`
   from the repository root completes successfully (the reactor discovers the
   module and builds it).
3. No existing build behavior changes when building the module directly with
   `mvn -f freemind/pom.xml -Pwith-local-libs ...`.
