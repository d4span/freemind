Change: add-ci-github-action
Title: Add GitHub Actions workflow to build on every push

Summary
-------
Add a minimal GitHub Actions workflow that runs on every push and builds the
project using Maven. The workflow will:

- Run on every push to any branch.
- Use Java 11 (Temurin) on an ubuntu-latest runner.
- Execute a Maven build of the repository (activating the
  `with-local-libs` profile so local jars under `freemind/lib` are available).
- Skip tests for now (`-DskipTests`) to keep CI fast while tests are stabilized.

Motivation
----------
Provide basic CI feedback on pushes so regressions in the build are detected
early. This is intentionally minimal: no artifact uploads, no matrix, and tests
are skipped as requested.

Scope
-----
- Add OpenSpec artifacts describing the workflow and tasks required to add the
  actual .github/workflows YAML file.
- Do not add the workflow file itself in this change — this proposal captures
  the desired behaviour and tasks.

Acceptance Criteria
-------------------
1. The repository contains an OpenSpec change `add-ci-github-action` with a
   `proposal.md`, `design.md`, and `tasks.md` that clearly describe the
   workflow and the minimal steps required to implement it.
2. The design includes the exact GitHub Actions YAML snippet needed to create
   the workflow.

Deliverables
------------
- proposal.md (this file)
- design.md (workflow and rationale)
- tasks.md (concrete steps to add the workflow file)
