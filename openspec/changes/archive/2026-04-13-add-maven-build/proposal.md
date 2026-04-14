Change: add-maven-build
Title: Add initial Maven build for core module and JiBX generation step

Summary
-------
Add an initial Maven build for the core `freemind` module. This first Maven
build will:

- Compile the existing source layout.
- Run the JiBX `gen` step during Maven's
  `generate-sources` phase so generated binding classes/jar are available to
  compilation.
- Produce a packaged jar with the manifest Main-Class set to
  `freemind.main.FreeMindStarter`.

Motivation
----------
- Introduce Maven as the project's primary build tool incrementally by
  providing a concrete initial build for the core module.

Constraints & Decisions
-----------------------
Keep current source layout.
- groupId for Maven coordinates: `net.sourceforge.freemind`.
- Java 11 compatibility (match existing Ant settings).

Scope (what this first Maven build will do)
-----------------------------------------
1. Add `freemind/pom.xml` configured to compile sources in-place and to use
   Java 11.
2. Integrate the JiBX `gen` Ant target by invoking it during
   `generate-sources`. The build must run the same generator steps the Ant
   build runs so generated classes/jars are available to compilation.
3. Configure packaging so `mvn -f freemind/pom.xml -Pwith-local-libs generate-sources compile
   package` produces a compiled jar with the manifest Main-Class set to
   `freemind.main.FreeMindStarter`.

Local build profile
-------------------
The POM provides a Maven profile `with-local-libs` that makes the
project's existing local third-party jars (and the generated bindings.jar)
available to Maven during compilation. These artifacts live in `freemind/lib`
and are not published to a remote Maven repository; the profile exposes them
locally so Maven can build the project without changing the repository
layout. Use `-Pwith-local-libs` when building locally.

Out of scope
------------
- Converting plugins, accessories, installers, distribution packaging, or
  changing the repository source layout. Those will be handled in later
  changes.

Acceptance Criteria
-------------------
1. `mvn -f freemind/pom.xml generate-sources compile package` completes
   successfully and runs the JiBX generation step.
2. The produced jar contains a manifest with Main-Class =
   `freemind.main.FreeMindStarter`.
3. No repository source layout changes are required for this build.

Deliverables
------------
- This proposal and a concise task list describing the concrete steps to add
  the Maven build.

 
