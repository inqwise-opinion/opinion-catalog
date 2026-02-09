<INSTRUCTIONS>
# Repository Guidelines

Follow this playbook to keep contributions consistent across Opinion Catalog modules.

| Topic | Guidance |
| --- | --- |
| Project Structure | `opinion-catalog/` hosts the HTTP service entry point, while `opinion-catalog-common/` packages reusable DTOs and helpers; both mirror the Maven `src/main/java` and `src/test/java` layout with generated sources under `src/main/generated`. |
| Module Scaffolding | Every new module must include `src/test/resources/log4j2-test.xml` containing the shared console configuration (see snippet below) and update `coverage-report/pom.xml` with its source/class paths to keep test logging and coverage consistent. |
| Build Commands | `./mvnw clean compile` compiles all modules; `./mvnw -pl opinion-catalog test` runs service tests; swap the module name (e.g., `opinion-catalog-common`) to scope builds. |
| Packaging | `./mvnw package` assembles deployable jars plus source and javadoc bundles; attach profiles locally only when asserting release readiness. |
| Coding Style | Use Java 21, tab indentation, and newline-terminated files; packages remain lowercase, classes PascalCase, members camelCase, and model types avoid public setters in favor of builders exposing `toJson()`. |
| Logging | Concrete classes declare `private static final Logger logger = LogManager.getLogger(ClassName.class);`; do not redeclare in subclasses, and use a `protected static final Logger` when the base type is abstract. |
| Gitignore | Maintain a single repository-wide `.gitignore` in the root; remove or update module-level ignores when scaffolding new packages. |
| Testing | Write JUnit 5 and Vert.x JUnit tests that mirror production packages, name files `*Test`, add an empty `@BeforeEach void setUp(Vertx vertx) throws Exception {}` (or override it) to trigger Vert.x logging hooks, and aim for both success and failure coverage before review. |
| Commits & PRs | Keep commit subjects imperative and ≤72 characters (e.g., `Add CatalogConfig model`), squash noisy history, and ensure pull requests link issues, list verification commands, and attach evidence for behavior changes. |
| Security | Store secrets outside the repo, validate configuration with `vertx-config` JSON locally, and schedule dependency refreshes using `./mvnw versions:use-latest-releases`. |

Additional conventions for agents are listed below. Architecture and dataflow live in `SPEC.md`.

## OpenAPI Contract Conventions

- Operation IDs must match router names.
- Colon paths are not allowed with OpenApiRouter; avoid `:` in OpenAPI paths.
- Success codes: `201` for create with response body, `204` for mutations without body, `200` for reads.
- Error response uses `#/components/schemas/Error`.

## DTO/JSON Conventions

- JSON keys use snake_case.
- DTOs expose `toJson()` and build from `JsonObject`.
- Enumerations serialize as numeric values.

## DAO Conventions

- Named parameters use `p_` prefix (e.g., `p_catalog_id`).
- Stored procedures are invoked via `CALL ...`.
- `RowMapper` and `TupleMapper` live in `DaoMappers`.

## Service Conventions

- Validate input with `ErrorTickets.checkAnyNotNull`.
- Log entry for each public method.
- Prefer read-optimized queries and cache-friendly responses.

## Javadoc

- Keep Javadoc up to date for public classes and methods; add brief summaries for non-obvious helpers.
- Update Javadoc when signatures or behavior changes.

## Prepare for Commit

- Run `./mvnw javadoc:javadoc -B` to validate doclint.
- Run scoped tests for touched modules (e.g., `./mvnw -pl opinion-catalog test`).

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT" follow="true">
            <PatternLayout>
                <pattern>%level{length=1} %d{HH:mm:ss.SSS}[%-26t]%c{...9} - %enc{%msg}{CRLF} %replace{%ex}{[\r\n]{1,2}}{|}%n%throwable</pattern>
            </PatternLayout>
        </Console>
    </Appenders>
    <Loggers>
        <Logger name="io.netty" level="warn" additivity="false">
            <AppenderRef ref="Console"/>
        </Logger>
        <Logger name="com.mchange" level="warn" additivity="false">
            <AppenderRef ref="Console"/>
        </Logger>
        <Root level="all">
            <AppenderRef ref="Console"/>
        </Root>
    </Loggers>
</Configuration>
```


## Skills
A skill is a set of local instructions to follow that is stored in a `SKILL.md` file. Below is the list of skills that can be used. Each entry includes a name, description, and file path so you can open the source for full instructions when using a specific skill.
### Available skills
- skill-creator: Guide for creating effective skills. This skill should be used when users want to create a new skill (or update an existing skill) that extends Codex's capabilities with specialized knowledge, workflows, or tool integrations. (file: /Users/glassfox/.codex/skills/.system/skill-creator/SKILL.md)
- skill-installer: Install Codex skills into $CODEX_HOME/skills from a curated list or a GitHub repo path. Use when a user asks to list installable skills, install a curated skill, or install a skill from another repo (including private repos). (file: /Users/glassfox/.codex/skills/.system/skill-installer/SKILL.md)
### How to use skills
- Discovery: The list above is the skills available in this session (name + description + file path). Skill bodies live on disk at the listed paths.
- Trigger rules: If the user names a skill (with `$SkillName` or plain text) OR the task clearly matches a skill's description shown above, you must use that skill for that turn. Multiple mentions mean use them all. Do not carry skills across turns unless re-mentioned.
- Missing/blocked: If a named skill isn't in the list or the path can't be read, say so briefly and continue with the best fallback.
- How to use a skill (progressive disclosure):
  1) After deciding to use a skill, open its `SKILL.md`. Read only enough to follow the workflow.
  2) When `SKILL.md` references relative paths (e.g., `scripts/foo.py`), resolve them relative to the skill directory listed above first, and only consider other paths if needed.
  3) If `SKILL.md` points to extra folders such as `references/`, load only the specific files needed for the request; don't bulk-load everything.
  4) If `scripts/` exist, prefer running or patching them instead of retyping large code blocks.
  5) If `assets/` or templates exist, reuse them instead of recreating from scratch.
- Coordination and sequencing:
  - If multiple skills apply, choose the minimal set that covers the request and state the order you'll use them.
  - Announce which skill(s) you're using and why (one short line). If you skip an obvious skill, say why.
- Context hygiene:
  - Keep context small: summarize long sections instead of pasting them; only load extra files when needed.
  - Avoid deep reference-chasing: prefer opening only files directly linked from `SKILL.md` unless you're blocked.
- Safety and fallback: If a skill can't be applied cleanly (missing files, unclear instructions), state the issue, pick the next-best approach, and continue.
</INSTRUCTIONS>
