Tasks for add-ci-github-action

- [x] Create workflow file
  - Path: `.github/workflows/maven-build.yml`
  - Notes: Created using the YAML snippet from design.md (checkout, setup-java
    JDK25, run `mvn -B -Pwith-local-libs -DskipTests package`).

- [ ] Commit the workflow file on a new branch and push
  - Branch name example: `ci/add-github-action`
  - Create a PR so CI checks run on the branch and validate the workflow
    triggers.

- [ ] Observe CI runs on push
  - Confirm the job starts on push and the Maven invocation completes
    successfully.
  - If the build fails due to missing local jars, double-check the
    `freemind/lib` contents and the `with-local-libs` profile activation.
