# ColoriLens Workflow and Documentation Map

Last updated: 2026-05-17

## 1. How to work on this project

Use this workflow for each meaningful change:

1. Read `docs/PRODUCT_REQUIREMENTS.md`.
2. Read the relevant agent role file in `docs/agents/`.
3. Update or create a short task plan.
4. Implement the smallest useful increment.
5. Run tests and checks.
6. Update documentation if behavior changed.
7. Commit changes with a clear message.
8. Ask the QA agent/test role to review the changed flow.

## 2. Documentation files

Required foundation:

- `README.md`: short project overview and entry point.
- `docs/PRODUCT_REQUIREMENTS.md`: living product requirements and scope.
- `docs/TECHNICAL_BLUEPRINT.md`: architecture, stack, data model, and AI integration plan.
- `docs/workflow/WORKFLOW.md`: team process and documentation map.
- `docs/agents/PROJECT_MANAGER.md`: product/project manager role prompt.
- `docs/agents/DESIGNER.md`: UI/UX designer role prompt.
- `docs/agents/DEVELOPER.md`: Android/backend developer role prompt.
- `docs/agents/QA_TESTER.md`: tester role prompt.
- `data/activities_seed.json`: seed activity catalog for MVP.

Recommended future docs:

- `docs/FORMULAS.md`: BMR/TDEE/MET formulas with sources and examples.
- `docs/API_CONTRACT.md`: backend endpoints and JSON schemas.
- `docs/DATABASE_SCHEMA.md`: Room schema and migration history.
- `docs/DESIGN_SYSTEM.md`: colors, typography, spacing, reusable components.
- `docs/RELEASE_CHECKLIST.md`: Google Play, privacy, closed testing, subscriptions, ads.
- `docs/PRIVACY.md`: data collected, retention, deletion, and external processors.
- `docs/TEST_PLAN.md`: regression checklist for each release.

## 3. Agent collaboration model

The project uses four documented roles:

1. Project Manager: owns scope, priorities, acceptance criteria, backlog clarity.
2. Designer: owns UI/UX, accessibility, visual consistency, user flows.
3. Developer: owns Android/backend implementation, architecture, code quality.
4. QA Tester: owns regression testing, bug reports, and release confidence.

A practical loop:

```text
Owner idea/request
  → Project Manager turns it into requirements and acceptance criteria
  → Designer checks UX and screen behavior
  → Developer implements
  → QA Tester verifies and reports bugs
  → Project Manager updates backlog and release notes
```

## 4. Suggested milestones

### Milestone 0: Foundation

- Product requirements documented.
- Technical blueprint documented.
- Agent role prompts documented.
- Activity seed catalog started.

### Milestone 1: Android scaffold

- Kotlin + Compose app builds.
- Navigation shell with Today, History, Stats, Activity, Profile.
- Light/dark Material 3 theme.
- Russian and English string resources.

### Milestone 2: Local diary

- Room database.
- Manual meal creation/edit/delete.
- Meal history by day.
- Profile setup.

### Milestone 3: AI photo analysis

- Camera/import flow.
- Backend proxy or developer-only AI client.
- Structured OpenAI nutrition estimate.
- Review-before-save screen.

### Milestone 4: Activities and calorie balance

- Activity catalog search.
- Add/edit/delete activity logs.
- MET calorie calculation.
- Daily deficit/surplus calculation.

### Milestone 5: Statistics

- Day/week/month/year charts.
- Macro charts.
- Period averages.

### Milestone 6: Play-ready hardening

- Privacy policy.
- Data deletion/export.
- Backend quotas.
- Monetization experiment design.
- Closed testing checklist.

## 5. Definition of done

A feature is done when:

- It matches `docs/PRODUCT_REQUIREMENTS.md` or an approved update to it.
- It has localized Russian and English UI strings.
- It stores data safely and predictably.
- It supports edit/delete where the product requires it.
- It has unit tests for calculations and data transformations.
- It has manual QA notes for affected flows.
- Documentation is updated if user-visible behavior changed.
